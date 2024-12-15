package com.project;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "ciutat")
public class Ciutat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ciutatId")
    private Long ciutatId;

    private String nom;
    private String pais;
    private int poblacio;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Ciutada> ciutadans = new HashSet<>();

    public Ciutat() {
    }

    public Ciutat(String nom, String pais, int poblacio) {
        this.nom = nom;
        this.pais = pais;
        this.poblacio = poblacio;
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

    public Set<Ciutada> getCiutadans() {
        return ciutadans;
    }

    public void setCiutadans(Set<Ciutada> ciutadans) {
        if (ciutadans != null) {
            ciutadans.forEach(this::addCiutada);
        }
    }


    public void addCiutada(Ciutada ciutada) {
        ciutadans.add(ciutada);
        ciutada.setCiutatId(this.ciutatId);
    }

    public void removeCiutada(Ciutada ciutada) {
        ciutadans.remove(ciutada);
        ciutada.setCiutatId(null);
    }

    @Override
    public String toString() {
        return "Ciutat{" +
                "ciutatId=" + ciutatId +
                ", nom='" + nom + '\'' +
                ", pais='" + pais + '\'' +
                ", poblacio=" + poblacio +
                ", ciutadans=" + ciutadans +
                '}';
    }
}