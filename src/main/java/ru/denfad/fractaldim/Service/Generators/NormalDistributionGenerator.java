package ru.denfad.fractaldim.Service.Generators;

import ru.denfad.fractaldim.Model.Point;

import java.util.ArrayList;
import java.util.List;

public class NormalDistributionGenerator implements TimeSeriesGenerator {

    @Override
    public List<Point> generateTimeSeries() {
        int count = 100;
        double nu = 0;
        double sigma = 3* Math.random() + 0.1;
        List<Point> points = new ArrayList<>();

        for(double i = -5; i < 5; i+= (10f / count)) {
            float x = (float)i;
            float y = normalDistribution(i, nu, sigma);
            x = (count / 10f)*(x + 5);
            y = y * 100;
            points.add(new Point(x, y));
        }
        return points;
    }

    private static float normalDistribution(double x, double nu, double sigma) {
        double a = -0.5 * Math.pow(((x - nu) / sigma),2);
        float y = (float)(1 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(a));
        return y;
    }
}
