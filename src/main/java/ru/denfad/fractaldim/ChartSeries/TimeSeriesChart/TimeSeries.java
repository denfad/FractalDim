package ru.denfad.fractaldim.ChartSeries.TimeSeriesChart;

import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public class TimeSeries extends AbstractSeries {

    private XYChart.Series series = new XYChart.Series<>();
    public TimeSeries() {
        super();
        series.setName("Временной ряд");
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
        int pSize = dimensionCalculator.getTimeSeries().size();
        if(dimensionCalculator.getResult().isEmpty()) {
            series.getData().clear();
            sSize = 0;
        }
        try {
            List<Point> list = dimensionCalculator.getTimeSeries().subList(sSize, pSize);
            for (Point p : list) {
                series.getData().add(new XYChart.Data(p.getT(), p.getX()));
            }
        } catch (Exception e){}
    }

    @Override
    public String getXLabel() {
        return "t";
    }

    @Override
    public String getYLabel() {
        return "x";
    }

    @Override
    public String getName() {
        return "График исходного временного ряда";
    }
}
