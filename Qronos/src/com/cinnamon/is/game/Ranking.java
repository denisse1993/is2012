package com.cinnamon.is.game;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinnamon.is.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class Ranking extends Activity{

	private String JSON;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking);
		JSON = getIntent().getStringExtra("JSON Valor");
		toJSONArray(JSON);
	}
	public boolean toJSONArray (String response) {
    	String nick;
    	String mj1;
    	String mj2;
    	String mj3;
    	JSONArray jArray;
    	try{
    	      jArray = new JSONArray(response);
    	      JSONObject json_data=null;
    	      for(int i=0;i<jArray.length();i++){
    	    	  	 int j = i+1;
    	             json_data = jArray.getJSONObject(i);
    	             nick=json_data.getString("NICK");
    	             mj1=json_data.getString("MJ1");
    	             mj2=json_data.getString("MJ2");
    	             mj3=json_data.getString("MJ3");
    	             setTabla(j,nick,mj1,mj2,mj3);
    	         }
    	      }
    	      catch(JSONException e1){
    	    	  Toast.makeText(getBaseContext(), "Error" ,Toast.LENGTH_LONG).show();
    	      } catch (ParseException e1) {
    				e1.printStackTrace();
    		}
		return false;
    	}
    	
    public void setTabla(int i,String nick, String mj1, String mj2, String mj3){
    	if (i ==1){
    		TextView text1 = (TextView) findViewById(R.id.textView1);
    		text1.setText(nick);
    		TextView text2 = (TextView) findViewById(R.id.textView2);
    		text2.setText(mj1);
    		TextView text3 = (TextView) findViewById(R.id.textView3);
    		text3.setText(mj2);
    		TextView text4 = (TextView) findViewById(R.id.textView4);
    		text4.setText(mj3);
    	}
    	if (i==2){
    		TextView text1 = (TextView) findViewById(R.id.textView5);
    		text1.setText(nick);
    		TextView text2 = (TextView) findViewById(R.id.textView6);
    		text2.setText(mj1);
    		TextView text3 = (TextView) findViewById(R.id.textView7);
    		text3.setText(mj2);
    		TextView text4 = (TextView) findViewById(R.id.textView8);
    		text4.setText(mj3);
    	}
    	//handler=new Handler();
    	
    	//handler.postDelayed(r, 1000);
    	// setContentView(R.layout.table);
    }

}
