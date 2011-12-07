package com.cs50.hotpotato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cs50.hotpotato.R;
import com.cs50.hotpotato.ShowGame;

import android.app.Activity;
import android.content.Intent;
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
		final Button login = (Button)findViewById(R.id.login_button);

		// this is the action listener
		login.setOnClickListener( new OnClickListener()
		{

			public void onClick(View viewParam)
			{
				// this gets the resources in the xml file and assigns it to a local variable of type EditText
				EditText usernameEditText = (EditText) findViewById(R.id.txt_username);
				EditText passwordEditText = (EditText) findViewById(R.id.txt_password);

				// the getText() gets the current value of the text box
				// the toString() converts the value to String data type
				// then assigns it to a variable of type String
				final String sUserName = usernameEditText.getText().toString();

				// this just catches the error if the program cant locate the GUI stuff
				if(usernameEditText == null || passwordEditText == null || !checkUser(sUserName))
				{
					Toast.makeText(HotPotatoActivity.this, "Couldn't find the username", 
							Toast.LENGTH_SHORT).show();
				}
				else
				{
					
					login.setOnClickListener(new View.OnClickListener() 
					{
						public void onClick(View arg0) 
						{
							Intent nextScreen = new Intent(getApplicationContext(), ShowGame.class);
							
							//Sending data to another Activity
							nextScreen.putExtra("name", sUserName);

							// starting new activity
							startActivity(nextScreen);

						}
					}); 

				}
			}
		});
	}

	private boolean checkUser(String username)
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

		for (Element oneRow : rows)
		{
			String temp = oneRow.toString();

			// get starting location of username
			int startName = temp.indexOf("username") + 21;		

			// get ending location of username
			String tempUser = temp.substring(startName);
			int endName = tempUser.indexOf("&quot");

			String oneUser = tempUser.substring(0, endName);

			/*
			// get starting location of password
			int startPassword = temp.indexOf("passwordHash") + 25;

			// get ending location of password
			String tempPass = temp.substring(startPassword);
			int endPassword = tempPass.indexOf("&quot");

			String oneHash = tempPass.substring(0, endPassword);
			 */

			// check if username exists, log in if it does
			if (oneUser.equals(username))
			{	
/*				TextView tv = new TextView(this);
				tv.setText("Welcome " + oneUser);
				setContentView(tv); 

*/				//	Toast.makeText(HotPotatoActivity.this, "Welcome" + oneUser,
				//			Toast.LENGTH_SHORT).show();

				return true;
			}
		}

		return false;
	}
}
