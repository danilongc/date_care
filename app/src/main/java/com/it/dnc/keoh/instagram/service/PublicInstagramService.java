package com.it.dnc.keoh.instagram.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PublicInstagramService {

    @GET("{userId}/")
    Call<ResponseBody> getUserById(@Path("userId") String userId);

}





/*public static final String GET_USER_SELF = "/users/self/";
    public static final String GET_USER_BY_ID = "/users/{userId}/";
    public static final String GET_USER_SELF_RECENT_MEDIA = "/users/self/media/recent/";
    public static final String GET_USER_RECENT_MEDIA_BY_ID = "/users/{userId}/media/recent/";
    public static final String GET_USER_SELF_MEDIA_LIKED = "users/self/media/liked/";
    public static final String GET_USER_SEARCH = "users/search/";*/

//Token : "1989964257.227f806.e40115244d8e493087e656bb7d7d0c15"
//#Instagram
//instagram.clientId=227f806dc2ec4f0ca1dadd2bb1757da5
//instagram.endpoint=https://api.instagram.com/v1/