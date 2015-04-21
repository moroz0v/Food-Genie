package morozovs.foodgenie.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import morozovs.foodgenie.R;
import morozovs.foodgenie.interfaces.IHomeNavigationManager;

public class WelcomeFragment extends BaseFragment {

    ViewGroup mainLayout;
    ViewGroup myWish;
    ViewGroup decide;
    ViewGroup visited;
    IHomeNavigationManager mNavigator;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        mainLayout = (ViewGroup) view.findViewById(R.id.welcome_main_layout);
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                myWish.setVisibility(View.VISIBLE);
                decide.setVisibility(View.VISIBLE);
                visited.setVisibility(View.VISIBLE);
            }
        });
        myWish = (ViewGroup) view.findViewById(R.id.welcome_make_my_wish);
        myWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.loadInfo();
            }
        });
        decide = (ViewGroup) view.findViewById(R.id.welcome_make_decide);
        decide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.initiateSuggestionSearch();
            }
        });
        visited = (ViewGroup) view.findViewById(R.id.welcome_make_visited);
        visited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.loadVisitedPlaces();
            }
        });
        return view;
    }

    @Override
    protected int getFragmentColor(){
        if(fragmentColor < 0)
            fragmentColor = getResources().getColor(android.R.color.holo_red_dark);
        return fragmentColor;
    }
}
