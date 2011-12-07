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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View.OnClickListener;

public class HotPotatoActivity extends Activity 
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get the button resource in the xml file and assign it to a local variable of type Button
		Button launch = (Button)findViewById(R.id.login_button);

		// this is the action listener
		launch.setOnClickListener( new OnClickListener()
		{

			public void onClick(View viewParam)
			{
				// this gets the resources in the xml file and assigns it to a local variable of type EditText
				EditText usernameEditText = (EditText) findViewById(R.id.txt_username);
				EditText passwordEditText = (EditText) findViewById(R.id.txt_password);

				// the getText() gets the current value of the text box
				// the toString() converts the value to String data type
				// then assigns it to a variable of type String
				String sUserName = usernameEditText.getText().toString();
				String sPassword = passwordEditText.getText().toString();

				// this just catches the error if the program cant locate the GUI stuff
				if(usernameEditText == null || passwordEditText == null)
				{
					Toast.makeText(HotPotatoActivity.this, "Couldn't find the 'txt_username' or 'txt_password'", 
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					// display the username and the password in string format
					Toast.makeText(HotPotatoActivity.this, "Logging in",
							Toast.LENGTH_SHORT).show();
				}
			}

/*			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			} */
		}

				); // end of launch.setOnclickListener
	}


	/*
        String username;
        String password;

        String hash = getHash(password);
        checkPassword(username, hash);

        TextView tv = new TextView(this);

        Button testButton = (Button) findViewById(R.id.button);


        button.setOnClickListener(new OnClickListener() 
        	{
            public void onClick(View v) 
            {
                // Perform action on clicks
    			tv.setText("please work");
    			setContentView(tv);
            }
        });

       myButton.setOnClickListener(new Button_Clicker());*/

/*
   class Button_Clicker implements Button.OnClickListener
   {
   @Override
       public void onClick(View v) {

              if(v==myButton)
          {
                   Toast.makeText(v.getContext(), "Hello!! button Clicked", Toast.LENGTH_SHORT).show();

          }    
   }
   }*/


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