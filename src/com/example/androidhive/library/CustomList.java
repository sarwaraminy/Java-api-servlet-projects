package com.example.androidhive.library;

import com.example.androidhive.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class CustomList extends ArrayAdapter<String>{
	private final Activity context;
	public CustomList(Activity context,
	String[] web, Integer[] imageId) {
	super(context, R.layout.search_layout, web);
	this.context = context;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.search_layout, null, true);
	Button btnMatch = (Button) rowView.findViewById(R.id.btnMatch);
	EditText txtSearch = (EditText) rowView.findViewById(R.id.txtSearch);
	
	return rowView;
	}
	
	
}
	
	
