package pbs.sme.survey.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Baseline;
import pk.gov.pbs.utils.StaticUtils;

public class BaseActivity extends FormActivity {

    private Button sbtn;
    private Baseline modelDatabase;

    private final String[] inputValidationOrder= new String[]{
    "a1","a2","a3","b1","b2","b3","b4","b5","b6","b6_other",
            "b7","b8","b9","b10","b10_other",
            "c1","c1_other","c2","c3","d1","d2","d2_other","d3","d4","d5",
            "d6","d7","d7_other","e1","e1_other","e2",
            "e3a","e3b","e3c","e3d","e3e",
            "e4",
            "e5a","e5b","e5b","e5d",
            "f1","f2","f3","f4","f5","f6","f6_other",
            "f7","f8","f9_other","f10",
            "f10_other",
            "f11a","f11b","f11c","f11d","f11e","f11f",
            "export1",
            "export2a","export2b","export2c","export2d","export2e","export2f","export2g","export2h",
            "export3a","export3b","export3c","export3d","export3e","export3f",
            "export4a","export4b","export4c","export4d","export4e","export4f",

            "export2_other","export5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setDrawer(this,"Baseline");
        setParent(this,HomeActivity.class);
        scrollView = findViewById(R.id.scrollView);

        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });
        init();

    }

    public void init(){
        RadioGroup b6=findViewById(R.id.b6);
        b6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.b6_other).getParent();
                if(checkedId==R.id.b66){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup b10=findViewById(R.id.b10);
        b10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.b10_other).getParent();
                if(checkedId==R.id.b104){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });


        RadioGroup c1=findViewById(R.id.c1);
        c1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.c1_other).getParent();
                if(checkedId==R.id.c16){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup d2=findViewById(R.id.d2);
        d2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.d2_other).getParent();
                if(checkedId==R.id.d24){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup d7=findViewById(R.id.d7);
        d7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.d7_other).getParent();
                if(checkedId==R.id.d76){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup e1=findViewById(R.id.e1);
        e1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.e1_other).getParent();
                if(checkedId==R.id.e14){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup f6=findViewById(R.id.f6);
        f6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.f6_other).getParent();
                if(checkedId==R.id.f66){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup f8=findViewById(R.id.f8);
        f8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup f9=(ViewGroup) findViewById(R.id.f9_other).getParent();
                ViewGroup f10=(ViewGroup) findViewById(R.id.f10).getParent();
                if(checkedId==R.id.f81){
                    f9.setVisibility(VISIBLE);
                    f10.setVisibility(GONE);
                }
                else{
                    f9.setVisibility(GONE);
                    f10.setVisibility(VISIBLE);
                }
            }
        });

        RadioGroup f10=findViewById(R.id.f10);
        f10.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.f10_other).getParent();
                if(checkedId==R.id.f106){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        CheckBox h2=findViewById(R.id.export2h);
        h2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.export_other).getParent();
                if(b){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });


        RadioGroup b1=findViewById(R.id.b1);
        b1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.b2).getParent();
                if(checkedId==R.id.b12){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup b2=findViewById(R.id.b2);
        b2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.b3).getParent();
                if(checkedId==R.id.b21){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });



        RadioGroup b9=findViewById(R.id.b9);
        b9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.b10).getParent();
                if(checkedId==R.id.b92){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup d1=findViewById(R.id.d1);
        d1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup parent=(ViewGroup) findViewById(R.id.d2).getParent();
                if(checkedId==R.id.d12){
                    parent.setVisibility(VISIBLE);
                }
                else{
                    parent.setVisibility(GONE);
                }
            }
        });

        RadioGroup ex1=findViewById(R.id.export1);
        ex1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ViewGroup e2=(ViewGroup) findViewById(R.id.export2a).getParent();
                ViewGroup e3=(ViewGroup) findViewById(R.id.export3a).getParent();
                if(checkedId==R.id.export11){
                    e2.setVisibility(VISIBLE);
                    e3.setVisibility(GONE);
                }
                else{
                    e2.setVisibility(GONE);
                    e3.setVisibility(VISIBLE);
                }
            }
        });
    }

    private void loadForm(){
        List<Baseline> s2= dbHandler.query(Baseline.class,"uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        if(s2.size() == 1){
            modelDatabase = s2.get(0);
            //Part1TextWatcher.IGNORE_TEXT_WATCHER = true;
            setFormFromModel(this, modelDatabase, inputValidationOrder, "", true, this.findViewById(android.R.id.content));
        }

    }



    private void saveForm() {
        sbtn.setEnabled(false);
        Baseline sec=null;

        if(modelDatabase!=null){
            sec=modelDatabase;
        }

        try {
            sec = (Baseline) extractValidatedModelFromForm(this, sec,true, inputValidationOrder,"", Baseline.class,true, this.findViewById(android.R.id.content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if (sec == null) {
            mUXToolkit.showAlertDialogue("Failed","فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔"  , alertForEmptyFieldEvent);
            sbtn.setEnabled(true);
            return;
        }

        /////TODO CHECKS////////////////////////////


        setCommonFields(sec);
        Long iid = dbHandler.replace(sec);

        if (iid != null && iid > 0) {
            mUXToolkit.showToast("Success");
            btnn.callOnClick();
        }else{
            mUXToolkit.showToast("Failed");
        }
        sbtn.setEnabled(true);
    }




    @Override
    protected void onResume() {
        super.onResume();
        loadForm();
    }
}