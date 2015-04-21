package morozovs.foodgenie.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import morozovs.foodgenie.R;
import morozovs.foodgenie.fragment.ExtendedPlaceInfoFragment;
import morozovs.foodgenie.fragment.FinalPlaceFragment;
import morozovs.foodgenie.fragment.ResultPlacesFragment;
import morozovs.foodgenie.fragment.SelectedPlacesFragment;
import morozovs.foodgenie.interfaces.IExtendedResultsGetter;
import morozovs.foodgenie.interfaces.IResultSelectionManagement;
import morozovs.foodgenie.interfaces.IResultSelectionRefresh;
import morozovs.foodgenie.models.MyPlaceInfo;

public class ResultsActivity extends BaseActivity implements IResultSelectionManagement, IExtendedResultsGetter {

    String searchParams;
    ArrayList<MyPlaceInfo> selectedPlaces;
    IResultSelectionRefresh selectedPlacesSelectionRefresh;
    IResultSelectionRefresh resultSelectionRefresh;


    private static final int NUM_PAGES = 2;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }
            @Override
            public void onPageSelected(final int i) {
                IResultSelectionRefresh fragment = (IResultSelectionRefresh) mPagerAdapter.instantiateItem(mPager, i);
                if (fragment != null) {
                    fragment.setSelection(getSelectedPlaces());
                }
            }
            @Override
            public void onPageScrollStateChanged(final int i) {
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null)
            searchParams = extras.getString(KEY_SEARCH_PARAMS);

        selectedPlaces = new ArrayList<MyPlaceInfo>();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void addSelectedPlace(MyPlaceInfo selectedPlace) {
        if(selectedPlaces == null)
            return;

        selectedPlaces.add(selectedPlace);
        selectedPlace.isSelected = true;
    }

    @Override
    public void removeSelectedPlace(MyPlaceInfo selectedPlace) {
        if(selectedPlaces == null)
            return;

        selectedPlaces.remove(selectedPlace);
        selectedPlace.isSelected = false;
    }

    @Override
    public ArrayList<MyPlaceInfo> getSelectedPlaces() {
        if(selectedPlaces == null)
            return new ArrayList<MyPlaceInfo>();
        else return selectedPlaces;
    }

    public void startFinalPlaceActivity(MyPlaceInfo finalPlace){
        Bundle b = new Bundle();
        b.putString(FinalPlaceFragment.KEY_PLACE_PARAM, finalPlace.toJSONString());
        b.putString(KEY_FINAL_FRAGMENT, "true");

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(b);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }

    @Override
    public void startExtendedPlaceInfo(MyPlaceInfo finalPlace){
        Bundle b = new Bundle();
        b.putString(ExtendedPlaceInfoFragment.KEY_PLACE, finalPlace.toJSONString());
        b.putString(KEY_PLACE_INFO_FRAGMENT, "true");

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtras(b);

        startActivity(intent);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    ResultPlacesFragment p = ResultPlacesFragment.newInstance(searchParams);
                    resultSelectionRefresh = p;
                    return p;
                case 1:
                    SelectedPlacesFragment f = new SelectedPlacesFragment();
                    selectedPlacesSelectionRefresh = f;
                    return f;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
