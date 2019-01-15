package com.simonsmarttravel.www.smarttravelapp.Model;

public class Comments {
    private int Id;
    private String CommentMsg;
    private int Rate;
    private Places PlacesId;
    private UserProfile UserProfileId;

    public Comments() {
    }

    public Comments(String commentMsg, int rate, Places placesId, UserProfile userProfileId) {
        CommentMsg = commentMsg;
        Rate = rate;
        PlacesId = placesId;
        UserProfileId = userProfileId;
    }

    public Comments(int id, String commentMsg, int rate, Places placesId, UserProfile userProfileId) {
        Id = id;
        CommentMsg = commentMsg;
        Rate = rate;
        PlacesId = placesId;
        UserProfileId = userProfileId;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public int getId() {
        return Id;
    }

    public String getCommentMsg() {
        return CommentMsg;
    }

    public void setCommentMsg(String commentMsg) {
        CommentMsg = commentMsg;
    }

    public Places getPlacesId() {
        return PlacesId;
    }

    public void setPlacesId(Places placesId) {
        PlacesId = placesId;
    }

    public UserProfile getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(UserProfile userProfileId) {
        UserProfileId = userProfileId;
    }
}
