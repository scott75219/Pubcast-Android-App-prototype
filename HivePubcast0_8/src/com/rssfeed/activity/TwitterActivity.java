package com.rssfeed.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rssfeed.R;
import com.rssfeed.adapter.DummyTwitterListAdapter;
import com.rssfeed.adapter.RssReaderListAdapter;
import com.rssfeed.adapter.TwitterListAdapter;
import com.rssfeed.helper.About;
import com.rssfeed.helper.MySQLiteHelper;
import com.rssfeed.helper.RssFeedStructure;
import com.rssfeed.twitter.Authenticated;
import com.rssfeed.twitter.Tweet;
import com.rssfeed.twitter.TweetLite;
import com.rssfeed.twitter.Twitter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Demonstrates how to use a twitter application keys to access a user's timeline
 */
public class TwitterActivity extends Activity {

	private ListActivity activity;
	final static String[] Names={"bffo","Alexbateman1","ewanbirney","wakibbe","eric_lander","newscientist","openscience","NatureRevCancer"};
	final static String ScreenName = "Alexbateman1";
	final static String LOG_TAG = "rnc";
	private TwitterListAdapter _adapter;
	private DummyTwitterListAdapter dummy_adapter;

	List<String> results = new ArrayList<String>();
	ListView _rssFeedListView;
	Twitter twits = null;
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.rssfeedreaderactivity);  
		_rssFeedListView = (ListView)findViewById(R.id.rssfeed_listview);
		  context=getBaseContext();
		  
		  ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		// System.out.println("TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (networkInfo == null) {
				List<TweetLite> OldTweets =new ArrayList<TweetLite>();
		          MySQLiteHelper db = new MySQLiteHelper(this.context); 
		          OldTweets=db.getAllTweets();
		        	Log.d("retreiving","retreiving");

				dummy_adapter = new DummyTwitterListAdapter(TwitterActivity.this,OldTweets);
		       _rssFeedListView.setAdapter(dummy_adapter);
		        }
		downloadTweets();
	}

	// download twitter timeline after first checking to see if there is a network connection
	public void downloadTweets() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			new DownloadTwitterTask(context).execute(Names);
		}
	}

	// Uses an AsyncTask to download a Twitter user's timeline
	private class DownloadTwitterTask extends AsyncTask<String, Void, String> {
		private ProgressDialog Dialog;
		private Context context;
		final static String CONSUMER_KEY = "2cZK0pUibXIuxXgAkNmFRA";
		final static String CONSUMER_SECRET = "WAfvx5NNnzvXZXs3aKOIfLwMJw6DuyiV4mMh5Ye0";
		final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
		final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";
		
		@Override
		protected void onPreExecute() {
			Dialog = new ProgressDialog(TwitterActivity.this);
			Dialog.setMessage("Loading...");
		Dialog.show();
		}
		
		
		public DownloadTwitterTask(Context context){
		    this.context=context;
		}
		protected String doInBackground(String... screenNames) {
			String result = null;
			if (screenNames.length > 0) {
				for(int x=0;x<screenNames.length;x++){
					result = getTwitterStream(screenNames[x]);
					results.add(result);
		}
			}
			return result;
		}

		// onPostExecute convert the JSON results into a Twitter object (which is an Array list of tweets
		@Override
		protected void onPostExecute(String result) {
	          MySQLiteHelper db = new MySQLiteHelper(context); 
			  Dialog.dismiss();
			Date corrected_date = null;
			 Calendar _calendar = Calendar.getInstance(Locale.getDefault());
			  _calendar.setTime(new Date());
			  _calendar.add(Calendar.DATE, -3);
			  Date DaysAgo = _calendar.getTime();
				List<String> Alltweets=results;
				List<Tweet> Tweets =new ArrayList<Tweet>();
				Log.i("size", String.valueOf(Alltweets.size()));

			for(String r:Alltweets){
				Twitter twits = jsonToTwitter(r);
		        
				Tweets.addAll(twits);
			}
			// lets write the results to the console as well
			//	tweets.add(twits.get(0));
		      Iterator <Tweet> itr = Tweets.iterator();

		      while(itr.hasNext()) {
		          Tweet tweet = itr.next();

		    	  String date=tweet.getDateCreated();

				try {
					corrected_date = getTwitterDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				if(corrected_date.before(DaysAgo)){
					itr.remove();
					}
				
			}

		//Sort by Date
		      Collections.sort(Tweets, new Comparator<Tweet>() {
		    	    public int compare(Tweet m1, Tweet m2) {
		    	        return m1.getDateCreated().compareTo(m2.getDateCreated());
		    	    }
		    	});
		      Collections.reverse(Tweets);
  
		      _adapter = new TwitterListAdapter(TwitterActivity.this,Tweets);
	        _rssFeedListView.setAdapter(_adapter);
	        GoTwitterAccount();
	        Log.d("EXISTS!!!",String.valueOf(db.checkTwitter()));
	        db.checkTwitter();
	        if(db.checkTwitter()==false){
	        	for (Tweet t: Tweets){
	        		db.addTwitter(t);
	        	}
			}
		}

		// converts a string of JSON data into a Twitter object
		private Twitter jsonToTwitter(String result) {
			if (result != null && result.length() > 0) {
				try {
					Gson gson = new Gson();
					twits = gson.fromJson(result, Twitter.class);
				} catch (IllegalStateException ex) {
					// just eat the exception
				}
			}
			return twits;
		}

		// convert a JSON authentication object into an Authenticated object
		private Authenticated jsonToAuthenticated(String rawAuthorization) {
			Authenticated auth = null;
			if (rawAuthorization != null && rawAuthorization.length() > 0) {
				try {
					Gson gson = new Gson();
					auth = gson.fromJson(rawAuthorization, Authenticated.class);
				} catch (IllegalStateException ex) {
					// just eat the exception
				}
			}
			return auth;
		}

		private String getResponseBody(HttpRequestBase request) {
			StringBuilder sb = new StringBuilder();
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
				HttpResponse response = httpClient.execute(request);
				int statusCode = response.getStatusLine().getStatusCode();
				String reason = response.getStatusLine().getReasonPhrase();

				if (statusCode == 200) {

					HttpEntity entity = response.getEntity();
					InputStream inputStream = entity.getContent();

					BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					String line = null;
					while ((line = bReader.readLine()) != null) {
						sb.append(line);
					}
				} else {
					sb.append(reason);
				}
			} catch (UnsupportedEncodingException ex) {
			} catch (ClientProtocolException ex1) {
			} catch (IOException ex2) {
			}
			return sb.toString();
		}

		private String getTwitterStream(String screenName) {
			String results = null;

			// Step 1: Encode consumer key and secret
			try {
				// URL encode the consumer key and secret
				String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
				String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

				// Concatenate the encoded consumer key, a colon character, and the
				// encoded consumer secret
				String combined = urlApiKey + ":" + urlApiSecret;

				// Base64 encode the string
				String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

				// Step 2: Obtain a bearer token
				HttpPost httpPost = new HttpPost(TwitterTokenURL);
				httpPost.setHeader("Authorization", "Basic " + base64Encoded);
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
				String rawAuthorization = getResponseBody(httpPost);
				Authenticated auth = jsonToAuthenticated(rawAuthorization);

				// Applications should verify that the value associated with the
				// token_type key of the returned object is bearer
				if (auth != null && auth.token_type.equals("bearer")) {

					// Step 3: Authenticate API requests with bearer token
					HttpGet httpGet = new HttpGet(TwitterStreamURL + screenName);

					// construct a normal HTTPS request and include an Authorization
					// header with the value of Bearer <>
					httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
					httpGet.setHeader("Content-Type", "application/json");
					// update the results with the body of the response
					results = getResponseBody(httpGet);
				}
			} catch (UnsupportedEncodingException ex) {
			} catch (IllegalStateException ex1) {
			}
			return results;
		}
	}
	public static Date getTwitterDate(String date) throws ParseException, java.text.ParseException {

		  final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		  SimpleDateFormat sf = new SimpleDateFormat(TWITTER,Locale.ENGLISH);
		  sf.setLenient(true);
		  return sf.parse(date);
		  }
	
		
	public void GoTwitterAccount(){
		_rssFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parentAdapter, View view, int position,long id) {
              //  String user=twits.get(position).getUser().getScreenName();
               Tweet user =(Tweet) _rssFeedListView.getItemAtPosition(position);
               String name=user.getUser().getScreenName();
                Log.i("position of row", String.valueOf(position));
        		Uri uri = Uri.parse("https://twitter.com/"+name);
                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(openBrowser);
        	
        	}
        			});	
	}
}
