package com.example.androidhive;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.HashMap;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import com.example.androidhive.library.DBAdapter;
import com.example.androidhive.library.UrlConnection;
import com.example.androidhive.library.UserFunctions;

import android.app.ListActivity;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.androidhive.library.XMLParser;



 
public class DashboardActivity extends ListActivity {
	//*********************************************************************
	// All static variables
	UrlConnection urlCon = new UrlConnection();
    //for accounts
    String MAcctXML = urlCon.getMACCTS_XML();    
    // using api call url
    String apiCall = urlCon.getAPI();
    //get the api asof date for call
    String apiAsOf;
	// XML node keys
	static final String KEY_ACCOUNTINFO = "ACCOUNTINFO"; // parent node
	static final String KEY_ACCOUNTID = "ACCOUNTID";
	static final String KEY_ACCOUNTNAME = "ACCOUNTNAME";
	static final String KEY_MKTVAL = "MKTVAL";
	static final String KEY_ASOFDATE = "ASOFDATE";
	static final String KEY_ICPDATE = "ICPDATE";
	static final String KEY_DAILYICP = "DAILYICP";
	static final String KEY_MTD = "MTD"; // parent node
	static final String KEY_YTD = "YTD"; // parent node
	static final String KEY_SIPROW = "SIPROW"; // parent node
	static final String KEY_LABEL = "LABEL";
	static final String KEY_VALUE = "VALUE";
	
	//*******graph tags********************
	static final String KEY_PCCATEGORY = "PCCATEGORY"; // parent node
	static final String KEY_SORINAME   = "NAME"; // FRPSI1.SORINAME
	static final String KEY_CATEGORY   = "CATEGORY"; // FRPSI1.SORI
	static final String KEY_TYPE       = "TYPE";
	static final String KEY_ALLOCATION = "ALLOCATION";
	static final String KEY_COLOR      = "COLOR";
	
	//*******graph tags********************
		static int i=0;
		int p[] = new int[6];
		int c[] = new int[6];
		String st[] = new String[5];
		String key_secName[] = new String[9];
		String key_mktval[] = new String[9];
		String key_color[] = new String[19];
		String key_lcolor[] = new String[19];
		DecimalFormat df = new DecimalFormat("#.00");
		int pi[] = new int[c.length];
		//convert tot string the integer
		  String percentVal[] = new String[c.length];
		int []key_colors1 = new int[9];
		int []key_colors2 = new int[9];
		int []key_colors3 = new int[9];
		
		int []colors = new int[9];
		int totalSector =0;
		
// for line graph
		//*******graph tags********************
		static final String KEY_LINECHART  = "LINECHART"; // parent node1
		static final String KEY_LCSECTOR   = "LCSECTOR"; // Parent node2
		static final String KEY_BENCHMARK1 = "BENCHMARK1"; // Benchmark parent
		static final String KEY_BENCHMARK2 = "BENCHMARK2"; // Benchmark parent
		static final String KEY_LCRETURNS  = "LCRETURNS"; // Parent 3
		static final String KEY_LCRETURN   = "LCRETURN"; // Parent 4
		static final String KEY_DATE       = "DATE"; 

		
//for sector		
String key_sdates[]  = new String[40];
String key_svalues[] = new String[40];
//for index1
String key_i1dates[]  = new String[40];
String key_i1values[] = new String[40];
// for index2
String key_i2dates[]  = new String[40];
String key_i2values[] = new String[40];

String key_lsecName[] = new String[9];
String key_benchName[] = new String[9];
String key_benchName2[] = new String[9];
String key_scolor[] = new String[19];
String key_i1color[] = new String[19];
String key_i2color[] = new String[19];

String key_acctid[] = new String[1000];
String key_acctnames[] = new String[1000];

int []key_scolors1 = new int[9];
int []key_scolors2 = new int[9];
int []key_scolors3 = new int[9];

int []key_i1colors1 = new int[9];
int []key_i1colors2 = new int[9];
int []key_i1colors3 = new int[9];

int []key_i2colors1 = new int[9];
int []key_i2colors2 = new int[9];
int []key_i2colors3 = new int[9];

String selectedAcct;
String acctName = "";


// setup the database
DBAdapter db = new DBAdapter(this);
private GraphicalView lChart;	
	//*********************************************************************
TableLayout htableLayout;

    UserFunctions userFunctions;
    Button btnLogout, btnPie;
    ForNetworkConnectiontStric stricMode;
    

    /** Called when the activity is first created. */
    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;
    SearchView searchView;
    CheckBox chkmpids;
    ListView acctListView;
    CheckBox chkMatchPartialIDs;    
    
    String topholdingActivity, performanceActivity;
    
