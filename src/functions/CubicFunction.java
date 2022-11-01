package functions;

import java.util.Map;

public class CubicFunction implements IFunction{

    @Override
    public double compute(double x, Map<String, Double> params) {
        return (x*x*x*params.get("A") + x*x*params.get("B") + x*params.get("C") + params.get("D"));
    }
}
