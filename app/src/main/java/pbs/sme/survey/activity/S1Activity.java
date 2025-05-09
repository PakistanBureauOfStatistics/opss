package pbs.sme.survey.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Section12;
import pk.gov.pbs.utils.StaticUtils;

public class S1Activity extends FormActivity {

    private Button sbtn;
    private Section12 section1_database;
    protected Spinner phone_type, phone_code, reason_no_phone;
    EditText phone_code2,phone_number;

    private final String[] inputValidationOrder= new String[]{
            "title","owner","owner_gender","name","designation","building_establishment","phone_type","phone_code","phone_number"
            ,"reason_no_phone"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);
        setDrawer(this,"Section 1: Basic Info");
        setParent(this, S2Activity.class);
        scrollView = findViewById(R.id.scrollView);

        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });

        init();


    }
    public void init(){
        Spinner respondentSpinner = findViewById(R.id.designation);

// Define the options
        String[] respondentOptions = {
                "Please Select:",
                "Owner",
                "Responsible Family Member",
                "Manager",
                "Other Employee"
        };

// Set up the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, respondentOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        respondentSpinner.setAdapter(adapter);
       // respondentSpinner.setSelection(0);

// Handle selection
        respondentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        Spinner buildingSpinner = findViewById(R.id.building_establishment);

// Define the options
        String[] buildingOptions = {
                "Please select:",
                "Owned",
                "Leased",
                "Rented",
                "Temporary (Khoka etc.)",
                "Movable (Street Vendor)"
        };

// Set up the adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingOptions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(adapter1);
        //buildingSpinner.setSelection(0);
// Handle selection
        buildingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        phone_code2 = findViewById(R.id.phone_code2);
        phone_number = findViewById(R.id.phone_number);
        phone_code = findViewById(R.id.phone_code);
        phone_type = findViewById(R.id.phone_type);
        reason_no_phone = findViewById(R.id.reason_no_phone);

        phone_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    phone_code.setEnabled(false);
                    phone_code2.setEnabled(false);
                    reason_no_phone.setEnabled(false);
                    phone_number.setEnabled(false);
                }
                else if (position == 1) {
                    if (phone_code2.getVisibility() == View.VISIBLE) {
                        //phone_code2.setText("");
                        phone_code2.setError(null);
                        phone_code2.setEnabled(false);
                        phone_code2.setVisibility(View.GONE);
                        phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                    }

                    if (reason_no_phone.getVisibility() == View.VISIBLE) {
                        //reason_no_phone.setSelection(0);
                        reason_no_phone.setEnabled(false);
                        reason_no_phone.setVisibility(View.GONE);
                        ((ViewGroup) reason_no_phone.getParent()).setVisibility(View.GONE);
                    }

                    if(phone_code.getVisibility()==View.GONE) {
                        phone_code.setEnabled(true);
                        //phone_code.setSelection(0);
                        phone_code.setVisibility(View.VISIBLE);
                        ((ViewGroup) phone_code.getParent()).setVisibility(View.VISIBLE);
                    }

                    if(phone_number.getVisibility()==View.GONE) {
                        phone_number.setEnabled(true);
                        //phone_number.setText("");
                        phone_number.setError(null);
                        phone_number.setVisibility(View.VISIBLE);
                    }

                } else if (position == 2) {
                    if (phone_code.getVisibility() == View.VISIBLE) {
                        //phone_code.setSelection(0);
                        phone_code.setEnabled(false);
                        phone_code.setVisibility(View.GONE);
                        ((ViewGroup) phone_code.getParent()).setVisibility(View.GONE);
                    }

                    if (reason_no_phone.getVisibility() == View.VISIBLE) {
                        //reason_no_phone.setSelection(0);
                        reason_no_phone.setEnabled(false);
                        reason_no_phone.setVisibility(View.GONE);
                        ((ViewGroup) reason_no_phone.getParent()).setVisibility(View.GONE);
                    }
                    if(phone_code2.getVisibility()==View.GONE) {
                        phone_code2.setEnabled(true);
                        //phone_code2.setText("");
                        phone_code2.setVisibility(View.VISIBLE);
                        phone_code2.setError(null);
                    }
                    if(phone_number.getVisibility()==View.GONE) {
                        phone_number.setEnabled(true);
                        //phone_number.setText("");
                        phone_number.setVisibility(View.VISIBLE);
                        phone_number.setError(null);
                    }

                    phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    StaticUtils.getHandler().post(()-> phone_code2.requestFocus());
                } else if (position == 3) {
                    if (phone_code.getVisibility() == View.VISIBLE) {
                        //phone_code.setSelection(0);
                        phone_code.setEnabled(false);
                        phone_code.setVisibility(View.GONE);
                        ((ViewGroup) phone_code.getParent()).setVisibility(View.GONE);
                    }

                    if (phone_code2.getVisibility() == View.VISIBLE){
                        //phone_code2.setText("");
                        phone_code2.setEnabled(false);
                        phone_code2.setVisibility(View.GONE);
                    }

                    if (phone_number.getVisibility() == View.VISIBLE){
                        //phone_number.setText("");
                        phone_number.setError(null);
                        phone_number.setEnabled(false);
                        phone_number.setVisibility(View.GONE);
                    }

                    if (reason_no_phone.getVisibility() == View.GONE) {
                        reason_no_phone.setEnabled(true);
                        //reason_no_phone.setSelection(0);
                        reason_no_phone.setVisibility(View.VISIBLE);
                        ((ViewGroup) reason_no_phone.getParent()).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadForm(){
        List<Section12> s1= dbHandler.query(Section12.class,"uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        if(s1.size() == 1){
            section1_database = s1.get(0);
            //Part1TextWatcher.IGNORE_TEXT_WATCHER = true;
            setFormFromModel(this, section1_database, inputValidationOrder, "",false, this.findViewById(android.R.id.content));
        }
        else{
            setFormFromModel(this,resumeModel,inputValidationOrder,"",false,this.findViewById(android.R.id.content));
        }

        if(resumeModel.phone_type!=null){
            phone_type.setSelection(resumeModel.phone_type);

            if (resumeModel.phone_type == 1) {
                int phoneCodePos = -1;
                for (int i = 0; i < phone_code.getAdapter().getCount(); i++){
                    if (((String) phone_code.getAdapter().getItem(i)).equalsIgnoreCase(resumeModel.phone_code))
                        phoneCodePos = i;
                }
                if (phoneCodePos != -1) {
                    phone_code.setSelection(phoneCodePos);
                    phone_number.setText(resumeModel.phone_number);
                }
            }
            else if (resumeModel.phone_type == 2){
                phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                if(resumeModel.phone_code!=null)
                    phone_code2.setText(resumeModel.phone_code);
                if(resumeModel.phone_number!=null)
                    phone_number.setText(resumeModel.phone_number);
            }
            else if (resumeModel.phone_type == 3 && resumeModel.reason_no_phone!=null){
                reason_no_phone.setSelection(resumeModel.reason_no_phone);
            }
        }
        else{
            phone_type.setSelection(1);
        }

        if(resumeModel.email!=null){
            ((EditText) findViewById(R.id.email)).setText(resumeModel.email);
        }

        if(resumeModel.website!=null){
            ((EditText) findViewById(R.id.website)).setText(resumeModel.website);
        }


        Log.d("VALE",String.valueOf(resumeModel.flag));


    }



    private void saveForm() {
        sbtn.setEnabled(false);
        Section12 s1;


        try {
            s1 = (Section12) extractValidatedModelFromForm(this, resumeModel,true, inputValidationOrder,"", Section12.class, false, this.findViewById(android.R.id.content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        if (s1 == null) {
            mUXToolkit.showAlertDialogue("Failed", "فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔", alertForEmptyFieldEvent);
            sbtn.setEnabled(true);
            return;
        }

        //optional fields
        s1.email=((EditText) findViewById(R.id.email)).getText().toString();
        s1.website=((EditText) findViewById(R.id.website)).getText().toString();


        /////TODO CHECKS////////////////////////////


        Long iid = dbHandler.replace(s1);

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