package pbs.sme.survey.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pbs.sme.survey.R;
import pbs.sme.survey.model.Section12;
import pk.gov.pbs.utils.StaticUtils;

public class S2Activity extends FormActivity {

    private Button sbtn;
    private Map<String, List<String>> descriptionMap = new HashMap<>();
    private Map<String, String> psicCodeMap = new HashMap<>();
    private Section12 section2_database;
    private RadioGroup is_registered, is_hostel_available;
    private Spinner major_activity;
    private Spinner organization_type;
    private LinearLayout l_seasonal;
    private LinearLayout layoutHostelFacility;
    private EditText establishment_months,psic;
    private CheckBox[] monthCheckboxes;
    private Spinner description_psic;
    private final String[] inputValidationOrder = new String[]{
            "year", "is_registered", "agency", "maintaining_accounts","major_activities",  "description_psic", "psic","type_org", "hostel_facilty",
            "food_laundry_other",  "months"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s2);
        setDrawer(this, "Section 2");
        setParent(this, S3Activity.class);
        scrollView = findViewById(R.id.scrollView);

        Spinner yearSpinner = findViewById(R.id.year);

// Create an array list for years (1947 to 2025)
        List<String> years = new ArrayList<>();
        years.add("Please select:");
        for (int i = 1947; i <= 2025; i++) {
            years.add(String.valueOf(i));
        }
        // Set up the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setSelection(0);

// Handle selection
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected Year: " + selectedYear, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        major_activity = findViewById(R.id.major_activities);
        description_psic = findViewById(R.id.description_psic);
        psic = findViewById(R.id.psic);
        updateMajorActivitySpinner(resumeModel.survey_id);
            updateDescriptionSpinner();

        sbtn = findViewById(R.id.btns);
        sbtn.setOnClickListener(v -> {
            sbtn.requestFocus();
            StaticUtils.getHandler().post(this::saveForm);
        });

        is_registered = findViewById(R.id.is_registered);
        is_registered.setOnCheckedChangeListener((group, checkedId) -> {
            findViewById(R.id.l_agency).setVisibility(checkedId == R.id.is_registered1 ? View.VISIBLE : View.GONE);
        });

        is_hostel_available = findViewById(R.id.hostel_facilty);
        is_hostel_available.setOnCheckedChangeListener((group, checkedId) -> {
            findViewById(R.id.l_food_services).setVisibility(checkedId == R.id.hostel_facilty1 ? View.VISIBLE : View.GONE);
        });

        organization_type = findViewById(R.id.type_org);
        l_seasonal = findViewById(R.id.l_seasonal);
        layoutHostelFacility = findViewById(R.id.layout_hostel_facility);
        establishment_months = findViewById(R.id.establishment_months);
        establishment_months.setKeyListener(null); // Make it non-editable

        monthCheckboxes = new CheckBox[]{
                findViewById(R.id.jan), findViewById(R.id.feb), findViewById(R.id.mar), findViewById(R.id.apr),
                findViewById(R.id.may), findViewById(R.id.jun), findViewById(R.id.jul), findViewById(R.id.aug),
                findViewById(R.id.sep), findViewById(R.id.oct), findViewById(R.id.nov), findViewById(R.id.dec)
        };

