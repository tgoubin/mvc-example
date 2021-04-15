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