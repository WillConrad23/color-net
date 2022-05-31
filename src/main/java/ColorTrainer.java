
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
// import java.io.File;
// import java.io.FileWriter;
// import java.io.RandomAccessFile;
// import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ColorTrainer extends ColorLabels implements ActionListener {
  

  // private static final Color PURPLE = new Color(164, 67, 224);
  // private static final Color PINK = new Color(255, 84, 238);
  // private static final Color BROWN = new Color(165, 42, 42);
  private static Color c;
  private static Color oldC;
  private static File file;
  private final int space = 10;
  private GridLayout grid = new GridLayout(3, 3, space, space);
  private Container pane;
  //private JLabel label;
  private int[] chosen;
  
  public ColorTrainer(String s) {
    chosen = new int[10];

    //Create the Jframe
    JFrame frame = new JFrame(s);
    frame.setSize(500,500);  
    
    frame.setTitle(frame.getTitle() + " - " + file.getName());
    pane = frame.getContentPane(); 
    c = randomColor();
    pane.setBackground(c);
    
    JPanel panel = new JPanel();
    panel.setBounds(25,300,450,150);  
    panel.setBackground(new Color(255,255,255,85));
    panel.setLayout(grid);
    
    // label = new JLabel("", JLabel.CENTER);
    // label.setBounds(
    //   frame.getWidth() / 2 - 100,
    //   frame.getHeight() - 50,
    //   200,
    //   30
    // );

    JButton undo = new JButton("Undo");
    undo.setBounds(
      (int)(frame.getWidth() * 0.75) - 50, 
      frame.getHeight() - 50, 
      100,
      30
    );
    undo.addActionListener(this);

    JButton skip = new JButton("Skip");
    skip.setBounds(
      (int)(frame.getWidth() * 0.25) - 50, 
      frame.getHeight() - 50, 
      100,
      30
    );
    skip.addActionListener(this);

    for (int i = 0; i < 10; i++)
    {
      JButton b = new JButton(names[i]);
      b.setBackground(colors[i]);
      b.setOpaque(true);
      b.setBounds(0,0,1,1);  
      b.addActionListener(this);
      panel.add(b);
    }
    frame.add(panel);
    frame.add(undo);
    frame.add(skip);
    frame.setLayout(null);
    frame.setVisible(true);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(e);
    String event = e.toString();
    if (event.indexOf("Undo") != -1) //Contains undo
    {
      pane.setBackground(oldC);
      c = oldC;
      
      //Remove last line of file
      try
      {
        RandomAccessFile f = new RandomAccessFile(file.getAbsolutePath(), "rw");
        long length = f.length() - 1;
        byte b;
        do {                     
          length -= 1;
          f.seek(length);
          b = f.readByte();
        } while(b != 10);
        f.setLength(length+1);
        f.close();
      } catch (IOException ex) {
        ex.printStackTrace();
        System.exit(1);
      }
    }
    else if (event.indexOf("Skip") != -1)
    {
      c = randomColor();
      pane.setBackground(c);
    }
    else
    {
      for (int i = 0; i < chosen.length; i++) chosen[i] = 0;

      if (event.indexOf("Red") != -1)         { chosen[0] = 1; }
      else if (event.indexOf("Orange") != -1) { chosen[1] = 1; }
      else if (event.indexOf("Yellow") != -1) { chosen[2] = 1; }
      else if (event.indexOf("Green") != -1)  { chosen[3] = 1; }
      else if (event.indexOf("Blue") != -1)   { chosen[4] = 1; }
      else if (event.indexOf("Purple") != -1)   { chosen[5] = 1; }
      else if (event.indexOf("Pink") != -1) { chosen[6] = 1; }
      else if (event.indexOf("Brown") != -1)   { chosen[7] = 1; }
      else if (event.indexOf("White") != -1)  { chosen[8] = 1; }
      else if (event.indexOf("Black") != -1)  { chosen[9] = 1; }
      double r = c.getRed();
      double g = c.getGreen();
      double b = c.getBlue();
      //r, g, b, isRed, isOrange, isYellow, isGreen, isBlue, isPurple, isPink, isBrown, isBlack, isWhite
      String data = (r/255 + ", " + g/255 + ", " + b/255);
      for (int i : chosen)
      {
        data = data + (", " + i);
      }
      data += "\n";
      try
      {
        FileWriter writer = new FileWriter(file.getAbsolutePath(), true);
        writer.write(data);
        writer.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      oldC = c;
      c = randomColor();
      pane.setBackground(c);
    }
  }
  //Get random color
  private static Color randomColor()
  {
    int r = (int)(Math.random() * 255);
    int g = (int)(Math.random() * 255);
    int b = (int)(Math.random() * 255);
    return new Color(r, g, b);
  }
  public static void main(String args[])
  {
    
    JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));

    chooser.setAcceptAllFileFilterUsed(false);
    //Limit file selection to text docs so nothing important gets overwritten
    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
    chooser.addChoosableFileFilter(filter);
    
    int res = chooser.showOpenDialog(null);
    if (res == JFileChooser.APPROVE_OPTION) { file = chooser.getSelectedFile(); }
    else { System.exit(0); }

    ColorTrainer ct = new ColorTrainer("Color Dataset Trainer");
    //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Color Trainer");
  }
}

