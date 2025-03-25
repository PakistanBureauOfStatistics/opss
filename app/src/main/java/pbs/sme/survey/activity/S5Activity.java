package pbs.sme.survey.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Section12;
import pbs.sme.survey.model.Section5;
import pk.gov.pbs.utils.StaticUtils;

public class S5Activity extends FormActivity {

    private Button sbtn;
    private Section5 modelDatabase;
    private Section12 section12Database;

    private final String[] inputValidationOrder= new String[]{
            "is_digital", "domestic", "international", "amount", "percent"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);
        setDrawer(this,"Section 5: DIGITAL INTERMEDIATION");
        setParent(this, S6Activity.class);
        scrollView = findViewById(R.id.scrollView);

        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });

        RadioGroup is_digital = findViewById(R.id.is_digital);
        LinearLayout layout_section5_yes = findViewById(R.id.layout_section5_yes);
        is_digital.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.is_digital2) {
                layout_section5_yes.setVisibility(View.GONE);
            }
            else{
                if ((section12Database.email==null && section12Database.website==null) || (section12Database.email.isEmpty() && section12Database.website.isEmpty())){
                    mUXToolkit.showAlertDialogue("Missing Data Alert","Kindly enter Email or Website in Section 1 (Basic Info).");
                }
                layout_section5_yes.setVisibility(View.VISIBLE);
            }
        });

        RadioGroup located = findViewById(R.id.located);
        located.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.located2) {
                findViewById(R.id.layout_international).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_domestic).setVisibility(View.GONE);
            }
            else{
                findViewById(R.id.layout_international).setVisibility(View.GONE);
                findViewById(R.id.layout_domestic).setVisibility(View.VISIBLE);
            }
        });

        RadioGroup payment_method = findViewById(R.id.payment_method);
        payment_method.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.payment_method2) {
                findViewById(R.id.layout_percent).setVisibility(View.VISIBLE);
                findViewById(R.id.layout_amount).setVisibility(View.GONE);
            }
            else{
                findViewById(R.id.layout_percent).setVisibility(View.GONE);
                findViewById(R.id.layout_amount).setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadForm() {
        List<Section12> s1= dbHandler.query(Section12.class,"uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        if(s1.size() == 1){
            section12Database = s1.get(0);
        }

        List<Section5> l= dbHandler.query(Section5.class," uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        if(l.size() == 1){
            modelDatabase = l.get(0);
            //Part1TextWatcher.IGNORE_TEXT_WATCHER = true;
            setFormFromModel(this, modelDatabase, inputValidationOrder, "", false, this.findViewById(android.R.id.content));
            if (modelDatabase.international != null) {
                ((RadioButton)findViewById(R.id.located2)).setChecked(true);
            }
            else {
                ((RadioButton)findViewById(R.id.located1)).setChecked(true);
            }
            if (modelDatabase.percent != null) {
                ((RadioButton)findViewById(R.id.payment_method2)).setChecked(true);
            }
            else {
                ((RadioButton)findViewById(R.id.payment_method1)).setChecked(true);
            }
        }
    }

    private void saveForm() {
        sbtn.setEnabled(false);
        Section5 sec = null;

        if(modelDatabase!=null){
            sec=modelDatabase;
        }

        try {
            sec = (Section5) extractValidatedModelFromForm(this, sec,true, inputValidationOrder,"", Section5.class,false, this.findViewById(android.R.id.content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if (sec == null) {
            mUXToolkit.showAlertDialogue("Failed","فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔"  , alertForEmptyFieldEvent);
            sbtn.setEnabled(true);
            return;
        }

        setCommonFields(sec);
        /////TODO CHECKS////////////////////////////



        Long iid = dbHandler.replace(sec);

        if (iid != null && iid > 0) {
            mUXToolkit.showToast("Success");
            btnn.callOnClick();
        }else{
            mUXToolkit.showToast("Failed");
        }
        sbtn.setEnabled(true);
        btnn.callOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadForm();
    }
}