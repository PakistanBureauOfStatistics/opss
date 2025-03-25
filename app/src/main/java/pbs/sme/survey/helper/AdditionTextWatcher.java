package pbs.sme.survey.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AdditionTextWatcher implements TextWatcher {
    private static boolean IGNORE_TEXT_WATCHER = false;
    private final EditText totalEditText;
    private int beforeValue = 0;

    public AdditionTextWatcher(EditText totalEditText) {
        this.totalEditText = totalEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (IGNORE_TEXT_WATCHER) return;

        beforeValue = parseInteger(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No real-time change needed here
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (IGNORE_TEXT_WATCHER) return;

        int afterValue = parseInteger(s.toString());
        int totalValue = parseInteger(totalEditText.getText().toString());

        // Update total value
        int newTotal = totalValue - beforeValue + afterValue;

        // Prevent infinite loop and only update if there's an actual change
        if (newTotal != totalValue) {
            IGNORE_TEXT_WATCHER = true;
            totalEditText.setText(String.valueOf(newTotal));
            totalEditText.setSelection(totalEditText.getText().length()); // Move cursor to the end
            IGNORE_TEXT_WATCHER = false;
        }
    }

    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}


