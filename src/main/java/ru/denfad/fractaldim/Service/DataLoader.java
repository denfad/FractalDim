package ru.denfad.fractaldim.Service;


import javafx.stage.FileChooser;
import ru.denfad.fractaldim.Model.Point;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {


    public static List<Point> loadData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с времянным рядом");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);
        return readFile(selectedFile);
    }

    public static void saveData(List<Point> points) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
           writeFile(points, file);
        }
    }

    private static void writeFile(List<Point> points, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            for(Point p: points) writer.println(p.getT() + " "+p.getX());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private static List<Point> readFile(File file) {
        List<Point> array = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                String[] a = s.split(" ");
                float x = Float.valueOf(a[0]);
                float y = Float.valueOf(a[1]);
                array.add(new Point(x,y));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
    }
}
