package pbs.sme.survey.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.helper.AdditionTextWatcher;
import pbs.sme.survey.model.Section47;
import pk.gov.pbs.utils.StaticUtils;

public class S7Activity extends FormActivity {
    private Button sbtn;
    private List<Section47> modelDatabase;

    private final String[] inputValidationOrder = new String[]{
            "month", "year", "student"
    };

    private final String[] codeList = new String[]{
            "701", "702", "703", "704", "705", "706", "707", "708", "709", "710",
            "711", "712", "713", "714", "715", "716", "717", "718", "719", "720",
            "721", "722", "723", "724", "725", "726", "727", "728", "729", "730",
            "731", "732", "733", "734", "735", "736", "737", "738", "739", "740",
            "741","1715","1716","1717","1718","1719","1720","1721","1722","1723",
            "2715","2716","2717","2718","2719","2720","2721","2722","2723","2724",
            "3715","3716","3717","3718","3719","3720","3721","3722","3723","3724",
            "3725","3726","3727","3728","3729","3730","3731","3732","3733","3734",
            "3735","3736","3737","3738","3739","3740","3741","4715","4716","5715",
            "5716","5717","5718","5719","5720","5721","5722","5723","5724","5725",
            "5709","5711","5713"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();

        EditText totalEditTextyear = findViewById(R.id.year__700);
        EditText totalEditTextmonth = findViewById(R.id.month__700);
        AdditionTextWatcher additionTextWatchermonth = new AdditionTextWatcher(totalEditTextmonth);
        AdditionTextWatcher additionTextWatcheryear = new AdditionTextWatcher(totalEditTextyear);
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);
        setDrawer(this, "Section 7: Quantity/Value of Fish Catch/Sold LY2023-24");
        setParent(this, S8Activity.class);
        scrollView = findViewById(R.id.scrollView);

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
        if(resumeModel.survey_id==1||resumeModel.survey_id==2)
        {
            for (int i = 0; i < codeList.length-1; i++) {
                String id = "year__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

            for (int i = 0; i < codeList.length-1; i++) {
                String id = "month__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

        }
        if(resumeModel.survey_id==3||resumeModel.survey_id==4||resumeModel.survey_id==5)
        {
            for (int i = 0; i < codeList.length; i++) {
                String id = "year__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.GONE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

            for (int i = 0; i < codeList.length; i++) {
                String id = "month__" + codeList[i];
                EditText et = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
                if (et != null) {
                    et.setVisibility(View.VISIBLE);
                } else {
                    Log.e("S4Activity", "EditText not found for ID: " + id);
                }
            }

        }
        // Show the layout based on the surveyid
        switch (resumeModel.survey_id) {
            case 1:
                layout1.setVisibility(View.VISIBLE);  // Show layout1

                break;
            case 2:
                layout2.setVisibility(View.VISIBLE);  // Show layout2
                break;
            case 3:
                layout3.setVisibility(View.VISIBLE);  // Show layout3

                break;
            case 4:
                layout4.setVisibility(View.VISIBLE);  // Show layout4
                break;
            case 5:
                layout5.setVisibility(View.VISIBLE);  // Show layout5
                break;
            default:
                // Optional: Handle cases where surveyid is not in the 1-5 range
                break;
        }

    }
    // validate code __702


    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}


