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

import java.util.ArrayList;

public class OverviewPage extends AppCompatActivity {

    private LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentLinearLayout = findViewById(R.id.overview_scroll_pane);

        createHeaders();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("Here");
        switch (item.getItemId()) {
            case R.id.tip_percent_sign:
                //Insert Functionality here
                System.out.println("Hello World");
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createHeaders() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View firstView = inflater.inflate(R.layout.overview_line_header, null);
        final View secondView = inflater.inflate(R.layout.overview_line_header, null);

        Person p1 = new Person(firstView, "Craig");
        Person p2 = new Person(secondView, "Christer");

        for (int i = 0; i < 20; i++) {
            p1.addItem("Item: " + i, i);
            if (i % 2 == 0) p2.addItem("Item: " + i, i);
        }

        parentLinearLayout.addView(firstView, parentLinearLayout.getChildCount());
        parentLinearLayout.addView(secondView, parentLinearLayout.getChildCount());

//        p1.inflateItems();
//        p2.inflateItems();

//        LinearLayout row = rowView.findViewById(R.id.line_header);
//
//        for (int i = 0; i < 22; i++) {
//            final View itemView = inflater.inflate(R.layout.overview_line_item, null);
//            final TextView tmp = itemView.findViewById(R.id.line_item_name);
//            tmp.setText("Hello " + i);
//            row.addView(itemView);
//        }
//
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("Hello Again");
//            }
//        });
//
//        final View totalView = inflater.inflate(R.layout.overview_line_totals, null);
//        row.addView(totalView);
//
//        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());

    }

    class Person {

        private ArrayList<String> items;
        private ArrayList<Double> prices;

        private View parent;
        private LinearLayout row;

        private Double totalCost;

        private boolean expanded = false;

        private Person(View parentView, String name) {

            parent = parentView;
            row = parent.findViewById(R.id.line_header);

            TextView fullName = row.findViewById(R.id.line_name);
            fullName.setText(name);

            totalCost = 0.0;

            items = new ArrayList<>();
            prices = new ArrayList<>();

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (expanded) collapseItems();
                    else inflateItems();
                }
            });
        }

        private void addItem(String item, double cost) {
            items.add(item);
            prices.add(cost);

            totalCost += cost;
        }

        private Double getTotalCost() {
            return totalCost;
        }

        private void inflateItems() {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int i = 0; i < items.size(); i++) {

                final View itemView = inflater.inflate(R.layout.overview_line_item, null);
                final TextView itemName = itemView.findViewById(R.id.line_item_name);
                itemName.setText(items.get(i));

                final TextView itemTotal = itemView.findViewById(R.id.line_item_total);
                itemTotal.setText("" + prices.get(i));

                row.addView(itemView);
            }

            final View totalView = inflater.inflate(R.layout.overview_line_totals, null);
            final TextView sub = totalView.findViewById(R.id.overview_subtotal);
            sub.setText("" + totalCost);
            row.addView(totalView);

            expanded = true;
        }

        private void collapseItems() {

            for (int i = row.getChildCount() - 1; i >= 2; i--) row.removeViewAt(i);
            expanded = false;
        }
    }
}
