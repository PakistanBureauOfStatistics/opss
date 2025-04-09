package pbs.sme.survey.activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pbs.sme.survey.R;
import pbs.sme.survey.helper.DialogHelper;
import pbs.sme.survey.helper.GPSHelper;
import pbs.sme.survey.helper.SectionAdapter;
import pbs.sme.survey.model.Constants;
import pbs.sme.survey.model.Section;
import pbs.sme.survey.model.Section12;
import pbs.sme.survey.model.Section3;
import pbs.sme.survey.model.Section47;
import pbs.sme.survey.model.Section5;
import pbs.sme.survey.model.Section6;
import pbs.sme.survey.model.Section8;
import pbs.sme.survey.model.Section9;

public class HomeActivity extends FormActivity {

    GPSHelper helper;
    LocationManager manager;
    Location gps, net, best;
    RecyclerView list;
    SectionAdapter adapter;
    TextView tv_progress, tv_synctime;
    int progress, total;
    String[] times;
    Integer[] status;
    DialogHelper gdh=new DialogHelper();
    String missing="";
    String stime=null;

    public static final String UPDATE_SYNC_STATUS = ListActivity.class.getCanonicalName() + "UPDATE_SYNC_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setDrawer(this,"Home Screen");
        setParent(this, S1Activity.class);
        list=findViewById(R.id.list);
        tv_progress=findViewById(R.id.tv_progress);
        tv_synctime=findViewById(R.id.tv_synctime);
        btnn=findViewById(R.id.btnn);
        setSection();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public void setSection(){
        List<Section> l=new ArrayList<>();
        String[] times=new String[Constants.SECTION_CODES.length];
        Integer[] status=new Integer[Constants.SECTION_CODES.length];
        total = times.length;

        List<Section12> s2= dbHandler.query(Section12.class,"uid='"+resumeModel.uid+"' AND (is_deleted=0 OR is_deleted is null)");
        if(s2!=null){
            Section12 o=s2.get(0);
            if(o.owner!=null){
                times[0]=o.created_time;
                status[0]=R.drawable.ic_tick;
            }
            if(o.year!=null){
                times[1]=o.created_time;
                status[1]=R.drawable.ic_tick;
            }
        }
        String s3time=dbHandler.queryString("SELECT min(created_time) from "+ Section3.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s3time!=null && !s3time.isEmpty()){
            times[2]=s3time;
            status[2]=R.drawable.ic_tick;
        }
        String s4time=dbHandler.queryString("SELECT min(created_time) from "+ Section47.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and Section='"+4+"'and (is_deleted=0 or is_deleted is null)",null);
        if(s4time!=null && !s4time.isEmpty()){
            times[3]=s4time;
            status[3]=R.drawable.ic_tick;
        }

        String s5time=dbHandler.queryString("SELECT min(created_time) from "+ Section5.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s5time!=null && !s5time.isEmpty()){
            times[4]=s5time;
            status[4]=R.drawable.ic_tick;
        }
        String s6time=dbHandler.queryString("SELECT min(created_time) from "+ Section6.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s6time!=null && !s6time.isEmpty()){
            times[5]=s6time;
            status[5]=R.drawable.ic_tick;
        }
        String s7time=dbHandler.queryString("SELECT min(created_time) from "+ Section47.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and Section='"+7+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s7time!=null && !s7time.isEmpty()){
            times[6]=s7time;
            status[6]=R.drawable.ic_tick;
        }
        String s8time=dbHandler.queryString("SELECT min(created_time) from "+ Section8.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s8time!=null && !s8time.isEmpty()){
            times[7]=s8time;
            status[7]=R.drawable.ic_tick;
        }
        String s9time=dbHandler.queryString("SELECT min(created_time) from "+ Section9.class.getSimpleName()+" where uid='"+resumeModel.uid+"' and  (is_deleted=0 or is_deleted is null)",null);
        if(s9time!=null && !s9time.isEmpty()){
            times[8]=s9time;
            status[8]=R.drawable.ic_tick;
        }
        progress=0;
        missing="";
        for(int i=0; i<Constants.SECTION_CODES.length; i++){
            if(status[i]!=null){
                if(status[i]==R.drawable.ic_block || status[i]==R.drawable.ic_tick) {
                    progress++;
                }
                else{
                    missing+="<br/>"+Constants.SECTION_NAMES[i];
                }
            }
            else{
                missing+="<br/>"+Constants.SECTION_NAMES[i];
            }
            l.add(new Section(Constants.SECTION_CODES[i],Constants.SECTION_NAMES[i],status[i],times[i],Constants.FORM_ACTIVITIES[i]));
        }
        tv_progress.setText(progress+"/"+total);
        if(progress==0){
            tv_progress.setText("No Started");
        }
        else if(progress==total){
            tv_progress.setText(+progress+"/ "+total+"     Completed  ");
        }
        else{
            tv_progress.setText(+progress+"/ "+total+"    Remaining  ");
        }

        adapter = new SectionAdapter(this,  l, dbHandler, intent);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        //setSyncTime(enumerationHousehold.hh_uid);

    }

    public void goNext(final View view){
        Intent intent=new Intent(HomeActivity.this, S1Activity.class);
        startActivity(intent);
        finish();

    }



    public void upload(View view) {
    }
}