package org.blagnac.coo.mvcexample.model;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * Classe metier de representation d'un Groupe de TP
 */
public class GroupeTP {

	/**
	 * Stockage des groupes de TP
	 */
	public static List<GroupeTP> LISTE = new ArrayList<>();

	/**
	 * Chemin vers le fichier CSV des groupes de TP
	 */
	private static final String GROUPES_TP_FILE = "/csv/groupesTP.csv";

	/**
	 * Identifiant
	 */
	private String identifiant;

	/**
	 * Groupe de TD associe
	 */
	private GroupeTD groupeTD;

	/**
	 * 'A' pour groupe A, 'B' pour groupe B, etc...
	 */
	private char groupe;

	/**
	 * Constructeur
	 * 
	 * @param identifiant l'identifiant
	 * @param groupeTD    le groupe de TD
	 * @param numero      le numero
	 */
	public GroupeTP(String identifiant, GroupeTD groupeTD, char groupe) {
		this.identifiant = identifiant;
		this.groupeTD = groupeTD;
		this.groupe = groupe;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public GroupeTD getGroupeTD() {
		return groupeTD;
	}

	public void setGroupeTD(GroupeTD groupeTD) {
		this.groupeTD = groupeTD;
	}

	public char getGroupe() {
		return groupe;
	}

	public void setGroupe(char groupe) {
		this.groupe = groupe;
	}

	/**
	 * Surcharge de la methode 'toString()' pour afficher correctement le libelle
	 * d'un groupe de TP : '1e annee - Groupe 3B' par exemple
	 */
	@Override
	public String toString() {
		return groupeTD.toString() + groupe;
	}

	/**
	 * Recuperation de tous les groupes de TP
	 */
	@SuppressWarnings("deprecation")
	public static void loadGroupesTP() {
		System.out.println("Chargement des groupes de TP");

		try {
			// Je declare un lecteur de fichier CSV, utilisant le separateur "tabulation"
			try (CSVReader csvReader = new CSVReader(
					new InputStreamReader(GroupeTP.class.getResourceAsStream(GROUPES_TP_FILE)), '\t')) {
				int ligne = 1;
				String[] ligneGroupeTP = null;
				while ((ligneGroupeTP = csvReader.readNext()) != null) {
					// On ne prend pas en compte la premiere ligne (en-tete)
					if (ligne > 1) {
						String idGroupeTD = ligneGroupeTP[1];
						GroupeTD groupeTD = GroupeTD.LISTE.stream().filter(g -> idGroupeTD.equals(g.getIdentifiant()))
								.findFirst().get();
						LISTE.add(new GroupeTP(ligneGroupeTP[0], groupeTD, ligneGroupeTP[2].charAt(0)));
					}
					ligne++;
				}
			}

			System.out.println("\tFin du chargement des groupes de TP : " + LISTE.size() + " groupes");
		} catch (Exception e) {
			System.err.println("Erreur durant la recuperation des groupes de TP");
			e.printStackTrace();
		}
	}
}
