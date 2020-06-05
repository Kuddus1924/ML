package packageFunction;

import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class Function {
    public static SimpleMatrix Log(SimpleMatrix matrixTMP)
    {
        SimpleMatrix matrix = new SimpleMatrix(matrixTMP);
        for(int i = 0; i < matrix.numRows();i++)
        {
            for(int j = 0; j < matrix.numCols();j++)
            {
                matrix.set(i,j,Math.log(matrix.get(i,j)));
            }
        }
        return matrix;
    }
    public static SimpleMatrix Log1(SimpleMatrix matrixTMP)
    {
        SimpleMatrix matrix = new SimpleMatrix(matrixTMP);
        for(int i = 0; i < matrix.numRows();i++)
        {
            for(int j = 0; j < matrix.numCols();j++)
            {
                matrix.set(i,j,Math.log(1.0 - matrix.get(i,j)));
            }
        }
        return matrix;
    }
    public static SimpleMatrix division(SimpleMatrix matrixTMP)
    {
        SimpleMatrix matrix = new SimpleMatrix(matrixTMP);
        for(int i = 0; i < matrix.numRows();i++)
        {
            for(int j = 0; j < matrix.numCols();j++)
            {
                matrix.set(i,j,matrix.get(i,j) / (double)matrix.numRows());
            }
        }
        return matrix;
    }
    public static SimpleMatrix OneMinus(SimpleMatrix matrixTMP)
    {
        SimpleMatrix matrix = new SimpleMatrix(matrixTMP);
        for(int i = 0; i < matrix.numRows();i++)
        {
            for(int j = 0; j < matrix.numCols();j++)
            {
                matrix.set(i,j,1.0 - matrix.get(i,j));
            }
        }
        return matrix;
    }
    public  static double lossFunctionLin(SimpleMatrix matrix)
    {
        SimpleMatrix matT = matrix.transpose();
        SimpleMatrix tmp = matT.mult(matrix);
        return tmp.get(0,0)/(2.0 * matrix.numRows());
    }
    public  static double lossFunctionLog(SimpleMatrix y, SimpleMatrix s)
    {
        SimpleMatrix matT = y.transpose();
        SimpleMatrix matT1 = Function.OneMinus(y).transpose();
        SimpleMatrix sigma1 = Function.Log1(s);
        SimpleMatrix tmp1 =  matT.mult(Function.Log(s));
        SimpleMatrix tmp2 = matT1.mult(sigma1);
        matT = tmp1.plus(tmp2);
        return (-1 * matT.get(0,0))/(y.numRows());
    }
    public static SimpleMatrix Round(SimpleMatrix matrixTMP)
    {
        SimpleMatrix matrix = new SimpleMatrix(matrixTMP);
        for(int i = 0; i < matrix.numRows();i++)
        {
            for(int j = 0; j < matrix.numCols();j++)
            {
                matrix.set(i,j,Math.round(matrix.get(i,j)));
            }
        }
        return matrix;
    }

    static void shuffleArray(double[][] ar) {
        Random rnd = new Random(1798);
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            double[] a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
