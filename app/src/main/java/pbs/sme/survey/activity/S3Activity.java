package pbs.sme.survey.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Section3;
import pk.gov.pbs.utils.StaticUtils;

public class S3Activity extends FormActivity {

    private Button sbtn;
    private CheckBox duration1, duration2, duration3;
    private List<Section3> modelDatabase;

    private final String[] inputValidationOrder= new String[]{
           "male", "female", "wages", "other_cash_payment", "payment_in_kind"
    };

    private final String[] codeList= new String[]{
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s3);
        setParent(this, S4Activity.class);
        scrollView = findViewById(R.id.scrollView);
        duration1 = findViewById(R.id.duration1);
        duration2 = findViewById(R.id.duration2);
        duration3 = findViewById(R.id.duration3);
        if(resumeModel.survey_id==4)
            duration1.setChecked(true);
        else if (resumeModel.survey_id==5)
            duration2.setChecked(true);
        else
            duration3.setChecked(true);

        for(String property : inputValidationOrder){
            for (String code : codeList) {
                EditText et = (EditText) findViewById(getResources().getIdentifier(property+"__"+code, "id", getPackageName()));
                String resourceName = getResources().getResourceEntryName(et.getId());
                if(resourceName.contains("ale")){
                                EditText total = findViewById(getResources().getIdentifier("persons__"+ code, "id", getPackageName()));
                }
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
        List<Section3> list=new ArrayList<>();

        for(int i = 0; i < codeList.length; i++) {

            Section3 m = null;
            if(modelDatabase != null && modelDatabase.size() == codeList.length){
                m = modelDatabase.get(i);
            }

            try {
                m = (Section3) extractValidatedModelFromForm(this, m, true, inputValidationOrder, codeList[i], Section3.class, false, this.findViewById(android.R.id.content));
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
        modelDatabase= dbHandler.query(Section3.class," uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        for(Section3 s: modelDatabase){
            setFormFromModel(this, s, inputValidationOrder, s.code, false, this.findViewById(android.R.id.content));
            EditText total = findViewById(getResources().getIdentifier("persons__"+ s.code, "id", getPackageName()));
            total.setText(String.valueOf(s.getPersons() ));
        }

    }

    private int GetInteger(String txt){
        try {
            return Integer.parseInt(txt);
        }
        catch (Exception e) {
            return 0;
        }
    }
}