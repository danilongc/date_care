package com.it.dnc.keoh.instagram.tasks;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.it.dnc.keoh.instagram.service.PublicInstagramApiCreator;
import com.it.dnc.keoh.instagram.service.PublicInstagramService;
import com.it.dnc.keoh.util.JsonUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dnc on 14/08/18.
 */

public class InstagramGetProfilePictureTask extends AsyncTask<String, Void, byte[]> {

    private PublicInstagramService instagramService;
    private ImageView profilePic;
    private Exception exception;

    public InstagramGetProfilePictureTask(ImageView profilePic){
        this.profilePic = profilePic;
    }


    @Override
    protected byte[] doInBackground(String... strings) {
        try {

            final String userId = strings[0];
            createAPI();
            final Call<ResponseBody> instaUser = instagramService.getUserById( userId);

            instaUser.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String html = "";
                    try {
                        html = response.body().string();
                    } catch (IOException e) {
                        exception = e;
                    }
                    String profile_picture =  extractProfilePicture(html);
                    RequestCreator load = Picasso.get().load(profile_picture);
                    //load.into(getTarget(String.format("prof_pic_%s", userId)));

                    load.into(profilePic);
                    //Bitmap bm=((BitmapDrawable)profilePic.getDrawable()).getBitmap();



                    //Picasso.get().load(String.format("prof_pic_%s", instaUser));
                    //load.into(profilePic);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    String teste = "";
                }
            });

        }catch (Exception e){
            this.exception = e;
            return null;
        }

        return null;
    }

    private static Target getTarget( final String fileName){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try {

                            File rootPath = new File(Environment.getExternalStorageDirectory(), "keoh");
                            if(!rootPath.exists()) {
                                rootPath.mkdirs();
                            }

                            File dataFile = new File(rootPath, fileName + ".jpg");

                            FileOutputStream ostream = new FileOutputStream(dataFile,false);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();

                            //profilePic.setImageBitmap(bitmap);


                        } catch (IOException e) {
                             Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }


    private String extractProfilePicture(String html){


        String json = null;
        LinkedHashMap linkedHashMap = null;
        String regex = "(window._sharedData = )\\{*.*\\}";
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher =  p.matcher(html);
        if(matcher.find()){
            json =  matcher.group().replace("window._sharedData = ", "");

            linkedHashMap = JsonUtil.jsonToObject(json, LinkedHashMap.class);
        }

        return ((LinkedHashMap)((LinkedHashMap)((LinkedHashMap)((List)((LinkedHashMap)linkedHashMap.get("entry_data")).get("ProfilePage")).get(0)).get("graphql")).get("user")).get("profile_pic_url_hd").toString();

    }

    public Exception getException() {
        return exception;
    }

    private void createAPI(){
        instagramService = PublicInstagramApiCreator.getRetrofitInstance().create(PublicInstagramService.class);
    }

}
