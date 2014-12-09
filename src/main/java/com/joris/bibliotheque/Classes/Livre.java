package com.joris.bibliotheque.Classes;

import java.util.ArrayList;

/**
 * Created by Joris on 18/11/2014.
 */
public class Livre {
    private int idLivre;
    private long ISBN;
    private String titre;
    private String auteur;
    private String editeur;
    private int annee;
    private String description;
    private String imageName;
    private Usager empruntePar;

    public Livre(int idLivre, long ISBN, String titre, String auteur, String editeur,
                 int annee, String description, String imageName, Usager empruntePar) {
        this.idLivre = idLivre;
        this.ISBN = ISBN;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.annee = annee;
        this.description = description;
        this.imageName = imageName;
        this.empruntePar = empruntePar;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public long getISBN() {
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

    public String getDescription() {
        return description;
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

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    static public Livre GetLivreList(ArrayList<Livre> livres, int id) {
        for (Livre l : livres) {
            if (l.getIdLivre() == id) {
                return l;
            }
        }
        return null;
    }
}
