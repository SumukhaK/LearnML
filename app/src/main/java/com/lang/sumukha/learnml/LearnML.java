package com.lang.sumukha.learnml;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LearnML {

    private static LearnML mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private LearnML(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();


    }

    public static synchronized LearnML getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new LearnML(context);
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }



    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
