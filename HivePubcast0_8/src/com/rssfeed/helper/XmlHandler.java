package com.rssfeed.helper;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import android.content.ContentValues;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import java.util.Date;



public class XmlHandler extends DefaultHandler {
public RssFeedStructure feedStr = new RssFeedStructure();
private List<RssFeedStructure> rssList = new ArrayList<RssFeedStructure>();
private List<RssFeedStructure> PlosOne = new ArrayList<RssFeedStructure>();
private List<RssFeedStructure> Else = new ArrayList<RssFeedStructure>();

Context context;

private int articlesAdded = 0;

// Number of articles to download
private static final int ARTICLES_LIMIT = 25;

StringBuffer chars = new StringBuffer();
boolean Date=false;
boolean Journal=false;
boolean Abstract=false;
boolean Author=false;
String LastName="";
String Initial="";
String AuthorList="";
String PubDate="";
String Month="";
String Day="";
String Year="";
String JournalName;
String Abs="";
String PMID="";
String Title="";
String Affiliation="";
String  DateFormat="";
//Calendar currentDate = Calendar.getInstance();
public void startElement(String uri, String localName, String qName, Attributes atts) {
chars = new StringBuffer();

if (localName.equalsIgnoreCase("PubMedPubDate")){
	Date=true;
}

if (localName.equalsIgnoreCase("Journal")){
	Journal=true;
}
if (localName.equalsIgnoreCase("Abstract")){
	Abstract=true;
}
if (localName.equalsIgnoreCase("AuthorList")){
	Author=true;
}

}
public void endElement(String uri, String localName, String qName) throws SAXException {
if (localName.equalsIgnoreCase("ArticleTitle"))
{
	Title=chars.toString();
	feedStr.setTitle(chars.toString());
}
else if (localName.equalsIgnoreCase("Affiliation"))
{
	feedStr.setAffiliation(chars.toString());
	Affiliation=chars.toString();
}
else if (localName.equalsIgnoreCase("PMID"))
{
	feedStr.setPMID(chars.toString());
	PMID=chars.toString();
	Log.d("add ID", PMID); 

}
else if (Abstract)
{
	if (localName.equalsIgnoreCase("AbstractText")){
		Abs=Abs+chars.toString()+"\n"+"\n";
		}
	else if (localName.equalsIgnoreCase("Abstract")){
		Abstract=false;
		feedStr.setAbstract(Abs);
		}
	

}
else if (Date)
{

	if (localName.equalsIgnoreCase("Year")){
		Year=chars.toString();}
	else if(localName.equalsIgnoreCase("Month")){
		Month=chars.toString();}
	else if(localName.equalsIgnoreCase("Day")){
		Day=chars.toString();
		PubDate=Month+"/"+Day+"/"+Year;
	feedStr.setPubDate(PubDate);
	Date=false;}
	
}
else if (Journal)
{

	if (localName.equalsIgnoreCase("ISOAbbreviation")){
	feedStr.setJournal(chars.toString());
	Journal=false;
	}
}


else if (Author)	
{
	if (localName.equalsIgnoreCase("LastName")){
		LastName=chars.toString();}
	else if (localName.equalsIgnoreCase("Initials")){
		Initial=chars.toString();
		AuthorList=AuthorList+LastName+" "+Initial+", ";
	}
		else if(localName.equalsIgnoreCase("AuthorList")){
			AuthorList = AuthorList.substring(0, AuthorList.length()-2);
		feedStr.setAuthors(AuthorList);
		Author=false;
		}
}

else if (qName.equalsIgnoreCase("PubmedArticleSet"))
	
{
	
}

if (localName.equalsIgnoreCase("PubmedArticle")) {

//if(feedStr.getAbstract()!=null){
rssList.add(feedStr);
//articlesAdded++;}

if (articlesAdded >= ARTICLES_LIMIT)
{
throw new SAXException();
}
}
}

public void characters(char ch[], int start, int length) {
chars.append(new String(ch, start, length));
}



public List<RssFeedStructure> getLatestArticles(String feedUrl,  String Type) {
URL url = null;
try {

SAXParserFactory spf = SAXParserFactory.newInstance();
SAXParser sp = spf.newSAXParser();
XMLReader xr = sp.getXMLReader();
url = new URL(feedUrl);
xr.setContentHandler(this);
xr.parse(new InputSource(url.openStream()));
} catch (IOException e) {
} catch (SAXException e) {

} catch (ParserConfigurationException e) {

}
int ID =Integer.parseInt(PMID);

//Log.v("Id:",String.valueOf(rssList.size()));
//Log.v("Id:",String.valueOf(rssList.size()));

return rssList;
}


}
