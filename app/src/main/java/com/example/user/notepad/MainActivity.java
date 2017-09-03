package com.example.user.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MainActivity.class
 */
public class MainActivity extends AppCompatActivity {

    private final String CLASS_NAME = "MainActivity";
    private final int ModifyRequest = 1;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private DBHelper dbHelper;
    private Button newNoteCreateButton;
    private NotepadModel notepadModel;

    private boolean dialogButtonSign = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(CLASS_NAME, "onCreate");

        newNoteCreateButton = (Button) findViewById(R.id.newNoteCreateButton);
        newNoteCreateButton.setOnClickListener(this::newNoteCreateButtonClick);

        dbHelper = new DBHelper(this);

        dbHelper.createTable();
        dbHelper.selectDataTable();

        notepadModel = new NotepadModel();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }
    /**
     * 새로운 메모 생성하기
     */
    private void newNoteCreateButtonClick(View view) {
        Intent intent = new Intent(this, RegisterNotepadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isNotifyWhenUseRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASS_NAME, "onResume");
        isNotifyWhenUseRecyclerView();
    }

    /**
     * RecyclerView의 재사용 알림
     */
    private void isNotifyWhenUseRecyclerView() {

        updateNotepadModel(dbHelper.getDataTableRowDatas());

        adapter = new RecyclerViewAdapter(this, dbHelper.getDataTableRowDatas());//dbHelper.getDataTableIndex 테이블 메모 데이터들

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void itemOnClick(String textViewStr, int selectedPosition) {
                Log.i("textviewStr", textViewStr);
                showDetailsNotepad(selectedPosition);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public boolean itemLongClick(String textViewStr) {
                createAskedToRemoveDialog(textViewStr);
                return dialogButtonSign;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//View를 언제 재사용하는지를 결정
        recyclerView.setAdapter(adapter);
    }//end setNotifyWhenUseRecyclerView

    /**
     * notepadModel 객체의 필드 객체 noteDatas 업데이트
     * @param datas
     */
    private void updateNotepadModel(ArrayList<String> datas){
        notepadModel.setNoteDatas(datas);
    }

    private void createAskedToRemoveDialog(String deleteData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        dialogButtonSign = false;

        builder.setTitle("선택한 메모를 삭제 하시겠습니까?")
                .setCancelable(false)

                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean deleteSign = dbHelper.deleteDataTableRow(deleteData);
                        dialogButtonSign = true;

                        isNotifyWhenUseRecyclerView();

                        if (deleteSign != false) {
                            Toast.makeText(getApplicationContext(), "정상적으로 삭제 되엇습니다.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "오류로 인하여 삭제가 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    }
                })

                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "취소 버튼을 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
                        dialogButtonSign = false;
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDetailsNotepad(int selectedPosition) {
        notepadModel.setDataPosition(selectedPosition);
        final String MODLE_KEY = "NotepadModel";
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MODLE_KEY, notepadModel);

        startActivity(intent);
    }//end showDetailedNotepad


}