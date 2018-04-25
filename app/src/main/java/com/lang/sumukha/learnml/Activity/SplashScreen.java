package com.lang.sumukha.learnml.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lang.sumukha.learnml.Interfaces.ContentsDownloadListner;
import com.lang.sumukha.learnml.LearnML;
import com.lang.sumukha.learnml.Models.LessonQuestionModel;
import com.lang.sumukha.learnml.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity implements ContentsDownloadListner {

    ProgressDialog progressDialog;
    ContentsDownloadListner allContents;
    int lessonSize;
    JSONArray lessonArray;
    ArrayList<LessonQuestionModel> lessonQuestionModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setMessage("Loading...");
        progressDialog.create();
        lessonQuestionModels = new ArrayList<>();
        LoadContents loadContents = new LoadContents(SplashScreen.this,SplashScreen.this);
        loadContents.execute();


    }

    @Override
    public void allContents(JSONObject response) {
        try {
            lessonArray = response.getJSONArray("lesson_data");
            lessonSize = lessonArray.length();
        }catch (Exception e){
            e.printStackTrace();
        }

        for(int i=0; i<lessonSize;i++){
            JSONObject singleObj = new JSONObject();
            LessonQuestionModel lessonQuestionModel = new LessonQuestionModel();
            try {
                singleObj = lessonArray.getJSONObject(i);
                lessonQuestionModel.setLessonConceptName(singleObj.getString("conceptName"));
                lessonQuestionModel.setLessonPronunciation(singleObj.getString("pronunciation"));
                lessonQuestionModel.setLessonTargetScript(singleObj.getString("targetScript"));
                lessonQuestionModel.setLessonType(singleObj.getString("type"));
                lessonQuestionModel.setLessonURL(singleObj.getString("audio_url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lessonQuestionModels.add(lessonQuestionModel);
        }
        progressDialog.dismiss();
        MainActivity.lessonQuestionModels = lessonQuestionModels;
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    public class LoadContents extends AsyncTask<Void,Void,Void> {
        Context context;
        ContentsDownloadListner downloadListner;

        public LoadContents(Context context, ContentsDownloadListner downloadListner) {
            this.context = context;
            this.downloadListner = downloadListner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progressDialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String  REQUEST_TAG = "volleyJsonObjectRequest";
            String  REQUEST_URL = "http://www.akshaycrt2k.com/getLessonData.php";
            JsonObjectRequest jsonObjectReq = new JsonObjectRequest(REQUEST_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            downloadListner.allContents(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("VolleyError", "Error: " + error.getMessage());
                    progressDialog.hide();
                }
            });
            // Adding JsonObject request to request queue
            LearnML.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
            //}
            return null;
        }
    }
}
