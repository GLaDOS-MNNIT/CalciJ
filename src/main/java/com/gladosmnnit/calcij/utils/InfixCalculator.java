import java.util.*;

public class InfixCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String infixExpression = scanner.nextLine();
        try {
            double result = evaluateInfix(infixExpression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Invalid expression: " + e.getMessage());
        }
    }

    // Method to evaluate an infix expression
    public static double evaluateInfix(String expression) throws Exception {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    // Method to convert infix expression to postfix
    public static String infixToPostfix(String expression) throws Exception {
        StringBuilder output = new StringBuilder();
        Stack<String> operators = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/% ", true);
        boolean expectNegativeNumber = true;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0)) || token.matches("\\d+\\.\\d*")) {  // Check if token is a number
                output.append(token).append(" ");
                expectNegativeNumber = false;  // After a number, don't expect a negative sign for a number
            } else if (token.equals("(")) {
                operators.push(token);
                expectNegativeNumber = true;  // After '(', a negative number can follow
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.append(operators.pop()).append(" ");
                }
                operators.pop(); // Remove the '('
                expectNegativeNumber = false;  // After ')', don't expect a negative sign for a number
            } else if (isOperator(token.charAt(0))) {
                // Handle negative numbers right after an operator or at the start of the expression
                if (token.equals("-") && expectNegativeNumber) {
                    output.append("-");  // Add '-' without appending space (indicating it's part of a number)
                    continue;
                }
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    output.append(operators.pop()).append(" ");
                }
                operators.push(token);
                expectNegativeNumber = true;  // After an operator, a negative number can follow
            } else {
                throw new Exception("Invalid character encountered: " + token);
            }
        }

        while (!operators.isEmpty()) {
            String op = operators.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new Exception("Mismatched parentheses");
            }
            output.append(op).append(" ");
        }

        return output.toString();
    }

    // Method to evaluate a postfix expression
    public static double evaluatePostfix(String expression) throws Exception {
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.matches("\\d+\\.\\d*|-\\d+\\.\\d*|\\d+|-\\d+")) {  // Check if token is a number (with optional negative sign)
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/':
                        if (b == 0) throw new Exception("Division by zero");
                        stack.push(a / b);
                        break;
                    case '%': stack.push(a % b); break;
                    default: throw new Exception("Invalid operator: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new Exception("Invalid expression");
        }

        return stack.pop();
    }

    // Method to check if a character is an operator
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
    }

    //  Method to determine the precedence of operators
    public static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/") || op.equals("%")) return 2;
        return -1;
    }


}
