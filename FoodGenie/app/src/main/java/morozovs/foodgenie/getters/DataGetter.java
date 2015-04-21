package morozovs.foodgenie.getters;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Type;

import morozovs.foodgenie.interfaces.IDataGetter;
import morozovs.foodgenie.interfaces.IPlacesGetterResponseHandler;
import morozovs.foodgenie.interfaces.IResponseHandler;
import morozovs.foodgenie.utils.ResponseHelper;
import morozovs.foodgenie.utils.AppController;

public class DataGetter implements IDataGetter, Response.ErrorListener{
    private static String tag_json_obj = "json_obj_req";

    @Override
    public void getData(String parameters, String baseUrl, Type returnObjectType, IResponseHandler callback, IPlacesGetterResponseHandler placesCallback){

        StringRequest strReq = new StringRequest(Request.Method.GET,
                baseUrl + parameters, new ResponseHelper(callback, returnObjectType, placesCallback), this){
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //TODO: handle errors
    }

}


