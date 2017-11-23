package com.example.inclass05;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements DoWork.IData,Image.IData2 {

    Spinner spinner;
    ImageButton ib1,ib2,ib3,ib4;
    Button btn_get_news,btn_finish;
    LinearLayout ll_main,ll_scroll;
    final static String key="4618175514c94309920d32df4fc2bf81";
    ImageView iv;
    String spinner_value,src;
    ArrayList<Article> articles=null;
    TextView tv;

    @Override
    public void setImage(Bitmap bitmap) {
        ImageView iv= (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(bitmap);
    }

    int flag =0 ,current_index=-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib1= (ImageButton) findViewById(R.id.ib_first);
        ib2= (ImageButton) findViewById(R.id.ib_2);
        ib3= (ImageButton) findViewById(R.id.ib_3);
        ib4= (ImageButton) findViewById(R.id.ib_4);
        btn_finish= (Button) findViewById(R.id.btn_finish);

        ib1.setEnabled(false);
        ib2.setEnabled(false);
        ib3.setEnabled(false);
        ib4.setEnabled(false);
        spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.source_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        tv=new TextView(MainActivity.this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_value=adapterView.getItemAtPosition(i).toString();
                switch (spinner_value){
                    case "BBC":src="bbc-news"; break;
                    case "CNN":src="cnn"; break;
                    case "Sky News":src="sky-news"; break;
                    case "Buzzfeed":src="buzzfeed"; break;
                    case "ESPN":src="espn"; break;
                    case "Select":src="Select"; break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_get_news= (Button) findViewById(R.id.btn_get_news);
        btn_get_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnectedOnline()){

                    if(src.equals("Select")){
                        Toast.makeText(MainActivity.this,"Please select a source",Toast.LENGTH_SHORT).show();
                    }else{
                        new DoWork(MainActivity.this).execute("https://newsapi.org/v1/articles?apiKey="+key+"&source="+src);
                        ib1.setEnabled(true);
                        ib2.setEnabled(true);
                        ib3.setEnabled(true);
                        ib4.setEnabled(true);
                    }

                }else{
                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_index==0){
                    Toast.makeText(MainActivity.this,"Reached end of list",Toast.LENGTH_SHORT).show();
                }else{
                    current_index=0;
                    displayArticle(articles.get(0));
                }

            }
        });
        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_index==articles.size()-1){
                    Toast.makeText(MainActivity.this,"Reached end of list",Toast.LENGTH_SHORT).show();
                }else{
                    current_index=articles.size()-1;
                    displayArticle(articles.get(current_index));
                }
            }
        });
        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_index==0){
                    Toast.makeText(MainActivity.this,"Reached end of list",Toast.LENGTH_SHORT).show();
                }else{
                    current_index--;
                    displayArticle(articles.get(current_index));

                }
            }
        });
        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current_index==articles.size()-1){
                    Toast.makeText(MainActivity.this,"Reached end of list",Toast.LENGTH_SHORT).show();
                }else{
                    current_index++;
                    displayArticle(articles.get(current_index));

                }
            }
        });






    }
    public boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;

    }
    public void layoutgenerator(ArrayList<Article> a){
        articles=a;
        current_index=0;
        displayArticle(articles.get(0));
    }
    public void displayArticle(Article a){
        ll_main= (LinearLayout) findViewById(R.id.LL_main);
        new Image(MainActivity.this).execute(a.url_to_img);
        ll_scroll= (LinearLayout) findViewById(R.id.ll_scroll);
        Log.d("demo",a.getAuthor());
        tv.setText(a.title+"\nAuthor: "+a.author+"\nPublished on: "+a.published_at+"\n\nDescription:\n"+a.description);
        if(flag==0){
            ll_scroll.addView(tv);
            flag=1;
        }
    }

}
