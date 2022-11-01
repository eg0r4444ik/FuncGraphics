package functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class Interpreter {
    private Map<Character, Integer> priority;

    public Interpreter() {
        priority = new HashMap<>();
        priority.put('+', 1);
        priority.put('-', 1);
        priority.put('*', 2);
        priority.put('/',2);
        priority.put('^', 3);
    }

    public double compute(String str, double arg){
        double result = 0;
        char[] c = str.toCharArray();
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();

        for(int i = 0; i < c.length; i++) {
            if(c[i] == ' '){
                continue;
            }else if(c[i] == 'e'){
                numbers.add(Math.E);
            }else if(i+2 < c.length && c[i] == 's' && c[i+1] == 'i' && c[i+2] == 'n'){
                String s = "";
                i += 4;
                while(i < c.length && c[i] != ')'){
                    s += c[i];
                    i++;
                }
                numbers.add(Math.sin(compute(s, arg)));
            }else if(i+2 < c.length && c[i] == 'c' && c[i+1] == 'o' && c[i+2] == 's'){
                String s = "";
                i += 4;
                while(i < c.length && c[i] != ')'){
                    s += c[i];
                    i++;
                }
                numbers.add(Math.cos(compute(s, arg)));
            }else if(c[i] == 'x' || c[i] == 'y'){
                numbers.add(arg);
            }else if(Character.isDigit(c[i])) {
                String num = "";
                while (i < c.length && Character.isDigit(c[i])) {
                    num += c[i];
                    i++;
                }
                i--;
                double n = Double.parseDouble(num);
                numbers.add(n);
            }else if(c[i] == '('){
                String s = "";
                i++;
                while(i < c.length && c[i] != ')'){
                    s += c[i];
                    i++;
                }
                numbers.add(compute(s, arg));
            }else{
                if(operations.empty()){
                    operations.add(c[i]);
                } else{
                    if(priority.get(c[i]) > priority.get(operations.peek())){
                        operations.add(c[i]);
                    } else{
                        numbers.add(doOperation(numbers.pop(), numbers.pop(), operations.pop()));
                        operations.add(c[i]);
                    }
                }
            }

        }

        while(!operations.empty()){
            numbers.add(doOperation(numbers.pop(), numbers.pop(), operations.pop()));
        }
        result = numbers.pop();

        return result;
    }

    private double doOperation(double num1, double num2, char operation){
        double num = 0;
        if(operation == '+'){
            num = num1 + num2;
        }
        if(operation == '-'){
            num = num2 - num1;
        }
        if(operation == '*'){
            num = num1 * num2;
        }
        if(operation == '/'){
            num = num2/num1;
        }
        if(operation == '^'){
            num = Math.pow(num2, num1);
        }

        return num;
    }
}
