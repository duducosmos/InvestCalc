package com.jds.investcalc.math;

import java.util.ArrayList;

/**
 * Created by eduardo on 22/12/14.
 */
public class CompoundInterest {
    private float jurosAA, inflacaoAA, jurosAM, inflacaoAM;
    private float initialApplication;
    private Integer totalPeriod;

    private boolean repeatedApplication;
    private ArrayList<Double> monthlyApplication = new ArrayList<Double>();
    private ArrayList<Double> interestToBeSum = new ArrayList<Double>();

    public CompoundInterest(float interest, float inflation,
                            float initialApplication,
                            Integer totalPeriod,
                            boolean repeatedApplication,
                            ArrayList valueAppliedMonthly) {
        this.jurosAA = interest;
        this.inflacaoAA = inflation;
        this.jurosAM = interest / 12f;
        this.inflacaoAM = inflation / 12f;
        this.initialApplication = initialApplication;
        this.totalPeriod = totalPeriod;
        this.repeatedApplication = repeatedApplication;
        this.monthlyApplication = valueAppliedMonthly;

        setMonthlyApplication();
    }

    private void setMonthlyApplication() {

        if (this.isRepeatedApplication()) {
            if (this.monthlyApplication.size() == 1) {
                double sameValue = this.monthlyApplication.get(0);
                for (Integer i = 1; i < this.totalPeriod; i++) {
                    this.monthlyApplication.add(sameValue);
                }
            } else if (this.monthlyApplication.size() == 0) {
                this.repeatedApplication = false;
                return;
            }


        }


    }

    public Float accumulatedNominal() {


        if (this.isRepeatedApplication()) {
            float total = 0;
            double interested = 0;
            for (int i = this.totalPeriod; i >= 0; i--) {
                if (i == this.totalPeriod) {
                    interested = this.initialApplication *
                            Math.pow(1.0d + ((double) this.jurosAM),
                                    (double) i + 1);

                } else {
                    interested = this.monthlyApplication.get(this.totalPeriod - 1 - i) *
                            Math.pow(1.0d + ((double) this.jurosAM),
                                    (double) i + 1);
                }
                total += (float) interested;
                this.interestToBeSum.add(interested);
            }
            return total;

        }

        return this.initialApplication * ((float) Math.pow(1.0d + ((double) this.jurosAM),
                (double) this.totalPeriod));
    }

    public Float vested() {
        Float asomar = 0.0f;

        for (double valor : this.monthlyApplication) {
            asomar += (float) valor;
        }
        return this.initialApplication + asomar;
    }

    public Float accumulatedReal() {
        float r = Fisher.realTax(this.inflacaoAM, this.jurosAM);
        if (this.isRepeatedApplication()) {
            float total = 0;
            double interested = 0;
            for (int i = this.totalPeriod; i >= 0; i--) {
                if (i == this.totalPeriod) {
                    interested = this.initialApplication *
                            Math.pow(1.0d + (double) r,
                                    (double) i + 1);

                } else {
                    interested = this.monthlyApplication.get(this.totalPeriod - 1 - i) *
                            Math.pow(1.0d + ((double) r),
                                    (double) i + 1);
                }
                total += (float) interested;
                this.interestToBeSum.add(interested);
            }
            return total;
        }


        return this.initialApplication * ((float) Math.pow(1.0d + ((double) r),
                (double) this.totalPeriod));
    }


    public boolean isRepeatedApplication() {
        return repeatedApplication;
    }

    public void setRepeatedApplication(boolean repeatedApplication) {
        this.repeatedApplication = repeatedApplication;
    }
}
