package com.rssfeed.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.rssfeed.twitter.Tweet;
import com.rssfeed.twitter.TweetLite;
import com.rssfeed.twitter.TwitterUser;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
	   // Books Table Columns names
	public static final String KEY_ID = "Id";
	public static final String KEY_PMID = "PMId";
    public static final String KEY_DATE = "Date";
    public static final String KEY_JOURNAL = "Journal";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_AUTHORS = "Authors";
    public static final String KEY_ABSTRACT = "Abstract";
    public static final String KEY_AFFILIATION = "Affiliation";
    public static final String KEY_TYPE = "Type";
    public static final String TABLE_TABS = "Tabs";
    
    public static final String 	TABLE_MANAGE="Manage";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_ITERATION = "Iteration";
    
    public static final String 	TABLE_TWITTER="Twitter";
    public static final String KEY_TWEET = "Text";
    public static final String KEY_NAME = "Name";
    public static final String KEY_USERNAME = "UserName";
    public static final String KEY_TWEETDATE = "Tweetdate";
    public static final String KEY_TID = "Id";

    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "Pubcast";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("onCreate", "database created");
        String CREATE_TABS_TABLE = "CREATE TABLE Tabs (PMId INTEGER PRIMARY KEY, Date VARCHAR, Journal VARCHAR, Title VARCHAR, Authors BLOB, Abstract BLOB, Affiliation BLOB, Type VARCHAR)";
        String CREATE_TABLE_MANAGE = "CREATE TABLE Manage (Type VARCHAR PRIMARY KEY,Location INTEGER,Iteration INTEGER)";
        String CREATE_TABLE_TWITTER = "CREATE TABLE Twitter (UserName VARCHAR, Name VARCHAR, Text BLOB PRIMARY KEY, Tweetdate VARCHAR)";

        // create books table
        db.execSQL(CREATE_TABS_TABLE);
        db.execSQL(CREATE_TABLE_MANAGE);
        db.execSQL(CREATE_TABLE_TWITTER);
        }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
       
    	db.execSQL("DROP TABLE IF EXISTS Tabs");
        db.execSQL("DROP TABLE IF EXISTS Manage");
    	db.execSQL("DROP TABLE IF EXISTS Twitter");

 
        // create fresh books table
        this.onCreate(db);
    }
    public void addArticle(RssFeedStructure rss, String Type){

    	//for logging
    	Log.v("addArticle","adding"); 

    	// 1. get reference to writable DB
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.isOpen(); 

    	Log.v("SQLiteDatabase db", "get writable"); 

    	// 2. create ContentValues to add key "column"/value
    	ContentValues values = new ContentValues();
    	
        
    	values.put(KEY_PMID, rss.getPMID());
    //	Log.d("add ID", rss.getPMID()); 

    	values.put(KEY_DATE, rss.getPubDate());
    	Log.d("add Date", rss.getPubDate()); 

    	values.put(KEY_JOURNAL, rss.getJournal());
    	Log.d("add Journal", rss.getJournal()); 

    	values.put(KEY_AUTHORS, rss.getAuthors());
    	//Log.d("add Authors", rss.getAuthors()); 

    	values.put(KEY_TITLE, rss.getTitle());
  //  	Log.d("add Title", rss.getTitle()); 

    	values.put(KEY_ABSTRACT, rss.getAbstract());
    	values.put(KEY_AFFILIATION, rss.getAffiliation());
    	
    	values.put(KEY_TYPE, Type);
    	Log.d("add Type",Type); 

    	// 3. insert
    	db.insert(TABLE_TABS, null, values); // key/value -> keys = column names/ values = column values
    	db.close(); 
    	 }
    public List<RssFeedStructure> getAll(String Type) {
        List<RssFeedStructure> rssList = new LinkedList<RssFeedStructure>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TABS;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        RssFeedStructure rss = null;
        if (cursor.moveToFirst()) {
           
        	do {
               
            	rss = new RssFeedStructure();
                rss.setPMID(cursor.getString(0));
                rss.setPubDate(cursor.getString(1));
                rss.setJournal(cursor.getString(2));
                rss.setTitle(cursor.getString(3));
                rss.setAuthors(cursor.getString(4));
                rss.setAbstract(cursor.getString(5));
                rss.setAffiliation(cursor.getString(6));

                rss.setType(cursor.getString(7));
                if(Type.equals(rss.getType())){
               // Integer.parseInt(cursor.getString(0))
                // Add book to books
                rssList.add(rss);}
            } while (cursor.moveToNext());
        }
        Collections.reverse(rssList);

    	db.close();
        return rssList;
    }
    public List<Integer> getParams(String Type) {
        List<Integer> rssList = new LinkedList<Integer>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MANAGE;
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
           
        	do {

        		String currentType=cursor.getString(0);
        		if(currentType.equals(Type)){
        		rssList.add(cursor.getInt(1));
                rssList.add(cursor.getInt(2));
        		}
            } while (cursor.moveToNext());
        }
   //     Collections.reverse(rssList);

    	db.close();
        return rssList;
    }
