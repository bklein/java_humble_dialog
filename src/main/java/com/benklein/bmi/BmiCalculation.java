package com.benklein.bmi;

public class BmiCalculation {

  public static BmiCalculation fromMetricKgMeters(double weightKg, double heightMeters) {
    final double bmi = weightKg  / (heightMeters * heightMeters);
    return new BmiCalculation(bmi);
  }

  public static BmiCalculation fromImperialLbsInches(double weightLbs, double heightInches) {
    final double bmi = (weightLbs * 703.0) / (heightInches * heightInches);
    return new BmiCalculation(bmi);
  }

  public double getBMI() {
    return bmi;
  }

  private double bmi;

  private BmiCalculation(double bmi) {
    this.bmi = bmi;
  }

}
