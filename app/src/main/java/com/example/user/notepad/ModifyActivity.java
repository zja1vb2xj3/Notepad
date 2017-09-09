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
        modifyEditText.setText("");

        final int textSize = getResources().getInteger(R.integer.noteTextSize);
        modifyEditText.setTextSize(textSize);

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
        int getDataId = findIdFromDatabaseTable(beforeModifyData);
        String wantToModifyData= modifyEditText.getText().toString();
        dbHelper.updateDataTableRow(wantToModifyData, getDataId);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int findIdFromDatabaseTable(String selectedItemIndex) {
        Log.i("selectedItemIndex", "/" + selectedItemIndex + "/");
        int id = dbHelper.getDataTableRowId(selectedItemIndex);
        Log.i("findid : ", String.valueOf(id));
        dbHelper.selectDataTable();

        return id;
    }


}
