package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Julien Moulin on 13/02/2016.
 */

public class Morpion {

    private static final Random RANDOM = new Random();
    private Case[][] cases;
    private int nombreCasesJouees;
    private int getNombreCasesTotales = 9;

    private static int IA_value = -1;
    private static int Humain_value = 1;

    public Morpion(Case[][] cases, int nombreCasesJouees) {
        this.cases = cases;
        this.nombreCasesJouees = nombreCasesJouees;
    }

    public String toString(){
        return "{grille: ["+
                cases[0][0].getJoueur()+","+cases[0][1].getJoueur()+","+cases[0][2].getJoueur()+"," +
                cases[1][0].getJoueur()+","+cases[1][1].getJoueur()+","+cases[1][2].getJoueur()+"," +
                cases[2][0].getJoueur()+","+cases[2][1].getJoueur()+","+cases[2][2].getJoueur()+
        "], finie: "+partieFinie()+", vainqueur: "+getVainqueur()+"}";
    }

    public boolean partieFinie() {
        return getVainqueur() != 0 || nombreCasesJouees == getNombreCasesTotales;
    }

    public void IA() {
        Case meilleureCase = getMeilleureCase();
        meilleureCase.jouer(IA_value);
        nombreCasesJouees++;
    }

    private int getAdversaire(int joueur) {
        return (joueur == IA_value)? Humain_value : IA_value;
}


    private Case getMeilleureCase() {

        Case caseTestee;
        List<Case> meilleuresCases = new LinkedList<Case>();

        int evaluationCaseTestee;
        int evaluationMeilleuresCases = Integer.MIN_VALUE;

		for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                caseTestee = cases[x][y];
                if (caseTestee.estLibre()) {
                    caseTestee.coupProvisoir(IA_value);
                    nombreCasesJouees++;
                    evaluationCaseTestee = negamax(IA_value);

                    if (evaluationCaseTestee > evaluationMeilleuresCases) {
                        meilleuresCases.clear();
                        meilleuresCases.add(caseTestee);
                        evaluationMeilleuresCases = evaluationCaseTestee;
                    } else if (evaluationCaseTestee == evaluationMeilleuresCases) {
                        meilleuresCases.add(caseTestee);
                    }
                    caseTestee.annulerCoup();
                    nombreCasesJouees--;
                }
            }
        }
        return meilleuresCases.get(RANDOM.nextInt(meilleuresCases.size()));
    }

    private int negamax(int joueur) {

        int negamax;

        if (partieFinie()) {
	        negamax = getVainqueur();
        } else {
            int adversaire = getAdversaire(joueur);
            Case caseTestee;

            int negamaxCase;
            int negamaxAdversaire = Integer.MIN_VALUE;

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    caseTestee = cases[x][y];
                    if (caseTestee.estLibre()) {

                        caseTestee.coupProvisoir(adversaire);
                        nombreCasesJouees++;
                        negamaxCase = negamax(adversaire);

                        caseTestee.annulerCoup();
                        nombreCasesJouees--;

                        if (negamaxCase > negamaxAdversaire) {
                            negamaxAdversaire = negamaxCase;
                        }
                    }
                }
            }
            negamax = -negamaxAdversaire;
        }
        return negamax;
    }

    private int getVainqueur() {

        int joueur;

		// Test des 3 colonnes
        for (int x = 0; x < 3; x++) {
			joueur = cases[x][0].getJoueur();
            if (joueur != 0 && cases[x][1].getJoueur() == joueur && cases[x][2].getJoueur() == joueur) {
				return joueur;
            }
        }

        // Test des 3 lignes
        for (int y = 0; y < 3; y++) {
    		joueur = cases[0][y].getJoueur();
            if (joueur != 0 && cases[1][y].getJoueur() == joueur && cases[2][y].getJoueur() == joueur) {
                return joueur;
            }
        }

        // Test des 2 diagonale
        joueur = cases[0][0].getJoueur();
        if (joueur != 0 && cases[1][1].getJoueur() == joueur && cases[2][2].getJoueur() == joueur) {
            return joueur;
        }

        joueur = cases[0][2].getJoueur();
        if (joueur != 0 && cases[1][1].getJoueur() == joueur && cases[2][0].getJoueur() == joueur) {
            return joueur;
        }

        // Sinon null
		return 0;

    }


}
