package morozovs.foodgenie.acynctasks;

import android.content.Context;

import morozovs.foodgenie.models.MyPlaceInfo;
import morozovs.foodgenie.utils.AppController;

public class AddPlaceAsyncTask extends BasePlaceAsyncTask {

    public AddPlaceAsyncTask(Context context){
        super(context);
    }

    @Override
    protected Boolean doInBackground(MyPlaceInfo... params) {
        if(params.length == 0 || params.length > 1){
            return false;
        }

        AppController.getPlacesGetter().saveVisitedPlace(params[0]);
        return null;
    }
}
