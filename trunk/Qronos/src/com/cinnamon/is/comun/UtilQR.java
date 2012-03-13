//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.comun;

import android.app.Activity;
import android.content.Intent;

import com.google.zxing.BarcodeFormat;

public final class UtilQR {

	public static final int REQUEST_CODE = 0x0000c0de;
	
	private final Activity activity;

	public UtilQR(Activity activity) {
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
		activity.startActivityForResult(intentScan, REQUEST_CODE);
	}

	/** Obtiene el QR
	 * @param requestCode codigo de lanzamiento
	 * @param resultCode codigo de rsultado
	 * @param intent el intent con los datos
	 * @return el raw del qr en string
	 */
	public String getQR(int requestCode, int resultCode, Intent intent) {
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
	public void generarQR(String text) {
		Intent intent = new Intent();
		intent.setAction(Props.Action.ENCODE);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    intent.putExtra("ENCODE_FORMAT", BarcodeFormat.QR_CODE.toString());
		intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
		intent.putExtra("ENCODE_DATA", text);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		activity.startActivity(intent);
	}

}
