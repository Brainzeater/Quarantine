package quarantine.modules;

import java.util.LinkedList;

public class ValidValuesModule {
    public String handleString(String string, LinkedList<Integer> numbers) {
        String str = "";
        while(!numbers.isEmpty()){
            str+=string.charAt(numbers.pollFirst());
        }
        return str;
    }
}
