package com.gladosmnnit.calcij;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.gladosmnnit.calcij.utils.InfixCalculator;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorController {

    @FXML
    private Label expressionLabel;  // Displays the current expression
    @FXML
    private Label answerLabel;      // Displays the result

    private String expression = "";  // Holds the current expression

    // Method to handle numeric and operator button clicks
    @FXML
    public void handleButtonClick(MouseEvent event) {
        Button sourceButton = (Button) event.getSource();
        String buttonText = sourceButton.getText();

        // Update the expression with the button's text
        expression += buttonText;
        expressionLabel.setText(expression);
    }

    // Method to clear the expression and result
    @FXML
    public void handleClear() {
        expression = "";
        expressionLabel.setText("0");
        answerLabel.setText("0");
    }

    // Method to handle the "=" button click, which evaluates the expression
    @FXML
    public void handleEquals() {
        // Check if the expression ends with an operator, remove it
        if (expression.endsWith("+") || expression.endsWith("-") || expression.endsWith("x") || expression.endsWith("/") || expression.endsWith("%")) {
            expression = expression.substring(0, expression.length() - 1);
        }
        expressionLabel.setText("0");
        // Evaluate the expression and display the result
        Double result=0.0;
        try{
            result = InfixCalculator.evaluateInfix(expression);
            answerLabel.setText(""+result);
            expression=""+result;
        }
        catch (Exception e){
            answerLabel.setText("error");
        }
    }

    @FXML
    public void quit(){
        System.exit(0);
    }

    @FXML
    public void changeToScientific(javafx.event.ActionEvent event){
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("science.fxml"));
            Parent newRoot = loader.load();

            // Create a new scene with the loaded FXML
            Scene newScene = new Scene(newRoot);

            // Get the current stage from the event source (Button click)
            Stage currentStage = (Stage) expressionLabel.getScene().getWindow();

            // Set the new scene on the current stage
            currentStage.setScene(newScene);
            currentStage.show();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}