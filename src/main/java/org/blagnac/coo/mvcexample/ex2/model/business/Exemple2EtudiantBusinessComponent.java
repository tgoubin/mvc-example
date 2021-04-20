package org.blagnac.coo.mvcexample.ex2.model.business;

import java.util.Optional;

import org.blagnac.coo.mvcexample.model.entity.Etudiant;
import org.springframework.stereotype.Component;

/**
 * Classe contenant la logique metier concernant les etudiants
 */
@Component
public class Exemple2EtudiantBusinessComponent {

	/**
	 * Verification de l'existence d'un etudiant a partir d'un identifiant, puis
	 * recuperation de l'etudiant
	 * 
	 * @param identifiant l'identifiant
	 * @return l'etudiant
	 * @throws Exception
	 */
	public Etudiant checkAndGetByIdentifiant(String identifiant) throws Exception {
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
}
