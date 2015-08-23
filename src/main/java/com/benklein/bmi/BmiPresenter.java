package com.benklein.bmi;

public class BmiPresenter {

  private final IBmiView view;

  public BmiPresenter(IBmiView view) {
    this.view = view;
  }

  public void onCalculate() {
    double weight = view.getWeight();
    double height = view.getHeight();
    IBmiView.Mode mode = view.getMode();

    BmiCalculation bmiCalculation = null;
    if (mode.equals(IBmiView.Mode.IMPERIAL)) {
       bmiCalculation = BmiCalculation.fromImperialLbsInches(weight, height);
    } else {
       bmiCalculation = BmiCalculation.fromMetricKgMeters(weight, height);
    }

    view.notifyBmi(bmiCalculation.getBMI());

  }

}
