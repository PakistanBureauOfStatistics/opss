package pbs.sme.survey.model;

import pk.gov.pbs.database.annotations.PrimaryKey;
import pk.gov.pbs.database.annotations.SqlPrimaryKey;

public class Section9 extends FormTable {
    public Integer  id;

    @PrimaryKey
    @SqlPrimaryKey
    public String code;
    public Integer survey_id;
    public Long rupees;

    @Override
    public String toString() {
        return "Section69{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", survey_id=" + survey_id +
                ", rupees=" + rupees +
                ", blk_desc='" + blk_desc + '\'' +
                ", sno=" + sno +
                ", uid='" + uid + '\'' +
                ", userid=" + userid +
                ", created_time='" + created_time + '\'' +
                ", modified_time='" + modified_time + '\'' +
                ", sync_time='" + sync_time + '\'' +
                ", deleted_time='" + deleted_time + '\'' +
                ", is_deleted=" + is_deleted +
                ", integrityCheck='" + integrityCheck + '\'' +
                ", pcode='" + pcode + '\'' +
                ", sid=" + sid +
                ", time_spent=" + time_spent +
                ", rcol1=" + rcol1 +
                ", rcol2=" + rcol2 +
                ", remarks='" + remarks + '\'' +
                ", flag=" + flag +
                ", survey='" + survey + '\'' +
                ", status=" + status +
                '}';
    }

}
