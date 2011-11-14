package com.pruebaimg;

import java.io.ObjectInputStream;

import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;

public class ImageRecognitionAndroidResult {

	 public static void main(String[] args) {
		 
	    // load trained neural network saved with easyNeurons (specify existing neural network file here)
	    NeuralNetwork nnet = NeuralNetwork.load("./pruebaturing.nnet"); // load trained neural network saved with easyNeurons 
	   
	     nnet.removePlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME); // remove the image recognition plugin from neural network
	     
	     //se supone que quitandole el plugin esta red neuronal funcionaria en android...
	     nnet.save("./safeturing.nnet");
	 }}
	
