package pbs.sme.survey.helper;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AdditionTextWatcher implements TextWatcher {

    public static boolean IGNORE_TEXT_WATCHER = false;
    private EditText totalEditText;
    public AdditionTextWatcher(EditText totalEditText) {
        this.totalEditText = totalEditText;
    }

    int beforeValue = 0;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (IGNORE_TEXT_WATCHER) return;
        try {
            beforeValue = Integer.parseInt(s.toString());

        } catch (NumberFormatException e) {
            beforeValue = 0;
        }
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (IGNORE_TEXT_WATCHER) return;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (IGNORE_TEXT_WATCHER) return;
        int afterValue,totalValue;
        try {
            afterValue = Integer.parseInt(s.toString());
        } catch (NumberFormatException e) {
            afterValue = 0;
        }

        try {
            totalValue = Integer.parseInt(totalEditText.getText().toString());
        } catch (NumberFormatException e) {
            totalValue = 0;
        }

        totalValue = totalValue - beforeValue + afterValue;
        totalEditText.setText(String.valueOf(totalValue));
    }
}