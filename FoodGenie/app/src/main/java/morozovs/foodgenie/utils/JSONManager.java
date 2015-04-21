package morozovs.foodgenie.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JSONManager {
    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting().create();

    public static Object getObject(String jsonString, Type classType){
        return gson.fromJson(jsonString, classType);
    }
}
