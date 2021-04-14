package org.blagnac.coo.mvcexample.controller;

import org.blagnac.coo.mvcexample.model.Etudiant;
import org.blagnac.coo.mvcexample.model.GroupeTD;
import org.blagnac.coo.mvcexample.model.GroupeTP;

/**
 * Controleur commun aux deux exemples
 */
public class MainController {

	/**
	 * Chargement de toutes les donnees
	 */
	public static void loadData() {
		GroupeTD.loadGroupesTD();
		GroupeTP.loadGroupesTP();
		Etudiant.loadEtudiants();
	}
}
