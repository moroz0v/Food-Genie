package morozovs.foodgenie.models;


import morozovs.foodgenie.utils.JSONManager;

public class MyPlaceInfo extends PlaceInfo{
    public String myReview = "";
    public boolean isBlacklisted;
    public boolean isVisited;
    public boolean isSelected;

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    public String getMyReview() {
        return myReview;
    }

    public void setMyReview(String myReview) {
        this.myReview = myReview;
    }

    @Override
    public boolean equals(Object object){
        if (object == null)
            return false;

        if(object instanceof MyPlaceInfo){
            PlaceInfo p = (MyPlaceInfo)object;
            return this.place_id.equals(p.place_id);
        }
        return false;
    }

    public String toJSONString(){
        return JSONManager.gson.toJson(this);
    }

    public static MyPlaceInfo fromJsonString(String JSONString){
        if(JSONString == null || JSONString.isEmpty())
            return new MyPlaceInfo();

        return (MyPlaceInfo)JSONManager.getObject(JSONString, MyPlaceInfo.class);
    }
}
