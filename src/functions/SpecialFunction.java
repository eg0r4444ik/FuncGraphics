package functions;

import java.util.Map;

public class SpecialFunction implements IFunction {

    @Override
    public double compute(double y, Map<String, Double> params) {
        return  params.get("A")*y*y*y + params.get("B")*y*y + params.get("C")*y + params.get("D");
    }
}
