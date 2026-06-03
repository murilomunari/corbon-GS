package br.com.fiap.bean;

public class Pais {
    private int id;
    private String nome;
    private String continente;

    public Pais() {
    }

    public Pais(int id, String nome, String continente) {
        this.id = id;
        this.nome = nome;
        this.continente = continente;
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

    public String getContinente() {
        return this.continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }
}
