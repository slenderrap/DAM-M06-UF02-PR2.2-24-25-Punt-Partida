package com.project;

import java.util.HashSet;
import java.util.Set;

public class Ciutat {
    private Long ciutatId;
    private String nom;
    private String pais;
    private int poblacio;
    private Set<Ciutada> ciutadants = new HashSet<>();

    public Ciutat(String nom, String pais, int poblacio) {
        this.nom = nom;
        this.pais = pais;
        this.poblacio = poblacio;
    }

    public Long getCiutatId() {
        return ciutatId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(int poblacio) {
        this.poblacio = poblacio;
    }

    public Set<Ciutada> getCiutadants() {
        return ciutadants;
    }

    public void setCiutadants(Set<Ciutada> ciutadants) {
        if (ciutadants != null) {
            ciutadants.forEach(this::addCiutada);
        }
    }


    public void addCiutada(Ciutada ciutada) {
        ciutadants.add(ciutada);
        ciutada.setCiutatId(this.ciutatId);
    }

    public void removeCiutada(Ciutada ciutada) {
        ciutadants.remove(ciutada);
        ciutada.setCiutatId(null);
    }
}