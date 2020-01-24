package com.example.teachermanagement;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText
{
    public CustomEditText(final Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                ((ResisterStudentActivity)context).GetScrollView().smoothScrollTo(0,  location[1] -200 );
                return false;
            }
        });


    }



}
