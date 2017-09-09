package com.example.user.notepad.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.notepad.Database.DBHelper;
import com.example.user.notepad.Model.NotepadModel;
import com.example.user.notepad.R;

import java.util.ArrayList;

/**
 * ModificationNotepadActivity.class
 * 메모수정 Activity
 */
public class ModifyActivity extends AppCompatActivity {
    private static final String CLASSNAME = "ModifyActivity";

    private EditText modifyEditText;

    private DBHelper dbHelper;
    private NotepadModel notepadModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        dbHelper = new DBHelper(getApplicationContext());

        modifyEditText = (EditText) findViewById(R.id.modifyEditText);

        final int textSize = getResources().getInteger(R.integer.noteTextSize);
        modifyEditText.setTextSize(textSize);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASSNAME, "onResume");
        String beforeModifyData = null;

        beforeModifyData = getModifyData();

        if(beforeModifyData != null){
            Log.i("beforeModifyData", beforeModifyData);
            modifyEditText.setText(beforeModifyData);
        }

        else{
            Toast.makeText(this, "beforeModifyData is null", Toast.LENGTH_SHORT).show();
        }
    }


    private String getModifyData() {
        Log.i("getModifyData", "Operate");
        if (getIntent() != null) {
            final String MODEL_KEY = getResources().getString(R.string.model_key);
            Intent intent = getIntent();
            notepadModel = (NotepadModel)intent.getSerializableExtra(MODEL_KEY);
            String beforeModifyData = notepadModel.getNoteDatas().get(notepadModel.getDataPosition());

            return beforeModifyData;
        }

        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modify, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.modifyNotepadCompleteItem){
            modifyNotepadCompleteItemClick();
        }

        return super.onOptionsItemSelected(item);
    }

    private void modifyNotepadCompleteItemClick() {
        dbHelper = new DBHelper(this);

        updateDBHelperDataTable();
    }

    private void updateDBHelperDataTable(){
        String beforeModifyData = notepadModel.getNoteDatas().get(notepadModel.getDataPosition());

        Log.i("beforeModifyData", beforeModifyData);

        ArrayList<Integer> idList = findDataBaseTableRowId(beforeModifyData);

        String wantToModifyData = modifyEditText.getText().toString();

        for(int i=0; i<idList.size(); i++){
            dbHelper.updateDataTableRow(wantToModifyData, idList.get(i));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private ArrayList<Integer> findDataBaseTableRowId(String selectedItemIndex) {
        Log.i("selectedItemIndex", "/" + selectedItemIndex + "/");
        ArrayList<Integer> idList = dbHelper.getDataTableRowId(selectedItemIndex);

        for(int i=0; i<idList.size(); i++)
        Log.i("findid : ", String.valueOf(idList.get(i)));
        dbHelper.selectDataTable();

        return idList;
    }


}
