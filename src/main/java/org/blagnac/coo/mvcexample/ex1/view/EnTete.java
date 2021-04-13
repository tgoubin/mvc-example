package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.blagnac.coo.mvcexample.ex1.controller.Exemple1GroupeTPController;
import org.blagnac.coo.mvcexample.model.GroupeTP;

/**
 * En-tete de la fenetre
 */
public class EnTete extends JPanel {

	private static final long serialVersionUID = 8805692934172868767L;

	private static final String NOM_LABEL = "Nom";
	private static final String PRENOM_LABEL = "Prenom";
	private static final String GROUPE_TP_LABEL = "Groupe de TP";
	private static final String RECHERCHER_LABEL = "Rechercher";
	private static final String AFFICHER_TOUT_LABEL = "Afficher tout";

	private static final int LARGEUR_CHAMPS_RECHERCHE = 100;
	private static final int HAUTEUR_CHAMPS_RECHERCHE = 25;

	private static final int LARGEUR_BOUTONS = 120;
	private static final int HAUTEUR_BOUTONS = 25;

	private Tableau tableau;

	private JTextField inputRechercheNom;
	private JTextField inputRecherchePrenom;
	private JComboBox<GroupeTP> inputRechercheGroupeTP;

	private JButton btRechercher;
	private JButton btAfficherTout;

	/**
	 * Constructeur
	 * 
	 * @param tableau le tableau
	 */
	public EnTete(Tableau tableau) {
		this.tableau = tableau;

		// Parametrage de l'en-tete
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Ajout des composants (labels, champs de saisie, boutons)
		add(new JLabel(NOM_LABEL + " :"));
		inputRechercheNom = new JTextField();
		inputRechercheNom.setPreferredSize(new Dimension(LARGEUR_CHAMPS_RECHERCHE, HAUTEUR_CHAMPS_RECHERCHE));
		add(inputRechercheNom);

		add(new JLabel(PRENOM_LABEL + " :"));
		inputRecherchePrenom = new JTextField();
		inputRecherchePrenom.setPreferredSize(new Dimension(LARGEUR_CHAMPS_RECHERCHE, HAUTEUR_CHAMPS_RECHERCHE));
		add(inputRecherchePrenom);

		add(new JLabel(GROUPE_TP_LABEL + " :"));
		inputRechercheGroupeTP = new JComboBox<>();
		inputRechercheGroupeTP.setModel(new GroupeTPComboBoxModel(getGroupesTP()));
		add(inputRechercheGroupeTP);

		btRechercher = new JButton(RECHERCHER_LABEL);
		btRechercher.setPreferredSize(new Dimension(LARGEUR_BOUTONS, HAUTEUR_BOUTONS));
		add(btRechercher);

		btAfficherTout = new JButton(AFFICHER_TOUT_LABEL);
		btAfficherTout.setPreferredSize(new Dimension(LARGEUR_BOUTONS, HAUTEUR_BOUTONS));
		add(btAfficherTout);

		// Gestion du clic sur les boutons
		onClickBtRechercher();
		onClickBtAfficherTout();
	}

	/**
	 * Recuperation de la liste des identifiants des groupes de TP, pour la ComboBox
	 * (avec une valeur vide en premier)
	 * 
	 * @return la liste des identifiants des groupes de TP
	 */
	private List<GroupeTP> getGroupesTP() {
		List<GroupeTP> groupesTP = Exemple1GroupeTPController.getAll();
		groupesTP.add(0, new GroupeTP(null, null, null));
		return groupesTP;
	}

	/**
	 * Gestion du clic sur le bouton "Rechercher"
	 */
	private void onClickBtRechercher() {
		btRechercher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableau.majTableau(inputRechercheNom.getText(), inputRecherchePrenom.getText(),
						((GroupeTP) inputRechercheGroupeTP.getSelectedItem()).getIdentifiant());
			}
		});
	}

	/**
	 * Gestion du clic sur le bouton "Afficher tout"
	 */
	private void onClickBtAfficherTout() {
		btAfficherTout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputRechercheNom.setText("");
				inputRecherchePrenom.setText("");
				inputRechercheGroupeTP.setSelectedIndex(0);
				tableau.majTableau(null, null, null);
			}
		});
	}

	/**
	 * Modele de combobox pour la liste des groupes de TP
	 */
	private static class GroupeTPComboBoxModel extends DefaultComboBoxModel<GroupeTP> {

		private static final long serialVersionUID = 553605937872036148L;

		/**
		 * Constructeur
		 */
		public GroupeTPComboBoxModel(List<GroupeTP> groupesTP) {
			super(new Vector<GroupeTP>(groupesTP));
		}
	}
}
