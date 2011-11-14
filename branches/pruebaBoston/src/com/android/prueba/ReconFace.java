package com.android.prueba;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.neuroph.contrib.imgrec.ImageRecognitionPlugin;
import org.neuroph.contrib.imgrec.ImageSizeMismatchException;
import org.neuroph.core.NeuralNetwork;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ReconFace extends Activity implements View.OnClickListener {

	Uri pathURI;
	private String path;

	Button b;
	ImageView iv;
	TextView tv;

	final static int CAMERA_PHOTO_CODE = 0;
	Bitmap bmp;

	NeuralNetwork nnet;
	ImageRecognitionPlugin imageRecognition;
	HashMap<String, Double> output;
	Thread big;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recon);
		initialize();
		
		big = new Thread(new ThreadGroup("bigTuringG"),this.big,"bigTuring",204800) {
			
			public void run() {
				InputStream is = null;
				int size = -1;
				AssetManager assetManager = getAssets();
				try {
					is = assetManager.open("safeturing.nnet");
					size = is.available();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (size > 0) {
					nnet = NeuralNetwork.load(is);
					imageRecognition = (ImageRecognitionPlugin) nnet
							.getPlugin(ImageRecognitionPlugin.IMG_REC_PLUGIN_NAME);
				}
			}
		};
		
		big.start();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		iv = (ImageView) findViewById(R.id.ivReturnedPicRecon);
		b = (Button) findViewById(R.id.bTakePicRecon);
		tv = (TextView) findViewById(R.id.tvRecon);
		b.setOnClickListener(this);
		path = Environment.getExternalStorageDirectory() + "/turing.jpg";
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bTakePicRecon:
			pathURI = Uri.fromFile(new File(path));// crea la ruta pal file

			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_OUTPUT, pathURI);// le dice onde
															// guardarla
			startActivityForResult(i, CAMERA_PHOTO_CODE);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			if (requestCode == CAMERA_PHOTO_CODE) {
				if (data != null) {
					/**
					 * /** A partir del nombre del archivo ya definido lo
					 * buscamos y creamos el bitmap para el ImageView
					 */

					iv.setImageBitmap(BitmapFactory.decodeFile(path));

					/**
					 * Para guardar la imagen en la galer’a, utilizamos una
					 * conexi—n a un MediaScanner
					 */
					new MediaScannerConnectionClient() {
						private MediaScannerConnection msc = null;
						{
							msc = new MediaScannerConnection(
									getApplicationContext(), this);
							msc.connect();
						}

						public void onMediaScannerConnected() {
							msc.scanFile(path, null);
						}

						public void onScanCompleted(String path, Uri uri) {
							msc.disconnect();
						}
					};

					/*BufferedImage bf = new BufferedImage(bmp.getWidth(),
							bmp.getHeight(), BufferedImage.TYPE_INT_ARGB); //
					// bf.setRGB(0, 0, bf.getWidth(), bf.getHeight(),
					// rgbArray,offset, scansize)
					for (int i = 0; i < bf.getWidth(); i++) {
						for (int j = 0; i < bf.getHeight(); j++) {
							bf.setRGB(i, j, bmp.getPixel(i, j));
						}
					}

					try {
						output = imageRecognition.recognizeImage(bf);
					} catch (ImageSizeMismatchException e) {
						e.printStackTrace();
					}

					tv.setText(output.toString());*/
					HashMap<String, Double> perico = null;
					try {
						perico = imageRecognition
								.recognizeImage(new File(path));
					} catch (ImageSizeMismatchException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(perico.toString());

				}
			}
		}
	}
}
