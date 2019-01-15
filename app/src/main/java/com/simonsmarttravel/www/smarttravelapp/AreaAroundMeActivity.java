package com.simonsmarttravel.www.smarttravelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.simonsmarttravel.www.smarttravelapp.Model.Places;

import java.util.ArrayList;
import java.util.List;

public class AreaAroundMeActivity extends AppCompatActivity {
    ListView listViewPlaces;
    List<Places> listContent = new ArrayList<Places>();
    PlacesListAdapter adapter;
    private int userId;
private int placesId;

    private String locationType;
    private String areaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_area_around_me);

            if (getIntent().getExtras() != null){
                Bundle bundle = getIntent().getExtras();
                locationType = bundle.getString("LocationType");
                areaName = bundle.getString("AreaName");
                userId = bundle.getInt("UserId");
            }

            listViewPlaces = (ListView) findViewById(R.id.listViewPlaces);

            DBHandler db = new DBHandler(this, null, null, 1);
            listContent = db.selectPlacesByAreaCategory(areaName, locationType);

            if (!listContent.isEmpty()){
                adapter = new PlacesListAdapter(this, R.layout.custom_row_activity_places, listContent);
                listViewPlaces.setAdapter(adapter);
                registerForContextMenu(findViewById(R.id.listViewPlaces));
            }

            listViewPlaces.setOnItemClickListener(new ListView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(AreaAroundMeActivity.this, PlaceInfoActivity.class);
                    placesId = adapter.getItem(i).getId();
                    intent.putExtra("PlacesId", placesId);
                    intent.putExtra("UserId",userId);
                    startActivityForResult(intent,1);
                }
            });
        }catch (Exception ex){
            Log.d("Error",ex.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("UserId", userId);
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                userId= Integer.parseInt( data.getStringExtra("UserId"));
                placesId= Integer.parseInt( data.getStringExtra("PlacesId"));
            }
        }
    }
}
