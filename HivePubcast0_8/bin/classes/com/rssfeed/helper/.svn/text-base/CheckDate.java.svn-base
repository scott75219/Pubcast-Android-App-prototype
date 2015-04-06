package com.rssfeed.helper;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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




public class CheckDate extends DefaultHandler {
public RssFeedStructure feedStr = new RssFeedStructure();
private List<RssFeedStructure> rssList = new ArrayList<RssFeedStructure>();
Context context;

private int articlesAdded = 0;

// Number of articles to download
private static final int ARTICLES_LIMIT = 25;

StringBuffer chars = new StringBuffer();
boolean Date=false;
boolean Journal=false;
boolean Abstract=false;
boolean Author=false;

String PubDate="";
String Month="";
String Day="";
String Year="";


public void startElement(String uri, String localName, String qName, Attributes atts) {
chars = new StringBuffer();

if (localName.equalsIgnoreCase("PubMedPubDate")){
	Date=true;
}



}
public void endElement(String uri, String localName, String qName) throws SAXException {

if (Date)
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


if (localName.equalsIgnoreCase("PubmedArticle")) {
if (articlesAdded >= ARTICLES_LIMIT)
{
throw new SAXException();
}
}
}

public void characters(char ch[], int start, int length) {
chars.append(new String(ch, start, length));
}



public RssFeedStructure getDate(String feedUrl,  String Type) {
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
return feedStr;
}


}
