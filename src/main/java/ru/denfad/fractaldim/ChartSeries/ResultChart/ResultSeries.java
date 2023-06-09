package ru.denfad.fractaldim.ChartSeries.ResultChart;


import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public class ResultSeries extends AbstractSeries {

    private XYChart.Series result = new XYChart.Series();
    private XYChart.Series line = new XYChart.Series();

    public ResultSeries() {
        super();
        result.setName("Функция спектра мощности");
        line.setName("Аппроксимирующая прямая");
        seriesList.add(result);
        seriesList.add(line);
    }

    @Override
    public List<XYChart.Series> getSeries() {
        updateSeries();
        return seriesList;
    }

    @Override
    public void updateSeries() {
        if (dimensionCalculator.getResult().isEmpty()) {
            result.getData().clear();
        }
        try {
            List<Point> list = dimensionCalculator.getResult();
            float maxX = dimensionCalculator.getResult().get(0).getT();
            float minX = dimensionCalculator.getResult().get(0).getT();
            float maxY = dimensionCalculator.getResult().get(0).getX();
            float minY = dimensionCalculator.getResult().get(0).getX();


            result.getData().clear();
            line.getData().clear();
            for (Point p : list) {
                if (p.getT() > maxX) maxX = p.getT();
                if (p.getT() < minX) minX = p.getT();
                if (p.getX() > maxY) maxY = p.getX();
                if (p.getX() < minY) minY = p.getX();
                result.getData().add(new XYChart.Data(p.getT(), p.getX()));
            }
            if (dimensionCalculator.getS() != 0) {
                double s = dimensionCalculator.getS();
                double intercept = dimensionCalculator.getIntercept();
                line.getData().add(new XYChart.Data(minX, minX * s + intercept));
                line.getData().add(new XYChart.Data(maxX, s * maxX + intercept));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public String getXLabel() {
        return "ln(ω)";
    }

    @Override
    public String getYLabel() {
        return "ln(S(ω))";
    }

    @Override
    public String getName() {
        return "Итоговый график";
    }
}
