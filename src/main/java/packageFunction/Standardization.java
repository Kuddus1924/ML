package packageFunction;

import org.ejml.simple.SimpleMatrix;

public class Standardization {
  static private double mean(double[][] data,int colums)
   {
       double result = 0;
       for(int i = 0;i < data.length;i++)
       {
           result+=data[i][colums];
       }
       return result/(double)data.length;
   }
   static private  double standardDeviation(double[][] data,int colums,double mean)
   {
       double sum = 0;
       for(int i = 0;i < data.length;i++)
       {
           sum += Math.pow(data[i][colums] - mean,2);
       }
       return Math.sqrt(sum / data.length);

   }
   static public void getStandart(double[][] data)
   {
        for (int i = 0;i < data[0].length;i ++)
        {
            double mean = mean(data,i);
            double standardDeviation = standardDeviation(data,i,mean);
            for(int j = 0;j < data.length;j ++)
            {
                data[j][i] =  (data[j][i]-mean)/standardDeviation;
            }
        }
   }
    public static SimpleMatrix combineWineOneColumn(SimpleMatrix matrix)
    {
        SimpleMatrix one = new SimpleMatrix(matrix.numRows(), 1);
        one.fill(1);

        return one.concatColumns(matrix);
    }
}
