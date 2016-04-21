package com.example.student.async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ResponseReceiver<List<String>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.getInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckInformationTask(MainActivity.this)
                        .execute(((EditText) findViewById(R.id.url)).getText().toString());
            }
        });
    }

    @Override
    public void respond(List<String> output) {
        try {
            TextView size=((TextView) findViewById(R.id.size));
            size.setText(output.get(0));
            size.setVisibility(View.VISIBLE);
            TextView type=((TextView) findViewById(R.id.type));
            type.setText(output.get(1));
            type.setVisibility(View.VISIBLE);
        }catch (IndexOutOfBoundsException e){
            System.err.println(e.getMessage());
        }
    }
}
