package com.lang.sumukha.learnml.Adapters;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.sumukha.learnml.Interfaces.AudioPlayerListner;
import com.lang.sumukha.learnml.Interfaces.ScrollLessonListner;
import com.lang.sumukha.learnml.Models.LessonQuestionModel;
import com.lang.sumukha.learnml.R;

import java.util.ArrayList;
import java.util.Locale;


public class LessonScrollerAdapter extends PagerAdapter{
    public static final int REQ_CODE_SPEECH_INPUT = 100;
    private Activity _activity;
    ArrayList<LessonQuestionModel> lessonQuestionModels;
    private LayoutInflater inflater;
    ScrollLessonListner scrollLessonListner;
    AudioPlayerListner audioPlayerListner;
    //private MediaPlayer mediaPlayer;

    TextView lessonTv, userInputTv;
    FloatingActionButton playAudioFab,nextLessonFab,previousLessonFab;
    ImageView recordAudioIv;
    CoordinatorLayout rowLayout;

    public LessonScrollerAdapter(Activity _activity, ArrayList<LessonQuestionModel> lessonQuestionModels, ScrollLessonListner scrollLessonListner,AudioPlayerListner audioPlayerListner) {
        this._activity = _activity;
        this.lessonQuestionModels = lessonQuestionModels;
        this.scrollLessonListner = scrollLessonListner;
        this.audioPlayerListner = audioPlayerListner;
    }

    @Override
    public int getCount() {
        return this.lessonQuestionModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CoordinatorLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.lesson_row, container,
                false);

        rowLayout = viewLayout.findViewById(R.id.lesson_rowlayout);
        lessonTv = viewLayout.findViewById(R.id.subject_textview);
        userInputTv = viewLayout.findViewById(R.id.userinput_textview);
        playAudioFab = viewLayout.findViewById(R.id.playaudio_fab);
        nextLessonFab = viewLayout.findViewById(R.id.nextscreen_fab);
        previousLessonFab = viewLayout.findViewById(R.id.previousscreen_fab);
        recordAudioIv = viewLayout.findViewById(R.id.recordaudio_imageview);
        lessonTv.setText(lessonQuestionModels.get(position).getLessonConceptName());
        ((ViewPager) container).addView(viewLayout);
        if(position == 0){
            previousLessonFab.setVisibility(View.GONE);
        }
        if(position == lessonQuestionModels.size()-1){
            nextLessonFab.setVisibility(View.GONE);
        }

        if("learn".equals(lessonQuestionModels.get(position).getLessonType())){
            recordAudioIv.setVisibility(View.GONE);
        }

        nextLessonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollLessonListner.scrollLesson(true,position);
            }
        });

        recordAudioIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


        previousLessonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollLessonListner.scrollLesson(false,position);
            }
        });


        playAudioFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(_activity,"audio "+lessonQuestionModels.get(position).getLessonURL(),Toast.LENGTH_LONG).show();
                String[] ext= (lessonQuestionModels.get(position).getLessonURL()).split("/");
                String[] ext1= (ext[ext.length-1]).split(".");
                String lastStr="";
                for (String a: ext) {
                    lastStr = a;
                }
                String extension = lastStr.substring(lastStr.length() - 3);
                String type=null;
                if(extension=="aac"){
                    type="audio/aac";
                }else{
                    type= "audio/mpeg";
                }
                String html= "<audio controls controlsList=\"nodownload\">"
                        + "  <source src='"+lessonQuestionModels.get(position).getLessonURL()+"' type='"+type+"'>"
                        +"</audio>";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
                WebView webView = new WebView(_activity);
                webView.loadData(html,null,null);
                alertDialogBuilder.setView(webView);
                alertDialogBuilder.create();
                alertDialogBuilder.show();
            }
        });

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((CoordinatorLayout) object);

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak Now");
        try {
            _activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(_activity,
                    "Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
