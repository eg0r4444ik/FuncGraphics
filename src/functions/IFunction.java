package functions;

import java.util.Map;

public interface IFunction {
    double compute(double x, Map<String, Double> params);
}