public boolean CheckExists(String DATE, String TYPE){
    SQLiteDatabase db = this.getReadableDatabase();

	Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TABS+ " WHERE " + KEY_DATE + "= '" + DATE + "'", null);
	Cursor c2 = db.rawQuery("SELECT * FROM " + TABLE_TABS+ " WHERE " + KEY_TYPE + "= '" + TYPE + "'", null);

	Log.v("Value of c",String.valueOf(c.getCount()));
	
	if(c.getCount()<=0|| c2.getCount()<=0)
    {
		db.close();

		return false;
    }
    else {	db.close();
return true;}
}
public boolean CheckIdExists(String PMID){
    SQLiteDatabase db = this.getReadableDatabase();

	Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TABS+ " WHERE " + KEY_PMID + "= '" + PMID + "'", null);

	Log.v("Value of c",String.valueOf(c.getCount()));
	
	if(c.getCount()<=0)
    {
		db.close();

		return false;
    }
    else {	db.close();
return true;}
}
public boolean CheckParamExists(String TYPE){
    SQLiteDatabase db = this.getReadableDatabase();

	Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MANAGE+ " WHERE " + KEY_TYPE + "= '" + TYPE + "'", null);

	Log.v("Value of c",String.valueOf(c.getCount()));
	
	if(c.getCount()<=0)
    {
		db.close();

		return false;
    }
    else {	db.close();
return true;}
}
public void DeleteAll(String Type){
    SQLiteDatabase db = this.getWritableDatabase();

 // 2. delete
    db.delete(TABLE_TABS,
            KEY_TYPE+" = ?",
            new String[] { Type });

    // 3. close
    db.close();
	}

public void deleteParams(String Type) {
	SQLiteDatabase db = this.getWritableDatabase();

	 // 2. delete
	    db.delete(TABLE_MANAGE,
	            KEY_TYPE+" = ?",
	            new String[] { Type });

	    // 3. close
	    db.close();


}
public void saveParameter(String Type, int Location, int Iteration){

	//for logging

	// 1. get reference to writable DB
	SQLiteDatabase db = this.getWritableDatabase();
	db.isOpen(); 


	// 2. create ContentValues to add key "column"/value
	ContentValues values = new ContentValues();
	
    
	values.put(KEY_TYPE, Type);
	values.put(KEY_LOCATION, Location);
	values.put(KEY_ITERATION, Iteration);

//	Log.d("add ID", rss.getPMID()); 

	
	// 3. insert
	db.insert(TABLE_MANAGE, null, values); // key/value -> keys = column names/ values = column values
	db.close(); 
	 }
public void addTwitter(Tweet tweet){

	// 1. get reference to writable DB
	SQLiteDatabase db = this.getWritableDatabase();
	db.isOpen(); 


	// 2. create ContentValues to add key "column"/value
	ContentValues values = new ContentValues();
	
	values.put(KEY_USERNAME, tweet.getUser().getScreenName());
	values.put(KEY_NAME, tweet.getUser().getName());
	values.put(KEY_TWEET, tweet.getText());
	values.put(KEY_TWEETDATE, tweet.getDateCreated());


	// 3. insert
	db.insert(TABLE_TWITTER, null, values); // key/value -> keys = column names/ values = column values
	db.close(); 
	 }
public void deleteTwtter() {
	SQLiteDatabase db = this.getWritableDatabase();

	 // 2. delete
	    db.execSQL("delete from "+ TABLE_TWITTER);
	    // 3. close
	    db.close();
}

public List<TweetLite> getAllTweets() {
	List<TweetLite> twits =new ArrayList<TweetLite>();

    // 1. build the query
    String query = "SELECT  * FROM " + TABLE_TWITTER;

    // 2. get reference to writable DB
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // 3. go over each row, build book and add it to list
    TweetLite tweet = null;

    if (cursor.moveToFirst()) {
       
    	do {
           
    		tweet = new TweetLite();
    		tweet.setScreenName(cursor.getString(0));
    		Log.v("Name", cursor.getString(0)); 
    		tweet.setName(cursor.getString(1));

    		tweet.setText(cursor.getString(2));
    		tweet.setDateCreated(cursor.getString(3));
           // Integer.parseInt(cursor.getString(0))
            // Add tweet to twits
            twits.add(tweet);
        } while (cursor.moveToNext());
    }
    Collections.reverse(twits);

	db.close();
    return twits;
}
public Boolean checkTwitter(){
    SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery("SELECT COUNT(*) FROM Twitter", null);
    c.moveToFirst();                       // Always one row returned.

	Log.d("c!!!",String.valueOf((c.getInt(0))));

	if(c.getInt(0)==0)
    {
		db.close();
		return false;
    }
    else {	db.close();
return true;}	
	
}
}
