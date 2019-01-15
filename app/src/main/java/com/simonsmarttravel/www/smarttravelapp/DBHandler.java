package com.simonsmarttravel.www.smarttravelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simonsmarttravel.www.smarttravelapp.Model.Comments;
import com.simonsmarttravel.www.smarttravelapp.Model.CommentsCustom;
import com.simonsmarttravel.www.smarttravelapp.Model.Places;
import com.simonsmarttravel.www.smarttravelapp.Model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SmartTravel.db";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "";

        strSql = "";
        strSql += "create table UserProfile (";
        strSql += "    Id integer primary key autoincrement,";
        strSql += "    UserName varchar(100),";
        strSql += "    UserLoginId varchar(100),";
        strSql += "    UserPassword varchar(100)";
        strSql += ");";

        db.execSQL(strSql);

        strSql = "";
        strSql += "create table Places (";
        strSql += "    Id integer primary key autoincrement,";
        strSql += "    PlaceName varchar(100),";
        strSql += "    PlaceDesc varchar(500),";
        strSql += "    Category varchar(100),";
        strSql += "    PlaceAddress varchar(200)";
        strSql += ");";

        db.execSQL(strSql);

        strSql = "";
        strSql += "create table Comments (";
        strSql += "    Id integer primary key autoincrement,";
        strSql += "    CommentMsg varchar(500),";
        strSql += "    Rate integer,";
        strSql += "    PlacesId integer,";
        strSql += "    UserProfileId integer,";
        strSql += "    foreign key(PlacesId) references Places(Id),";
        strSql += "    foreign key(UserProfileId) references UserProfile(Id)";
        strSql += ");";

        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Comments");
        db.execSQL("drop table if exists UserProfile");
        db.execSQL("drop table if exists Places");
    }

    // region UserProfile CRUD
    public void insertUserProfile(UserProfile input) {
        ContentValues values = new ContentValues();

        values.put("UserName", input.getUserName());
        values.put("UserLoginId", input.getUserLoginId());
        values.put("UserPassword", input.getUserPassword());

        SQLiteDatabase db = getWritableDatabase();

        db.insert("UserProfile", null, values);
        db.close();
    }

    public List<UserProfile> selectAllUserProfile() {
        List<UserProfile> valueList = new ArrayList<UserProfile>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM UserProfile;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("UserName")) != null) {
                UserProfile value = new UserProfile(c.getInt(c.getColumnIndex("Id")),
                        c.getString(c.getColumnIndex("UserName")),
                        c.getString(c.getColumnIndex("UserLoginId")),
                        c.getString(c.getColumnIndex("UserPassword")));
                valueList.add(value);
                c.moveToNext();
            }
        }

        db.close();
        c.close();

        return valueList;
    }

    public UserProfile selectUserProfileById(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT UserName,UserLoginId,UserPassword FROM UserProfile WHERE Id = "+ id +";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        UserProfile value = new UserProfile(id,
                c.getString(c.getColumnIndex("UserName")),
                c.getString(c.getColumnIndex("UserLoginId")),
                c.getString(c.getColumnIndex("UserPassword")));
        db.close();
        c.close();
        return value;
    }

    public int getIdByUsernamePassword(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT Id FROM UserProfile where UserLoginId = '"+ username +"' and UserPassword = '"+ password +"';";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        int Id = -1;

        if (c.getCount() == 1){
            Id = c.getInt(c.getColumnIndex("Id"));
        }else{
            Id = -1;
        }

        return Id;
    }


    // endregion

