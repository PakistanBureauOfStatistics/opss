package pbs.sme.survey.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.helper.AdditionTextWatcher;
import pbs.sme.survey.model.Section8;
import pbs.sme.survey.model.Section8;
import pk.gov.pbs.utils.StaticUtils;

public class S8Activity extends FormActivity {

    private Button sbtn;
    private List<Section8> modelDatabase;

    private final String[] inputValidationOrder= new String[]{
            //"value"
            "acq_fixed_assets", "major_improvements", "sales_proceeds", "own_account_capital",
            "exp_life", "scrap_value"
    };

    private final String[] codeList= new String[]{
            "801","802","803","804","805","806","807","808", "809",
            "810","811","812","813"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s8);
        setDrawer(this,"Section 8: GROSS FIXED CAPITAL FORMATION");
        setParent(this, S9Activity.class);
        scrollView = findViewById(R.id.scrollView);

        for(String property : inputValidationOrder){
            for (String code : codeList) {
                if (!property.equalsIgnoreCase("exp_life")) {
                    EditText et = (EditText) findViewById(getResources().getIdentifier(property+"__"+code, "id", getPackageName()));
                    EditText total300 = findViewById(getResources().getIdentifier(property+"__800", "id", getPackageName()));
                    et.addTextChangedListener(new AdditionTextWatcher(total300));
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
        List<Section8> list=new ArrayList<>();

        for(int i = 0; i < codeList.length; i++) {

            Section8 m = null;
            if(modelDatabase != null && modelDatabase.size() == codeList.length){
                m = modelDatabase.get(i);
            }

            try {
                m = (Section8) extractValidatedModelFromForm(this, m, true, inputValidationOrder, codeList[i], Section8.class, false, this.findViewById(android.R.id.content));
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
        modelDatabase= dbHandler.query(Section8.class," uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        for(Section8 s: modelDatabase){
            setFormFromModel(this, s, inputValidationOrder, s.code, false, this.findViewById(android.R.id.content));
        }

    }
}