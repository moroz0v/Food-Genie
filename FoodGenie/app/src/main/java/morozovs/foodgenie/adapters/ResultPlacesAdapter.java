package morozovs.foodgenie.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import morozovs.foodgenie.R;
import morozovs.foodgenie.activities.HomeActivity;
import morozovs.foodgenie.activities.ResultsActivity;
import morozovs.foodgenie.acynctasks.AddPlaceAsyncTask;
import morozovs.foodgenie.acynctasks.RemovePlaceAsyncTask;
import morozovs.foodgenie.acynctasks.UpdatePlaceAsyncTask;
import morozovs.foodgenie.interfaces.IExtendedResultsGetter;
import morozovs.foodgenie.interfaces.IResultSelectionManagement;
import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.utils.StringUtils;

public class ResultPlacesAdapter extends ArrayAdapter<MyPlaceInfo> {
    Context context;
    ArrayList<MyPlaceInfo> places;
    IResultSelectionManagement resultSelectionManagement;
    IExtendedResultsGetter extendedResultsGetter;

    public ResultPlacesAdapter(Context context, ArrayList<MyPlaceInfo> places, IResultSelectionManagement resultSelectionManagement, IExtendedResultsGetter extendedResultsGetter) {
        super(context, R.layout.result_place_row, places);
        this.context = context;
        this.places = places;
        this.resultSelectionManagement = resultSelectionManagement;
        this.extendedResultsGetter = extendedResultsGetter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.result_place_row, parent, false);
        }

        MyPlaceInfo place = places.get(position);

        ViewGroup mainLayout = (ViewGroup) convertView.findViewById(R.id.result_place_main);

        TextView resultPlaceName = (TextView)convertView.findViewById(R.id.result_place_name);
        resultPlaceName.setText(place.name);
        resultPlaceName.setTag(place);
        resultPlaceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPlaceInfo place = (MyPlaceInfo) v.getTag();
                extendedResultsGetter.startExtendedPlaceInfo(place);
            }
        });

        TextView resultPlaceDistance = (TextView)convertView.findViewById(R.id.result_place_rating);
        resultPlaceDistance.setText(place.getFormattedRating());

        TextView resultPlacePrice = (TextView)convertView.findViewById(R.id.result_place_price);
        resultPlacePrice.setText(place.getFormattedPrice());

        CheckBox isSelected = (CheckBox)convertView.findViewById(R.id.result_place_checkbox);
        isSelected.setTag(place);
        isSelected.setOnCheckedChangeListener(null);
        isSelected.setChecked(place.isSelected);
        isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyPlaceInfo placeInfo = (MyPlaceInfo)buttonView.getTag();
                if(isChecked) {
                    resultSelectionManagement.addSelectedPlace(placeInfo);
                }
                else resultSelectionManagement.removeSelectedPlace(placeInfo);
            }
        });

        final ViewGroup additionalInfo = (ViewGroup)convertView.findViewById(R.id.result_my_info);
        additionalInfo.setVisibility(View.GONE);
        Button addToVisited = (Button)convertView.findViewById(R.id.result_place_add_to_faves);
        addToVisited.setText("Go here");
        addToVisited.setTag(position);
        addToVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =  (Integer)v.getTag();
                addPlace(position);
            }
        });

        final TextView showMore = (TextView) convertView.findViewById(R.id.result_place_show_more);
        showMore.setText("see more");
        showMore.setPaintFlags(showMore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(additionalInfo.getVisibility() == View.VISIBLE) {
                    additionalInfo.setVisibility(View.GONE);
                    showMore.setText("see more");
                }
                else {
                    additionalInfo.setVisibility(View.VISIBLE);
                    showMore.setText("see less");
                }
            }
        });

        if(place.isVisited()){
            showMore.setVisibility(View.VISIBLE);

            mainLayout.setBackgroundColor(context.getResources().getColor(R.color.visited_place));

            final CheckBox isBlacklisted = (CheckBox)convertView.findViewById(R.id.result_is_blacklisted_check);
            if(place.isBlacklisted)
                isBlacklisted.setChecked(true);
            else isBlacklisted.setChecked(false);

            final EditText myReview = (EditText) convertView.findViewById(R.id.result_display_my_review);
            myReview.setText(place.getMyReview());

            final TextView myReviewEdit = (TextView) convertView.findViewById(R.id.result_review_edit);
            myReviewEdit.setPaintFlags(myReviewEdit.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            myReviewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = (TextView) v;
                    if(!StringUtils.isNullOrEmpty(t.getText().toString()) && !t.getText().toString().equals("Delete")){
                        myReviewEdit.setText("Delete");
                        if(myReview.getVisibility() != View.VISIBLE)
                            myReview.setVisibility(View.VISIBLE);
                        myReview.setEnabled(true);
                        myReview.requestFocus();
                    }else {
                        myReviewEdit.setText("Add");
                        myReview.setText("");
                        myReview.setEnabled(false);
                    }
                }
            });

            if(place.getMyReview().isEmpty()){
                myReviewEdit.setText("Add");
                myReviewEdit.setVisibility(View.VISIBLE);
                myReview.setVisibility(View.GONE);
            } else {
                myReviewEdit.setText("Edit");
                myReviewEdit.setVisibility(View.VISIBLE);
                myReview.setVisibility(View.VISIBLE);
            }

            Button saveChanges = (Button)convertView.findViewById(R.id.result_save);
            saveChanges.setTag(position);
            saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  (Integer)v.getTag();
                   updatePlace(position, isBlacklisted, myReview);
                }
            });

            Button removePlace = (Button) convertView.findViewById(R.id.result_remove);
            removePlace.setTag(position);
            removePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  (Integer)v.getTag();
                    removePlace(position);
                }
            });

        }
        else {
            mainLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            additionalInfo.setVisibility(View.GONE);
            showMore.setVisibility(View.GONE);
        }


        return convertView;
    }

    private void updatePlace(int position, CheckBox isBlacklistedCheck, TextView review){

        boolean isBlacklisted = isBlacklistedCheck.isChecked();
        String myReview = review.getText() == null ? "" : review.getText().toString();

        MyPlaceInfo place = places.get(position);
        place.setBlacklisted(isBlacklisted);
        place.setMyReview(myReview);

        new UpdatePlaceAsyncTask(context).execute(place);
        notifyDataSetChanged();
    }

    private void removePlace(int position){

        MyPlaceInfo place = places.get(position);
        new RemovePlaceAsyncTask(context).execute(place);
        places.remove(position);
        notifyDataSetChanged();
    }

    private void addPlace(int position){
        MyPlaceInfo place = places.get(position);

        if(context instanceof ResultsActivity){
            ((ResultsActivity)context).startFinalPlaceActivity(place);
        } else if(context instanceof HomeActivity) {
            ((HomeActivity)context).loadFinalPlace(place);
        }

        if(place.isVisited())
            return;

        place.setVisited(true);
        new AddPlaceAsyncTask(context).execute(place);
    }

}
