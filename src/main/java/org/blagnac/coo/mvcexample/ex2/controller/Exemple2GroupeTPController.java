package org.blagnac.coo.mvcexample.ex2.controller;

import java.util.List;

import org.blagnac.coo.mvcexample.model.GroupeTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controleur pour les groupes de TP (exemple 2)
 */
@Controller
//Ce controleur est accessible depuis l'URL "http://localhost:8080/<server.servlet.context-path declare dans application.properties>/groupetp"
@RequestMapping(value = "/groupetp")
public class Exemple2GroupeTPController {

	/**
	 * Recuperation des groupes de TP depuis le modele
	 * 
	 * URL complete : GET /groupetp (RequestMapping de la classe + RequestMapping de
	 * la methode)
	 * 
	 * @return les groupes de TP
	 */
	@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<List<GroupeTP>> getAll() {
		return new ResponseEntity<List<GroupeTP>>(GroupeTP.LISTE, HttpStatus.OK);
	}
}
