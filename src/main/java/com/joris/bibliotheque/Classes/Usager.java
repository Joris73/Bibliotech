package com.joris.bibliotheque.Classes;

import java.util.ArrayList;

/**
 * Created by Joris on 18/11/2014.
 */
public class Usager {
    private int idUsager;
    private String loginUsager;
    private String nomUsager;
    private String prenomUsager;
    private ArrayList<Livre> listeEmprunt;


    public Usager(int idUsager, String loginUsager, String nomUsager, String prenomUsager) {
        this.idUsager = idUsager;
        this.loginUsager = loginUsager;
        this.nomUsager = nomUsager;
        this.prenomUsager = prenomUsager;
        this.listeEmprunt = new ArrayList<>();
    }

    public Usager(int idUsager, String loginUsager, String nomUsager, String prenomUsager, ArrayList<Livre> listeEmprunt) {
        this.idUsager = idUsager;
        this.loginUsager = loginUsager;
        this.nomUsager = nomUsager;
        this.prenomUsager = prenomUsager;
        this.listeEmprunt = listeEmprunt;
        this.listeEmprunt = new ArrayList<>();
    }

    public int getIdUsager() {
        return idUsager;
    }

    public String getLoginUsager() {
        return loginUsager;
    }

    public String getNomUsager() {
        return nomUsager;
    }

    public String getPrenomUsager() {
        return prenomUsager;
    }

    public ArrayList<Livre> getListeEmprunt() {
        return listeEmprunt;
    }

    public void setListeEmprunt(ArrayList<Livre> listeEmprunt) {
        this.listeEmprunt = listeEmprunt;
    }

    public void addEmprunt(Livre livre) {
        if (this.listeEmprunt == null)
            this.listeEmprunt = new ArrayList<>();
        this.listeEmprunt.add(livre);
        livre.setIdEmpruntePar(this.idUsager);
    }

    public void rendre(Livre livre) {
        if (this.listeEmprunt != null) {
            this.listeEmprunt.remove(livre);
            livre.setIdEmpruntePar(null);
        }
    }
}
