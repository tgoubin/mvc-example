package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Fenetre principale
 */
public class Fenetre extends JFrame {

	private static final long serialVersionUID = -2292399880668479643L;

	private JPanel panelPrincipal;

	private static final String TITRE = "Liste des etudiants";

	private static final int LARGEUR = 800;
	private static final int HAUTEUR = 600;

	/**
	 * Constructeur
	 */
	public Fenetre() {
		// Parametrage de la fenetre
		setTitle(TITRE);
		setSize(LARGEUR, HAUTEUR);
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ajout du panel principal
		panelPrincipal = new JPanel(new BorderLayout());
		setContentPane(panelPrincipal);
		Tableau tableau = new Tableau();
		panelPrincipal.add(tableau, BorderLayout.CENTER);
		panelPrincipal.add(new EnTete(tableau), BorderLayout.NORTH);

		// Affichage de la fenetre
		setVisible(true);
	}
}
