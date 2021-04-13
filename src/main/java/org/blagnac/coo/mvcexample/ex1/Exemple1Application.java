package org.blagnac.coo.mvcexample.ex1;

import org.blagnac.coo.mvcexample.ex1.view.Fenetre;
import org.blagnac.coo.mvcexample.model.Etudiant;
import org.blagnac.coo.mvcexample.model.GroupeTD;
import org.blagnac.coo.mvcexample.model.GroupeTP;

/**
 * Classe principale de l'application pour l'exemple 1
 */
public class Exemple1Application {

	public static void main(String[] args) {
		System.out.println("Lancement de l'application pour l'exemple 1");

		// Chargement des donnees
		GroupeTD.loadGroupesTD();
		GroupeTP.loadGroupesTP();
		Etudiant.loadEtudiants();

		// Lancement de l'IHM
		new Fenetre();
	}
}
