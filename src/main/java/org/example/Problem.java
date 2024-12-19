package org.example;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Problem {
    private final List<Variable> variables;
    private final List<Constraint> constraints;

    public Problem(List<Variable> variables, List<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
        for (Variable v : constraint.getVariables()) {
            v.register(constraint);
        }
    }

    //La fonction renvoi faux si une contrainte à été évité ( c'est à dire que le problème n'est pas solvable)
    public boolean filter(){
        Deque<Constraint> queue = new java.util.ArrayDeque<>();
        for(Constraint c : constraints) {
            queue.add(c);
        }
        //on déroule la queue une pemière fois pour initialiser les domaines
        boolean filtered = false;
        while(!queue.isEmpty()) {
            Constraint c = queue.poll();
            List<Variable> modified = c.filter();
            if(!modified.isEmpty()) {
                filtered = true;
                for(Variable v : modified) {
                    for(Constraint c2 : v.getConstraints()) {
                        if(c2 != c) {
                            //On ajoute les contraintes qui ont un lien avec une borne d'une variable qui a été modifié à la liste des contraintes à vérifier
                            queue.add(c2);
                        }
                    }
                }
            }
        }
        return filtered;
    }

    public String toString() {
        return  constraints.stream().map(Constraint::toString).collect(Collectors.joining("\n"));
    }

    public void solve() {
        while(filter());
    }
}
