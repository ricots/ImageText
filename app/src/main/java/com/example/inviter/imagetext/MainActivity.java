package com.example.inviter.imagetext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button button;
    EditText txtBride, txtGroom;
    Drawable done;
    public String name = "";

    private Bitmap pictureBitmap;
    private Canvas pictureCanvas;
    public SurfaceView v;
    public SurfaceHolder h;
    int imgPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button);
        txtBride = (EditText) findViewById(R.id.editText);
        txtGroom = (EditText) findViewById(R.id.editText2);

        Intent intent = getIntent();
        imgPosition = intent.getExtras().getInt("imgPosition");

        //txtBride.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBride.measure(0, 0);

                Rect brideNameWidth = new Rect();
                Rect groomNameWidth = new Rect();
                txtBride.getPaint().getTextBounds(txtBride.getText().toString(),0,txtBride.getText().length(),brideNameWidth);
                txtGroom.getPaint().getTextBounds(txtGroom.getText().toString(), 0, txtGroom.getText().length(), groomNameWidth);

               /* String wdt = String.valueOf(txtBride.getTextSize());
                Toast.makeText(getApplicationContext(), wdt, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), String.valueOf(brideNameWidth.width()), Toast.LENGTH_LONG).show();*/

                Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                i.putExtra("brideName", txtBride.getText().toString());
                i.putExtra("groomName", txtGroom.getText().toString());
                i.putExtra("imgPosition", imgPosition);

                /*i.putExtra("brideNameWidth", brideNameWidth.width());
                i.putExtra("groomNameWidth", groomNameWidth.width());
                i.putExtra("brideNameTextSize", Float.valueOf(txtBride.getTextSize()));
                i.putExtra("groomNameTextSize", Float.valueOf(txtGroom.getTextSize()));*/

                startActivity(i);
            }
        });

    }


}
