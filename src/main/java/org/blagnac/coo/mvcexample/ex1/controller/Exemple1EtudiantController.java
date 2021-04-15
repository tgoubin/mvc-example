package org.blagnac.coo.mvcexample.ex1.controller;

import org.blagnac.coo.mvcexample.model.Etudiant;
import org.blagnac.coo.mvcexample.model.GroupeTP;

/**
 * Controleur pour les etudiants (exemple 1)
 */
public class Exemple1EtudiantController {

	/**
	 * Creation d'un etudiant
	 * 
	 * @param nom      le nom
	 * @param prenom   le prenom
	 * @param groupeTP le groupe de TP
	 * @return l'etudiant ajoute
	 * @throws Exception une exception fonctionnelle
	 */
	public static Etudiant createEtudiant(String nom, String prenom, GroupeTP groupeTP) throws Exception {
		System.out.println("Ajout de l'etudiant 'nom=" + nom + ", prenom=" + prenom + ", groupeTP=" + groupeTP + "'");

		// Le nom ne peut pas etre vide ou nul
		if (nom == null || "".equals(nom.trim())) {
			throw new Exception("Le nom de l'etudiant ne peut pas etre nul");
		}

		// Le prenom ne peut pas etre vide ou nul
		if (prenom == null || "".equals(prenom.trim())) {
			throw new Exception("Le prenom de l'etudiant ne peut pas etre nul");
		}

		// Le groupe de TP ne peut pas etre nul
		if (groupeTP == null) {
			throw new Exception("Le groupe de TP de l'etudiant ne peut pas etre nul");
		}

		return Etudiant.create(nom.trim(), prenom.trim(), groupeTP);
	}

	/**
	 * Modification d'un etudiant
	 * 
	 * @param identifiant l'identifiant de l'etudiant a modifier
	 * @param nom         le nom
	 * @param prenom      le prenom
	 * @param groupeTP    le groupe de TP
	 * @return l'etudiant ajoute
	 * @throws Exception une exception fonctionnelle
	 */
	public static Etudiant updateEtudiant(String identifiant, String nom, String prenom, GroupeTP groupeTP)
			throws Exception {
		System.out.println("Modification de l'etudiant 'identifiant=" + identifiant + "'");

		// La liste des etudiants doit contenir un etudiant ayant l'identifiant passe en
		// parametre
		if (!Etudiant.LISTE.stream().filter(e -> e.getIdentifiant().equals(identifiant)).findFirst().isPresent()) {
			throw new Exception(
					"La liste ne contient pas d'etudiant correspondant a l'identifiant '" + identifiant + "'");
		}

		// Le nom ne peut pas etre vide ou nul
		if (nom == null || "".equals(nom.trim())) {
			throw new Exception("Le nom de l'etudiant ne peut pas etre nul");
		}

		// Le prenom ne peut pas etre vide ou nul
		if (prenom == null || "".equals(prenom.trim())) {
			throw new Exception("Le prenom de l'etudiant ne peut pas etre nul");
		}

		// Le groupe de TP ne peut pas etre nul
		if (groupeTP == null) {
			throw new Exception("Le groupe de TP de l'etudiant ne peut pas etre nul");
		}

		return Etudiant.update(identifiant, nom.trim(), prenom.trim(), groupeTP);
	}

	/**
	 * Suppression d'un etudiant
	 * 
	 * @param identifiant l'identifiant de l'étudiant
	 * @throws Exception une exception fonctionnelle
	 */
	public static void deleteEtudiant(String identifiant) throws Exception {
		System.out.println("Suppression de l'etudiant 'identifiant=" + identifiant + "'");

		// L'identifiant ne peut pas etre nul
		if (identifiant == null) {
			throw new Exception("L'identifiant de l'etudiant ne peut pas etre nul");
		}

		// La liste des etudiants doit contenir un etudiant ayant l'identifiant passe en
		// parametre
		if (!Etudiant.LISTE.stream().filter(e -> e.getIdentifiant().equals(identifiant)).findFirst().isPresent()) {
			throw new Exception(
					"La liste ne contient pas d'etudiant correspondant a l'identifiant '" + identifiant + "'");
		}

		Etudiant.delete(identifiant);
	}
}
