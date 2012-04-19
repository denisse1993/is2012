//
// Universidad Complutense de Madrid
// Ingenieria Informática
//
// PROYECTO: Qronos
// ASIGNATURA : Ingeniería del Software
//
package com.cinnamon.is.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinnamon.is.R;
import com.cinnamon.is.comun.UtilQR;

public class InGame extends Activity implements OnClickListener {

	Button scan, bqr;

	TextView result;

	EditText etqr;
	ImageView qr;

	private UtilQR q;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zxing);
		scan = (Button) findViewById(R.id.bScan);
		bqr = (Button) findViewById(R.id.bqr);
		etqr = (EditText) findViewById(R.id.etqr);
		result = (TextView) findViewById(R.id.tvQR);
		qr = (ImageView) findViewById(R.id.ivQR);
		scan.setOnClickListener(this);
		bqr.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bScan:
			q = new UtilQR(this);
			q.lanzarQR();
			break;
		case R.id.bqr:
			q = new UtilQR(this);
			Bitmap b = q.getQR(etqr.getText().toString());
			qr.setImageBitmap(b);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String contents = q.getRawQR(requestCode, resultCode, data);
		if (requestCode == UtilQR.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String format = data.getStringExtra("SCAN_RESULT_FORMAT");
				result.setText(contents);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancell
			}
		}

	}
}