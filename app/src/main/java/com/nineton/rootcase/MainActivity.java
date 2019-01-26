package com.nineton.rootcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hook.rooter.CheckRootTask;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);

        CheckRootTask checkRootTask = new CheckRootTask(this, new CheckRootTask.OnCheckRootFinishedListener() {
            @Override
            public void onCheckRootFinished(boolean isRooted) {
                Log.d("CheckRootTask", "device is root : " + isRooted);
            }
        });
        checkRootTask.execute(true);
    }

}
