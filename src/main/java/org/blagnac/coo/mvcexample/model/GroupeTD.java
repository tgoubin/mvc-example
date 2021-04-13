package org.blagnac.coo.mvcexample.model;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * Classe metier de representation d'un Groupe de TD
 */
public class GroupeTD {

	/**
	 * Stockage des groupes de TD
	 */
	public static List<GroupeTD> LISTE = new ArrayList<>();

	/**
	 * Chemin vers le fichier CSV des groupes de TD
	 */
	private static final String GROUPES_TD_FILE = "/csv/groupesTD.csv";

	/**
	 * Identifiant
	 */
	private String identifiant;

	/**
	 * '1' pour premiere annee - '2' pour seconde annee, etc...
	 */
	private int annee;

	/**
	 * Numero du groupe
	 */
	private int numero;

	/**
	 * Constructeur
	 * 
	 * @param identifiant l'identifiant
	 * @param annee       l'annee
	 * @param numero      le numero
	 */
	public GroupeTD(String identifiant, int annee, int numero) {
		this.identifiant = identifiant;
		this.annee = annee;
		this.numero = numero;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Surcharge de la methode 'toString()' pour afficher correctement le libelle
	 * d'un groupe de TD : '1e annee - Groupe 3' par exemple
	 */
	@Override
	public String toString() {
		return String.valueOf(annee) + String.valueOf(numero);
	}

	/**
	 * Recuperation de tous les groupes de TD
	 */
	@SuppressWarnings("deprecation")
	public static void loadGroupesTD() {
		System.out.println("Chargement des groupes de TD");

		try {
			// Je declare un lecteur de fichier CSV, utilisant le separateur "tabulation"
			try (CSVReader csvReader = new CSVReader(
					new InputStreamReader(GroupeTD.class.getResourceAsStream(GROUPES_TD_FILE)), '\t')) {
				int ligne = 1;
				String[] ligneGroupeTD = null;
				while ((ligneGroupeTD = csvReader.readNext()) != null) {
					// On ne prend pas en compte la premiere ligne (en-tete)
					if (ligne > 1) {
						LISTE.add(new GroupeTD(ligneGroupeTD[0], Integer.parseInt(ligneGroupeTD[1]),
								Integer.parseInt(ligneGroupeTD[2])));
					}
					ligne++;
				}
			}

			System.out.println("\tFin du chargement des groupes de TD : " + LISTE.size() + " groupes");
		} catch (Exception e) {
			System.err.println("Erreur durant la recuperation des groupes de TD");
			e.printStackTrace();
		}
	}
}
