package com.leadcampusapp.remote;

public class Class_ApiUtils {

    public static final String BASE_URL = "https://www.dfindia.org:82/api/";//

    //https://www.dfindia.org:82/api/Authentication/getpaytmchecksumkey?OrderId=202103291320
    public static Interface_userservice getUserService() {
        return Class_RetrofitClient.getClient(BASE_URL).create(Interface_userservice.class);
    }


}
