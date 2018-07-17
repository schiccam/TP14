package com.example.schic.tp14;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {

    ListView lvMot;
    List<String> Mots;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1fragment, container, false);

        lvMot = view.findViewById(R.id.lvMot);

        Mots = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis = getActivity().openFileInput("dico");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while((line = br.readLine()) != null){

                String[] arr = line.split(" ",2);
                String First = arr[0];
                String Second = arr[1];

                Mots.add(First);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,Mots);
        lvMot.setAdapter(adapter);

        lvMot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO changement de tab sur click
                /*Tab3Fragment fragment = new Tab3Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("texte", "testechangefrag");
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llFrag3,fragment).commit();*/

            }
        });

        return view;
    }

}
