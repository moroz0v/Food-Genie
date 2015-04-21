package morozovs.foodgenie.fragment;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.utils.StringUtils;

public abstract class BaseFragment extends Fragment {

    ProgressDialog progressDialog;
    protected int fragmentColor = -1;

    @Override
    public void onResume(){
        super.onResume();
        setGlobalColor(getFragmentColor());
    }

    public Intent createShareIntent(ArrayList<MyPlaceInfo> selectedPlaces){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, StringUtils.myPlacesLIstToString(selectedPlaces));
        shareIntent.setType("text/plain");
        return shareIntent;
    }

    protected void startLoadAnimation(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Genie working");
        progressDialog.setMessage("Your wish is my command!");
        progressDialog.show();
    }

    protected void stopLoadAnimation(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected int getFragmentColor(){
        if(fragmentColor < 0)
            fragmentColor = getResources().getColor(android.R.color.background_dark);
        return fragmentColor;
    }

    void setGlobalColor(int  color) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            //setting status and action buttons background
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setNavigationBarColor(color);
            getActivity().getWindow().setStatusBarColor(color);
            //setting status bar background
            ActionBar mActionBar = getActivity().getActionBar();
            if (mActionBar != null) {
                ColorDrawable d = new ColorDrawable();
                d.setColor(color);
                mActionBar.setBackgroundDrawable(d);
            }
        }
    }
}
