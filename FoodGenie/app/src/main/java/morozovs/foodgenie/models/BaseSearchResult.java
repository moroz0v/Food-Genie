package morozovs.foodgenie.models;

import morozovs.foodgenie.utils.StringUtils;

public class BaseSearchResult {
    public String status;


    public boolean validResults(){
        return !StringUtils.isNullOrEmpty(status) && (status.equals("OK"));
    }

}
