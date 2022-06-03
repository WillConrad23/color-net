import com.opencsv.*;
import java.io.*;
import java.util.*;

public class Dataset {
  private double[][] inputs;
  private double[][] outputs;
  private List<String[]> allData;
  private int length;

  public Dataset(String path)
  {
    try { 
      File file = new File(path);
      FileReader filereader = new FileReader(file);
      CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
      CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();

      allData = csvReader.readAll();
      length = allData.size();

    } catch(Exception e) {
      e.printStackTrace();
    }
    
    inputs = new double[length][3];
    outputs = new double[length][10];
    populate();
  }
  private void populate()
  {
    for (int r = 0; r < length; r++) {
      String[] row = allData.get(r);
      for (int c = 0; c < 3; c++) {
          inputs[r][c] = Double.parseDouble(row[c]);
      }
      for (int c = 0; c < 10; c++) {
          outputs[r][c] = Double.parseDouble(row[c+3]);
      }
    }
  }
  public double[][] getInputs() { return inputs; }
  public double[][] getOutputs() { return outputs; }
  public int getLength() { return length; }
  public static void main(String[] args)
  {
    Dataset d = new Dataset("src/main/resources/data.csv");
    for (double[] r : d.getInputs())
    {
      for (double c : r)
      {
        System.out.println(c);
      }
      System.out.println();
    }
    
    for (double[] r : d.getOutputs())
    {
      for (double c : r)
      {
        System.out.println(c);
      }
      System.out.println();
    }

  }
}
