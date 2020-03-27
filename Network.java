import java.util.ArrayList;

public class Network 
{
	private ArrayList<Double> InputLayer, OutputLayer;
	private int InputLayerSize, OutputLayerSize;
	private ArrayList<ArrayList<Double>> Weights;
	private Double LearningRate;

	public Double getLearningRate() {
		return LearningRate;
	}
	public void setLearningRate(Double learningRate) {
		LearningRate = learningRate;
	}
	public Network(int InputLayerSize,int OutputLayerSize, Double learningrate)
	{
		this.InputLayerSize = InputLayerSize;
		this.OutputLayerSize = OutputLayerSize;
		OutputLayer = new ArrayList<Double>();
		LearningRate = learningrate;
	}
	public ArrayList<Double> EvaluateNetwork()
	{
		OutputLayer.clear();
		for (int i = 0; i < OutputLayerSize; i++)
		{
			Double NewValue = 0.0;
			for (int j = 0; j < InputLayerSize; j++)
			{
				NewValue += Weights.get(j).get(i)*InputLayer.get(j);
			}
			OutputLayer.add(Sigmoid(NewValue));
		}
		return OutputLayer;

	}

	
	public ArrayList<Double> getInputLayer() {
		return InputLayer;
	}
	public void setInputLayer(ArrayList<Double> inputLayer) {
		InputLayer = inputLayer;
	}
	public int getInputLayerSize() {
		return InputLayerSize;
	}
	public void setInputLayerSize(int inputLayerSize) {
		InputLayerSize = inputLayerSize;
	}
	public int getOutputLayerSize() {
		return OutputLayerSize;
	}
	public void setOutputLayerSize(int outputLayerSize) {
		OutputLayerSize = outputLayerSize;
	}
	public ArrayList<ArrayList<Double>> getWeights() {
		return Weights;
	}
	public void setWeights(ArrayList<ArrayList<Double>> weights) {
		Weights = weights;
	}
	public ArrayList<Double> getOutputLayer() {
		return OutputLayer;
	}
	public void recomputeWeights(ArrayList<Double> supposedoutput)
	{
		ArrayList<Double> delta = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> Weights = this.getWeights();
		
		//Korrekturen berechnen 
		
		//Zuerst deltas berechnen
		for (int i = 0; i < OutputLayerSize; i++)
		{
			delta.add((OutputLayer.get(i)-supposedoutput.get(i))*OutputLayer.get(i)*(1.0-OutputLayer.get(i)));
		}
		
		//Jetzt die Weights korrigieren
		for (int i = 0; i < InputLayerSize; i++)
		{
			for (int j = 0; j < OutputLayerSize; j++)
			{
				Weights.get(i).set(j, Weights.get(i).get(j)-LearningRate*delta.get(j)*InputLayer.get(i));
			}

		}
	}

	
	public static double Sigmoid(double x)
	{
		return 1.0/(1.0+Math.exp(-x));
	}

}
