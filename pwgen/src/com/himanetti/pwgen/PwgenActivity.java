package com.himanetti.pwgen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Random;

public class PwgenActivity extends Activity {
	
	// Initialize variables
	private Button mGeneratePasswd;
	private EditText mPasswdLength;
	private EditText mGeneratedPasswd;
	private String generatedPasswd;
	public Integer passwdType;
	
	// Set different password character types
	private char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	private char[] lowercase = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 
								'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
								'w', 'x', 'y', 'z' };
	private char[] uppercase = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
								'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
								'W', 'X', 'Y', 'Z' };
	private char[] pwCharacters;
	
	// Create random generator
	static Random generator = new Random();
	
	public static char[] arrayMerge(char[]... arrays) {
		
		// Determine required size of new array
	    int count = 0;
	    for (char[] array : arrays)
	    {
	        count += array.length;
	    }
		
	    // create new array of required class
	    char[] mergedArray = new char[count];

	    // Merge each array into new array
	    int start = 0;
	    for (char[] array : arrays) {
	        System.arraycopy(array, 0, mergedArray, start, array.length);
	        start += array.length;
	    }
	    return mergedArray;
	}
			
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Create Spinner for password type selection.
        Spinner passwdTypeSpinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        passwdTypeSpinner.setOnItemSelectedListener(new customOnItemSelectedListener());
        passwdTypeSpinner.setAdapter(adapter);
        
        // Get handles for UI objects.
        mGeneratePasswd = (Button) findViewById(R.id.button1);
        mPasswdLength = (EditText) findViewById(R.id.editText1);
        mGeneratedPasswd = (EditText) findViewById(R.id.editText2);
         
        // Set what to do on UI actions
        mGeneratePasswd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Integer passwdLength = Integer.parseInt(mPasswdLength.getText().toString());
				generatedPasswd = generatePasswd(passwdType, passwdLength);
				mGeneratedPasswd.setText(generatedPasswd);
			}
		});      
        
    }
    
    protected String generatePasswd(Integer passwdType, Integer passwdLength) {
    	
    	StringBuffer output = new StringBuffer(passwdLength);
    	Integer i = 0;
		switch (passwdType) {
    		case 0:
    			pwCharacters = lowercase;	
    			break;
    		case 1:
    			pwCharacters = numbers;
    			break;
    		case 2: 
    			pwCharacters = arrayMerge(lowercase, uppercase);
    			break;
    		case 3: 
    			pwCharacters = arrayMerge(lowercase, numbers);
    			break;
    		case 4: 
    			pwCharacters = arrayMerge(uppercase, numbers);
    			break;
    		case 5: 
    			pwCharacters = arrayMerge(lowercase, uppercase, numbers);
    			break;
    	}
		while (i < passwdLength) {
			output.append(selectRandom.get(pwCharacters));
			i++;
		};

		return output.toString();
    }
    
    public class customOnItemSelectedListener implements OnItemSelectedListener {
    	
    	public void onItemSelected(AdapterView<?> parent,
    				View view, int pos, long id) {
    		passwdType = pos;
    	}

    	public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub
    		
    	}
    }
}    

class selectRandom {
	public static char get (char[] pwCharacters) {
        int rnd = PwgenActivity.generator.nextInt(pwCharacters.length);
        return pwCharacters[rnd];
    }
}
