package com.example.inviter.imagetext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    ListView imgListView;
    MenuDrawerAdapter menuDrawerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<ImageGroups> ImageGroups = new ArrayList<ImageGroups>();
        ImageGroups.add(new ImageGroups(R.drawable.one, "Image First"));
        ImageGroups.add(new ImageGroups(R.drawable.two, "Image Second"));
        ImageGroups.add(new ImageGroups(R.drawable.three, "Image Third"));

        imgListView = (ListView)findViewById(R.id.imgListView);
        menuDrawerAdapter = new MenuDrawerAdapter(getApplicationContext(), ImageGroups);
        imgListView.setAdapter(menuDrawerAdapter);

        imgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("imgPosition", position);
                startActivity(i);

            }
        });
    }

    public class MenuDrawerAdapter extends ArrayAdapter<ImageGroups> {
        public MenuDrawerAdapter(Context context, ArrayList<ImageGroups> imageGroupses){
            super(context, 0 , imageGroupses);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageGroups imageGroups = getItem(position);
            if(convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_list, parent, false);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            TextView textView = (TextView) convertView.findViewById(R.id.textViewName);

            imageView.setImageResource(imageGroups.icon);
            textView.setText(imageGroups.name);

            return convertView;
        }
    }
}
