package com.codesnroses.foodo.Etc;

import android.app.Application;

/**
 * Created by Zhan on 15-02-22.
 */
public class CurrentFragment extends Application {

    private int fragmentIndex;
    private String fragmentName;

    public String getFragmentName() {
        return fragmentName;
    }

    public void setFragmentName(String fragmentName) {
        this.fragmentName = fragmentName;
    }

    private static CurrentFragment mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static synchronized  CurrentFragment getInstance(){return mInstance;}

    public int getFragmentIndex() {
        return fragmentIndex;
    }

    public void setFragmentIndex(int fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
    }
}
