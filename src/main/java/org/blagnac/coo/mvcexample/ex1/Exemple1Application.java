package org.blagnac.coo.mvcexample.ex1;

import org.blagnac.coo.mvcexample.controller.MainController;
import org.blagnac.coo.mvcexample.ex1.view.Fenetre;

/**
 * Classe principale de l'application pour l'exemple 1
 */
public class Exemple1Application {

	public static void main(String[] args) {
		System.out.println("Lancement de l'application pour l'exemple 1");

		// Chargement des donnees
		MainController.loadData();

		// Lancement de l'IHM
		new Fenetre();
	}
}
