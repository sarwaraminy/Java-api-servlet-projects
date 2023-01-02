package com.example.androidhive;

import android.os.StrictMode;

public class ForNetworkConnectiontStric {
	
	// create a method and close the apps
    public void onStricMode(){
    	if (BuildConfig.DEBUG) {
    	    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
    	          .detectDiskReads()
    	          .detectDiskWrites()
    	          .detectNetwork()
    	          .penaltyLog()
    	          .build());
    	    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
    	          .detectAll()
    	          .penaltyLog()
    	           .build());
    	}
    }// end of the method

}
