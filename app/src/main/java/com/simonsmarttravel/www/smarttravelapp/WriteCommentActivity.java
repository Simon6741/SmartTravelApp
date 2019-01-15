package com.simonsmarttravel.www.smarttravelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.simonsmarttravel.www.smarttravelapp.Model.Comments;
import com.simonsmarttravel.www.smarttravelapp.Model.Places;
import com.simonsmarttravel.www.smarttravelapp.Model.UserProfile;

public class WriteCommentActivity extends AppCompatActivity {

    private int placesId;
    private int userId;

    RatingBar rtbRate;
    TextView txtComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        rtbRate = (RatingBar) findViewById(R.id.rtbRate);
        txtComment = (TextView) findViewById(R.id.txtComment);

        Bundle bundle = getIntent().getExtras();
        placesId = bundle.getInt("PlacesId");
        userId = bundle.getInt("UserId");
    }

    public void onClickSubmit(View view) {
        int rate = (int) rtbRate.getRating();
        String comment = txtComment.getText().toString();

        DBHandler db = new DBHandler(this, null, null, 1);

        Comments newValue = new Comments(comment, rate,
                new Places(placesId, null, null, null, null),
                new UserProfile(userId, null, null, null));
        db.insertComments(newValue);

        Toast.makeText(this, "Submitted successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("PlacesId", placesId);
        intent.putExtra("UserId",userId);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("PlacesId", placesId);
        intent.putExtra("UserId",userId);
        setResult(RESULT_OK, intent);
    }
}
