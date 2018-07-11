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
    private int screenHalf;

    private Person expandedPerson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_page);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentLinearLayout = findViewById(R.id.overview_scroll_pane);

        createHeaders();

        screenHalf = getResources().getDisplayMetrics().widthPixels / 2;

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

        final View headerView = inflater.inflate(R.layout.overview_header, null);
        parentLinearLayout.addView(headerView, parentLinearLayout.getChildCount());

        final View firstView = inflater.inflate(R.layout.overview_line_header, null);
        final View secondView = inflater.inflate(R.layout.overview_line_header, null);
        final View thirdView = inflater.inflate(R.layout.overview_line_header, null);

        Person p1 = new Person(firstView, "Craig");
        Person p2 = new Person(secondView, "Christer");
        Person p3 = new Person(thirdView, "Some Random Name");

        for (int i = 0; i < 20; i++) {
            p1.addItem("Item: " + i, i);
            if (i % 2 == 0) p2.addItem("Item: " + i, i);
            if (i % 3 == 0) p3.addItem("Item: " + i, i);
        }

        parentLinearLayout.addView(firstView, parentLinearLayout.getChildCount());
        parentLinearLayout.addView(secondView, parentLinearLayout.getChildCount());
        parentLinearLayout.addView(thirdView, parentLinearLayout.getChildCount());

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

        private LinearLayout listParent;
        private LinearLayout row;

        private Double totalCost;

        private Person(View parentView, String name) {

            listParent = parentView.findViewById(R.id.line_item_list);
            row = parentView.findViewById(R.id.line_header);

            TextView fullName = row.findViewById(R.id.line_name);
            fullName.setText(name);

            totalCost = 0.0;

            items = new ArrayList<>();
            prices = new ArrayList<>();

            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle();
                }
            });

            listParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    toggle();
                }
            });
        }

        private void toggle() {
            if (expandedPerson == null) inflateItems(false);
            else if (expandedPerson == this) collapseItems();
            else inflateItems(true);
        }

        private void addItem(String item, double cost) {
            items.add(item);
            prices.add(cost);

            totalCost += cost;
        }

        private Double getTotalCost() {
            return totalCost;
        }

        private void inflateItems(boolean collapseOther) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int i = 0; i < items.size(); i++) {

                final View itemView = inflater.inflate(R.layout.overview_line_item, null);
                final TextView itemName = itemView.findViewById(R.id.line_item_name);
                itemName.setText(items.get(i));

                final TextView itemTotal = itemView.findViewById(R.id.line_item_total);
                itemTotal.setText("" + prices.get(i));

                listParent.addView(itemView);
            }

            final View totalView = inflater.inflate(R.layout.overview_line_totals, null);
            final TextView sub = totalView.findViewById(R.id.overview_subtotal);
            sub.setText("" + totalCost);
            listParent.addView(totalView);

            if (collapseOther) expandedPerson.collapseItems();

            expandedPerson = this;
        }

        private void collapseItems() {
            for (int i = listParent.getChildCount() - 1; i >= 0; i--) listParent.removeViewAt(i);
            expandedPerson = null;
        }
    }

}
