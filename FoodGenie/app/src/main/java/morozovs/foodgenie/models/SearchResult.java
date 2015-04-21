package morozovs.foodgenie.models;

import java.util.ArrayList;

public class SearchResult extends BaseSearchResult {
    public ArrayList<MyPlaceInfo> results;

    public SearchResult(){
        results = new ArrayList<MyPlaceInfo>();
        status = "";
    }

    public SearchResult(ArrayList<MyPlaceInfo> myPlaces){
        results = myPlaces;
        status = "OK";
    }
}
