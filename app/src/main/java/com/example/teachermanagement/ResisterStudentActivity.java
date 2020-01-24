package com.example.teachermanagement;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResisterStudentActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    StudentDatabase studentDatabase;

    public static final String PREFS_NEWSTUDENT = "PrefsFile";
    //공유페러퍼런스와 데이터베이스

    private ArrayAdapter<String> subjectItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister_student);

        //텍스트뷰에 포커스 // 액티비티 진입시 키보드 뜨는거 방지
        TextView title = (TextView)findViewById(R.id.TitleText);
        title.setFocusableInTouchMode(true);
        title.requestFocus();

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

        LoadPreferences();

        //데이터베이스초기화
        studentDatabase = new StudentDatabase(this);

        //데이터베이스에서 객체얻기
        try{
            db = studentDatabase.getWritableDatabase();
        } catch (SQLiteException ex){
            db = studentDatabase.getReadableDatabase();
        }
    }

    private void LoadPreferences()
    {
        //공유 프레퍼런스 읽기
        SharedPreferences settings = getPreferences(0);
        EditText fieldValue = (EditText)findViewById(R.id.NameEditText);
        fieldValue.setText(settings.getString("newStudentName",""));
        fieldValue = (EditText)findViewById(R.id.AgeEditText);
        fieldValue.setText(settings.getString("newStudentAge",""));
        fieldValue = (EditText)findViewById(R.id.SchoolEditText);
        fieldValue.setText(settings.getString("newStudentSchool",""));

        RadioButton radioValue = (RadioButton)findViewById(R.id.ManRadioButton);
        if(settings.getBoolean("newStudentSex",false) == false)
        {
            radioValue.setChecked(false);
            radioValue = (RadioButton)findViewById(R.id.womanRadioButton);
            radioValue.setChecked(true);
        }
        else
            radioValue.setChecked(true);

        fieldValue = (EditText) findViewById(R.id.PhoneNumberEditText);
        fieldValue.setText(settings.getString("newStudentPhone",""));
        fieldValue = (EditText) findViewById(R.id.EmailEditText);
        fieldValue.setText(settings.getString("newStudentEmail",""));

        Spinner spinnerValue = (Spinner)findViewById(R.id.EmailSpinner);
        int index = settings.getInt("newStudentEmailDomain",0);
        spinnerValue.setSelection(index);

        spinnerValue = (Spinner)findViewById(R.id.SubjectSpinner);
        index = settings.getInt("newStudentSubject",0);
        spinnerValue.setSelection(index);

        fieldValue = (EditText) findViewById(R.id.IssueEditText);
        fieldValue.setText(settings.getString("newStudentIssue",""));
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        //공유프레퍼런스 에 쓰기
        SharedPreferences settings = getPreferences(0);
        SharedPreferences.Editor editor = settings.edit();
        EditText fieldValue = (EditText)findViewById(R.id.NameEditText);
        editor.putString("newStudentName",fieldValue.getText().toString());
        fieldValue = (EditText)findViewById(R.id.AgeEditText);
        editor.putString("newStudentAge",fieldValue.getText().toString());
        fieldValue = (EditText)findViewById(R.id.SchoolEditText);
        editor.putString("newStudentSchool",fieldValue.getText().toString());
        RadioButton radioValue = (RadioButton)findViewById(R.id.ManRadioButton);
        editor.putBoolean("newStudentSex",radioValue.isChecked());
        fieldValue = (EditText)findViewById(R.id.PhoneNumberEditText);
        editor.putString("newStudentPhone",fieldValue.getText().toString());
        fieldValue = (EditText)findViewById(R.id.EmailEditText);
        editor.putString("newStudentEmail",fieldValue.getText().toString());
        Spinner spinnerValue = (Spinner)findViewById(R.id.EmailSpinner);
        editor.putInt("newStudentEmailDomain",spinnerValue.getSelectedItemPosition());
        spinnerValue = (Spinner)findViewById(R.id.SubjectSpinner);
        editor.putInt("newStudentSubject",spinnerValue.getSelectedItemPosition());
        fieldValue = (EditText)findViewById(R.id.IssueEditText);
        editor.putString("newStudentIssue",fieldValue.getText().toString());

        editor.commit();
    }

    //학생 등록중 과목 추가시
    public void onClick_AddSubject(View v)
    {
        //과목추가 다이얼로그 생성
        final Dialog subjectAddDialog = new Dialog(this);
        subjectAddDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        subjectAddDialog.setTitle("과목추가");
        subjectAddDialog.setContentView(R.layout.subjectadd_dialog);

        //과목 추가  추가버튼
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

        //과목 추가 다이얼로그 취소
        Button cancelButton = (Button) subjectAddDialog.findViewById(R.id.CancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                subjectAddDialog.dismiss();
            }
        });

        //다이얼로그 실행
        subjectAddDialog.show();
    }

    //학생 등록중 과목 삭제시
    public void onClick_RemoveSubject(View v)
    {
        final Dialog subjectRemoveDialog = new Dialog(this);
        subjectRemoveDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        subjectRemoveDialog.setTitle("과목삭제");
        subjectRemoveDialog.setContentView(R.layout.subjectremove_dialog);

        Spinner subjectSpinner = (Spinner)subjectRemoveDialog.findViewById(R.id.SubjectSpinner);
        subjectSpinner.setAdapter(subjectItems);

        Button removeButton = (Button)subjectRemoveDialog.findViewById(R.id.RemoveButton);
        removeButton.setOnClickListener(new View.OnClickListener()
        {
            //삭제 버튼 누를시
            @Override
            public void onClick(View v) {
                Spinner subjectSpinner = (Spinner)subjectRemoveDialog.findViewById(R.id.SubjectSpinner);
                String subjectDel = subjectSpinner.getSelectedItem().toString();
                for (int i = 0; i < subjectItems.getCount(); i++) {
                    if (subjectItems.getItem(i).equals(subjectDel)) {
                        subjectItems.remove(subjectDel);
                        break;
                    }
                }
                subjectRemoveDialog.dismiss();
            }
        });

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

        //다이얼로그 실행
        subjectRemoveDialog.show();
    }

    //학생 등록 액티비티 취소
    public void onClick_Cancel(View v)
    {
        super.onBackPressed();
    }

    //데이터베이스에 학생을 실제 등록
    public void onClick_Resister(View v)
    {
        //db.execSQL("INSERT INTO contacts VALUES (null, '" + name + "');");
        Toast.makeText(getApplicationContext(),"성공적으로 추가되었음",Toast.LENGTH_SHORT).show();

        super.onBackPressed();

        //실제 등록하면 프레퍼런스 전부 비움
        EditText fieldValue = (EditText)findViewById(R.id.NameEditText);
        fieldValue.setText(null);
        fieldValue = (EditText)findViewById(R.id.AgeEditText);
        fieldValue.setText(null);
        fieldValue = (EditText)findViewById(R.id.SchoolEditText);
        fieldValue.setText(null);
        fieldValue = (EditText)findViewById(R.id.PhoneNumberEditText);
        fieldValue.setText(null);
        fieldValue = (EditText)findViewById(R.id.EmailEditText);
        fieldValue.setText(null);
        fieldValue = (EditText)findViewById(R.id.IssueEditText);
        fieldValue.setText(null);
    }

    //자신의 스크롤뷰를 반환
    public ScrollView GetScrollView()
    {
        ScrollView sv = (ScrollView)findViewById(R.id.ResisterView);
        return sv;
    }







}






