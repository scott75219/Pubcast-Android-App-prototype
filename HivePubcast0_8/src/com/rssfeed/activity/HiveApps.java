package com.rssfeed.activity;

import com.rssfeed.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HiveApps extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps);
        Button PubButton = (Button) findViewById(R.id.pubcast);
        Button GeneButton = (Button) findViewById(R.id.genecast);

        PubButton.setOnClickListener(new View.OnClickListener() {          
            public void onClick(View v) {
                // TODO Auto-generated method stub               
            	Uri uri = Uri.parse("https://hive.biochemistry.gwu.edu/tools/HivePubcast/HIVE_Pubcast.apk");
                                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openBrowser);
            }
        });
        
        GeneButton.setOnClickListener(new View.OnClickListener() {          
            public void onClick(View v) {
                // TODO Auto-generated method stub               
            	Uri uri = Uri.parse("https://hive.biochemistry.gwu.edu/tools/HiveGenecast/HIVEGenecast.apk");
                                Intent openBrowser = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(openBrowser);
            }
        });

        }
}
