package ru.denfad.fractaldim.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import ru.denfad.fractaldim.ChartSeries.AbstractSeries;
import ru.denfad.fractaldim.ChartSeries.ChartFactory;
import ru.denfad.fractaldim.ChartSeries.HanningChart.HanningFactory;
import ru.denfad.fractaldim.ChartSeries.LogarithmChart.LogarithmFactory;
import ru.denfad.fractaldim.ChartSeries.ResultChart.ResultFactory;
import ru.denfad.fractaldim.ChartSeries.SpectrumChart.SpectrumFactory;
import ru.denfad.fractaldim.ChartSeries.TimeSeriesChart.TimeSeriesChartFactory;
import ru.denfad.fractaldim.Model.DimensionCalculator;
import ru.denfad.fractaldim.Observer;
import ru.denfad.fractaldim.Service.DataLoader;
import ru.denfad.fractaldim.Service.Generators.GeneratorType;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class MainController implements Observer {


    @FXML
    private Label dimensionLabel;

    @FXML
    private Label SAngleText;

    @FXML
    private TreeView chartsTree;

    @FXML
    private Label SpectrText;

    @FXML
    private Label NPointText;


    @FXML
    private LineChart chartView;

    private DimensionCalculator dimensionCalculator;

    private AbstractSeries series;

    private boolean draw = false;

    private boolean transparentPoints = true;

    private boolean transparentLines = false;

    @FXML
    public void initialize() {

        dimensionCalculator = DimensionCalculator.getInstance();
        dimensionCalculator.registerObserver(this);

        //charts
        ((NumberAxis) chartView.getYAxis()).setForceZeroInRange(false);
        ((NumberAxis) chartView.getXAxis()).setForceZeroInRange(false);

        //treeView
        TreeItem<ChartFactory> timeSeries = new TreeItem<ChartFactory>(new TimeSeriesChartFactory());
        timeSeries.getChildren().add(new TreeItem<>(new TimeSeriesChartFactory()));
        timeSeries.getChildren().add(new TreeItem<>(new HanningFactory()));
        timeSeries.getChildren().add(new TreeItem<>(new SpectrumFactory()));
        timeSeries.getChildren().add(new TreeItem<>(new LogarithmFactory()));
        timeSeries.getChildren().add(new TreeItem<>(new ResultFactory()));
        chartsTree.setRoot(timeSeries);
        chartsTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ChartFactory>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<ChartFactory>> observableValue, TreeItem<ChartFactory> chartFactoryTreeItem, TreeItem<ChartFactory> t1) {
                series = t1.getValue().getSeries();
                series.clearSeries();
                chartView.getData().setAll(series.getSeries());
                chartView.getXAxis().setLabel(series.getXLabel());
                chartView.getYAxis().setLabel(series.getYLabel());
                SpectrText.setText(series.getName());
                if(!transparentLines)  series.getSeries().get(0).getNode().setStyle("-fx-stroke: #FF0000;");
                else  series.getSeries().get(0).getNode().setStyle("-fx-stroke: transparent;");
            }
        });

        chartsTree.getSelectionModel().select(0);
    }

    @FXML
    protected void saveFractalClick() {
        if (!dimensionCalculator.getTimeSeries().isEmpty()) DataLoader.saveData(dimensionCalculator.getResult());
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Временной ряд пуст!");
            alert.show();
            alert.setOnCloseRequest(e -> alert.close());
        }
    }

    @FXML
    protected void downloadFractalClick() {
        dimensionCalculator.setTimeSeries(DataLoader.loadData());
        chartsTree.getSelectionModel().select(0);
    }

    @FXML
    protected void getDimensionClick() {
        if (!dimensionCalculator.isWork() && !dimensionCalculator.getTimeSeries().isEmpty()) {
            series.clearSeries();
            dimensionCalculator.calcDimension();
            chartsTree.getRoot().setExpanded(true);
            chartsTree.getSelectionModel().select(5);
        } else if (dimensionCalculator.isWork()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Дождитесь окончания работы алгоритма");
            alert.show();
            alert.setOnCloseRequest(e -> alert.close());
        } else if (dimensionCalculator.getTimeSeries().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Никакой временной ряд не сгенерирован или открыт!");
            alert.show();
            alert.setOnCloseRequest(e -> alert.close());
        }
    }

    @FXML
    protected void generateNormalClick() {
        if (!dimensionCalculator.isWork()) {
            dimensionCalculator.generateTimeSeries(GeneratorType.NORMAL_DISTRIBUTION);
            chartsTree.getSelectionModel().select(0);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Дождитесь окончания работы алгоритма");
            alert.show();
            alert.setOnCloseRequest(e -> alert.close());
        }
    }

    @FXML
    protected void generateLogisticClick() {
        if (!dimensionCalculator.isWork()) {
            dimensionCalculator.generateTimeSeries(GeneratorType.LOGISTIC_DISTRIBUTION);
            chartsTree.getSelectionModel().select(0);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText("Дождитесь окончания работы алгоритма");
            alert.show();
            alert.setOnCloseRequest(e -> alert.close());
        }
    }

    @FXML
    public void tsPointsClick() {
        chartView.setCreateSymbols(false);

    }



    @Override
    public void update() {
        if (!draw) {
            draw = true;
            if (!dimensionCalculator.getTimeSeries().isEmpty()) {
                NPointText.setText("Колличество точек N: " + dimensionCalculator.getN());
                series.updateSeries();
            }

            if (!dimensionCalculator.getResult().isEmpty()) {
                dimensionLabel.setText("Фрактальная размерность D: " + String.format("%.03f", dimensionCalculator.getDimension()));
                SAngleText.setText("Параметр наклона прямой s: " + String.format("%.03f", dimensionCalculator.getS()));
            }
        }
        draw = false;
    }

    @FXML
    public void trancparentClick() {
        transparentPoints = !transparentPoints;
        chartView.setCreateSymbols(transparentPoints);
    }

    @FXML
    public void trancparentLineClick() {
        if(transparentLines)  series.getSeries().get(0).getNode().setStyle("-fx-stroke: #FF0000;");
        else  series.getSeries().get(0).getNode().setStyle("-fx-stroke: transparent;");
        transparentLines = !transparentLines;

    }
}
