const API = "http://localhost:8080/carteira";

async function atualizarSaldo() {
  const res = await fetch(`${API}/saldo`);
  const saldo = await res.text();
  document.getElementById("saldo").innerText = "R$ " + parseFloat(saldo).toFixed(2);
}

async function atualizarHistorico() {
  const res = await fetch(`${API}/historico`);
  const lista = await res.json();

  const ul = document.getElementById("historico");
  ul.innerHTML = "";

  lista.forEach(item => {
    const li = document.createElement("li");
    li.innerText = item;
    li.style.color = item.startsWith("+") ? "green" : "red";
    ul.appendChild(li);
  });
}

async function adicionar() {
  const valor = document.getElementById("valor").value;
  await fetch(`${API}/adicionar?valor=${valor}`, { method: "POST" });
  atualizarSaldo();
  atualizarHistorico();
}

async function retirar() {
  const valor = document.getElementById("valor").value;
  await fetch(`${API}/retirar?valor=${valor}`, { method: "POST" });
  atualizarSaldo();
  atualizarHistorico();
}

// 🔥 ADICIONA AQUI PRA BAIXO

async function simularInvestimento() {
  const valor = document.getElementById("invValor").value;
  const taxa = document.getElementById("invTaxa").value;
  const meses = document.getElementById("invMeses").value;

  await fetch(`${API}/investir?valor=${valor}&taxa=${taxa}&meses=${meses}`, {
    method: "POST"
  });

  alert("Investimento realizado!");

  atualizarSaldo();
  atualizarHistorico();
  atualizarDivida();
}

async function simularEmprestimo() {
  const valor = document.getElementById("empValor").value;
  const taxa = document.getElementById("empTaxa").value;
  const meses = document.getElementById("empMeses").value;

  await fetch(`${API}/emprestimo?valor=${valor}&taxa=${taxa}&meses=${meses}`, {
    method: "POST"
  });

  alert("Empréstimo aprovado!");

  atualizarSaldo();
  atualizarHistorico();
  atualizarDivida();
}
async function atualizarDivida() {
  const res = await fetch(`${API}/divida`);
  const valor = await res.text();

  document.getElementById("divida").innerText =
    "R$ " + parseFloat(valor).toFixed(2);
}

async function pagarDivida() {
  const valor = document.getElementById("valorDivida").value;

  await fetch(`${API}/pagar-divida?valor=${valor}`, {
    method: "POST"
  });

  atualizarSaldo();
  atualizarHistorico();
  atualizarDivida();
}


// 🚀 mantém isso no final
atualizarSaldo();
atualizarHistorico();
atualizarDivida();