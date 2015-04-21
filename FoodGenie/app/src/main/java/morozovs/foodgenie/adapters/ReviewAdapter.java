package morozovs.foodgenie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import morozovs.foodgenie.R;
import morozovs.foodgenie.models.Review;

public class ReviewAdapter  extends ArrayAdapter<Review> {

    ArrayList<Review> reviews;
    Context context;

    public ReviewAdapter(Context context, ArrayList<Review> reviews){
        super(context, R.layout.review_row, reviews);
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_row, parent, false);
        }

        Review review = reviews.get(position);

        TextView authorName = (TextView) convertView.findViewById(R.id.review_author_name);
        authorName.setText(review.author_name);

        TextView rating = (TextView) convertView.findViewById(R.id.review_rating);
        rating.setText(review.getFormattedRating());

        TextView text = (TextView) convertView.findViewById(R.id.review_text);
        text.setText(review.text);


        return convertView;
    }

}
