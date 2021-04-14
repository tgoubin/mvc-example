package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.blagnac.coo.mvcexample.ex1.controller.Exemple1EtudiantController;
import org.blagnac.coo.mvcexample.model.Etudiant;

/**
 * Panel contenant le tableau
 */
public class Tableau extends JPanel {

	private static final long serialVersionUID = 6782929412739429045L;

	private static final int LARGEUR_TABLEAU = 900;
	private static final int HAUTEUR_TABLEAU = 480;

	private JTable tableauEtudiants;

	/**
	 * Constructeur
	 */
	public Tableau() {
		// Parametrage du panel
		setLayout(new FlowLayout(FlowLayout.CENTER));

		// Parametrage du tableau
		tableauEtudiants = new JTable();
		tableauEtudiants.setModel(new TableauEtudiantsModel());
		tableauEtudiants.setPreferredScrollableViewportSize(new Dimension(LARGEUR_TABLEAU, HAUTEUR_TABLEAU));
		add(new JScrollPane(tableauEtudiants));
	}

	/**
	 * Mise a jour du tableau des etudiants en fonction des (eventuels) filtres
	 * 
	 * @param nom                 la valeur du filtre sur le nom
	 * @param prenom              la valeur du filtre sur lele prenom
	 * @param identifiantGroupeTP la valeur du filtre sur le groupe de TP
	 */
	public void majTableau(String nom, String prenom, String identifiantGroupeTP) {
		TableauEtudiantsModel tableauEtudiantsModel = (TableauEtudiantsModel) tableauEtudiants.getModel();
		tableauEtudiantsModel.setEtudiants(Exemple1EtudiantController.get(nom, prenom, identifiantGroupeTP));
		tableauEtudiantsModel.fireTableDataChanged();
	}

	/**
	 * Modele de tableau pour la liste des etudiants
	 */
	private static class TableauEtudiantsModel extends AbstractTableModel {

		private static final long serialVersionUID = -1993914232997885021L;

		private static final String COLONNE_NOM = "NOM";
		private static final String COLONNE_PRENOM = "PRENOM";
		private static final String COLONNE_ANNEE = "ANNEE";
		private static final String COLONNE_GROUPE_TD = "GROUPE DE TD";
		private static final String COLONNE_GROUPE_TP = "GROUPE DE TP";

		private static final String[] COLONNES = new String[] { COLONNE_NOM, COLONNE_PRENOM, COLONNE_ANNEE,
				COLONNE_GROUPE_TD, COLONNE_GROUPE_TP };

		private List<Etudiant> etudiants;

		/**
		 * Constructeur
		 */
		public TableauEtudiantsModel() {
			this.etudiants = new ArrayList<>();
		}

		public void setEtudiants(List<Etudiant> etudiants) {
			this.etudiants = etudiants;
		}

		/**
		 * Methode de "AbstractTableModel" a implementer
		 */
		@Override
		public int getRowCount() {
			return etudiants.size();
		}

		/**
		 * Methode de "AbstractTableModel" a redefinir pour avoir les bons noms de
		 * colonnes
		 */
		@Override
		public String getColumnName(int column) {
			return COLONNES[column];
		}

		/**
		 * Methode de "AbstractTableModel" a implementer
		 */
		@Override
		public int getColumnCount() {
			return COLONNES.length;
		}

		/**
		 * Methode de "AbstractTableModel" a implementer
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object value = null;
			Etudiant etudiant = etudiants.get(rowIndex);

			switch (columnIndex) {
			case 0:
				value = etudiant.getNom();
				break;
			case 1:
				value = etudiant.getPrenom();
				break;
			case 2:
				value = etudiant.getGroupeTP().getGroupeTD().getAnnee();
				break;
			case 3:
				value = etudiant.getGroupeTP().getGroupeTD().getNumero();
				break;
			case 4:
				value = etudiant.getGroupeTP().getGroupe();
			default:
				break;
			}

			return value;
		}
	}
}
