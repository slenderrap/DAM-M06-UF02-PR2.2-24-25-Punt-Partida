package com.project;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Ciutada {

    private Long ciutatId;
    private String nom;
    private String cognom;
    private int edat;
    private  Long ciutadaId;


    public Ciutada() {
    }

    public Ciutada(String nom, String cognom, int edat) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
    }

    public void setCiutadaId(Long ciutadaId) {
        this.ciutadaId = ciutadaId;
    }

    public Long getCiutadaId() {
        return ciutadaId;
    }

    public Long getCiutatId() {
        return ciutatId;
    }

    public void setCiutatId(Long ciutatId) {
        this.ciutatId = ciutatId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    @Override
    public String toString() {
        return "Ciutada{" +
                "ciutadaId=" + ciutadaId +
                ", ciutatId=" + ciutatId +
                ", nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", edat=" + edat +
                '}';
    }
}