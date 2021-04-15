package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * Formulaires
 */
public class Formulaires extends JPanel {

	private static final long serialVersionUID = -1439663064358055090L;

	private static final int LARGEUR_FORMULAIRES = 950;
	private static final int HAUTEUR_FORMULAIRES = 150;

	/**
	 * Constructeur
	 * 
	 * @param tableau le tableau
	 */
	public Formulaires(Tableau tableau) {
		// Parametrage du panel
		setPreferredSize(new Dimension(LARGEUR_FORMULAIRES, HAUTEUR_FORMULAIRES));
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout du formulaire de recherche
		add(new FormRecherche(tableau));

		// Ajout du formulaire d'ajout / modification / suppression
		add(new FormActions(tableau));
	}
}
