package ru.denfad.fractaldim.ChartSeries.SpectrumChart;

import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;

public class SpectrumFactory implements ChartFactory {
    @Override
    public AbstractSeries getSeries() {
        return new SpectrumSeries();
    }

    @Override
    public String toString() {
        return "Спектр мощности";
    }
}
