package morozovs.foodgenie.models;


import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Calendar;

import morozovs.foodgenie.utils.StringUtils;

public class SearchParameters {
    public static final String APIKey = "AIzaSyBE5eItFKgOAyFo0F-zbIGQglurDfsEeRI";

    ArrayMap<String, String> parameters;
    ArrayList<String> types;
    String keyWord;

    public SearchParameters(){
        parameters = new ArrayMap<String, String>();
        types = new ArrayList<String>();
        setDefaultParams();
    }

    public void addParameter(String key, String value){
        if(key.equals("keyword")){
            if(!StringUtils.isNullOrEmpty(value))
                keyWord = value;
        }
        else if(key.equals("types")){
            if(!types.contains(value))
                types.add(value);
        }
        else parameters.put(key, value);
    }

    public void removeParameter(String key, String value){
        if(key.equals("types")){
            if(types.contains(value))
                types.remove(types.indexOf(value));
        }
        else if(parameters.containsKey(key)){
            parameters.remove(key);
        }
    }

    public String buildSearchParams(){
        StringBuilder params = new StringBuilder("");
        if(!parameters.isEmpty()) {
            for (String s : parameters.keySet()) {
                params.append(s).append("=").append(parameters.get(s)).append("&");
            }
            params.deleteCharAt(params.length() - 1);
        }
        if(!types.isEmpty()) {
            if(params.length() == 0)
                params.append("types=");
            else params.append("&types=");

            for (String t : types){
                params.append(t).append("|");
            }
            params.deleteCharAt(params.length() - 1);
        }
        if(!StringUtils.isNullOrEmpty(keyWord)){
            params.append("&keyword=").append(keyWord);
        }
        return params.toString();
    }

    public String getSuggestionParams(String location){
        String keyword;

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        //looking for breakfast, lunch, dinner, or bar based on current time
        if(hour >= 5 && hour < 11){
            keyword = "breakfast";
        } else if(hour >= 11 && hour < 15){
            keyword = "lunch";
        } else if(hour >= 15 && hour < 21){
            keyword = "dinner";
        } else keyword = "bar";

        addParameter("location", location);
        addParameter("keyword", keyword);
        addParameter("radius", 1000 + "");
        return buildSearchParams();
    }

    private void setDefaultParams(){
        addParameter("key", APIKey);
        addParameter("radius", 10000 + "");
        addParameter("isUsingSensor", "false");
        addParameter("useDefaultRanking", "true");
        addParameter("rankby", "prominence");
        addParameter("types", "restaurant");
        addParameter("types", "cafee");
        addParameter("types", "bar");

    }
}
