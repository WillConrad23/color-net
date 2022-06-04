

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.io.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.ejml.data.Matrix;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleOperations;

import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;
import basicneuralnetwork.utilities.InterfaceAdapter;

public class NetworkInterface extends ColorLabels implements ChangeListener {
  private double r, g, b;
  private JSlider rIn, gIn, bIn;
  private JButton btn;
  private JLabel guess, rNum, gNum, bNum;
  //private JProgressBar pBarRed, pBarOrange, pBarYellow, pBarGreen, pBarBlue, pBarPurple, pBarPink, pBarBrown, pBarWhite, pBarBlack;
  private JProgressBar[] pBars = new JProgressBar[10];// = {pBarRed, pBarOrange, pBarYellow, pBarGreen, pBarBlue, pBarPurple, pBarPink, pBarBrown, pBarWhite, pBarBlack};
  private Container pane;
  private NeuralNetwork nn;// = NeuralNetwork.readFromFile("./classes/rgbModel.json");
  private TitledBorder[] borders = new TitledBorder[10];
  
  private String path = "";

  public NetworkInterface() {
    nn = NeuralNetwork.readFromFile("src/main/resources/rgbModel-95.9923.json");
    //Create the Jframe
    JFrame frame = new JFrame();  
    frame.setSize(500,500);  
    frame.setTitle("ColorNet Interactive");
    pane = frame.getContentPane(); 
    pane.setBackground(Color.BLACK);
    
    JPanel input = new JPanel();
    input.setBounds(25,25,450,150);  
    input.setOpaque(false);
    input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
    
    JPanel bg1 = new JPanel();
    bg1.setBounds(20,20,460,105);  
    bg1.setBackground(new Color (0, 0, 0, 100));
    bg1.setBorder(BorderFactory
      .createMatteBorder(2, 2, 2, 2, Color.WHITE)
    );

    //RED
    rIn = new JSlider(0, 255, 0);
    rIn.setBounds(40, 40, 420, 20);
    rIn.setBorder(BorderFactory
      .createMatteBorder(1, 35, 1, 1, Color.RED)
    );
    rIn.addChangeListener(this);
    //VALUE
    rNum = new JLabel("0");
    rNum.setBounds(20, 30, 35, 20);
    rNum.setHorizontalAlignment(JLabel.RIGHT);
    
    //GREEN
    gIn = new JSlider(0, 255, 0);
    gIn.setBounds(40, 90, 420, 20);
    gIn.setBorder(BorderFactory
      .createMatteBorder(1, 35, 1, 1, Color.GREEN)
    );
    gIn.addChangeListener(this);
    //VALUE
    gNum = new JLabel("0");
    gNum.setBounds(20, 62, 35, 20);
    gNum.setHorizontalAlignment(JLabel.RIGHT);
    //BLUE
    bIn = new JSlider(0, 255, 0);
    bIn.setBounds(40, 140, 420, 20);
    bIn.setBorder(BorderFactory
      .createMatteBorder(1, 35, 1, 1, Color.BLUE)
    );
    bIn.addChangeListener(this);

    bNum = new JLabel("0");
    bNum.setBounds(20, 94, 35, 20);
    bNum.setHorizontalAlignment(JLabel.RIGHT);
    //------BUTTON--------

    btn = new JButton();
    btn.setBounds(260, 132, 220, 50);
    btn.setOpaque(true);
    btn.setBorder(BorderFactory
      .createMatteBorder(2, 2, 2, 2, Color.BLACK)
    );
    btn.addChangeListener(this);

    JLabel btnLbl = new JLabel("Guess!");
    btnLbl.setFont(new Font("Verdana", Font.PLAIN, 30));
    btnLbl.setHorizontalAlignment(JLabel.CENTER);
    btnLbl.setForeground(Color.BLACK);
  
    btnLbl.setOpaque(false);
    // guess.setText("TEST");
    btnLbl.setBounds(260, 132, 220, 50);
    
    btn.add(btnLbl);
    frame.add(btn);
    
    


    JPanel answer = new JPanel();
    answer.setBounds(25,140,225,320);  
    answer.setOpaque(false);
    answer.setLayout(new BoxLayout(answer, BoxLayout.Y_AXIS));

    for (int i = 0; i < 9; i++)
    {
      borders[i] = new TitledBorder(
        BorderFactory.createLineBorder(Color.black),
        "0.0",
        TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION,
        new Font("Default", Font.PLAIN, 13),
        Color.black
      );
    }
    borders[9] = new TitledBorder(
      BorderFactory.createLineBorder(Color.white),
      "0.0",
      TitledBorder.DEFAULT_JUSTIFICATION,
      TitledBorder.DEFAULT_POSITION,
      new Font("Default", Font.PLAIN, 13),
      Color.white
    );

    for (int i = 0; i < 10; i++)
    {
      pBars[i] = new JProgressBar(0, 100);
      Color c = colors[i];
      pBars[i].setBackground(c.darker());
      pBars[i].setPreferredSize(new Dimension(200, 30));
      pBars[i].setOpaque(true);
      pBars[i].setBorder(borders[i]);
      // if (i < 9) pBars[i].setForeground(Color.BLACK);
      // else pBars[9].setForeground(Color.WHITE);
      answer.add(pBars[i]);

    }
    
    JPanel bg2 = new JPanel();
    bg2.setBounds(20,135,235,330);  
    bg2.setBackground(new Color (0, 0, 0, 100));
    bg2.setBorder(BorderFactory
      .createMatteBorder(2, 2, 2, 2, Color.WHITE)
    );
    
    // guess = new JLabel();
    // guess.setBounds(0, 0, 200, 200);
    // guess.setHorizontalAlignment(JLabel.CENTER);
    // guess.setVerticalAlignment(JLabel.CENTER);
    // guess.setFont(new Font("Verdana", Font.PLAIN, 50));
    // guess.setText("TEST");
    
    
    //answer.add(guess);
    

    
    input.add(rIn);
    input.add(gIn);
    input.add(bIn);

    frame.add(rNum);
    frame.add(gNum);
    frame.add(bNum);

    frame.add(input);
    frame.add(bg1);
    
    frame.add(answer);
    frame.add(bg2);
    
    
    frame.setLayout(null);
    frame.setVisible(true);  
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  @Override
  public void stateChanged(ChangeEvent e) {
    if (e.getSource() == rIn) {
      r = rIn.getValue();
      rNum.setText("" + (int)r);
    }
    else if (e.getSource() == gIn) {
      g = gIn.getValue();
      gNum.setText("" + (int)g);
    }
    else if (e.getSource() == bIn) {
      b = bIn.getValue();
      bNum.setText("" + (int)b);
    }

    // if (e.getSource() != btn) {
    //   pane.setBackground(new Color((int)r,(int)g,(int)b));
    // }
    if (e.getSource() != btn)
    {
      pane.setBackground(new Color((int)r,(int)g,(int)b));


      double[] input = {r/255.0, g/255.0, b/255.0};
      //System.out.println(input[0] + " " + input[1] + " " + input[2]);
      double[] guessArr = nn.guess(input);
    
      // for (SimpleMatrix sm : guess)
      // {
      //   System.out.println(sm);
      
      // }
      
      for (int i = 0; i < 10; i++)
      {
        int v = (int)(guessArr[i] * 100);
        pBars[i].setValue(v);
        borders[i].setTitle("" + v);
      }
      
      int largestIndex = 0;
      double largest = guessArr[0];
      for (int i = 0; i < guessArr.length; i++)
      {
        if (guessArr[i] > largest)
        {
          largest = guessArr[i];
          largestIndex = i;
        }
      }
      for (int i = 0; i < 10; i++)
        pBars[i].setBackground(colors[i].darker());
      
      pBars[largestIndex].setBackground(colors[largestIndex]);
    }
  }
  
  //Get random color
  public static void main(String args[])
  {
    NetworkInterface ct = new NetworkInterface();
    //System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ColorNet Interactive");
  }
}

