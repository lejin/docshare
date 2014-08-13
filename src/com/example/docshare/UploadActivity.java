package com.example.docshare;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

public class UploadActivity extends Activity {
	 
	 private static final int PICKFILE_RESULT_CODE = 1;
	 TextView textFile;
	 TextView pinview;

	 Button uploadbutton;
	 Button sharebutton;
	 String resp;
	 String FilePath;
	 String key;
	 String blobkey;
	 String username;
	 String errorMsg;
	 int check;

	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		check=20;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload);
		 Intent myIntent = getIntent(); // gets the previously created intent
         username = myIntent.getStringExtra("username");
		 Button buttonPick = (Button)findViewById(R.id.browsebutton);
		 uploadbutton = (Button)findViewById(R.id.uploadbutton);
		 sharebutton = (Button)findViewById(R.id.sharebutton);
		 sharebutton.setVisibility(View.INVISIBLE);

	       textFile = (TextView)findViewById(R.id.filepath);
	       pinview = (TextView)findViewById(R.id.pinview);

	       buttonPick.setOnClickListener(new Button.OnClickListener(){

	    	   @Override
	    	   public void onClick(View arg0) {
	    	    // TODO Auto-generated method stub

	    	    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	    	             intent.setType("file/*");
	    	       startActivityForResult(intent,PICKFILE_RESULT_CODE);
	    	    
	    	   }});
	}
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // TODO Auto-generated method stub
	  switch(requestCode){
	  case PICKFILE_RESULT_CODE:
	   if(resultCode==RESULT_OK){
	    FilePath = data.getData().getPath();
	    textFile.setText(FilePath);
	    
	   }
	   break;
	   
	  }
	  uploadbutton.setOnClickListener(new View.OnClickListener() {

		   @Override
		   public void onClick(View v) {
		    /** According with the new StrictGuard policy,  running long tasks on the Main UI thread is not possible
		    So creating new thread to create and execute http operations */
		    new Thread(new Runnable() {

		     @Override
		     public void run() {
		      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		     postParameters.add(new BasicNameValuePair("usernamesignup","dummy"));
		      //postParameters.add(new BasicNameValuePair("passwordsignup","dummy"));
		   //   postParameters.add(new BasicNameValuePair("phonesignup","dummy"));
		   //   postParameters.add(new BasicNameValuePair("emailsignup","dummy"));


		      HttpResponse response = null;
		      try {
		       response = SimpleHttpClient.executeHttpGet("http://docshare-demo.appspot.com/getuploadurl");
		       int responseCode = response.getStatusLine().getStatusCode();
		      
		       switch(responseCode) {
		       case 200:
		       HttpEntity entity = response.getEntity();
		           if(entity != null) {
		               resp = EntityUtils.toString(entity);
		           }
		           break;
		       }
		       HttpResponse response1=SimpleHttpClient.executeHttpPost(resp,postParameters,FilePath);
		      HttpEntity  entity2 = response1.getEntity();
		       

		       key =  EntityUtils.toString(entity2);
		       blobkey = key.replaceAll("\\s+", "");
		       
		       	check=21;
		      } catch (Exception e) {
		       e.printStackTrace();
		      }
		     }

		    }).start();
		    while(check!=21){
		    	//do nothing
		    }
		    if(blobkey!=null){
			       pinview.setText("Pin Number : "+blobkey);
			       }
			 sharebutton.setVisibility(View.VISIBLE);
			 sharebutton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					
					
					
					  new Thread(new Runnable() {

						  public void run() {
		    			      ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		    			      postParameters.add(new BasicNameValuePair("pinnumber",blobkey));
		    			      postParameters.add(new BasicNameValuePair("username",username));		    			      

		    			      String response = null;
		    			      try {
		    			       response = Loginpost.executeHttpPost("http://3.docshare-demo.appspot.com/mmail", postParameters);
		    			       String res = response.toString();
		    			       resp = res.replaceAll("\\s+", "");
		    			    
		    			       if (resp.equals("1")) {
			    			    	 pinview.setText("succesfully shared");		     
			    				}
			    			     else {
			    			      pinview.setText("something went wrong");
			    			     }
			    			     
		    			      } catch (Exception e) {
		    			       e.printStackTrace();
		    			       errorMsg = e.getMessage();
		    			      }
		    			     }

		    			    }).start();
					  	
					 
		    			  
		    			    
					
					
					
				}
			});

		   }
		  });
		 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload, menu);
		return true;
	}

}
