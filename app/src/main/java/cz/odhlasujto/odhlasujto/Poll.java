package cz.odhlasujto.odhlasujto;

public class Poll {
    private int pollId;
    private String pollName;
    private String pollDesc;
    private String optionName;
    private int SUM;

    public Poll() {

    }

    public int getPollId() {
        return this.pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public String getPollName() {
        return this.pollName;
    }

    public void setPollName(String jmeno) {
        this.pollName = jmeno;
    }

    public String getPollDesc() {
        return this.pollDesc;
    }

    public void setPollDesc(String pollDesc) {
        this.pollDesc = pollDesc;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getSUM() {
        return this.SUM;
    }

    public void setSUM(int SUM) {
        this.SUM = SUM;
    }

    public String toString() {
        return "Poll [pollID=" + pollId + ", pollName=" + pollName + ", pollDesc=" + pollDesc
                + "]";
    }
}