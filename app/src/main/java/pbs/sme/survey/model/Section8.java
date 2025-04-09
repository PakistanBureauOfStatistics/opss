package pbs.sme.survey.model;

import pk.gov.pbs.database.annotations.PrimaryKey;
import pk.gov.pbs.database.annotations.SqlPrimaryKey;

public class Section8 extends FormTable {
    public Integer  id;
    @PrimaryKey
    @SqlPrimaryKey
    public String code;
    public Integer survey_id;
    public Long acq_fixed_assets;


    public Long major_improvements;


    public Long sales_proceeds;


    public Long own_account_capital;


    public Long GFCF;


    public Long exp_life;


    public Long scrap_value;

    @Override
    public String toString() {
        return "Section8{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", survey_id=" + survey_id +
                ", acq_fixed_assets=" + acq_fixed_assets +
                ", major_improvements=" + major_improvements +
                ", sales_proceeds=" + sales_proceeds +
                ", own_account_capital=" + own_account_capital +
                ", GFCF=" + GFCF +
                ", exp_life=" + exp_life +
                ", scrap_value=" + scrap_value +
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
                ", survey=" + survey +
                ", status=" + status +
                '}';
    }
}
