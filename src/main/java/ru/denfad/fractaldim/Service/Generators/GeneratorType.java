package ru.denfad.fractaldim.Service.Generators;

public enum GeneratorType {

    NORMAL_DISTRIBUTION(new NormalDistributionGenerator()),
    LOGISTIC_DISTRIBUTION(new LogisticDistributionGenerator());

    private TimeSeriesGenerator generator;
    GeneratorType(TimeSeriesGenerator generator) {
        this.generator = generator;
    }
    public TimeSeriesGenerator getGenerator() {return generator;}
}
