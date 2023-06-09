package ru.denfad.fractaldim.ChartSeries.TimeSeriesChart;

import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;

public class TimeSeriesChartFactory implements ChartFactory {
    @Override
    public AbstractSeries getSeries() {
        return new TimeSeries();
    }

    @Override
    public String toString() {
        return "Временной ряд";
    }
}
