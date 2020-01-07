package com.example.teachermanagement;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ResisterStudentActivity extends AppCompatActivity
{
    public static final String INFO_NEWSTUDENT = "PrefsFile";
    private String newStudentName;

    private ArrayAdapter<String> subjectItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister_student);

        //과목 추가 초기화
        Spinner subjectSpinner = (Spinner) findViewById(R.id.SubjectSpinner);
        subjectItems = new ArrayAdapter<String>(ResisterStudentActivity.this, R.layout.support_simple_spinner_dropdown_item);
        subjectItems.add("C언어");
        subjectItems.add("파이썬");
        subjectSpinner.setAdapter(subjectItems);

        //이메일 선택 리스트 초기화
        Spinner emailSpinner = (Spinner)findViewById(R.id.EmailSpinner);
        ArrayAdapter<CharSequence> emailItems = ArrayAdapter.createFromResource(this,R.array.emailaddress_array,R.layout.support_simple_spinner_dropdown_item);
        emailSpinner.setAdapter(emailItems);

        //공유 프레퍼런스 읽기
        EditText value = (EditText)findViewById(R.id.NameEditText);
        SharedPreferences settings = getSharedPreferences(INFO_NEWSTUDENT,0);
        newStudentName= settings.getString("newStudentName","");
        value.setText(newStudentName);
    }
    @Override
    protected void onStop()
    {
        super.onStop();

        //이름 공유프레퍼런스 에 쓰기
        SharedPreferences settings = getSharedPreferences(INFO_NEWSTUDENT,0);
        SharedPreferences.Editor editor = settings.edit();
        EditText value = (EditText)findViewById(R.id.NameEditText);
        newStudentName = value.getText().toString();
        editor.putString("newStudentName",newStudentName);
        editor.commit();
    }

    //학생 등록중 과목 추가시
    public void onClick_AddSubject(View v)
    {
        final Dialog subjectAddDialog = new Dialog(this);
        subjectAddDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        subjectAddDialog.setTitle("과목추가");
        subjectAddDialog.setContentView(R.layout.subjectadd_dialog);

        //과목 추가 액티비티 추가버튼
        Button addButton = (Button) subjectAddDialog.findViewById(R.id.AddButton);
        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText e = (EditText)subjectAddDialog.findViewById(R.id.SubjectAddEditText);
                subjectItems.add(e.getText().toString());
                subjectAddDialog.dismiss();
            }
        });

        //과목 추가 액티비티 취소
        Button cancelButton = (Button) subjectAddDialog.findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subjectAddDialog.dismiss();
            }
        });

        subjectAddDialog.show();
    }

    public void onClick_RemoveSubject(View v)
    {
        final Dialog subjectRemoveDialog = new Dialog(this);
        subjectRemoveDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        subjectRemoveDialog.setTitle("과목삭제");
        subjectRemoveDialog.setContentView(R.layout.subjectremove_dialog);

        Spinner subjectSpinner = (Spinner) subjectRemoveDialog.findViewById(R.id.SubjectSpinner);
        subjectSpinner.setAdapter(subjectItems);

        //Button removeButton = (Button)subjectRemoveDialog.findViewById(R.id.RemoveButton);
        //removeButton.setOnClickListener((v) ->{
        //    Spinner s = (Spinner)subjectRemoveDialog.findViewById(R.id.SubjectSpinner);

        //});

        //과목 추가 액티비티 취소
        Button cancelButton = (Button) subjectRemoveDialog.findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subjectRemoveDialog.dismiss();
            }
        });
        subjectRemoveDialog.show();
    }
}
