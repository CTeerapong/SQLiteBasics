package com.example.sqlitebasics;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MyHelper dbHelper;
    private SQLiteDatabase db;
    SimpleCursorAdapter adapter;
    long del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = readAllData();

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{MyHelper.COL_NAME, MyHelper.COL_PHONE_NAMBER},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                del = id;
                new AlertDialog.Builder(MainActivity.this)
                .setTitle("คุณต้องการลบข้อมูลใช่ไหม?")

                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.execSQL("DELETE FROM contacts WHERE _id ="+ del);
                                Cursor cursor = readAllData();
                                adapter.changeCursor(cursor);

                                 /*db.delete(MyHelper.TABLE_NAME,6
                                    String.valueOf(id),
                                    new String[] {""});*/
                            }
                        })
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();

            }
        });


        Button btnInsert = (Button) findViewById(R.id.insert_button);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, 123);
            }
        });

        //กำหนดการทำงานปุ่มDelete
       /* Button btnDelete = (Button) findViewById(R.id.delete_button);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ลบทุกแถวที่มี0สามตัวอยู่ข้างหน้า
                db.delete(MyHelper.TABLE_NAME,
                        MyHelper.COL_PHONE_NAMBER + "LIKE ?",
                        new String[] {"000%"});


                //db.execSQL("DELETE FROM contacts WHERE phone_number LIKE '000%'");
                Cursor cursor = readAllData();
                adapter.changeCursor(cursor);
            }
        });*/
    }

    private Cursor readAllData() {
        String[] columns = {MyHelper.COL_ID, MyHelper.COL_NAME, MyHelper.COL_PHONE_NAMBER};

        Cursor cursor = db.query(MyHelper.TABLE_NAME, columns, null, null, null, null, null);

        return cursor;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                String userName = data.getStringExtra(MyHelper.COL_NAME);
                String userPhoneNumber = data.getStringExtra(MyHelper.COL_PHONE_NAMBER);

                ContentValues cv = new ContentValues();
                cv.put(MyHelper.COL_NAME, userName);
                cv.put(MyHelper.COL_PHONE_NAMBER, userPhoneNumber);
                db.insert(MyHelper.TABLE_NAME, null, cv);

                Cursor cursor = readAllData();
                adapter.changeCursor(cursor);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
