package morozovs.foodgenie.api;

import android.util.Log;

import morozovs.foodgenie.interfaces.IDataGetter;
import morozovs.foodgenie.interfaces.IPlacesGetterResponseHandler;
import morozovs.foodgenie.interfaces.IResponseHandler;
import morozovs.foodgenie.models.ExtendedSearchResult;
import morozovs.foodgenie.models.SearchParameters;
import morozovs.foodgenie.models.SearchResult;
import morozovs.foodgenie.getters.DataGetter;

public class FoodFinderAPI {
    private static IDataGetter dataGetter = new DataGetter();
    private static final String placeSearchUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private static final String placeDetailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?";

    public static void toLog(String tag, String message){

        Log.i("", "\n**************  " + tag.toUpperCase() + "  **************");
        Log.i("", message);
        Log.i("", "\n**************  END  **************");
    }

    public static void searchForPlaces(String params, IResponseHandler callback, IPlacesGetterResponseHandler placesCallback) {
        dataGetter.getData(params, placeSearchUrl, SearchResult.class, callback, placesCallback);
    }

    public static void getPlaceInfo(String placeId, IResponseHandler callback){
        String params = "placeid="+placeId+"&key="+ SearchParameters.APIKey;
        dataGetter.getData(params, placeDetailsUrl, ExtendedSearchResult.class, callback, null);
    }
}
