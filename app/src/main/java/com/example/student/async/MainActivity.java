package com.example.student.async;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ResponseReceiver<List<String>>, ProgressUpdater<Integer> {
    ProgressBar progressBar;
    TextView staus;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadTask(MainActivity.this).execute(((EditText) findViewById(R.id.url)).
                        getText().toString());
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        staus = (TextView) findViewById(R.id.bytes);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void update(Integer output) {
        if (progressBar != null && staus != null) {
            if (progressBar.getVisibility() == View.INVISIBLE)
                progressBar.setVisibility(View.VISIBLE);
            if (staus.getVisibility() == View.INVISIBLE) staus.setVisibility(View.VISIBLE);
            staus.setText(output);
            progressBar.setProgress(output);
        }
    }

    @Override
    public void respond(List<String> output) {
        try {
            if (Integer.parseInt(output.get(0)) > 0) {
                TextView size = ((TextView) findViewById(R.id.size));
                size.setText(output.get(0));
                size.setVisibility(View.VISIBLE);
                TextView type = ((TextView) findViewById(R.id.type));
                type.setText(output.get(1));
                type.setVisibility(View.VISIBLE);
            } else Toast.makeText(MainActivity.this, "There is no file to be downloaded over here",
                    Toast.LENGTH_SHORT).show();
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.student.async/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.student.async/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
