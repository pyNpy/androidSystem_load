package example.demo.ndkdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kl.tools.load.AndroidTaskThread;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        Context context=getApplicationContext ();
        new AndroidTaskThread (getApplicationContext ()).run ();
         

    }
    
    
}
