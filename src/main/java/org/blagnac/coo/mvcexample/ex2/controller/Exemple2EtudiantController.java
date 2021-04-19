package org.blagnac.coo.mvcexample.ex2.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.blagnac.coo.mvcexample.model.Etudiant;
import org.blagnac.coo.mvcexample.model.GroupeTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controleur pour les etudiants (exemple 2)
 */
@Controller
// Ce controleur est accessible depuis l'URL "http://localhost:8080/<server.servlet.context-path declare dans application.properties>/etudiant"
@RequestMapping(value = "/etudiant")
public class Exemple2EtudiantController {

	/**
	 * Recuperation des etudiants depuis le modele, eventuellement a partir de
	 * filtres de recherche
	 * 
	 * URL complete : GET /etudiant (RequestMapping de la classe + RequestMapping de
	 * la methode)
	 * 
	 * @param nom                 la valeur du filtre sur le nom - transmis par URL
	 * @param prenom              la valeur du filtre sur le prenom - transmis par
	 *                            URL
	 * @param identifiantGroupeTP la valeur du filtre sur le groupe de TP - transmis
	 *                            par URL
	 * @return la reponse du controleur - les etudiants correspondant eventuellement
	 *         aux filtres de recherche
	 */
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Etudiant>> get(@RequestParam(value = "nom", required = false) String nom,
			@RequestParam(value = "prenom", required = false) String prenom,
			@RequestParam(value = "groupetp", required = false) String identifiantGroupeTP) {
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

		return new ResponseEntity<List<Etudiant>>(etudiants, HttpStatus.OK);
	}

