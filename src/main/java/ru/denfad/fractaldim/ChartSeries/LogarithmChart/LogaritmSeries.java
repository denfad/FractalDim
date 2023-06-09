package ru.denfad.fractaldim.ChartSeries.LogarithmChart;

import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public class LogaritmSeries extends AbstractSeries {

    private XYChart.Series series = new XYChart.Series();

    public LogaritmSeries() {
        super();
        series.setName("Логарифм");
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
        int pSize = dimensionCalculator.getResult().size();
        if(dimensionCalculator.getResult().isEmpty()) {
            series.getData().clear();
            sSize = 0;
        }
        try {
            List<Point> list = dimensionCalculator.getResult().subList(sSize, pSize);
            for (Point p : list) {
                series.getData().add(new XYChart.Data(p.getT(), p.getX()));
            }
        } catch (Exception e) {}
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
        return "График логарифма функции";
    }
}
