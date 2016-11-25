package edu.louisville.cischef.thread;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.louisville.cischef.R;

/**
 * Created by Max02man on 11/24/2016.
 */

public class ThreadFragment extends Fragment{

    public ThreadFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thread, container, false);
    }
}
