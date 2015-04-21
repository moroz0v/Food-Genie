package morozovs.foodgenie.fragment;

import android.app.Activity;

import morozovs.foodgenie.interfaces.IExtendedResultsGetter;
import morozovs.foodgenie.interfaces.IResultSelectionManagement;

public abstract class BasePlacesFragment extends BaseFragment {

    IResultSelectionManagement resultSelectionManagement;
    IExtendedResultsGetter extendedResultsGetter;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            resultSelectionManagement = (IResultSelectionManagement) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement IResultSelectionManagement Interface");
        }

        try {
            extendedResultsGetter = (IExtendedResultsGetter) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement IExtendedResultsGetter Interface");
        }
    }
}
