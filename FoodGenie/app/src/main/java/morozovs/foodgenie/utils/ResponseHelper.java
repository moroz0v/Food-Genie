package morozovs.foodgenie.utils;

import android.os.AsyncTask;

import com.android.volley.Response;

import java.lang.reflect.Type;
import java.util.Objects;

import morozovs.foodgenie.interfaces.IPlacesGetterResponseHandler;
import morozovs.foodgenie.interfaces.IResponseHandler;
import morozovs.foodgenie.utils.JSONManager;

public class ResponseHelper implements Response.Listener<String> {
    private Type objectReturnType;
    private IResponseHandler callback;
    private IPlacesGetterResponseHandler placesHandlerCallback;
    public ResponseHelper(IResponseHandler callback, Type objectReturnType, IPlacesGetterResponseHandler placesHandlerCallback){
        this.callback = callback;
        this.objectReturnType = objectReturnType;
        this.placesHandlerCallback = placesHandlerCallback;
    }

    @Override
    public void onResponse(String response) {
        new ResponseParser().execute(response);
    }

    //doing JSON parsing asynchronously
    class ResponseParser extends AsyncTask<String, Object, Object>{
        @Override
        protected Object doInBackground(String... params) {
            if(params == null || params.length == 0){
                //TODO: handle error
                return null;
            }
            String json = params[0];
            return JSONManager.getObject(json, objectReturnType);
        }

        protected void onPostExecute(Object result) {
            //a bit hacky, should refactor to use Reflection
            if(placesHandlerCallback != null)
                placesHandlerCallback.onPlacesResponse(callback, result);
            else callback.onResponse(result);
        }
    }
}
