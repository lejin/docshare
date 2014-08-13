package com.example.docshare;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import org.apache.http.util.EntityUtils;

import com.example.docshare.SimpleHttpClient;
import com.example.docshare.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SharedActivity extends Activity {
 int check;
 TextView tv;
 String username;
 String resp=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shared);
		check=25;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            tv = (TextView)findViewById(R.id.sharedfiles);
            Intent myIntent = getIntent(); // gets the previously created intent
            username = myIntent.getStringExtra("username");
		new Thread(new Runnable() {

		     @Override
		     public void run() {
		      
		      HttpResponse response = null;
		      try {
		       response = SimpleHttpClient.executeHttpGet("http://8.docshare-demo.appspot.com/jsonreply?username="+username);
		       int responseCode = response.getStatusLine().getStatusCode();
		      
		       switch(responseCode) {
		       case 200:
		       HttpEntity entity = response.getEntity();
		           if(entity != null) {
		               resp = EntityUtils.toString(entity);
		           }
		           break;
		       }
		       	check=21;
		      } catch (Exception e) {
		       e.printStackTrace();
		      }
		     }

		    }).start();
		    while(check!=21){
		    	//do nothing
		    }
		    StringBuilder filefrag=new StringBuilder();
		    try {
				JSONObject object = new JSONObject(resp);
				 JSONArray list=(JSONArray) object.getJSONArray("mainlist");
			int num=0;
				System.out.println(list.length()+ "arraylength ");
				 for(int i=0;i<list.length();i++)
		    	    {
					 num=i+1;
		    	    	JSONObject fileobject=(JSONObject) list.get(i);
		    	    	String pin=fileobject.getString("pin").toString();
						String filename=fileobject.getString("filename").toString();
		    	    	filefrag.append("\n"+num+".  "+pin+"   "+filename+"  "+"\n");
		    	    }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String filestring=filefrag.toString();
		    tv.setText(filestring);
		//System.out.println(resp);
		}
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
