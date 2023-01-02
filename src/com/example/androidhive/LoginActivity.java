package com.example.androidhive;
 
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.androidhive.library.UserFunctions;


 
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputSiteKey;
    TextView loginErrorMsg;
 
    //---for internet connection
    ForNetworkConnectiontStric stricMode;
    ProgressDialog prodialog;
	
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    
 
    //hide action bar
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //hide the action bar
        final ActionBar actionBar = getActionBar();
        actionBar.hide();
        // Importing all assets like buttons, text fields
        inputEmail    = (EditText) findViewById(R.id.txtUserID);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        inputSiteKey  = (EditText) findViewById(R.id.txtSiteKey);
        
        btnLogin = (Button) findViewById(R.id.btnLogin);
        //btnLinkToRegister = (Button) findViewById(R.id.btnRegister);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
        
      //--for solving the networkOnMainThread
        stricMode = new ForNetworkConnectiontStric();
        stricMode.onStricMode();
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String siteKey  = inputSiteKey.getText().toString();
                
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password, siteKey);
                prodialog = new ProgressDialog(LoginActivity.this);
                // check for login response
                try {
                	// show a dialog progress when user is login
                	//create the progress dialog
                    prodialog.setMessage("Please wait Loading...");
                    prodialog.setCancelable(false);
                    prodialog.setIndeterminate(true);
                    prodialog.show();
                    
                    if (json.getString(KEY_SUCCESS) != null) {
                    	//---
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                          //create a bundle object and send this values to the dashboard activity
                			Bundle b = new Bundle();
                			b.putString("IBIC_user", email);
                			b.putString("IBIC_pass", "aaAA11!!");
                			b.putString("CLIENT", siteKey);
                			dashboard.putExtras(b);
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                            //prodialog.dismiss();
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                        	
                    		String messages = "Incorrect username/password";
                    		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    		builder.setMessage(messages);
                    		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    			
                    			@Override
                    			public void onClick(DialogInterface dialog, int which) {
                    				// TODO Auto-generated method stub
                    				dialog.cancel();
                    				if(prodialog != null)
                    					prodialog.cancel();
                    			}
                    		});
                    		
                    		AlertDialog alertdialog=builder.create();
                    		alertdialog.show();
                    		((Button)alertdialog.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.ok_back);
                    		((Button)alertdialog.findViewById(android.R.id.button1)).setTextColor(Color.WHITE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NullPointerException np){
                	String messages = "there is no connection or the remote site is down";
            		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            		builder.setMessage(messages);
            		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            			
            			@Override
            			public void onClick(DialogInterface dialog, int which) {
            				// TODO Auto-generated method stub
            				dialog.cancel();
            				if(prodialog != null)
            					prodialog.cancel();
            			}
            		});
            		
            		AlertDialog alertdialog=builder.create();
            		alertdialog.show();
            		((Button)alertdialog.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.ok_back);
            		((Button)alertdialog.findViewById(android.R.id.button1)).setTextColor(Color.WHITE);
                }
            }
        });
 
       /* // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        }); */
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    }
    //-- show the dialog box when users clicks on "Legal information"
	
	public void showLegalInfo(View v){
		try{
			String messages = "First Rate Performance®, ver. 6.3.\n"+
                    "Copyright © 1991-2013 First Rate®, Inc. All rights reserved.\n\n"+
			          "First Rate®, Inc.\n1903 Ascension Blvd\nArlington, TX 76006\n\n"+
                    "You can also contact the First Rate®, Inc. staff via email at \n"+
			          "info@FirstRate.com\nFor more information about First Rate®, Inc., please contact us at 888.393.RATE (7283)\n\n"+
			          "Portions Copyright © 2005, Morningstar Inc. All Rights Reserved.\n"+
                    "The information, data, analyses and opinions contained herein\n"+
			          "(a) include confidential and proprietary information of Morningstar Inc,\n"+
                    "(b) may not be copied or redistributed for any purpose,\n"+
			          "(c) are provided solely for use by First Rate®, Inc. for informational purposes, and\n"+
                    "(d) are not warranted or represented to be correct, complete, accurate "+
			          "or timely. Past performance is no guarantee of future results. "+
                    "Morningstar, Inc. is not affiliated with First Rate®, Inc..\n\n"+
			          "Portions Copyright © 2005 The McGraw-Hill Companies, Inc. Standard & "+
                    "Poor's (\"S&P\") is a division of The McGraw-Hill Companies, Inc. "+
			          "Reproduction of any information obtained from First Rate Performance "+
                    "in any form is prohibited except with the written permission of S&P. Because of "+
			          "the possibility of human or mechanical error by S&P's sources, S&P or other, S&P "+
                    "does not guarantee the accuracy, adequacy, completeness or availability of any "+
			          "information and is not responsible for any errors or omissions or for the results "+
                    "obtained from the use of such information "+
			          "THERE ARE NO EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, WARRANTIES "+
                    "OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE OR USE. In no event shall S&P be "+
			          "liable for any indirect, special or consequential damages in connection with "+
                    "subscriber's or others' use of First Rate Performance.\n\n"+
			          "The Dow Jones Averages(sm) and the Dow Jones Global Indexes(sm) are compiled, calculated and "+
                    "distributed by Dow Jones & Company, Inc. and have been licensed for use. All content of "+
			          "The Dow Jones Averages(sm) and The Dow Jones Global Indexes(sm) © 2005 Dow Jones & "+
                    "Company, Inc. All Rights Reserved.\n\n"+
			          "\"Dow Jones(sm),\" \"The Dow Jones Indexes(sm),\" \"the Dow Jones Averages(sm),\" \"Dow Jones Industrial Average(sm),"+
                    "\" \"DJIA(sm),\" \"Dow Jones Transportation Average(sm),\" \"DJTA(sm),\" \"Dow Jones Utility Average(sm),\" "+
			          "\"\"DJUA(sm),\" \"Dow Jones Composite Average(sm),\" \"The Dow Jones Global Indexes(sm),\" \"DJGI(sm)\" "+
                    "and names of the component indexes of The Dow Jones Global Indexes(sm) are famous well-known and internationally "+
			          "recognized trademarks of Dow Jones & Company, Inc. and have been licensed for use by First Rate®, Inc.\n";
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
			builder.setTitle("Legal Information");
			builder.setMessage(messages);
			builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			
			AlertDialog alertdialog=builder.create();
			//show the alert dialog
			alertdialog.show();
			((Button)alertdialog.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.ok_back);
			((Button)alertdialog.findViewById(android.R.id.button1)).setTextColor(Color.WHITE);
			((Button)alertdialog.findViewById(android.R.id.button1)).setPivotX(BIND_ABOVE_CLIENT);
		} catch(NullPointerException np){
			np.printStackTrace();
		}
		
	}
    //-- show the dialog box when users clicks on "Help for User ID / Password"
    public void showHelpDialog(View v){
    	try{
    		//put the alert text to an string variable
    		String messages = "If you have forgotten your user ID, \n Passwor, or site key,\n Please contact your administrator";
    		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
    		builder.setTitle("Help for User ID / Password");
    		builder.setMessage(messages);
    		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    			
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// TODO Auto-generated method stub
    				dialog.cancel();
    			}
    		});
    		
    		AlertDialog alertdialog=builder.create();
    		alertdialog.show();
    		((Button)alertdialog.findViewById(android.R.id.button1)).setBackgroundResource(R.drawable.ok_back);
    		((Button)alertdialog.findViewById(android.R.id.button1)).setTextColor(Color.WHITE);
    	} catch(NullPointerException np){
			np.printStackTrace();
		}
		
	}
}