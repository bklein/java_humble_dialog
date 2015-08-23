package com.benklein.bmi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class BmiPresenterTest {

  @Test
  public void onCalculateImperial() {
    final double weight = 150;
    final double height = 64;

    IBmiView mockView = mock(IBmiView.class);

    when(mockView.getWeight()).thenReturn(weight);
    when(mockView.getHeight()).thenReturn(height);
    when(mockView.getMode()).thenReturn(IBmiView.Mode.IMPERIAL);

    final BmiCalculation bmiCalculation = BmiCalculation.fromImperialLbsInches(weight, height);

    BmiPresenter bmiPresenter = new BmiPresenter(mockView);
    final double expectedBmi = bmiCalculation.getBMI();
    bmiPresenter.onCalculate();

    verify(mockView).notifyBmi(expectedBmi);
  }

  @Test
  public void onCalculateMetric() {
    final double weight = 150;
    final double height = 64;

    IBmiView mockView = mock(IBmiView.class);

    when(mockView.getWeight()).thenReturn(weight);
    when(mockView.getHeight()).thenReturn(height);
    when(mockView.getMode()).thenReturn(IBmiView.Mode.METRIC);

    final BmiCalculation bmiCalculation = BmiCalculation.fromMetricKgMeters(weight, height);

    BmiPresenter bmiPresenter = new BmiPresenter(mockView);
    final double expectedBmi = bmiCalculation.getBMI();
    bmiPresenter.onCalculate();

    verify(mockView).notifyBmi(expectedBmi);
  }


}
