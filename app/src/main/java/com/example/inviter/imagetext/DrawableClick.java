package com.example.inviter.imagetext;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by inviter on 15/8/15.
 */
public class DrawableClick
{
    public static boolean getClear(EditText editText, MotionEvent motionEvent)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if(motionEvent.getRawX() >= editText.getRight() - editText.getTotalPaddingRight())
            {
                editText.setText("");
                return true;
            }
        }
        return true;
    }

    public static void textChange(TextView textView, Drawable cancel)
    {
        if (textView.length() >0) {
            textView.setCompoundDrawables(null, null, cancel, null);
        }
        else
        {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }
}
