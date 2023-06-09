package ru.denfad.fractaldim.ChartSeries.HanningChart;

import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;

public class HanningFactory implements ChartFactory {
    @Override
    public AbstractSeries getSeries() {
        return new HanningSeries();
    }

    @Override
    public String toString() {
        return "Окно Хэннинга";
    }
}
