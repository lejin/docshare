package com.example.docshare;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FollowActivity extends Activity {
	EditText followmail;
	 TextView error; 
	 private String errorMsg;
	 private String resp;
	 private String mail;
	 private String username;
	 int check;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		check=30;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            
            followmail=(EditText)findViewById(R.id.followmailid);
            error= (TextView) findViewById(R.id.followsuccess);
            Intent myIntent = getIntent(); // gets the previously created intent
            username = myIntent.getStringExtra("username");
        	Button loginclicked=(Button) findViewById(R.id.followbutton);
    		loginclicked.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {new Thread(new Runnable() {

    			     @Override
    			     public void run() {
    			      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
    			      postParameters.add(new BasicNameValuePair("follow",followmail.getText().toString()));
    			      postParameters.add(new BasicNameValuePair("username",username));

    			      mail=followmail.getText().toString();
    			      

    			      String response = null;
    			      try {
    			       response = Loginpost.executeHttpPost("http://docshare-demo.appspot.com/mfollow", postParameters);
    			       String res = response.toString();
    			       resp = res.replaceAll("\\s+", "");
    			       check=33;
    			      } catch (Exception e) {
    			       e.printStackTrace();
    			       errorMsg = e.getMessage();
    			      }
    			     }
    			 
    			    }).start();
    			   while(check!=33){
    				    //
    			   }
    			    try {
    			     if (resp.equals("1")) {
    			    	 error.setText("you are ow following"+mail);		     
    				}
    			     else {
    			      error.setText("Sorry!! Incorrect Username or Password");
    			     }
    			     if (null != errorMsg && !errorMsg.isEmpty()) {
    			      error.setText(errorMsg);
    			     }
    			    } catch (Exception e) {
    			     error.setText(e.getMessage());
    			    }
    			   }
    			  });
            
            
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
