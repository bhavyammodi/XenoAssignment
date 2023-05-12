import java.util.*;

//
//    let's say our input is without the symbols
//
//    If order >= 100 and customer = VIP then discount = 10
//    If temperature > 90 then air_conditioning = on else air_conditioning = off
//    If consumer > 10 then discount = 15 else discount = 10
//
//    Temperature = 95
//    consumer = 9
//    order = 100 customer = VIP
//
public class RuleEngine {
    // Define two hashmaps to store rules and their corresponding results
    Map<String, List<rule>> rules;
    Map<String, result> results;

    public RuleEngine() {
        this.rules = new HashMap<>();
        this.results = new HashMap<>();
    }

    // Sample Input:
    /*
        If order >= 100 and customer = VIP then discount = 10 else discount = 5
        If temperature > 90 then air_conditioning = on else air_conditioning = off
        If consumer > 10 then discount = 25 else discount = 15
        Temperature = 95
        consumer = 9
        order = 100 customer = VIP
        end
     */
    // Sample Output:
    /*
        air_conditioning = on
        discount = 15
        discount = 10
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RuleEngine ob = new RuleEngine();
        System.out.println("Enter \"end\" to end");
        while (true) {
            String in = sc.nextLine().trim();
            if (in.equalsIgnoreCase("end")) break;
            else if (in.substring(0, 2).equalsIgnoreCase("if")) ob.addRule(in);
            else ob.evaluate(in);
        }
    }

    // Function to add a new rule to the hashmaps
    public void addRule(String in) {
        in = in.toLowerCase();
        List<String> parts = List.of(in.split(" "));
        boolean isOr = parts.contains("or");
        List<rule> rulesList = new ArrayList<>();
        int pointer = 0;
        // Looking for multiple rules
        while (pointer < parts.size() && !parts.get(pointer).equalsIgnoreCase("then")) {
            String operand = parts.get(1 + pointer);
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
            rule r = new rule(operand, operator, isInt, comp, value, isOr);
            rulesList.add(r);
            pointer += 4;
        }
        pointer++; // Move past "then" in the list of words
        result res = new result();
        // Loop through the list of words until "else" is found, adding each word to the "comp_true" string
        while (pointer < parts.size() && !parts.get(pointer).equalsIgnoreCase("else")) {
            res.comp_true += parts.get(pointer) + " ";
            pointer++;
        }
        pointer++; // pointer is on "else"
        while (pointer < parts.size()) {
            res.comp_false += parts.get(pointer) + " ";
            pointer++;
        }
        rules.put(parts.get(1), rulesList);
        results.put(parts.get(1), res);
        System.out.println("Rule added");
    }

    public boolean compare(String operator, int value1, int value2) {
        switch (operator) {
            case "=":
                return value1 == value2;
            case ">":
                return value1 > value2;
            case ">=":
                return value1 >= value2;
            case "<":
                return value1 < value2;
            case "<=":
                return value1 <= value2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    void evaluate(String in) {
        in = in.toLowerCase();
        boolean isOr = false;
        List<String> parts = List.of(in.split(" "));
        int pointer = 0;
        String originalOperand = parts.get(0);
        // Looking for multiple conditions
        while (pointer < parts.size()) {
            String operand = parts.get(pointer);
            String valueStr = parts.get(2 + pointer);
            int value = 0;
            if (valueStr.charAt(0) >= '0' && valueStr.charAt(0) <= '9') value = Integer.parseInt(valueStr);
            if (!rules.containsKey(originalOperand)) {
                System.out.println("Operand not present in rules");
                return;
            }
            List<rule> ruleList = rules.get(originalOperand);
            for (rule r : ruleList) {
                if (r.operand.equals(operand)) {
                    isOr = r.isOr;
                    if (r.isInt) {
                        if (compare(r.operator, value, r.value)) {
                            if (isOr) {
                                // if rules have or, and any true, we will print true value
                                System.out.println(results.get(operand).comp_true);
                                return;
                            }
                        } else {
                            if (!isOr) {
                                // if rules have and, and any false, we will print true value
                                System.out.println(results.get(operand).comp_false);
                                return;
                            }
                        }
                    } else {
                        if (r.comp.equalsIgnoreCase(valueStr)) {
                            if (isOr) {
                                // if rules have or, and any true, we will print true value
                                System.out.println(results.get(operand).comp_true);
                                return;
                            }
                        } else {
                            if (!isOr) {
                                // if rules have and, and any false, we will print true value
                                System.out.println(results.get(operand).comp_false);
                                return;
                            }
                        }
                    }
                }
            }
            pointer += 3;
        }
        // if code has reached till here, then we have 2 options:
        // 1st, rules had OR, then we have not found any true
        if (isOr) System.out.println(results.get(originalOperand).comp_false);
            // 2nd, rules had AND, then we have not found any false
        else System.out.println(results.get(originalOperand).comp_true);
    }

    public class rule {
        String operand; // Example: order, customer
        String operator; // Example =, >=
        boolean isInt; // if the compare value is int
        String comp; // if String, to compare
        int value; // else value
        boolean isOr; // boolean to check if it's an OR statement

        public rule(String operand, String operator, boolean isInt, String comp, int value, boolean isOr) {
            this.operand = operand;
            this.operator = operator;
            this.isInt = isInt;
            this.comp = comp;
            this.value = value;
            this.isOr = isOr;
        }
    }

    public class result {
        String comp_true; // if evaluate is true
        String comp_false; // if evaluate is false

        public result() {
            this.comp_true = "";
            this.comp_false = "";
        }
    }
}