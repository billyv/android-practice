package com.commonsware.empublite;

/**
 * Created by Billy on 2015-03-20.
 */
public class BookUpdateInfo {
    String updatedOn;
    String updateUrl;

    // This is modeling the JSON we will be retrieving to tell Retrofit.
    // In this case, JSON looks like..
    //
    // "updatedOn": "20120512",
    // "updateUrl": "http://misc.commonsware.com/WarOfTheWorlds-Update.zip"
}
