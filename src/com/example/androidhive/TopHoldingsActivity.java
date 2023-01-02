package com.example.androidhive;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.widget.SimpleAdapter;

import com.example.androidhive.library.UrlConnection;
import com.example.androidhive.library.XMLParser;

public class TopHoldingsActivity extends ListActivity{
	// All static variables
	UrlConnection urlCon = new UrlConnection();
	// using api call url
    String apiCallUri = urlCon.getAPI();
    //AS OF DATE FOR CALL
    String apiAsOf;
    
	// XML node keys
	static final String KEY_TOPHOLDINGS= "TOPHOLDINGS"; //Parent node
	static final String KEY_THASSET    = "THASSET";
	static final String KEY_ASSETID    = "ASSETID"; 
	static final String KEY_ASSETNAME  = "ASSETNAME";
	static final String KEY_MKTVAL     = "MKTVAL";
	static final String KEY_COST       = "COST";
	static final String KEY_ALLOCATION  = "ALLOCATION";
	static final String KEY_GL         = "GL";
	static final String KEY_PERGL      = "PERGL"; // 
	
	Button goBack;
	ForNetworkConnectiontStric stricMode;
	public static final String KEY_LOAD_DATA = "load_data";
	
	String selectedAcct, sitek, userid, pass;
	
	//*******************************************************************
    // samini.September, 03 2014: this code will log off the application
    //*******************************************************************
    public static final long DISCONNECT_TIMEOUT = 300000; // 5 min = 5 * 60 * 1000 ms

    private Handler disconnectHandler = new Handler(){
        public void handleMessage(Message msg) {
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
        	try{
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                
                finish();
        	} catch(NullPointerException np){
        		np.printStackTrace();
        	}
        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }
    //*******************************************************************
    // samini.September, 03 2014: end of loging off
    //*******************************************************************
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		//---get parameter from DashboardActivity.java
		Bundle extras = getIntent().getExtras();
		selectedAcct = extras.getString("ACCT");
		sitek  = extras.getString("CLIENT");
		apiAsOf=extras.getString("ASOF");
		userid = extras.getString("IBIC_user");
    	pass   = extras.getString("IBIC_pass");

		//hide the action bar
        final ActionBar actionBar = getActionBar();
        actionBar.hide();
        
	 //--for solving the networkOnMainThread
		//--for solving the networkOnMainThread
		stricMode = new ForNetworkConnectiontStric();
        stricMode.onStricMode();
//.......................................
//---check if the user is clicked on back batton
       goBack = (Button) findViewById(R.id.btngoback);
       goBack.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(TopHoldingsActivity.this,DashboardActivity.class);
			//create a bundle object
			Bundle b = new Bundle();
			b.putString("TOPHOLD", "TopHoldings");
			b.putString("PERFORMER", "Performance");
			b.putString("ACCT", selectedAcct);
			b.putString("CLIENT", sitek);
			b.putString("ASOF", apiAsOf);
			b.putString("IBIC_user", userid);
			b.putString("IBIC_pass", pass);
			
			i.putExtras(b);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        startActivity(i);
            // Closing top Holdings screen
            finish();
			
		}
	});
     //-------top holdings data -----------------------------------------   
       topHoldingsData();
    //-------------------------------------------------------------------

	}// end of the onCreate method
	
	//--
    public String getAPIuri(){
    	String apiParam = selectedAcct+"&IBIC_user="+userid+"&IBIC_pass="+pass+"&CLIENT=DV"+sitek+"63&ASOFDATE="+apiAsOf;
    	return apiParam;
    }
    
	public void topHoldingsData(){
		try{
			ArrayList<HashMap<String, String>> menuItemsM = new ArrayList<HashMap<String, String>>();
		       
		       XMLParser parserM = new XMLParser();    	    
		       String xmlM = parserM.getXmlFromUrl(apiCallUri+this.getAPIuri()); // getting XML    	    
		       Document docM = parserM.getDomElement(xmlM); // getting DOM element

		       NodeList nlMTDM = docM.getElementsByTagName(KEY_TOPHOLDINGS);
		       
		         if(nlMTDM != null && nlMTDM.getLength() > 0){
		       	  //--loop for mtd tag
		       	  for(int k=0; k <nlMTDM.getLength(); k++){
		       	// looping through all item nodes <item>
		       		  Node nodeM =nlMTDM.item(k);
		       		  if(nodeM.getNodeType() == Node.ELEMENT_NODE){
		       			  Element ePM = (Element) nodeM;
		       			  NodeList nlM = ePM.getElementsByTagName(KEY_THASSET);  
		       		  
		       	for (int i = 0; i < nlM.getLength(); i++) {
		       	// creating new HashMap
		       	HashMap<String, String> mapM = new HashMap<String, String>();
		       	Element eM = (Element) nlM.item(i);
		       	// adding each child node to HashMap key => value
		       	mapM.put(KEY_ASSETID, parserM.getValue(eM, KEY_ASSETID));
		       	mapM.put(KEY_ASSETNAME, parserM.getValue(eM, KEY_ASSETNAME));
		       	mapM.put(KEY_MKTVAL, parserM.getValue(eM, KEY_MKTVAL).trim());
		       	mapM.put(KEY_COST, parserM.getValue(eM, KEY_COST).trim());
		       	mapM.put(KEY_ALLOCATION, parserM.getValue(eM, KEY_ALLOCATION).trim());
		       	mapM.put(KEY_GL, parserM.getValue(eM, KEY_GL).trim());
		       	mapM.put(KEY_PERGL, parserM.getValue(eM, KEY_PERGL).trim());
		       	// adding HashList to ArrayList
		       	menuItemsM.add(mapM);
		       	}
		          }
		          }
		         }
		         
		      // Adding menuItems to ListView
		         
		       // ListView toplst = (ListView) findViewById(R.id.topHoldingslst);
		         ListAdapter adapter = new SimpleAdapter(this, menuItemsM,R.layout.topholdings,
		        		 new String[] { KEY_ASSETID, KEY_ASSETNAME, KEY_MKTVAL,KEY_COST,KEY_ALLOCATION,KEY_GL,KEY_PERGL }, new int[] 
		        		 {
		        		    R.id.assetid, R.id.assetname,R.id.mktval,R.id.cost,R.id.allocation,R.id.gainloss,R.id.pergainloss });
		         setListAdapter(adapter);
		} catch (NullPointerException np){
			np.printStackTrace();
		}//end of the holdings data
	}
	
	//************************************************************************************
    ///////////application status//////////////////////////////////////
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	resetDisconnectTimer();
    }
    
    @Override
    protected void onRestart(){
    	super.onRestart();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
  //************************************************************************************

}