    // get the userid,password and site key from login acitivity
    String userid, pass, sitek;
    
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
        		userFunctions.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                
                // delete all stored data
                deleteStoredData();
                // Closing dashboard screen
                
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.dashboard);

            // check if the button is back button then load data for that acctoun
           try{
        	   Bundle extras = getIntent().getExtras();
        	// get selected account
        	   selectedAcct = extras.getString("ACCT");
        	   sitek  = extras.getString("CLIENT");
        	   apiAsOf=extras.getString("ASOF");
        	   //---------
        	   userid = extras.getString("IBIC_user").toUpperCase();
           	   pass   = extras.getString("IBIC_pass");
           	   // Passing from another activity
           	   topholdingActivity = extras.getString("TOPHOLD");
           	  performanceActivity = extras.getString("PERFORMER");
           	   //System.out.println("from login activity: userid=>"+userid+" password=>"+pass+" sitekey=>"+sitek);
        	   // find the show allocation summary and enable it
			Button btnSAS = (Button) findViewById(R.id.btnShowSectors);
			btnSAS.setEnabled(true);
			 //-----find the pie graph and set background color
			 btnPie = (Button) findViewById(R.id.btnShowPieG);
			 btnPie.setEnabled(true);
			 //---find the line graph button and enable
			 Button btnLineG = (Button) findViewById(R.id.btnShowLineG);
			 btnLineG.setEnabled(true);
			 //---find the Portfolio Statement button and enable
			 Button btnPortStat = (Button) findViewById(R.id.btnPortState);
			 btnPortStat.setEnabled(true);
			 
			 if(topholdingActivity != null){
				// account information
				  accountInfo();
				  //create the MTD List information
				 	mtdListInf();
				 	// create the YTD infor
				  	ytdListInfo();
				// display the pie graph
				   displayGraph();
			 }
			 else {/*skip and do nothing */}
			 
           } catch (NullPointerException np){
        	   np.printStackTrace();
           }
            
            
            userFunctions = new UserFunctions();
          //--for solving the networkOnMainThread
            stricMode = new ForNetworkConnectiontStric();
            stricMode.onStricMode();
    //.......................................
          //hide the action bar
            final ActionBar actionBar = getActionBar();
            actionBar.hide();
            /////////////////for searchview ++++++++++++++++++++++++++++++++++++++++++
            searchAcct();
           /////////////////for searchview ++++++++++++++++++++++++++++++++++++++++++
         
            // check if the user is clicked on logout button
            btnLogout = (Button) findViewById(R.id.btnLogout);
             
            btnLogout.setOnClickListener(new View.OnClickListener() {
                 
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                	try{
                		userFunctions.logoutUser(getApplicationContext());
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        
                        // delete all stored data
                        deleteStoredData();
                        // Closing dashboard screen
                        
                        finish();
                	} catch(NullPointerException np){
                		np.printStackTrace();
                	}
                    
                }
            });
            
            //---the following is for datepicker---------------------
            mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
            mPickDate = (Button) findViewById(R.id.pickDate);

            mPickDate.setOnClickListener(new View.OnClickListener() {
            	
            	@Override
                public void onClick(View v) {
                	try{
                		showDialog(DATE_DIALOG_ID);
                	} catch(NullPointerException np){
                		np.printStackTrace();
                	} 
                }
            });//end of the datepicker listener

            //System.out.println("holdingss and performanfe pass: "+ topholdingActivity + " perf: "+performanceActivity);
            if(topholdingActivity == null){
            	final Calendar c = Calendar.getInstance();
                
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                updateDisplay();
            }
            
           else if(performanceActivity == null){
            	final Calendar c = Calendar.getInstance();
                
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                updateDisplay();
            }
            else {
            	mYear  = Integer.parseInt(apiAsOf.substring(0,4));
            	mMonth = Integer.parseInt(apiAsOf.substring(4,6))-1;
            	mDay   = Integer.parseInt(apiAsOf.substring(6));
            	updateDisplay(); 
            	System.out.println("the api date: "+"Y: "+mYear+" M: "+mMonth+" D: "+mDay);
            }
            //--------------------------------------------------------

            chkMatchPartialIDs = (CheckBox) findViewById(R.id.chkmpids);
            chkMatchPartialIDs.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            	 @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                    	//--- do something
                    	loadAccounts();
                    }else{
                    	acctListView.setVisibility(acctListView.INVISIBLE);
                    }
                }
            });
      //-----***************for the tab settings *************************************
            createMtdYtdTab();
     //*************************end of the tab settings ************************************            
         // show the accounts
         //insertAcct();    
    }// end of the onCreate method
    

    // when the "Match Patial IDs" checked do the action
    public void loadAccounts(){
    	try{
    		XMLParser parserA = new XMLParser();    	    
    		String xmlA = parserA.getXmlFromUrl(MAcctXML+this.getAPIuri()); // getting XML    	    
    		Document docA = parserA.getDomElement(xmlA); // getting DOM element  
    		//*************** Account information ***********************************      
    		  NodeList nlA = docA.getElementsByTagName("ACCOUNT");
    		  System.out.println("the API URI: "+MAcctXML+this.getAPIuri()+" nlA: "+nlA.getLength());
    		 // looping through all item nodes <item>
    		    if(nlA != null && nlA.getLength() >0){
    		     for (int s = 0; s < nlA.getLength(); s++) {
    			  //
    			  Node acctid = nlA.item(s);
    			  if(acctid.getNodeType() == Node.ELEMENT_NODE){
    				  Element firstACCT = (Element)acctid;
    				  
    				  //------
    				  NodeList firstACCTList = firstACCT.getElementsByTagName("ACCOUNTID");
    				  Element firstACCTElement = (Element)firstACCTList.item(0);
    				  
    				  NodeList textACCTList = firstACCTElement.getChildNodes();
    				  key_acctid[s] = ((Node)textACCTList.item(0)).getNodeValue().trim();
    				  System.out.println("Account id : " + key_acctid[s]);
    				  
    				  //------
    				  NodeList acctnameList = firstACCT.getElementsByTagName("ACCOUNTNAME");
    				  Element acctnameElement = (Element)acctnameList.item(0);
    				  
    				  NodeList textAcctnameList = acctnameElement.getChildNodes();
    				  key_acctnames[s] = ((Node)textAcctnameList.item(0)).getNodeValue().trim();
    				  
    				  //System.out.println("Account name : " + key_acctnames[s]);  
    				  

    			  } // end of the if statement
    		     } // loop
                } //if statement
	     	   
	     	// Adding menuItems to ListView

  			     ArrayList<String> accounts = new ArrayList<String>();
  			     for(int act=0; act <key_acctid.length; act++){
  			    	if(key_acctid[act] != null){
  			    		accounts.add(key_acctid[act] + " " +key_acctnames[act]);
  	  				    acctListView.setVisibility(View.VISIBLE);
  	  				    acctListView.setAdapter(new ArrayAdapter<String>(DashboardActivity.this,android.R.layout.simple_list_item_1, accounts));
  	  				    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
  			    	}
  			    	else { /*--do nothing */}
  			     }
  			     
  			// selecting single ListView item
     	    	acctListView.setOnItemClickListener(new OnItemClickListener() {

     	    		@Override
     	    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     	    			//................................
     	    			String aidaname = acctListView.getItemAtPosition(position).toString().trim();
     	    			String splittext[] = aidaname.split(" ");
     	    			//System.out.println("the selected item: "+ aidaname);
     	    			selectedAcct = splittext[0];
     	    		    acctName = null;
     	    		    if(acctName == null)
     	    		    	acctName = "";
     	    		    
     	    			System.out.println("the acctid is: "+splittext[0]);
     	    			for(int x =1; x<splittext.length; x++){
     	    				acctName += " "+splittext[x].toString();
     	    			}
     	    			
     	    			//callAPI();
     	    			// account information
     		            accountInfo();
     		            //create the MTD List information
     	              	mtdListInf();
     	              	// create the YTD infor
     	               	ytdListInfo();
     	    			// display the pie graph
     	                displayGraph(); 
     	                
     	                //hide the account list
     	               acctListView.setVisibility(acctListView.INVISIBLE);
     	               // uncheck the checkbox
     	              chkmpids.toggle();
     	          // find the show allocation summary and enable it
     	 	    	Button btnSAS = (Button) findViewById(R.id.btnShowSectors);
     	 	    	btnSAS.setEnabled(true);
     	             //-----find the pie graph and set background color
     	             btnPie = (Button) findViewById(R.id.btnShowPieG);
     	             btnPie.setEnabled(true);
     	             //---find the line graph button and enable
     	             Button btnLineG = (Button) findViewById(R.id.btnShowLineG);
     	             btnLineG.setEnabled(true);
     	             //---find the Portfolio Statement button and enable
     	             Button btnPortStat = (Button) findViewById(R.id.btnPortState);
     	             btnPortStat.setEnabled(true);
     	    		}
     	    		});
				  
    	} catch (NullPointerException np){
    		np.printStackTrace();
    	}
    }
    
    // create the ytd and mtd tabs
    public void createMtdYtdTab(){
    	try{
    		TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
            tabHost.setup();
         
            TabSpec spectxt =tabHost.newTabSpec("Month To Date");
            spectxt.setContent(R.id.tabtext);
            spectxt.setIndicator("Summary");
            
            TabSpec spec1=tabHost.newTabSpec("Month To Date");
            spec1.setContent(R.id.tab1);
            spec1.setIndicator("MTD");
         
         
            TabSpec spec2=tabHost.newTabSpec("Year To Date");
            spec2.setIndicator("YTD");
            spec2.setContent(R.id.tab2);
            
            //tabHost.addTab(spectxt);           
            tabHost.addTab(spec1);
            tabHost.addTab(spec2);
    	} catch(NullPointerException np){
    		np.printStackTrace();
    	}
    }
    
  //create the account info
    public void accountInfo(){
    	try {              
   	      ArrayList<HashMap<String, String>> menuItemsA = new ArrayList<HashMap<String, String>>();
  		  XMLParser parserA = new XMLParser();    	    
  		  String xmlA = parserA.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML 
  		  Document docA = parserA.getDomElement(xmlA); // getting DOM element  
            //*************** Account information *********************************** 
       	   NodeList nlA = docA.getElementsByTagName(KEY_ACCOUNTINFO);
       	   
       	  // looping through all item nodes <item>
       	   for (int iA = 0; iA < nlA.getLength(); iA++) {
       	    // creating new HashMap
       	    HashMap<String, String> mapA = new HashMap<String, String>();
       	    Element eA = (Element) nlA.item(iA);
       	    // adding each child node to HashMap key => value
       	    mapA.put(KEY_ACCOUNTNAME, parserA.getValue(eA, KEY_ACCOUNTNAME));
       	    mapA.put(KEY_ACCOUNTID, parserA.getValue(eA, KEY_ACCOUNTID));
       	    mapA.put(KEY_MKTVAL, parserA.getValue(eA, KEY_MKTVAL));
       	    mapA.put(KEY_ASOFDATE, parserA.getValue(eA, KEY_ASOFDATE));
       	    mapA.put(KEY_ICPDATE, parserA.getValue(eA, KEY_ICPDATE));
       	    mapA.put(KEY_DAILYICP, parserA.getValue(eA, KEY_DAILYICP));
       	    // adding HashList to ArrayList
       	    menuItemsA.add(mapA);   	  
       	   } 
       	   // Adding menuItems to ListView
       	   ListView lst = (ListView) findViewById(R.id.account_list);
       	   ListAdapter adapterA = new SimpleAdapter(this, menuItemsA,
       	   R.layout.account,
       	   new String[] { KEY_ACCOUNTNAME , KEY_ACCOUNTID }, new int[]
       	   {
       	   R.id.name, R.id.account });
       	   lst.setAdapter(adapterA);
    	} catch (NullPointerException np){
    		np.printStackTrace();
    	}
    }
    
    
    //--
    public String getAPIuri(){
    	String apiParam = selectedAcct+"&IBIC_user="+userid+"&IBIC_pass="+pass+"&CLIENT=DV"+sitek+"63&ASOFDATE="+apiAsOf;
    	return apiParam;
    }
    // create the mtd list information
    public void mtdListInf(){
    	try{
    		ArrayList<HashMap<String, String>> menuItemsM = new ArrayList<HashMap<String, String>>();
       	    XMLParser parserM = new XMLParser();    	    
       	    String xmlM = parserM.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML   
       	    Document docM = parserM.getDomElement(xmlM); // getting DOM element
        
       	    NodeList nlMTDM = docM.getElementsByTagName(KEY_MTD);
       	    
       	      if(nlMTDM != null && nlMTDM.getLength() > 0){
       	    	  //--loop for mtd tag
       	    	  for(int k=0; k <nlMTDM.getLength(); k++){
       	    	// looping through all item nodes <item>
       	    		  Node nodeM =nlMTDM.item(k);
       	    		  if(nodeM.getNodeType() == Node.ELEMENT_NODE){
       	    			  Element ePM = (Element) nodeM;
       	    			  NodeList nlM = ePM.getElementsByTagName(KEY_SIPROW);  
       	    		  
       	    	for (int i = 0; i < nlM.getLength(); i++) {
       	    	// creating new HashMap
       	    	HashMap<String, String> mapM = new HashMap<String, String>();
       	    	Element eM = (Element) nlM.item(i);
       	    	// adding each child node to HashMap key => value
       	    	mapM.put(KEY_LABEL, parserM.getValue(eM, KEY_LABEL));
       	    	mapM.put(KEY_VALUE, parserM.getValue(eM, KEY_VALUE).trim());
       	    	// adding HashList to ArrayList
       	    	menuItemsM.add(mapM);
       	    	}
       	       }
       	       }
       	      }
       	       
       	   // Adding menuItems to ListView
       	   ListView lst_mtd = (ListView) findViewById(R.id.mtd_list);
       	   ListAdapter adapterM = new SimpleAdapter(this, menuItemsM,
       	   R.layout.list_item,
       	   new String[] { KEY_LABEL, KEY_VALUE }, new int[]
       	   {
       	   R.id.soriname, R.id.mktval });
       	  //lst_mtd.setAdapter(adapterM);
       	lst_mtd.setAdapter(adapterM);
    	} catch (NullPointerException np){
    		np.printStackTrace();
    	}
    }
    
    // create the ytd info
    public void ytdListInfo(){
    	try{ 
    	        ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
    			  XMLParser parser = new XMLParser();    	    
    			  String xml = parser.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
    			  Document doc = parser.getDomElement(xml); // getting DOM element
    			  
    	     	    NodeList nlMTD = doc.getElementsByTagName(KEY_YTD);
    	     	    
    	     	      if(nlMTD != null && nlMTD.getLength() > 0){
    	     	    	  //--loop for mtd tag
    	     	    	  for(int k=0; k <nlMTD.getLength(); k++){
    	     	    	// looping through all item nodes <item>
    	     	    		  Node node =nlMTD.item(k);
    	     	    		  if(node.getNodeType() == Node.ELEMENT_NODE){
    	     	    			  Element eP = (Element) node;
    	     	    			  NodeList nl = eP.getElementsByTagName(KEY_SIPROW);  
    	     	    		  
    	     	    	for (int i = 0; i < nl.getLength(); i++) {
    	     	    	// creating new HashMap
    	     	    	HashMap<String, String> map = new HashMap<String, String>();
    	     	    	Element e = (Element) nl.item(i);     	    	
    	     	    	// adding each child node to HashMap key => value
    	     	    	map.put(KEY_LABEL, parser.getValue(e, KEY_LABEL));
    	     	    	map.put(KEY_VALUE, parser.getValue(e, KEY_VALUE).trim());
    	     	    	// adding HashList to ArrayList
    	     	    	menuItems.add(map);
    	     	    	}
    	     	       }
    	     	       }
    	     	      }  	     
    	     	   // Adding menuItems to ListView
    	     	   ListView lst_ytd = (ListView) findViewById(R.id.mtd_list);
    	     	   ListAdapter adapter = new SimpleAdapter(this, menuItems,
    	     	   R.layout.list_item,
    	     	   new String[] { KEY_LABEL, KEY_VALUE }, new int[]
    	     	   {
    	     	   R.id.soriname, R.id.mktval });
    	     	  setListAdapter(adapter);
    			    	   
    	 //**************************************************************************************     
    	     	  //lstG.setAdapter(adapterG);  	
    		 } catch (NullPointerException npe){
    			 //String  arr[]={"Please check your internet connection"};
    			 //ListView lst = (ListView) findViewById(R.id.account_list);
    		    // ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);
    		    // lst.setAdapter(adapter);
    		 }
    }
    
    //update the clander display
    private void updateDisplay() {
    	int localMonth = (mMonth + 1);
    	String monthString = localMonth < 10 ? "0" + localMonth : Integer
    			.toString(localMonth);
    	String dayString = mDay < 10 ? "0" + mDay : Integer
    			.toString(mDay);
    	// for api call
    		apiAsOf = mYear+monthString+"01";
    	System.out.println("top holdings activity: "+topholdingActivity);
    	//set the screaninig date
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append("As of: ")
                    .append(monthString).append("/")
                    //.append(dayString).append("/")
                    .append(mYear).append(" "));
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
    	    
    	//Show the topholdings data
    	    public void onTopHoldingsClick(View view){
    	    	// TODO Auto-generated method stub
    	        Intent i = new Intent(getApplicationContext(),TopHoldingsActivity.class);
    	      //create a bundle object
    			Bundle b = new Bundle();
    			b.putString("ACCT", selectedAcct);
    			b.putString("CLIENT", sitek);
    			b.putString("ASOF", apiAsOf);
    			b.putString("IBIC_user", userid);
    			b.putString("IBIC_pass", pass);
    			
    			i.putExtras(b);
    	        startActivity(i);
    	    }
    	    
    	    //Show the portfoli statement
    	    public void showPerformance(View view) {
    	    	Intent ip = new Intent(getApplicationContext(),PerformanceActivity.class);
    	    	//create a bundle object
    			Bundle b = new Bundle();
    			b.putString("ACCT", selectedAcct);
    			b.putString("CLIENT", sitek);
    			b.putString("ASOF", apiAsOf);
    			b.putString("IBIC_user", userid);
    			b.putString("IBIC_pass", pass);
    			
    			ip.putExtras(b);
    	        startActivity(ip);
    	    }
    	    
    	    //create pie graph
    	    public void displayGraph(){
    	    	try {
    	    	//--------------------the graph soriname and market value----------------------------   
    	    		XMLParser parserGp = new XMLParser();    	    
    	    		String xmlGp = parserGp.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
    	    		Document docGp = parserGp.getDomElement(xmlGp); // getting DOM element  
    	    		//*************** Account information *********************************** 
    	    		
    	    		  NodeList nlp = docGp.getElementsByTagName(KEY_PCCATEGORY);
    	    		 // looping through all item nodes <item>
    	    		   totalSector = nlp.getLength();
    	    		  //System.out.println("Total no of Sectors: " + totalSector);
    	    		  for (int s = 0; s < nlp.getLength(); s++) {
    	    			  //
    	    			  Node soriname = nlp.item(s);
    	    			  if(soriname.getNodeType() == Node.ELEMENT_NODE){
    	    				  Element firstSector = (Element)soriname;
    	    				  
    	    				  //------
    	    				  NodeList firstSectorList = firstSector.getElementsByTagName(KEY_SORINAME);
    	    				  Element firstSectorElement = (Element)firstSectorList.item(0);
    	    				  
    	    				  NodeList textSectorList = firstSectorElement.getChildNodes();
    	    				  key_secName[s] = ((Node)textSectorList.item(0)).getNodeValue().trim();
    	    				  //System.out.println("Soriname : " + key_secName[s]);
    	    				  
    	    				  //-------
    	    				  NodeList mktvalList = firstSector.getElementsByTagName(KEY_MKTVAL);
    	    				  Element mktvalElement = (Element)mktvalList.item(0);
    	    				  
    	    				  NodeList textMKTVAList = mktvalElement.getChildNodes();
    	    				  key_mktval[s] = ((Node)textMKTVAList.item(0)).getNodeValue().trim();
    	    				  //System.out.println("Market Value : " + key_mktval[s]);
    	    				  
    	    				  //------
    	    				  NodeList colorList = firstSector.getElementsByTagName(KEY_COLOR);
    	    				  Element colorElement = (Element)colorList.item(0);
    	    				  
    	    				  NodeList textColorList = colorElement.getChildNodes();
    	    				  key_color[s] = ((Node)textColorList.item(0)).getNodeValue().trim();
    	    				  
    	    				  //System.out.println("Color is : " + key_color[s]);
    	    				  
    	    				  String[] s1 = key_color[s].split(",");
    	    				  for(int x=0; x<s1.length; x++){
    	    					  //System.out.println("color.: " +s1[x]);
    	    					  //add to the system
    	    					  key_colors1[s] = Integer.parseInt(s1[0]);
    	    					  key_colors2[s] = Integer.parseInt(s1[1]); 
    	    					  key_colors3[s] = Integer.parseInt(s1[2]);
    	    					  //System.out.println("Color.rgb: " + key_colors1[s]+", "+ key_colors2[s]+" , "+key_colors3[s]);
    	    				  }
    	    				  
    	    				  //----
    	    				   st[s] = ((Node)textMKTVAList.item(0)).getNodeValue().trim();
    	    				   st[s] = st[s].replaceAll(",", "");
    	    				   
    	    				   c[s] += Integer.parseInt(st[s]);
    	    			  } //end of if clause
    	    		  }   //end of for loop with s var
    	    		  Double tot = 0.0;
    	    		  
    	    		  for(int i =0; i<st.length; i++){
    	    			  //System.out.println("f1 : " +st[i]);
    	    			  tot = Double.parseDouble(df.format(tot + c[i]));
    	    			  //System.out.println("tot : " +tot);
    	    		  }
    	    		  

    	    		  // get the percentage of values
    	    		  Double perce[] = new Double[c.length];
    	    		  for(int i=0; i<c.length; i++){
    	    			  perce[i] = Double.parseDouble(df.format((c[i]/tot)*100));
    	    			  pi[i] = (int) Math.round(perce[i]);
    	    			  percentVal[i] = Integer.toString(pi[i])+"%";
    	    			  //System.out.println("percentge : "+ perce[i]);
    	    			  //System.out.println("the integer: "+ pi[i]);
    	    		  }
    	    		  
    	    			  
    	    		} catch (NullPointerException npe){
    	    			 
    	    			 npe.printStackTrace();
    	    		  } catch (Throwable t){
    	    			  t.printStackTrace();
    	    		  }

    	    		//--- create the graph
    	    		 CategorySeries series = new CategorySeries("title"); // adding series to charts. //collect 3 value in array. therefore add three series.
    	    		 for(int g=0; g<totalSector; g++){    
    	    		 series.add(key_secName[g]+" "+percentVal[g],pi[g]);
    	    		 }  
    	    		 
    	    		// set style for series
    	    		     DefaultRenderer renderer = new DefaultRenderer();
    	    		     for(int c=0; c<totalSector; c++){
    	    		         SimpleSeriesRenderer r = new SimpleSeriesRenderer();
    	    		         r.setColor(Color.rgb(key_colors1[c], key_colors2[c], key_colors3[c]));
    	    		         r.setDisplayBoundingPoints(true);
    	    		         r.setShowLegendItem(false);
    	    		         r.setDisplayChartValues(true);
    	    		         renderer.addSeriesRenderer(r);
    	    		     }
    	    		     renderer.isInScroll();
    	    		     renderer.setZoomButtonsVisible(false);   //set zoom button in Graph
    	    		     renderer.setApplyBackgroundColor(true);
    	    		     renderer.setBackgroundColor(Color.WHITE); //set background color
    	    		     //renderer.setChartTitleTextSize((float) 30);
    	    		     renderer.setShowLabels(true); 
    	    		     renderer.setLabelsTextSize(13);
    	    		     renderer.setLabelsColor(Color.BLACK);
    	    		     //renderer.setLegendTextSize(25);
    	    		     renderer.setDisplayValues(false);
    	    		     // Add chart to the linerlayout
    	    		     LinearLayout lnp = (LinearLayout) findViewById(R.id.pie_chart);
    	    		   //first clear the content of layout and then add
    	    		    lnp.removeAllViews();     	    		     
    	    		     GraphicalView myChart = ChartFactory.getPieChartView(this, series, renderer);    	    		     
    	    		     lnp.addView(myChart);
    	    		     
    	    		     //set a click listener   	    		     
    	    }
    	    
    	    // show the sectors when the show sectos is clicked
    	    public void showSectors(View view){
    	    	//--------------------the graph soriname and market value---------------------------- 
    	    	try{
    	    		// creating new HashMap
        	    	ArrayList<HashMap<String, String>> menuItemsG = new ArrayList<HashMap<String, String>>();
     	     	     HashMap<String, String> mapH = new HashMap<String, String>();
     	     	     mapH.put(KEY_SORINAME, "     Sector");
     	     	     mapH.put(KEY_ALLOCATION, "Allocation");
     	     	     mapH.put(KEY_MKTVAL, "Market Value");
     	     	     
     	     	      menuItemsG.add(mapH);

     	     	      //get the data 
        			  XMLParser parserG = new XMLParser();    	    
        			  String xmlG = parserG.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
        			  Document docG = parserG.getDomElement(xmlG); // getting DOM element  
        	          //*************** Allocation ***********************************    	
        		 
        	     	   NodeList nlG = docG.getElementsByTagName(KEY_PCCATEGORY);
        	     	  // looping through all item nodes <item>
        	     	   for (int iG = 0; iG < nlG.getLength(); iG++) {
        	     	    // creating new HashMap
        	     	    HashMap<String, String> mapG = new HashMap<String, String>();
        	     	    Element eG = (Element) nlG.item(iG);
        	     	    // adding each child node to HashMap key => value
        	     	    mapG.put(KEY_SORINAME, parserG.getValue(eG, KEY_SORINAME));
        	     	    mapG.put(KEY_CATEGORY, parserG.getValue(eG, KEY_CATEGORY));
        	     	    mapG.put(KEY_TYPE, parserG.getValue(eG, KEY_TYPE));
        	     	    mapG.put(KEY_MKTVAL, parserG.getValue(eG, KEY_MKTVAL).trim());
        	     	    mapG.put(KEY_ALLOCATION, parserG.getValue(eG, KEY_ALLOCATION));
        	     	    mapG.put(KEY_COLOR, parserG.getValue(eG, KEY_COLOR));
        	     	    // adding HashList to ArrayList
        	     	    menuItemsG.add(mapG);
        	     	  }
        	     	   // Adding menuItems to ListView
        	     	   //ListView lstG = (ListView) findViewById(R.id.grap_h_list);
        	     	  ListAdapter adapterG = new SimpleAdapter(this, menuItemsG,
        	     	   R.layout.graph_list,
        	     	   new String[] { KEY_SORINAME , KEY_ALLOCATION, KEY_MKTVAL }, new int[]
        	     	   {
        	     	   R.id.soriname, R.id.alloc, R.id.mktval });
        	     	  //lstG.setAdapter(adapterG);
        	     	  
        	     	  // show the sector informatin in dialog box
        	     	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	 		builder.setTitle("Allocation Summary Report");
        	 		//builder.setMessage(messages);
        	 		builder.setAdapter(adapterG, null);
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
        			
    	    	} catch (NullPointerException np){
    	    		// show the sector informatin in dialog box
    	    		String messages = "You lost your connection or the remote site is goese down";
        	     	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	 		builder.setTitle("Connection Problem!");
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
    	    	}
   			 
    	     }// end of the pie graph method
  
    	    // crate the line graph
    	    public void createLineGraph(){
    	    	//.......................................
    	    	try {
    	    	//--------------------the line chart value and date sector one----------------------------   
    	    	XMLParser parserLG = new XMLParser();    	    
    	    	String xmlLG = parserLG.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
    	    	Document docLG = parserLG.getDomElement(xmlLG); // getting DOM element  
    	    	//*************** Account information *********************************** 

    	    	  NodeList nlL = docLG.getElementsByTagName(KEY_LCSECTOR);
    	    	 // looping through all item nodes <item>
    	    	    //if(nl != null && nl.getLength() >0){
    	    	     for (int s = 0; s < nlL.getLength(); s++) {
    	    		  //
    	    		  Node soriname = nlL.item(s);
    	    		  if(soriname.getNodeType() == Node.ELEMENT_NODE){
    	    			  Element firstSector = (Element)soriname;
    	    			  
    	    			  //------
    	    			  NodeList firstSectorList = firstSector.getElementsByTagName(KEY_SORINAME);
    	    			  Element firstSectorElement = (Element)firstSectorList.item(0);
    	    			  
    	    			  NodeList textSectorList = firstSectorElement.getChildNodes();
    	    			  key_lsecName[s] = ((Node)textSectorList.item(0)).getNodeValue().trim();
    	    			 // System.out.println("Soriname : " + key_lsecName[s]);
    	    			  
    	    			  //------
    	    			  NodeList colorList = firstSector.getElementsByTagName(KEY_COLOR);
    	    			  Element colorElement = (Element)colorList.item(0);
    	    			  
    	    			  NodeList textColorList = colorElement.getChildNodes();
    	    			  key_scolor[s] = ((Node)textColorList.item(0)).getNodeValue().trim();
    	    			  
    	    			  //System.out.println("Color is : " + key_scolor[s]);
    	    			  
    	    			  String[] s1 = key_scolor[s].split(",");
    	    			  for(int x=0; x<s1.length; x++){
    	    				  //System.out.println("color.: " +s1[x]);
    	    				  //add to the system
    	    				  key_scolors1[s] = Integer.parseInt(s1[0]);
    	    				  key_scolors2[s] = Integer.parseInt(s1[1]); 
    	    				  key_scolors3[s] = Integer.parseInt(s1[2]);
    	    				  //System.out.println("Color.rgb: " + key_colors1[s]+", "+ key_colors2[s]+" , "+key_colors3[s]);
    	    			  }

    	    	////////=======Sector date and value :
    	    	  
    	    			  // Get for the childe parent
    	    			  NodeList nlr = docLG.getElementsByTagName(KEY_LCRETURNS);
    	    			  // looping through all item nodes <item>
    	    			    totalSector = nlr.getLength();
    	    			   //System.out.println("Total no of Sectors: " + totalSector);
    	    			  if(nlL != null && nlL.getLength() > 0){
    	    			   for (int r = 0; r < nlL.getLength(); r++) {
    	    			 	  //
    	    			 	  Node dates = nlr.item(r);
    	    			 	  if(dates.getNodeType() == Node.ELEMENT_NODE){
    	    			 		  Element firstDatep = (Element)dates;
    	    			 		  
    	    			 		  NodeList nlrt = firstDatep.getElementsByTagName(KEY_LCRETURN);
    	    			 		  for(int rt=0; rt<nlrt.getLength(); rt++){
    	    			 			  Element firstDate = (Element) nlrt.item(rt);
    	    			 		  //------
    	    			 		  NodeList firstDateList = firstDate.getElementsByTagName(KEY_DATE);
    	    			 		  Element firstDateElement = (Element)firstDateList.item(0);
    	    			 		  
    	    			 		  NodeList textDateList = firstDateElement.getChildNodes();
    	    			 		  key_sdates[r] = ((Node)textDateList.item(0)).getNodeValue().trim();
    	    			 		  //System.out.println("Dates : " + key_sdates[r]);
    	    			 		  
    	    			 		  //-------
    	    			 		 NodeList firstValueList = firstDate.getElementsByTagName(KEY_VALUE);
    	    			 		  Element firstValueElement = (Element)firstValueList.item(0);
    	    			 		  
    	    			 		  NodeList textValueList = firstValueElement.getChildNodes();
    	    			 		 key_svalues[r] = ((Node)textValueList.item(0)).getNodeValue().trim();
    	    			 		  //System.out.println("Values : " + String.format(key_svalues[r], key_svalues[r]));
    	    			 		  
    	    			 		 db.open();
    	    			 		  db.insertContact("s",key_sdates[r], key_svalues[r]);
    	    			 		 db.close();
    	    			 		 
    	    			 		  } //third for loop
    	    			 	    } // end of the third if
    	    			 	  } // end of the second if
    	    			   } // end of the second for loop 
    	    			  // get the percentage of values
    	    			  
    	    		  } // end of the if statement

    	    		  } //end of loop		  
    	    		} catch (NullPointerException npe){
    	    			 
    	    			 npe.printStackTrace();
    	    		  } catch (Throwable t){
    	    			  t.printStackTrace();
    	    		  } 


    	    	///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    	    	// get the benchmark1 value
    	    	try {
    	    		//--------------------the line chart value and date sector one----------------------------   
    	    		XMLParser parserB = new XMLParser();    	    
    	    		String xmlB = parserB.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
    	    		Document docB = parserB.getDomElement(xmlB); // getting DOM element  
    	    		//*************** Account information *********************************** 

    	    		  NodeList nlb = docB.getElementsByTagName(KEY_BENCHMARK1);
    	    		 // looping through all item nodes <item>
    	    		    if(nlb != null && nlb.getLength() >0){
    	    		     for (int s = 0; s < nlb.getLength(); s++) {
    	    			  //
    	    			  Node soriname = nlb.item(s);
    	    			  if(soriname.getNodeType() == Node.ELEMENT_NODE){
    	    				  Element firstSector = (Element)soriname;
    	    				  
    	    				  //------
    	    				  NodeList firstSectorList = firstSector.getElementsByTagName(KEY_SORINAME);
    	    				  Element firstSectorElement = (Element)firstSectorList.item(0);
    	    				  
    	    				  NodeList textSectorList = firstSectorElement.getChildNodes();
    	    				  key_benchName[s] = ((Node)textSectorList.item(0)).getNodeValue().trim();
    	    				  //System.out.println("benchmark Soriname : " + key_benchName[s]);
    	    				  
    	    				  //------
    	    				  NodeList colorList = firstSector.getElementsByTagName(KEY_COLOR);
    	    				  Element colorElement = (Element)colorList.item(0);
    	    				  
    	    				  NodeList textColorList = colorElement.getChildNodes();
    	    				  key_i1color[s] = ((Node)textColorList.item(0)).getNodeValue().trim();
    	    				  
    	    				  //System.out.println("benchmark1 Color is : " + key_i1color[s]);
    	    				  
    	    				  String[] s1 = key_i1color[s].split(",");
    	    				  for(int x=0; x<s1.length; x++){
    	    					  //System.out.println("color.: " +s1[x]);
    	    					  //add to the system
    	    					  key_i1colors1[s] = Integer.parseInt(s1[0]);
    	    					  key_i1colors2[s] = Integer.parseInt(s1[1]); 
    	    					  key_i1colors3[s] = Integer.parseInt(s1[2]);
    	    					  //System.out.println("Color.rgb: " + key_colors1[s]+", "+ key_colors2[s]+" , "+key_colors3[s]);
    	    				  }

    	    		////////=======Sector date and value :
    	    		  
    	    				  // Get for the childe parent
    	    				  NodeList nlr = docB.getElementsByTagName(KEY_LCRETURNS);
    	    				  // looping through all item nodes <item>
    	    				  if(nlr != null && nlr.getLength() > 0){
    	    				   for (int r = 0; r < nlb.getLength(); r++) {
    	    				 	  //
    	    				 	  Node dates = nlb.item(r);
    	    				 	  if(dates.getNodeType() == Node.ELEMENT_NODE){
    	    				 		  Element firstDatep = (Element)dates;
    	    				 		  
    	    				 		  NodeList nlrt = firstDatep.getElementsByTagName(KEY_LCRETURN);
    	    				 		  for(int rt=0; rt<nlrt.getLength(); rt++){
    	    				 			  Element firstDate = (Element) nlrt.item(rt);
    	    				 		  //------
    	    				 		  NodeList firstDateList = firstDate.getElementsByTagName(KEY_DATE);
    	    				 		  Element firstDateElement = (Element)firstDateList.item(0);
    	    				 		  
    	    				 		  NodeList textDateList = firstDateElement.getChildNodes();
    	    				 		  key_i1dates[r] = ((Node)textDateList.item(0)).getNodeValue().trim();
    	    				 		  //System.out.println("benchmark1 Dates : " + key_i1dates[r]);
    	    				 		  
    	    				 		  //-------
    	    				 		 NodeList firstValueList = firstDate.getElementsByTagName(KEY_VALUE);
    	    				 		  Element firstValueElement = (Element)firstValueList.item(0);
    	    				 		  
    	    				 		  NodeList textValueList = firstValueElement.getChildNodes();
    	    				 		 key_i1values[r] = ((Node)textValueList.item(0)).getNodeValue().trim();
    	    				 		  //System.out.println("Benchmark Values : " + String.format(key_i1values[r], key_i1values[r]));
    	    				 		  
    	    				 		 db.open();
    	    				 		  db.insertContact("b1",key_i1dates[r], key_i1values[r]);
    	    				 		 db.close();
    	    				 		 
    	    				 		  } //third for loop
    	    				 	    } // end of the third if
    	    				 	  } // end of the second if
    	    				   } // end of the second for loop 
    	    				  // get the percentage of values
    	    				  
    	    			  } // end of the if statement

    	    			  } //end of loop
    	    		    } // if
    	    			} catch (NullPointerException npe){
    	    				 
    	    				 npe.printStackTrace();
    	    			  } catch (Throwable t){
    	    				  t.printStackTrace(); 
    	    			  }

    	    	///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    	    	///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    	    	//get the benchmark2 value
    	    	try {
    	    		//--------------------the line chart value and date sector one----------------------------   
    	    		XMLParser parserB2 = new XMLParser();    	    
    	    		String xmlB2 = parserB2.getXmlFromUrl(apiCall+this.getAPIuri()); // getting XML    	    
    	    		Document docB2 = parserB2.getDomElement(xmlB2); // getting DOM element  
    	    		//*************** Account information *********************************** 

    	    		  NodeList nl = docB2.getElementsByTagName(KEY_BENCHMARK2);
    	    		 // looping through all item nodes <item>
    	    		   totalSector = nl.getLength();
    	    		  //System.out.println("Total no of Sectors: " + totalSector);
    	    		    //if(nl != null && nl.getLength() >0){
    	    		     for (int s = 0; s < nl.getLength(); s++) {
    	    			  //
    	    			  Node soriname = nl.item(s);
    	    			  if(soriname.getNodeType() == Node.ELEMENT_NODE){
    	    				  Element firstSector = (Element)soriname;
    	    				  
    	    				  //------
    	    				  NodeList firstSectorList = firstSector.getElementsByTagName(KEY_SORINAME);
    	    				  Element firstSectorElement = (Element)firstSectorList.item(0);
    	    				  
    	    				  NodeList textSectorList = firstSectorElement.getChildNodes();
    	    				  key_benchName2[s] = ((Node)textSectorList.item(0)).getNodeValue().trim();
    	    				  //System.out.println("benchmark2 Soriname : " + key_benchName2[s]);
    	    				  
    	    				  //------
    	    				  NodeList colorList = firstSector.getElementsByTagName(KEY_COLOR);
    	    				  Element colorElement = (Element)colorList.item(0);
    	    				  
    	    				  NodeList textColorList = colorElement.getChildNodes();
    	    				  key_i2color[s] = ((Node)textColorList.item(0)).getNodeValue().trim();
    	    				  
    	    				  //System.out.println("benchmark2 Color is : " + key_i2color[s]);
    	    				  
    	    				  String[] s1 = key_i2color[s].split(",");
    	    				  for(int x=0; x<s1.length; x++){
    	    					  //System.out.println("color.: " +s1[x]);
    	    					  //add to the system
    	    					  key_i2colors1[s] = Integer.parseInt(s1[0]);
    	    					  key_i2colors2[s] = Integer.parseInt(s1[1]); 
    	    					  key_i2colors3[s] = Integer.parseInt(s1[2]);
    	    					  //System.out.println("Color.rgb: " + key_colors1[s]+", "+ key_colors2[s]+" , "+key_colors3[s]);
    	    				  }

    	    		////////=======Sector date and value :
    	    		  
    	    				  // Get for the childe parent
    	    				  NodeList nlr = docB2.getElementsByTagName(KEY_LCRETURNS);
    	    				  // looping through all item nodes <item>
    	    				    totalSector = nlr.getLength();
    	    				   //System.out.println("Total no of Sectors: " + totalSector);
    	    				  if(nlr != null && nlr.getLength() > 0){
    	    				   for (int r = 0; r < nl.getLength(); r++) {
    	    				 	  //
    	    				 	  Node dates = nl.item(r);
    	    				 	  if(dates.getNodeType() == Node.ELEMENT_NODE){
    	    				 		  Element firstDatep = (Element)dates;
    	    				 		  
    	    				 		  NodeList nlrt = firstDatep.getElementsByTagName(KEY_LCRETURN);
    	    				 		  for(int rt=0; rt<nlrt.getLength(); rt++){
    	    				 			  Element firstDate = (Element) nlrt.item(rt);
    	    				 		  //------
    	    				 		  NodeList firstDateList = firstDate.getElementsByTagName(KEY_DATE);
    	    				 		  Element firstDateElement = (Element)firstDateList.item(0);
    	    				 		  
    	    				 		  NodeList textDateList = firstDateElement.getChildNodes();
    	    				 		  key_i2dates[r] = ((Node)textDateList.item(0)).getNodeValue().trim();
    	    				 		  //System.out.println("benchmark2 Dates : " + key_i2dates[r]);
    	    				 		  
    	    				 		  //-------
    	    				 		 NodeList firstValueList = firstDate.getElementsByTagName(KEY_VALUE);
    	    				 		  Element firstValueElement = (Element)firstValueList.item(0);
    	    				 		  
    	    				 		  NodeList textValueList = firstValueElement.getChildNodes();
    	    				 		 key_i2values[r] = ((Node)textValueList.item(0)).getNodeValue().trim();
    	    				 		  //System.out.println("Benchmark2 Values : " + String.format(key_i2values[r], key_i2values[r]));
    	    				 		  
    	    				 		 db.open();
    	    				 		  db.insertContact("b2",key_i2dates[r], key_i2values[r]);
    	    				 		 db.close();
    	    				 		 
    	    				 		  } //third for loop
    	    				 	    } // end of the third if
    	    				 	  } // end of the second if
    	    				   } // end of the second for loop 
    	    				  // get the percentage of values
    	    				  
    	    			  } // end of the if statement

    	    			  } //end of loop		  
    	    			} catch (NullPointerException npe){
    	    				 
    	    				 npe.printStackTrace();
    	    			  } catch (Throwable t){
    	    				  t.printStackTrace(); 
    	    			  }

    	    	///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    	    	//onStricMode();

    	    	//----get all values-----
    	    	//*****************************Sector*****************************************
    	    	db.open();
    	    	Cursor c = db.getAllValues("s");

    	    	ArrayList<Double> valueList = new ArrayList<Double>();

    	    	if(c.moveToFirst()){
    	    		do{
    	    			//DisplayContact(c);
    	    			valueList.add(Double.parseDouble(c.getString(0)));
    	    		} while(c.moveToNext());
    	    	}
    	    	//close the cursor
    	    	c.close();
    	    	//close the database
    	    	db.close();
    	    	//System.out.println("the array List Values: " +valueList);
    	    	//**************************end of Sector*************************************

    	    	//**************************Sector *******************************************
    	    	db.open();
    	    	Cursor cdate = db.getAllDates("s");

    	    	ArrayList<String> dateList = new ArrayList<String>();

    	    	if(cdate.moveToFirst()){
    	    		do{
    	    			//DisplayContact(c);
    	    			dateList.add(cdate.getString(0));
    	    		} while(cdate.moveToNext());
    	    	}
    	    	//close the cursor
    	    	cdate.close();
    	    	//close the database
    	    	db.close();
    	    	// convert the arrayList to Array
    	    	String dates[] = dateList.toArray(new String[dateList.size()]);
    	    	//System.out.println("the array List Date: " +dateList);
    	    	//*******************end of the sector****************************************

    	    	///--------------------------------------------------------------------------------------
    	    	//----get all values of benchmark1-----
    	    	//*****************************Benchmark1*****************************************
    	    	db.open();
    	    	Cursor ben1 = db.getAllValuesOfIndex1("b1");

    	    	ArrayList<Double> i1valueList = new ArrayList<Double>();

    	    	if(ben1.moveToFirst()){
    	    		do{
    	    			//DisplayContact(c);
    	    			i1valueList.add(Double.parseDouble(ben1.getString(0)));
    	    		} while(ben1.moveToNext());
    	    	}
    	    	//close the cursor
    	    	ben1.close();
    	    	//close the database
    	    	db.close();
    	    	//System.out.println("the array List index1 Values: " +i1valueList);
    	    	//**************************end of benchmark*************************************

    	    	//----get all values of benchmark2-----
    	    	//*****************************Benchmark2*****************************************
    	    	db.open();
    	    	Cursor ben2 = db.getAllValuesOfIndex2("b2");

    	    	ArrayList<Double> i2valueList = new ArrayList<Double>();

    	    	if(ben2.moveToFirst()){
    	    		do{
    	    			//DisplayContact(c);
    	    			i2valueList.add(Double.parseDouble(ben2.getString(0)));
    	    		} while(ben2.moveToNext());
    	    	}
    	    	//close the cursor
    	    	ben2.close();
    	    	//close the database
    	    	db.close();
    	    	//System.out.println("the array List index2 Values: " +i2valueList);
    	    	//**************************end of benchmark*************************************


    	    	///--------------------------------------------------------------------------------------
    	    	//------get the sorinames and color
    	    	String sector_name[] = new String[key_lsecName.length];
    	    	for(int i =0; i <key_secName.length; i++){
    	    		 if(key_lsecName[i] !=null){
    	    	 	 sector_name[i] = key_lsecName[i];
    	    	 	 // print out to see it is working
    	    	 	 //System.out.println("the final Sector name: "+sector_name[i]);
    	    		 }
    	    		 else {/***do nathing */}
    	    	}

    	    	////*****************benchmark1 name***************************************
    	    	   String Benchmark_name[] = new String[key_benchName.length];
    	    	   for(int b=0; b<key_benchName.length; b++){
    	    		   if(key_benchName[b] != null){
    	    			   Benchmark_name[b] = key_benchName[b];
    	    		   }
    	    		   else {/*do nothing*/}
    	    	   }
    	    	   
    	    	////*****************benchmark2 name***************************************
    	    	  String Benchmark_name2[] = new String[key_benchName2.length];
    	    	  for(int b=0; b<key_benchName2.length; b++){
    	    		   if(key_benchName2[b] != null){
    	    			   Benchmark_name2[b] = key_benchName2[b];
    	    		   }
    	    		   else {/*do nothing*/}
    	    	  }
    	    	  
    	    	////*********************end of********************************************
    	    	//Define the number of elements you want in the chart.
    	    	//int z[]=  {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29};
    	    	Double x3[]= i2valueList.toArray(new Double[i2valueList.size()]);
    	    	//Double x3[] = {10.2,15.1,10.6,5.7,65.2,8.6,90.5,30.9,0.8,111.1,112.1,156.2,157.0};
    	    	Double x2[]= i1valueList.toArray(new Double[i1valueList.size()]);
    	    	//Double x2[] = {10.2,1.1,1.6,15.7,25.2,18.6,50.5,10.9,10.8,71.1,82.1,96.2,97.0};
    	    	Double x1[] = valueList.toArray(new Double[valueList.size()]);

    	    	// get the array value
    	    	int ind[] = new int[x1.length];
    	    	for(int i=0; i<x1.length; i++){
    	    		// get the array list indexes
    	    		ind[i] = i;
    	    	}
    	    	// Create XY Series for X Series.
    	    	//get the legend name f
    	    	XYSeries x3Series=new XYSeries(Benchmark_name2[0]);

    	    	//Adding data to the X Series the y chart
    	    	for(int i=0; i<valueList.size(); i++){
    	    		if(x3[i] != null)
    	    		  x3Series.add(ind[i], x3[i]);
    	    		else {/*do nothing*/}
    	    	}

    	    	XYSeries x2Series=new XYSeries(Benchmark_name[0]);

    	    	//Adding data to the X Series the y chart
    	    	for(int i=0; i<valueList.size(); i++){
    	    		if(x2[i] != null)
    	    			x2Series.add(ind[i], x2[i]);
    	    		else {/*do nothing*/}
    	    	}

    	    	XYSeries x1Series=new XYSeries(sector_name[0]);
    	    	//Adding data to the X Series.
    	    	for(int i=0;i<valueList.size();i++){
    	    		if(x1[i] != null)
    	    			x1Series.add(ind[i],x1[i]);
    	    		else {/*do nothing*/}
    	    	}

    	    	//Create a Dataset to hold the XSeries.
    	    	XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
    	    	//Add X series to the Dataset.  
    	    	for(int i=0; i<key_secName.length; i++){
    	    		if(key_lsecName[i] !=null)
    	    			dataset.addSeries(x1Series);
    	    		if(key_benchName[i] != null)
    	    		    dataset.addSeries(x2Series);
    	    		if(key_benchName2[i] != null)
    	    		    dataset.addSeries(x3Series);
    	    		else {/*do nothing */}
    	    	} 
    	    	//Create XYSeriesRenderer to customize XSeries
    	    	XYSeriesRenderer X1renderer=new XYSeriesRenderer();
    	    	//get the color from xml
    	    	for(int i=0; i<key_lsecName.length; i++){
    	    		if(key_secName[i] != null)
    	    			X1renderer.setColor(Color.rgb(key_scolors1[i], key_scolors2[i], key_scolors3[i]));
    	    		else {/*do nothing*/}
    	    	}
    	    	X1renderer.setPointStyle(PointStyle.CIRCLE);
    	    	X1renderer.setDisplayChartValues(false);
    	    	X1renderer.setFillPoints(true);

    	    	//----
    	    	XYSeriesRenderer X2renderer=new XYSeriesRenderer();
    	    	//get the benchmark1 color
    	    	for(int b=0; b<key_benchName.length; b++){
    	    		if(key_benchName[b] != null)
    	    			X2renderer.setColor(Color.rgb(key_i1colors1[b], key_i1colors2[b], key_i1colors3[b]));
    	    		else {/*donathing */}
    	    	}

    	    	X2renderer.setPointStyle(PointStyle.CIRCLE);
    	    	X2renderer.setDisplayChartValues(false);
    	    	X2renderer.setFillPoints(true);

    	    	//----
    	    	XYSeriesRenderer X3renderer=new XYSeriesRenderer();
    	    	//get the benchmark1 color
    	    	for(int b=0; b<key_benchName2.length; b++){
    	    		if(key_benchName[b] != null)
    	    			X3renderer.setColor(Color.rgb(key_i2colors1[b], key_i2colors2[b], key_i2colors3[b]));
    	    		else {/*donathing */}
    	    	}
    	    	X3renderer.setPointStyle(PointStyle.CIRCLE);
    	    	X3renderer.setDisplayChartValues(false);
    	    	X3renderer.setFillPoints(true);
    	    	// Create XYMultipleSeriesRenderer to customize the whole chart
    	    	XYMultipleSeriesRenderer mRenderer=new XYMultipleSeriesRenderer();
    	    	mRenderer.setChartTitle("TOTAL ACCOUNT SUMMARY");
    	    	//mRenderer.setXTitle("X Values");
    	    	//mRenderer.setYTitle("Y Values");
    	    	mRenderer.setZoomButtonsVisible(false);
    	    	//mRenderer.setXLabels(0);
    	    	mRenderer.setPanEnabled(false);
    	    	mRenderer.setShowGrid(true);
    	    	mRenderer.setClickEnabled(false);
    	    	for(int i=0;i<ind.length;i++)
    	    	{
    	    	mRenderer.addXTextLabel(i, dates[i]);
    	    	}
    	    	mRenderer.setXLabelsAngle(-45);
    	    	// Adding the XSeriesRenderer to the MultipleRenderer.
    	    	if(key_lsecName[0] != null)
    	    		mRenderer.addSeriesRenderer(X1renderer);
    	    	if(key_benchName[0] != null)
    	    		mRenderer.addSeriesRenderer(X2renderer);
    	    	if(key_benchName2[0] != null)
    	    		mRenderer.addSeriesRenderer(X3renderer);
    	    	else {/*do nothing*/}
    	    	//change the margin colors
    	    	mRenderer.setMarginsColor(Color.WHITE);
    	    	//setup the x axis label
    	    	mRenderer.setLabelsColor(Color.BLACK);
    	    	mRenderer.setXLabelsColor(Color.BLACK);
    	    	mRenderer.setXLabelsAlign(Paint.Align.RIGHT);
    	    	//setup the y axis label
    	    	mRenderer.setYLabelsAlign(Paint.Align.RIGHT);
    	    	mRenderer.setYLabels(10);
    	    	mRenderer.setYLabelsPadding(5);
    	    	mRenderer.setXLabelsPadding(5);

    	    	LinearLayout chart_container=(LinearLayout)findViewById(R.id.pie_chart);
    	    	
    	    	//Creating an intent to plot line chart using dataset and multipleRenderer
    	    	lChart=(GraphicalView)ChartFactory.getLineChartView(getBaseContext(), dataset, mRenderer);
    	    	
    	    	//Adding click event to the Line Chart.

    	    	 // Add the graphical view mChart object into the Linear layout .
    	    	 chart_container.addView(lChart);
    	    	//}

    	    	//System.exit(0);
    	    } // end of the line graph method
    	    
    	    //show pie graph when it clicked
    	    public void showPieGraph(View view){
    	    	LinearLayout chart_container=(LinearLayout)findViewById(R.id.pie_chart);
    	    	chart_container.removeAllViews();
    	    	
    	    	// find the show allocation summary and set it invisiable
    	    	Button btnSAS = (Button) findViewById(R.id.btnShowSectors);
    	    	btnSAS.setEnabled(true);
    	    	
    	    	displayGraph();
    	    }
    	    
    	    //show line graph when it clicked
    	    public void showLineGraph(View view){
    	    	LinearLayout chart_container=(LinearLayout)findViewById(R.id.pie_chart);
    	    	chart_container.removeAllViews();
    	    	
    	    	// find the pie button and set it visable
    	    	btnPie = (Button) findViewById(R.id.btnShowPieG);
    	    	btnPie.setEnabled(true);
    	    	
    	    	// find the show allocation summary and set it invisiable
    	    	Button btnSAS = (Button) findViewById(R.id.btnShowSectors);
    	    	btnSAS.setEnabled(false);
    	    	
    	    	//----delete all record from table
    	         db.open();
    	         db.deleteContact();
    	         db.close();
    	         
    	    	createLineGraph();
    	    }// end of the line graph creation
    	 
    	    // delete all record when the user is login out 
    	    public void deleteStoredData(){
    	    	//----delete all record from line chart table
   	            db.open();
   	            db.deleteContact();
   	            db.close();
   	            
   	            // delete all stored accounts
   	            db.open();
   	            db.deleteAllAccts();
   	            db.close();
    	    }

 // Search accounts  	
    	    public void searchAcct() {
    	    	try{
    	    		//find the list view
    	    		acctListView=(ListView) findViewById(R.id.viewMatchAcct);
    	    		searchView=(SearchView) findViewById(R.id.search_view);
    	    		//searchView.setQueryHint(Html.fromHtml("<font color='#FFFFFF'>Find a Portfolio</font>"));
    	    		searchView.setBackgroundColor(R.drawable.ok_back);
    	    		searchView.setFocusable(false);
    	    		searchView.setSubmitButtonEnabled(false);
    	    		
    	    		// find the match partial checkbox
    	    		chkmpids = (CheckBox) findViewById(R.id.chkmpids);
    	    		//apply the text color
    				
                    //*** setOnQueryTextListener ***
                    searchView.setOnQueryTextListener(new OnQueryTextListener() {
            			
            		@Override
            		public boolean onQueryTextSubmit(String query) {
            			// TODO Auto-generated method stub
            			System.out.println("you clicked on Submit button: " + query);
            			return true;
            		}
            		
            		
            		@Override
            		public boolean onQueryTextChange(String newText) {
            			XMLParser parserA = new XMLParser();    	    
                		String xmlA = parserA.getXmlFromUrl(MAcctXML+this.getAPIuriS()); // getting XML  
                		Document docA = parserA.getDomElement(xmlA); // getting DOM element  
                		//*************** Account information ***********************************
                		  NodeList nlA = docA.getElementsByTagName("ACCOUNT");
            			//open the database and do searching
            			if(newText.length() != 0){
            				// looping through all item nodes <item>
                		    if(nlA != null && nlA.getLength() >0){
                		     for (int s = 0; s < nlA.getLength(); s++) {
                			  //
                			  Node acctid = nlA.item(s);
                			  if(acctid.getNodeType() == Node.ELEMENT_NODE){
                				  Element firstACCT = (Element)acctid;
                				  
                				  //------
                				  NodeList firstACCTList = firstACCT.getElementsByTagName("ACCOUNTID");
                				  Element firstACCTElement = (Element)firstACCTList.item(0);
                				  
                				  NodeList textACCTList = firstACCTElement.getChildNodes();
                				  key_acctid[s] = ((Node)textACCTList.item(0)).getNodeValue().trim();
                				  //System.out.println("Account id : " + key_acctid[s]);
                				  
                				  //------
                				  NodeList acctnameList = firstACCT.getElementsByTagName("ACCOUNTNAME");
                				  Element acctnameElement = (Element)acctnameList.item(0);
                				  
                				  NodeList textAcctnameList = acctnameElement.getChildNodes();
                				  key_acctnames[s] = ((Node)textAcctnameList.item(0)).getNodeValue().trim();
                				  
                				  //System.out.println("Account name SARWARDDDDD : " + key_acctnames[s]);  
                				  

                			  } // end of the if statement
                		     } // loop
                            } //if statement
            				ArrayList<String> accounts = new ArrayList<String>();
            				System.out.println("the sarwar test");
             			     for(int act=0; act <key_acctid.length; act++){
             			    	if(key_acctid[act] != null){
             			    		
             			    		if(key_acctid[act].matches(newText) || key_acctnames[act].matches(newText)){
             			    			//check the typed text is matching to the accounts
             			    			//System.out.println("Account name SARWARDDDDD : "+key_acctid[act]+" == " + newText);
                 			    		accounts.add(key_acctid[act] + " " +key_acctnames[act]);
                 	  				    acctListView.setVisibility(View.VISIBLE);
                 	  				    acctListView.setAdapter(new ArrayAdapter<String>(DashboardActivity.this,android.R.layout.simple_list_item_1, accounts));
                 	  				    getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
             			    		}
             			    	}
             			    	else { /*--do nothing */}
             			     }
                			
                	    	// selecting single ListView item
                	    	acctListView.setOnItemClickListener(new OnItemClickListener() {

                	    		@Override
                	    		public void onItemClick(AdapterView<?> parent, View view,
                	    		int position, long id) {
                	    			//................................
                 	    			String aidaname = acctListView.getItemAtPosition(position).toString().trim();
                 	    			String splittext[] = aidaname.split(" ");
                 	    			//System.out.println("the selected item: "+ aidaname);
                 	    			selectedAcct = splittext[0];
                 	    		    acctName = null;
                 	    		    if(acctName == null)
                 	    		    	acctName = "";
                 	    		    
                 	    			System.out.println("the acctid is: "+splittext[0]);
                 	    			for(int x =1; x<splittext.length; x++){
                 	    				acctName += " "+splittext[x].toString();
                 	    			}
                 	    			
                	    		// getting values from selected ListItem
                	    			// account information
                 		            accountInfo();
                 		            //create the MTD List information
                 	              	mtdListInf();
                 	              	// create the YTD infor
                 	               	ytdListInfo();
                 	            // display the pie graph
                 				   displayGraph(); 
                 	                
                 	                //hide the account list
                 	               acctListView.setVisibility(acctListView.INVISIBLE);
                 	           // find the show allocation summary and enable it
                    	 	    	Button btnSAS = (Button) findViewById(R.id.btnShowSectors);
                    	 	    	btnSAS.setEnabled(true);
                    	             //-----find the pie graph and set background color
                    	             btnPie = (Button) findViewById(R.id.btnShowPieG);
                    	             btnPie.setEnabled(true);
                    	             //---find the line graph button and enable
                    	             Button btnLineG = (Button) findViewById(R.id.btnShowLineG);
                    	             btnLineG.setEnabled(true);
                    	             //---find the Portfolio Statement button and enable
                    	             Button btnPortStat = (Button) findViewById(R.id.btnPortState);
                    	             btnPortStat.setEnabled(true);
                	    		}
                	    		});
                	    	  return true;
                	    	  
                	    	  
            			}//end of the if condition
            	    	
            			return true;
            		}


					private String getAPIuriS() {
						String apiParam = selectedAcct+"&IBIC_user="+userid+"&IBIC_pass="+pass+"&CLIENT=DV"+sitek+"63&ASOFDATE="+apiAsOf;
				    	return apiParam;
					}
            		
            	});
                  //-- setup the close listener
                    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
						
						@Override
						public boolean onClose() {
							// TODO Auto-generated method stub
							acctListView.setVisibility(View.INVISIBLE);
							return true;
						}
					});
    	    	} catch (NullPointerException np){
    	    		np.printStackTrace();
    	    	}
    	    	
    	    }// end of the search value
    	    
    	    
    	    public void DisplayMatchAcct(String searchVal){
    	    	Toast.makeText(getBaseContext(), searchVal, 
    	    			Toast.LENGTH_SHORT).show();
    	    }
    	    
    	    // display the help dialog
    	    public void showHelpDialog(View view){
    	    	try{
    	    		LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
    	    	    View layout = inflater.inflate(R.layout.helplayout, (ViewGroup)findViewById(R.id.root));

    	    		AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this, android.R.style.Theme);
    				//builder.setTitle("help how to use app");
    				//builder.setMessage("this is a test message");
    				//builder.setIcon(R.drawable.help);
    				builder.setView(layout);
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
    	    	} catch (NullPointerException np){
    	    		np.printStackTrace();
    	    	}
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

