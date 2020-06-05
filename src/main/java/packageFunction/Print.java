package packageFunction;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;

public class Print {

    public static void print (ArrayList<Double> array, ArrayList<Double> array1,String name, int k) {

        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < k;i+=100)
        {
            list.add(i);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).title(name).xAxisTitle("x").yAxisTitle("y").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(false);
        chart.getStyler().setPlotGridLinesVisible(false);

        chart.addSeries("Обучающая выборка", list, array);
        chart.addSeries("Проверочная выборка", list,array1);
        List<XYChart> charts = new ArrayList<XYChart>();
        charts.add(chart);
        new SwingWrapper<XYChart>(charts).displayChartMatrix();

    }
}
