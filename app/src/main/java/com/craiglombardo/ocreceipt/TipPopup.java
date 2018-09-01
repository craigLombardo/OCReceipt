package com.craiglombardo.ocreceipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

/**
 * Created by CraigLombardo on 8/27/18.
 */

public class TipPopup extends DialogFragment {

    View view;
    EditText tipPercent;
    EditText tipManual;

    OverviewPage myHome;

    RadioButton percentRB;
    RadioButton manualRB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        view = getActivity().getLayoutInflater().inflate(R.layout.custom_tip_popup, null);

        tipPercent = view.findViewById(R.id.tip_percent_edit_text);
        tipManual = view.findViewById(R.id.tip_manual_edit_text);

        percentRB = view.findViewById(R.id.tip_percent_button);
        manualRB = view.findViewById(R.id.tip_manual_button);

        percentRB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                manualRB.setChecked(false);
            }
        });

        manualRB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                percentRB.setChecked(false);
            }
        });


        List<String> items = Arrays.asList(getTag().split("\\s*,\\s*"));

        boolean isPercent = items.get(0).equals("true");
        percentRB.setChecked(isPercent);
        manualRB.setChecked(!isPercent);

        tipPercent.setText("" + items.get(1));
        tipManual.setText("" + items.get(2));

        tipPercent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    percentRB.setChecked(true);
                    manualRB.setChecked(false);
                }
            }
        });

        tipManual.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    manualRB.setChecked(true);
                    percentRB.setChecked(false);
                }
            }
        });


        tipPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                percentRB.setChecked(true);
                manualRB.setChecked(false);
            }
        });

        tipManual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                manualRB.setChecked(true);
                percentRB.setChecked(false);
            }
        });

        tipManual.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    view.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return false;
            }
        });

        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setTitle("Please Enter The Tip Amounts!")
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("Select", null)
                .setNegativeButton("Cancel", null)
                .create();
        builder.setOnShowListener(new DialogInterface.OnShowListener()

        {

            @Override
            public void onShow(DialogInterface dialog) {
                Button acceptButton = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                acceptButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String message;
                        int conditionReturn = checkConditions();

                        if (conditionReturn == 0) {
                            myHome.setTipInformation(percentRB.isChecked(),
                                    Double.parseDouble(tipPercent.getText().toString()),
                                    Double.parseDouble(tipManual.getText().toString()));
                            dismiss();
                        } else {
                            switch (conditionReturn) {
                                case 1:
                                    message = "Please select a tip percent between 0 and 100";
                                    break;
                                case 2:
                                    message = "Please select a tip amount between 0 and 500";
                                    break;
                                default:
                                    message = "Please make sure there is no text in the entry fields and try again.";
                            }

                            //Code taken from http://www.riptutorial.com/android/example/15750/show-toast-message-above-soft-keyboard
                            View root = getActivity().findViewById(android.R.id.content);
                            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
                            int yOffset = Math.max(0, root.getHeight() - toast.getYOffset());
                            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, yOffset);
                            toast.show();
                        }
                    }
                });

                Button cancelButton = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            }
        });

        view.requestFocus();


        return builder;
    }

    private int checkConditions() {
        double percentValue;
        double manualValue;

        try {
            percentValue = Double.parseDouble(tipPercent.getText().toString());
            manualValue = Double.parseDouble(tipManual.getText().toString());

            if (percentValue < 0.0 || percentValue > 100.0) return 1;
            else if (manualValue < 0.0 || manualValue >= 500.0) return 2;
            else return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    public void setHome(OverviewPage home) {
        myHome = home;
    }

}
