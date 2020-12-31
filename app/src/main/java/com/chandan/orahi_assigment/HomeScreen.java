package com.chandan.orahi_assigment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    StringBuilder stringBuilder;
    SharedPreferences sharedPreferences;
    ArrayList<TextView> textViewArrayList;
    ArrayList<ImageView> imageViewArrayList;
    public class DownloadWeb extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    stringBuilder.append(current);
                    data = reader.read();
                }
                sharedPreferences.edit().putString("jsonStringData", stringBuilder.toString()).apply();
                Log.i("message", "data sync sucessfully");
                return stringBuilder.toString();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String data = jsonObject.getString("data");
                JSONArray jsonArray = new JSONArray(data);
                ArrayList<Integer> arrayList = new ArrayList<>();
                for(int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                    arrayList.add(Integer.parseInt(jsonObject1.getString("stat")));
                    Log.i(String.valueOf(i), jsonObject1.getString("month")+"  "+jsonObject1.getString("stat"));
                }
                for(int i = 0; i < 12 ; i++){
                    textViewArrayList.get(i).setText(String.valueOf(arrayList.get(i)));
                    imageViewArrayList.get(i).setScrollY(-730+arrayList.get(i)*2);
                }
            }catch (Exception e){
                Log.i("Error", e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        stringBuilder = new StringBuilder();
        sharedPreferences = this.getSharedPreferences("com.chandan.orahi_assigment", Context.MODE_PRIVATE);
        imageViewArrayList = new ArrayList<>();
        imageViewArrayList.add((ImageView) findViewById(R.id.bar1));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar2));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar3));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar4));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar5));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar6));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar7));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar8));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar9));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar10));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar11));
        imageViewArrayList.add((ImageView) findViewById(R.id.bar12));
        textViewArrayList = new ArrayList<>();
        textViewArrayList.add((TextView)findViewById(R.id.stat1));
        textViewArrayList.add((TextView)findViewById(R.id.stat2));
        textViewArrayList.add((TextView)findViewById(R.id.stat3));
        textViewArrayList.add((TextView)findViewById(R.id.stat4));
        textViewArrayList.add((TextView)findViewById(R.id.stat5));
        textViewArrayList.add((TextView)findViewById(R.id.stat6));
        textViewArrayList.add((TextView)findViewById(R.id.stat7));
        textViewArrayList.add((TextView)findViewById(R.id.stat8));
        textViewArrayList.add((TextView)findViewById(R.id.stat9));
        textViewArrayList.add((TextView)findViewById(R.id.stat10));
        textViewArrayList.add((TextView)findViewById(R.id.stat11));
        textViewArrayList.add((TextView)findViewById(R.id.stat12));
        if(isOnline()) {
            String url = "https://demo5636362.mockable.io/stats";
            DownloadWeb downloadWeb = new DownloadWeb();
            downloadWeb.execute(url);
        }else{
            String jsonStringData = sharedPreferences.getString("jsonStringData","");
            if(jsonStringData.isEmpty()) {
                Toast.makeText(HomeScreen.this, "First connect to internet to sync data", Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONObject jsonObject = new JSONObject(jsonStringData);
                    String data = jsonObject.getString("data");
                    JSONArray jsonArray = new JSONArray(data);
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                        arrayList.add(Integer.parseInt(jsonObject1.getString("stat")));
                        Log.i(String.valueOf(i), jsonObject1.getString("month")+"  "+jsonObject1.getString("stat"));
                    }
                    for(int i = 0; i < 12 ; i++){
                        textViewArrayList.get(i).setText(String.valueOf(arrayList.get(i)));
                        imageViewArrayList.get(i).setScrollY(-730+arrayList.get(i)*2);
                    }
                }catch (Exception e){
                    Log.i("Error", e.getMessage());
                }
            }
            Log.i("error", "offline");
        }
    }
    protected boolean isOnline() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI && activeNetwork.isConnected()) {
                haveConnectedWifi = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE && activeNetwork.isConnected()) {
                haveConnectedMobile = true;
            }
        }
        return (haveConnectedWifi || haveConnectedMobile);
    }
}