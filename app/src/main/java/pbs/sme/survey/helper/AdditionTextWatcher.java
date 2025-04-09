package pbs.sme.survey.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AdditionTextWatcher implements TextWatcher {
    private boolean ignoreTextWatcher = false;
    private final EditText totalEditText;
    private long beforeValue = 0;

    public AdditionTextWatcher(EditText totalEditText) {
        this.totalEditText = totalEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (ignoreTextWatcher) return;

        beforeValue = parseLong(s.toString());
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No real-time change needed here
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (ignoreTextWatcher) return;

        Long afterValue = parseLong(s.toString());
        Long totalValue =parseLong(totalEditText.getText().toString());

        // Update total value
        Long newTotal = totalValue - beforeValue + afterValue;

        // Prevent infinite loop and only update if there's an actual change
        if (!newTotal.equals(totalValue)) {
            ignoreTextWatcher = true;
            totalEditText.setText(String.valueOf(newTotal));
            totalEditText.setSelection(totalEditText.getText().length()); // Move cursor to the end
            ignoreTextWatcher = false;
        }
    }

    private Long parseLong(String value) {
        if (value.equals("0") || value.isEmpty()) {
            return 0L;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}



