// Chargement du module de recherche
function load_Recherche() {
	load_GroupesTP('inputGroupeTP');
	onClick_btRechercher();
	onClick_btAfficherTout();
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

		displayEtudiantsInTable(filtres);
	};
}

// Gestion du clic sur le bouton "Afficher tout"
function onClick_btAfficherTout() {
	document.getElementById('btAfficherTout').onclick = function() {
		document.getElementById('inputNom').value = '';
		document.getElementById('inputPrenom').value = '';
		document.getElementById('inputGroupeTP').value = '';

		displayEtudiantsInTable();
	};
}

// Affichage des etudiants dans la <table>
function displayEtudiantsInTable(filtres) {
	// Appel du controller "/etudiant" declare dans la methode "get()" de Example2EtudiantContoller
	// On lui transmet les filtres de recherche, par l'URL
	fetch('etudiant' + ((filtres) ? filtres : '')).then(
		// Traitement de la reponse du controller
		function(response) {
			// 1ere etape : envoi de la reponse au format JSON vers la 2eme etape
			return response.json();
		}
	).then(
		// 2eme etape : reception de la reponse au format JSON
		function(etudiants) {
			// Affichage de la reponse (= les etudiants)
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
				createEtudiantFieldCell(etudiants[i].nom, tr);
		
				// Creation de la cellule pour le prenom
				createEtudiantFieldCell(etudiants[i].prenom, tr);
		
				// Creation de la cellule pour l'annee
				createEtudiantFieldCell(etudiants[i].groupeTP.groupeTD.annee, tr);
		
				// Creation de la cellule pour le groupe de TD
				createEtudiantFieldCell(etudiants[i].groupeTP.groupeTD.numero, tr);
		
				// Creation de la cellule pour le groupe de TP
				createEtudiantFieldCell(etudiants[i].groupeTP.groupe, tr);
		
				// Ajout de la ligne au tableau
				tbody.appendChild(tr);
			}
		
			document.querySelector('#tableau table').appendChild(tbody);
		}
	);
}

// Creation d'une cellule pour un champ de l'objet etudiant
function createEtudiantFieldCell(etudiantField, tr) {
	var td = document.createElement('td');
	// Recuperation du champ depuis le JSON (correspondance avec l'attribut equivalent de la classe Java)
	td.appendChild(document.createTextNode(etudiantField));
	tr.appendChild(td);
}