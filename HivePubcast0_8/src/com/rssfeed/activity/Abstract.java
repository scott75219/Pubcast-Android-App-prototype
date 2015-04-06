package com.rssfeed.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rssfeed.R;
import com.rssfeed.activity.Abstract;
import com.rssfeed.adapter.RssReaderListAdapter;
import com.rssfeed.helper.About;
import com.rssfeed.helper.RssFeedStructure;
import com.rssfeed.helper.XmlHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Abstract extends Activity {
	
	ListView _rssFeedListView;
	 List<RssFeedStructure> rssStr ;
	private RssReaderListAdapter _adapter;
	String sorti = "";
	String mode = "";
	 XmlHandler rh = new XmlHandler();
		List<RssFeedStructure> imageAndTexts1 =null;
		RssFeedReaderActivity RS= new RssFeedReaderActivity();

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        SetDate();
        SetTitle();
        SetAbstract();
        SetAuthors();
        SetPMID();

        }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_options, menu);
    	return true;
    	 
    	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	 
	switch (item.getItemId()) {
	case R.id.email:
		String title = getIntent().getExtras().getString("title")+"\n"+"\n";
		String abs = getIntent().getExtras().getString("abstract")+"\n";
		String authors = getIntent().getExtras().getString("authors")+"\n";
		String date = getIntent().getExtras().getString("date")+"\n"+"\n";
		String subject=date+title+authors+abs;
		
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/html");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "From HIVE Pubcast: "+title);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, subject);		
		startActivity(Intent.createChooser(emailIntent, "Email:"));
		break;
	case R.id.about:
		startActivity(new Intent(this, About.class));
 
	break;
	
	}
	return true;
	}
	public void SetTitle(){
		TextView view = (TextView) findViewById(R.id.Title);
		String position = getIntent().getExtras().getString("title");
		view.setText(position);
		
	}
	public void SetAbstract(){
		TextView view = (TextView) findViewById(R.id.Abstract);
		String position = getIntent().getExtras().getString("abstract");
		view.setText(position);
	}
	
	public void SetAuthors(){
		TextView view = (TextView) findViewById(R.id.author_list);
		String position = getIntent().getExtras().getString("authors");
		view.setText(position);
	}
	public void SetDate(){
		TextView view = (TextView) findViewById(R.id.Date);
		String position = getIntent().getExtras().getString("date")+"\n";
		view.setText(position);
	}
	public void SetPMID(){
		TextView view = (TextView) findViewById(R.id.pmid);
		final String position = getIntent().getExtras().getString("pmid")+"\n";
		view.setText("PMID: "+ position);
		view.setPaintFlags(view.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
		view.setOnClickListener(new View.OnClickListener() {          
            public void onClick(View v) {
                // TODO Auto-generated method stub               
            	Uri uri = Uri.parse("http://www.ncbi.nlm.nih.gov/pubmed/?term="+position);
                                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openBrowser);
            }
        });
	}

}
