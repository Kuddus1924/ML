package packageOne;

import org.ejml.simple.SimpleMatrix;
import packageFunction.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Simulate1 {
    public static SimpleMatrix trainTeta(SimpleMatrix x, SimpleMatrix y, double alpha, int iterations)
    {

        SimpleMatrix teta = SimpleMatrix.random_DDRM(x.numCols(), 1, 0, 1, new Random());
        int end =(int) (x.numRows() * 0.7);
        int endY =(int) (y.numRows() * 0.7);
        SimpleMatrix Xcontrol = new SimpleMatrix(x.rows(end,x.numRows()));
        SimpleMatrix Ycontrol = new SimpleMatrix(y.rows(endY,y.numRows()));
        x =  new SimpleMatrix(x.rows(0,end));
        y = new SimpleMatrix(y.rows(0,end));
        ArrayList<Double> losses = new ArrayList<>();
        ArrayList<Double> lossesControl = new ArrayList<>();
        SimpleMatrix x_T = x.transpose();
        for (int i = 0; i < iterations; i++) {
            SimpleMatrix tmp = x.mult(teta);
            tmp = tmp.minus(y);
            if(i%100 == 0)
            {
                    losses.add(Function.lossFunctionLin(tmp));
                    lossesControl.add(Function.lossFunctionLin(Xcontrol.mult(teta).minus(Ycontrol)));
            }
            tmp = x_T.mult(tmp);
            tmp = tmp.scale(alpha);
            teta = teta.minus(tmp);
        }
        Print.print(losses,lossesControl,"linreg",iterations);
        return teta;
    }
    public static SimpleMatrix trainTetaLog(SimpleMatrix x, SimpleMatrix y, double alpha, int iterations)
    {
        SimpleMatrix teta = SimpleMatrix.random_DDRM(x.numCols(), 1, 0, 1, new Random());
        int end =(int) (x.numRows() * 0.7);
        int endY =(int) (y.numRows() * 0.7);
        SimpleMatrix Xcontrol = new SimpleMatrix(x.rows(end,x.numRows()));
        SimpleMatrix Ycontrol = new SimpleMatrix(y.rows(endY,y.numRows()));
        x =  new SimpleMatrix(x.rows(0,end));
        y = new SimpleMatrix(y.rows(0,end));
        ArrayList<Double> losses = new ArrayList<>();
        ArrayList<Double> lossesControl = new ArrayList<>();
        SimpleMatrix x_T = x.transpose();
        for (int i = 0; i < iterations; i++) {
            SimpleMatrix tmp = x.mult(teta);
            Sigmoid.sigmoid(tmp);
            if(i%100 == 0)
            {
                losses.add(Function.lossFunctionLog(y,tmp));
                SimpleMatrix tmp2 = Xcontrol.mult(teta);
                Sigmoid.sigmoid(tmp2);
                lossesControl.add(Function.lossFunctionLog(Ycontrol,tmp2));
            }
            tmp = tmp.minus(y);
            tmp = x_T.mult(tmp);
            tmp = Function.division(tmp);
            tmp = tmp.scale(alpha);
            teta = teta.minus(tmp);
        }
        Print.print(losses,lossesControl,"logreg",iterations);
        return teta;
    }

    public static void main(String arg[]) throws IOException
    {
        //линеная
        SimpleMatrix x = IOMatrix.readFromCsvFile("t1_linreg_x_train.csv", ",", 0,true,true);
        SimpleMatrix x_t = IOMatrix.readFromCsvFile("t1_linreg_x_test.csv", ",", 0,true,false);
        SimpleMatrix y = IOMatrix.readFromCsvFile("t1_linreg_y_train.csv", ",", 0,false,true);
        x = Standardization.combineWineOneColumn(x);
        x_t = Standardization.combineWineOneColumn(x_t);

        double alpha = 1e-7;
        int iterations = 1000000;
        SimpleMatrix teta = trainTeta(x, y, alpha, iterations);
        System.out.println("Lin reg");
        teta.print();
        SimpleMatrix y_t = x_t.mult(teta);
        teta.saveToFileCSV("teta.csv");
        y_t.saveToFileCSV("y_t.csv");
        //лог
       SimpleMatrix xL = IOMatrix.readFromCsvFile("t1_logreg_x_train.csv", ",", 0,true,true);
        SimpleMatrix xL_t = IOMatrix.readFromCsvFile("t1_logreg_x_test.csv", ",", 0,true,false);
        SimpleMatrix yL = IOMatrix.readFromCsvFile("t1_logreg_y_train.csv", ",", 0,false,true);
        xL = Standardization.combineWineOneColumn(xL);
        xL_t = Standardization.combineWineOneColumn(xL_t);

        double alphaL = 5e-2;
        int iterationsL = 10000;
        SimpleMatrix tetaL = trainTetaLog(xL, yL, alphaL, iterationsL);
        System.out.println("Log reg");
        tetaL.print();
        SimpleMatrix yL_t = xL_t.mult(tetaL);
        Sigmoid.sigmoid(yL_t);
        yL_t = Function.Round(yL_t);
        tetaL.saveToFileCSV("tetaL.csv");
        yL_t.saveToFileCSV("y_tL.csv");
    }
}
