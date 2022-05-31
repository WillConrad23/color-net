import com.opencsv.*;
import java.io.*;
import java.util.*;

public class Interpreter {
  private double[][] inputs;
  private double[][] outputs;
  private List<String[]> allData;
  private int length;
  private int[] chosen;
  Double red, green, blue;
  public Interpreter(String path)
  {
    try { 
      File file = new File(path);
      FileReader filereader = new FileReader(file);
 
      // create csvParser object with
      // custom separator semi-colon
      CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

      // create csvReader object with parameter
      // filereader and parser
      CSVReader csvReader = new CSVReaderBuilder(filereader)
                                .withCSVParser(parser)
                                .build();

      // Read all data at once
      allData = csvReader.readAll();
      length = allData.size();
      System.out.println(length);

    } catch(Exception e) {
      e.printStackTrace();
    }
    chosen = new int[10];
   
    for (int r = 0; r < length; r++)
    {
      String[] row = allData.get(r);

      red = Double.parseDouble(row[0]);
      green = Double.parseDouble(row[1]);
      blue = Double.parseDouble(row[2]);

      String event = row[3];
      for (int i = 0; i < 10; i++)
      {
        chosen[i] = 0;
      }
      if (event.indexOf("Red") != -1)         { chosen[0] = 1; }
      else if (event.indexOf("Orange") != -1) { chosen[1] = 1; }
      else if (event.indexOf("Yellow") != -1) { chosen[2] = 1; }
      else if (event.indexOf("Green") != -1)  { chosen[3] = 1; }
      else if (event.indexOf("Blue") != -1)   { chosen[4] = 1; }
      else if (event.indexOf("Purple") != -1) { chosen[5] = 1; }
      else if (event.indexOf("Pink") != -1)   { chosen[6] = 1; }
      else if (event.indexOf("Brown") != -1)  { chosen[7] = 1; }
      else if (event.indexOf("White") != -1)  { chosen[8] = 1; }
      else if (event.indexOf("Black") != -1)  { chosen[9] = 1; }
      
      String data = (red/255 + ", " + green/255 + ", " + blue/255);
      for (int i : chosen)
      {
        data = data + (", " + i);
      }
      data += "\n";
      try
      {
        FileWriter writer = new FileWriter("newData.csv", true);
        writer.write(data);
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
      //r, g, b, isRed, isOrange, isYellow, isGreen, isBlue, isPurple, isPink, isBrown, isBlack, isWhite
      
  }
  
 
  public static void main(String[] args)
  {
    Interpreter d = new Interpreter("oldData.csv");
  }
}
