public class Rule
{
    private String operand;  // Example: order, customer
    private String operator; // Example =, >=
    private boolean isInt;   // if the compare value is int
    private String comp;     // if String, to compare
    private int value;       // else value
    private int numberOfTrueStatements;    // count of minimum true statements required

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isInt() {
        return isInt;
    }

    public void setInt(boolean anInt) {
        isInt = anInt;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNumberOfTrueStatements() {
        return numberOfTrueStatements;
    }

    public void setNumberOfTrueStatements(int numberOfTrueStatements) {
        this.numberOfTrueStatements = numberOfTrueStatements;
    }

    public Rule(String operand, String operator, boolean isInt, String comp, int value, int numberOfTrueStatements) {
        this.operand = operand;
        this.operator = operator;
        this.isInt = isInt;
        this.comp = comp;
        this.value = value;
        this.numberOfTrueStatements = numberOfTrueStatements;
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
}