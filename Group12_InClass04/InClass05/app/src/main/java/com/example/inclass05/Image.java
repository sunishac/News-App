package com.example.inclass05;

/**
 * Created by ranga on 2/6/2017.
 */




        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;

        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;


public class Image extends AsyncTask<String,Void,Bitmap> {
    IData2 activity;
    public Image(IData2 activity){
        this.activity=activity;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.setImage(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Bitmap img= BitmapFactory.decodeStream(con.getInputStream());
            return img;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    static public interface IData2{
        public void setImage(Bitmap bitmap);
    }
}

