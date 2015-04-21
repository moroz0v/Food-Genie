package morozovs.foodgenie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import morozovs.foodgenie.R;
import morozovs.foodgenie.interfaces.IHomeNavigationManager;
import morozovs.foodgenie.models.SearchParameters;

public class SearchBuilderFragment extends BaseFragment {

    public SearchParameters searchParameters;
    IHomeNavigationManager mNavigator;
    ViewGroup infoPriceRange;
    ViewGroup infoDistance;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mNavigator = (IHomeNavigationManager) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement INavigationManager");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        searchParameters = new SearchParameters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        infoPriceRange = (ViewGroup)view.findViewById(R.id.info_price_point);
        infoDistance = (ViewGroup)view.findViewById(R.id.info_distance);

        ArrayAdapter<CharSequence> typesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.food_categories, android.R.layout.simple_spinner_item);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner foodTypesArray = (Spinner) view.findViewById(R.id.food_categories);
        foodTypesArray.setAdapter(typesAdapter);
        foodTypesArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos!= 0)
                    infoPriceRange.setVisibility(View.VISIBLE);
                if (pos != 0 && pos != 1) {
                    searchParameters.addParameter("keyword", parent.getItemAtPosition(pos) + "");
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner pricePoints = (Spinner) view.findViewById(R.id.food_price);
        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.food_price_points, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pricePoints.setAdapter(priceAdapter);
        pricePoints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(pos != 0)
                    infoDistance.setVisibility(View.VISIBLE);
                if(pos != 0 && pos != 1) {
                    searchParameters.addParameter("minprice", pos-1 + "");
                    searchParameters.addParameter("maxprice", pos-1 + "");
                } else {
                    searchParameters.removeParameter("minprice", "");
                    searchParameters.removeParameter("maxprice", "");
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RadioGroup infoRadioDistanceGroup = (RadioGroup)view.findViewById(R.id.info_distance_radio_group);
        infoRadioDistanceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radius = 50000;
                switch (checkedId){
                    case R.id.info_radio_none:
                        break;
                    case R.id.info_radio_1: radius = 1000; break;
                    case R.id.info_radio_2: radius = 2000; break;
                    case R.id.info_radio_3: radius = 3000; break;
                }
                searchParameters.addParameter("radius", radius + "");
            }
        });

        Button submit_button = (Button)view.findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String params = searchParameters.buildSearchParams();
                mNavigator.initiateSearch(params);
            }
        });

        return view;
    }

    @Override
    protected int getFragmentColor(){
        if(fragmentColor < 0)
            fragmentColor = getResources().getColor(android.R.color.holo_green_dark);
        return fragmentColor;
    }
}
