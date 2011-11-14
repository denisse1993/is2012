package com.cinnamon.is;

import com.cinnamon.is.custom.appeareance.CustomButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainMenu extends Activity implements OnClickListener {

	Button bNuevaPartida, bOpciones, bAyuda, bSalir;

	MediaPlayer menuTheme;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		inicializar();
	}

	/** No toqueis esto que ahora no lo necesitamos pero puede que en un futuro si
	 * es es layout del main pero hecho en java o parte de él.
	 */
	/*private View getContentView() {
		LinearLayout layoutMain = new LinearLayout(this);
		layoutMain.measure(android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.FILL_PARENT);
		layoutMain.setOrientation(LinearLayout.VERTICAL);
		layoutMain.setBackgroundResource(R.drawable.menu);
		
		LinearLayout layoutText = new LinearLayout(this);
		layoutText.setGravity(Gravity.CENTER);
		layoutText.measure(android.widget.LinearLayout.LayoutParams.FILL_PARENT,144);
		layoutText.setOrientation(LinearLayout.VERTICAL);
		
		TextView textTitulo = new TextView(this);
		textTitulo.setWidth(android.widget.LinearLayout.LayoutParams.FILL_PARENT);
		textTitulo.setHeight(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		textTitulo.setText("Hola");
		textTitulo.setTextSize(100);
		textTitulo.setTextColor(Color.WHITE);
		
		CustomButton bNuevaPartida = new CustomButton(this,R.drawable.b_nueva_partida,"bNuevaPartida");
		bNuevaPartida = new CustomButton(this, R.drawable.b_nueva_partida, "bNuevaPartida");
		bNuevaPartida.setOnClickListener(this);
		bNuevaPartida.setGravity(Gravity.CENTER);
		
		
		
		//layoutText.addView(textTitulo);
		layoutText.addView(bNuevaPartida);
		layoutMain.addView(layoutText);
		
		return layoutMain;
	}*/

	private void inicializar() {
		menuTheme = MediaPlayer.create(MainMenu.this, R.raw.menu);
		menuTheme.start();
		
		bNuevaPartida = (Button) findViewById(R.id.bNuevaPartida);
		bOpciones = (Button) findViewById(R.id.bOpciones);
		bAyuda = (Button) findViewById(R.id.bAyuda);
		bSalir = (Button) findViewById(R.id.bSalir);		
		
		bNuevaPartida.setOnClickListener(this);
		bOpciones.setOnClickListener(this);
		bAyuda.setOnClickListener(this);
		bSalir.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {		
		switch (v.getId()) {
		case R.id.bNuevaPartida:
			menuTheme.release();
			
			MediaPlayer openingTheme = MediaPlayer.create(MainMenu.this, R.raw.opening);
			openingTheme.start();
						
			Intent iInGame = new Intent(Intents.Action.INGAME);
			startActivity(iInGame);
			break;

		case R.id.bOpciones:

			break;

		case R.id.bAyuda:

			break;

		case R.id.bSalir:
			finish();
			break;
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		menuTheme.release();
		
	}
	
	

}
