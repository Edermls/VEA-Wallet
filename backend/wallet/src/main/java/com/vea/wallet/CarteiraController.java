package com.vea.wallet;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carteira")
@CrossOrigin(origins = "*")
public class CarteiraController {

    private float saldo = 0;
    private double divida = 0;
    private List<String> historico = new ArrayList<>();

    // =========================
    // 💰 OPERAÇÕES BÁSICAS
    // =========================

    @PostMapping("/adicionar")
    public float adicionar(@RequestParam float valor) {
        saldo += valor;
        historico.add("+ R$ " + String.format("%.2f", valor));
        return saldo;
    }

    @PostMapping("/retirar")
    public float retirar(@RequestParam float valor) {
        if (valor > saldo) {
            throw new RuntimeException("Saldo insuficiente");
        }
        saldo -= valor;
        historico.add("- R$ " + String.format("%.2f", valor));
        return saldo;
    }

    @GetMapping("/saldo")
    public float saldo() {
        return saldo;
    }

    @GetMapping("/historico")
    public List<String> historico() {
        return historico;
    }

    // =========================
    // 📊 SIMULAÇÕES
    // =========================

    @GetMapping("/simular-investimento")
    public double simularInvestimento(
            @RequestParam double valor,
            @RequestParam double taxa,
            @RequestParam int meses) {

        double resultado = valor * Math.pow(1 + (taxa / 100), meses);
        return Math.round(resultado * 100.0) / 100.0;
    }

    @GetMapping("/simular-emprestimo")
    public double simularEmprestimo(
            @RequestParam double valor,
            @RequestParam double taxa,
            @RequestParam int meses) {

        double resultado = valor * Math.pow(1 + (taxa / 100), meses);
        return Math.round(resultado * 100.0) / 100.0;
    }

    // =========================
    // 💰 INVESTIMENTO REAL
    // =========================

    @PostMapping("/investir")
    public float investir(
            @RequestParam float valor,
            @RequestParam float taxa,
            @RequestParam int meses) {

        if (valor > saldo) {
            throw new RuntimeException("Saldo insuficiente");
        }

        saldo -= valor;

        double retorno = valor * Math.pow(1 + (taxa / 100), meses);
        retorno = Math.round(retorno * 100.0) / 100.0;

        saldo += retorno;

        historico.add("Investimento: + R$ " + String.format("%.2f", retorno));

        return saldo;
    }

    // =========================
    // 💳 EMPRÉSTIMO
    // =========================

    @PostMapping("/emprestimo")
    public float emprestimo(
            @RequestParam float valor,
            @RequestParam float taxa,
            @RequestParam int meses) {

        double total = valor * Math.pow(1 + (taxa / 100), meses);
        total = Math.round(total * 100.0) / 100.0;

        saldo += valor;
        divida += total;

        historico.add("Empréstimo recebido: + R$ " + String.format("%.2f", valor));
        historico.add("Dívida adicionada: - R$ " + String.format("%.2f", total));

        return saldo;
    }

    // =========================
    // 💸 DÍVIDA
    // =========================

    @GetMapping("/divida")
    public double getDivida() {
        return Math.round(divida * 100.0) / 100.0;
    }

    @PostMapping("/pagar-divida")
    public float pagarDivida(@RequestParam float valor) {

        if (valor > saldo) {
            throw new RuntimeException("Saldo insuficiente");
        }

        if (valor > divida) {
            valor = (float) divida;
        }

        saldo -= valor;
        divida -= valor;

        historico.add("Pagamento de dívida: - R$ " + String.format("%.2f", valor));

        return saldo;
    }
}