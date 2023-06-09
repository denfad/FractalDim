package ru.denfad.fractaldim.ChartSeries.LogarithmChart;

import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;

public class LogarithmFactory implements ChartFactory {
    @Override
    public AbstractSeries getSeries() {
        return new LogaritmSeries();
    }

    @Override
    public String toString() {
        return "Логарифм";
    }
}
