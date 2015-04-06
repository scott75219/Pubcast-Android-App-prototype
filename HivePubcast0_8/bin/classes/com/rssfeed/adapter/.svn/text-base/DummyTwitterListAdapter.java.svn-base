package com.rssfeed.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.rssfeed.R;
import com.rssfeed.helper.MySQLiteHelper;
import com.rssfeed.helper.RssFeedStructure;
import com.rssfeed.twitter.Tweet;
import com.rssfeed.twitter.TweetLite;
import com.rssfeed.twitter.Twitter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ParseException;
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

public class DummyTwitterListAdapter extends ArrayAdapter<TweetLite> {
	List<TweetLite> imageAndTexts1 =null;
	List<TweetLite> Final_Tweet =null;

	HashMap<String,Integer> Copies= new HashMap<String,Integer>();

	public DummyTwitterListAdapter(Activity activity,List<TweetLite> tweets) {
		super(activity, 0, tweets);
	    imageAndTexts1=tweets;
				Log.i("In public", String.valueOf(imageAndTexts1.size()));
				
}


@Override
public View getView(int position, View convertView, ViewGroup parent) {
	
	Log.i("In getView", "Here");

	Activity activity = (Activity) getContext();
	LayoutInflater inflater = activity.getLayoutInflater();


	View rowView = inflater.inflate(R.layout.twitteradapter_layout, null);
	TextView tweet = (TextView) rowView.findViewById(R.id.feed_text);
	ImageView retweet= (ImageView) rowView.findViewById(R.id.retweeticon);
	TextView timeFeedText = (TextView) rowView.findViewById(R.id.feed_updatetime);
	TextView user = (TextView) rowView.findViewById(R.id.first_line);
	final String TWITTER="EEE MMM dd yyyy";
	SimpleDateFormat sf = new SimpleDateFormat(TWITTER,Locale.ENGLISH);
	  Date Thisdate=null;
	String User=imageAndTexts1.get(position).getName();
	String rawdate=imageAndTexts1.get(position).getDateCreated();
	try {
		 Thisdate=getTwitterDate(rawdate);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String date=sf.format(Thisdate);
	 tweet.setText(imageAndTexts1.get(position).getText());
	timeFeedText.setText(String.valueOf(date));
	user.setText(imageAndTexts1.get(position).getName());
	String str = imageAndTexts1.get(position).getText().substring(0, 2);
		if(!str.equals("RT")){
			retweet.setVisibility(View.INVISIBLE);}
	
	return rowView;

}


public static Date getTwitterDate(String date) throws ParseException, java.text.ParseException {

	  final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
	  SimpleDateFormat sf = new SimpleDateFormat(TWITTER,Locale.ENGLISH);
	  sf.setLenient(true);
	  return sf.parse(date);
	  
}
}