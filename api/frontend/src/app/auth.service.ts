import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiBase = 'http://localhost:8081';

  login(email: string, password: string): Observable<string> {
    return this.http.post(`${this.apiBase}/login`, { email, password }, { responseType: 'text' }).pipe(
      tap((token) => localStorage.setItem('token', token))
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return Boolean(localStorage.getItem('token'));
  }
}
