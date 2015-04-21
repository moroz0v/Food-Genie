package morozovs.foodgenie.utils;

import java.util.ArrayList;

import morozovs.foodgenie.models.MyPlaceInfo;

public class StringUtils {
    public static boolean isNullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static String myPlacesLIstToString(ArrayList<MyPlaceInfo> placesList){
        StringBuilder b = new StringBuilder();
        if(placesList.size() > 1)
            b.append("Hey, I think these places are a good option for our next outing. What do you think? \n\n");
        else b.append("Hey, I think this place is a good option for our next outing. What do you think? \n\n");
        int count = 0;
        for(MyPlaceInfo p : placesList){
            count ++;
            b.append(count).append(". ").append(p.getName().toUpperCase());
            if(!StringUtils.isNullOrEmpty(p.getFormattedPrice())) {
                b.append(", ").append(p.getFormattedPrice());
            }
            if(!StringUtils.isNullOrEmpty(p.getFormattedRating())) {
                b.append(", ").append(p.getFormattedRating());
            }
            if(!StringUtils.isNullOrEmpty(p.getVicinity())) {
                b.append(", ").append(p.getVicinity());
            }
             b.append("\n\n");
        }
        return b.toString();
    }
}
