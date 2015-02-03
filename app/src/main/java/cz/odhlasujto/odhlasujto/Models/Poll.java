package cz.odhlasujto.odhlasujto.Models;

public class Poll {
    public String pollId;
    private String pollName;
    private String pollDesc;

    public Poll() {
    }

    public String getPollId() {
        return this.pollId;
    }

    public void setPollId(String pollId) {
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

    public String toString() {
        return "Poll [pollID=" + pollId + ", pollName=" + pollName + ", pollDesc=" + pollDesc
                + "]";
    }
}
