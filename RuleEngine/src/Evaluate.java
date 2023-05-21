import java.util.*;

public class Evaluate {
    RulesWithResults rulesWithResults;
    public void start() {
        rulesWithResults = new RulesWithResults();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter \"end\" to end");
        while (true) {
            String in = sc.nextLine().trim();
            if (in.equalsIgnoreCase("end")) break;
            else if (in.substring(0, 2).equalsIgnoreCase("if")) addRule(in);
            else evaluate(in);
        }
    }

    // Function to add a new rule to the hashmaps
    public void addRule(String in) {
        in = in.toLowerCase();
        List<String> parts = List.of(in.split(" ")), operands = new ArrayList<>();
        int numberOfTrueStatements = 0;
        List<Rule> rulesList = new ArrayList<>();
        int pointer = 0;
        // Looking for multiple rules
        while (pointer < parts.size() && !parts.get(pointer).equalsIgnoreCase("then")) {
            numberOfTrueStatements++;
            String operand = parts.get(1 + pointer);
            operands.add(operand);
            String operator = parts.get(2 + pointer);
            boolean isInt = true;
            String comp = "";
            int value = 0;
            // If the operator is "=", get the comparison value from the list
            if (operator.equals("=")) {
                comp = parts.get(3 + pointer);
                // Check if the comparison value is an integer or a string
                if (comp.charAt(0) >= '0' && comp.charAt(0) <= '9') {
                    value = Integer.parseInt(comp);
                    isInt = true;
                } else {
                    isInt = false;
                }
            } else {
                // If the operator is ">", "<", ">=", or "<=", get the comparison value from the list
                value = Integer.parseInt(parts.get(3 + pointer));
                isInt = true;
            }
            // Create a new rule object with the obtained values and add it to the list of rules for the operand
            Rule r = new Rule(operand, operator, isInt, comp, value, 0);
            rulesList.add(r);
            pointer += 4;
        }
        pointer++; // Move past "then" in the list of words
        // Loop through the list of words until "else" is found, adding each word to the "comp_true" string
        String comp_true = "", comp_false = "";
        while (pointer < parts.size() && !parts.get(pointer).equalsIgnoreCase("else")) {
            comp_true += parts.get(pointer) + " ";
            pointer++;
        }
        pointer++; // pointer is on "else"
        while (pointer < parts.size()) {
            comp_false += parts.get(pointer) + " ";
            pointer++;
        }
        Result res = new Result();
        res.setComp_true(comp_true);
        res.setComp_false(comp_false);
        numberOfTrueStatements = parts.contains("or") ? 1 : numberOfTrueStatements;
        for (Rule rule : rulesList)
            rule.setNumberOfTrueStatements(numberOfTrueStatements);
        for (String operand : operands) {
            rulesWithResults.addRule(operand, rulesList);
            rulesWithResults.addResult(operand, res);
        }
        System.out.println("Rule added");
    }

    void evaluate(String in) {
        in = in.toLowerCase();
        int numberOfTrueStatements = 0;
        List<String> parts = List.of(in.split(" "));
        int pointer = 0;
        String originalOperand = parts.get(0);
        // Looking for multiple conditions
        while (pointer < parts.size()) {
            String operand = parts.get(pointer);
            String valueStr = parts.get(2 + pointer);
            int value = 0;
            if (valueStr.charAt(0) >= '0' && valueStr.charAt(0) <= '9') value = Integer.parseInt(valueStr);
            if (!rulesWithResults.containsRule(originalOperand)) {
                System.out.println("Operand not present in rules");
                return;
            }
            List<Rule> ruleList = rulesWithResults.getRule(originalOperand);
            for (Rule r : ruleList) {
                if (r.operand.equals(operand)) {
                    if (r.isInt) {
                        if (r.compare(r.operator, value, r.value)) {
                            numberOfTrueStatements++;
                            if (numberOfTrueStatements >= r.getNumberOfTrueStatements()) {
                                System.out.println(rulesWithResults.getResult(operand).comp_true);
                                return;
                            }
                        }
                    } else {
                        if (r.comp.equalsIgnoreCase(valueStr)) {
                            numberOfTrueStatements++;
                            if (numberOfTrueStatements >= r.getNumberOfTrueStatements()) {
                                System.out.println(rulesWithResults.getResult(operand).comp_true);
                                return;
                            }
                        }
                    }
                }
            }
            pointer += 3;
        }
        // if code has reached till here, then the result is false:
        System.out.println(rulesWithResults.getResult(originalOperand).comp_false);
    }

}
