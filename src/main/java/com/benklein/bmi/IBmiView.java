package com.benklein.bmi;

public interface IBmiView {

  public double getWeight();
  public double getHeight();

  public enum Mode {
    IMPERIAL,
    METRIC
  }

  public Mode getMode();

  public void notifyBmi(double bmi);

}
