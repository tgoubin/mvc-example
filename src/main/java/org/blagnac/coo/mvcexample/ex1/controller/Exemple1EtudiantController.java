package org.blagnac.coo.mvcexample.ex1.controller;

import java.util.List;

import org.blagnac.coo.mvcexample.model.Etudiant;

/**
 * Controleur pour les etudiants (exemple 1)
 */
public class Exemple1EtudiantController {

	/**
	 * Recuperation des etudiants depuis le modele
	 * 
	 * @return les etudiants
	 */
	public static List<Etudiant> getAll() {
		System.out.println("Recuperation de tous les etudiants");
		List<Etudiant> etudiants = Etudiant.getAll();
		System.out.println("\tFin de la recuperation de tous les etudiants : " + etudiants.size() + " etudiants");
		return etudiants;
	}

	/**
	 * Recuperation des etudiants depuis le modele, et selon des filtres de
	 * recherche
	 * 
	 * @param nom                 la valeur du filtre sur le nom
	 * @param prenom              la valeur du filtre sur lele prenom
	 * @param identifiantGroupeTP la valeur du filtre sur le groupe de TP
	 * @return les etudiants correspondant aux filtres de recherche
	 */
	public static List<Etudiant> getBy(String nom, String prenom, String identifiantGroupeTP) {
		System.out.println("Recuperation des etudiants par nom='" + nom + "', prenom='" + prenom + "', groupeTP='"
				+ identifiantGroupeTP + "'");
		List<Etudiant> etudiants = Etudiant.getBy(nom, prenom, identifiantGroupeTP);
		System.out.println("\tFin de la recuperation de tous les etudiants : " + etudiants.size() + " etudiants");
		return etudiants;
	}
}
