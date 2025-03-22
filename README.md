# Projet-algo

Solveur de Labyrinthe en Java 

# Objectif du Projet 

L'objectif de ce projet est d'implémenter un solveur de labyrinthe en Java en utilisant des algorithmes de recherche de chemin. Le programme charge un labyrinthe à partir d'un fichier texte et trouve un chemin entre un point de départ (S) et un point d'arrivée (E). 

# Choix Algorithmiques 

Le programme implémente deux stratégies principales pour explorer le labyrinthe : 

Recherche en profondeur (DFS - Depth First Search) : Utilise une pile (Pile) pour explorer un chemin jusqu'à atteindre un cul-de-sac, puis revenir en arrière. 

Recherche en largeur (BFS - Breadth First Search) : Utilise une file (Queue) pour explorer tous les chemins possibles de manière itérative, garantissant le chemin le plus court. 

Par défaut, l'algorithme DFS est utilisé dans le code actuel, ce qui signifie que l'exploration se fait en profondeur. Toutefois, l'utilisateur peut modifier le code pour utiliser BFS en remplaçant la structure Pile par Queue dans la méthode solve(). Une amélioration future pourrait inclure un paramètre permettant de choisir dynamiquement l'algorithme à l'exécution. 

# Exécution du Code 

1. Compilation du projet 

Assurez-vous d'avoir Java installé sur votre machine. 

       javac Labyrinthe.java 

 

2. Exécution du solveur 

 

java Labyrinthe maze.txt  # Utilisation de DFS (par défaut) 

Si vous souhaitez modifier le code pour utiliser BFS, changez l'instance de Pile en Queue dans la méthode solve(). 

3. Format du fichier maze.txt 

Le fichier doit contenir : 

S : Point de départ (obligatoire, unique) 

E : Point d'arrivée (obligatoire, unique) 

= : Mur 

(espace) : Passage libre 

Exemple de maze.txt 

 

=====S===== 

=         = 

=  ====   = 

=  =  =   = 

=  =  === = 

=     =   = 

=====E===== 

 

# Dépendances 

Aucune dépendance externe n'est requise. Assurez-vous seulement d'avoir Java installé (JDK 8 ou supérieur). 

Résultats 

Le programme affiche : 

Le labyrinthe initial 

Le nombre de chemins explorés 

La solution trouvée (si existante) 

Le labyrinthe avec le chemin marqué par * 

Exemple de sortie 

Labyrinthe : 

 

█████S█████ 

█         █ 

█  ████   █ 

█  █  █   █ 

█  █  ███ █ 

█     █   █ 

█████E█████ 

 

Résolution... 

Chemins explorés : 10 

Solution : 

 

█████S█████ 

█  *****  █ 

█  ████*  █ 

█  █  █*  █ 

█  █  ███*█ 

█     █  *█ 

█████E█████ 

 

Si aucun chemin n'est trouvé, un message d'erreur est affiché : 

Aucune solution trouvée. 
