package com.example.kavehkhorram.alpha;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity2 extends ActionBarActivity {
    private static final String USER_AGENT = "Mozilla/5.0";
   // ArrayList store = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextView) findViewById(R.id.input));
                TextView de = ((TextView) findViewById(R.id.debug));
                de.setText("Getting user text");
                CharSequence a = tv.getText(); // Get user text
                String CTS = a.toString();
                de.setText("Adding to arraylist");
                                                    // Send Data to PHP server


               new MyAsyncTask().execute(CTS);


                de.setText("Going to convert from String to Byte");
                StringToByte(a.toString());

            }
        });
    }
    private class MyAsyncTask extends AsyncTask<String, Void, Object>
    {
        @Override
        protected Object doInBackground(String... params) {
            try {
                String url = "http://nfc.kkhorram.info/add.php?item=" + params[0];
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet(url));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    String responseString = out.toString();
                    out.close();
                    //..more logic
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }





            } catch(MalformedURLException E){
                System.out.println("Malformed URL Connection");
            } catch(IOException i){
                System.out.println("IO Exception");
            }
            return null;
        }
    }
    public byte[] StringToByte(String a){ // convert String to Byte, then return
        TextView tv = ((TextView)findViewById(R.id.debug));
        byte[] b = a.getBytes(Charset.forName("UTF-8"));
        tv.setText(b + "  <- Byte");
        return b;



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
