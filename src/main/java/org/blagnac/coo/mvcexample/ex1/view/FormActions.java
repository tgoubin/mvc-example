package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.blagnac.coo.mvcexample.ex1.controller.Exemple1EtudiantController;
import org.blagnac.coo.mvcexample.model.entity.Etudiant;
import org.blagnac.coo.mvcexample.model.entity.GroupeTP;

/**
 * Formulaire d'actions
 */
public class FormActions extends JPanel {

	private static final long serialVersionUID = -1475221097594229914L;

	private static final String ACTIONS_LABEL = "ACTIONS";
	private static final String AJOUTER_LABEL = "Ajouter un etudiant";
	private static final String SELECTIONNER_ETUDIANT_LABEL = "Selectionner un etudiant";
	private static final String MODIFIER_LABEL = "Modifier";
	private static final String SUPPRIMER_LABEL = "Supprimer";
	private static final String NOM_LABEL = "Nom";
	private static final String PRENOM_LABEL = "Prenom";
	private static final String GROUPE_TP_LABEL = "Groupe de TP";
	private static final String ETUDIANT_AJOUTE_SUCCES_LABEL = "L'etudiant a ete ajoute avec succes. Il a l'identifiant";
	private static final String VEUILLEZ_SELECTIONNER_ETUDIANT_LABEL = "Veuillez selectionner un etudiant pour effectuer cette action";
	private static final String ETUDIANT_MODIFIE_SUCCES_LABEL = "L'etudiant a ete modifie avec succes";
	private static final String CONFIRMATION_SUPPRESSION_LABEL = "Confirmez-vous la suppression de cet etudiant ?";
	private static final String ETUDIANT_SUPPRIME_SUCCES_LABEL = "L'etudiant a ete supprime avec succes";

	private static final int LARGEUR_FORMULAIRE = 950;
	private static final int HAUTEUR_FORMULAIRE = 60;

	private static final int LARGEUR_BOUTONS = 150;
	private static final int HAUTEUR_BOUTONS = 25;

	private Tableau tableau;

	private JComboBox<Etudiant> inputEtudiant;

	private JButton btAjouterEtudiant;
	private JButton btModifierEtudiant;
	private JButton btSupprimerEtudiant;

	/**
	 * Constructeur
	 * 
	 * @param tableau le tableau
	 */
	public FormActions(Tableau tableau) {
		this.tableau = tableau;

		// Parametrage du formulaire
		setPreferredSize(new Dimension(LARGEUR_FORMULAIRE, HAUTEUR_FORMULAIRE));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBorder(BorderFactory.createTitledBorder(ACTIONS_LABEL));

		btAjouterEtudiant = new JButton(AJOUTER_LABEL);
		btAjouterEtudiant.setPreferredSize(new Dimension(LARGEUR_BOUTONS, HAUTEUR_BOUTONS));
		add(btAjouterEtudiant);

		add(new JLabel(SELECTIONNER_ETUDIANT_LABEL + " :"));
		inputEtudiant = new JComboBox<>();
		inputEtudiant.setModel(new EtudiantComboBoxModel());
		add(inputEtudiant);

		btModifierEtudiant = new JButton(MODIFIER_LABEL);
		btModifierEtudiant.setPreferredSize(new Dimension(LARGEUR_BOUTONS, HAUTEUR_BOUTONS));
		add(btModifierEtudiant);

		btSupprimerEtudiant = new JButton(SUPPRIMER_LABEL);
		btSupprimerEtudiant.setPreferredSize(new Dimension(LARGEUR_BOUTONS, HAUTEUR_BOUTONS));
		add(btSupprimerEtudiant);

		// Gestion du clic sur les boutons
		onClickBtAjouterEtudiant();
		onClickBtModifierEtudiant();
		onClickBtSupprimerEtudiant();
	}

