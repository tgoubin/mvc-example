package org.blagnac.coo.mvcexample.ex2.controller;

import java.util.List;

import org.blagnac.coo.mvcexample.model.Etudiant;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
	 * URL complete : GET /etudiant/ (RequestMapping de la classe + RequestMapping
	 * de la methode)
	 * 
	 * @param nom                 la valeur du filtre sur le nom - transmis par URL
	 * @param prenom              la valeur du filtre sur le prenom - transmis par URL
	 * @param identifiantGroupeTP la valeur du filtre sur le groupe de TP - transmis par URL
	 * @return les etudiants correspondant eventuellement aux filtres de recherche
	 */
	@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<Etudiant>> get(@RequestParam(value = "nom", required = false) String nom,
			@RequestParam(value = "prenom", required = false) String prenom,
			@RequestParam(value = "groupeTP", required = false) String identifiantGroupeTP) {
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
}