        for (CheckBox monthCheckbox : monthCheckboxes) {
            monthCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> updateMonthCount());
        }


        toggleSeasonalVisibility(resumeModel.survey_id);
    }


    private void updateDescriptionSpinner() {
        if (major_activity == null || description_psic == null) return;

        int surveyId = resumeModel.survey_id; // Retrieve survey ID
        int arrayId;
        Map<String, String> psicCodeMap = new HashMap<>();

        switch (surveyId) {
            case 1: // Education
                arrayId = R.array.education_descriptions;
                String[] educationCodes = getResources().getStringArray(R.array.education_codes);
                String[] educationDescriptions = getResources().getStringArray(R.array.education_descriptions);
                for (int i = 0; i < educationDescriptions.length; i++) {
                    psicCodeMap.put(educationDescriptions[i], educationCodes[i]);
                }
                break;

            case 2: // Health
                arrayId = R.array.health_activity_descriptions;
                String[] healthCodes = getResources().getStringArray(R.array.health_activity_codes);
                String[] healthDescriptions = getResources().getStringArray(R.array.health_activity_descriptions);
                for (int i = 0; i < healthDescriptions.length; i++) {
                    psicCodeMap.put(healthDescriptions[i], healthCodes[i]);
                }
                break;

            case 3: // Publishing
                arrayId = R.array.publishing_descriptions;
                String[] publishingCodes = getResources().getStringArray(R.array.publishing_psic_codes);
                String[] publishingDescriptions = getResources().getStringArray(R.array.publishing_descriptions);
                for (int i = 0; i < publishingDescriptions.length; i++) {
                    psicCodeMap.put(publishingDescriptions[i], publishingCodes[i]);
                }
                break;

            case 4: // Personal Services
                arrayId = R.array.other_personal_services_descriptions;
                String[] personalCodes = getResources().getStringArray(R.array.other_personal_services_codes);
                String[] personalDescriptions = getResources().getStringArray(R.array.other_personal_services_descriptions);
                for (int i = 0; i < personalDescriptions.length; i++) {
                    psicCodeMap.put(personalDescriptions[i], personalCodes[i]);
                }
                break;

            case 5: // Food & Accommodation
                arrayId = R.array.accommodation_food_descriptions;
                String[] foodCodes = getResources().getStringArray(R.array.accommodation_food_codes);
                String[] foodDescriptions = getResources().getStringArray(R.array.accommodation_food_descriptions);
                for (int i = 0; i < foodDescriptions.length; i++) {
                    psicCodeMap.put(foodDescriptions[i], foodCodes[i]);
                }
                break;

            default:
                arrayId = R.array.education_descriptions; // Default case
        }

        // Fetch descriptions from arrays.xml
        String[] descriptions = getResources().getStringArray(arrayId);

        runOnUiThread(() -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, descriptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            description_psic.setAdapter(adapter);
        });

        // Set PSIC code based on selected description
        description_psic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedDescription = parent.getItemAtPosition(position).toString();
                psic.setText(psicCodeMap.getOrDefault(selectedDescription, ""));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }



    private void updateMajorActivitySpinner(int surveyPosition) {

        int arrayId;
        switch (surveyPosition) {
            case 1:
                arrayId = R.array.spn_major_activity_education;
                break;
            case 2:
                arrayId = R.array.spn_major_activity_health;
                break;
            case 3:
                arrayId = R.array.spn_major_activity_publishing;
                break;
            case 4:
                arrayId = R.array.spn_major_activity_personal_services;
                break;
            case 5:
                arrayId = R.array.spn_major_activity_accommodation_food;
                break;
            default:
                arrayId = R.array.spn_major_activity_education;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(arrayId));

        major_activity.setAdapter(adapter);
    }

    private void toggleSeasonalVisibility(int surveyPosition) {
        if (surveyPosition == 3 || surveyPosition == 4 || surveyPosition == 5) {
            l_seasonal.setVisibility(View.VISIBLE);
        } else {
            l_seasonal.setVisibility(View.GONE);
        }
        if (surveyPosition == 1) {
            layoutHostelFacility.setVisibility(View.VISIBLE);
        } else {
            layoutHostelFacility.setVisibility(View.GONE);
        }
    }

    private void updateMonthCount() {
        int count = 0;
        for (CheckBox monthCheckbox : monthCheckboxes) {
            if (monthCheckbox.isChecked()) {
                count++;
            }
        }
        establishment_months.setText(String.valueOf(count));
    }

    private void loadForm() {
        List<Section12> s2 = dbHandler.query(Section12.class, "uid='" + resumeModel.uid + "' AND (is_deleted=0 OR is_deleted is null)");
        if (s2.size() == 1) {
            section2_database = s2.get(0);
            setFormFromModel(this, section2_database, inputValidationOrder, "", false, this.findViewById(android.R.id.content));
        }
    }

    private void saveForm() {
        sbtn.setEnabled(false);
        Section12 s2;

        try {
            s2 = (Section12) extractValidatedModelFromForm(this, resumeModel, true, inputValidationOrder, "", Section12.class, false, this.findViewById(android.R.id.content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if (s2 == null) {
            mUXToolkit.showAlertDialogue("Failed", "فارم کو محفوظ نہیں کر سکتے، براہ کرم آگے بڑھنے سے پہلے تمام ڈیٹا درج کریں۔خالی اندراج یا غلط جوابات دیکھنے کے لیے \"OK\" پر کلک کریں۔", alertForEmptyFieldEvent);
            sbtn.setEnabled(true);
            return;
        }


        Long iid = dbHandler.replace(s2);

        if (iid != null && iid > 0) {
            mUXToolkit.showToast("Success");
            btnn.callOnClick();
        } else {
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
