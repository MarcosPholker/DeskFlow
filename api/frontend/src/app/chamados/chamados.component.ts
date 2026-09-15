import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

interface Chamado {
  id: number;
  titulo: string;
  descricao: string;
  status: 'ABERTO' | 'FECHADO';
}

@Component({
  selector: 'app-chamados',
  imports: [ReactiveFormsModule],
  templateUrl: './chamados.component.html',
  styleUrl: './chamados.component.scss'
})
export class ChamadosComponent {
  private readonly http = inject(HttpClient);
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly apiBase = 'http://localhost:8081';

  protected readonly chamados = signal<Chamado[]>([]);
  protected readonly isFormVisible = signal(false);
  protected readonly isLoading = signal(true);
  protected readonly isSubmitting = signal(false);
  protected readonly message = signal('');
  protected readonly messageType = signal<'success' | 'error'>('success');
  protected readonly pendingCloseId = signal<number | null>(null);
  protected readonly form = this.formBuilder.nonNullable.group({
    titulo: ['', Validators.required],
    descricao: ['', Validators.required]
  });

  constructor() {
    this.loadChamados();
  }

  protected loadChamados(): void {
    this.http.get<Chamado[]>(`${this.apiBase}/chamado/listar`, { headers: this.authHeaders() }).subscribe({
      next: (chamados) => { this.chamados.set(chamados); this.isLoading.set(false); },
      error: (error) => {
        this.isLoading.set(false);
        if (error.status === 401 || error.status === 403) {
          this.authService.logout();
          this.router.navigate(['/login']);
          return;
        }
        this.showMessage('Não foi possível carregar os chamados.', 'error');
      }
    });
  }

  protected createChamado(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.isSubmitting.set(true);
    this.http.post(`${this.apiBase}/chamado`, this.form.getRawValue(), { headers: this.authHeaders() }).subscribe({
      next: () => {
        this.showMessage('Chamado criado com sucesso.', 'success');
        this.form.reset();
        this.isFormVisible.set(false);
        this.isSubmitting.set(false);
        this.loadChamados();
      },
      error: (error) => {
        this.showMessage(this.errorMessage(error, 'Não foi possível criar o chamado.'), 'error');
        this.isSubmitting.set(false);
      }
    });
  }

  protected closeChamado(id: number): void {
    this.pendingCloseId.set(id);
  }

  protected cancelClose(): void {
    this.pendingCloseId.set(null);
  }

  protected confirmClose(): void {
    const id = this.pendingCloseId();
    if (id === null) return;

    this.pendingCloseId.set(null);
    this.http.put(`${this.apiBase}/chamado/alterar/${id}?status=FECHADO`, {}, {
      headers: this.authHeaders(),
      responseType: 'text'
    }).subscribe({
      next: () => { this.showMessage('Chamado fechado com sucesso.', 'success'); this.loadChamados(); },
      error: (error) => {
        if (error.status === 401 || error.status === 403) {
          this.authService.logout();
          this.router.navigate(['/login']);
          return;
        }
        this.showMessage(this.errorMessage(error, 'Não foi possível fechar o chamado.'), 'error');
      }
    });
  }

  protected logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  protected showForm(): void {
    this.message.set('');
    this.form.reset();
    this.isFormVisible.set(true);
  }

  protected hideForm(): void {
    this.form.reset();
    this.isFormVisible.set(false);
  }

  private authHeaders(): HttpHeaders {
    return new HttpHeaders({ Authorization: `Bearer ${localStorage.getItem('token') ?? ''}` });
  }

  private showMessage(message: string, type: 'success' | 'error'): void {
    this.message.set(message);
    this.messageType.set(type);
  }

  private errorMessage(error: { error?: { erro?: string } | string }, fallback: string): string {
    if (typeof error.error === 'object' && error.error?.erro) return error.error.erro;
    if (typeof error.error === 'string' && error.error) return error.error;
    return fallback;
  }
}
