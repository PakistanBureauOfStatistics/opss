package pbs.sme.survey.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
            "rupees"
    };

    private final String[] codeList= new String[]{
            "901","902", "900"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s9);
        setDrawer(this,"Section 9: Inventories");
        setParent(this, BaseActivity.class);
        scrollView = findViewById(R.id.scrollView);
        for (String code : codeList) {
            if (!code.equals("900")) {
                EditText et = (EditText) findViewById(getResources().getIdentifier("rupees__"+code, "id", getPackageName()));
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        EditText et901 = findViewById(getResources().getIdentifier("rupees__901", "id", getPackageName()));
                        EditText et902 = findViewById(getResources().getIdentifier("rupees__902", "id", getPackageName()));
                        EditText total = findViewById(getResources().getIdentifier("rupees__900", "id", getPackageName()));
                        Long  Count901 = GetInteger(et901.getText().toString());
                        Long  Count902 = GetInteger(et902.getText().toString());
                        total.setText(String.valueOf(Count901 - Count902));
                    }
                });
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
    private Long GetInteger(String txt){
        try {
            return Long.parseLong(txt);
        }
        catch (Exception e) {
            return 0L;
        }
    }
}