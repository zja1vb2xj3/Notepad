package com.example.user.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

/**
 * ModificationNotepadActivity.class
 * 메모수정 Activity
 */
public class ModifyActivity extends AppCompatActivity {

    private Button modificationButton;
    private EditText modifyEditText;

    private DBHelper dbHelper;
    private NotepadModel notepadModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
//        modificationButton = (Button) findViewById(R.id.modificationButton);
//        modificationButton.setOnClickListener(this::modificationButtonClick);
        dbHelper = new DBHelper(getApplicationContext());
        modifyEditText = (EditText) findViewById(R.id.modifyEditText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String beforeModifyData = getModifyData();
        if(beforeModifyData != null){
            modifyEditText.setText(beforeModifyData);
        }
    }

    private String getModifyData() {
        if (getIntent() != null) {
            final String MODLE_KEY = "NotepadModel";
            Intent intent = getIntent();
            notepadModel = (NotepadModel)intent.getSerializableExtra(MODLE_KEY);
            String beforeModifyData = notepadModel.getNoteDatas().get(notepadModel.getDataPosition());
            return beforeModifyData;
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    //    private void modificationButtonClick(View view) {
//        dbHelper = new DBHelper(this);
//        //해당 id 찾음
//
//        int findId = findIdFromDatabaseTable(selectedItemIndex);
//        if (findId == -1) {
//            Toast.makeText(getApplicationContext(), "수정 오류", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        } else {
//            String upDateTextStr = detailedNote_EditText.getText().toString();
//            dbHelper.updateDataTableRow(upDateTextStr, findId);
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "수정 성공", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    //
//    private int findIdFromDatabaseTable(String selectedItemIndex) {
//        int id = 0;
//        Log.i("selectedItemIndex", "/" + selectedItemIndex + "/");
//        id = dbHelper.getDataTableRowId(selectedItemIndex);
//        //id가 0이라면 데이터가 없음
//        if (id == 0)
//            return -1;
//
//        else
//            return id;
//
//    }

}
