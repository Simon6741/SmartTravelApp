package com.simonsmarttravel.www.smarttravelapp.Model;

public class Places {
    private int Id;

    private String PlaceName;
    private String PlaceDesc;
    private String Category;
    private String PlaceAddress;
    private int AvgRating;

    public Places() {
    }

    public Places(String placeName, String placeDesc, String category, String placeAddress) {
        PlaceName = placeName;
        PlaceDesc = placeDesc;
        Category = category;
        PlaceAddress = placeAddress;
    }

    public Places(int id, String placeName, String placeDesc, String category, String placeAddress) {
        Id = id;
        PlaceName = placeName;
        PlaceDesc = placeDesc;
        Category = category;
        PlaceAddress = placeAddress;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getId() {
        return Id;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getPlaceDesc() {
        return PlaceDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        PlaceDesc = placeDesc;
    }

    public String getPlaceAddress() {
        return PlaceAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        PlaceAddress = placeAddress;
    }

    public int getAvgRating() {
        return AvgRating;
    }

    public void setAvgRating(int avgRating) {
        AvgRating = avgRating;
    }
}
