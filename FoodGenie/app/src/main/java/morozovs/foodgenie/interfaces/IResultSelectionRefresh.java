package morozovs.foodgenie.interfaces;

import java.util.ArrayList;

import morozovs.foodgenie.models.MyPlaceInfo;

public interface IResultSelectionRefresh {
    public abstract void setSelection(ArrayList<MyPlaceInfo> selectedPlaces);
}
