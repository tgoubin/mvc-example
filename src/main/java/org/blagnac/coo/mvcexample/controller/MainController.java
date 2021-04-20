package org.blagnac.coo.mvcexample.controller;

import org.blagnac.coo.mvcexample.model.entity.Etudiant;
import org.blagnac.coo.mvcexample.model.entity.GroupeTD;
import org.blagnac.coo.mvcexample.model.entity.GroupeTP;

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
