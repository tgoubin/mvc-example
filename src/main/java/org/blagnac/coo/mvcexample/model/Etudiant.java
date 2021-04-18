package org.blagnac.coo.mvcexample.model;

import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

/**
 * Classe metier de representation d'un Etudiant
 */
public class Etudiant implements Serializable {

	private static final long serialVersionUID = 417979284812134072L;

	/**
	 * Stockage des etudiants
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
	 */
	public Etudiant() {
		groupeTP = new GroupeTP();
	}
	
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
	 * Surcharge de la methode 'toString()' pour afficher correctement un etudiant
	 */
	@Override
	public String toString() {
		if (identifiant == null) {
			return "";
		}
		return nom + " " + prenom + " - " + groupeTP.toString();
	}

	/**
	 * Chargement de tous les etudiants
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

	/**
	 * Recuperation des etudiants depuis le modele
	 * 
	 * @return les etudiants
	 */
	public static List<Etudiant> getAll() {
		// On copie la liste d'etudiants pour pouvoir la trier, sans impacter les
		// donnees initiales (Etudiant.LISTE)
		List<Etudiant> etudiants = new ArrayList<>(LISTE);

		// Tri par nom, puis par prenom
		etudiants.sort(Comparator.comparing(Etudiant::getNom).thenComparing(Etudiant::getPrenom));

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
		// On recupere tous les etudiants deja tries
		List<Etudiant> etudiants = getAll();

		// Filtre par nom
		if (nom != null && !"".equals(nom.trim())) {
			etudiants = etudiants.stream().filter(e -> e.getNom().toLowerCase().contains(nom.trim().toLowerCase()))
					.collect(Collectors.toList());
		}

		// Filtre par prenom
		if (prenom != null && !"".equals(prenom.trim())) {
			etudiants = etudiants.stream()
					.filter(e -> e.getPrenom().toLowerCase().contains(prenom.trim().toLowerCase()))
					.collect(Collectors.toList());
		}

		// Filtre par prenom
		if (identifiantGroupeTP != null && !"".equals(identifiantGroupeTP.trim())) {
			etudiants = etudiants.stream().filter(e -> e.getGroupeTP().getIdentifiant().equals(identifiantGroupeTP))
					.collect(Collectors.toList());
		}

		return etudiants;
	}

	/**
	 * Creation d'un etudiant
	 * 
	 * @param nom      le nom
	 * @param prenom   le prenom
	 * @param groupeTP le groupe de TP
	 * @return l'etudiant ajoute
	 */
	public static Etudiant create(String nom, String prenom, GroupeTP groupeTP) {
		Etudiant etudiant = new Etudiant(String.valueOf(LISTE.size() + 1), nom, prenom, groupeTP);
		LISTE.add(etudiant);
		return etudiant;
	}

	/**
	 * Modification d'un etudiant
	 * 
	 * @param identifiant l'identifiant de l'etudiant a modifier
	 * @param nom         le nom
	 * @param prenom      le prenom
	 * @param groupeTP    le groupe de TP
	 * @return l'etudiant modifie
	 */
	public static Etudiant update(String identifiant, String nom, String prenom, GroupeTP groupeTP) {
		Etudiant etudiant = LISTE.stream().filter(e -> e.getIdentifiant().equals(identifiant)).findFirst().get();
		etudiant.setNom(nom);
		etudiant.setPrenom(prenom);
		etudiant.setGroupeTP(groupeTP);
		return etudiant;
	}

	/**
	 * Suppression d'un etudiant
	 * 
	 * @param identifiant
	 */
	public static void delete(String identifiant) {
		LISTE.removeIf(e -> e.getIdentifiant().equals(identifiant));
	}
}
