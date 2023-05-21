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


    // Sample Input:
    /*
        If order >= 100 and customer = VIP then discount = 10 else discount = 5
        If temperature > 90 then air_conditioning = on else air_conditioning = off
           If consumer > 10 then discount = 25 else discount = 15
        If a < 100 and b = vip and c = vvip then abc = nice else abc = good
        Temperature = 95
        consumer = 9
        order = 100 customer = VIP
        a = 99 b = vip c = vvip
        end
     */
    // Sample Output:
    /*
        air_conditioning = on
        discount = 15
        discount = 10
        abc = nice
     */
    public static void main(String[] args) {
        Evaluate ob = new Evaluate();
        ob.start();
    }
}