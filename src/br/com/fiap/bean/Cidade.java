package br.com.fiap.bean;

public class Cidade {
    private int id;
    private String nome;
    private double latitude;
    private double longitude;
    private Pais pais;

    public Cidade() {
    }

    public Cidade(int id, String nome, double latitude, double longitude, Pais pais) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pais = pais;
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

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Pais getPais() {
        return this.pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getLocalizacao() {
        return this.latitude + ", " + this.longitude;
    }

    public boolean validarCoordenadas() {
        if (this.latitude < -90 || this.latitude > 90) {
            return false;
        }
        if (this.longitude < -180 || this.longitude > 180) {
            return false;
        }
        return true;
    }
}
