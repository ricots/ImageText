package com.example.inviter.imagetext;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ImageActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    public String brideName = "";
    public String groomName = "";
    public int position;
    private Bitmap pictureBitmap;
    private Canvas pictureCanvas;
    public SurfaceView v, sv;
    public SurfaceHolder h, hl;
    private int width, height;
    private Paint foregroundPaint;
    private Paint backgroundPaint;
    private int groomNameWidth, brideNameWidth;
    private float groomNameTextSize, brideNameTextSize;
    private int textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        v = (SurfaceView) findViewById(R.id.image_sf);
        h = v.getHolder();
        h.addCallback(this);

        Intent intent = getIntent();
        brideName = intent.getExtras().getString("brideName");
        groomName = intent.getExtras().getString("groomName");
        position = intent.getExtras().getInt("imgPosition");

        if (brideName.length() > 9)
            textSize = getTextSize(new Paint(), brideName, 500);
        else textSize=60;

        /*groomNameWidth = intent.getExtras().getInt("groomNameWidth");
        brideNameWidth = intent.getExtras().getInt("brideNameWidth");
        groomNameTextSize = intent.getExtras().getFloat("groomNameTextSize");
        brideNameTextSize = 60; //= intent.getExtras().getFloat("brideNameTextSize");*/

        //Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
    }

    private void drawBubbles( final Canvas canvas )
    {
        v.getWidth();
        //Toast.makeText(getApplicationContext(), "Name is " + name, Toast.LENGTH_LONG).show();
        //name = "RAvikant verma";
        final int cells = 1;
        final int margin = 8;
        final int totalMargin = (cells + 1) * margin;
        final int width = 600;//(canvas.getWidth() - totalMargin) / cells;
        final int height = 120; //(c.getHeight() - totalMargin) / cells;

        drawBubble(canvas, 20, 50, width, height, brideName);
        drawBubble(canvas, 50, 170, width, height, "Weds");
        drawBubble(canvas, 80, 290, width, height, groomName);
    }

    private void drawBubble(final Canvas canvas, final int leftMargin, final int topMargin,
                            final int width, final int height, final String text )
    {
        //Toast.makeText(getApplicationContext(), String.valueOf(text.length()), Toast.LENGTH_LONG).show();
        // SET UP FONT
        final TextRect textRect;

        final Paint fontPaint = new Paint();
        fontPaint.setColor(Color.WHITE);
        fontPaint.setAntiAlias(true);

        fontPaint.setTextSize(textSize);

        textRect = new TextRect(fontPaint );
        final int h = textRect.prepare(text, width - 8, height - 8);

        // DRAW IMAGE BUBBLE
        final Paint p = new Paint();
        p.setColor(Color.TRANSPARENT);
        //p.setARGB(125, 255, 255, 255);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias( true );

        canvas.drawRoundRect(new RectF(leftMargin, topMargin, leftMargin + width,
                topMargin + h + 8), 4, 4, p);

        textRect.draw(canvas, leftMargin + 4, topMargin + 4);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height )
    {
        this.width = width;
        this.height = height;
        if (pictureBitmap != null) {
            pictureBitmap.recycle();
        }
        pictureBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        pictureCanvas = new Canvas(pictureBitmap);
       // clear();
        //draw();
    }

    /*public void draw() {
        final Canvas c = h.lockCanvas();
        //Canvas c = null;
        if (c != null) {
            c.drawBitmap(pictureBitmap, 0, 0, null);
            h.unlockCanvasAndPost(c);
        }
    }*/
    public void draw() {
        SurfaceHolder surfaceHolder = new SurfaceView(this).getHolder();
        final Canvas c = surfaceHolder.lockCanvas();
        if (c != null) {
            c.drawBitmap(pictureBitmap, 0, 0, null);
            surfaceHolder.unlockCanvasAndPost(c);
        }
    }

    public void clear() {
        pictureCanvas.drawRect(0, 0, width, height, backgroundPaint);
        draw();
    }

    /** Surface created */
    public void surfaceCreated( SurfaceHolder holder )
    {
        if( holder == null )
            return;

        Canvas canvas = null;

        try
        {
            if( (canvas = holder.lockCanvas()) != null ) {
                bgImageLoader(canvas);
                drawBubbles(canvas);
            }
        }
        finally
        {
            if( canvas != null )
                holder.unlockCanvasAndPost( canvas );
        }
    }

    public void bgImageLoader(Canvas canvas)
    {
        Bitmap icon;
        if(position == 0)
        {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.one);
        }
        else if(position == 1)
        {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.two);
        }
        else if(position == 2)
        {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.three);
        }
        else
        {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.my_db);
        }
        icon = Bitmap.createScaledBitmap(icon, v.getWidth(), v.getHeight(), true);
        canvas.drawBitmap(icon, 0, 0, new Paint());

    }

    public void surfaceDestroyed( SurfaceHolder holder )
    {
        if (pictureBitmap != null) {
            String name = UUID.randomUUID().toString() + ".png";
            saveFile(pictureBitmap, name);
            pictureBitmap.recycle();
        }
        pictureBitmap = null;
    }

    private void saveFile(Bitmap bitmap, String name) {
        //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        // String filename = String.valueOf(System.currentTimeMillis()) ;
        String extStorageDirectory;
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        final File file = new File(extStorageDirectory, name);
        try {
            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } catch (final FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private int getTextSize(Paint paint,String str, float maxWidth)
    {
        int size = 0;
        do {
            paint.setTextSize(++ size);
        } while(paint.measureText(str) < maxWidth);
        return size;
    }

}
