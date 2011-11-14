package com.pruebaimg;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.neuroph.contrib.imgrec.FractionRgbData;
import org.neuroph.contrib.imgrec.ImageSampler;
import org.neuroph.core.NeuralNetwork;

public class ImageReconUsingNnet {
	public static void main(String[] args) {
		NeuralNetwork nnet = NeuralNetwork.load("./safeturing.nnet");

		File imgFile = new File("./turing.jpg");
		BufferedImage img;
		double input[];
		try {
			img = ImageIO.read(imgFile);
			FractionRgbData imgRgb = new FractionRgbData(
					ImageSampler.downSampleImage(new Dimension(8, 8), img));
			input = imgRgb.getFlattenedRgbValues();
			nnet.setInput(input);
			nnet.calculate();
			Vector output = nnet.getOutput();
			Double answer = (Double) output.get(0);
			System.out.println((answer.toString()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
