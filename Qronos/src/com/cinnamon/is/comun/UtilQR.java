//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.google.zxing.WriterException;
import com.google.zxing.client.android.encode.QRCodeEncoder;

/**
 * Clase de ayuda para lanzar y crear QRs
 * 
 * @author Cinnamon Team
 * @version 1.1 19.04.2012
 */
public final class UtilQR {

	public static final int REQUEST_CODE = 0x0000c0de;

	private final Activity activity;

	public UtilQR(final Activity activity) {
		this.activity = activity;
	}

	/**
	 * Lanza el lectorQR
	 */
	public void lanzarQR() {
		Intent intentScan = new Intent(Props.Action.SCAN);
		intentScan.putExtra("SCAN_MODE", "QR_CODE_MODE");
		intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		this.activity.startActivityForResult(intentScan, REQUEST_CODE);
	}

	public void lanzarQRTiempo() {
		Intent intentScan = new Intent(Props.Action.SCAN);
		intentScan.putExtra("SCAN_MODE", "QR_CODE_MODE");
		intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentScan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		Bundle b = new Bundle();
		b.putBoolean(Props.Comun.CAMARABOMBA, true);
		Launch.lanzaActivity(this.activity, Props.Comun.CAMARABOMBA, b,
				REQUEST_CODE);
	}

	/**
	 * Obtiene el QR
	 * 
	 * @param requestCode
	 *            codigo de lanzamiento
	 * @param resultCode
	 *            codigo de rsultado
	 * @param intent
	 *            el intent con los datos
	 * @return el raw del qr en string
	 */
	public String getRawQR(final int requestCode, final int resultCode,
			final Intent intent) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				return intent.getStringExtra("SCAN_RESULT");
			}
		}
		return null;
	}

	/**
	 * Shares the given text by encoding it as a barcode, such that another user
	 * can scan the text off the screen of the device.
	 * 
	 * @param text
	 *            the text string to encode as a barcode
	 */
	public void verQR(final String text) {
		Intent intent = new Intent();
		intent.setAction(Props.Action.ENCODE);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		// intent.putExtra("ENCODE_FORMAT", BarcodeFormat.QR_CODE.toString());
		intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
		intent.putExtra("ENCODE_DATA", text);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		this.activity.startActivity(intent);
	}

	/**
	 * Obtiene un Bitmap del qr a partir de un texto
	 * 
	 * @param text
	 *            the text string to encode as a barcode
	 * @return el bitmap que representa el text
	 */
	public Bitmap getQR(final String text) {

		// Para establecer tamaño(copiao tal cual de zxing)
		WindowManager manager = (WindowManager) this.activity
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		int smallerDimension = width < height ? width : height;
		smallerDimension = smallerDimension * 7 / 8;

		// Para info de encode
		Intent intent = new Intent();
		intent.setAction(Props.Action.ENCODE);
		intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
		intent.putExtra("ENCODE_DATA", text);

		// Encode
		Bitmap bitmap = null;
		QRCodeEncoder qrCodeEncoder = null;
		try {
			qrCodeEncoder = new QRCodeEncoder(this.activity, intent,
					smallerDimension, false);
			bitmap = qrCodeEncoder.encodeAsBitmap();
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

}
