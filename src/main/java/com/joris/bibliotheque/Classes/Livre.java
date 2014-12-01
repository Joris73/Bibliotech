package com.joris.bibliotheque.Classes;

import java.util.ArrayList;

/**
 * Created by Joris on 18/11/2014.
 */
public class Livre {
    private String idOuvrage;
    private int ISBN;
    private String titre;
    private String auteur;
    private String editeur;
    private int annee;
    private String imageName;
    private Usager empruntePar;

    public Livre(String idOuvrage, int ISBN, String titre, String auteur, String editeur, int annee, String imageName, Usager empruntePar) {
        this.idOuvrage = idOuvrage;
        this.ISBN = ISBN;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.annee = annee;
        this.imageName = imageName;
        this.empruntePar = empruntePar;
    }

    public String getIdOuvrage() {
        return idOuvrage;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getEditeur() {
        return editeur;
    }

    public int getAnnee() {
        return annee;
    }

    public Usager getEmpruntePar() {
        return empruntePar;
    }

    public String getImageName() {
        return imageName;
    }

    public void setEmpruntePar(Usager empruntePar) {
        this.empruntePar = empruntePar;
    }

    public boolean isEmprunte() {
        return this.empruntePar != null;
    }

    static public Livre GetLivreList(ArrayList<Livre> livres, String id) {
        for (Livre l : livres) {
            if (l.getIdOuvrage().equals(id)) {
                return l;
            }
        }
        return null;
    }
}
