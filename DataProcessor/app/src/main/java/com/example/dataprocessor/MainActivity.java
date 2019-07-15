package com.example.dataprocessor;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends Activity {

    double[] input, output;
    long t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create data arrays
        input = new double[1024];
        output = new double[1024];

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        //Start processing
        t1 = SystemClock.elapsedRealtime();
        process(input, output, 1024);
        t2 = SystemClock.elapsedRealtime();
        Log.d("MainActivity","Process Time: "+ (t2-t1));
        Log.d("MainActivity", "Output: "+ Arrays.toString(output));
    }


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
    public native int process(double[] input, double[] output, int len);
}
