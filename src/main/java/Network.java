import org.ejml.simple.SimpleMatrix;

import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;



public class Network {
  double[][] inputs, targets; 
  NeuralNetwork nn;
  Dataset d;
  private void init()
  {
    d = new Dataset("src/main/resources/bigData.csv");

    inputs = d.getInputs();
    System.out.println("IN DONE");
    targets = d.getOutputs();
    System.out.println("OUT DONE");

    nn = new NeuralNetwork(3, 2, 9, 10);
    nn.setActivationFunction(ActivationFunction.SIGMOID);
    nn.setLearningRate(0.01);
    System.out.println("DONE");
  }
  private void train(int iters)
  {
    for (int i = 0; i < iters; i++) {
      // training in random order
      int random = (int)(Math.random() * d.getLength());
      nn.train(inputs[random], targets[random]);
      if (i % (iters / 20) == 0) 
      {
        System.out.println((float)(i) / iters * 100 + "%");
      }
      //if (i % 1000000 == 0) nn.mutate(0.1);
    }
    nn.writeToFile();
  }
  private double getAccuracy()
  {
    double correct = 0;
      for (int i = 0; i < d.getLength(); i++) 
      {
        double[] guess = nn.guess(inputs[i]);
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
        double[] answer = targets[i];
        int correctIndex = 0;
        for (int h = 0; h < 10; h++)
        {
          if (answer[h] == 1) { correctIndex = h; }
        }
        if (index == correctIndex) { correct++; }
      }
      return correct / d.getLength();
  }
  // Example program to test the basic neural network by letting it solve XOR
  public static void main(String args[]){
      Network network = new Network();
      network.init();
      network.train(2000000);
      System.out.println(network.getAccuracy());
      
  }


}