package com.example.user.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * RegisterNotepadActivity.class
 * 메모등록 Activity
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText note_EditText = null;
    private DBHelper dbHelper;
    private final String CLASS_NAME = "NotepadActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.i(CLASS_NAME, "onCreate");

        final int noteTextSize = getResources().getInteger(R.integer.noteTextSize);

        note_EditText = (EditText) findViewById(R.id.note_EditText);
        note_EditText.setTextSize(noteTextSize);

        dbHelper = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        note_EditText.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.registerNotepadItem){
            registerNotepadItemClick();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerNotepadItemClick() {
        String memoData = String.valueOf(note_EditText.getText());
        Log.i(CLASS_NAME, memoData);
        //Pacelable 로 Main에 보내면서 MainActivity실행 후 RecyclerView에 데이터 추가됨
        if(memoData.equals("") || memoData == null){
            Toast.makeText(getApplicationContext(), "입력한 데이터가 없습니다.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else{
            boolean insertSign = dbHelper.insertDataTableRow(memoData);
            if (insertSign == true) {
                Toast.makeText(getApplicationContext(), "메모등록성공", Toast.LENGTH_LONG).show();
                dbHelper.selectDataTable();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), "메모등록실패", Toast.LENGTH_LONG).show();
        }
    }

}//end RegisterActivity
