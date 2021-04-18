// Chargement du module d'actions
function load_Actions() {
	load_Etudiants();
	onClick_btAjouterEtudiant();
	onClick_btModifierEtudiant();
	onClick_btSupprimerEtudiant();
}

// Chargement des etudiants
function load_Etudiants() {
	// Appel de GET etudiant - Exemple2EtudiantController::get(String, String, String)
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
		displayFormulaireCreationModification(false);
	};
}

// Gestion du clic sur le bouton "Modifier etudiant"
function onClick_btModifierEtudiant() {
	document.getElementById('btModifierEtudiant').onclick = function() {
		if (document.getElementById('inputEtudiant').value !== '') {
			displayFormulaireCreationModification(true);
		} else {
			alert('Veuillez selectionner un etudiant pour effectuer cette action');
		}
	};
}

// Affichage du formulaire de creation / modification
function displayFormulaireCreationModification(isModification) {
	load_GroupesTP('inputGroupeTPAjoutModification');
	document.getElementById('form-creation-modification').style.display = 'block';
	var idEtudiant = null;

	// Si c'est une modification, on recupere les informations de l'etudiant selectionne
	if (isModification) {
		idEtudiant = document.getElementById('inputEtudiant').value;
		
		// Appel de GET etudiant/{identifiant} - Exemple2EtudiantController::getById(String)
		fetch('etudiant/' + idEtudiant).then(
			// Traitement de la reponse du controller
			function(response) {
				// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
				return response.json();
			}
		).then(
			// 2eme etape : reception de la reponse au format JSON
			function(etudiant) {
				// On pre-remplit les valeurs des champs de formulaire
				document.getElementById('inputNomAjoutModification').value = etudiant.nom;
				document.getElementById('inputPrenomAjoutModification').value = etudiant.prenom;
				document.getElementById('inputGroupeTPAjoutModification').value = etudiant.groupeTP.identifiant;
			}
		);
	}

	onClick_btAjouterModifierEtudiant(idEtudiant);
	
	document.getElementById('btAnnulerAjouterModifierEtudiant').onclick = function() {
		hideFormulaireCreationModification();
	};
}

// Gestion du clic sur le bouton "Sauvegarder"
function onClick_btAjouterModifierEtudiant(idEtudiant) {
	// On determine l'url du controleur a appeler, ainsi que la methode HTTP
	var url = 'etudiant';
	var method = 'POST';
	if (idEtudiant !== null) {
		url += '/' + idEtudiant;
		method = 'PUT';
	}
	
	document.getElementById('btAjouterModifierEtudiant').onclick = function() {
		// Appel de POST etudiant ou PUT etudiant/{identifiant} - Exemple2EtudiantController::create(Etudiant) ou Exemple2EtudiantController::update(String, Etudiant)
		var etudiant = {
			nom: document.getElementById('inputNomAjoutModification').value, 
			prenom: document.getElementById('inputPrenomAjoutModification').value, 
			groupeTP: {
				identifiant: document.getElementById('inputGroupeTPAjoutModification').value
			}
		};
		fetch(url, {
			method: method, 
			// Corps de la requete : un objet JSON "Etudiant"
			body: JSON.stringify(etudiant), 
			headers: {
				"Content-Type": "application/json"
			}
		}).then(
			// Traitement de la reponse du controller
			function(response) {
				// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
				return response.json();
			}
		).then(
			// 2eme etape : reception de la reponse au format JSON
			function(response) {
				if (!response.erreur) {
					// Mise a jour des listes des etudiants
					majListesEtudiants();
					
					// Affichage d'un message de confirmation de creation
					if (idEtudiant !== null) {
						alert('L\'etudiant a ete modifie avec succes.');
					} else {
						alert('L\'etudiant a ete ajoute avec succes. Il a l\'identifiant \'' + response.identifiant +'\'');
					}
					
					// On masque le formulaire
					hideFormulaireCreationModification();
				} else {
					alert(response.erreur);
				}
			}
		);
	}
}

// Fermeture de formulaire de creation / modification
function hideFormulaireCreationModification() {
	document.getElementById('form-creation-modification').style.display = 'none';
	document.getElementById('inputNomAjoutModification').value = '';
	document.getElementById('inputPrenomAjoutModification').value = '';
	document.getElementById('inputGroupeTPAjoutModification').value = '';
}

// Gestion du clic sur le bouton "Supprimer etudiant"
function onClick_btSupprimerEtudiant() {
	document.getElementById('btSupprimerEtudiant').onclick = function() {
		if (document.getElementById('inputEtudiant').value !== '') {
			if (confirm('Confirmez-vous la suppression de cet etudiant ?')) {
				// Appel de DELETE etudiant/{identifiant} - Exemple2EtudiantController::delete(String)
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