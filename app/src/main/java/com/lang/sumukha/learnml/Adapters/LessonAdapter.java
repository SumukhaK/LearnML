package com.lang.sumukha.learnml.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lang.sumukha.learnml.Models.LessonQuestionModel;
import com.lang.sumukha.learnml.R;

import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonsViewHolder> {


    Context context;
    ArrayList<LessonQuestionModel> lessonQuestionModels;

    public LessonAdapter(Context context, ArrayList<LessonQuestionModel> lessonQuestionModels) {
        this.context = context;
        this.lessonQuestionModels = lessonQuestionModels;
    }

    @NonNull
    @Override
    public LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_row, parent, false);

        return new LessonAdapter.LessonsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsViewHolder holder, int position) {

        holder.lessonTv.setText(lessonQuestionModels.get(position).getLessonConceptName());


    }

    @Override
    public int getItemCount() {
        return lessonQuestionModels.size();
    }

    class LessonsViewHolder extends RecyclerView.ViewHolder {

        TextView lessonTv, userInputTv;
        FloatingActionButton playAudioFab,nextLessonFab,previousLessonFab;
        ImageView recordAudioIv;
        CoordinatorLayout rowLayout;
        public LessonsViewHolder(View itemView) {
            super(itemView);

            rowLayout = itemView.findViewById(R.id.lesson_rowlayout);
            lessonTv = itemView.findViewById(R.id.subject_textview);
            userInputTv = itemView.findViewById(R.id.userinput_textview);
            playAudioFab = itemView.findViewById(R.id.playaudio_fab);
            nextLessonFab = itemView.findViewById(R.id.nextscreen_fab);
            previousLessonFab = itemView.findViewById(R.id.previousscreen_fab);
            recordAudioIv = itemView.findViewById(R.id.recordaudio_imageview);

        }
    }
}
