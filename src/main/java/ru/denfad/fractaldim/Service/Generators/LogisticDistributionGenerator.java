package ru.denfad.fractaldim.Service.Generators;

import ru.denfad.fractaldim.Model.Point;

import java.util.ArrayList;
import java.util.List;

public class LogisticDistributionGenerator implements TimeSeriesGenerator{
    @Override
    public List<Point> generateTimeSeries() {
        int count = 500;
        double nu = 0;
        double s = 10* Math.random();
        List<Point> points = new ArrayList<>();

        for(double i = -5; i < 5; i+= (10f / count)) {
            float x = (float)i;
            float y = logisticDistribution(i, nu, s);
            x = (count / 10f)*(x + 5);
            y = y * 100;
            points.add(new Point(x, y));
        }
        return points;
    }

    private static float logisticDistribution(double x, double nu, double s) {
        double a = Math.exp(-(x-nu)/ s);
        float y = (float) (a / (s * Math.pow(1 + a, 2)));
        return y;
    }
}
