package com.example.user.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailedNotepadActivity extends Activity {
    private Button checkCompletion_Button;
    private EditText detailed_EditText;
    private final String CLASSNAME = "DetailedNotepadActivity";
    private DBHelper dbHelper;
    private int updatePosition;
    private String beforeModifyData;
    private String selectedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_notepad);
        checkCompletion_Button = (Button)findViewById(R.id.checkCompletion_Button);
        checkCompletion_Button.setOnClickListener(this::checkCompletion_ButtonClick);
        detailed_EditText = (EditText)findViewById(R.id.detailed_EditText);

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

    private void getMainActivityData(){
        Intent intent = getIntent();
        if(intent != null){
            final String DATA_KEY = "DATAKEY";
            final String POSITION_KEY = "POSITIONKEY";
            selectedItemIndex = intent.getExtras().getString(DATA_KEY);
            int position = intent.getExtras().getInt(POSITION_KEY);

            updatePosition = position+1;

            Log.i("String", selectedItemIndex);
            Log.i("Int", String.valueOf(position));
            detailed_EditText.setText(selectedItemIndex);
        }
    }
    private void checkCompletion_ButtonClick(View view) {

        dbHelper = DBHelper.getInstance(this);
        //해당 id 찾음
        int id = dbHelper.selectId(selectedItemIndex);
        System.out.println(id);

        String updateData = detailed_EditText.getText().toString();

        dbHelper.updateDataTableItem(updateData, id);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
