import org.ejml.simple.SimpleMatrix;

import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;

public class Network {

  // Example program to test the basic neural network by letting it solve XOR
  public static void main(String args[]){

      Dataset d = new Dataset("src/main/resources/data.csv");

      double[][] trainingDataInputs = d.getInputs();

      double[][] trainingDataTargets = d.getOutputs();
      //NeuralNetwork nn = NeuralNetwork.readFromFile("src/main/resources/rgbModel.json");
      NeuralNetwork nn = new NeuralNetwork(3, 2, 9, 10);
      nn.setActivationFunction(ActivationFunction.SIGMOID);
      nn.setLearningRate(0.01);
      for (int i = 0; i < 5000000; i++) {
          // training in random order
          int random = (int)(Math.random() * d.getLength());
          nn.train(trainingDataInputs[random], trainingDataTargets[random]);
          System.out.println((float)(i) / 5000000 * 100 + "%");
          //if (i % 1000000 == 0) nn.mutate(0.1);
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
          if (answer[h] == 1) { correctIndex = h; }
        }
        if (index == correctIndex) { correct++; }
      }
      System.out.println("accuracy: " + correct / d.getLength());

      nn.writeToFile("src/main/resources/rgbModel");
  }


}