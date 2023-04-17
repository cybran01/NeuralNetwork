import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] argv) {
		// Initialize (MNIST and) EMNIST dataset/s
		int[] labels = mnist.MnistReader.getLabels("./EMNIST/emnist-digits-train-labels-idx1-ubyte");
		List<int[][]> imgs = mnist.MnistReader.getImages("./EMNIST/emnist-digits-train-images-idx3-ubyte");

		ArrayList<ArrayList<Double>> Weights = new ArrayList<ArrayList<Double>>();

		// Initialize all weights of Network with 0
		for (int i = 0; i < 28 * 28; i++) {
			Weights.add(new ArrayList<Double>());
			for (int j = 0; j < 10; j++) {
				Weights.get(i).add(0.0);
			}
		}

		Network NeuroNet = new Network(28 * 28, 10, 0.05);
		NeuroNet.setWeights(Weights);

		// Start the training phase
		for (int i = 0; i < 200000; i++) {
			ArrayList<Double> InputLayer = new ArrayList<Double>();

			// Read in training image from EMNIST dataset
			for (int j = 0; j < 28 * 28; j++) {
				Double CurValue = Sigmoid((double) imgs.get(i)[(int) ((double) j / 28.0)][j % 28]);
				InputLayer.add(CurValue);
			}

			// Evaluate the network
			NeuroNet.setInputLayer(InputLayer);
			NeuroNet.EvaluateNetwork();

			// Construct the correct output
			ArrayList<Double> SupposedOutput = new ArrayList<Double>();

			for (int j = 0; j < 10; j++) {
				SupposedOutput.add(0.0);
			}
			SupposedOutput.set(labels[i], 1.0);

			// Train the network
			NeuroNet.recomputeWeights(SupposedOutput);

			if (i % 10000 == 0)
				System.out.print(".");

		}
		System.out.println();

		// Verification Phase
		int counter = 0;
		for (int i = 200000; i < 240000; i++) {
			ArrayList<Double> InputLayer = new ArrayList<Double>(), OutputLayer;

			// Read in verification image from MNIST data set
			for (int j = 0; j < 28 * 28; j++) {
				Double CurValue = Sigmoid((double) imgs.get(i)[(int) ((double) j / 28.0)][j % 28]);
				InputLayer.add(CurValue);
			}

			// Evaluate the network
			NeuroNet.setInputLayer(InputLayer);
			OutputLayer = NeuroNet.EvaluateNetwork();

			// Find the result with highest network confidence
			int bestguess = 0;
			for (int j = 1; j < 10; j++) {
				if (OutputLayer.get(j) > OutputLayer.get(bestguess))
					bestguess = j;
			}

			// Check if the determined result is correct
			if (labels[i] == bestguess) {
				counter++;
			}

		}
		System.out.println("Ratio: " + counter / 40000.0);

		try {
			FileOutputStream fos = new FileOutputStream("Weights.dat");
			DataOutputStream dos = new DataOutputStream(fos);

			ArrayList<ArrayList<Double>> saveWeights = NeuroNet.getWeights();
			for (int i = 0; i < 28 * 28; i++)
				for (int j = 0; j < 10; j++)
					dos.writeDouble(saveWeights.get(i).get(j));

			dos.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private static double Sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

}
