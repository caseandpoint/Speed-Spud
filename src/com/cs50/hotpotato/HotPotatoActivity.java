package com.cs50.hotpotato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HotPotatoActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        String username;
        String password;
        
        String hash = getHash(password);
        checkPassword(username, hash);
    }
    
    private String getHash(String password)
    {
    	String hash = "";
    	
    	Document doc = null;
    	try
    	{
    		doc = Jsoup.connect("http://speedspud.com/android/hash.php").get();
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    	
    	Element hashLine = doc.getElementById("hash");
    	hash = hashLine.toString();
    	
    	return hash;
    }
    
    private boolean checkPassword(String username, String hash)
    {
        Document doc = null;
		try 
		{
			doc = Jsoup.connect("http://speedspud.com/android/userInfo.php/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Elements rows = doc.select("p");
		List<String> userInfo = new ArrayList<String>();
		
		// pdfList.add(packagename.text());
		
		for (Element oneRow : rows)
		{
			String temp = oneRow.toString();
			
			// get starting location of username
			int startName = temp.indexOf("username") + 21;		

			// get ending location of username
			String tempUser = temp.substring(startName);
			int endName = tempUser.indexOf("&quot");
			
			String oneUser = tempUser.substring(0, endName);
			
			// get starting location of password
			int startPassword = temp.indexOf("passwordHash") + 25;

			// get ending location of password
			String tempPass = temp.substring(startPassword);
			int endPassword = tempPass.indexOf("&quot");
			
			String oneHash = tempPass.substring(0, endPassword);

			TextView tv = new TextView(this);
			tv.setText(oneUser + oneHash);
			setContentView(tv);
		}
		
		return false;
    }
}