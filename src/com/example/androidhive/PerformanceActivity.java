package com.example.androidhive;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.widget.SimpleAdapter;
import android.widget.TabHost.TabSpec;

import com.example.androidhive.library.UrlConnection;
import com.example.androidhive.library.XMLParser;

public class PerformanceActivity extends ListActivity {
	// All static variables
		UrlConnection urlCon = new UrlConnection();
		// using api call url
	    String apiCallUri = urlCon.getAPI();
	    String selectedAcct, sitek, apiAsOf, userid, pass;
	    //get the point to point xml
	    String URLptp = urlCon.getURLptp();
	    
	    java.util.Date finalFrmDate=null;
		java.util.Date finalToDate = null;
		//for api
		String apiFromDate, apiToDate;
		
		// XML node keys
		static final String KEY_PPCATEGORIES= "PPCATEGORIES"; //Parent node
		static final String KEY_PPCATEGORY = "PPCATEGORY";
		static final String KEY_NAME       = "NAME"; 
		static final String KEY_CATEGORY   = "CATEGORY";
		static final String KEY_TYPE       = "TYPE";
		static final String KEY_MKTVAL     = "MKTVAL";
		static final String KEY_RETURN1    = "RETURN1";
		static final String KEY_RETURN2    = "RETURN2";
		static final String KEY_RETURN3    = "RETURN3";
		static final String KEY_RETURN4    = "RETURN4"; // 
		static final String KEY_RETURN5    = "RETURN5"; // parent node
		static final String KEY_RETURN6    = "RETURN6"; // parent node
		static final String KEY_RETURN7    = "RETURN7"; // parent node
		//get the sectors benchmarks
		static final String KEY_PPBENCHMARKS = "PPBENCHMARKS";
		static final String KEY_PPBENCHMARK  = "PPBENCHMARK";
		
		// for period performance sections
		static final String KEY_PERIODPERFORMANCE = "PERIODPERFORMANCE";
		static final String KEY_PERIODS = "PERIODS";
		static final String KEY_PERIOD1 = "PERIOD1";
		static final String KEY_PERIOD2 = "PERIOD2";
		static final String KEY_PERIOD3 = "PERIOD3";
		static final String KEY_PERIOD4 = "PERIOD4";
		static final String KEY_PERIOD5 = "PERIOD5";
		static final String KEY_PERIOD6 = "PERIOD6";
		static final String KEY_PERIOD7 = "PERIOD7";
		
		// XML node keys
		static final String KEY_TOPPERFORMERS  = "TOPPERFORMERS"; //Parent node
		static final String KEY_FROMDATE       = "FROMDATE";
		static final String KEY_TPASSET        = "TPASSET";
		static final String KEY_ASSETID        = "ASSETID"; 
		static final String KEY_ASSETNAME      = "ASSETNAME";
		static final String KEY_RETURN         = "RETURN";
		static final String KEY_EARNINGS       = "EARNINGS";
		static final String KEY_ALLOCATION     = "ALLOCATION";
		static final String KEY_ASSETCLASSCONT = "ASSETCLASSCONT";
		
		//----for point to point
		static final String KEY_PTPreturns     = "PTPRETURNS";
		static final String KEY_PTPreturn      = "PTPRETURN";
		static final String KEY_date           = "DATE";
		static final String KEY_type           = "TYPE";
		static final String KEY_retvalue       = "RETVALUE";
		static final String KEY_cumdailyret    = "CUMDAILYRET";

		private int mYear, mMonth, mDay;
	    static final int DATE_DIALOG_ID = 0;
	    TextView frmDateDisp, toDateDisplay;
	    Button frmPickDate, toPickDate;
	    private int btnClick = 0;
		Button goBack, goPtP;
		ForNetworkConnectiontStric stricMode;	

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
			this.setContentView(R.layout.performance_layout);
			
			//---get parameter from DashboardActivity.java
			Bundle extras = getIntent().getExtras();
			selectedAcct = extras.getString("ACCT");
			sitek  = extras.getString("CLIENT");
			apiAsOf=extras.getString("ASOF");
			userid = extras.getString("IBIC_user");
	    	pass   = extras.getString("IBIC_pass");
	    	//System.out.println("from dashboard activity: userid=>"+userid+" password=>"+pass+" sitekey=>"+sitek);
			//hide the action bar
	        final ActionBar actionBar = getActionBar();
	        actionBar.hide();
	        
