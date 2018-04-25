package com.lang.sumukha.learnml.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.sumukha.learnml.Adapters.LessonScrollerAdapter;
import com.lang.sumukha.learnml.Helpers.TextMatchHelper;
import com.lang.sumukha.learnml.Interfaces.AudioPlayerListner;
import com.lang.sumukha.learnml.Interfaces.ScrollLessonListner;
import com.lang.sumukha.learnml.Models.LessonQuestionModel;
import com.lang.sumukha.learnml.R;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ScrollLessonListner,AudioPlayerListner{

    public static ArrayList<LessonQuestionModel> lessonQuestionModels;
    LessonScrollerAdapter lessonScrollerAdapter;
    ViewPager lessonPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lessonPager = findViewById(R.id.lesson_pager);
        lessonScrollerAdapter = new LessonScrollerAdapter(MainActivity.this,lessonQuestionModels,MainActivity.this,MainActivity.this);
        lessonPager.setAdapter(lessonScrollerAdapter);


    }

    @Override
    public void scrollLesson(boolean isNextClicked,int position) {

        if(isNextClicked){
            lessonPager.setCurrentItem(position+1);
        }else {

            lessonPager.setCurrentItem(position-1);
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }*/

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case LessonScrollerAdapter.REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //Toast.makeText(getApplicationContext(),result.get(0),Toast.LENGTH_LONG).show();
                    //lessonScrollerAdapter.setUserSpeechdata(result.get(0));
                    int itemIndex= lessonPager.getCurrentItem();
                    String originalText= (lessonQuestionModels.get(itemIndex)).getLessonTargetScript();
                    TextMatchHelper helper= new TextMatchHelper();
                    double per= helper.matchText(originalText, result.get(0));
                    Toast.makeText(getApplicationContext(), "Match percentage : "+per*100, Toast.LENGTH_LONG).show();
                    View view= lessonPager.getChildAt(itemIndex);
                    ((TextView)view.findViewById(R.id.userinput_textview)).setText(result.get(0));
                }
                break;
            }

        }
    }

    //private MediaPlayer mediaPlayer= new MediaPlayer();


    @Override
    public void playAudio(String url) {

       // new PlayAudio(this, url).execute();

    }

    public class PlayAudio extends AsyncTask<Void,Void,Void> {
        Context context;
        String url;

        public PlayAudio(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            /*try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();*/
            //progressDialog.hide();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*try {
                mediaPlayer.setDataSource(context, Uri.parse(url));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }
    }
}
