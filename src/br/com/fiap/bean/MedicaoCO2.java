package br.com.fiap.bean;

import java.time.LocalDate;

public class MedicaoCO2 implements Monitoravel {
    private int id;
    private double concentracaoPpm;
    private String nivelRisco;
    private LocalDate dataMedicao;
    private Cidade cidade;

    public MedicaoCO2() {
    }

    public MedicaoCO2(int id, double concentracaoPpm, String nivelRisco, LocalDate dataMedicao, Cidade cidade) {
        this.id = id;
        this.concentracaoPpm = concentracaoPpm;
        this.nivelRisco = nivelRisco;
        this.dataMedicao = dataMedicao;
        this.cidade = cidade;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getConcentracaoPpm() {
        return this.concentracaoPpm;
    }

    public void setConcentracaoPpm(double concentracaoPpm) {
        this.concentracaoPpm = concentracaoPpm;
    }

    public String getNivelRisco() {
        return this.nivelRisco;
    }

    public void setNivelRisco(String nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public LocalDate getDataMedicao() {
        return this.dataMedicao;
    }

    public void setDataMedicao(LocalDate dataMedicao) {
        this.dataMedicao = dataMedicao;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public void registrarMedicao(double ppm) {
        this.concentracaoPpm = ppm;
        this.dataMedicao = LocalDate.now();
        this.nivelRisco = classificarRisco();
    }

    @Override
    public String classificarRisco() {
        if (this.concentracaoPpm < 400) {
            return "baixo";
        } else if (this.concentracaoPpm < 1000) {
            return "moderado";
        } else if (this.concentracaoPpm < 2000) {
            return "alto";
        } else {
            return "critico";
        }
    }

    public boolean gerarAlerta() {
        if (this.concentracaoPpm >= 1000) {
            return true;
        }
        return false;
    }
}
