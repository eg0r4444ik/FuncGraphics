package functions;

import java.util.Map;

public class SinCosFunction implements IFunction{
    @Override
    public double compute(double x, Map<String, Double> params) {
        return (params.get("A1")*Math.sin(params.get("W1")*x + params.get("F1"))*(params.get("A2")*Math.cos(params.get("W2")*x + params.get("F2"))+params.get("C2")) + params.get("C1"));
    }
}
