package packageFunction;

import org.ejml.simple.SimpleMatrix;

import java.util.Vector;

public class Sigmoid {
    public static void sigmoid(SimpleMatrix matrix)
    {
        for(int i = 0; i < matrix.numCols();i++)
        {
            for(int j = 0; j < matrix.numRows();j++)
            {
                matrix.set(j,i,getSigmoid(matrix.get(j,i)));
            }
        }
    }
    private static double getSigmoid(double x)
    {
        return 1.0/(1.0 + Math.exp(-x));
    }
}
