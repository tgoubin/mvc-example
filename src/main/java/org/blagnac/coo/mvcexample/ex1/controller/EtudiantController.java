package org.blagnac.coo.mvcexample.ex1.controller;

import java.util.List;

import org.blagnac.coo.mvcexample.model.Etudiant;

/**
 * Controleur pour les etudiants
 */
public class EtudiantController {

	/**
	 * Recuperation des etudiants depuis le modele
	 * 
	 * @return les etudiants
	 */
	public static List<Etudiant> getAll() {
		return Etudiant.LISTE;
	}

}
