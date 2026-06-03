package br.com.fiap.bean;

import java.time.LocalDate;

public class Agricultor {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataCadastro;
    private Cidade cidade;

    public Agricultor() {
    }

    public Agricultor(int id, String nome, String email, String telefone, LocalDate dataCadastro, Cidade cidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.cidade = cidade;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public void adicionarPlantio(Plantio p) {
        if (p != null) {
            p.setAgricultor(this);
        }
    }

    public void receberAlerta(Alerta a) {
        if (a != null) {
            System.out.println("Agricultor " + this.nome + " recebeu alerta: " + a.getMensagem());
        }
    }
}
