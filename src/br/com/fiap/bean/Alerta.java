package br.com.fiap.bean;

import java.time.LocalDate;

public class Alerta {
    private int id;
    private LocalDate dataAlerta;
    private String mensagem;
    private boolean lido;
    private Agricultor agricultor;
    private MedicaoCO2 medicao;

    public Alerta() {
    }

    public Alerta(int id, LocalDate dataAlerta, String mensagem, Agricultor agricultor, MedicaoCO2 medicao) {
        this.id = id;
        this.dataAlerta = dataAlerta;
        this.mensagem = mensagem;
        this.lido = false;
        this.agricultor = agricultor;
        this.medicao = medicao;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataAlerta() {
        return this.dataAlerta;
    }

    public void setDataAlerta(LocalDate dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLido() {
        return this.lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    public Agricultor getAgricultor() {
        return this.agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public MedicaoCO2 getMedicao() {
        return this.medicao;
    }

    public void setMedicao(MedicaoCO2 medicao) {
        this.medicao = medicao;
    }

    public void marcarComoLido() {
        this.lido = true;
    }

    public void enviarNotificacao() {
        if (this.agricultor != null) {
            this.agricultor.receberAlerta(this);
        }
    }
}
