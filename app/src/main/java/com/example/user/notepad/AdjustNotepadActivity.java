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
* 메모수정 Activity
*/
public class AdjustNotepadActivity extends Activity {
    private Button checkCompletion_Button;
    private EditText detailed_EditText;
    private final String CLASSNAME = "AdjustNotepadActivity";
    private DBHelper dbHelper;

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
            final String DATA_KEY = "DATA_KEY";
            selectedItemIndex = intent.getExtras().getString(DATA_KEY);

            Log.i("String", selectedItemIndex);
            detailed_EditText.setText(selectedItemIndex);
        }
        else
            Toast.makeText(getApplicationContext(),"Intent 가 null 입니다.",Toast.LENGTH_LONG).show();
    }

    private void checkCompletion_ButtonClick(View view) {
        dbHelper = DBHelper.getInstance(this);
        //해당 id 찾음

        int findId = selectItemFindId(selectedItemIndex);
        if(findId == -1){
            Toast.makeText(getApplicationContext(),"수정 오류", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            String upDateTextStr= detailed_EditText.getText().toString();
            dbHelper.updateDataTableItem(upDateTextStr, findId);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"수정 성공", Toast.LENGTH_LONG).show();
        }

    }

    private int selectItemFindId(String selectedItemIndex){
        int id = 0;
        Log.i("selectedItemIndex", "/"+selectedItemIndex+"/");
        id = dbHelper.selectId(selectedItemIndex);
        //id가 0이라면 데이터가 없음
        if(id == 0)
        return -1;

        else
            return id;

    }


}
