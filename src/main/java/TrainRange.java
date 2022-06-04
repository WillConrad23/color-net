import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class TrainRange
{
  private static double[] range = {227, 255};
  private static double increment = 0.1;
  private static File file = new File("src\\main\\resources\\data.csv");
  public static void main(String[] args)
  {
    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(15);

    for(double d = range[0] + increment; d < range[1]; d+=increment)
    {
      // String data = ("0" + df.format(d/255) + ", 0" + df.format(d/255) + ", 0" + df.format(d/255) + ", 0, 0, 0, 0, 0, 0, 0, 0, 1, 0\n");
      String data = ("0.8902, 0.8902, 0" + df.format(d/255) + ", 0, 0, 0, 0, 0, 0, 0, 0, 1, 0\n");

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