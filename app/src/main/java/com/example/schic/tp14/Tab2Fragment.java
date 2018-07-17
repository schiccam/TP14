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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {

    ListView lvDef;
    List<String> Defs;

    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2fragment, container, false);

        lvDef = view.findViewById(R.id.lvDef);

        Defs = new ArrayList<>();

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

                Defs.add(Second);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,Defs);
        lvDef.setAdapter(adapter);

        lvDef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TabLayout tabLayout = getActivity().findViewById(R.id.tbl_pages);
                TabLayout.Tab tab = tabLayout.getTabAt(2);
                tab.select();

            }
        });
        return view;
    }

}
