package org.example;

import java.util.ArrayList;
import java.util.List;

//contrainte qui dit qu'une tâche doit être effectuée avant une autre
public class PrecedenceConstraint extends Constraint{
    private final Variable v1;
    private final Variable v2;
    private int duration;

    public PrecedenceConstraint(Variable v1, Variable v2, int duration) {
        this.v1 = v1;
        this.v2 = v2;
        this.duration = duration;
    }

    @Override
    public List<Variable> filter() {
        List<Variable> modifiedVariables = new ArrayList<>();
        if (v2.getLb() < v1.getLb() + duration) {
            v2.setLb(v1.getLb() + duration);
            modifiedVariables.add(v2);
        }
        return modifiedVariables;
    }

    @Override
    public boolean check(int... values) {
        return values[0] + duration <= values[1];
    }

    @Override
    public List<Variable> getVariables() {
        return List.of(v1, v2);
    }

    public String toString() {
        return v1.getName() + " + " + duration + " <= " + v2.getName();
    }
}
