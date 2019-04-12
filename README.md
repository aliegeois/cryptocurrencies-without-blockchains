# Cryptocurrencies without blockchains

## Idées en vrac du 18/01 (à la sortie de la première réunion):

- Quand on veut ajouter un notaire, on notifie tous les notaires (avec vérification à 2/3 ?)
- Supprimer un notaire: quand on effectue une transaction (chaque transaction ?) qui échoue on regarde les notaires qui ont accepté (donc potentiellement byzantins) et on les vire du système. Ou on a un système de confiance et un notaire qui se plante baisse en confiance jusqu'à être viré s'il fait trop d'erreurs
- _Faire comme Skype ? Un client peut devenir un notaire s'il n'envoie jamais de demande qui foire comme le double spending (pas sûr que ça soit viable)_
- Les notaires doivent-ils garder une liste des clients pour les notifier de l'ajout / suppression de notaires ?
- Avoir un sous ensemble de notaires dits "de confiance absolue" qui ne bougent pas souvent et qui se connaissent entre eux. Un ajout/suppression de notaire "annexe" (donc pas de confiance absolue) se fait à 2/3 (l'unanimité ??) parmi ces notaires. Les clients ont des références vers ces notaires là uniquement et lors d'une transaction les notaires de confiance absolue passent les demandes aux notaires annexes. L'ajout de notaire "de confiance absolue" peut être couteux en nombre de messages
- Avoir un système de notation pour les notaires ?



## Idées du 25/01:

Lorsqu'un nouveau notaire arrive, comment le mettre à jour au niveau des transactions déjà effectuées ?  

Il demande à tous les notaires de lui envoyer l'état de leurs transactions.  
On crée un set dans lequel on met chaque état ainsi que le nombre de fois qu'il a été reçu.  
On récupère l'état ayant le plus d'occurences et on le définit comme étant l'état actuel du nouveau notaire.  

Ex:  
`En`: état normal envoyé par 3 notaires  
`Eb`: état byzantin envoyé par 1 notaire  
{ `En` x 3, `Eb` x 1 }  
`En` est le plus présent, il est conservé

## Idée de Charles du 08/03

Ajout d'un notaire:
Après qu'un nouveau notaire se soit mis à jour avec le tableau accepts des autres notaires, il n'envoie pas sa colonne aux autres notaires. Elle sera mise à jour petit à petit par les autres notaires au fur et à mesure qu'il (le nouveau notaire) envoie des M2

Pour les autres notaires, lors de l'ajout d'un nouveau, on met des null dans sa colonne et pour la réception d'un M1, si null est présent dans une colonne, on l'exclus du calcul. Quand un M2 est reçu, on remplace le null par la valeur donnée.

## Update du 29/03

On supprime la notion du pièce, on stocke maintenant juste la quantité d'argent que possède chaque client.