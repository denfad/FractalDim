package ru.denfad.fractaldim.ChartSeries.SpectrumChart;

import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public class SpectrumSeries extends AbstractSeries {

    private XYChart.Series series = new XYChart.Series();
    public SpectrumSeries() {
        super();
        series.setName("Функция спектра мощности");
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
        int pSize = dimensionCalculator.getSpectrum().size();
        if(dimensionCalculator.getResult().isEmpty()) {
            series.getData().clear();
            sSize = 0;
        }
        try {
            List<Point> list = dimensionCalculator.getSpectrum().subList(sSize, pSize);
            for (Point p : list) {
                series.getData().add(new XYChart.Data(p.getT(), p.getX()));
            }
        } catch (Exception e) {}

    }

    @Override
    public String getXLabel() {
        return "ω";
    }

    @Override
    public String getYLabel() {
        return "S(ω)";
    }

    @Override
    public String getName() {
        return "График функции спектра мощности";
    }

    @Override
    public String toString() {
        return "Спектр мощности";
    }
}
