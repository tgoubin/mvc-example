// Chargement du module de recherche
function load_Recherche() {
	load_GroupesTP();
	onClick_btRechercher();
	onClick_btAfficherTout();
}

// Chargement des groupes de TP
function load_GroupesTP() {
	// Appel du controller "/groupetp" declare dans la methode "get()" de Example2GroupeTPContoller
	fetch('groupetp/').then(
		// Traitement de la reponse du controller
		function(response) {
			// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
			return response.json();
		}
	).then(
		// 2eme etape : reception de la reponse au format JSON
		function(result) {
			// Affichage de la reponse (= les groupes de TP) dans le <select>
			var option0 = document.createElement('option');
			option0.text = '';
			option0.value = '';
			document.querySelector('#inputGroupeTP').appendChild(option0);

			for (var i in result) {
				var option = document.createElement('option');
				option.text = result[i].groupeTD.annee + 'e annee - Groupe ' + result[i].groupeTD.numero + result[i].groupe;
				option.value = result[i].identifiant;
				document.querySelector('#inputGroupeTP').appendChild(option);
			}
		}
	);
}

// Gestion du clic sur le bouton "Rechercher"
function onClick_btRechercher() {
	document.getElementById('btRechercher').onclick = function() {
		// Construction du morceau de l'URL correspondant aux filtres de recherche
		// ?<nom du filtre 1>=<valeur du filtre 1>&<nom du filtre 2>=<valeur du filtre 2>...
		var filtres = '?';

		// Ajout du filtre "nom" (correspondance avec le parametre "nom" de la methode "get()" de Example2EtudiantContoller)
		if (document.getElementById('inputNom').value !== '') {
			filtres += 'nom=' + document.getElementById('inputNom').value + '&';
		}

		// Ajout du filtre "prenom" (correspondance avec le parametre "prenom" de la methode "get()" de Example2EtudiantContoller)
		if (document.getElementById('inputPrenom').value !== '') {
			filtres += 'prenom=' + document.getElementById('inputPrenom').value + '&';
		}

		// Ajout du filtre "groupeTP" (correspondance avec le parametre "groupeTP" de la methode "get()" de Example2EtudiantContoller)
		if (document.getElementById('inputGroupeTP').value !== '') {
			filtres += 'groupeTP=' + document.getElementById('inputGroupeTP').value + '&';
		}

		// Appel du controller "/etudiant" declare dans la methode "get()" de Example2EtudiantContoller
		// On lui transmet les filtres de recherche, par l'URL
		fetch('etudiant/' + filtres).then(
			// Traitement de la reponse du controller
			function(response) {
				// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
				return response.json();
			}
		).then(
			// 2eme etape : reception de la reponse au format JSON
			function(result) {
				// Affichage de la reponse (= les etudiants)
				displayEtudiantsInTable(result);
			}
		);
	};
}

// Gestion du clic sur le bouton "Afficher tout"
function onClick_btAfficherTout() {
	document.getElementById('btAfficherTout').onclick = function() {
		document.getElementById('inputNom').value = '';
		document.getElementById('inputPrenom').value = '';
		document.getElementById('inputGroupeTP').value = '';

		// Appel du controller "/etudiant" declare dans la methode "get()" de Example2EtudiantContoller
		// On ne lui transmet aucun filtre de recherche, puisqu'on veut afficher tous les etudiants
		fetch('etudiant/').then(
			// Traitement de la reponse du controller
			function(response) {
				// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
				return response.json();
			}
		).then(
			// 2eme etape : reception de la reponse au format JSON
			function(result) {
				// Affichage de la reponse (= les etudiants)
				displayEtudiantsInTable(result);
			}
		);
	};
}

// Affichage des etudiants dans la <table>
function displayEtudiantsInTable(etudiants) {
	// Reset de la table
	var existingTBody = document.querySelector('#tableau table tbody');
	if (existingTBody) {
		existingTBody.remove();
	}

	var tbody = document.createElement('tbody');

	for (var i in etudiants) {
		// Creation d'une ligne
		var tr = document.createElement('tr');

		// Creation de la cellule pour le nom
		var tdNom = document.createElement('td');
		// Recuperation du nom depuis le JSON : propriete "nom" (correspondance avec l'attribut "nom" de la classe Etudiant)
		tdNom.appendChild(document.createTextNode(etudiants[i].nom));
		tr.appendChild(tdNom);

		// Creation de la cellule pour le prenom
		var tdPrenom = document.createElement('td');
		// Recuperation du nom depuis le JSON : propriete "prenom" (correspondance avec l'attribut "prenom" de la classe Etudiant)
		tdPrenom.appendChild(document.createTextNode(etudiants[i].prenom));
		tr.appendChild(tdPrenom);

		// Creation de la cellule pour l'annee
		var tdAnnee = document.createElement('td');
		// Recuperation du nom depuis le JSON : propriete "annee" (correspondance avec l'attribut "annee" de la classe GroupeTD)
		tdAnnee.appendChild(document.createTextNode(etudiants[i].groupeTP.groupeTD.annee));
		tr.appendChild(tdAnnee);

		// Creation de la cellule pour le groupe de TD
		var tdGroupeTD = document.createElement('td');
		// Recuperation du nom depuis le JSON : propriete "numero" (correspondance avec l'attribut "numero" de la classe GroupeTD)
		tdGroupeTD.appendChild(document.createTextNode(etudiants[i].groupeTP.groupeTD.numero));
		tr.appendChild(tdGroupeTD);

		// Creation de la cellule pour le groupe de TP
		var tdGroupeTP = document.createElement('td');
		// Recuperation du nom depuis le JSON : propriete "groupe" (correspondance avec l'attribut "groupe" de la classe GroupeTP)
		tdGroupeTP.appendChild(document.createTextNode(etudiants[i].groupeTP.groupe));
		tr.appendChild(tdGroupeTP);

		// Ajout de la ligne au tableau
		tbody.appendChild(tr);
	}

	document.querySelector('#tableau table').appendChild(tbody);
}