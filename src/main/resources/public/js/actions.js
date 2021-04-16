// Chargement du module d'actions
function load_Actions() {
	load_Etudiants();
	onClick_btAjouterEtudiant();
	onClick_btModifierEtudiant();
	onClick_btSupprimerEtudiant();
}

// Chargement des etudiants
function load_Etudiants() {
	// Appel du controller "/etudiant" declare dans la methode "get()" de Example2EtudiantContoller
	// Sans lui transmettre de filtre de recherche
	fetch('etudiant').then(
		// Traitement de la reponse du controller
		function(response) {
			// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
			return response.json();
		}
	).then(
		// 2eme etape : reception de la reponse au format JSON
		function(result) {
			// Reset du select
			document.getElementById('inputEtudiant').innerHTML = '';
			
			// Affichage de la reponse (= les etudiants) dans le <select>
			var option0 = document.createElement('option');
			option0.text = '';
			option0.value = '';
			document.getElementById('inputEtudiant').appendChild(option0);

			for (var i in result) {
				var option = document.createElement('option');
				option.text =
					result[i].nom + ' ' + result[i].prenom + ' - ' +
					result[i].groupeTP.groupeTD.annee + 'e annee - Groupe ' + result[i].groupeTP.groupeTD.numero + result[i].groupeTP.groupe;
				option.value = result[i].identifiant;
				document.getElementById('inputEtudiant').appendChild(option);
			}
		}
	);
}

// Gestion du clic sur le bouton "Ajouter etudiant"
function onClick_btAjouterEtudiant() {
	document.getElementById('btAjouterEtudiant').onclick = function() {
	
	};
}

// Gestion du clic sur le bouton "Modifier etudiant"
function onClick_btModifierEtudiant() {
	document.getElementById('btModifierEtudiant').onclick = function() {
		if (document.getElementById('inputEtudiant').value !== '') {
			
		} else {
			alert('Veuillez selectionner un etudiant pour effectuer cette action');
		}
	};
}

// Affichage du formulaire de creation / modification
function displayFormulaireCreationModification() {
	load_GroupesTP('inputGroupeTPEtudiant');
}

// Gestion du clic sur le bouton "Supprimer etudiant"
function onClick_btSupprimerEtudiant() {
	document.getElementById('btSupprimerEtudiant').onclick = function() {
		if (document.getElementById('inputEtudiant').value !== '') {
			if (confirm('Confirmez-vous la suppression de cet etudiant ?')) {
				fetch('etudiant/' + document.getElementById('inputEtudiant').value, {
					method: 'DELETE'
				}).then(
					// Traitement de la reponse du controller
					function(response) {
						if (response.ok) {
							// Mise a jour des listes des etudiants
							majListesEtudiants();
						} else {
							alert(response.text);
						}
					}
				);
			}
		} else {
			alert('Veuillez selectionner un etudiant pour effectuer cette action');
		}
	};
}

// Mise a jour des listes des etudiants
function majListesEtudiants() {
	displayEtudiantsInTable();
	load_Etudiants();
}