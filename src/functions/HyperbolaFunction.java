package functions;

import java.util.Map;

public class HyperbolaFunction implements IFunction{
    @Override
    public double compute(double x, Map<String, Double> params) {
        return (params.get("A") / (params.get("B")*x+params.get("D")) + params.get("C"));
    }
}
