package com.example.Users;

public class UserRating {
    private String userName;
    private int rating;
    private String comment;

    public String getUserName() {
        return userName;
    }

    public UserRating(String userName, int rating, String comment) {
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String toString()
    {
        return ("username: " + userName + ", rating: " + rating + ", comment: " + comment);
    }
}
