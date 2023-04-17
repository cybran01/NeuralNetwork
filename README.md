# NeuralNetwork
One of the simplest possible neural networks using the backpropagation algorithm, detects hand-drawn numbers from 0 to 9.
# Preparation
Download the [The EMNIST Dataset](https://www.nist.gov/itl/products-and-services/emnist-dataset) (http://arxiv.org/abs/1702.05373 for details), extract the files ```emnist-digits-train-images-idx3-ubyte.gz``` and ```emnist-digits-train-labels-idx1-ubyte.gz``` and decompress them into a folder called ```EMNIST``` in the working directory of the project.
Note that these files are ```.gz``` compressed in The EMNIST Dataset, and need to be decompressed before use.

Your working directory should look like this:
```
EMNIST
      |-- emnist-digits-train-images-idx3-ubyte
      |-- emnist-digits-train-labels-idx1-ubyte
DrawPanel.java
Main.java
MnistReader.java
Network.java
NumberDetect.java
```
# Usage
After the preparation, first execute ```Main.java``` to generate the file ```Weights.dat``` containing the weights of the neural network in binary format.
Wait for this to finish.

Then start ```NumberDetect.java``` to detect hand-drawn single digit numbers. 
