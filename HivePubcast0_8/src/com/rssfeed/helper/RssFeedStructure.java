package com.rssfeed.helper;

import java.net.URL;


public class RssFeedStructure {

private int ID;
private String title;
private String abs;
private String authors;
private String affiliation;
private String PMID;
private String description;
private String imgLink;
private String pubDate;
private URL url;
private String Journal;
private String type;
//public RssFeedStructure(String title,String PMID, String pubDate,String Journal,String authors,String abs,String affiliation, String type) {
  //  super();
    //this.PMID = PMID;
    //this.title = title;
    //this.pubDate = pubDate;
    //this.Journal = Journal;
    //this.authors = authors;
    //this.affiliation = affiliation;
    //this.type=type;
    
//}

public String getAuthors() {
return authors;
}
/**
* @param feedId the feedId to set
*/
public void setAuthors(String authors) {
this.authors = authors;
}
/**
* @return the abstract
*/
public String getAbstract() {
return abs;
}
/**
* @param title the Abstract to set
*/
public void setAbstract(String abs) {
this.abs = abs;
}

public String getTitle() {
return title;
}
/**
* @param title the title to set
*/
public void setTitle(String title) {
this.title = title;
}
/**
* @return the url
*/
public URL getUrl() {
return url;
}
/**
* @param url the url to set
*/
public void setUrl(URL url) {
this.url = url;
}
/**
* @param description the description to set
*/
public void setDescription(String description) {
this.description = description;


if (description.contains("<img ")){
String img = description.substring(description.indexOf("<img "));
String cleanUp = img.substring(0, img.indexOf(">")+1);
img = img.substring(img.indexOf("src=") + 5);
int indexOf = img.indexOf("'");
if (indexOf==-1){
indexOf = img.indexOf("\"");
}
img = img.substring(0, indexOf);

setImgLink(img);

this.description = this.description.replace(cleanUp, "");
}
}
/**
* @return the description
*/
public String getDescription() {
return description;
}
/**
* @param pubDate the pubDate to set
*/
public void setPubDate(String pubDate) {
this.pubDate = pubDate;
}
/**
* @return the pubDate
*/
public String getPubDate() {
return pubDate;
}
/**
* @param encodedContent the encodedContent to set
*/
public void setJournal(String Journal) {
this.Journal = Journal;
}
/**
* @return the encodedContent
*/
public String getJournal() {
return Journal;
}
/**
* @param imgLink the imgLink to set
*/
public void setImgLink(String imgLink) {
this.imgLink = imgLink;
}
/**
* @return the imgLink
*/
public String getImgLink() {
return imgLink;
}

public void setAffiliation(String affiliation) {
	this.affiliation=affiliation;
}

public String getAffiliation(){
	return affiliation;
}

public void setPMID(String PMID) {
	this.PMID=PMID;
}

public String getPMID(){
	return PMID;
}

public void setType(String type) {
	this.type=type;
}

public String getType(){
	return type;
}







}


