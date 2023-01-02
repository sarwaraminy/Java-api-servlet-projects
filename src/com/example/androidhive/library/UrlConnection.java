package com.example.androidhive.library;

public class UrlConnection {
	 // All static variables
	
	//-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//-- suit global variables
	//-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	static String FRP_ROOT    = "FRP";
	static String FRP_VERSION = "63";
	static String FRP_SUITE   = "ADV";
	static String ipadd = "http://10.0.0.10:8080";
	//----------------------------------------------------------------------------
	
	
	static final String MACCTS_XML = ipadd+"/ibi_apps/WFServlet?IBIF_ex=FRPNADA&USE_RC=Y&PROGRAM=FRMACCTS"+
			"&FRPAPI=Y&FRP_ROOT="+FRP_ROOT+"&FRP_VERSION="+FRP_VERSION+"&FRP_SUITE="+FRP_SUITE+"&ACCT=";
	
	static final String apiToCreatXML = ipadd+"/ibi_apps/WFServlet?IBIF_ex=FRPNADA&USE_RC=Y&PROGRAM=FRMMAIN"+
			"&FRPAPI=Y&FRP_ROOT="+FRP_ROOT+"&FRP_VERSION="+FRP_VERSION+"&FRP_SUITE="+FRP_SUITE+"&ACCT=";
	
	
	public String getAPI(){
		return apiToCreatXML; 
	}
	//for network
	// point to point api call url
	static final String URLptp = ipadd+"/ibi_apps/WFServlet?IBIF_ex=FRPNADA&USE_RC=Y&PROGRAM=FRMPTP"+
			"&FRPAPI=Y&FRP_ROOT="+FRP_ROOT+"&FRP_VERSION="+FRP_VERSION+"&FRP_SUITE="+FRP_SUITE+"&ACCT=";
	
	static final String loginUrl = "http://firstrate.af/android_login_api/";
	//static final String loginUrl = "http://10.0.0.13/android_login_api/";
	
	static final String registerUrl = "http://firstrate.af/android_login_api/"; 
	
	//get the xml file path
	/**
	 * @return
	 */

	//--get the point to point xml
	//get the xml file path
		public String getURLptp(){
			return URLptp;
		}
	
	//get the login url
	public String getLoginUrl(){
		return loginUrl;
	}
	
	// get the register url
	public String getRegisterUrl(){
		return registerUrl;
	}
	
	
	//---add the accounts to the account list
	public String getMACCTS_XML(){
		return MACCTS_XML;
	}
}
