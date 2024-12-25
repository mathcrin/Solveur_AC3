# Problème de Job Shop planification

Ce projet est une implémentation en Java du problème de Job Shop Scheduling (JSSP) utilisant la programmation par contraintes. L'objectif est de planifier des tâches sur des machines tout en respectant les contraintes de précédence et disjonctives.

## Problème
Je n'ai pas réussi à faire fonctionner correctement les contraintes disjonctives disjonctives.\
Le problème vient de la partie suivant dans la méthode filter() :
```java
    else if (v2.getLb() + duration2 < v1.getLb()) {
            v1.setLb(v2.getLb() + duration2);
            modifiedVariables.add(v1);
            }
```
Qui est censé représenté cette partie là du code en minizinc :
```minizinc
\/ (debut_des_taches[machine,tache+1]+ processing_times[machine,tache+1]) <= debut_des_taches[machine,tache]
);
```
Le code minzinc du porblème est fourni à la racine du projet afin de comparer les résultats

## Structure du projet

- `src/main/java/org/example/`
    - `Variable.java` : Représente une variable dans le problème de planification.
    - `Constraint.java` : Classe abstraite pour les contraintes.
    - `PrecedenceConstraint.java` : Représente les contraintes de précédence entre les tâches.
    - `DisjunctiveConstraint.java` : Représente les contraintes disjonctives entre les tâches.
    - `Problem.java` : Représente le problème de planification et contient des méthodes pour ajouter des contraintes et résoudre le problème.

- `src/test/java/org/example/`
    - `ProblemTest.java` : Contient des cas de test pour le problème de planification.

## Prise en main

### Prérequis

- Java 11 ou supérieur
- Maven

### Installation

1. Clonez le dépôt :
   ```sh
   git clone https://github.com/mathcrin/job-shop-scheduling.git
   cd job-shop-scheduling
    ```
2. Construisez le projet avec Maven :.
   ```sh
   mvn clean install
   ```
3. Exécutez les tests :
   ```sh
    mvn test
    ```
   
## Utilisation
La fonctionnalité principale est implémentée dans la classe Problem. Vous pouvez créer une nouvelle instance de problème, ajouter des variables et des contraintes, et résoudre le problème.\
Voici un exemple d'utilisation :

```java
Problem problem = new Problem();
// Ajouter des variables
Variable x1 = problem.addVariable("x1", 0, 10);
Variable x2 = problem.addVariable("x2", 0, 10);
// Ajouter des contraintes
problem.addConstraint(new PrecedenceConstraint(x1, x2));
problem.addConstraint(new DisjunctiveConstraint(x1, x2));
// Résoudre le problème
problem.solve();
```

## Licence
Ce projet est distribué sous la licence MIT. Voir `LICENSE` pour plus d'informations.

## Sources
Inspiré par diverses ressources et exemples de programmation par contraintes en Minizinc.
