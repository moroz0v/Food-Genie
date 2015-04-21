package morozovs.foodgenie.interfaces;

import java.lang.reflect.Type;

public interface IDataGetter {
    public void getData(String parameters, String baseUrl, Type returnObjectType, IResponseHandler callback, IPlacesGetterResponseHandler placesCallback);
}
