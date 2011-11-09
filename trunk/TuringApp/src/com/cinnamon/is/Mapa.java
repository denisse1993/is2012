package com.cinnamon.is;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class Mapa extends Activity implements OnClickListener{

	// Constantes para controlar que actividad tratar en onActivityResult
	private static final int cMapa = 0;
	private static final int cCamara = 1;
	private static final int cMochila = 2;
	private static final int cMinijuego = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.menumapa, menu);		    
	     return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    
	        case R.id.bCamara:
	        	Intent iScan = new Intent(Intents.Action.SCAN);
				iScan.putExtra("SCAN_MODE", "QR_CODE_MODE"); //a–ade informacion extra al intent
				startActivityForResult(iScan, cCamara);
	            return true;
	        case R.id.bVolver:
	        
	            return true;
	        case R.id.bMochila:
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
	}

}
