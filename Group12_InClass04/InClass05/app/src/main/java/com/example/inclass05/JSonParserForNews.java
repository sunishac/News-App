package com.example.inclass05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by ranga on 2/6/2017.
 */

public class JSonParserForNews {
    public static ArrayList<Article> parser(String in) throws JSONException {
        ArrayList<Article> articles=new ArrayList<Article>();

        JSONObject root=new JSONObject(in);
        JSONArray first=root.getJSONArray("articles");
        for(int i=0;i<first.length();i++){
            JSONObject articleJSONObject=first.getJSONObject(i);
            Article a=new Article();
            if(articleJSONObject.getString("author")!=null){
                a.setAuthor(articleJSONObject.getString("author"));
            }else{
                a.setAuthor("-");
            }
            if(articleJSONObject.getString("title")!=null){
                a.setTitle(articleJSONObject.getString("title"));
            }else{
                a.setTitle("-");
            }
            if(articleJSONObject.getString("description")!=null){
                a.setDescription(articleJSONObject.getString("description"));
            }else{
                a.setDescription("-");
            }
            if(articleJSONObject.getString("url")!=null){
                a.setUrl(articleJSONObject.getString("url"));
            }else{
                a.setUrl("-");
            }
            if(articleJSONObject.getString("urlToImage")!=null){
                a.setUrl_to_img(articleJSONObject.getString("urlToImage"));
            }else{
                a.setUrl_to_img("-");
            }
            if(articleJSONObject.getString("publishedAt")!=null){
                a.setPublished_at(articleJSONObject.getString("publishedAt"));
            }else{
                a.setPublished_at("-");
            }
            articles.add(a);

        }
        return articles;
    }

}
