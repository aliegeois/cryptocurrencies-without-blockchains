# Cryptocurrencies without blockchains

## Idées en vrac du 18/01 (à la sortie de la première réunion):

- Quand on veut ajouter un notaire, on notifie tous les notaires (avec vérification à 2/3 ?)
- Supprimer un notaire: quand on effectue une transaction (chaque transaction ?) qui échoue on regarde les notaires qui ont accepté (donc potentiellement byzantins) et on les vire du système. Ou on a un système de confiance et un notaire qui se plante baisse en confiance jusqu'à être viré s'il fait trop d'erreurs
- _Faire comme Skype ? Un client peut devenir un notaire s'il n'envoie jamais de demande qui foire comme le double spending (pas sûr que ça soit viable)_
- Les notaires doivent-ils garder une liste des clients pour les notifier de l'ajout / suppression de notaires ?
- Avoir un sous ensemble de notaires dits "de confiance absolue" qui ne bougent pas souvent et qui se connaissent entre eux. Un ajout/suppression de notaire "annexe" (donc pas de confiance absolue) se fait à 2/3 (l'unanimité ??) parmi ces notaires. Les clients ont des références vers ces notaires là uniquement et lors d'une transaction les notaires de confiance absolue passent les demandes aux notaires annexes. L'ajout de notaire "de confiance absolue" peut être couteux en nombre de messages
- Avoir un système de notation pour les notaires ?
