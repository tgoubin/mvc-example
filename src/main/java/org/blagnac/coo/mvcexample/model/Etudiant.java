package org.blagnac.coo.mvcexample.model;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * Classe metier de representation d'un Etudiant
 */
public class Etudiant {

	/**
	 * Stockage des groupes de TP
	 */
	public static List<Etudiant> LISTE = new ArrayList<>();

	/**
	 * Chemin vers le fichier CSV des groupes de TP
	 */
	private static final String ETUDIANTS_FILE = "/csv/etudiants.csv";

	/**
	 * Identifiant
	 */
	private String identifiant;

	/**
	 * Nom
	 */
	private String nom;

	/**
	 * Prenom
	 */
	private String prenom;

	/**
	 * Groupe de TP
	 */
	private GroupeTP groupeTP;

	/**
	 * Constructeur
	 * 
	 * @param identifiant l'identifiant
	 * @param nom         le nom
	 * @param prenom      le prenom
	 * @param groupeTP    le groupe de TP
	 */
	public Etudiant(String identifiant, String nom, String prenom, GroupeTP groupeTP) {
		this.identifiant = identifiant;
		this.nom = nom;
		this.prenom = prenom;
		this.groupeTP = groupeTP;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public GroupeTP getGroupeTP() {
		return groupeTP;
	}

	public void setGroupeTP(GroupeTP groupeTP) {
		this.groupeTP = groupeTP;
	}

	/**
	 * Recuperation de tous les etudiants
	 */
	@SuppressWarnings("deprecation")
	public static void loadEtudiants() {
		System.out.println("Chargement des etudiants");

		try {
			// Je declare un lecteur de fichier CSV, utilisant le separateur "tabulation"
			try (CSVReader csvReader = new CSVReader(
					new InputStreamReader(Etudiant.class.getResourceAsStream(ETUDIANTS_FILE)), '\t')) {
				int ligne = 1;
				String[] ligneEtudiant = null;
				while ((ligneEtudiant = csvReader.readNext()) != null) {
					// On ne prend pas en compte la premiere ligne (en-tete)
					if (ligne > 1) {
						String idGroupeTP = ligneEtudiant[3];
						GroupeTP groupeTP = GroupeTP.LISTE.stream().filter(g -> idGroupeTP.equals(g.getIdentifiant()))
								.findFirst().get();
						LISTE.add(new Etudiant(ligneEtudiant[0], ligneEtudiant[1], ligneEtudiant[2], groupeTP));
					}
					ligne++;
				}
			}

			System.out.println("\tFin du chargement des etudiants : " + LISTE.size() + " etudiants");
		} catch (Exception e) {
			System.err.println("Erreur durant la recuperation des etudiants");
			e.printStackTrace();
		}
	}
}
