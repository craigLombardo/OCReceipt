package com.craiglombardo.ocreceipt;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by CraigLombardo on 6/26/18.
 */

public class FilterPage extends AppCompatActivity {

    private LinearLayout parentLinearLayout;

    private ArrayList<String> firstList;
    private ArrayList<String> secondList;
    private ArrayList<String> thirdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkbox_clusters);

        parentLinearLayout = findViewById(R.id.filter_layout);

        setup();

        firstCluster(firstList);
        secondCluster(secondList);
        thirdCluster(thirdList);

        Button captureButton = findViewById(R.id.capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCluster(R.id.first_cluster, "First ones: ");
                getCluster(R.id.second_cluster, "Second ones: ");
                getCluster(R.id.third_cluster, "Third ones: ");
            }
        });

    }

    private void setup() {
        firstList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        secondList = new ArrayList<>(Arrays.asList("test", "this", "code", "over", "here", "please"));
        thirdList = new ArrayList<>(Arrays.asList("something", "something2", "darkside", "we", "have", "cookies"));
    }

    private void firstCluster(ArrayList<String> list) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout row = parentLinearLayout.findViewById(R.id.first_cluster);

        for (String item : list) {
            final View itemView = inflater.inflate(R.layout.checkbox_line_item, null);
            final TextView lineItemName = itemView.findViewById(R.id.line_item_name);
            lineItemName.setText(item);
            row.addView(itemView);
        }

    }

    private void secondCluster(ArrayList<String> list) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout row = parentLinearLayout.findViewById(R.id.second_cluster);

        for (String item : list) {
            final View itemView = inflater.inflate(R.layout.checkbox_line_item, null);
            final TextView lineItemName = itemView.findViewById(R.id.line_item_name);
            lineItemName.setText(item);
            row.addView(itemView);
        }

    }

    private void thirdCluster(ArrayList<String> list) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout row = parentLinearLayout.findViewById(R.id.third_cluster);

        for (String item : list) {
            final View itemView = inflater.inflate(R.layout.checkbox_line_item, null);
            final TextView lineItemName = itemView.findViewById(R.id.line_item_name);
            lineItemName.setText(item);
            row.addView(itemView);
        }

    }

    private ArrayList<String> getCluster(int clusterID, String message) {
        LinearLayout row = parentLinearLayout.findViewById(clusterID);

        ArrayList<String> output = new ArrayList<>();

        for (int i = 0; i < row.getChildCount(); i++) {
            View lineItem = row.getChildAt(i);
            CheckBox box = lineItem.findViewById(R.id.checkbox);
            TextView name = lineItem.findViewById(R.id.line_item_name);

            if (box.isChecked()) {
                output.add(name.getText().toString());
            }
        }

        String printStr = message;
        for (String s : output) printStr += s + ",";

        System.out.println(printStr);

        return output;
    }

}
