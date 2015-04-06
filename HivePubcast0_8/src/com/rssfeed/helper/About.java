package com.rssfeed.helper;

import com.rssfeed.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Activity {


public String License="We have chosen to apply the Creative Commons Attribution-NonCommercial-NoDerivs (http://creativecommons.org/licenses/by-nd/3.0/) License to all parts ( http://wiki.creativecommons.org/Data) of our databases, datasets and tools. This means that you are free to copy, distribute, display non-access controlled databases, datasets and tools in all legislations, provided you give us credit. If you wish to use this work for commercial purposes please contact us. If you intend to distribute a modified version of one of our databases, datasets or tools you must ask us for permission first."+"\n";
public String Privacy="Data submitted through this website will never be shared with any other third parties. We reserve the right to use information about visitors (IP addresses), date/time visited, page visited, referring website, etc. for site usage statistics and to improve our services."+"\n";
public String Disclaimer="We make no warranties regarding the correctness of the data, and disclaim liability for damages resulting from its use. We cannot provide unrestricted permission regarding the use of the data, as some data may be covered by patents or other rights."+"\n";


public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    TextView LinkButton = (TextView) findViewById(R.id.Contact);
    LinkButton.setOnClickListener(new View.OnClickListener() {          
            public void onClick(View v) {
                // TODO Auto-generated method stub               
            	Uri uri = Uri.parse("http://hive.biochemistry.gwu.edu/dna.cgi?cmd=contact");
                                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openBrowser);
            }
        });
    
   
	TextView Lic = (TextView) findViewById(R.id.License);
	TextView Priv = (TextView) findViewById(R.id.Privacy);
	TextView Disc = (TextView) findViewById(R.id.Disclaimer);

	Lic.setText(License);
	Priv.setText(Privacy);
	Disc.setText(Disclaimer);
	}
}
