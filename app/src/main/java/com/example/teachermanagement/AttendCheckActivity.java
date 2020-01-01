package com.example.teachermanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendCheckActivity extends AppCompatActivity {

    ListView studentListView;

    String[] studentNameList = {"이승원","장승민","김여산","문준우"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_check);

        StudentList adapter = new StudentList(AttendCheckActivity.this);
        studentListView = (ListView)findViewById(R.id.StudentList);
        studentListView.setAdapter(adapter);

    }

    public class StudentList extends ArrayAdapter<String>
    {
        private final Activity context;
        public StudentList(Activity context)
        {
            super(context, R.layout.studentitem,studentNameList);
            this.context = context;
        }
        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.studentitem,null,true);
            TextView name = (TextView) rowView.findViewById(R.id.name);
            TextView age = (TextView) rowView.findViewById(R.id.age);
            name.setText(studentNameList[position]);
            return rowView;
        }
    }
}
