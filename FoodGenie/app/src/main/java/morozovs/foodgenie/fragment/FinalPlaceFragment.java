package morozovs.foodgenie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import morozovs.foodgenie.R;
import morozovs.foodgenie.activities.HomeActivity;
import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.utils.JSONManager;

public class FinalPlaceFragment extends BaseFragment{

    public static final String KEY_PLACE_PARAM = "my_place_place";

    MyPlaceInfo myPlaceInfo;
    ViewGroup reviewLayout;
    TextView name;
    TextView price;
    TextView distance;
    TextView review;

    public static FinalPlaceFragment newInstance(MyPlaceInfo myPlaceInfo){
        Bundle b = new Bundle();
        b.putString(KEY_PLACE_PARAM, JSONManager.gson.toJson(myPlaceInfo));
        FinalPlaceFragment p = new FinalPlaceFragment();
        p.setArguments(b);
        return p;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_place, container, false);
        name = (TextView) view.findViewById(R.id.my_place_name);
        price = (TextView) view.findViewById(R.id.my_place_price);
        distance = (TextView) view.findViewById(R.id.my_place_distance);
        review = (TextView) view.findViewById(R.id.my_place_review);
        reviewLayout = (ViewGroup) view.findViewById(R.id.my_place_review_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            myPlaceInfo = (MyPlaceInfo)JSONManager.getObject(getArguments().getString(KEY_PLACE_PARAM), MyPlaceInfo.class);
        }
        name.setText(myPlaceInfo.getName());
        price.setText(myPlaceInfo.getFormattedPrice());
        distance.setText(myPlaceInfo.getFormattedRating());
        if(!myPlaceInfo.getMyReview().isEmpty()) {
            reviewLayout.setVisibility(View.VISIBLE);
            review.setText(myPlaceInfo.getMyReview());
        }else reviewLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_share){
            ArrayList<MyPlaceInfo> a = new ArrayList<MyPlaceInfo>();
            a.add(myPlaceInfo);
            startActivity(createShareIntent(a));
        } else {
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
        return true;
    }

    @Override
    protected int getFragmentColor(){
        if(fragmentColor < 0)
            fragmentColor = getResources().getColor(android.R.color.holo_orange_dark);
        return fragmentColor;
    }
}
