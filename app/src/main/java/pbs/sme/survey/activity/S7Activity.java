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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    EditText student_701 ,student_702, student_703,student_704,student_705,student_706,student_707,student_708, student_700, student_709, student_710 ,student_711, student_712, student_713, student_714;

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
        setDrawer(this, "Section 7: OUTPUT/SALES ");
        setParent(this, S8Activity.class);
        scrollView = findViewById(R.id.scrollView);

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
        student_701 = findViewById(R.id.student__701);
        student_702 = findViewById(R.id.student__702);
        student_703 = findViewById(R.id.student__703); // Percentage Field
        student_704 = findViewById(R.id.student__704);
        student_705 = findViewById(R.id.student__705);
        student_706 = findViewById(R.id.student__706);
        student_707= findViewById(R.id.student__707);
        student_708= findViewById(R.id.student__708);
        init();
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
        for (int i = 0; i < codeList.length -6; i++) {
            EditText et = findViewById(getResources().getIdentifier("student__" + codeList[i], "id", getPackageName()));
            if (et != null) {
                et.removeTextChangedListener(additionTextWatcherstudent);
                et.addTextChangedListener(additionTextWatcherstudent);
            }
        }
        for (int i = 0; i < codeList.length - 6; i++) {
            EditText et = findViewById(getResources().getIdentifier("month__" + codeList[i], "id", getPackageName()));
            if (et != null) {
                et.removeTextChangedListener(additionTextWatchermonth);
                et.addTextChangedListener(additionTextWatchermonth);
            }
        }

        for (int i = 0; i < codeList.length - 6; i++) {
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
        sbtn.setEnabled(false); // Prevent duplicate submissions
        List<Section47> list = new ArrayList<>();

        // --- Survey ID 1 Specific Validation ---
        // For survey_id==1, ensure that among EditTexts from student__1715 to student__1723 and
        // year__1715 to year__1723, at least two fields contain values greater than zero.
        if (resumeModel != null && resumeModel.survey_id == 1) {
            int validCount = 0;
            Log.d("Validation", "Starting survey_id==1 validation from student__1715 to student__1723");

            // Iterate from 1715 to 1723 (inclusive)
            for (int i = 1715; i <= 1723; i++) {
                String studentIdStr = "student__" + i;
                String yearIdStr = "year__" + i;

                int studentResId = getResources().getIdentifier(studentIdStr, "id", getPackageName());
                int yearResId = getResources().getIdentifier(yearIdStr, "id", getPackageName());

                // Get the EditText for student field
                EditText studentField = (EditText) findViewById(studentResId);
                // Get the EditText for year field
                EditText yearField = (EditText) findViewById(yearResId);

                if (studentField != null) {
                    studentField.clearFocus(); // Ensure latest value is committed
                    String studentText = studentField.getText().toString().trim();
                    Log.d("Validation", studentIdStr + " found with value: " + studentText);
                    try {
                        int studentValue = Integer.parseInt(studentText);
                        if (studentValue > 0) {
                            validCount++;
                        }
                    } catch (NumberFormatException e) {
                        Log.d("Validation", "Error parsing " + studentIdStr + ": " + e.getMessage());
                    }
                } else {
                    Log.d("Validation", "EditText " + studentIdStr + " not found, resId: " + studentResId);
                }

                if (yearField != null) {
                    yearField.clearFocus(); // Ensure latest value is committed
                    String yearText = yearField.getText().toString().trim();
                    Log.d("Validation", yearIdStr + " found with value: " + yearText);
                    try {
                        int yearValue = Integer.parseInt(yearText);
                        if (yearValue > 0) {
                            validCount++;
                        }
                    } catch (NumberFormatException e) {
                        Log.d("Validation", "Error parsing " + yearIdStr + ": " + e.getMessage());
                    }
                } else {
                    Log.d("Validation", "EditText " + yearIdStr + " not found, resId: " + yearResId);
                }
            }
            Log.d("Validation", "Total valid (nonzero) count: " + validCount);

            if (validCount < 2) {
                mUXToolkit.showAlertDialogue(
                        "درست معلومات درکار",
                        "سروے آئی ڈی 1 کے لیے، 1715 تا 1723 میں سے کم از کم دو فیلڈز میں غیر صفر ویلیو ہونی چاہیے۔ براہ کرم درست معلومات درج کریں۔",
                        null
                );
                sbtn.setEnabled(true);
                return;
            }
        }
// --- Check fields from "month__701" to "month__708" and "year__701" to "year__708" to ensure at least one field has a non-zero value ---
        int nonZeroCount = 0;

// Check month fields from "month__701" to "month__708"
        for (int i = 701; i <= 708; i++) {
            String monthIdStr = "month__" + i;  // month__701, month__702, ..., month__708
            int monthResId = getResources().getIdentifier(monthIdStr, "id", getPackageName());

            EditText monthField = (EditText) findViewById(monthResId);
            if (monthField != null) {
                monthField.clearFocus(); // Ensure latest value is committed
                String monthText = monthField.getText().toString().trim();
                Log.d("Validation", monthIdStr + " found with value: " + monthText);

                try {
                    int monthValue = Integer.parseInt(monthText);
                    if (monthValue > 0) {
                        nonZeroCount++;
                    }
                } catch (NumberFormatException e) {
                    Log.d("Validation", "Error parsing " + monthIdStr + ": " + e.getMessage());
                }
            } else {
                Log.d("Validation", "EditText " + monthIdStr + " not found, resId: " + monthResId);
            }
        }

// Check year fields from "year__701" to "year__708"
        for (int i = 701; i <= 708; i++) {
            String yearIdStr = "year__" + i;  // year__701, year__702, ..., year__708
            int yearResId = getResources().getIdentifier(yearIdStr, "id", getPackageName());

            EditText yearField = (EditText) findViewById(yearResId);
            if (yearField != null) {
                yearField.clearFocus(); // Ensure latest value is committed
                String yearText = yearField.getText().toString().trim();
                Log.d("Validation", yearIdStr + " found with value: " + yearText);

                try {
                    int yearValue = Integer.parseInt(yearText);
                    if (yearValue > 0) {
                        nonZeroCount++;
                    }
                } catch (NumberFormatException e) {
                    Log.d("Validation", "Error parsing " + yearIdStr + ": " + e.getMessage());
                }
            } else {
                Log.d("Validation", "EditText " + yearIdStr + " not found, resId: " + yearResId);
            }
        }

// If no non-zero values found in the fields from "month__701" to "month__708" and "year__701" to "year__708"
        if (nonZeroCount == 0) {
            mUXToolkit.showAlertDialogue(
                    "درست معلومات درکار",
                    "کم از کم ایک مہینہ یا سال فیلڈ میں غیر صفر ویلیو ہونی چاہیے۔ براہ کرم درست معلومات درج کریں۔",
                    null
            );
            sbtn.setEnabled(true);
            return;
        }

        if (validateSection12EditChecks()) {
            // If validation passes, proceed with saving the form
            for (String c : codeList) {
                Section47 m = null;
                try {
                    m = (Section47) extractValidatedModelFromForm(this, m, true, inputValidationOrder,
                            c, Section47.class, false, this.findViewById(android.R.id.content));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (m == null) {
                    mUXToolkit.showAlertDialogue(
                            "Failed",
                            "فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔ خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔",
                            alertForEmptyFieldEvent
                    );
                    sbtn.setEnabled(true);
                    return;
                }
                list.add(m);
                setCommonFields(m);
                m.section = 7;
                m.code = c;
            }
            if (validateFields()) {
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
                btnn.callOnClick(); // Trigger any subsequent actions (e.g., navigation)
            } else {
                // If validation fails, show the error and allow the user to correct the form
                mUXToolkit.showAlertDialogue("درست معلومات درکار", "فارم میں کچھ غلطیاں ہیں۔ براہ کرم درست کریں اور دوبارہ کوشش کریں۔", null);
                sbtn.setEnabled(true);
            } // Save data or move to next screen


        }
        sbtn.setEnabled(true);

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
        TextView lastmonthTextView = findViewById(R.id.lmonth);


        // Hide all TextViews initially
        studentTextView.setVisibility(View.GONE);
        monthsTextView.setVisibility(View.GONE);
        yearTextView.setVisibility(View.GONE);
        lastmonthTextView.setVisibility(View.GONE);

        // Check if resumeModel is not null before proceeding
        if (resumeModel != null) {
            if (resumeModel.survey_id == 1) {
                // Show "Number of Students"
                studentTextView.setVisibility(View.VISIBLE);
                yearTextView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.reference)).setText("SECTION-7: OUTPUT/SALES DURING  " + yearTextView.getText());

            } else if (resumeModel.survey_id == 2) {
                // Show "Year"
                yearTextView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.reference)).setText("SECTION-7: OUTPUT/SALES DURING" + yearTextView.getText());

            } else if (resumeModel.survey_id == 4) {
                yearTextView.setVisibility(View.INVISIBLE);
                monthsTextView.setVisibility(View.INVISIBLE);
                lastmonthTextView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.reference)).setText("SECTION-7: OUTPUT/SALES DURING " + "Last Month");

            } else if (resumeModel.survey_id == 3 || resumeModel.survey_id == 5) {
                // Show "Last 3 Calendar Months"
                yearTextView.setVisibility(View.INVISIBLE);
                monthsTextView.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.reference)).setText("SECTION-7: OUTPUT/SALES DURING " + monthsTextView.getText());

            } else {
                // Default case: Show "2023-24"
                studentTextView.setVisibility(View.VISIBLE);
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
        LinearLayout commmon = findViewById(R.id.common);

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
        if (resumeModel.survey_id == 1 ) {

            for (String code : codeList) {
                EditText studentEt = findViewById(getResources().getIdentifier("student__" + code, "id", getPackageName()));
                if (studentEt != null) studentEt.setVisibility(View.VISIBLE);

            }
            for (String code : codeList) {
                EditText yearEt = findViewById(getResources().getIdentifier("year__" + code, "id", getPackageName()));
                if (yearEt != null) yearEt.setVisibility(View.VISIBLE);
            }
            for (String code : codeList) {
                EditText monthEt = findViewById(getResources().getIdentifier("month__" + code, "id", getPackageName()));
                if (monthEt != null) monthEt.setVisibility(View.GONE);
            }
        }
        // Show specific EditTexts based on survey_id
        if ( resumeModel.survey_id == 2) {
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
                student_701.setVisibility(View.INVISIBLE);
                student_702.setVisibility(View.INVISIBLE);
                student_703.setVisibility(View.INVISIBLE);
                student_704.setVisibility(View.INVISIBLE);
                student_705.setVisibility(View.INVISIBLE);
                student_706.setVisibility(View.INVISIBLE);
                student_708.setVisibility(View.INVISIBLE);
                student_707.setVisibility(View.INVISIBLE);
                student_709.setVisibility(View.INVISIBLE);
                student_710.setVisibility(View.INVISIBLE);
                student_711.setVisibility(View.INVISIBLE);
                student_712.setVisibility(View.INVISIBLE);
                student_713.setVisibility(View.INVISIBLE);
                student_714.setVisibility(View.INVISIBLE);
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
    private boolean validateSection12EditChecks() {
        // Check if the section12 object is null, if so, return true (validation passes)
        if (resumeModel.survey_id == null || resumeModel.major_activities == null) {
            showError("Major Activities is not selected.");
            return false;
        }

        int surveyId = resumeModel.survey_id;  // Get survey ID
        int majorActivity = resumeModel.major_activities;  // Get major activity ID

        // ---------- Survey 1 ----------
        if (surveyId == 1) {
            switch (majorActivity) {
                case 1:
                    if (!isFilled("year__1715") || !isFilled("student__1715")) {
                        showError("سال 1715 اور طالبعلم 1715 دونوں درج ہونے چاہئیں۔");
                        return false;  // Validation fails if fields are not filled
                    }
                    break;
                case 2:
                    if (!isFilled("year__1716") || !isFilled("student__1716")) {
                        showError("سال 1716 اور طالبعلم 1716 دونوں درج ہونے چاہئیں۔");
                        return false;
                    }
                    break;
                case 3:
                    if ((!isFilled("year__1717") && !isFilled("year__1718")) ||
                            (!isFilled("student__1717") && !isFilled("student__1718"))) {
                        showError("سال یا طالبعلم 1717 یا 1718 میں سے کم از کم ایک پر کریں۔");
                        return false;
                    }
                    break;
                case 4:
                    if ((!isFilled("year__1719") && !isFilled("year__1720")) ||
                            (!isFilled("student__1719") && !isFilled("student__1720"))) {
                        showError("سال یا طالبعلم 1719 یا 1720 میں سے کم از کم ایک پر کریں۔");
                        return false;
                    }
                    break;
                case 7: case 8: case 9:
                    if (!isFilled("year__1721") || !isFilled("student__1721")) {
                        showError("سال 1721 اور طالبعلم 1721 لازمی درج ہوں۔");
                        return false;
                    }
                    break;
            }

            boolean anyFilled = false;
            for (int i = 1715; i <= 1723; i++) {
                if (isFilled("year__" + i) || isFilled("student__" + i)) {
                    anyFilled = true;
                    break;
                }
            }
            if (!anyFilled) {
                showError("1715 تا 1723 میں سے کم از کم ایک فیلڈ پر کریں۔");
                return false;
            }
        }

        // ---------- Survey 2 ----------
        else if (surveyId == 2) {
            switch (majorActivity) {
                case 1:
                    for (int i = 2715; i <= 2724; i++) {
                        if (isFilled("year__" + i)) return true;
                    }
                    showError("سال 2715 تا 2724 میں سے کم از کم ایک پر کریں۔");
                    return false;

                case 2:
                    if (!isFilled("year__2716") || !isFilled("year__2717")) {
                        showError("سال 2716 اور 2717 دونوں درج کریں۔");
                        return false;
                    }
                    break;
                case 3:
                    if (!isFilled("year__2720")) {
                        showError("سال 2720 لازمی درج کریں۔");
                        return false;
                    }
                    break;
                case 4: case 5:
                    if (!isFilled("year__2721") && !isFilled("year__2722")) {
                        showError("سال 2721 یا 2722 میں سے ایک پر کریں۔");
                        return false;
                    }
                    break;
                case 6: case 7:
                    if (!isFilled("year__2722") && !isFilled("year__2723")) {
                        showError("سال 2722 یا 2723 میں سے ایک پر کریں۔");
                        return false;
                    }
                    break;
            }
        }

        // ---------- Survey 3 ----------
        else if (surveyId == 3) {
            if ((majorActivity == 1 || majorActivity == 2) &&
                    (!isFilled("month__3715") || !isFilled("month__3719"))) {
                showError("مہینہ 3715 اور 3719 دونوں درج کریں۔");
                return false;
            }

            if (majorActivity == 3 && !isFilled("month__3716")) {
                showError("مہینہ 3716 لازمی پر کریں۔");
                return false;
            }

            if (majorActivity == 4) {
                for (int i = 3717; i <= 3732; i++) {
                    if (!isFilled("month__" + i)) {
                        showError("مہینہ " + i + " لازمی پر کریں۔");
                        return false;
                    }
                }
            }
        }

        // ---------- Survey 4 ----------
        else if (surveyId == 4) {
            if (majorActivity == 1 && !isFilled("month__4715")) {
                showError("مہینہ 4715 درج کریں۔");
                return false;
            }
            if (majorActivity == 2 && !isFilled("month__4716")) {
                showError("مہینہ 4716 درج کریں۔");
                return false;
            }
        }

        // ---------- Survey 5 ----------
        else if (surveyId == 5) {
            if (majorActivity >= 1 && majorActivity <= 3) {
                int count = 0;
                for (int i = 5715; i <= 5719; i++) {
                    if (isFilled("month__" + i)) count++;
                }
                if (isFilled("month__5722")) count++;
                if (isFilled("month__5725")) count++;

                if (count < 2) {
                    showError("کم از کم دو مہینے 5715 تا 5719، 5722، 5725 میں سے پر کریں۔");
                    return false;
                }
            }

            if (majorActivity == 4 &&
                    (!isFilled("month__5720") || !isFilled("month__5721") || !isFilled("month__5725"))) {
                showError("ماہ 5720، 5721، 5725 لازمی درج ہوں۔");
                return false;
            }

            if (majorActivity == 5 && !isFilled("month__5723")) {
                showError("مہینہ 5723 لازمی درج کریں۔");
                return false;
            }

            if (majorActivity == 6) {
                boolean oneFilled = false;
                for (int i = 5715; i <= 5725; i++) {
                    if (isFilled("month__" + i)) {
                        oneFilled = true;
                        break;
                    }
                }
                if (!oneFilled) {
                    showError("مہینوں 5715 تا 5725 میں سے کم از کم ایک پر کریں۔");
                    return false;
                }
            }

            if (majorActivity == 7 &&
                    (!isFilled("month__5724") || !isFilled("month__5725"))) {
                showError("مہینہ 5724 اور 5725 دونوں پر کریں۔");
                return false;
            }
        }

        return true;  // All checks passed, validation successful
    }

    private boolean isFilled(String fieldId) {
        int resId = getResources().getIdentifier(fieldId, "id", getPackageName());
        if (resId == 0) return false; // View ID not found

        View view = findViewById(resId);
        if (view instanceof EditText) {
            String value = ((EditText) view).getText().toString().trim();
            if (value.isEmpty()) return false; // Field is empty

            try {
                // Ensure the value is an integer
                int number = Integer.parseInt(value);
                return number != 0; // Not zero
            } catch (NumberFormatException e) {
                return false; // Not a valid integer
            }
        }

        return false; // Not an EditText
    }



//    private boolean isFilled(String fieldId) {
//        int resId = getResources().getIdentifier(fieldId, "id", getPackageName());
//        if (resId == 0) return false; // ID not found
//
//        View view = findViewById(resId);
//        if (view instanceof EditText) {
//            String value = ((EditText) view).getText().toString().trim();
//            return !value.isEmpty();
//        }
//
//        return false; // Not an EditText or view not found
//    }

    private void showError(String message) {
        mUXToolkit.showAlertDialogue("درست معلومات درکار", message, null);
    }

    private boolean validateFields() {
        try {
            long totalYear = GetInteger(year_700.getText().toString().trim());
            long partYear = GetInteger(year_709.getText().toString().trim());
            long partYear11 = GetInteger(year_711.getText().toString().trim());
            long partYear13 = GetInteger(year_713.getText().toString().trim());

            long totalMonth = GetInteger(month_700.getText().toString().trim());
            long  partMonth = GetInteger(month_709.getText().toString().trim());
            long  partMonth11 = GetInteger(month_711.getText().toString().trim());
            long  partMonth13 = GetInteger(month_713.getText().toString().trim());

            long totalStudent = GetInteger(student_700.getText().toString().trim());
            long partStudent = GetInteger(student_709.getText().toString().trim());
            long partStudent11 = GetInteger(student_711.getText().toString().trim());
            long partStudent13 = GetInteger(student_713.getText().toString().trim());

            if (partYear > totalYear) {
                year_709.setError("Value cannot be greater than total year");
                return false;
            }

            if (partYear11 > totalYear) {
                year_711.setError("Value cannot be greater than total year");
                return false;
            }
            if (partYear13 > totalYear) {
                year_713.setError("Value cannot be greater than total year");
                return false;
            }

            if (partMonth > totalMonth) {
                month_709.setError("Value cannot be greater than total month");
                return false;
            }
            if (partMonth11 > totalMonth) {
                month_711.setError("Value cannot be greater than total month");
                return false;
            }
            if (partMonth13 > totalMonth) {
                month_713.setError("Value cannot be greater than total month");
                return false;
            }

            if (partStudent > totalStudent) {
                student_709.setError("Value cannot be greater than total student");
                return false;
            }
            if (partStudent11 > totalStudent) {
                student_711.setError("Value cannot be greater than total student");
                return false;
            }
            if (partStudent13 > totalStudent) {
                student_713.setError("Value cannot be greater than total student");
                return false;
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private Long GetInteger(String txt){
        try {
            return Long.parseLong(txt);
        }
        catch (Exception e) {
            return 0L;
        }
    }

}



