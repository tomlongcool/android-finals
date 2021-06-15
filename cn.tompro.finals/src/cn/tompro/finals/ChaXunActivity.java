package cn.tompro.finals;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChaXunActivity extends Activity {
	
	EditText ed1,ed2;
	String name,value;
	Button btn;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cha_xun);
		ed1=(EditText)findViewById(R.id.searchName);
		ed2=(EditText)findViewById(R.id.searchValue);
		btn=(Button)findViewById(R.id.searchBtn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=ed1.getText().toString();
				value=ed2.getText().toString();
				Intent intent=getIntent();
				intent.putExtra("name", name);
				intent.putExtra("value",value);
				setResult(201,intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cha_xun, menu);
		return true;
	}

}