// region Places CRUD

    public void insertPlaces(Places input) {
        ContentValues values = new ContentValues();

        values.put("PlaceName", input.getPlaceName());
        values.put("PlaceDesc", input.getPlaceDesc());
        values.put("Category", input.getCategory());
        values.put("PlaceAddress", input.getPlaceAddress());

        SQLiteDatabase db = getWritableDatabase();

        db.insert("Places", null, values);
        db.close();
    }

    public List<Places> selectPlacesByAreaCategory(String area, String category) {

        List<Places> valueList = new ArrayList<Places>();
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT a.Id, a.PlaceName, a.PlaceDesc, a.Category, a.PlaceAddress, ifnull(b.AvgRate,0) as AvgRate " +
                " FROM Places a" +
                " left join (" +
                "     select PlacesId, avg(Rate) as AvgRate " +
                "     from Comments " +
                "     group by PlacesId" +
                " ) b" +
                " on b.PlacesId = a.Id"+
                " where a.PlaceAddress like '%" + area + "%' " +
                " and a.Category = '" + category + "' ;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("PlaceName")) != null) {
                Places value = new Places(c.getInt(c.getColumnIndex("Id")),
                        c.getString(c.getColumnIndex("PlaceName")),
                        c.getString(c.getColumnIndex("PlaceDesc")),
                        c.getString(c.getColumnIndex("Category")),
                        c.getString(c.getColumnIndex("PlaceAddress")));
                value.setAvgRating(c.getInt(c.getColumnIndex("AvgRate")));
                valueList.add(value);
                c.moveToNext();
            }
        }

        db.close();
        c.close();

        return valueList;
    }

    public Places selectPlacesById(int id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT Id, PlaceName, PlaceDesc, Category, PlaceAddress FROM Places WHERE Id = "+ id +";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Places value = new Places(id,
                c.getString(c.getColumnIndex("PlaceName")),
                c.getString(c.getColumnIndex("PlaceDesc")),
                c.getString(c.getColumnIndex("Category")),
                c.getString(c.getColumnIndex("PlaceAddress")));
        db.close();
        c.close();
        return value;
    }
    // endregion

    // region Comments CRUD


    public void insertComments(Comments input) {
        ContentValues values = new ContentValues();

        values.put("CommentMsg", input.getCommentMsg());
        values.put("Rate", input.getRate());
        values.put("PlacesId", input.getPlacesId().getId());
        values.put("UserProfileId", input.getUserProfileId().getId());

        SQLiteDatabase db = getWritableDatabase();

        db.insert("Comments", null, values);
        db.close();
    }

    public List<Comments> selectCommentsByPlacesId(int placesId, int userProfileId) {
        List<Comments> valueList = new ArrayList<Comments>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT CommentMsg FROM Comments where PlacesId = " + placesId + " and UserProfileId = " + userProfileId + " ;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("CommentMsg")) != null) {
                Comments value = new Comments();

                value.setCommentMsg(c.getString(c.getColumnIndex("CommentMsg")));

                valueList.add(value);
                c.moveToNext();
            }
        }

        db.close();
        c.close();

        return valueList;
    }

    public int selectAverageRateByPlacesId(int placesId, int userProfileId) {
        List<Comments> valueList = new ArrayList<Comments>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT avg(Rate) as AvgRate FROM Comments where PlacesId = " + placesId + " and UserProfileId = " + userProfileId + " ;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        int avgRate = 0;

        if (!c.isAfterLast()) {
            avgRate = c.getInt(c.getColumnIndex("AvgRate"));
        }

        db.close();
        c.close();
        return avgRate;
    }
    // endregion

    public List<CommentsCustom> selectCommentsByPlacesId(int placesId) {

        List<CommentsCustom> valueList = new ArrayList<CommentsCustom>();
        SQLiteDatabase db = getWritableDatabase();
        String query = " select a.CommentMsg, a.Rate, b.UserName " +
                " from Comments a" +
                " left join UserProfile b" +
                " on b.Id = a.UserProfileId" +
                " where a.PlacesId = " + placesId;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("CommentMsg")) != null) {
                CommentsCustom value = new CommentsCustom(c.getString(c.getColumnIndex("CommentMsg")),
                        c.getInt(c.getColumnIndex("Rate")),
                        c.getString(c.getColumnIndex("UserName")));
                valueList.add(value);
                c.moveToNext();
            }
        }

        db.close();
        c.close();

        return valueList;
    }
}
