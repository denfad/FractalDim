package ru.denfad.fractaldim.Service.Generators;

public class GeneratorFactory {

    public static TimeSeriesGenerator getGenerator(GeneratorType type) {
        return type.getGenerator();
    }
}
