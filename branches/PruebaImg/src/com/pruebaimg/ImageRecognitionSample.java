package com.pruebaimg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.core.NeuralNetwork;

public class ImageRecognitionSample {

	 public static void main(String[] args) {
		 
	    // load trained neural network saved with easyNeurons (specify existing neural network file here)
	    NeuralNetwork nnet = NeuralNetwork.load("./pruebaTuring.nnet"); // load trained neural network saved with easyNeurons 
	    // get the image recognition plugin from neural network
	    ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME); // get the image recognition plugin from neural network

	    try {
	         // image recognition is done here (specify some existing image file)
	        HashMap<String, Double> output = imageRecognition.recognizeImage(new File("turing.jpg"));
	        System.out.println(output.toString());
	        HashMap<String, Double> perico = imageRecognition.recognizeImage(new File("steve-jobs.jpg"));
	        System.out.println(perico.toString());
	    } catch(IOException ioe) {
	        ioe.printStackTrace();
	    }
	 }}
	
