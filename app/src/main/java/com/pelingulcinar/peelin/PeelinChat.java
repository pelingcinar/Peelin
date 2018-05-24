package com.pelingulcinar.peelin;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
//import com.squareup.picasso.0kHttDownloader;
//import com.squareup.picasso.Picasso;

/**
 * Created by Pelin on 11.05.2018.
 */

public class PeelinChat extends Application {

    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //Picasso



    }
}
