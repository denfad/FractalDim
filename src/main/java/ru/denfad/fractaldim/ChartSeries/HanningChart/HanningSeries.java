package ru.denfad.fractaldim.ChartSeries.HanningChart;

import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public class HanningSeries extends AbstractSeries {

    private XYChart.Series series;

    public HanningSeries() {
        super();
        series = new XYChart.Series();
        series.setName("Окно Хэннинга");
        seriesList.add(series);
    }

    @Override
    public List<XYChart.Series> getSeries() {
        updateSeries();
        return seriesList;
    }

    @Override
    public void updateSeries() {
        int sSize = series.getData().size();
        int pSize = dimensionCalculator.getHanning().size();
        if (dimensionCalculator.getResult().isEmpty()) {
            series.getData().clear();
            sSize = 0;
        }
        try {
            List<Point> list = dimensionCalculator.getHanning().subList(sSize, pSize);
            for (Point p : list) {
                series.getData().add(new XYChart.Data(p.getT(), p.getX()));
            }
        } catch (Exception e) {
        }

    }

    @Override
    public String getXLabel() {
        return "ω";
    }

    @Override
    public String getYLabel() {
        return "x";
    }

    @Override
    public String getName() {
        return "График окна хеннинга";
    }
}
