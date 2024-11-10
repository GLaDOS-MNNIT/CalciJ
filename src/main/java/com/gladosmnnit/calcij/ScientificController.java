package com.gladosmnnit.calcij;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.gladosmnnit.calcij.utils.ScientificCalculate;

import java.io.IOException;

public class ScientificController {

    @FXML
    private Label expressionLabel; // To display the current input expression
    @FXML
    private Label answerLabel;    // To display the result

    private boolean isFunctionMode = false;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        expressionLabel.setText(""); // Start with an empty expression
        answerLabel.setText("0");     // Start with no result
    }

    @FXML
    private void clickHandle(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        switch (buttonText) {
            case "AC":
                expressionLabel.setText("");
                answerLabel.setText("");
                isFunctionMode = false;
                break;

            case "sin":
            case "cos":
            case "tan":
            case "log":
            case "ln":
            case "√":
            case "x^y":
            case "10^x":
                expressionLabel.setText(expressionLabel.getText() + buttonText + "(");
                isFunctionMode = true;
                break;

            case "e":
            case "π":
                expressionLabel.setText(expressionLabel.getText() + buttonText);
                break;

            case ")":
                if (isFunctionMode) {
                    expressionLabel.setText(expressionLabel.getText() + ")");
                    isFunctionMode = false;
                }
                break;

            case "+":
            case "-":
            case "X":
            case "/":
            case "%":
            case "x^2":
            case "x!":
                expressionLabel.setText(expressionLabel.getText() + " " + buttonText + " ");
                break;

            case "=":
                evaluateExpression();
                break;

            case "Rad":
                // Toggle radians/degrees if needed
                break;

            default:
                // For digits and decimal point
                expressionLabel.setText(expressionLabel.getText() + buttonText);
                break;
        }
    }

    private void evaluateExpression() {
        String expression = expressionLabel.getText();
        // Expression evaluation logic goes here
        // For now, let's just set a placeholder result
        try {
            // Example of evaluating the expression, replace with actual logic
            double result = ScientificCalculate.evaluateInfix(expression);
            answerLabel.setText(String.valueOf(result));
        } catch (Exception e) {
            answerLabel.setText("Error");
        }

    }

}
