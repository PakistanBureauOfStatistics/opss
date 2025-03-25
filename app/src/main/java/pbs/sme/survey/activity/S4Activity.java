package pbs.sme.survey.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.helper.AdditionTextWatcher;
import pbs.sme.survey.model.Section47;
import pbs.sme.survey.R;
//import pbs.sme.survey.helper.AdditionTextWatcher;
import pk.gov.pbs.utils.StaticUtils;

public class S4Activity extends FormActivity {

    private Button sbtn;
    private List<Section47> modelDatabase;

    private final String[] inputValidationOrder= new String[]{
            "month","student","year"
    };

    private final String[] codeList= new String[]{
            "401","402","403","404","405","406","407","408","409",
            "410","411","412","413","414","415","416","417","418","419",
            "420","421","422","423","424","425","1426","1427","1428","1429",
            "1430","1431","1432","1433","1434","1435","1436", "1437","2426","2427","2428","2429",
            "2430","2431","2432","2433","2434","2435","2436","3426","3427","3428","3429",
            "3430","3431","3432","3433","4426","4427","4428","4429",
            "4430","4431","4432","5426","5427","5428","5429",
            "5430","5431","5432","5433","5434","5435","5436","5437","5438","5439"
            ,"5440","5441","5442","5443","5444","400"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);
        setDrawer(this,"Section 4: INTERMEDIATE INPUTS DURING LAST YEAR ");
        setParent(this, S5Activity.class);
        scrollView = findViewById(R.id.scrollView);
        init();

        EditText totalEditTextyear = findViewById(R.id.year__400);
        EditText totalEditTextmonth = findViewById(R.id.month__400);
        AdditionTextWatcher additionTextWatchermonth = new AdditionTextWatcher(totalEditTextmonth);
        AdditionTextWatcher additionTextWatcheryear= new AdditionTextWatcher(totalEditTextyear);
        for(int i = 0; i < codeList.length-1; i++) {
            EditText et = findViewById(getResources().getIdentifier("month__"+codeList[i], "id", getPackageName()));
            if (et!=null) {
                et.removeTextChangedListener(additionTextWatchermonth);
                et.addTextChangedListener(additionTextWatchermonth);
            }
        }

        for(int i = 0; i < codeList.length-1; i++) {
            EditText et = findViewById(getResources().getIdentifier("year__"+codeList[i], "id", getPackageName()));
            if (et!=null) {
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
    private void init() {
        // Get references to all layouts
        LinearLayout layout1 = findViewById(R.id.survey1);
        LinearLayout layout2 = findViewById(R.id.survey2);
        LinearLayout layout3 = findViewById(R.id.survey3);
        LinearLayout layout4 = findViewById(R.id.survey4);
        LinearLayout layout5 = findViewById(R.id.survey5);
        TextView v1=findViewById(R.id.month);
        TextView v2=findViewById(R.id.year);
        // Hide all layouts initially
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);
        layout4.setVisibility(View.GONE);
        layout5.setVisibility(View.GONE);
        if(resumeModel.survey_id==1||resumeModel.survey_id==2)
        {
            v2.setVisibility(View.VISIBLE);
            v1.setVisibility(View.GONE);
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
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.GONE);
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
        // Find EditText elements once to avoid duplicate calls
        EditText year_400 = findViewById(R.id.year__400);
        EditText month_400 = findViewById(R.id.month__400);

// Hide EditTexts initially
        year_400.setVisibility(View.GONE);
        month_400.setVisibility(View.GONE);

// Show the layout based on survey_id
        switch (resumeModel.survey_id) {
            case 1:
                layout1.setVisibility(View.VISIBLE);
                year_400.setVisibility(View.VISIBLE);
                break;
            case 2:
                layout2.setVisibility(View.VISIBLE);
                year_400.setVisibility(View.VISIBLE); // Ensure this is intentional
                break;
            case 3:
                layout3.setVisibility(View.VISIBLE);
                month_400.setVisibility(View.VISIBLE);
                break;
            case 4:
                layout4.setVisibility(View.VISIBLE);
                month_400.setVisibility(View.VISIBLE);
                break;
            case 5:
                layout5.setVisibility(View.VISIBLE);
                month_400.setVisibility(View.VISIBLE);
                break;
            default:
                // Handle cases where survey_id is not in range 1-5
                break;
        }
    }
    private void saveForm() {
        sbtn.setEnabled(false);
        List<Section47> list=new ArrayList<>();

        for(int i = 0; i < codeList.length; i++) {

            Section47 m = null;
            if(modelDatabase != null && modelDatabase.size() == codeList.length){
                m = modelDatabase.get(i);
            }

            try {
                m = (Section47) extractValidatedModelFromForm(this, m, true, inputValidationOrder, codeList[i], Section47.class, false, this.findViewById(android.R.id.content));
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
            m.section=4;
            m.code=codeList[i];

        }


        /////TODO CHECKS////////////////////////////


        List<Long> iid = dbHandler.replace(list);
        for(Long i:iid){
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


    private void loadForm(){
        List<Section47> list= dbHandler.query(Section47.class,"section="+4+" AND uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        for(Section47 s: list){
            setFormFromModel(this, s, inputValidationOrder, s.code, false, this.findViewById(android.R.id.content));
        }

    }
}