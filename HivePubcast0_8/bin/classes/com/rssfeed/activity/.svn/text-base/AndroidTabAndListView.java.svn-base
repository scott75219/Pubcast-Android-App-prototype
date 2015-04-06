package com.rssfeed.activity;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.rssfeed.R;
import com.rssfeed.helper.About;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


 
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidTabAndListView extends TabActivity {
    private TabHost mTabHost;
    private int querey=1000;
    String Genomics="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=(Genomics)+NOT+proteomics+AND%20hasabstract/&reldate=10&datetype=edat&retmax="+querey+"&usehistory=y";
    String Proteomics="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=(Proteomics)+NOT+genomics+AND%20hasabstract/&reldate=10&datetype=edat&retmax="+querey+"&usehistory=y";
    String Opinion="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=(((editorial[Publication%20Type])%20OR%20news[Publication%20Type]))%20AND%20hasabstract/&retmax="+querey+"&usehistory=y";
    String Genes="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=";
    static TextView tab;
   
    
    private void setupTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
}
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setupTabHost();
        setupTab(new TextView(this), "Genomics", "RssFeedReaderActivity.class");
        setupTab(new TextView(this), "Proteomics", "RssFeedReaderActivity.class");
        setupTab(new TextView(this), "Opinion", "TwitterActivity.class");
        
    }
    private void setupTab( View view,  String tag,  String className) {
    		View tabview = createTabView(mTabHost.getContext(), tag);
    		Intent intent = new Intent().setClass(this, RssFeedReaderActivity.class);
    		
    		if (tab.getText().equals("Opinion")) {
                intent = new Intent().setClass(this, TwitterActivity.class);
                intent.putExtra("Search",Opinion);
    			intent.putExtra("Type","Opinion");    
    		}
    		
    		if (tab.getText().equals("Genomics")) {
    			intent = new Intent().setClass(this, RssFeedReaderActivity.class);
    			intent.putExtra("Search",Genomics);
    			intent.putExtra("Type","Genomics");
    			
    			}
    		if (tab.getText().equals("Proteomics")) {
                intent = new Intent().setClass(this, RssFeedReaderActivity.class);
                intent.putExtra("Search",Proteomics);
    			intent.putExtra("Type","Proteomics");    
    		}
    		
    		
   
    		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(intent); {
    };
mTabHost.addTab(setContent);
}

    private static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        tab=tv;
        return view;
}

    @Override
  	public boolean onCreateOptionsMenu(Menu menu) {
      	MenuInflater inflater = getMenuInflater();
      	inflater.inflate(R.menu.list_menu, menu);
      	
      	return true;
      	 
      	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {	 

    	switch (item.getItemId()) {
	case R.id.about:
		startActivity(new Intent(this, About.class));
		break;

	case R.id.apps:
		startActivity(new Intent(this, HiveApps.class));

	break;
	
	case R.id.refresh:
	recreate();
	}
	return true;
	}
    
    public String Genes() {
    	String file = "/files/Genes.csv";
    	BufferedReader br = null;
    	String line = "";
    	String cvsSplitBy = ",";
     
    	try {
     
    		br = new BufferedReader(new FileReader(file));
    		while ((line = br.readLine()) != null) {
     
    		        // use comma as separator
    			String[] g = line.split(",");
    			  for (int i = 0; i < g.length; i++){
    			        Genes=Genes+"OR+"+g[i];
    			}
    		}
    			
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}    
    	Genes=Genes+"AND%20hasabstract/&reldate=10&datetype=edat&retmax="+querey+"&usehistory=y";
		return Genes;
      }
}
