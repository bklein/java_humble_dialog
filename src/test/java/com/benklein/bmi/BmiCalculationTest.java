package com.benklein.bmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BmiCalculationTest {

  @Test
  public void correctBmiImperial() {
    BmiCalculation bmi = BmiCalculation.fromImperialLbsInches(150.0, 64.0);
    assertEquals(25.74, bmi.getBMI(), 0.01);
  }

  @Test
  public void correctBmiMetric() {
    BmiCalculation bmi = BmiCalculation.fromMetricKgMeters(125.0, 2.35);
    assertEquals(22.63, bmi.getBMI(), 0.01);
  }

}
