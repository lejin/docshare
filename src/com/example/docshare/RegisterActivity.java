package com.example.docshare;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	 EditText usernamesignup;
	 EditText emailsignup;
	 EditText phonesignup;
	 TextView error; 
	 EditText passwordsignup;
	 String response;
	 String resp;
	 int check;
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        check=49;
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
            usernamesignup=(EditText) findViewById(R.id.usernamesignup);
            emailsignup=(EditText) findViewById(R.id.emailsignup);
            phonesignup=(EditText) findViewById(R.id.phonesignup);
            passwordsignup=(EditText) findViewById(R.id.passwordsignup);
            error= (TextView) findViewById(R.id.registermsg);
            Button pressed=(Button)findViewById(R.id.signupclicked);
            pressed.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					 new Thread(new Runnable() {

					     @Override
					     public void run() {
					      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					      postParameters.add(new BasicNameValuePair("usernamesignup",usernamesignup.getText().toString()));
					      postParameters.add(new BasicNameValuePair("passwordsignup",passwordsignup.getText().toString()));
					      postParameters.add(new BasicNameValuePair("phonesignup",phonesignup.getText().toString()));
					      postParameters.add(new BasicNameValuePair("emailsignup",emailsignup.getText().toString()));
					     
					      try {
					       response=Registerpost.executeHttpPost("http://docshare-demo.appspot.com/mregister", postParameters);
					      
					       String res = response.toString();
	    			       resp = res.replaceAll("\\s+", "");
	    			       check=50;
					      } catch (Exception e) {
					       e.printStackTrace();
					      }
					     }

					    }).start(); 
					 
					 //if (resp.equals("1")) {
						 error.setText("succesfully registerd");
					// }
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