		 //--for solving the networkOnMainThread
			stricMode = new ForNetworkConnectiontStric();
            stricMode.onStricMode();
	//.......................................
           
	//---check if the user is clicked on back batton
	       goBack = (Button) findViewById(R.id.btngoback);
	       goBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(PerformanceActivity.this,DashboardActivity.class);
				//create a bundle object
				Bundle b = new Bundle();
				b.putString("PERFORMER", "Performance");
				b.putString("TOPHOLD", "TopHoldings");
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
	       
	       // setup the Calander buttons
	     //---the following is for datepicker---------------------
           frmDateDisp = (TextView) findViewById(R.id.frmDateDisplay);
           frmPickDate = (Button) findViewById(R.id.frmPickDate);

           frmPickDate.setOnClickListener(new View.OnClickListener() {
        	   
           	@Override
               public void onClick(View v) {
               	try{
               		showDialog(DATE_DIALOG_ID);
               		btnClick = 1;
               	} catch(NullPointerException np){
               		np.printStackTrace();
               	} 
               }
           });//end of the datepicker listener

           
           //--------------------------------------------------------
           
         //---the following is for datepicker---------------------
           toDateDisplay = (TextView) findViewById(R.id.toDateDisplay);
           toPickDate = (Button) findViewById(R.id.toPickDate);

           toPickDate.setOnClickListener(new View.OnClickListener() {
           	
           	@Override
               public void onClick(View v) {
               	try{
               		showDialog(DATE_DIALOG_ID);
               		btnClick = 2;
               	} catch(NullPointerException np){
               		np.printStackTrace();
               	} 
               }
           });//end of the datepicker listener

           final Calendar c = Calendar.getInstance();
           
           mYear = c.get(Calendar.YEAR);
           mMonth = c.get(Calendar.MONTH);
           mDay = c.get(Calendar.DAY_OF_MONTH);
           updateDisplay();
	       
	     //-----***************for the tab settings *************************************
	       performanceTab();
        
  //---------For Overview data 
	       overViewData();
	       //top performance data
	       topPerformanceData();
          //Add the the point to point data
          pointToPointData();
          
