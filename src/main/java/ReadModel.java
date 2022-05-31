import basicneuralnetwork.NeuralNetwork;
import basicneuralnetwork.activationfunctions.ActivationFunction;
import basicneuralnetwork.utilities.InterfaceAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.ejml.data.Matrix;
import org.ejml.simple.SimpleOperations;
import java.io.InputStreamReader;



public class ReadModel {
  public static NeuralNetwork getModel() {
    NeuralNetwork nn = null;

    Gson gson = getGsonBuilder().create();
    JsonReader jsonReader = new JsonReader(new InputStreamReader(NetworkInterface.class.getResourceAsStream("rgbModel.json")));
    nn = gson.fromJson(jsonReader, NeuralNetwork.class);

    return nn;
  }

  // Get a GsonBuilder-object with all the needed TypeAdapters added
  private static GsonBuilder getGsonBuilder(){
      GsonBuilder gsonBuilder = new GsonBuilder();

      gsonBuilder.registerTypeAdapter(ActivationFunction.class, new InterfaceAdapter<ActivationFunction>());
      gsonBuilder.registerTypeAdapter(Matrix.class, new InterfaceAdapter<Matrix>());
      gsonBuilder.registerTypeAdapter(SimpleOperations.class, new InterfaceAdapter<SimpleOperations>());
      gsonBuilder.setPrettyPrinting();

      return gsonBuilder;
  }
}
