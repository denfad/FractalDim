package ru.denfad.fractaldim.ChartSeries;

import javafx.scene.chart.XYChart;
import ru.denfad.fractaldim.Model.DimensionCalculator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSeries {
    protected List<XYChart.Series> seriesList = new ArrayList<>();
    protected DimensionCalculator dimensionCalculator;
    public AbstractSeries() {
        this.dimensionCalculator = DimensionCalculator.getInstance();
    }
    public abstract List<XYChart.Series> getSeries();

    public abstract void updateSeries();

    public void clearSeries() {
        for(XYChart.Series s : seriesList) s.getData().clear();
    }

    public abstract String getXLabel();
    public abstract String getYLabel();

    public abstract String getName();

}
