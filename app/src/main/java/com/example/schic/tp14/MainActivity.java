package com.example.schic.tp14;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AlertDialog dialog;
    ViewPager vp_pages;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Contrôle si premier lancement ou non
        pref = getSharedPreferences("tp14", MODE_PRIVATE);
        if(pref.getBoolean("firstrun", true)){

            pref.edit().putBoolean("firstrun", false).commit();
            pref.edit().putInt("langue",0).commit();
            pref.edit().putBoolean("admin",false).commit();
        }

        // Création du fichier si il n'existe pas
        try {
            createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        vp_pages= (ViewPager) findViewById(R.id.vp_pages);
        PagerAdapter pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_pages.setAdapter(pagerAdapter);

        TabLayout tbl_pages= (TabLayout) findViewById(R.id.tbl_pages);
        tbl_pages.setupWithViewPager(vp_pages);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferenceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            AddToDico();
        } else if (id == R.id.nav_delete) {
            deleteInternalFile();
            vp_pages.getAdapter().notifyDataSetChanged();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void AddToDico() {
        //Custom Dialog pour formulaire ajout au dico
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
        View myView = getLayoutInflater().inflate(R.layout.dialog_addtodico, null);
        final EditText etMot = myView.findViewById(R.id.etMot);
        final EditText etDef = myView.findViewById(R.id.etDef);

        Button valider = myView.findViewById(R.id.btnValider);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Vérification
                if(!etMot.getText().toString().isEmpty() && !etDef.getText().toString().isEmpty()) {

                    String mot = etMot.getText().toString();
                    String def = etDef.getText().toString();
                    String line = mot+" "+def;

                    File file = new File(getApplicationContext().getFilesDir(), "dico");
                    if(!file.exists()) {
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        new FileOutputStream(file,true).write((line + System.getProperty("line.separator")).getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    vp_pages.getAdapter().notifyDataSetChanged();
                    dialog.dismiss();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Veuillez remplir tous les champs !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Affichage du dialog dnas l'UI
        myBuilder.setView(myView);
        dialog = myBuilder.create();
        dialog.show();
    }

    public void createFile() throws IOException {

        File file = new File(this.getFilesDir(), "dico");
        if(!file.exists()){

            FileOutputStream fileOutputStream = new FileOutputStream(file,true);

            Scanner scan =  new Scanner(getResources().openRawResource(R.raw.dict));
            while (scan.hasNextLine())
            {
                String line = scan.nextLine();
                fileOutputStream.write((line + System.getProperty("line.separator")).getBytes());
            }
            scan.close();

            file.createNewFile();
        }
    }

    public void deleteInternalFile(){
        File file = new File(getFilesDir(),"dico");
        if(file.exists()){
            deleteFile("dico");
        }
    }
}
