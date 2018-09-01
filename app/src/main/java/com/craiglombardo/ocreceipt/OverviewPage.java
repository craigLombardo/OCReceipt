package com.craiglombardo.ocreceipt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OverviewPage extends AppCompatActivity {

    private LinearLayout parentLinearLayout;

    private Person expandedPerson = null;

    private DecimalFormat deci;

    private double taxPercent;
    private double tipPercent;
    private double tipManual;
    private boolean isPercent;

    private TipPopup tipFragment;
    private TaxPopup taxFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_page);

        deci = new DecimalFormat("0.00");

        isPercent = true;
        tipManual = 0.0;
        taxPercent = 7.0;
        tipPercent = 20.0;

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.overview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parentLinearLayout = findViewById(R.id.overview_scroll_pane);

        createHeaders();

        tipFragment = new TipPopup();
        taxFragment = new TaxPopup();

//        screenHalf = getResources().getDisplayMetrics().widthPixels / 2;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    //TODO make sure that the ids match up to their corresponding menu id
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tax:
                //Insert Functionality here
                taxFragment.show(getFragmentManager(), "" + taxPercent);
                taxFragment.setHome(this);
                return true;
            case R.id.menu_tip:
                //Insert Functionality here
                tipFragment.show(getFragmentManager(), "" + isPercent + "," + tipPercent + "," + tipManual);
                tipFragment.setHome(this);
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

        Person p1 = createPerson("Craig");
        Person p2 = createPerson("Christer");
        Person p3 = createPerson("Some Random Name");

        for (int i = 0; i < 20; i++) {
            p1.addItem("Item: " + i, i * 1.3);
            if (i % 2 == 0) p2.addItem("Item: " + i, i * 1.12);
            if (i % 3 == 0) p3.addItem("Item: " + i, i * 1.2);
        }

        for (int i = 0; i < 17; i++) {
            p1.increaseItemCount("Item: " + i);
            if (i % 2 == 0) p2.increaseItemCount("Item: " + i);
            if (i % 3 == 0) p3.increaseItemCount("Item: " + i);
        }

        inflatePerson(p1);

        inflatePerson(p2);

        inflatePerson(p3);
    }

    private void inflatePerson(Person person) {
        if (person == null) return;
        person.rotateButton(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Set personSet = person.getItemsList().entrySet();
        Iterator personIter = personSet.iterator();

        LinearLayout listParent = person.getListParent();

        while (personIter.hasNext()) {
            Map.Entry mapE = (Map.Entry) personIter.next();

            Item item = (Item) mapE.getValue();

            View itemView = inflater.inflate(R.layout.overview_line_item, null);
            TextView itemName = itemView.findViewById(R.id.line_item_name);
            itemName.setText(mapE.getKey().toString());

            TextView itemQuantity = itemView.findViewById(R.id.line_count);
            itemQuantity.setText("" + item.getItemQuantity());

            TextView itemTotal = itemView.findViewById(R.id.line_item_total);

            itemTotal.setText(deci.format(item.getItemQuantity() * item.getItemPrice()));

            listParent.addView(itemView);
        }

        //This is the overview list header
        LinearLayout listRow = person.getListRow();

        //Person full name
        TextView fullName = listRow.findViewById(R.id.line_name);
        fullName.setText(person.getPersonName());

        //Person total item count
        TextView itemTotal = listRow.findViewById(R.id.total_count);
        itemTotal.setText("" + person.getItemsCount());

        //This is the overview subtotal layout
        View totalView = inflater.inflate(R.layout.overview_line_totals, null);

        TextView subtotal = totalView.findViewById(R.id.overview_subtotal);
        subtotal.setText("" + deci.format(person.getItemsTotal()));

        Double taxAddition = person.getItemsTotal() * (taxPercent / 100);
        TextView taxTotal = totalView.findViewById(R.id.overview_tax);
        taxTotal.setText("" + deci.format(taxAddition));

        Double tipAddition = person.getItemsTotal() * (tipPercent / 100);
        TextView tipTotal = totalView.findViewById(R.id.overview_tip);
        tipTotal.setText("" + deci.format(tipAddition));

        Double overallTotal = person.getItemsTotal() + tipAddition + taxAddition;
        TextView total = totalView.findViewById(R.id.overview_total);
        total.setText("" + deci.format(overallTotal));

        //Person total cost
        TextView personTotal = listRow.findViewById(R.id.line_total_price);
        personTotal.setText("" + deci.format(overallTotal));

        listParent.addView(totalView);

        hidePerson(person);
    }

    private void collapsePerson(Person person) {
        if (person == null) return;
        person.rotateButton(false);
        LinearLayout listParent = person.getListParent();
        for (int i = listParent.getChildCount() - 1; i >= 0; i--) listParent.removeViewAt(i);
        expandedPerson = null;
    }

    private void hidePerson(Person person) {
        if (person == null) return;
        person.rotateButton(false);
        LinearLayout listParent = person.getListParent();

//        listParent.setBackgroundColor(0x00000000);

        for (int i = listParent.getChildCount() - 1; i >= 0; i--)
            listParent.setVisibility(View.GONE);
        expandedPerson = null;
    }

    private void showPerson(Person person) {
        if (person == null) return;
        person.rotateButton(true);
        LinearLayout listParent = person.getListParent();

//        listParent.setBackgroundColor(ContextCompat.getColor(this, R.color.selectedColor));

        for (int i = listParent.getChildCount() - 1; i >= 0; i--)
            listParent.setVisibility(View.VISIBLE);
        expandedPerson = person;
    }

    private void toggle(Person p) {
        if (expandedPerson != null) {
            if (expandedPerson == p) {
                hidePerson(expandedPerson);
                return;
            }
            hidePerson(expandedPerson);
        }
        showPerson(p);
    }

    private Person createPerson(String personName) {
        LayoutInflater pInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View pView = pInflater.inflate(R.layout.overview_line_header, null);

        Button pDropDown = pView.findViewById(R.id.drop_down);

        final Person p = new Person(pView, personName, pDropDown);

        pView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle(p);
            }
        });

        p.getListParent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        parentLinearLayout.addView(pView, parentLinearLayout.getChildCount());
        return p;
    }

    public void setTaxPercent(double newP) {
        taxPercent = newP;
    }

    public void setTipInformation(boolean isTipPercent, double newPercent, double newManual) {
        isPercent = isTipPercent;
        tipPercent = newPercent;
        tipManual = newManual;
    }
}


