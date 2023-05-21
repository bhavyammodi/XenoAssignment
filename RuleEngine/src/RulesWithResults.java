import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulesWithResults {
    // Define two hashmaps to store rules and their corresponding results
    Map<String, List<Rule>> rules;
    Map<String, Result> results;

    public RulesWithResults() {
        this.rules = new HashMap<>();
        this.results = new HashMap<>();
    }

    public void addRule(String key, List<Rule> ruleList) {
        rules.put(key, ruleList);
    }

    public void addResult(String key, Result result) {
        results.put(key, result);
    }
    boolean containsRule(String operand)
    {
        return rules.containsKey(operand);
    }
    public List<Rule> getRule(String operand)
    {
        return rules.get(operand);
    }
    public Result getResult(String operand)
    {
        return results.get(operand);
    }
}
