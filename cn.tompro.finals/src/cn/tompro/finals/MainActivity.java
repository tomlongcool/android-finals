package cn.tompro.finals;

import android.os.Bundle;
import android.provider.Contacts.Intents.Insert;
import android.provider.ContactsContract.DataUsageFeedback;
import android.support.v4.widget.SimpleCursorAdapter;

import org.apache.http.conn.ManagedClientConnection;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText id,age;
	CheckBox[] hobbies=new CheckBox[6];
	Spinner depart;
	Button create,insert,update,delete,select; 
	RadioGroup rg1,rg2;
	ListView lv;
	int eid,eage;
	TomDataBaseHelper tomDataBaseHelper=new TomDataBaseHelper(MainActivity.this, "tom.db", null, 1);
	String socialStr="",hobbyStr="",departStr="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		id=(EditText)findViewById(R.id.empid);
		age=(EditText)findViewById(R.id.age);
		hobbies[0]=(CheckBox)findViewById(R.id.english);
		hobbies[1]=(CheckBox)findViewById(R.id.recite);
		hobbies[2]=(CheckBox)findViewById(R.id.dance);
		hobbies[3]=(CheckBox)findViewById(R.id.sports);
		hobbies[4]=(CheckBox)findViewById(R.id.sing);
		hobbies[5]=(CheckBox)findViewById(R.id.coding);
		rg1=(RadioGroup)findViewById(R.id.rg);
		rg2=(RadioGroup)findViewById(R.id.rg1);
		depart=(Spinner)findViewById(R.id.depart);
		lv=(ListView)findViewById(R.id.lv);
		create=(Button)findViewById(R.id.create);
		insert=(Button)findViewById(R.id.insert);
		update=(Button)findViewById(R.id.update);
		select=(Button)findViewById(R.id.select);
		delete=(Button)findViewById(R.id.delete);
		rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for(int i=0;i<rg1.getChildCount();i++) {
					RadioButton m=(RadioButton)rg1.getChildAt(i);
					if(m.isChecked()) {
						rg2.clearCheck();
					}
				}

			}
		});
		rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for(int i=0;i<rg2.getChildCount();i++) {
					RadioButton m=(RadioButton)rg2.getChildAt(i);
					if(m.isChecked()) {
						rg1.clearCheck();
					}
				}
			}
		});
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=tomDataBaseHelper.getWritableDatabase();
				Toast.makeText(MainActivity.this, "zhifa",Toast.LENGTH_LONG).show();
				if(db!=null) {
					AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
							.setTitle("数据库").setMessage("数据库tom.db创建成功，请继续操作").setPositiveButton("ok", null)
							.setNegativeButton("no", new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									System.exit(0);
									//退出程序
								}
							})
							.create();
					dialog.show();
				}
				db.close();
			}

		});
		insert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				eid=Integer.parseInt(id.getText().toString());
				eage=Integer.parseInt(age.getText().toString());
				for(int i=0;i<hobbies.length;i++) {
					if(hobbies[i].isChecked()) {
						hobbyStr+=hobbies[i].getText().toString();
					}
				}
				for(int i=0;i<2;i++) {
					if(i==0) {
						RadioGroup mGroup=rg1;
						for(int j=0;j<mGroup.getChildCount();j++) {
							RadioButton rb=(RadioButton)mGroup.getChildAt(j);
							if(rb.isChecked()) {
								socialStr=rb.getText().toString();
								break;
							}
						}
					}
					if(i==1) {
						RadioGroup mGroup=rg2;
						for(int j=0;j<mGroup.getChildCount();j++) {
							RadioButton m=(RadioButton)mGroup.getChildAt(j);
							if(m.isChecked()) {
								socialStr=m.getText().toString();
								break;
							}
						}
					}
				}
				departStr=depart.getSelectedItem().toString();
				ContentValues values=new ContentValues();
				values.put("eid", eid);
				values.put("age", eage);
				values.put("social", socialStr);
				values.put("hobby", hobbyStr);
				values.put("depart", departStr);
				SQLiteDatabase db=tomDataBaseHelper.getWritableDatabase();
				db.insert("employee", null, values);
				db.close();
			}
		});
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SQLiteDatabase db=tomDataBaseHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
				values.put("social", "群众");
				values.put("depart", "人力部");
				db.update("employee", values, "eid=?",new String[] {"160001"});
				db.close();
			}
		});
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,DeleteActivity.class);
				startActivityForResult(intent, 404);
				//404 为自定义的请求码
			}
		});
		select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,ChaXunActivity.class);
				startActivityForResult(intent,301);
				//requestCode=301
			}
		});

	};


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==404&&resultCode==200) {
			SQLiteDatabase db=tomDataBaseHelper.getWritableDatabase();
			Intent intent=getIntent();
			String name=data.getStringExtra("name");
			String value=data.getStringExtra("value");
			String where=name+"=?";
			db.delete("employee", where, new String[] {value});
			Toast.makeText(MainActivity.this, "delete success", Toast.LENGTH_LONG).show();	
			db.close();
		}
		if(requestCode==301&&resultCode==201) {
			//显示
			SQLiteDatabase db=tomDataBaseHelper.getWritableDatabase();
			String name=data.getStringExtra("name");
			String value=data.getStringExtra("value");
			String where=name+"=?";
			Cursor cursor=db.query("employee", null, where, new String[] {value}, null, null, null);
			SimpleCursorAdapter adapter=new SimpleCursorAdapter(MainActivity.this, R.layout.listview, cursor, 
					new String[] {"eid","age","social","hobby","depart"}, 
					new int[]{R.id.eid,R.id.age,R.id.social,R.id.hobby,R.id.depart});
			lv.setAdapter(adapter);
			db.close();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}    
}
