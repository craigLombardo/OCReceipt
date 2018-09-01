package com.craiglombardo.ocreceipt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by CraigLombardo on 8/27/18.
 */

public class TaxPopup extends DialogFragment {

    View view;
    EditText taxPercent;

    OverviewPage myHome;

    private final int MISC_ERR = -1;
    private final int TAX_RANGE_PASS = 0;
    private final int TAX_RANGE_ERROR = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        view = getActivity().getLayoutInflater().inflate(R.layout.custom_tax_popup, null);

        taxPercent = view.findViewById(R.id.tax_percent_edit_text);
        taxPercent.setText(getTag());

        taxPercent.setOnEditorActionListener(new EditText.OnEditorActionListener() {
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
                .setTitle("Please Enter The Tax Percentage!")
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

                        if (conditionReturn == TAX_RANGE_PASS) {
                            myHome.setTaxPercent(Double.parseDouble(taxPercent.getText().toString()));
                            dismiss();
                        } else {
                            switch (conditionReturn) {
                                case TAX_RANGE_ERROR:
                                    message = "Please select a tax percent between 0 and 100";
                                    break;
                                case MISC_ERR:
                                    message = "Please make sure there is no text in the entry fields and try again.";
                                    break;
                                default:
                                    message = "Something went wrong.";
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

        try {
            percentValue = Double.parseDouble(taxPercent.getText().toString());

            if (percentValue < 0.0 || percentValue > 100.0) return TAX_RANGE_ERROR;
            else return TAX_RANGE_PASS;

        } catch (Exception e) {
            return MISC_ERR;
        }
    }

    public void setHome(OverviewPage home) {
        myHome = home;
    }
}
