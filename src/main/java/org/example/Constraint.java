package org.example;

import java.util.List;

public abstract class Constraint {
    // SAT 4J solver Java SAT ( juste pour info)

    //ALGO AC-3 (Arc Consistency 3) ajoute les contraine qui ont un lien avec une borne d'une variable qui a été modifié à la liste des contraintes à vérifier ( file d'attente)
    //Retourne la liste des variables qui ont été modifié
    public abstract List<Variable> filter();

    // Méthode qui vérifie si une contrainte est satisfaite
    public abstract boolean check(int... values);

    public abstract  List<Variable> getVariables() ;
}
