package com.example.docshare;



import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
	 
	 private String username;
	@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
            Intent myIntent = getIntent(); // gets the previously created intent
            username = myIntent.getStringExtra("username");
            Button downloadmenu=(Button)findViewById(R.id.downloadmenu);
            Button uploadmenu=(Button)findViewById(R.id.uploadmenu);
            Button followmenu=(Button)findViewById(R.id.followmenu);
            Button logoutmenu=(Button)findViewById(R.id.logoutmenu);
            Button sharedmenu=(Button)findViewById(R.id.sharedmenu);

 logoutmenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(intent);
					  
					   }
					  });


            uploadmenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
					 intent.putExtra("username",username);
						startActivity(intent);
					  
					   }
					  });
            followmenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
					 intent.putExtra("username",username);

					 startActivity(intent);
					  
					   }
					  });
            sharedmenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent(getApplicationContext(), SharedActivity.class);
					 intent.putExtra("username",username);
	
					 startActivity(intent);
					  
					   }
					  });
            
            downloadmenu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					 Intent intent = new Intent(getApplicationContext(), DownloadActivity.class);
					 intent.putExtra("username",username);
	
					 startActivity(intent);
					  
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
