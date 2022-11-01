package functions;

import java.util.Arrays;
import java.util.Map;

public class AnyFunctions{
    private String str;

    public AnyFunctions(String str) {
        this.str = str;
    }

    public char defineArg(){
        char[] c = str.toCharArray();
        int i = 0;
        while(c[i] == ' '){
            i++;
        }
        return c[i];
    }

    public double compute(double arg) {
        Interpreter interpreter = new Interpreter();
        char[] c = str.toCharArray();
        int i = 0;
        while(c[i] != '='){
            i++;
        }
        char[] c1 = Arrays.copyOfRange(c, i+1, c.length);
        return interpreter.compute(new String(c1), arg);
    }

}
