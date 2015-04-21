package morozovs.foodgenie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import morozovs.foodgenie.R;
import morozovs.foodgenie.adapters.ResultPlacesAdapter;
import morozovs.foodgenie.interfaces.IResultSelectionRefresh;
import morozovs.foodgenie.models.MyPlaceInfo;

public class SelectedPlacesFragment extends BasePlacesFragment  implements IResultSelectionRefresh{

    ListView listView;
    ResultPlacesAdapter selectedPlacesAdapter;
    ArrayList<MyPlaceInfo> selectedPlaces;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_places_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getView().findViewById(android.R.id.list);
        selectedPlaces = resultSelectionManagement.getSelectedPlaces();
        selectedPlacesAdapter = new ResultPlacesAdapter(getActivity(), selectedPlaces, resultSelectionManagement, extendedResultsGetter);
        listView.setAdapter(selectedPlacesAdapter);
    }

    @Override
    public void setSelection(ArrayList<MyPlaceInfo> selectedPlaces) {
        if(listView == null)
            return;

        this.selectedPlaces = selectedPlaces;
        selectedPlacesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            startActivity(createShareIntent(selectedPlaces));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getFragmentColor(){
        if(fragmentColor < 0)
            fragmentColor = getResources().getColor(android.R.color.holo_purple);
        return fragmentColor;
    }
}
