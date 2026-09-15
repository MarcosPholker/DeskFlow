// ============================
// ELEMENTOS DA PÁGINA
// ============================

const form = document.querySelector("form");

const listaChamados = document.querySelector("#lista-chamados");

const botaoNovoChamado = document.querySelector("#novo-chamado");

const formularioChamado = document.querySelector("#formulario-chamado");

const formChamado = document.querySelector("#form-chamado");

const botaoCancelar = document.querySelector("#cancelar-chamado");

const botaoLogout = document.querySelector("#logout");


// ============================
// LOGIN
// ============================

if (form && document.querySelector("#email")) {

    form.addEventListener("submit", async function (event) {

        event.preventDefault();

        const email = document.querySelector("#email").value;

        const password = document.querySelector("#password").value;


        const resposta = await fetch(
            "http://localhost:8082/login",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }
        );


        if (!resposta.ok) {

            alert("E-mail ou senha inválidos.");

            return;
        }


        const token = await resposta.text();

        localStorage.setItem("token", token);


        console.log("Login realizado!");


        // Vai para a página de chamados
        window.location.href = "chamados.html";

    });

}


// ============================
// LISTAR CHAMADOS
// ============================

if (listaChamados) {

    carregarChamados();

}


async function carregarChamados() {

    const token = localStorage.getItem("token");


    if (!token) {

        window.location.href = "index.html";

        return;
    }


    const resposta = await fetch(
        "http://localhost:8082/chamado/listar",
        {
            method: "GET",

            headers: {
                "Authorization": "Bearer " + token
            }
        }
    );


    if (!resposta.ok) {

        alert("Não foi possível carregar os chamados.");

        return;
    }


    const chamados = await resposta.json();


    listaChamados.innerHTML = "";


    chamados.forEach(chamado => {

        const div = document.createElement("div");

        div.classList.add("chamado");


        div.innerHTML = `
            <h3>#${chamado.id} - ${chamado.titulo}</h3>

            <p>${chamado.descricao}</p>

            <strong>Status: ${chamado.status}</strong>

            <div class="acoes-chamado">

                <button
                    class="btn-editar"
                    onclick="editarChamado(
                        ${chamado.id},
                        '${chamado.titulo.replace(/'/g, "\\'")}',
                        '${chamado.descricao.replace(/'/g, "\\'")}'
                    )">
                    Editar
                </button>

                <button
                    class="btn-excluir"
                    onclick="excluirChamado(${chamado.id})">
                    Excluir
                </button>

            </div>
        `;


        listaChamados.appendChild(div);

    });

}


// ============================
// NOVO CHAMADO
// ============================

if (
    botaoNovoChamado &&
    formularioChamado &&
    formChamado &&
    listaChamados
) {

    botaoNovoChamado.addEventListener("click", function () {

        formularioChamado.style.display = "block";

        listaChamados.style.display = "none";

        botaoNovoChamado.style.display = "none";


        // Garante que é um novo chamado
        formChamado.reset();

        delete formChamado.dataset.id;


        document.querySelector(".formulario-header h2").textContent =
            "Novo chamado";


        document.querySelector(".btn-criar").textContent =
            "Criar chamado";

    });

}


// ============================
// CANCELAR
// ============================

if (
    botaoCancelar &&
    formularioChamado &&
    formChamado &&
    listaChamados
) {

    botaoCancelar.addEventListener("click", function () {

        formularioChamado.style.display = "none";

        listaChamados.style.display = "flex";

        botaoNovoChamado.style.display = "block";


        formChamado.reset();

        delete formChamado.dataset.id;


        document.querySelector(".formulario-header h2").textContent =
            "Novo chamado";


        document.querySelector(".btn-criar").textContent =
            "Criar chamado";

    });

}


// ============================
// CRIAR / EDITAR CHAMADO
// ============================

const mensagem = document.querySelector("#mensagem");
if (formChamado) {

    formChamado.addEventListener("submit", async function (event) {

        event.preventDefault();


        const titulo =
            document.querySelector("#titulo").value;


        const descricao =
            document.querySelector("#descricao").value;


        const token =
            localStorage.getItem("token");


        // Verifica se existe ID
        const id = formChamado.dataset.id;


        // Por padrão será criação
        let url = "http://localhost:8082/chamado";

        let metodo = "POST";


        // Se tiver ID, será edição
        if (id) {

            url =
                "http://localhost:8082/chamado/alterar/" + id;

            metodo = "PUT";

        }

        const resposta = await fetch(
            url,
            {
                method: metodo,

                headers: {
                    "Content-Type": "application/json",

                    "Authorization": "Bearer " + token
                },

                body: JSON.stringify({
                    titulo: titulo,
                    descricao: descricao
                })
            }
        );

        function mostrarMensagem(texto, tipo) {

            mensagem.textContent = texto;

            mensagem.className = "mensagem " + tipo;

        }


        if (!resposta.ok) {

            const erro = await resposta.json();

            mostrarMensagem(
                erro.erro || "Não foi possível salvar o chamado.",
                "erro"
            );

            return;
        }

        if (id) {

            alert("Chamado alterado com sucesso!");

        } else {

            alert("Chamado criado com sucesso!");

        }


        // Limpa formulário
        formChamado.reset();


        // Remove o ID de edição
        delete formChamado.dataset.id;


        // Volta para lista
        formularioChamado.style.display = "none";

        listaChamados.style.display = "flex";

        botaoNovoChamado.style.display = "block";


        // Volta título do formulário
        document.querySelector(".formulario-header h2").textContent =
            "Novo chamado";


        document.querySelector(".btn-criar").textContent =
            "Criar chamado";


        // Atualiza lista
        carregarChamados();

    });

}


// ============================
// EDITAR CHAMADO
// ============================

function editarChamado(id, titulo, descricao) {

    formularioChamado.style.display = "block";

    listaChamados.style.display = "none";

    botaoNovoChamado.style.display = "none";


    // Preenche os campos
    document.querySelector("#titulo").value = titulo;

    document.querySelector("#descricao").value = descricao;


    // Guarda o ID do chamado
    formChamado.dataset.id = id;


    // Muda o título
    document.querySelector(".formulario-header h2").textContent =
        "Editar chamado";


    // Muda o botão
    document.querySelector(".btn-criar").textContent =
        "Salvar alterações";

}


// ============================
// EXCLUIR CHAMADO
// ============================

async function excluirChamado(id) {

    const confirmar = confirm(
        "Tem certeza que deseja excluir este chamado?"
    );


    if (!confirmar) {

        return;
    }


    const token =
        localStorage.getItem("token");


    const resposta = await fetch(
        "http://localhost:8082/chamado/delete/" + id,
        {
            method: "DELETE",

            headers: {
                "Authorization": "Bearer " + token
            }
        }
    );


    if (!resposta.ok) {

        alert("Não foi possível excluir o chamado.");

        return;
    }


    alert("Chamado excluído!");


    // Atualiza a lista
    carregarChamados();

}


// ============================
// LOGOUT
// ============================

if (botaoLogout) {

    botaoLogout.addEventListener("click", function () {

        localStorage.removeItem("token");

        window.location.href = "index.html";

    });

}