// Correction eventuelle de l'URL du navigateur
baseUrl = window.location.href;
if (!baseUrl.endsWith('/')) {
	baseUrl = baseUrl + '/';
}

// Au chargement de la page
window.onload = function() {
	load_Recherche();
	load_Actions();
};

// Chargement des groupes de TP dans un <select>
function load_GroupesTP(selectId) {
	// Appel de GET groupetp - Exemple2GroupeTPController::getAll()
	fetch('groupetp').then(
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
			document.getElementById(selectId).appendChild(option0);

			for (var i in result) {
				var option = document.createElement('option');
				option.text = result[i].groupeTD.annee + 'e annee - Groupe ' + result[i].groupeTD.numero + result[i].groupe;
				option.value = result[i].identifiant;
				document.getElementById(selectId).appendChild(option);
			}
		}
	);
}