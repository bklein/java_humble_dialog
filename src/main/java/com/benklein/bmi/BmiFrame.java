package com.benklein.bmi;

import java.util.EnumMap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class BmiFrame
  extends JFrame {

  final IBmiView bmiView;
  private final JPanel bmiPanel;

  private JTextField weightTextField;
  private JTextField heightTextField;
  private JLabel weightLabel;
  private JLabel heightLabel;
  private JButton calculateButton;

  private BmiPresenter bmiPresenter;

  private static String MODE_IMPERIAL = "IMPERIAL";
  private static String MODE_METRIC = "METRIC";

  private static EnumMap<IBmiView.Mode,String> weightLabelTexts =
    initWeightLabelTexts();
  private static EnumMap<IBmiView.Mode,String> heightLabelTexts =
    initHeightLabelTexts();

  private static EnumMap<IBmiView.Mode, String> initWeightLabelTexts() {
    EnumMap<IBmiView.Mode, String> map = new EnumMap<IBmiView.Mode, String>(IBmiView.Mode.class);
    map.put(IBmiView.Mode.IMPERIAL, "Weight (lbs)");
    map.put(IBmiView.Mode.METRIC,   "Weight (kgs)");
    return map;
  }

  private static EnumMap<IBmiView.Mode, String> initHeightLabelTexts() {
    EnumMap<IBmiView.Mode, String> map = new EnumMap<IBmiView.Mode, String>(IBmiView.Mode.class);
    map.put(IBmiView.Mode.IMPERIAL, "Height (inches)");
    map.put(IBmiView.Mode.METRIC,   "Height (meters)");
    return map;
  }

  private IBmiView.Mode currentMode;

  public BmiFrame() {
    super("BMI");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    currentMode = IBmiView.Mode.IMPERIAL;

    bmiPanel = createBmiPanel();

    setContentPane(bmiPanel);
    pack();
    setVisible(true);

    bmiView = new IBmiView() {
      @Override
      public double getWeight() {
        return getInputWeight();
      }

      @Override
      public double getHeight() {
        return getInputHeight();
      }

      @Override
      public Mode getMode() {
        return getInputMode();
      }

      @Override
      public void notifyBmi(double bmi) {
        onNotifyBmi(bmi);
      }
    };

    bmiPresenter = new BmiPresenter(bmiView);
  }

  public void onNotifyBmi(double bmi) {
    JOptionPane.showMessageDialog(this, "BMI is " + bmi);
  }

  private double getInputHeight() {
    final double height = Double.parseDouble(heightTextField.getText());
    return height;
  }

  private double getInputWeight() {
    final double weight = Double.parseDouble(weightTextField.getText());
    return weight;
  }

  private IBmiView.Mode getInputMode() {
    return currentMode;
  }

  JPanel createBmiPanel() {
    calculateButton = new JButton("calculate");
    calculateButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        bmiPresenter.onCalculate();
      }
    });

    JPanel containerPanel = new JPanel(new BorderLayout());

    JPanel topPanel = new JPanel(new GridLayout(1,1));
    topPanel.add(new JLabel("Calculate BMI"));
    containerPanel.add(topPanel, BorderLayout.PAGE_START);

    heightTextField = new JTextField(20);
    heightTextField.setText("68");
    weightTextField = new JTextField(20);
    weightTextField.setText("150");
    DocumentListener documentListener = new DocumentListener() {
      @Override
      public void changedUpdate(DocumentEvent e) {
        validateDouble(heightTextField);
        validateDouble(weightTextField);
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        validateDouble(heightTextField);
        validateDouble(weightTextField);
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        validateDouble(heightTextField);
        validateDouble(weightTextField);
      }

    };
    heightTextField.getDocument().addDocumentListener(documentListener);
    weightTextField.getDocument().addDocumentListener(documentListener);
    validateDouble(heightTextField);
    validateDouble(weightTextField);

    JPanel centerPanel = new JPanel(new GridLayout(3,2));
    weightLabel = new JLabel(weightLabelTexts.get(currentMode));
    centerPanel.add(weightLabel);
    centerPanel.add(weightTextField);
    heightLabel = new JLabel(heightLabelTexts.get(currentMode));
    centerPanel.add(heightLabel);
    centerPanel.add(heightTextField);

    ActionListener buttonGroupListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        updateCurrentMode(e.getActionCommand());
      }
    };
    JRadioButton imperialButton = new JRadioButton("Imperial");
    imperialButton.setActionCommand(MODE_IMPERIAL);
    imperialButton.setSelected(true);
    centerPanel.add(imperialButton);
    JRadioButton metricButton = new JRadioButton("Metric");
    metricButton.setActionCommand(MODE_METRIC);
    imperialButton.addActionListener(buttonGroupListener);
    metricButton.addActionListener(buttonGroupListener);
    centerPanel.add(metricButton);
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(imperialButton);
    buttonGroup.add(metricButton);

    containerPanel.add(centerPanel, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new GridLayout(1,1));
    bottomPanel.add(calculateButton);

    containerPanel.add(bottomPanel, BorderLayout.PAGE_END);

    containerPanel.setOpaque(true);
    return containerPanel;
  }

  private void updateCurrentMode(String mode) {
    if (mode == MODE_IMPERIAL) {
      currentMode = IBmiView.Mode.IMPERIAL;
    } else {
      currentMode = IBmiView.Mode.METRIC;
    }
    updateLabels();
  }

  private void updateLabels() {
    weightLabel.setText(weightLabelTexts.get(currentMode));
    heightLabel.setText(heightLabelTexts.get(currentMode));
  }

  private void validateDouble(JTextField textField) {
    boolean parseable = true;
    try {
      Double.parseDouble(textField.getText());
    } catch (NumberFormatException e) {
      parseable = false;
    }
    if (parseable) {
      textField.setBackground(Color.GREEN);
      calculateButton.setEnabled(true);
    } else {
      textField.setBackground(Color.RED);
      calculateButton.setEnabled(false);
    }
  }
}