	/**
	 * Gestion du clic sur le bouton "Ajouter etudiant"
	 */
	private void onClickBtAjouterEtudiant() {
		btAjouterEtudiant.addActionListener(new ActionListener() {

			/**
			 * Methode appelee au clic sur le bouton "Ajouter etudiant"
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(getParent(), NOM_LABEL + " : ", AJOUTER_LABEL,
						JOptionPane.QUESTION_MESSAGE);

				if (nom != null) {
					String prenom = JOptionPane.showInputDialog(getParent(), PRENOM_LABEL + " : ", AJOUTER_LABEL,
							JOptionPane.QUESTION_MESSAGE);

					if (prenom != null) {
						Object[] groupesTP = GroupeTP.LISTE.toArray();
						GroupeTP groupeTP = (GroupeTP) JOptionPane.showInputDialog(getParent(), GROUPE_TP_LABEL,
								AJOUTER_LABEL, JOptionPane.QUESTION_MESSAGE, null, groupesTP, groupesTP[0]);

						if (groupeTP != null) {
							try {
								// Passage par le controleur pour ajouter l'etudiant
								Etudiant etudiant = Exemple1EtudiantController.createEtudiant(nom, prenom, groupeTP);

								// Mise a jour des listes des etudiants
								majListesEtudiants();

								// Affichage d'un message de confirmation de creation
								JOptionPane.showMessageDialog(getParent(),
										ETUDIANT_AJOUTE_SUCCES_LABEL + " '" + etudiant.getIdentifiant() + "'", null,
										JOptionPane.INFORMATION_MESSAGE);
							} catch (Exception exc) {
								// Affichage d'une erreur fonctionnelle eventuellement renvoyee par
								// le controller
								JOptionPane.showMessageDialog(getParent(), exc.getMessage(), null,
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});
	}

	/**
	 * Gestion du clic sur le bouton "Modifier"
	 */
	private void onClickBtModifierEtudiant() {
		btModifierEtudiant.addActionListener(new ActionListener() {

			/**
			 * Methode appelee au clic sur le bouton "Modifier"
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Etudiant etudiantSelectionne = (Etudiant) inputEtudiant.getSelectedItem();
				if (etudiantSelectionne.getIdentifiant() != null) {
					String nom = (String) JOptionPane.showInputDialog(getParent(), NOM_LABEL + " : ",
							MODIFIER_LABEL + " '" + etudiantSelectionne.toString() + "'", JOptionPane.QUESTION_MESSAGE,
							null, null, etudiantSelectionne.getNom());

					if (nom != null) {
						String prenom = (String) JOptionPane.showInputDialog(getParent(), PRENOM_LABEL + " : ",
								MODIFIER_LABEL + " '" + etudiantSelectionne.toString() + "'",
								JOptionPane.QUESTION_MESSAGE, null, null, etudiantSelectionne.getPrenom());

						if (prenom != null) {
							Object[] groupesTP = GroupeTP.LISTE.toArray();
							GroupeTP groupeTP = (GroupeTP) JOptionPane.showInputDialog(getParent(), GROUPE_TP_LABEL,
									MODIFIER_LABEL + " '" + etudiantSelectionne.toString() + "'",
									JOptionPane.QUESTION_MESSAGE, null, groupesTP, etudiantSelectionne.getGroupeTP());

							if (groupeTP != null) {
								try {
									// Passage par le controleur pour ajouter l'etudiant
									Exemple1EtudiantController.updateEtudiant(etudiantSelectionne.getIdentifiant(), nom,
											prenom, groupeTP);

									// Mise a jour des listes des etudiants
									majListesEtudiants();

									// Affichage d'un message de confirmation de modification
									JOptionPane.showMessageDialog(getParent(), ETUDIANT_MODIFIE_SUCCES_LABEL, null,
											JOptionPane.INFORMATION_MESSAGE);
								} catch (Exception exc) {
									// Affichage d'une erreur fonctionnelle eventuellement renvoyee par
									// le controller
									JOptionPane.showMessageDialog(getParent(), exc.getMessage(), null,
											JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(getParent(), VEUILLEZ_SELECTIONNER_ETUDIANT_LABEL, null,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Gestion du clic sur le bouton "Supprimer"
	 */
	private void onClickBtSupprimerEtudiant() {
		btSupprimerEtudiant.addActionListener(new ActionListener() {

			/**
			 * Methode appelee au clic sur le bouton "Supprimer"
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				Etudiant etudiantSelectionne = (Etudiant) inputEtudiant.getSelectedItem();
				if (etudiantSelectionne.getIdentifiant() != null) {
					if (JOptionPane.showConfirmDialog(getParent(),
							CONFIRMATION_SUPPRESSION_LABEL) == JOptionPane.YES_OPTION) {
						try {
							// Passage par le controleur pour supprimer l'etudiant
							Exemple1EtudiantController.deleteEtudiant(etudiantSelectionne.getIdentifiant());

							// Mise a jour des listes des etudiants
							majListesEtudiants();

							// Affichage d'un message de confirmation de suppression
							JOptionPane.showMessageDialog(getParent(), ETUDIANT_SUPPRIME_SUCCES_LABEL, null,
									JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception exc) {
							// Affichage d'une erreur fonctionnelle eventuellement renvoyee par
							// le controller
							JOptionPane.showMessageDialog(getParent(), exc.getMessage(), null,
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(getParent(), VEUILLEZ_SELECTIONNER_ETUDIANT_LABEL, null,
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Mise a jour des listes des etudiants
	 */
	private void majListesEtudiants() {
		tableau.majTableau(null, null, null);
		inputEtudiant.setModel(new EtudiantComboBoxModel());
	}

	/**
	 * Modele de combobox pour la liste des etudiants
	 */
	private static class EtudiantComboBoxModel extends DefaultComboBoxModel<Etudiant> {

		private static final long serialVersionUID = 553605937872036148L;

		/**
		 * Constructeur
		 */
		public EtudiantComboBoxModel() {
			super(new Vector<Etudiant>(getEtudiants()));
		}

		/**
		 * Recuperation de la liste des etudiants (avec une valeur vide en premier)
		 * 
		 * @return la liste des etudiants
		 */
		private static List<Etudiant> getEtudiants() {
			// Recuperation de la liste des etudiants aupres du modele
			List<Etudiant> etudiants = new ArrayList<>(Etudiant.getAll());

			// Ajout d'un element vide destine a la premiere position de la ComboBox
			etudiants.add(0, new Etudiant(null, null, null, null));

			return etudiants;
		}
	}
}
