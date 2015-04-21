package morozovs.foodgenie.interfaces;


import java.util.ArrayList;

import morozovs.foodgenie.models.MyPlaceInfo;

public interface IResultSelectionManagement {
    public abstract void addSelectedPlace(MyPlaceInfo selectedPlace);
    public abstract void removeSelectedPlace(MyPlaceInfo selectedPlace);
    public ArrayList<MyPlaceInfo> getSelectedPlaces();
}
