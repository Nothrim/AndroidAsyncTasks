package com.example.student.async;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by student on 21.04.16.
 */
public class DownloadTask extends AsyncTask<String,Integer,Integer> {
private ProgressUpdater<Integer> updater;
    public static final int BLOCK_SIZE=1024;
        public DownloadTask(ProgressUpdater progressUpdater)
        {
            updater=progressUpdater;
        }
        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                URL url=new URL(buildUrl(params[0]));
                connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                File output=new File(Environment.getExternalStorageDirectory()+File.separator+url.getFile());
                if(output.exists())output.delete();
                FileOutputStream downloadStream;
                DataInputStream reader=new DataInputStream(connection.getInputStream());
                downloadStream=new FileOutputStream(output.getPath());
                byte buffer[]=new byte[BLOCK_SIZE];
                int downloaded=reader.read(buffer,0,BLOCK_SIZE);
                int progress=0;
                while (downloaded!=-1){
                    downloadStream.write(buffer,0,downloaded);
                    progress+=downloaded;
                    downloaded=reader.read(buffer,0,BLOCK_SIZE);
                    updater.update(downloaded);
                }
                downloadStream.close();
                reader.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }

        private String buildUrl(String url)
        {
            StringBuilder sb=new StringBuilder();
        try {
            if (!url.substring(0, 4).contains("http"))
                sb.append("http://");
            if (!url.substring(7, 11).contains("www"))
                sb.append("www.");
            }catch (Exception ignored){}
            finally {
                sb.append(url);
            }
            return sb.toString();
        }

}