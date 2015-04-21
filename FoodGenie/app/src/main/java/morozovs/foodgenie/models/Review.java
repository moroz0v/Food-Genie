package morozovs.foodgenie.models;

public class Review {
    public String author_name;
    public double rating;
    public String text;

    public String getFormattedRating(){
        if(rating == 0.0)
            return "Not rated";
        return "Rated: " + rating;
    }
}