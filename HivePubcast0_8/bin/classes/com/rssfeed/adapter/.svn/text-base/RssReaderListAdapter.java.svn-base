package com.rssfeed.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.rssfeed.R;
import com.rssfeed.helper.MySQLiteHelper;
import com.rssfeed.helper.RssFeedStructure;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssReaderListAdapter extends ArrayAdapter<RssFeedStructure> {
	List<RssFeedStructure> imageAndTexts1 =null;

public RssReaderListAdapter(Activity activity, List<RssFeedStructure> imageAndTexts) {
	super(activity, 0, imageAndTexts);
	imageAndTexts1=imageAndTexts;
	  SortJournal();

	
}


@Override
public View getView(int position, View convertView, ViewGroup parent) {

Activity activity = (Activity) getContext();
LayoutInflater inflater = activity.getLayoutInflater();


View rowView = inflater.inflate(R.layout.rssfeedadapter_layout, null);
TextView textView = (TextView) rowView.findViewById(R.id.feed_text);
TextView timeFeedText = (TextView) rowView.findViewById(R.id.feed_updatetime);
TextView abs = (TextView) rowView.findViewById(R.id.abs);
TextView authors = (TextView) rowView.findViewById(R.id.authors);
TextView part_abs = (TextView) rowView.findViewById(R.id.first_line);
TextView pmid = (TextView) rowView.findViewById(R.id.pmid);


String str = "";

        try {
        	
        //	Log.d("rssfeed", "imageAndTexts1.get(position).getImgLink() :: " +imageAndTexts1.get(position).getImgLink() +" :: " +imageAndTexts1.get(position).getTitle());
        	textView.setText(imageAndTexts1.get(position).getTitle());
        	String Summary="";
        	if(imageAndTexts1.get(position).getAbstract()!=null){
        			Summary=imageAndTexts1.get(position).getAbstract()+"\n";
        			if(Summary.length()>=120){str = Summary.substring(0, 120) + "...";}
        			else{str=Summary;}
        			part_abs.setText(str);
        		}
        	if(imageAndTexts1.get(position).getAffiliation()!=null){Summary=Summary+imageAndTexts1.get(position).getAffiliation()+"\n"+"\n";}
        	String PMID =imageAndTexts1.get(position).getPMID();
        	pmid.setText(PMID);
        	
        	String Authors="\n"+imageAndTexts1.get(position).getAuthors()+"\n";

        	
        	abs.setText(Summary);
        	authors.setText(Authors);

        	SpannableString Date = new SpannableString(imageAndTexts1.get(position).getPubDate());
        	SpannableString Journal = new SpannableString(imageAndTexts1.get(position).getJournal());
        	String Feed="Date: "+ Date +"   Journal: "+Journal+": ";
        	// content.setSpan(new UnderlineSpan(), 0, 13, 0);
        	
        	timeFeedText.setText(Feed);
        	if(imageAndTexts1.get(position).getImgLink() !=null){
        		
       
        	URL feedImage= new URL(imageAndTexts1.get(position).getImgLink().toString());
        	if(!feedImage.toString().equalsIgnoreCase("null")){
        		HttpURLConnection conn= (HttpURLConnection)feedImage.openConnection();
            	InputStream is = conn.getInputStream();
 //           	Bitmap img = BitmapFactory.decodeStream(is);
        	}
        	 else{
             }
        		}
       
        	
        } catch (MalformedURLException e) {
       
        }
        catch (IOException e) {
        
        }

return rowView;

}

public void SortJournal(){
	List<RssFeedStructure> PlosOne =   new ArrayList<RssFeedStructure>();
	List<RssFeedStructure> Science =   new ArrayList<RssFeedStructure>();
	List<RssFeedStructure> Nature =   new ArrayList<RssFeedStructure>();
	List<RssFeedStructure> Proteomics =   new ArrayList<RssFeedStructure>();


	  List<RssFeedStructure> Else =  new ArrayList<RssFeedStructure>();
	  List<RssFeedStructure> All =  new ArrayList<RssFeedStructure>();;
	for(RssFeedStructure rss : imageAndTexts1 ){
		  if(rss.getJournal().equals("PLoS ONE")){
			  PlosOne.add(rss);
			  }
		  else if(rss.getJournal().equals("Science")){
			  Science.add(rss);
			  }
		  else if(rss.getJournal().equals("Nature")){
			  Nature.add(rss);
			  }
		  else if(rss.getJournal().equals("Proteomics")||rss.getJournal().equals("J Proteomics")){
			  Proteomics.add(rss);
			  }
		  else{
					  Else.add(rss);
			//		  imageAndTexts.remove(rss);
				  }
		}
		if(PlosOne.size()>=1){All.addAll(PlosOne);}
		if(Proteomics.size()>=1){All.addAll(Proteomics);}
		if(Science.size()>=1){All.addAll(Science);}
		if(Nature.size()>=1){All.addAll(Nature);}

		All.addAll(Else);
		imageAndTexts1=All;
}

}