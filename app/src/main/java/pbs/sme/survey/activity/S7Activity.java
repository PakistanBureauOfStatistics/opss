package pbs.sme.survey.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pbs.sme.survey.R;
import pbs.sme.survey.helper.AdditionTextWatcher;
import pbs.sme.survey.model.Section47;
import pk.gov.pbs.utils.StaticUtils;

public class S7Activity extends FormActivity {
    private Button sbtn;
    private List<Section47> modelDatabase;
    // Year Fields
    EditText year_700,  year_709, year_710 ,year_711, year_712, year_713, year_714;
    // Month Fields
    EditText month_700, month_709, month_710,month_711, month_712, month_713, month_714;
    // Student Fields
    EditText student_700, student_709, student_710 ,student_711, student_712, student_713, student_714;

    private final String[] inputValidationOrder = new String[]{
            "month", "year", "student"
    };

    private final String[] codeList = new String[]{
            "701", "702", "703", "704", "705", "706", "707", "708",
            "1715","1716","1717","1718","1719","1720","1721","1722","1723",
            "2715","2716","2717","2718","2719","2720","2721","2722","2723","2724",
            "3715","3716","3717","3718","3719","3720","3721","3722","3723","3724",
            "3725","3726","3727","3728","3729","3730","3731","3732","3733","3734",
            "3735","3736","3737","3738","3739","3740","3741","4715","4716",
            "5715","5716","5717","5718","5719","5720","5721","5722","5723","5724","5725",
            "700","709","710","711","712","713","714"

    };

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);
        setDrawer(this, "Section 7: Quantity/Value of Fish Catch/Sold LY2023-24");
        setParent(this, S8Activity.class);
        scrollView = findViewById(R.id.scrollView);
        init();
        updateTextViewVisibility();
        // Initialize Year Fields
        year_700 = findViewById(R.id.year__700);
        year_709 = findViewById(R.id.year__709);
        year_710 = findViewById(R.id.year__710); // Percentage Fiel
        year_711 = findViewById(R.id.year__711);
        year_712 = findViewById(R.id.year__712);
        year_713 = findViewById(R.id.year__713);
        year_714 = findViewById(R.id.year__714);

        // Initialize Month Fields
        month_700 = findViewById(R.id.month__700);
        month_709 = findViewById(R.id.month__709);
        month_710 = findViewById(R.id.month__710); // Percentage Field
        month_711 = findViewById(R.id.month__711);
        month_712 = findViewById(R.id.month__712);
        month_713 = findViewById(R.id.month__713);
        month_714 = findViewById(R.id.month__714);

        // Initialize Student Fields
        student_700 = findViewById(R.id.student__700);
        student_709 = findViewById(R.id.student__709);
        student_710 = findViewById(R.id.student__710); // Percentage Field
        student_711 = findViewById(R.id.student__711);
        student_712 = findViewById(R.id.student__712);
        student_713 = findViewById(R.id.student__713);
        student_714 = findViewById(R.id.student__714);

        // Add Listeners to Calculate Percentages
        addPercentageCalculation(year_700, year_711, year_712);
        addPercentageCalculation(year_700, year_713, year_714);

        addPercentageCalculation(month_700, month_711, month_712);
        addPercentageCalculation(month_700, month_713, month_714);

        addPercentageCalculation(student_700, student_711, student_712);
        addPercentageCalculation(student_700, student_713, student_714);
        addPercentageCalculation(year_700, year_709, year_710);
        addPercentageCalculation(month_700, month_709, month_710);
        addPercentageCalculation(student_700, student_709, student_710);

        EditText totalEditTextyear = findViewById(R.id.year__700);
        EditText totalEditTextmonth = findViewById(R.id.month__700);
        EditText totalEditTexttstudent= findViewById(R.id.student__700);
        AdditionTextWatcher additionTextWatchermonth = new AdditionTextWatcher(totalEditTextmonth);
        AdditionTextWatcher additionTextWatcheryear = new AdditionTextWatcher(totalEditTextyear);
        AdditionTextWatcher additionTextWatcherstudent = new AdditionTextWatcher(totalEditTexttstudent);
        for (int i = 0; i < codeList.length - 1; i++) {
            EditText et = findViewById(getResources().getIdentifier("student__" + codeList[i], "id", getPackageName()));
            if (et != null) {
                et.removeTextChangedListener(additionTextWatcherstudent);
                et.addTextChangedListener(additionTextWatcherstudent);
            }
        }
        for (int i = 0; i < codeList.length - 1; i++) {
            EditText et = findViewById(getResources().getIdentifier("month__" + codeList[i], "id", getPackageName()));
            if (et != null) {
                et.removeTextChangedListener(additionTextWatchermonth);
                et.addTextChangedListener(additionTextWatchermonth);
            }
        }

        for (int i = 0; i < codeList.length - 1; i++) {
            EditText et = findViewById(getResources().getIdentifier("year__" + codeList[i], "id", getPackageName()));
            if (et != null) {
                et.removeTextChangedListener(additionTextWatcheryear);
                et.addTextChangedListener(additionTextWatcheryear);
            }
        }




        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });

    }
    private void saveForm() {
        sbtn.setEnabled(false);
        List<Section47> list = new ArrayList<>();

        for (String c : codeList) {
            Section47 m = null;
            try {
                m = (Section47) extractValidatedModelFromForm(this, m, true, inputValidationOrder, c, Section47.class, false, this.findViewById(android.R.id.content));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (m == null) {
                mUXToolkit.showAlertDialogue("Failed", "فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔", alertForEmptyFieldEvent);
                sbtn.setEnabled(true);
                return;
            }

            list.add(m);
            setCommonFields(m);
            m.section = 7;
            m.code = c;

        }


        /////TODO CHECKS////////////////////////////


        List<Long> iid = dbHandler.replace(list);
        for (Long i : iid) {
            if (i != null && i < 0) {
                mUXToolkit.showToast("Failed");
                sbtn.setEnabled(true);
                return;
            }
        }
        mUXToolkit.showToast("Success");
        sbtn.setEnabled(true);
        btnn.callOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadForm();
    }


    private void loadForm() {
        List<Section47> list = dbHandler.query(Section47.class, "section=" + 7 + " AND uid='" + resumeModel.uid + "' AND (is_deleted=0 OR is_deleted is null)");
        for (Section47 s : list) {
            setFormFromModel(this, s, inputValidationOrder, s.code, false, this.findViewById(android.R.id.content));
        }

    }
    private void updateTextViewVisibility() {
        // Get references to the TextViews
        TextView studentTextView = findViewById(R.id.student);
        TextView monthsTextView = findViewById(R.id.Months);
        TextView yearTextView = findViewById(R.id.year);

        // Hide all TextViews initially
        studentTextView.setVisibility(View.GONE);
        monthsTextView.setVisibility(View.GONE);
        yearTextView.setVisibility(View.GONE);

        // Show the correct TextView based on survey_id
        if (resumeModel != null) {
            if (resumeModel.survey_id == 1 || resumeModel.survey_id == 2) {
                yearTextView.setVisibility(View.VISIBLE);  // Show "Number of Students"
            } else if (resumeModel.survey_id == 3 || resumeModel.survey_id == 4 || resumeModel.survey_id == 5) {
                monthsTextView.setVisibility(View.VISIBLE);   // Show "Last 3 Calendar Months"
            } else {
                studentTextView.setVisibility(View.VISIBLE);   // Default case: Show "2023-24"
            }
        } else {
            Log.e("SurveyDebug", "resumeModel is null!");
        }
    }

    private void init() {
        // Get references to all layouts
        LinearLayout layout1 = findViewById(R.id.survey1);
        LinearLayout layout2 = findViewById(R.id.survey2);
        LinearLayout layout3 = findViewById(R.id.survey3);
        LinearLayout layout4 = findViewById(R.id.survey4);
        LinearLayout layout5 = findViewById(R.id.survey5);

        // Hide all layouts initially
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);

        // Hide all dynamically created EditTexts
        for (String code : codeList) {
            String yearId = "year__" + code;
            String studentId = "student__" + code;
            String monthId = "month__" + code;

            EditText yearEt = findViewById(getResources().getIdentifier(yearId, "id", getPackageName()));
            EditText studentEt = findViewById(getResources().getIdentifier(studentId, "id", getPackageName()));
            EditText monthEt = findViewById(getResources().getIdentifier(monthId, "id", getPackageName()));

            if (yearEt != null) yearEt.setVisibility(View.GONE);
            if (studentEt != null) studentEt.setVisibility(View.GONE);
            if (monthEt != null) monthEt.setVisibility(View.GONE);
        }

        // Show specific EditTexts based on survey_id
        if (resumeModel.survey_id == 1 || resumeModel.survey_id == 2) {
            for (String code : codeList) {
                EditText yearEt = findViewById(getResources().getIdentifier("year__" + code, "id", getPackageName()));
                if (yearEt != null) yearEt.setVisibility(View.VISIBLE);
            }
            for (String code : codeList) {
                EditText studentEt = findViewById(getResources().getIdentifier("student__" + code, "id", getPackageName()));
                if (studentEt != null) studentEt.setVisibility(View.GONE);
            }
            for (String code : codeList) {
                EditText monthEt = findViewById(getResources().getIdentifier("month__" + code, "id", getPackageName()));
                if (monthEt != null) monthEt.setVisibility(View.GONE);
            }
        }

        if (resumeModel.survey_id == 3 || resumeModel.survey_id == 4 || resumeModel.survey_id == 5) {
            for (String code : codeList) {
                EditText yearEt = findViewById(getResources().getIdentifier("year__" + code, "id", getPackageName()));
                if (yearEt != null) yearEt.setVisibility(View.GONE);
            }
            for (String code : codeList) {
                EditText studentEt = findViewById(getResources().getIdentifier("student__" + code, "id", getPackageName()));
                if (studentEt != null) studentEt.setVisibility(View.GONE);
            }
            for (String code : codeList) {
                EditText monthEt = findViewById(getResources().getIdentifier("month__" + code, "id", getPackageName()));
                if (monthEt != null) monthEt.setVisibility(View.VISIBLE);
            }
        }

        // Show the layout based on survey_id
        switch (resumeModel.survey_id) {
            case 1:
                layout1.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout2.setVisibility(View.VISIBLE);
                break;
            case 3:
                layout3.setVisibility(View.VISIBLE);
                break;
            case 4:
                layout4.setVisibility(View.VISIBLE);
                break;
            case 5:
                layout5.setVisibility(View.VISIBLE);
                break;
            default:
                Log.e("SurveyDebug", "Invalid survey ID: " + resumeModel.survey_id);
                break;
        }
    }

