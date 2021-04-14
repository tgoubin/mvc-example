package org.blagnac.coo.mvcexample.ex1.controller;

import java.util.ArrayList;
import java.util.List;

import org.blagnac.coo.mvcexample.model.GroupeTP;

/**
 * Controleur pour les groupes de TP (exemple 1)
 */
public class Exemple1GroupeTPController {

	/**
	 * Recuperation des groupes de TP depuis le modele
	 * 
	 * @return les groupes de TP
	 */
	public static List<GroupeTP> getAll() {
		return new ArrayList<>(GroupeTP.LISTE);
	}
}
