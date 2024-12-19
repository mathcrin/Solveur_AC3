package org.example;

import java.util.ArrayList;
import java.util.List;

public class Variable {
    private String name;
    private  Interval domain;
    private List<Constraint> constraints;

    public Variable(String name, int lb, int ub) {
        this.name = name;
        this.domain = new Interval(lb, ub);
        this.constraints = new ArrayList<>();
    }

    public Variable(String name, Interval domain) {
        this.name = name;
        this.domain = domain;
        this.constraints = new ArrayList<>();
    }

    public void register(Constraint c) {
       constraints.add(c);
    }

    public String getName() {
        return name;
    }

    public int getLb() {
        return domain.getLb();
    }

    public int getUb() {
        return domain.getUb();
    }

    public void setLb(int lb) {
        domain.setLb(lb);
    }

    public void setUb(int ub) {
        domain.setUb(ub);
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }
}
