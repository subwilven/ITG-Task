package com.islam.basepropject.project_base.base.POJO;

import com.islam.basepropject.R;

public class ErrorModel {
    private int title;
    private int message;

    public ErrorModel(int title, int message) {
        this.title = title;
        this.message = message;
    }

    public boolean isFreeError(){
        return  title == -1 ;

    }

    public static ErrorModel noConnection(){
        return new ErrorModel(R.string.no_internet_connection, R.string.check_your_mobile_data_or_wi_fi);
    }

    public static ErrorModel timeOut(){
        return new ErrorModel(R.string.server_cannot_be_reached, R.string.please_try_again_later);
    }

    public static ErrorModel freeError(){
        return new ErrorModel(-1, -1);
    }


    public int getTitle() {
        return title;
    }


    public int getMessage() {
        return message;
    }

}
