package com.cinnamon.is.minijuegos.end;

import android.app.Activity;
import android.os.Bundle;

public class EndMJ extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
       
    }

}