	/**
	 * Recuperation d'un etudiant a partir de son identifiant
	 * 
	 * URL complete : GET /etudiant/{identifiant} (RequestMapping de la classe +
	 * RequestMapping de la methode)
	 * 
	 * @param identifiant l'identifiant de l'étudiant
	 * @return la reponse du controleur - soit l'etudiant, soit un message d'erreur
	 */
	@RequestMapping(value = "/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<?> getByIdentifiant(@PathVariable(value = "identifiant") String identifiant) {
		System.out.println("Recuperation de l'etudiant 'identifiant=" + identifiant + "'");

		Etudiant etudiant = null;

		// Verification de l'identifiant
		try {
			etudiant = checkAndGetByIdentifiant(identifiant);
		} catch (Exception e) {
			// On renvoie une erreur avec le code 404 / NOT FOUND (code HTTP pour designer
			// une ressource introuvable)
			return new ResponseEntity<Erreur>(new Erreur(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Etudiant>(etudiant, HttpStatus.CREATED);
	}

	/**
	 * Creation d'un etudiant
	 * 
	 * URL complete : POST /etudiant (RequestMapping de la classe + RequestMapping
	 * de la methode)
	 * 
	 * @param etudiant l'etudiant
	 * @return la reponse du controleur - soit l'etudiant, soit un message d'erreur
	 */
	@RequestMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Etudiant etudiant) {
		System.out.println("Ajout de l'etudiant 'nom=" + etudiant.getNom() + ", prenom=" + etudiant.getPrenom()
				+ ", groupeTP=" + etudiant.getGroupeTP().getIdentifiant() + "'");

		// On isole le groupe de TP de l'etudiant de la liste des groupes de TP pour
		// l'utiliser dans la verification
		Optional<GroupeTP> groupeTP = GroupeTP.LISTE.stream()
				.filter(g -> g.getIdentifiant().equals(etudiant.getGroupeTP().getIdentifiant())).findFirst();

		// Verification des parametres
		try {
			checkCreateOrUpdateFields(etudiant, groupeTP);
		} catch (Exception e) {
			// On renvoie une erreur avec le code 400 / Bad Request (code HTTP pour designer
			// une mauvaise requete)
			return new ResponseEntity<Erreur>(new Erreur(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Etudiant>(
				Etudiant.create(etudiant.getNom().trim(), etudiant.getPrenom().trim(), groupeTP.get()),
				HttpStatus.CREATED);
	}

	/**
	 * Modification d'un etudiant
	 * 
	 * URL complete : PUT /etudiant/{identifiant} (RequestMapping de la classe +
	 * RequestMapping de la methode)
	 * 
	 * @param identifiant l'identifiant de l'étudiant
	 * @param etudiant    l'etudiant
	 * @return la reponse du controleur - soit l'etudiant, soit un message d'erreur
	 */
	@RequestMapping(value = "/{identifiant}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable(value = "identifiant") String identifiant,
			@RequestBody Etudiant etudiant) {
		System.out.println("Modification de l'etudiant 'identifiant=" + identifiant + "'");

		// On isole le groupe de TP de l'etudiant de la liste des groupes de TP pour
		// l'utiliser dans la verification
		Optional<GroupeTP> groupeTP = GroupeTP.LISTE.stream()
				.filter(g -> g.getIdentifiant().equals(etudiant.getGroupeTP().getIdentifiant())).findFirst();

		// Verification de l'identifiant
		try {
			checkAndGetByIdentifiant(identifiant);
		} catch (Exception e) {
			// On renvoie une erreur avec le code 404 / NOT FOUND (code HTTP pour designer
			// une ressource introuvable)
			return new ResponseEntity<Erreur>(new Erreur(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		// Verification des autres parametres
		try {
			checkCreateOrUpdateFields(etudiant, groupeTP);
		} catch (Exception e) {
			// On renvoie une erreur avec le code 400 / Bad Request (code HTTP pour designer
			// une mauvaise requete)
			return new ResponseEntity<Erreur>(new Erreur(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Etudiant>(
				Etudiant.update(identifiant, etudiant.getNom().trim(), etudiant.getPrenom().trim(), groupeTP.get()),
				HttpStatus.OK);
	}

	/**
	 * Verification des champs pour une creation ou une modification
	 * 
	 * @param etudiant l'etudiant
	 * @param groupeTP le groupe de TP (encore non verifie)
	 * @throws Exception une exception fonctionnelle
	 */
	private void checkCreateOrUpdateFields(Etudiant etudiant, Optional<GroupeTP> groupeTP) throws Exception {
		// Le nom ne peut pas etre vide ou nul
		if (etudiant.getNom() == null || "".equals(etudiant.getNom().trim())) {
			String erreur = "Le nom de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		// Le prenom ne peut pas etre vide ou nul
		if (etudiant.getPrenom() == null || "".equals(etudiant.getPrenom().trim())) {
			String erreur = "Le prenom de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		// Le groupe de TP ne peut pas etre nul
		if (etudiant.getGroupeTP().getIdentifiant() == null
				|| "".equals(etudiant.getGroupeTP().getIdentifiant().trim())) {
			String erreur = "Le groupe de TP de l'etudiant ne peut pas etre nul";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		// La liste des groupes de TP doit contenir un groupe de TP ayant l'identifiant
		// passe en parametre
		if (!groupeTP.isPresent()) {
			String erreur = "La liste ne contient pas de groupe de TP correspondant a l'identifiant '"
					+ etudiant.getGroupeTP() + "'";
			System.err.println(erreur);
			throw new Exception(erreur);
		}
	}

	/**
	 * Suppression d'un etudiant
	 * 
	 * URL complete : DELETE /etudiant/{identifiant} (RequestMapping de la classe +
	 * RequestMapping de la methode)
	 * 
	 * @param identifiant l'identifiant de l'étudiant
	 * @return la reponse du controleur - soit rien si l'etudiant est correctement
	 *         supprime, soit un message d'erreur
	 */
	@RequestMapping(value = "/{identifiant}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "identifiant") String identifiant) {
		System.out.println("Suppression de l'etudiant 'identifiant=" + identifiant + "'");

		// Verification de l'identifiant
		try {
			checkAndGetByIdentifiant(identifiant);
		} catch (Exception e) {
			// On renvoie une erreur avec le code 404 / NOT FOUND (code HTTP pour designer
			// une ressource introuvable)
			return new ResponseEntity<Erreur>(new Erreur(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		Etudiant.delete(identifiant);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Verification de l'existence d'un etudiant a partir d'un identifiant, puis
	 * recuperation de l'etudiant
	 * 
	 * @param identifiant l'identifiant
	 * @return l'etudiant
	 * @throws Exception
	 */
	private Etudiant checkAndGetByIdentifiant(String identifiant) throws Exception {
		Optional<Etudiant> etudiant = Etudiant.LISTE.stream().filter(e -> e.getIdentifiant().equals(identifiant))
				.findFirst();

		// La liste des etudiants doit contenir un etudiant ayant l'identifiant passe en
		// parametre
		if (!etudiant.isPresent()) {
			String erreur = "La liste ne contient pas d'etudiant correspondant a l'identifiant '" + identifiant + "'";
			System.err.println(erreur);
			throw new Exception(erreur);
		}

		return etudiant.get();
	}

	/**
	 * Classe de description d'une erreur
	 */
	private static class Erreur implements Serializable {

		private static final long serialVersionUID = 6605102136399759405L;

		/**
		 * Le message d'erreur
		 */
		private String erreur;

		/**
		 * Constructeur
		 * 
		 * @param erreur le message d'erreur
		 */
		public Erreur(String erreur) {
			super();
			this.erreur = erreur;
		}

		// Eclipse considere cette methode comme "unused" mais en realite elle est
		// utilisee pour le mapping JSON <-> Java par SpringBoot
		@SuppressWarnings("unused")
		public String getErreur() {
			return erreur;
		}

		// Eclipse considere cette methode comme "unused" mais en realite elle est
		// utilisee pour le mapping JSON <-> Java par SpringBoot
		@SuppressWarnings("unused")
		public void setErreur(String erreur) {
			this.erreur = erreur;
		}
	}
}
