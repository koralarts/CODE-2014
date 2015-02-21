package com.codesnroses.foodo.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codesnroses.foodo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FruitsFragment extends Fragment {


    public FruitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fruits, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
