package com.simonsmarttravel.www.smarttravelapp.Model;

public class CommentsCustom {

    private String CommentMsg;
    private int Rate;
    private String UserName;

    public CommentsCustom(String commentMsg, int rate, String userName) {
        CommentMsg = commentMsg;
        Rate = rate;
        UserName = userName;
    }

    public String getCommentMsg() {
        return CommentMsg;
    }

    public int getRate() {
        return Rate;
    }

    public String getUserName() {
        return UserName;
    }
}
