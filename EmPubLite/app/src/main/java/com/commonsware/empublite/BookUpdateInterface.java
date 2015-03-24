package com.commonsware.empublite;

/**
 * Created by Billy on 2015-03-20.
 */

import retrofit.http.GET;

public interface BookUpdateInterface {
    @GET("/misc/empublite-update.json")
    BookUpdateInfo update();
}
