package morozovs.foodgenie.getters;

import android.os.AsyncTask;

import java.util.ArrayList;

import morozovs.foodgenie.api.FoodFinderAPI;
import morozovs.foodgenie.interfaces.IPlacesGetterResponseHandler;
import morozovs.foodgenie.interfaces.IResponseHandler;
import morozovs.foodgenie.interfaces.IPlacesGetter;
import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.models.SearchResult;
import morozovs.foodgenie.utils.FoodGenieDataSource;

public class PlacesGetter implements IPlacesGetterResponseHandler, IPlacesGetter {

    private static FoodGenieDataSource _dataSource;
    private static ArrayList<MyPlaceInfo> _myPlaces;

    public SearchResult getVisitedPlaces() {
        return new SearchResult(getMyVisitedPlaces());
    }

    public void searchForPlaces(String params, IResponseHandler fragmentCallback) {
        FoodFinderAPI.searchForPlaces(params, fragmentCallback, this);
    }

    public void saveVisitedPlace(MyPlaceInfo visitedPlace) {
        for(MyPlaceInfo p : getMyPlaces()){
            if(p.equals(visitedPlace))
                return;
        }

        getDataSource().saveMyPlaceInfo(visitedPlace);
        getMyPlaces().add(visitedPlace);
    }

    public void removeVisitedPlace(String placeId) {
        getDataSource().deleteMyPlaceInfo(placeId);
        setMyPlaces(getMyVisitedPlaces());
    }

    public void updateVisitedPlace(MyPlaceInfo visitedPlace) {
        getDataSource().updateMyPlaceInfo(visitedPlace);
        setMyPlaces(getMyVisitedPlaces());
    }

    @Override
    public void onPlacesResponse(IResponseHandler responseHandler, Object places) {
        new MergePlacesWithLocalData(responseHandler).execute(places);
    }

    private static ArrayList<MyPlaceInfo> getMyVisitedPlaces(){
        //checking if we already visited a place, should not do on UI thread, will fix later
        ArrayList<MyPlaceInfo> myPlaceInfos = getDataSource().getAllPlaces();
        if(myPlaceInfos == null)
            return new ArrayList<MyPlaceInfo>();
        return myPlaceInfos;
    }

    public static ArrayList<MyPlaceInfo> getMyPlaces(){
        if(_myPlaces == null)
            _myPlaces = getMyVisitedPlaces();
        return _myPlaces;
    }

    private static FoodGenieDataSource getDataSource(){
        if(_dataSource == null)
            _dataSource = new FoodGenieDataSource();
        return  _dataSource;
    }

    private static void setMyPlaces(ArrayList<MyPlaceInfo> myPlaces){
        if(myPlaces != null)
            _myPlaces = myPlaces;
    }

    class MergePlacesWithLocalData extends AsyncTask<Object, Object, SearchResult>{

        IResponseHandler responseHandler;

        MergePlacesWithLocalData(IResponseHandler responseHandler){
            this.responseHandler = responseHandler;
        }

        @Override
        protected SearchResult doInBackground(Object... params) {
            if(params == null || params.length == 0){
                //TODO: handle error
                return null;
            }

            SearchResult searchResult = (SearchResult) params[0];
            if(!searchResult.validResults())
                responseHandler.onResponse(new SearchResult());

            //would optimize this by using a HashMap instead of an Array using a place name as a key
            ArrayList<MyPlaceInfo> temp = (ArrayList<MyPlaceInfo>)searchResult.results.clone();
            for(MyPlaceInfo p : temp){
                int indexOf = getMyPlaces().indexOf(p);
                if(indexOf >= 0) {
                    MyPlaceInfo m = getMyPlaces().get(indexOf);
                    if(m.isBlacklisted)
                        searchResult.results.remove(p);
                    else {
                        p.setMyReview(m.getMyReview());
                        p.setVisited(m.isVisited());
                    }
                }
            }
            return searchResult;
        }

        protected void onPostExecute(SearchResult result) {
            responseHandler.onResponse(result);
        }
    }
}




