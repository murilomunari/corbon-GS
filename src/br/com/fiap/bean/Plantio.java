package br.com.fiap.bean;

import java.time.LocalDate;

public class Plantio {
    private int id;
    private String cultura;
    private double areaHectares;
    private LocalDate dataInicio;
    private Agricultor agricultor;

    public Plantio() {
    }

    public Plantio(int id, String cultura, double areaHectares, LocalDate dataInicio, Agricultor agricultor) {
        this.id = id;
        this.cultura = cultura;
        this.areaHectares = areaHectares;
        this.dataInicio = dataInicio;
        this.agricultor = agricultor;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCultura() {
        return this.cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public double getAreaHectares() {
        return this.areaHectares;
    }

    public void setAreaHectares(double areaHectares) {
        this.areaHectares = areaHectares;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Agricultor getAgricultor() {
        return this.agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public String getDetalhes() {
        return "Plantio " + this.id + " - " + this.cultura + " (" + this.areaHectares + " ha)";
    }
}
