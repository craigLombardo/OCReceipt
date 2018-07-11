package com.craiglombardo.ocreceipt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectionPage extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    private int screenHalf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.selection_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentLinearLayout = findViewById(R.id.selection_scroll_pane);

        test();

        screenHalf = getResources().getDisplayMetrics().widthPixels / 2;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.selection_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void test() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for (int i = 1; i <= 30; i++) {
            final View testItem = inflater.inflate(R.layout.selection_line_item, null);

            LinearLayout line = testItem.findViewById(R.id.selection_line);
            TextView count = line.findViewById(R.id.selection_line_item_count);
            count.setText("" + i);

            line.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Here");
                }
            });

            parentLinearLayout.addView(testItem, parentLinearLayout.getChildCount());
        }
    }
}
