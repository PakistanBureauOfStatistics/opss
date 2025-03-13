package pbs.sme.survey.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Section9;
import pk.gov.pbs.utils.StaticUtils;

public class S9Activity extends FormActivity {

    private Button sbtn;
    private List<Section9> modelDatabase;

    private final String[] inputValidationOrder= new String[]{
            "value"
    };

    private final String[] codeList= new String[]{
            "901","902"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s9);
        setDrawer(this,"Section 9: Inventories");
        setParent(this, BaseActivity.class);
        scrollView = findViewById(R.id.scrollView);

        /*EditText totalEditText = findViewById(R.id.value__900);

        AdditionTextWatcher additionTextWatcher = new AdditionTextWatcher(totalEditText);

        for(int i = 0; i < codeList.length-2; i++) {
            EditText et = findViewById(getResources().getIdentifier("value__"+codeList[i], "id", getPackageName()));
            et.removeTextChangedListener(additionTextWatcher);
            et.addTextChangedListener(additionTextWatcher);
        }*/

        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });
    }
    private void saveForm() {
        sbtn.setEnabled(false);
        List<Section9> list=new ArrayList<>();

        for(int i = 0; i < codeList.length; i++) {

            Section9 m = null;
            if(modelDatabase != null && modelDatabase.size() == codeList.length){
                m = modelDatabase.get(i);
            }

            try {
                m = (Section9) extractValidatedModelFromForm(this, m, true, inputValidationOrder, codeList[i], Section9.class, false, this.findViewById(android.R.id.content));
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
            m.section=9;
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
        modelDatabase= dbHandler.query(Section9.class," uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        for(Section9 s: modelDatabase){
            setFormFromModel(this, s, inputValidationOrder, s.code, false, this.findViewById(android.R.id.content));
        }

    }
}