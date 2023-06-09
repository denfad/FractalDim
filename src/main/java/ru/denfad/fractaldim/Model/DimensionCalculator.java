package ru.denfad.fractaldim.Model;

import javafx.application.Platform;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import ru.denfad.fractaldim.Observable;
import ru.denfad.fractaldim.Observer;
import ru.denfad.fractaldim.Service.Generators.GeneratorFactory;
import ru.denfad.fractaldim.Service.Generators.GeneratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DimensionCalculator implements Observable {
    private static DimensionCalculator instance;
    private List<Point> timeSeries = new ArrayList<>();

    private List<Point> hanning = new ArrayList<>();

    private List<Point> spectrum = new ArrayList<>();

    private List<Point> result = new ArrayList<>();

    private int N;

    private boolean work = false;

    private double s;
    private double intercept;

    private double dimension;

    private List<Observer> observers = new ArrayList<>();

    private DimensionCalculator() {}

    public static DimensionCalculator getInstance() {
        if (instance == null) instance = new DimensionCalculator();
        return instance;
    }

    public void setTimeSeries(List<Point> timeSeries) {
        dimension = 0;
        intercept = 0;
        s = 0;
        this.timeSeries = timeSeries;
        N = timeSeries.size();
        hanning.clear();
        spectrum.clear();
        result.clear();
        notifyObservers();
    }

    public void generateTimeSeries(GeneratorType type) {
        setTimeSeries(GeneratorFactory.getGenerator(type).generateTimeSeries());
    }

    public void calcDimension() {
        if(!work) {
            work = true;
            dimension = 0;
            intercept = 0;
            s = 0;
            Thread thread = new Thread(() -> {
                hanning.clear();
                spectrum.clear();
                result.clear();
                SimpleRegression regression = new SimpleRegression();
                for (Point p : timeSeries) {
                    //Hanning window
                    float t = (float) (0.5 * (1 - Math.cos(2 * Math.PI * p.t / (N-1))));
                    hanning.add(new Point(t, p.getX()));

                    //Spectrum function
                    Complex sum = new Complex(0, 0);
                    for (int t1 = 0; t1 < N; t1++) {
                        Complex j = new Complex(0, -1);
                        j = j.multiply(t * t1).exp().multiply(timeSeries.get(t1).x);
                        sum = sum.add(j);
                    }
                    float x = (float) (Math.pow(sum.abs(), 2) /(Math.PI * 2* N));
                    spectrum.add(new Point(t,x));

                    //Logarithm
                    t = (float) Math.log(t);
                    x = (float) Math.log(x);

                    //Approximation
                    if (!Float.isInfinite(t) && !Float.isInfinite(x)) {
                        regression.addData(t, x);
                        s = regression.getSlope();
                        intercept = regression.getIntercept();
                        result.add(new Point(t,x));
                        notifyObservers();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {

                        }
                    }

                }
                System.gc();

                //Dimension
                if (s < 0) dimension =  ((double) 5 + s) / 2;
                else dimension = ((double) 3 + s) / 2;
                work = false;
                notifyObservers();
            });
            thread.start();
        } else {
            System.out.println("Подсчёт уже идёт");
        }
    }



    public boolean isWork() {
        return work;
    }

    public int getN() {
        return N;
    }

    public double getS() {
        return s;
    }

    public double getIntercept() {
        return intercept;
    }

    public double getDimension() {
        return dimension;
    }

    public List<Point> getTimeSeries() {
        return timeSeries;
    }

    public List<Point> getHanning() {
        return hanning;
    }

    public List<Point> getSpectrum() {
        return spectrum;
    }

    public List<Point> getResult() {
        return result;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        Runnable updater = () -> {
            for (Observer o : observers) {
                o.update();
            }
        };
        Platform.runLater(updater);
    }

}