          // check the go button is clicked on Point to Point tab
          goPtP = (Button) findViewById(R.id.btnGo);
          goPtP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pointToPointData();
			}
		});
		}
		
		//--
	    public String getAPIuri(){
	    	String apiParam = selectedAcct+"&IBIC_user="+userid+"&IBIC_pass="+pass+"&CLIENT=DV"+sitek+"63&ASOFDATE="+apiAsOf;
	    	return apiParam;
	    }
	    
	    // return the point to point api
	    public String getAPIptpUri(){
	    	String apiParam = selectedAcct+"&IBIC_user="+userid+"&IBIC_pass="+pass+"&CLIENT=DV"+sitek+"63&ASOFDATE="+apiToDate+"&FROMDATE="+apiFromDate;
	    	return apiParam;
	    }
	    
		// create the Overview data
		public void overViewData(){
			try{
				ArrayList<HashMap<String, String>> menuItemsOv = new ArrayList<HashMap<String, String>>();
	      	    HashMap<String, String> mapMT = new HashMap<String, String>();

	      	    XMLParser parserM = new XMLParser();    	    
	      	    String xmlM = parserM.getXmlFromUrl(apiCallUri+this.getAPIuri()); // getting XML    	    
	      	    Document docM = parserM.getDomElement(xmlM); // getting DOM element
	      	    mapMT.put(KEY_NAME, "   Sector");
	      	    mapMT.put(KEY_MKTVAL, "Market Value");
	      	    
	      	 // get the Periods title from xml*******************************
	      	  NodeList nlPR = docM.getElementsByTagName(KEY_PERIODPERFORMANCE);
	    	       
	  	      if(nlPR != null && nlPR.getLength() > 0){
	  	    	  //--loop for mtd tag
	  	    	  for(int p=0; p <nlPR.getLength(); p++){
	  	    	// looping through all item nodes <item>
	  	    		  Node nodeP =nlPR.item(p);
	  	    		  if(nodeP.getNodeType() == Node.ELEMENT_NODE){
	  	    			  Element ePR = (Element) nodeP;
	  	    			  NodeList nlP = ePR.getElementsByTagName(KEY_PERIODS);  
	  	    		  
	  	    	for (int ip = 0; ip < nlP.getLength(); ip++) {
	  	    	Element eP = (Element) nlP.item(ip);
	  	    	// adding each child node to HashMap key => value
	  	    	mapMT.put(KEY_RETURN1, parserM.getValue(eP, KEY_PERIOD1));
	  	    	mapMT.put(KEY_RETURN2, parserM.getValue(eP, KEY_PERIOD2));
	  	    	mapMT.put(KEY_RETURN3, parserM.getValue(eP, KEY_PERIOD3));
	  	    	mapMT.put(KEY_RETURN4, parserM.getValue(eP, KEY_PERIOD4));
	  	    	mapMT.put(KEY_RETURN5, parserM.getValue(eP, KEY_PERIOD5));
	  	    	mapMT.put(KEY_RETURN6, parserM.getValue(eP, KEY_PERIOD6));
	  	    	mapMT.put(KEY_RETURN7, parserM.getValue(eP, KEY_PERIOD7)); 	    	
	  	    	
	  	    	// adding HashList to ArrayList
	  	    	menuItemsOv.add(mapMT);
	  	    	}
	  	       }
	  	       }
	  	      }
	  	      
	  	 // Adding menuItems to ListView
	     	   ListView overv = (ListView) findViewById(R.id.overviewh);
	     	   ListAdapter adapterO = new SimpleAdapter(this, menuItemsOv,
	     	   R.layout.overview_cheader,
	     	   new String[] { KEY_NAME, KEY_MKTVAL, KEY_RETURN1, KEY_RETURN2, KEY_RETURN3,
	     			   KEY_RETURN4, KEY_RETURN5, KEY_RETURN6, KEY_RETURN7}, new int[]
	     	   {
	     	   R.id.soriname, R.id.mktval, R.id.ret1, R.id.ret2, R.id.ret3, R.id.ret4,
	     	   R.id.ret5, R.id.ret6, R.id.ret7});
	     	  //lst_mtd.setAdapter(adapterM);
	     	  overv.setAdapter(adapterO);
	     	
	      	//*****************************************************
	     	 ArrayList<HashMap<String, String>> menuItemsM = new ArrayList<HashMap<String, String>>();
	      	    NodeList nlMTDM = docM.getElementsByTagName(KEY_PPCATEGORIES);
	      	    NodeList nlBen = docM.getElementsByTagName(KEY_PPBENCHMARKS);
	      	    NodeList nlPPSec = docM.getElementsByTagName("PPSECTORS");
	      	    
	      	      if(nlMTDM != null && nlMTDM.getLength() > 0){
	      	    	  //--loop for mtd tag
	      	    	  for(int k=0; k <nlMTDM.getLength(); k++){
	      	    	// looping through all item nodes <item>
	      	    		  Node nodeM =nlMTDM.item(k);
	      	    		  if(nodeM.getNodeType() == Node.ELEMENT_NODE){
	      	    			  Element ePM = (Element) nodeM;
	      	    			  NodeList nlM = ePM.getElementsByTagName(KEY_PPCATEGORY);  
	      	    		  
	      	    	for (int i = 0; i < nlM.getLength(); i++) {
	      	    	// creating new HashMap
	      	    	HashMap<String, String> mapM = new HashMap<String, String>();
	      	    	Element eM = (Element) nlM.item(i);
	      	    	// adding each child node to HashMap key => value
	      	    	mapM.put(KEY_NAME, parserM.getValue(eM, KEY_NAME));
	      	    	mapM.put(KEY_CATEGORY, parserM.getValue(eM, KEY_CATEGORY));
	      	    	mapM.put(KEY_TYPE, parserM.getValue(eM, KEY_TYPE));
	      	    	mapM.put(KEY_MKTVAL, parserM.getValue(eM, KEY_MKTVAL));
	      	    	mapM.put(KEY_RETURN1, parserM.getValue(eM, KEY_RETURN1).trim());
	      	    	mapM.put(KEY_RETURN2, parserM.getValue(eM, KEY_RETURN2).trim());
	      	    	mapM.put(KEY_RETURN3, parserM.getValue(eM, KEY_RETURN3).trim());
	      	    	mapM.put(KEY_RETURN4, parserM.getValue(eM, KEY_RETURN4).trim());
	      	    	mapM.put(KEY_RETURN5, parserM.getValue(eM, KEY_RETURN5).trim());
	      	    	mapM.put(KEY_RETURN6, parserM.getValue(eM, KEY_RETURN6).trim());
	      	    	mapM.put(KEY_RETURN7, parserM.getValue(eM, KEY_RETURN7).trim());
	      	    	
	      	    	// adding HashList to ArrayList
	      	    	menuItemsM.add(mapM);
                   
	        	    	}}}}
                     ////////////////////add the sub element to the category
	      	    	if(nlPPSec != null && nlPPSec.getLength() > 0){
	      	    		//-- loop 
	      	    		for(int sk=0; sk <nlPPSec.getLength(); sk++){
	      	    			//--looping through all item nodes <ppsector>
	      	    			Node nodePPS = nlPPSec.item(sk);
	      	    			if(nodePPS.getNodeType() == Node.ELEMENT_NODE){
	      	    				Element ePPS = (Element) nodePPS;
	      	    				NodeList ppsL = ePPS.getElementsByTagName("PPSECTOR");
	      	    				
	      	    				for(int elPPS=0; elPPS<ppsL.getLength(); elPPS++){
	      	    				// creating new HashMap
	      	  	      	    	HashMap<String, String> mPPS = new HashMap<String, String>();
	      	    				Element eLPPS = (Element) ppsL.item(elPPS);
	      	    				// adding each child node to HashMap key => value
	      	    				mPPS.put(KEY_NAME, parserM.getValue(eLPPS, KEY_NAME));
	      	    				mPPS.put(KEY_CATEGORY, parserM.getValue(eLPPS, KEY_CATEGORY));
	      	    				mPPS.put(KEY_TYPE, parserM.getValue(eLPPS, KEY_TYPE));
	      	    				mPPS.put(KEY_MKTVAL, parserM.getValue(eLPPS, KEY_MKTVAL));
	      	    				mPPS.put(KEY_RETURN1, parserM.getValue(eLPPS, KEY_RETURN1).trim());
	      	    				mPPS.put(KEY_RETURN2, parserM.getValue(eLPPS, KEY_RETURN2).trim());
	      	    				mPPS.put(KEY_RETURN3, parserM.getValue(eLPPS, KEY_RETURN3).trim());
	      	    				mPPS.put(KEY_RETURN4, parserM.getValue(eLPPS, KEY_RETURN4).trim());
	      	    				mPPS.put(KEY_RETURN5, parserM.getValue(eLPPS, KEY_RETURN5).trim());
	      	    				mPPS.put(KEY_RETURN6, parserM.getValue(eLPPS, KEY_RETURN6).trim());
	      	    				mPPS.put(KEY_RETURN7, parserM.getValue(eLPPS, KEY_RETURN7).trim());
	      	    				
	      	    			// adding HashList to ArrayList
	      		      	    	menuItemsM.add(mPPS);
	      	    				}
	      	    			}
	      	    		}
	      	    	}
	      	 // Benchmarks // read the second childe for reading     	    	 
	        	    
	    	      if(nlBen != null && nlBen.getLength() > 0){
	    	    	  //--loop for mtd tag
	    	    	  for(int j=0; j <nlBen.getLength(); j++){
	    	    	// looping through all item nodes <item>
	    	    		Node  nodeM =nlBen.item(j);
	    	    		  if(nodeM.getNodeType() == Node.ELEMENT_NODE){
	    	    		Element ePM = (Element) nodeM;
	    	    		NodeList nlM = ePM.getElementsByTagName(KEY_PPBENCHMARK);  
	    	    		  
	    	    	for (int n = 0; n < nlM.getLength(); n++) {
	    	    	// creating new HashMap
	          	    HashMap<String, String> mapM = new HashMap<String, String>();
	    	    	Element eM = (Element) nlM.item(n);
	    	    	// adding each child node to HashMap key => value
	       	    	mapM.put(KEY_NAME,"            "+ parserM.getValue(eM, KEY_NAME));
	       	    	mapM.put(KEY_CATEGORY, parserM.getValue(eM, KEY_CATEGORY));
	       	    	mapM.put(KEY_TYPE, parserM.getValue(eM, KEY_TYPE));
	       	    	mapM.put(KEY_MKTVAL, parserM.getValue(eM, KEY_MKTVAL));
	       	    	mapM.put(KEY_RETURN1, parserM.getValue(eM, KEY_RETURN1).trim());
	       	    	mapM.put(KEY_RETURN2, parserM.getValue(eM, KEY_RETURN2).trim());
	       	    	mapM.put(KEY_RETURN3, parserM.getValue(eM, KEY_RETURN3).trim());
	       	    	mapM.put(KEY_RETURN4, parserM.getValue(eM, KEY_RETURN4).trim());
	       	    	mapM.put(KEY_RETURN5, parserM.getValue(eM, KEY_RETURN5).trim());
	       	    	mapM.put(KEY_RETURN6, parserM.getValue(eM, KEY_RETURN6).trim());
	       	    	mapM.put(KEY_RETURN7, parserM.getValue(eM, KEY_RETURN7).trim());
	       	    	
	       	  // adding HashList to ArrayList
	      	    	menuItemsM.add(mapM);
	      	    	}
	      	       }
	      	       }
	      	      }
	      	    
	      	      
	      	   // Adding menuItems to ListView
	      	   ListView lst_mtd = (ListView) findViewById(R.id.mtd_list);
	      	   ListAdapter adapterM = new SimpleAdapter(this, menuItemsM,
	      	   R.layout.performance,
	      	   new String[] { KEY_NAME, KEY_MKTVAL, KEY_RETURN1, KEY_RETURN2, KEY_RETURN3,
	      			   KEY_RETURN4, KEY_RETURN5, KEY_RETURN6, KEY_RETURN7}, new int[]
	      	   {
	      	   R.id.soriname, R.id.mktval, R.id.returna, R.id.returnb, R.id.returnc, R.id.returnd,
	      	   R.id.returne, R.id.returnf, R.id.returng});
	      	  //lst_mtd.setAdapter(adapterM);
	      	lst_mtd.setAdapter(adapterM);
			} catch (NullPointerException np){
				np.printStackTrace();
			}
		}
		//---- create the point to point datqa
		public void pointToPointData(){
		try{
      	    ArrayList<HashMap<String, String>> menuItemsptp = new ArrayList<HashMap<String, String>>();
      	    HashMap<String, String> mapptp = new HashMap<String, String>();

      	    XMLParser parserptp = new XMLParser();    	    
      	    String xmlptp = parserptp.getXmlFromUrl(URLptp+this.getAPIptpUri()); // getting XML 
      	    //System.out.println("the ptp url: "+URLptp+this.getAPIptpUri());
      	    Document docptp = parserptp.getDomElement(xmlptp); // getting DOM element
      	    
      	  mapptp.put(KEY_date, "  Date");
      	  //mapptp.put(KEY_type, "Return Type");
      	  mapptp.put(KEY_retvalue, "Return");
      	  mapptp.put(KEY_cumdailyret, "Cumolitive Daily Return");
	
  	    	// adding HashList to ArrayList
      	menuItemsptp.add(mapptp);
  	      
  	 // Adding menuItems to ListView
     	   ListView overv = (ListView) findViewById(R.id.ptp_header);
     	   ListAdapter adapterPtP = new SimpleAdapter(this, menuItemsptp,
     	   R.layout.ch_point_to_point,
     	   new String[] { KEY_date, KEY_retvalue, KEY_cumdailyret}, new int[]
     	   {
     	   R.id.ch_date, R.id.ret_value, R.id.cum_val});
     	  //lst_mtd.setAdapter(adapterM);
     	  overv.setAdapter(adapterPtP);
  	     
     	// ******************* get Account Inceptoin Date *******************
     	    // find the Text views 
     	    TextView txtICPDATE  = (TextView) findViewById(R.id.txtIncept);
     	    TextView txtDAILYICP = (TextView) findViewById(R.id.txtDailyIncept);
     	    TextView txtCDR      = (TextView) findViewById(R.id.txtCumulativeRet);
 
     	    // get the parent element
     	    NodeList acctn       = docptp.getElementsByTagName("POINTTOPOINT");
     	    
     	    //Loop through all items
     	    for(int c = 0; c < acctn.getLength(); c++){
     	    	Node incept = acctn.item(c);
     	    	if(incept.getNodeType() == Node.ELEMENT_NODE){
     	    		Element firstIncept = (Element)incept;
     	    		
     	    		//-------ICPDATE
     	    		NodeList firstInceptList    = firstIncept.getElementsByTagName("ICPDATE");
     	    		Element  firstInceptElement = (Element)firstInceptList.item(0);
     	    		NodeList textInceptList     = firstInceptElement.getChildNodes();
     	    		txtICPDATE.setText("  Inception Date: " +((Node)textInceptList.item(0)).getNodeValue().trim());
     	    		
     	    		//-------DAILYICP
     	    		NodeList firstDailyIcpList    = firstIncept.getElementsByTagName("DAILYICP");
     	    		Element  firstDailyIcpElement = (Element)firstDailyIcpList.item(0);
     	    		NodeList textDailyIcpList     = firstDailyIcpElement.getChildNodes();
     	    		txtDAILYICP.setText("Daily Inception: " + ((Node)textDailyIcpList.item(0)).getNodeValue().trim());
     	    		
     	    		//--------Cumulative Daily Return
     	    		NodeList firstCumulativeList    = firstIncept.getElementsByTagName("CUMULATIVERETURN");
     	    		Element  firstCumulativeElement = (Element)firstCumulativeList.item(0);
     	    		NodeList textCumulativeList     = firstCumulativeElement.getChildNodes();
     	    		 // get the percentage of values
  	    		  Double sumCumulative[] = new Double[acctn.getLength()];
  	    		  sumCumulative[c] = Double.parseDouble(((Node)textCumulativeList.item(0)).getNodeValue());
     	    		txtCDR.setText("Cumulative Daily Return: " + sumCumulative[c]);
     	    	}
     	    }
  	        
      	//*****************************************************
     	 ArrayList<HashMap<String, String>> menuItemsPtPD = new ArrayList<HashMap<String, String>>();
      	    NodeList nlMTDM = docptp.getElementsByTagName(KEY_PTPreturns);
      	      if(nlMTDM != null && nlMTDM.getLength() > 0){
      	    	  //--loop for mtd tag
      	    	  for(int k=0; k <nlMTDM.getLength(); k++){
      	    	// looping through all item nodes <item>
      	    		  Node nodeM =nlMTDM.item(k);
      	    		  if(nodeM.getNodeType() == Node.ELEMENT_NODE){
      	    			  Element ePM = (Element) nodeM;
      	    			  NodeList nlM = ePM.getElementsByTagName(KEY_PTPreturn);
      	    			  
      	    	for (int i = 0; i < nlM.getLength(); i++) {
      	    	// creating new HashMap
	        	    	HashMap<String, String> mapM = new HashMap<String, String>();
	        	    	Element eM = (Element) nlM.item(i);
	                      // adding each child node to HashMap key => value
      	        	    	mapM.put(KEY_date, parserptp.getValue(eM, KEY_date).trim());
      	        	    	//mapM.put(KEY_type, parserptp.getValue(eM, KEY_type).trim());
      	        	    	mapM.put(KEY_retvalue, parserptp.getValue(eM, KEY_retvalue).trim());
      	        	    	mapM.put(KEY_cumdailyret, parserptp.getValue(eM, KEY_cumdailyret).trim());
      	    		menuItemsPtPD.add(mapM);

      	    	
      	    	}
      	       }
      	       }
      	      }
      	    
      	      
      	   // Adding menuItems to ListView
      	   ListView lst_mtd = (ListView) findViewById(R.id.point_to_plist);
      	   ListAdapter adapterM = new SimpleAdapter(this, menuItemsPtPD,
      	   R.layout.point_to_point,
      	   new String[] { KEY_date, KEY_retvalue, KEY_cumdailyret}, new int[]
      	   {
      	   R.id.date, R.id.retvalue, R.id.cum_val});
      	  //lst_mtd.setAdapter(adapterM);
      	lst_mtd.setAdapter(adapterM);
	} catch (NullPointerException npe){
			 npe.printStackTrace();
		  }
     
		}// end of the point to point
		
		//create the top performance data
		public void topPerformanceData(){
			try{
				ArrayList<HashMap<String, String>> menuItemsTop = new ArrayList<HashMap<String, String>>();
		        
		        XMLParser parserT = new XMLParser();    	    
		        String xmlT = parserT.getXmlFromUrl(apiCallUri+this.getAPIuri()); // getting XML    	    
		        Document docT = parserT.getDomElement(xmlT); // getting DOM element
		        NodeList nlMTDT = docT.getElementsByTagName(KEY_TOPPERFORMERS);
		        
		          if(nlMTDT != null && nlMTDT.getLength() > 0){
		        	  //--loop for mtd tag
		        	  for(int k=0; k <nlMTDT.getLength(); k++){
		        	// looping through all item nodes <item>
		        		  Node nodeT =nlMTDT.item(k);
		        		  if(nodeT.getNodeType() == Node.ELEMENT_NODE){
		        			  Element ePT = (Element) nodeT;
		        			  NodeList nlT = ePT.getElementsByTagName(KEY_TPASSET);  
		        		  
		        	for (int i = 0; i < nlT.getLength(); i++) {
		        	// creating new HashMap
		        	HashMap<String, String> mapT = new HashMap<String, String>();
		        	Element eT = (Element) nlT.item(i);
		        	// adding each child node to HashMap key => value
		        	mapT.put(KEY_ASSETID, parserT.getValue(eT, KEY_ASSETID));
		        	mapT.put(KEY_ASSETNAME, parserT.getValue(eT, KEY_ASSETNAME));
		        	mapT.put(KEY_RETURN, parserT.getValue(eT, KEY_RETURN).trim());
		        	mapT.put(KEY_EARNINGS, parserT.getValue(eT, KEY_EARNINGS).trim());
		        	mapT.put(KEY_MKTVAL, parserT.getValue(eT, KEY_MKTVAL).trim());        	
		        	mapT.put(KEY_ALLOCATION, parserT.getValue(eT, KEY_ALLOCATION).trim());
		        	mapT.put(KEY_ASSETCLASSCONT, parserT.getValue(eT, KEY_ASSETCLASSCONT).trim());
		        	// adding HashList to ArrayList
		        	menuItemsTop.add(mapT);
		        	}
		           }
		           }
		          }
		          
		       // Adding menuItems to ListView
		          
		        // ListView toplst = (ListView) findViewById(R.id.topHoldingslst);
		          ListAdapter adapter = new SimpleAdapter(this, menuItemsTop,R.layout.topperformers_layout,
		         		 new String[] { KEY_ASSETID, KEY_ASSETNAME, KEY_RETURN, KEY_EARNINGS, 
		        		  KEY_MKTVAL, KEY_ALLOCATION,KEY_ASSETCLASSCONT }, new int[] 
		         		 {
		         		    R.id.assetid, R.id.assetname,R.id.assetreturn,R.id.earnings, R.id.mktval,
		         		    R.id.allocation,R.id.assetclasscount });
		          setListAdapter(adapter);
			} catch (NullPointerException np){
				np.printStackTrace();
			}
		}
		//create the Performance tab
		public void performanceTab(){
			try{
	           TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
	           tabHost.setup();
	        
	           TabSpec spec1=tabHost.newTabSpec("Month To Date");
	           spec1.setContent(R.id.tab1);
	           spec1.setIndicator("Overview");
	                
	           TabSpec spec2=tabHost.newTabSpec("Year To Date");
	           spec2.setIndicator("Point to Point");
	           spec2.setContent(R.id.tab2);
	           
	           TabSpec spec3=tabHost.newTabSpec("Year To Date");
	           spec3.setIndicator("Top Performance");
	           spec3.setContent(R.id.tab3);
	       
	           tabHost.addTab(spec1);
	           tabHost.addTab(spec2);
	           tabHost.addTab(spec3);
	           
	           TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
	           tv.setTextSize(10);
	           
	           TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
	           tv2.setTextSize(10);
	           
	           TextView tv3 = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
	           tv3.setTextSize(10);
			} catch (NullPointerException np){
				np.printStackTrace();
			}
		}
		
		//update the clander display
	    private void updateDisplay() {
	    	int localMonth = (mMonth + 1);
	    	String monthString = localMonth < 10 ? "0" + localMonth : Integer
	    			.toString(localMonth);
	    	String dayString = mDay < 10 ? "0" + mDay : Integer
	    			.toString(mDay);
	    	String monthName = "";
	    	if(localMonth == 1)
	    		monthName = "January";
	    	else if(localMonth == 2)
	    		monthName = "February";
	    	else if(localMonth == 3)
	    		monthName = "March";
	    	else if (localMonth == 4)
	    		monthName = "April";
	    	else if(localMonth == 5)
	    		monthName = "May";
	    	else if(localMonth == 6)
	    		monthName = "June";
	    	else if(localMonth == 7)
	    		monthName = "July";
	    	else if(localMonth == 8)
	    		monthName = "August";
	    	else if(localMonth == 9)
	    		monthName = "September";
	    	else if(localMonth == 10)
	    		monthName = "October";
	    	else if (localMonth == 11)
	    		monthName = "November";
	    	else if (localMonth == 12)
	    		monthName = "December";
	    	
	    	// check which button is clicked
	    	if (btnClick == 1)
	    	{
	    		//for api
	    		apiFromDate = mYear+monthString+dayString;
	    		//for textview
	    		frmDateDisp.setText(
	    	            new StringBuilder()
	    	                    // Month is 0 based so add 1
	    	                    .append(monthName).append(" ")
	    	                    .append(dayString).append(", ")
	    	                    .append(mYear).append(" "));
	    	}
	    	
	    	else if(btnClick == 2)
	    	{
	    		//for api
	    		apiToDate = mYear+monthString+dayString;
	    		// for textview
	    		toDateDisplay.setText(
			            new StringBuilder()
			                    // Month is 0 based so add 1
			                    .append(monthName).append(" ")
			                    .append(dayString).append(", ")
			                    .append(mYear).append(" "));
	    	}
	    	
	    	else {
	    		String frmDateD = frmDateDisp.getText().toString();
	    		String toDateD  = toDateDisplay.getText().toString();
	    		java.util.Date thedate=null;
	    		java.util.Date test = null;
	    		try {
					thedate = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).parse(toDateD);
				} catch (java.text.ParseException e1) {
					e1.printStackTrace();
				}
	    			    			
	    			System.out.println("this is to date formating:   "+thedate);
	    	}
	    }
	    
	    
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	    	    new DatePickerDialog.OnDateSetListener() {
	    	
	    	@Override
	    	        public void onDateSet(DatePicker view, int year, 
	    	                              int monthOfYear, int dayOfMonth) {
	    	            mYear = year;
	    	            mMonth = monthOfYear;
	    	            mDay = dayOfMonth;
	    	            updateDisplay();
	    	        }
	    	    };
	    	    
	    	    @Override
	    	    protected Dialog onCreateDialog(int id) {
	    	        switch (id) {
	    	        case DATE_DIALOG_ID:
	    	        	/*
	    	            return new DatePickerDialog(this,
	    	                        mDateSetListener,
	    	                        mYear, mMonth, mDay);
	    	                        */
	    	        	DatePickerDialog datePickerDialog = this.customDatePicker();
	    	        	return datePickerDialog;
	    	        }
	    	        return null;
	    	    }
	    	    
	    	    private DatePickerDialog customDatePicker() {
	    	    	  DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListener,
	    	    	    mYear, mMonth, mDay);
	    	    	  try {

	    	    	   Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
	    	    	   for (Field datePickerDialogField : datePickerDialogFields) {
	    	    	    if (datePickerDialogField.getName().equals("mDatePicker")) {
	    	    	     datePickerDialogField.setAccessible(true);
	    	    	     DatePicker datePicker = (DatePicker) datePickerDialogField
	    	    	       .get(dpd);
	    	    	     Field datePickerFields[] = datePickerDialogField.getType()
	    	    	       .getDeclaredFields();
	    	    	     for (Field datePickerField : datePickerFields) {
	    	    	      if ("mDayPicker".equals(datePickerField.getName())
	    	    	        || "mDaySpinner".equals(datePickerField
	    	    	          .getName())) {
	    	    	       datePickerField.setAccessible(true);
	    	    	       Object dayPicker = new Object();
	    	    	       dayPicker = datePickerField.get(datePicker);
	    	    	       ((View) dayPicker).setVisibility(View.GONE);
	    	    	      }
	    	    	     }
	    	    	    }

	    	    	   }
	    	    	  } catch (Exception ex) {
	    	    	  }
	    	    	  return dpd;
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
