package ru.denfad.fractaldim.ChartSeries.ResultChart;

import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;

public class ResultFactory implements ChartFactory {
    @Override
    public AbstractSeries getSeries() {
        return new ResultSeries();
    }

    @Override
    public String toString() {
        return "Аппроксимация";
    }
}
