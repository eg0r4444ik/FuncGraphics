package functions;

import java.util.Map;

public class SinEFunction implements IFunction{
    @Override
    public double compute(double x, Map<String, Double> params) {
        return (params.get("A")*Math.sin(params.get("W")*x + params.get("F"))*(Math.exp((-x/params.get("T"))))+params.get("C"));
    }
}
