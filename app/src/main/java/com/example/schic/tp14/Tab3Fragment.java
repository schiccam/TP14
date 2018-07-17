package com.example.schic.tp14;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab3Fragment extends Fragment {

    TextView tvFrag3;


    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3fragment, container, false);
        tvFrag3 = view.findViewById(R.id.tvFrag3);
        Bundle bundle = getArguments();
        if(bundle!= null)
        {
            String value = getArguments().getString("texte");
            tvFrag3.setText(value);
        }
        return view;
    }

}
