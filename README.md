# XenoAssignment
Researched about rule engines Like Drool, and implemented this rule engine. <br>
Here we can add different rules with basic semantics, and it wil output when we give the values of keywords

Sample Input:
```
If order >= 100 and customer = VIP then discount = 10 else discount = 5
If temperature > 90 then air_conditioning = on else air_conditioning = off
If consumer > 10 then discount = 25 else discount = 15
Temperature = 95
consumer = 9
order = 100 customer = VIP
end
```
Semantics of input:
<br>
Adding new rules:
```
if <operand> <operator> <value> and/or <operand> <operator> <value> and/or ... then <true value> else <false value>
```
Evaluating exressions:
```
<operand> = <value> <operand> = <value> ...
```
  
Sample Output:
```
air_conditioning = on
discount = 15
discount = 10
```

Compile and run the code using Java Compiler
