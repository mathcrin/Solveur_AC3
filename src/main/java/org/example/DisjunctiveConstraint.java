package org.example;

import java.util.ArrayList;
import java.util.List;

//Contrainte disjonctive >= ou <=
public class DisjunctiveConstraint extends Constraint{
    private Variable v1;
    private Variable v2;
    private int duration1;
    private int duration2;

    public DisjunctiveConstraint(Variable v1, Variable v2, int duration1, int duration2) {
        this.v1 = v1;
        this.v2 = v2;
        this.duration1 = duration1;
        this.duration2 = duration2;
    }

    @Override
    public List<Variable> filter() {
        List<Variable> modifiedVariables = new ArrayList<>();
        if (v1.getLb() + duration1 > v2.getUb()) {
            v2.setLb(v1.getLb() + duration1);
            modifiedVariables.add(v2);
        }
        if (v2.getLb() + duration2 > v1.getUb()) {
            v1.setLb(v2.getLb() + duration2);
            modifiedVariables.add(v1);
        }
        return modifiedVariables;
    }

    @Override
    public boolean check(int... values) {
        return (values[0] + duration1 <= values[1]) || (values[1] + duration2 <= values[0]);
    }

    @Override
    public List<Variable> getVariables() {
        return List.of(v1, v2);
    }

    public String toString() {
        return "(" + v1.getName() + " + " + duration1 + " <= " + v2.getName() + ") || (" + v2.getName() + " + " + duration2 + " <= " + v1.getName() + ")";
    }

}
