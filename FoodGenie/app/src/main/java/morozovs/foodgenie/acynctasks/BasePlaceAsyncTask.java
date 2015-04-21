package morozovs.foodgenie.acynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import morozovs.foodgenie.models.MyPlaceInfo;

public abstract class BasePlaceAsyncTask extends AsyncTask<MyPlaceInfo, Void, Boolean> {
    protected Context context;

    public BasePlaceAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(Boolean success){
        super.onPostExecute(success);
        String msg;
        if(success == null || success)
            msg = "Success";
        else msg = "Failure";
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
