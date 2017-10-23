package com.tianfu.cutton.fragment.store;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianfu.cutton.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreNodataFragment extends Fragment {


    public StoreNodataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_nodata, container, false);
    }

}
