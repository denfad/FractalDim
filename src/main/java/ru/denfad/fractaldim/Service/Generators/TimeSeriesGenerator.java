package ru.denfad.fractaldim.Service.Generators;

import ru.denfad.fractaldim.Model.Point;

import java.util.List;

public interface TimeSeriesGenerator {
    List<Point> generateTimeSeries();
}
