package com.envenomedsoftware.padaccountswitcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends Activity {
	
	View backupPrompt;
	Spinner backupSelection;
	ArrayAdapter<String> accountsAdapter;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        backupPrompt = getLayoutInflater().inflate(R.layout.backup_dialog, null);
        backupSelection = (Spinner) findViewById(R.id.accounts_spinner);
        accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backupSelection.setAdapter(accountsAdapter);
        
        String path = getFilesDir().getAbsolutePath();
        Button btn = (Button) findViewById(R.id.btn_delete);
        btn.setText(path);
	}
	
	public void backup(View view) {
		if(alert == null) {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("Backup Account");
	        builder.setView(backupPrompt);
	        builder.setPositiveButton("Backup", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   EditText accountNameEdit = (EditText) backupPrompt.findViewById(R.id.backup_name);
	                	   String accountName = accountNameEdit.getText().toString();
	                	   File storage = new File(getFilesDir(), accountName);
	                	   storage.mkdir();
	                	   File testData = new File(storage, "test.txt");
	                	   try {
	                		   FileOutputStream out = new FileOutputStream(testData);
	                		   out.write("Generated File".getBytes());
	                		   out.close();
	                	   } catch (FileNotFoundException e) {
	                		   e.printStackTrace();
	                	   } catch (IOException e) {
	                		   e.printStackTrace();
						}
	                       accountsAdapter.add(accountName);
	                       accountsAdapter.notifyDataSetChanged();
	                   }
	               });
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       
	                   }
	               });
	        alert =  builder.create();
		}
        alert.show();
	}
	
}
