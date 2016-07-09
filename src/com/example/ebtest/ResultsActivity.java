//package com.example.ebtest;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ImageView;
//
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//
//public class ResultsActivity extends Activity {
//    String FILENAME = "rewards_file";
//	 OnSharedPreferenceChangeListener listener;
//	 ImageView imageViewStatic1;
//	 ImageView imageViewResults1;
//	 String preferencesGroupItems;
//	 PicsResource p;
//    Handler handler;
//    private void writeSettingsToFile(Context ctx, String textToWrite) throws IOException
//    {
//
//        FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        fos.write(textToWrite.getBytes());
//        fos.close();
//    }
//
//    private String readSettingsFromFile(Context ctx) throws IOException, FileNotFoundException
//    {
//        FileInputStream fis;
//        fis = ctx.openFileInput(FILENAME);
//        StringBuffer fileContent = new StringBuffer("");
//        byte[] buffer = new byte[1024];
//
//        while (fis.read(buffer) != -1) {
//            fileContent.append(new String(buffer));
//        }
//        String temp = fileContent.toString();
//        return temp;
//        //return fileContent.toString().equalsIgnoreCase(textToCompare);
//    }
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		try{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_results);
//		imageViewStatic1 = (ImageView)findViewById(R.id.ImageView01);
//		imageViewResults1 = (ImageView)findViewById(R.id.ImageView02);
//
//		p = new PicsResource();
//		Integer[] resultsPics = p.getResultsPics();		
//		imageViewStatic1.setImageResource(resultsPics[0]);
//
//         Intent i = getIntent();
//            if(i.hasExtra("galleryplayactivity")){
//                imageViewResults1.setImageResource(resultsPics[PicsResource.resultScore+1]);
//            }else{
//                try{
//                    Integer val = Integer.valueOf(readSettingsFromFile(getApplicationContext()).trim());
//
//                    imageViewResults1.setImageResource(resultsPics[(val/5) + 1]);
//
//                }catch(Exception ex){
//                    Log.i("ResultsActivity", ex.getMessage());
//                }
//            }
//		//
//
//		
//		if(!BusinessPlatformProviders.BN)
//			p.callAds(this);
//			
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//
//        Intent intent = getIntent();
//        String message = intent.getStringExtra("blob");
//        if((message != null) && (message.equalsIgnoreCase("blob"))){
//            handler = new Handler();
//            //handler = getWindow().getDecorView().getHandler();
//            handler.postDelayed(runnable, 5000);
//        } else {
//            handler = new Handler();
//            handler.postDelayed(multipleClickActivityRunnable,5000);
//        }
//	}
//    final Runnable multipleClickActivityRunnable = new Runnable()
//    {
//        public void run()
//        {
//            finish();
//            Intent i = new Intent( getApplicationContext(),MultipleClickActivity.class);
//            startActivity(i);
//        }
//
//    };
//    final Runnable runnable = new Runnable()
//    {
//        public void run()
//        {
//            //start the new activity here.
//                    finish();
//                    Intent i = new Intent( getApplicationContext(),Blob.class);
//                    startActivity(i);
//
//        }
//    };
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//    	   super.onCreateOptionsMenu(menu);
//
//    	   //menu.add(0,1,0,"Play");           // Group
//    	   //menu.add(0,2,1,"Learn");
//    	   menu.add(0,4,1,"Settings");
//    	   menu.add(0,3,1,"Home");
//    	   
//
//    	   //It is important to return true to see the menu
//    	   return true;
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//       switch(item.getItemId()) {
//       case 1:
//       		StartSecondActivity();
//       		break;
//       case 2:
//    	   StartLearnActivity();
//    	   break;
//       case 3:
//    	   StartSettingsActivity();
//    	   break;
//       case 4:
//    	   StartLaunchPageActivity();
//    	   break;
//       }
//
//       return true;
//
//    }
//    
//    public void setSettingsListener()
//    {
//   	 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
//      	  public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
//      	    // Implementation
//      	        preferencesGroupItems = prefs.getString("aboutlist", "Contact Us");        
//      	    	if(preferencesGroupItems.equalsIgnoreCase("Contact Us")){
//      	    		//pics = a.getAlphaFloralPics();
//      	    		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//      	    		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"zdiscov@gmail.com"});
//      	    		emailIntent.setType("text/plain");
//      	    		startActivity(Intent.createChooser(emailIntent, "Send a mail ..."));
//      	    		prefs.edit().putString("aboutlist", "Facebook").commit();
//      	    	}else if(preferencesGroupItems.equalsIgnoreCase("Facebook")){
//      	    	     String myUrl = "http://www.facebook.com/pages/Fruitphabets/395489760536429";
//      	    	        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl)) ;
//      	    	        startActivity(i);
//      	    	      prefs.edit().putString("aboutlist", "Facebook").commit();
//      	    	}
//      	    	
//
//      	  }
//      	};
//
//      	settings.registerOnSharedPreferenceChangeListener(listener);
//    }
//
//    protected void StartLaunchPageActivity()
//    {
//    	finish();
//    	Intent i = new Intent(this,MultipleClickActivity.class);
//    	startActivity(i);
//    }
//    
//
//    protected void StartSettingsActivity()
//    {
//    	finish();
//    	Intent i = new Intent(this,PreferencesActivityTest.class);
//    	startActivity(i);
//    	
//    	
//    }
//    protected void StartLearnActivity()
//    {
//    	finish();
//    	Intent i = new Intent(this,GalleryView.class);
//    	startActivity(i);
//    }
//    
//    protected void StartSecondActivity()
//    {
//    	finish();
//        Intent i = new Intent(this,GalleryPlayActivity.class);
//        startActivity(i);
//
//    }
//    
//	  @Override
//	     public void onDestroy() {
//		  	 if(imageViewStatic1 != null){
//		  		 imageViewStatic1.setImageDrawable(null);		  	 
//		  		 imageViewStatic1 = null;
//		  	 }
//		  	 if(imageViewResults1 != null){
//		  		 imageViewResults1.setImageDrawable(null);		  	 
//		  		 imageViewResults1 = null;
//		  	 }
//		  	 if(p!=null){
//		  		 p.destroy();
//		  		 p=null;
//		  	 }
//		  	
//			 super.onDestroy();
//	     }
//	    @Override
//	    public void onStop()
//	    {
//
//		  	 if(imageViewStatic1 != null){
//		  		 imageViewStatic1.setImageDrawable(null);		  	 
//		  		 imageViewStatic1 = null;
//		  	 }
//		  	 if(imageViewResults1 != null){
//		  		 imageViewResults1.setImageDrawable(null);		  	 
//		  		 imageViewResults1 = null;
//		  	 }
//		  	 if(p!=null){
//		  		 p.destroy();
//		  		 p=null;
//		  	 }
//		  	finish();
//	    	super.onStop();
//	    	
//	    }
//
//		@Override
//		public void onBackPressed(){
//			StartLaunchPageActivity();
//		}
//
//
//}
