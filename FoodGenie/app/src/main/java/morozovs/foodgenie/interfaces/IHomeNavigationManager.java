package morozovs.foodgenie.interfaces;

import morozovs.foodgenie.models.MyPlaceInfo;

public interface IHomeNavigationManager {
    public void loadInfo();
    public void loadWelcome();
    public void loadVisitedPlaces();
    public void initiateSearch(String searchParams);
    public void initiateSuggestionSearch();
    public void loadFinalPlace(MyPlaceInfo finalPlace);
}
