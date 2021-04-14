package org.blagnac.coo.mvcexample.ex1.controller;

import java.util.List;

import org.blagnac.coo.mvcexample.model.Etudiant;

/**
 * Controleur pour les etudiants (exemple 1)
 */
public class Exemple1EtudiantController {

	/**
	 * Recuperation des etudiants depuis le modele, eventuellement a partir de
	 * filtres de recherche
	 * 
	 * @param nom                 la valeur du filtre sur le nom
	 * @param prenom              la valeur du filtre sur le prenom
	 * @param identifiantGroupeTP la valeur du filtre sur le groupe de TP
	 * @return les etudiants correspondant eventuellement aux filtres de recherche
	 */
	public static List<Etudiant> get(String nom, String prenom, String identifiantGroupeTP) {
		List<Etudiant> etudiants = null;

		if (nom == null && prenom == null && identifiantGroupeTP == null) {
			// Aucun filtrage
			System.out.println("Recuperation de tous les etudiants");
			etudiants = Etudiant.getAll();
			System.out.println("\tFin de la recuperation de tous les etudiants : " + etudiants.size() + " etudiants");
		} else {
			// Recherche par criteres
			System.out.println("Recuperation des etudiants par nom='" + nom + "', prenom='" + prenom + "', groupeTP='"
					+ identifiantGroupeTP + "'");
			etudiants = Etudiant.getBy(nom, prenom, identifiantGroupeTP);
			System.out.println("\tFin de la recuperation de tous les etudiants : " + etudiants.size() + " etudiants");
		}

		return etudiants;
	}
}
