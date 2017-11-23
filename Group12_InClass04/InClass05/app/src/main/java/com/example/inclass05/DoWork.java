package com.example.inclass05;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by ranga on 2/6/2017.
 */

public class DoWork extends AsyncTask<String,Void,String>{

    IData activity;
    ArrayList<Article> articles=null;

    public DoWork(IData activity){
        this.activity=activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            articles=JSonParserForNews.parser(s);
            activity.layoutgenerator(articles);
            Log.d("demo",articles.get(0).title+articles.get(0).published_at);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader reader=null;

        try {
            URL url=new URL(strings[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line="";
            StringBuilder sb=new StringBuilder();
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    static public interface IData{
        public void layoutgenerator(ArrayList<Article> a);
    }

}
