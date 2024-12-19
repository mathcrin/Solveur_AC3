package org.example;

public class Interval {
    private int lb = 0;
    private int ub = 0;

    public Interval(int lb, int ub) {
        this.lb = lb;
        this.ub = ub;
    }

    public boolean isEmpty() {
        return lb > ub;
    }

    public Interval union(Interval interval) {
        if(isEmpty()){
            return interval;
        }
        if(interval.isEmpty()){
            return this;
        }
        if(interval.equals(this)){
            return this;
        }
       return new Interval(
                Math.min(this.lb, interval.lb),
                Math.max(this.ub, interval.ub)
       );
        //Union des domaines de deux variables
    }

    public boolean equals(Interval interval) {
        return this.lb == interval.lb && this.ub == interval.ub;
    }

    public boolean contains(int value) {
        return value >= lb && value <= ub;
    }

    public String toSring(){
        return String.format("[%d, %d]", lb, ub);
    }

    public Interval intersection  (Interval interval) {
        if(isEmpty() || interval.isEmpty()){
            return new Interval(1, 0); //Si notre interval est vide alors on renvoi un interval vide
        }
        return withMin(interval.ub).withMax(interval.lb);
    }

    public Interval withMin(int min) {
        return new Interval(Math.max(lb, min), ub);
    }

    public Interval withMax(int max) {
        return new Interval(lb, Math.min(ub, max));
    }

    public int getLb() {
        return lb;
    }
    public int getUb() {
        return ub;
    }
    public void setLb(int lb) {
        this.lb = lb;
    }
    public void setUb(int ub) {
        this.ub = ub;
    }
}
