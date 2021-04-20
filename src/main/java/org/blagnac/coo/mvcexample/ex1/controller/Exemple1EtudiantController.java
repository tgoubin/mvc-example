package org.blagnac.coo.mvcexample.ex1.controller;

import org.blagnac.coo.mvcexample.model.entity.Etudiant;
import org.blagnac.coo.mvcexample.model.entity.GroupeTP;

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

		// Verification des parametres
		checkCreateOrUpdateFields(nom, prenom, groupeTP);

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

		// Verification de l'identifiant
		checkIdentifiant(identifiant);

		// Verification des autres parametres
		checkCreateOrUpdateFields(nom, prenom, groupeTP);

		return Etudiant.update(identifiant, nom.trim(), prenom.trim(), groupeTP);
	}

	/**
	 * Verification des champs pour une creation ou une modification
	 * 
	 * @param nom      le nom
	 * @param prenom   le prenom
	 * @param groupeTP le groupe de TP
	 * @throws Exception une exception fonctionnelle
	 */
	private static void checkCreateOrUpdateFields(String nom, String prenom, GroupeTP groupeTP) throws Exception {
		// Le nom ne peut pas etre vide ou nul
		if (nom == null || "".equals(nom.trim())) {
			String erreur = "Le nom de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		// Le prenom ne peut pas etre vide ou nul
		if (prenom == null || "".equals(prenom.trim())) {
			String erreur = "Le prenom de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		// Le groupe de TP ne peut pas etre nul
		if (groupeTP == null) {
			String erreur = "Le groupe de TP de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}
	}

	/**
	 * Suppression d'un etudiant
	 * 
	 * @param identifiant l'identifiant de l'étudiant
	 * @throws Exception une exception fonctionnelle
	 */
	public static void deleteEtudiant(String identifiant) throws Exception {
		System.out.println("Suppression de l'etudiant 'identifiant=" + identifiant + "'");

		// Verification de l'identifiant
		checkIdentifiant(identifiant);

		Etudiant.delete(identifiant);
	}

	/**
	 * Verification de l'identifiant passe en parametre
	 * 
	 * @param identifiant l'identifiant
	 * @throws Exception
	 */
	private static void checkIdentifiant(String identifiant) throws Exception {
		// L'identifiant ne peut pas etre nul
		if (identifiant == null) {
			String erreur = "L'identifiant de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}
	}
}
