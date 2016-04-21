package com.example.student.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 14.04.16.
 */
public class CheckInformationTask extends AsyncTask<String,Integer,List<String>> {
    private ResponseReceiver<List<String>> receiver;
    public CheckInformationTask(ResponseReceiver responseReceiver)
    {
        receiver=responseReceiver;
    }
    @Override
    protected List<String> doInBackground(String... params) {
        List<String> result=new ArrayList<>();
        HttpURLConnection connection=null;
        try {
            connection=(HttpURLConnection)(new URL(params[0]).openConnection());
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            result.add(Integer.toString(connection.getContentLength()));
            result.add(connection.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        receiver.respond(strings);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
