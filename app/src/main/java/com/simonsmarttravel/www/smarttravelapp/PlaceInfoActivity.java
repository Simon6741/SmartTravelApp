package com.simonsmarttravel.www.smarttravelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.simonsmarttravel.www.smarttravelapp.Model.Comments;
import com.simonsmarttravel.www.smarttravelapp.Model.CommentsCustom;
import com.simonsmarttravel.www.smarttravelapp.Model.Places;
import com.simonsmarttravel.www.smarttravelapp.Model.UserProfile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlaceInfoActivity extends AppCompatActivity {
    ListView listViewComments;
    List<CommentsCustom> listContent = new ArrayList<CommentsCustom>();
    CommentListAdapter adapter;

    private Places places;

    private int placesId;
    private int userId;

    TextView txtPlaceName;
    TextView txtPlaceDesc;
    RatingBar rtbRate;
    TextView txtComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        txtPlaceName = (TextView) findViewById(R.id.txtPlaceName);
        txtPlaceDesc = (TextView) findViewById(R.id.txtPlaceDesc);
        rtbRate = (RatingBar) findViewById(R.id.rtbRate);
        txtComment = (TextView) findViewById(R.id.txtComment);
        listViewComments = (ListView) findViewById(R.id.listViewComment) ;

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            placesId = bundle.getInt("PlacesId");
            userId = bundle.getInt("UserId");
        }

        DBHandler db = new DBHandler(this, null, null, 1);
        places = db.selectPlacesById(placesId);

         db = new DBHandler(this, null, null, 1);
        listContent = db.selectCommentsByPlacesId(placesId);

        txtPlaceName.setText(places.getPlaceName());
        txtPlaceDesc.setText(places.getPlaceDesc());

        if (!listContent.isEmpty()){
            adapter = new CommentListAdapter(this, R.layout.custom_row_activity_comment, listContent);
            listViewComments.setAdapter(adapter);
            registerForContextMenu(findViewById(R.id.listViewComment));
        }
    }

    public void onClickWriteComment(View view){
        Intent intent = new Intent(PlaceInfoActivity.this, WriteCommentActivity.class);
        intent.putExtra("PlacesId", placesId);
        intent.putExtra("UserId",userId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("PlacesId", placesId);
        intent.putExtra("UserId",userId);
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
