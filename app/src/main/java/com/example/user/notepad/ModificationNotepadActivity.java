package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ModificationNotepadActivity.class
 * 메모수정 Activity
 */
public class ModificationNotepadActivity extends Activity {

    private Button modificationButton;
    private EditText detailedNote_EditText;

    private DBHelper dbHelper;

    private String selectedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notepad);
        modificationButton = (Button) findViewById(R.id.modificationButton);
        modificationButton.setOnClickListener(this::modificationButtonClick);
        detailedNote_EditText = (EditText) findViewById(R.id.detailedNote_EditText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMainActivityData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMainActivityData();
    }

    private void getMainActivityData() {
        Intent intent = getIntent();
        if (intent != null) {
            final String DATA_KEY = "DATA_KEY";
            selectedItemIndex = intent.getExtras().getString(DATA_KEY);

            Log.i("String", selectedItemIndex);
            detailedNote_EditText.setText(selectedItemIndex);
        } else
            Toast.makeText(getApplicationContext(), "Intent 가 null 입니다.", Toast.LENGTH_LONG).show();
    }

    private void modificationButtonClick(View view) {
        dbHelper = new DBHelper(this);
        //해당 id 찾음

        int findId = findIdFromDatabaseTable(selectedItemIndex);
        if (findId == -1) {
            Toast.makeText(getApplicationContext(), "수정 오류", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            String upDateTextStr = detailedNote_EditText.getText().toString();
            dbHelper.updateDataTableItem(upDateTextStr, findId);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "수정 성공", Toast.LENGTH_LONG).show();
        }
    }

    //
    private int findIdFromDatabaseTable(String selectedItemIndex) {
        int id = 0;
        Log.i("selectedItemIndex", "/" + selectedItemIndex + "/");
        id = dbHelper.getDatabaseRowId(selectedItemIndex);
        //id가 0이라면 데이터가 없음
        if (id == 0)
            return -1;

        else
            return id;

    }

}
