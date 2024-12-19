package org.example;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProblemTest extends TestCase {
    private int nb_machine = 5;
    private int nb_job = 20;

    private int[][] precedence = {

    };
    int[][] processing_times = {
            {54, 83, 15, 71, 77, 36, 53, 38, 27, 87, 76, 91, 14, 29, 12, 77, 32, 87, 68, 94},
            {79, 3, 11, 99, 56, 70, 99, 60, 5, 56, 3, 61, 73, 75, 47, 14, 21, 86, 5, 77},
            {16, 89, 49, 15, 89, 45, 60, 23, 57, 64, 7, 1, 63, 41, 63, 47, 26, 75, 77, 40},
            {66, 58, 31, 68, 78, 91, 13, 59, 49, 85, 85, 9, 39, 41, 56, 40, 54, 77, 51, 31},
            {58, 56, 20, 85, 53, 35, 53, 41, 69, 13, 86, 72, 8, 49, 47, 87, 58, 18, 68, 28}
    };
    private List<Variable> variables; // TODO : Mettre les vairable sous forme de matrice
    private List<Constraint> constraints;
    private Problem problem;

    @Before
    public void setUp() {
        variables = new ArrayList<>();
        constraints = new ArrayList<>();
        problem = new Problem(variables, constraints);

        for (int i = 0; i < nb_machine; i++) {
            for (int j = 0; j < nb_job; j++) {
                Variable v = new Variable("M" + i + "_J" + j, 0, 5000); // Example bounds
                variables.add(v);
            }
        }
    }

    @Test
    public void testShopClassic() {
        //Ajout des contraintes de précédence entre les machines
        for (int i = 0; i < nb_machine - 1; i++) {
            for (int j = 0; j < nb_job; j++) {
                Variable v1 = variables.get(i * nb_job + j);
                Variable v2 = variables.get((i+1) * nb_job + j);
                int duration = processing_times[i][j];

                Constraint c = new PrecedenceConstraint(v1, v2, duration);
                problem.addConstraint(c);
            }
        }

        //Ajout des contraintes de précédence entre les jobs
        for (int i = 0; i < nb_machine; i++) {
            for (int j = 0; j < nb_job - 1; j++) {
                Variable v1 = variables.get(i * nb_job + j);
                Variable v2 = variables.get(i * nb_job + j+1);
                int duration = processing_times[i][j];

                Constraint c = new PrecedenceConstraint(v1, v2, duration);
                problem.addConstraint(c);
            }
        };

        problem.solve();
        afficherSoltution();
    }


    @Test
    public void testJobShop() {
//        // Initialize start times for the first machine
//        for (int j = 0; j < nb_job; j++) {
//            Variable v = variables.get(j);
//            v.setLb(j == 0 ? 0 : variables.get(j - 1).getLb() + processing_times[0][j - 1]);
//            v.setUb(v.getLb());
//        }

        //Ajout des contraintes de précédence entre les machines
        for (int i = 0; i < nb_machine - 1; i++) {
            for (int j = 0; j < nb_job; j++) {
                Variable v1 = variables.get(i * nb_job + j);
                Variable v2 = variables.get((i+1) * nb_job + j);
                int duration = processing_times[i][j];

                Constraint c = new PrecedenceConstraint(v1, v2, duration);
                problem.addConstraint(c);
            }
        }

        //Ajout des contraintes disjonctives
        for (int i = 0; i < nb_machine; i++) {
            for (int j = 0; j < nb_job; j++) {
                for (int k = j + 1; k < nb_job; k++) {
                    Variable v1 = variables.get(i * nb_job + j);
                    Variable v2 = variables.get(i * nb_job + k);
                    int duration1 = processing_times[i][j];
                    int duration2 = processing_times[i][k];

                    Constraint c = new DisjunctiveConstraint(v1, v2, duration1, duration2);
                    problem.addConstraint(c);
                }
            }
        }

        problem.solve();
        afficherSoltution();
    }

    private void afficherSoltution() {
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
}

