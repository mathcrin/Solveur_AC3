package org.example;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Problem {
    private final List<Variable> variables;
    private final List<Constraint> constraints;
    private int iterationCounter = 0;
    private int printInterval = 1000;
    private int nb_machine = 5;
    private int nb_job = 20;

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
            if (iterationCounter % printInterval == 0) {
                afficherSolution();
            }
        }
        return filtered;
    }

    private void afficherSolution() {
        int[][] debut_des_taches = new int[nb_machine][nb_job];
        for (int i = 0; i < nb_machine; i++) {
            for (int j = 0; j < nb_job; j++) {
                debut_des_taches[i][j] = variables.get(i * nb_job + j).getLb();
            }
        }

        System.out.println("debut_des_taches = ");
        System.out.print("[|");
        for (int i = 0; i < nb_machine; i++) {
            for (int j = 0; j < nb_job; j++) {
                System.out.printf("%4d", debut_des_taches[i][j]);
                if (j < nb_job - 1) {
                    System.out.print(", ");
                }
            }
            if (i < nb_machine - 1) {
                System.out.println();
                System.out.print(" |");
            }
        }
        System.out.println();
        System.out.println(" |];");
    }

    public String toString() {
        return  constraints.stream().map(Constraint::toString).collect(Collectors.joining("\n"));
    }

    public void solve() {
        while(filter());
    }
}
