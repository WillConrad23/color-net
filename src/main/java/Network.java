import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;

import java.util.Arrays;
import java.util.Random;


public class Network {

  // Example program to test the basic neural network by letting it solve XOR
  public static void main(String args[]){

      Dataset d = new Dataset("data.csv");
      Random r = new Random();
      
      // Training Data Inputs
      double[][] trainingDataInputs = d.getInputs();

      // Training Data Targets
      double[][] trainingDataTargets = d.getOutputs();

      // Testing Data
      double[][] testingData = {
        {0, 0, 0},
      };
      // for (int row = 0; row < testingData.length; row++)
      // {
      //   for (int c = 0; c < testingData[0].length; c++)
      //   {
      //     testingData[row][c] = testingData[row][c] / 255;
      //   }
      // }

      NeuralNetwork nn = new NeuralNetwork(3, 2, 9, 10);
      nn.setActivationFunction(ActivationFunction.SIGMOID);
      nn.setLearningRate(0.01);
      for (int i = 0; i < 5000000; i++) {
          // training in random order
          int random = (int)(Math.random() * d.getLength());
          nn.train(trainingDataInputs[random], trainingDataTargets[random]);
      }
      double correct = 0;
      for (int i = 0; i < d.getLength(); i++) 
      {
        double[] guess = nn.guess(trainingDataInputs[i]);
        int index = 0;
        double value = guess[0];
        for (int e = 0; e < 10; e++)
        {
          if (guess[e] > value)
          {
            index = e;
            value = guess[e];
          }
        }
        double[] answer = trainingDataTargets[i];
        int correctIndex = 0;
        for (int h = 0; h < 10; h++)
        {
          if (answer[h] == 1)
            correctIndex = h;
        }
        if (index == correctIndex)
          correct++;
        
      }
      System.out.println("accuracy: " + correct / d.getLength());

      //NeuralNetwork nn = NeuralNetwork.readFromFile();
      
    
      // for (int i = 0; i < testingData.length; i++) {
      //     System.out.println("Guess for " + testingData[i][0] + ", " + testingData[i][1] + ": ");
      //     double[] guess = nn.guess(testingData[i]);
      //     System.out.println(Arrays.toString(guess));
      // }
      // double[] guess = nn.guess(testingData[0]);
      // for (double dob : guess)
      // {
      //   System.out.println(dob);
      // }
      // System.out.println("Red: " + (int)(guess[0] * 100));
      // System.out.println("Orange: " + (int)(guess[1] * 100));
      // System.out.println("Yellow: " + (int)(guess[2] * 100));
      // System.out.println("Green: " + (int)(guess[3] * 100));
      // System.out.println("Blue: " + (int)(guess[4] * 100));
      // System.out.println("Purple: " + (int)(guess[5] * 100));
      // System.out.println("Pink: " + (int)(guess[6] * 100));
      // System.out.println("Brown: " + (int)(guess[7] * 100));
      // System.out.println("White: " + (int)(guess[8] * 100));
      // System.out.println("Black: " + (int)(guess[9] * 100));
      
      nn.writeToFile("src/main/resources/rgbModel");
  }


}