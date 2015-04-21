package morozovs.foodgenie.models;

public class BasePlace{
    public String place_id;
    public String name;
    public String[] types;
    public String vicinity;
    public double rating;
    public int price_level;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getFormattedRating(){
        if(rating == 0.0)
            return "Not rated";
        return "Rated: " + rating;
    }

    public String getFormattedPrice(){
        String price = "Price level: ";
        switch (price_level){
            case 1: return price + "$";
            case 2: return price + "$$";
            case 3: return price + "$$$";
            case 4: return price + "$$$$";
            default: return price + "Unknown";
        }
    }
}
