package packageFunction;

import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.Predicate;

public class IOMatrix {
    public static SimpleMatrix readFromCsvFile(String file, String separator, int headerRows,boolean norm,boolean permutation) throws IOException
    {
        Path path = Path.of(file);
        double[][] data = Files.lines(path)
            .skip(headerRows)
            .filter(Predicate.not(String::isBlank))
            .map(s -> convertCsvLineToArray(s, separator))
            .toArray(double[][]::new);
       if (norm)
            Standardization.getStandart(data);
        if(permutation)
            Function.shuffleArray(data);
            return new SimpleMatrix(data);
    }

    private static double[] convertCsvLineToArray(String line, String separator) {
        return Arrays.stream(line.split(separator))
            .mapToDouble(Double::parseDouble)
            .toArray();
    }
}
