package com.rssfeed.activity;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rssfeed.R;
import com.rssfeed.adapter.RssReaderListAdapter;
import com.rssfeed.helper.About;
import com.rssfeed.helper.CheckDate;
import com.rssfeed.helper.MySQLiteHelper;
import com.rssfeed.helper.ReverseOrder;
import com.rssfeed.helper.RssFeedStructure;

import com.rssfeed.helper.XmlHandler;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RssFeedReaderActivity extends Activity {
    /** Called when the activity is first created. */
	Typeface helveticaBold;
	Typeface helveticaRegular;
	ListView _rssFeedListView;
	  Button buttonLoad;	
	 ListView _rssFeedListViewExists;
	List<JSONObject> jobs ;
	 List<RssFeedStructure> rssStr ;
	 List<RssFeedStructure> LoadMore ;

	 List<RssFeedStructure> rssStrExists=null ;
	 int location=0;
	 int DEFAULT_LIMIT=10;
     int Iterations=1;
     int IterationsAmnt=4;
 	private Context context;

	private RssReaderListAdapter _adapter;
	String sorti = "";
	String mode = "";
	 XmlHandler rh = new XmlHandler();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rssfeedreaderactivity);
       _rssFeedListView = (ListView)findViewById(R.id.rssfeed_listview);
      
       
		  context=getBaseContext();
      RssFeedTask rssTask = new RssFeedTask(context);
      rssTask.execute();
     
	  MySQLiteHelper db = new MySQLiteHelper(context); 
       	String Tab=getIntent().getExtras().getString("Type");	
       	boolean CheckExists=db.CheckParamExists(Tab);
     
       	if(CheckExists==true){
	  List<Integer> Params=db.getParams(Tab);
		Iterations=Params.get(1);}
	  
        if(Iterations<IterationsAmnt){
        	buttonLoad = new Button(this);
            buttonLoad.setText("Load More");
		  _rssFeedListView.addFooterView(buttonLoad);

      buttonLoad.setOnClickListener(new View.OnClickListener() {
		  
          @Override
          public void onClick(View arg0) {
        	  ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
      		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

    		if (networkInfo != null && networkInfo.isConnected()) {
              new loadMoreListView(context).execute();}       }   
      });  } 
     
		// 
    }
    private class RssFeedTask extends AsyncTask<String, Void, String> {
		private ProgressDialog Dialog;
		String response = "";
		private Context context;

		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(RssFeedReaderActivity.this);
			Dialog.setMessage("Loading...");
		Dialog.show();
		
		}
		public RssFeedTask(Context context){
		    this.context=context;
		}
		@Override
		protected String doInBackground(String... urls) {
			 String Tab=getIntent().getExtras().getString("Type");
  
			try {
				//  String feed = "http://feeds.nytimes.com/nyt/rss/HomePage";
		          MySQLiteHelper db = new MySQLiteHelper(context); 

		          ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		      		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
Log.d("internet",String.valueOf(networkInfo));
		    		if (networkInfo != null && networkInfo.isConnected()) {
		           
		                //Connected
						String search = getIntent().getExtras().getString("Search");
						URL url=new URL(search);

				// True if the phone is connected to some type of network i.e. has signal
			  //      URL url = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=(Genomics)+NOT+proteomics&retmax=40&usehistory=y");
				 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("POST");
			        conn.setRequestProperty("ACCEPT","application/xml");
			        InputStream xml = conn.getInputStream();

			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder builder = factory.newDocumentBuilder();
			        org.w3c.dom.Document document = builder.parse(xml);
			        document.getDocumentElement().normalize();


			        NodeList nodes = document.getElementsByTagName("Id");

			          String checkids[] = getNodeValue(nodes);
			          String idtemp=checkids[0];
			          Log.v("id???",String.valueOf(idtemp));

			     	 CheckDate dt=new CheckDate();
			         String feed=" http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+idtemp+"&retmode=xml";
					     
			         RssFeedStructure feedStr = dt.getDate(feed,Tab);  
					     String date=feedStr.getPubDate();
					     String PMID=feedStr.getPMID();

				          boolean DoesExists= db.CheckExists(date,Tab);
					     Log.v("Exists???",String.valueOf(DoesExists));

			          if(DoesExists==true){
			        	  rssStrExists=db.getAll(Tab);
			        		db.getAll(Tab);
			          }
			          else if(DoesExists==false){
			        	  db.DeleteAll(Tab);
			        	  db.deleteParams(Tab);
					     rssStr = rh.getLatestArticles(feed,Tab);  
						 for(String id : checkids){
							 if(id!=checkids[0]){
							location++;
						  XmlHandler rh = new XmlHandler();
						  feed="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+id+"&retmode=xml";
			        	  rssStr.addAll( rh.getLatestArticles(feed,Tab));}
						if(rssStr.size()==DEFAULT_LIMIT){break;}
						 }
						//	rssStr=st.Sorting(rssStr);
						 }
		           }
		           
			       else
			           {
			    	   rssStrExists=db.getAll(Tab);
		        		db.getAll(Tab);
			           }				  
		           } catch (Exception e) {
			        }
			
			        return response;

		}

		@Override
		protected void onPostExecute(String result) {
			 String Tab=getIntent().getExtras().getString("Type");

			  if(rssStr != null ){
		          MySQLiteHelper db = new MySQLiteHelper(context); 

				  boolean CheckExists=db.CheckParamExists(Tab);
			        if(CheckExists==true){
			      	db.deleteParams(Tab);}
			     	db.saveParameter(Tab,location,Iterations);
				  for(RssFeedStructure rss : rssStr ){
			        db.addArticle(rss,Tab);
				  }
				  _adapter = new RssReaderListAdapter(RssFeedReaderActivity.this,rssStr);
		        _rssFeedListView.setAdapter(_adapter);
				  FillAbstractView();

			  }
			  else if(rssStrExists!=null){
				  _adapter = new RssReaderListAdapter(RssFeedReaderActivity.this,rssStrExists);
			        _rssFeedListView.setAdapter(_adapter);
					  FillAbstractView();
			  }
			  Dialog.dismiss();


		}
	}
    
    String[] getNodeValue(NodeList nodes) {
        String checkIds[] = new String[nodes.getLength()];
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            checkIds[i] = node.getTextContent();
        }
        return checkIds;
    }
 

	public void FillAbstractView(){
		_rssFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
                
                Intent detailIntent =new Intent(RssFeedReaderActivity.this, Abstract.class);          		                
                
                TextView text = (TextView) view.findViewById(R.id.feed_text);
                String Title = text.getText().toString();
                
                text = (TextView) view.findViewById(R.id.abs);
                String Abstract = text.getText().toString();
                
                text = (TextView) view.findViewById(R.id.authors);
                String Authors = text.getText().toString();
                
                text = (TextView) view.findViewById(R.id.feed_updatetime);
                String Date = text.getText().toString();
                
                text = (TextView) view.findViewById(R.id.pmid);
                String PMID = text.getText().toString();
                
                detailIntent.putExtra("abstract", Abstract);
                detailIntent.putExtra("title", Title);
                detailIntent.putExtra("authors", Authors);
                detailIntent.putExtra("date", Date);
                detailIntent.putExtra("pmid", PMID);


                startActivity(detailIntent); 
        	
        	}
        			});	
	}
	
	
	/**
	 * Async Task that send a request to url
	 * Gets new list view data
	 * Appends to list view
	 * */
	private class loadMoreListView extends AsyncTask<String, Void, String> {
		private ProgressDialog Dialog;
		String response = "";
		private Context context;
		
	    @Override
	    protected void onPreExecute() {
	    	Dialog = new ProgressDialog(RssFeedReaderActivity.this);
			Dialog.setMessage("Loading...");
			Dialog.show();
	      	String Tab=getIntent().getExtras().getString("Type");
			
			
		//	if(Iterations>=5){
			//	  _rssFeedListView.removeFooterView(buttonLoad);
		   //   }
	        
	    }
	    public loadMoreListView(Context context){
		    this.context=context;
		}
	    @Override

	    protected String doInBackground(String... urls) {
	        runOnUiThread(new Runnable() {
	            public void run() {
	  	          MySQLiteHelper db = new MySQLiteHelper(context); 
	            	String Tab=getIntent().getExtras().getString("Type");

	            	List<Integer> Params=db.getParams(Tab);
	    			Iterations=Params.get(1);
	    			if(location!=0){location=Params.get(0);}
	            	  
	    			try {
	    				//  String feed = "http://feeds.nytimes.com/nyt/rss/HomePage";

	    				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			      		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

			    		if (networkInfo != null && networkInfo.isConnected()) {
	    		                //Connected
	    						String search = getIntent().getExtras().getString("Search");
	    						URL url=new URL(search);

	    				// True if the phone is connected to some type of network i.e. has signal
	    			  //      URL url = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=(Genomics)+NOT+proteomics&retmax=40&usehistory=y");
	    				 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    			        conn.setRequestMethod("POST");
	    			        conn.setRequestProperty("ACCEPT","application/xml");
	    			        InputStream xml = conn.getInputStream();

	    			        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    			        DocumentBuilder builder = factory.newDocumentBuilder();
	    			        org.w3c.dom.Document document = builder.parse(xml);
	    			        document.getDocumentElement().normalize();


	    			        NodeList nodes = document.getElementsByTagName("Id");

	    			          String checkids[] = getNodeValue(nodes);
	    			          String idtemp=checkids[location];
	    			          Log.v("id???",String.valueOf(idtemp));

	    			     	 CheckDate dt=new CheckDate();
	    			     if(rssStr!=null){	rssStr.clear();}
   							  rssStr=db.getAll(Tab);
   						 // }
	    			     	 String feed=" http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+idtemp+"&retmode=xml";
	    					     rssStr.addAll( rh.getLatestArticles(feed,Tab));  
	    						int size=0;
	    					     for(String id : checkids){
	    							 if(id!=checkids[location]){
	    						  XmlHandler rh = new XmlHandler();
	    						  feed="http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+checkids[location]+"&retmode=xml";
	    							location++;

	    						  rssStr.addAll( rh.getLatestArticles(feed,Tab));
	    						  size++;
	    						  }
	    						if(size>=DEFAULT_LIMIT){
	    							Iterations++;
	    							break;}
	    						 }
	    		           }
	    			      				  
	    		           } catch (Exception e) {
	    		}

	                // get listview current position - used to maintain scroll position
	                int currentPosition = _rssFeedListView.getFirstVisiblePosition();
	 
	                // Appending new data to menuItems ArrayList
	                for(RssFeedStructure rss : rssStr ){
				          if(!db.CheckIdExists(rss.getPMID())){
				        db.addArticle(rss,Tab);}
					  }
					  _adapter = new RssReaderListAdapter(RssFeedReaderActivity.this,rssStr);
			        _rssFeedListView.setAdapter(_adapter);
					  FillAbstractView();
	 
	                // Setting new scroll position
	                _rssFeedListView.setSelectionFromTop(currentPosition + 1, 0);
	            }
	        });

	        return (null);
	    }       
	 @Override
	    protected void onPostExecute(String Result) {
         MySQLiteHelper db = new MySQLiteHelper(context); 
      	String Tab=getIntent().getExtras().getString("Type");
      	boolean CheckExists=db.CheckParamExists(Tab);
        if(CheckExists==true){
      	db.deleteParams(Tab);}
     	db.saveParameter(Tab,location,Iterations);
		 if(Iterations>=IterationsAmnt){
			  _rssFeedListView.removeFooterView(buttonLoad);
	      }
	        Dialog.dismiss();
	    }

		
	}
	
	
}