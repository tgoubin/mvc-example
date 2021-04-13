package org.blagnac.coo.mvcexample.ex1.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.blagnac.coo.mvcexample.ex1.controller.EtudiantController;
import org.blagnac.coo.mvcexample.model.Etudiant;

/**
 * Panel contenant le tableau
 */
public class Tableau extends JPanel {

	private static final long serialVersionUID = 6782929412739429045L;

	private JTable tableauEtudiants;

	/**
	 * Constructeur
	 */
	public Tableau() {
		// Parametrage de l'en-tete
		setLayout(new FlowLayout(FlowLayout.CENTER));

		tableauEtudiants = new JTable();
		tableauEtudiants.setModel(new TableauEtudiantsModel());
		add(tableauEtudiants);
	}

	/**
	 * Mise a jour du tableau des etudiants en fonction des (eventuels) filtres
	 * 
	 * @param nom      la valeur du filtre sur le nom
	 * @param prenom   la valeur du filtre sur lele prenom
	 * @param groupeTP la valeur du filtre sur lele groupe de TP
	 */
	public void majTableau(String nom, String prenom, String groupeTP) {
		TableauEtudiantsModel tableauEtudiantsModel = (TableauEtudiantsModel) tableauEtudiants.getModel();

		if (nom == null && prenom == null && groupeTP == null) {
			// Aucun filtrage
			tableauEtudiantsModel.setEtudiants(EtudiantController.getAll());
			tableauEtudiantsModel.fireTableDataChanged();
		} else {

		}
	}

	/**
	 * Modele de tableau pour la liste des etudiants
	 */
	private static class TableauEtudiantsModel extends AbstractTableModel {

		private static final long serialVersionUID = -1993914232997885021L;

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

		@Override
		public int getRowCount() {
			return etudiants.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

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
				value = etudiant.getGroupeTP().toString();
				break;
			default:
				break;
			}

			return value;
		}
	}
}
