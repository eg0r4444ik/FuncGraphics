package functions;

import java.util.Map;

public class ExpFunction implements IFunction{
    @Override
    public double compute(double x, Map<String, Double> params) {
        return (params.get("A")* 1/(1+Math.exp(-x)) + params.get("C"));
    }
}
