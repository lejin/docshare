package com.example.docshare;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	EditText usernamelogin;
	 EditText passwordlogin;
	 TextView error; 
	 private String errorMsg;
	 private String resp;
	 private String username;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        	
		usernamelogin=(EditText) findViewById(R.id.usernamelogin);
		passwordlogin=(EditText) findViewById(R.id.passwordlogin);
		error= (TextView) findViewById(R.id.error);

		Button loginclicked=(Button) findViewById(R.id.loginclicked);
		loginclicked.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {new Thread(new Runnable() {

			     @Override
			     public void run() {
			      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			      postParameters.add(new BasicNameValuePair("usernamelogin",usernamelogin.getText().toString()));
			      postParameters.add(new BasicNameValuePair("passwordlogin",passwordlogin.getText().toString()));
			      username=usernamelogin.getText().toString();
			      String response = null;
			      try {
			       response = Loginpost.executeHttpPost("http://docshare-demo.appspot.com/mlogin", postParameters);
			       String res = response.toString();
			       resp = res.replaceAll("\\s+", "");
			       ///////////
			       try {
					     if (resp.equals("1")) {
					    	 Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
					    	 intent.putExtra("username",username);
							 startActivity(intent);			     
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
			       ///////////
			      } catch (Exception e) {
			       e.printStackTrace();
			       errorMsg = e.getMessage();
			      }
			     }

			    }).start();
			   
//			    try {
//			     if (resp.equals("1")) {
//			    	 Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
//			    	 intent.putExtra("username",username);
//					 startActivity(intent);			     
//				}
//			     else {
//			      error.setText("Sorry!! Incorrect Username or Password");
//			     }
//			     if (null != errorMsg && !errorMsg.isEmpty()) {
//			      error.setText(errorMsg);
//			     }
//			    } catch (Exception e) {
//			     error.setText(e.getMessage());
//			    }
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
