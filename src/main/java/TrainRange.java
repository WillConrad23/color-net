import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class TrainRange
{
  private static double[] range = {0, 227};
  private static double increment = 0.03;
  private static File file = new File("data.csv");
  public static void main(String[] args)
  {
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(15);

    for(double d = range[0] + increment; d < range[1]; d+=increment)
    {
      // String data = ("0" + df.format(d/255) + ", 0" + df.format(d/255) + ", 0" + df.format(d/255) + ", 0, 0, 0, 0, 0, 0, 0, 0, 1, 0\n");
      String data = ("1, 0" + df.format(d/255) + ", 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0\n");

      try
      {
        FileWriter writer = new FileWriter(file.getAbsolutePath(), true);
        writer.write(data);
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}