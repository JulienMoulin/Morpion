package model;

/**
 * Created by stagiaire on 13/02/2016.
 */

public class Case {

    // 0 : null, 1 : humain, -1 : IA
    private int joueur;

    public Case(int joueur){
        this.joueur = joueur;
    }

    public int getJoueur() {
        return joueur;
    }

    public void setJoueur(int joueur) {
        this.joueur = joueur;
    }

    public boolean estLibre() {
        return joueur == 0;
    }

    public void jouer(int joueur) {
        this.joueur = joueur;
    }

    public void coupProvisoir(int joueur) {
        this.joueur = joueur;
    }

    public void annulerCoup() {
        joueur = 0;
    }

}