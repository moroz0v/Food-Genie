package morozovs.foodgenie.interfaces;

import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.models.SearchResult;

public interface IPlacesGetter {
    void searchForPlaces(String params, IResponseHandler fragmentCallback);
    SearchResult getVisitedPlaces();
    void saveVisitedPlace(MyPlaceInfo visitedPlace);
    void removeVisitedPlace(String placeId);
    void updateVisitedPlace(MyPlaceInfo visitedPlace);
}