/*    private void init() {
        // Get references to all layouts
        LinearLayout layout1 = findViewById(R.id.survey1);
        LinearLayout layout2 = findViewById(R.id.survey2);
        LinearLayout layout3 = findViewById(R.id.survey3);
        LinearLayout layout4 = findViewById(R.id.survey4);
        LinearLayout layout5 = findViewById(R.id.survey5);

        // Hide all layouts initially
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);
        if (resumeModel.survey_id == 1 || resumeModel.survey_id == 2) {
            for (int i = 0; i < codeList.length - 1; i++) {
                String id = "year__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }
            for (int i = 0; i < codeList.length - 1; i++) {
                String id = "student__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

            for (int i = 0; i < codeList.length - 1; i++) {
                String id = "month__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }


        if (resumeModel.survey_id == 3 || resumeModel.survey_id == 4 || resumeModel.survey_id == 5) {
            for (int i = 0; i < codeList.length; i++) {
                String id = "year__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S47activity", "EditText not found for ID: " + id);
                }
            }
            for (int i = 0; i < codeList.length - 1; i++) {
                String id = "student__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }
            for (String s : codeList) {
                String id = "month__" + s;
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

            }
        // Find EditText elements once to avoid duplicate calls
        EditText year_700 = findViewById(R.id.year__700);
        EditText month_700 = findViewById(R.id.month__700);
        EditText student_700 = findViewById(R.id.student__700);

        EditText year_709 = findViewById(R.id.year__709);
        EditText month_709 = findViewById(R.id.month__709);
        EditText student_709 = findViewById(R.id.student__709);

        EditText year_710 = findViewById(R.id.year__710);
        EditText month_710 = findViewById(R.id.month__710);
        EditText student_710 = findViewById(R.id.student__710);

        EditText year_711 = findViewById(R.id.year__711);
        EditText month_711 = findViewById(R.id.month__711);
        EditText student_711 = findViewById(R.id.student__711);

        EditText year_712 = findViewById(R.id.year__712);
        EditText month_712 = findViewById(R.id.month__712);
        EditText student_712 = findViewById(R.id.student__712);

        EditText year_713 = findViewById(R.id.year__713);
        EditText month_713 = findViewById(R.id.month__713);
        EditText student_713 = findViewById(R.id.student__713);

        EditText year_714 = findViewById(R.id.year__714);
        EditText month_714 = findViewById(R.id.month__714);
        EditText student_714 = findViewById(R.id.student__714);

// Hide all EditTexts initially
        year_700.setVisibility(View.GONE);
        month_700.setVisibility(View.GONE);
        student_700.setVisibility(View.GONE);

        year_709.setVisibility(View.GONE);
        month_709.setVisibility(View.GONE);
        student_709.setVisibility(View.GONE);

        year_710.setVisibility(View.GONE);
        month_710.setVisibility(View.GONE);
        student_710.setVisibility(View.GONE);

        year_711.setVisibility(View.GONE);
        month_711.setVisibility(View.GONE);
        student_711.setVisibility(View.GONE);

        year_712.setVisibility(View.GONE);
        month_712.setVisibility(View.GONE);
        student_712.setVisibility(View.GONE);

        year_713.setVisibility(View.GONE);
        month_713.setVisibility(View.GONE);
        student_713.setVisibility(View.GONE);

        year_714.setVisibility(View.GONE);
        month_714.setVisibility(View.GONE);
        student_714.setVisibility(View.GONE);


// Show the layout based on survey_id
            switch (resumeModel.survey_id) {
                case 1:
                    layout1.setVisibility(View.VISIBLE);
                    student_700.setVisibility(View.VISIBLE);
                    student_709.setVisibility(View.VISIBLE);
                    student_710.setVisibility(View.VISIBLE);
                    student_711.setVisibility(View.VISIBLE);
                    student_712.setVisibility(View.VISIBLE);
                    student_713.setVisibility(View.VISIBLE);
                    student_714.setVisibility(View.VISIBLE);

                    break;
                case 2:
                    layout2.setVisibility(View.VISIBLE);
                    year_700.setVisibility(View.VISIBLE); // Ensure this is intentional
                    year_709.setVisibility(View.VISIBLE);
                    year_710.setVisibility(View.VISIBLE);
                    year_711.setVisibility(View.VISIBLE);
                    year_712.setVisibility(View.VISIBLE);
                    year_713.setVisibility(View.VISIBLE);
                    year_714.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    layout3.setVisibility(View.VISIBLE);
                    month_700.setVisibility(View.VISIBLE);
                    month_700.setVisibility(View.VISIBLE); // Ensure this is intentional
                    month_709.setVisibility(View.VISIBLE);
                    month_710.setVisibility(View.VISIBLE);
                    month_711.setVisibility(View.VISIBLE);
                    month_712.setVisibility(View.VISIBLE);
                    month_713.setVisibility(View.VISIBLE);
                    month_714.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    layout4.setVisibility(View.VISIBLE);
                    month_700.setVisibility(View.VISIBLE);
                    month_709.setVisibility(View.VISIBLE);
                    month_710.setVisibility(View.VISIBLE);
                    month_711.setVisibility(View.VISIBLE);
                    month_712.setVisibility(View.VISIBLE);
                    month_713.setVisibility(View.VISIBLE);
                    month_714.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    layout5.setVisibility(View.VISIBLE);
                    month_700.setVisibility(View.VISIBLE); // Ensure this is intentional
                    month_709.setVisibility(View.VISIBLE);
                    month_710.setVisibility(View.VISIBLE);
                    month_711.setVisibility(View.VISIBLE);
                    month_712.setVisibility(View.VISIBLE);
                    month_713.setVisibility(View.VISIBLE);
                    month_714.setVisibility(View.VISIBLE);
                    break;
                default:
                    // Handle cases where survey_id is not in range 1-5
                    break;
            }
        }
        }*/

        // validate code __702


        private int parseInteger (String value){
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    private void addPercentageCalculation(EditText totalEditText, EditText valueEditText, EditText percentEditText) {
        TextWatcher textWatcher = new TextWatcher() {
            private boolean isUpdating = false; // Prevents infinite loop

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating) {
                    isUpdating = true;
                    updatePercentage();
                    isUpdating = false;
                }
            }

            private void updatePercentage() {
                String totalStr = totalEditText.getText().toString().trim().replace(",", ".");
                String valueStr = valueEditText.getText().toString().trim().replace(",", ".");

                if (isValidNumber(totalStr) && isValidNumber(valueStr)) {
                    try {
                        double total = Double.parseDouble(totalStr);
                        double value = Double.parseDouble(valueStr);

                        if (total > 0) {
                            double percentage = (value / total) * 100;

                            // Round the percentage to the nearest whole number
                            int roundedPercentage = (int) Math.round(percentage);
                            String roundedPercentageStr = String.valueOf(roundedPercentage);

                            // Update only if different
                            if (!roundedPercentageStr.equals(percentEditText.getText().toString().trim())) {
                                percentEditText.setText(roundedPercentageStr);
                                percentEditText.setSelection(roundedPercentageStr.length());
                            }
                        } else {
                            setDefaultPercentage();
                        }
                    } catch (NumberFormatException e) {
                        setDefaultPercentage();
                    }
                } else {
                    setDefaultPercentage();
                }
            }

            private void setDefaultPercentage() {
                percentEditText.setText("0");
                percentEditText.setSelection(1); // Move cursor to end
            }

            private boolean isValidNumber(String str) {
                return str.matches("-?\\d+(\\.\\d+)?"); // Matches integers & decimals
            }
        };

        totalEditText.addTextChangedListener(textWatcher);
        valueEditText.addTextChangedListener(textWatcher);
    }


}



