package com.cs50.hotpotato;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HotPotatoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        Document doc = null;
		try {
			doc = Jsoup.connect("http://speedspud.com/android/userInfo.php/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element rowzero = doc.getElementById("0");
		String temp = rowzero.toString();
		
		// get starting location of username
		int startName = temp.indexOf("username");		
		startName = startName + 20;
		
		// get ending location of username
		String tempUser = temp.substring(startName);
		int endName = tempUser.indexOf("&quot");
		
		// get starting location of password
		int startPassword = temp.indexOf("passwordHash");
		startPassword = startPassword + 5; //WHAT?
		
		// get ending location of password
		String tempPass = temp.substring(startPassword);
		int endPassword = tempPass.indexOf("&quot");
		
		// GO FROM USERNAME TO END -> make substring tempname
		// THEN search for firth &quot thing -> this will be the end of the username
		//Same for password
		
		// get username and password hash
		String username = temp.substring(startName, endName);
		String passwordHash = temp.substring(startPassword, endPassword);
		
		TextView tv = new TextView(this);
		tv.setText(temp);
		//tv.setText(startName);    
		setContentView(tv);

    }
}