package pbs.sme.survey.model;

public class Section5 extends FormTable {
    public Integer is_digital;
    public String domestic;
    public String international;
    public Long amount;
    public Double percent;

    @Override
    public String toString() {
        return "Section5{" +
                "is_digital=" + is_digital +
                ", domestic='" + domestic + '\'' +
                ", international='" + international + '\'' +
                ", amount=" + amount +
                ", percent=" + percent +
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
