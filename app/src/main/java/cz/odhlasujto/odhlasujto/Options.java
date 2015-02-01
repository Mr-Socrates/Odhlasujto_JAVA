package cz.odhlasujto.odhlasujto;

public class Options {
    private int optionID;
    private String optionName;
    private int createdPoll;

    public Options() {

    }

    public int getOptionID() {
        return this.optionID;
    }

    public void setOptionID(int optionID) {
        this.optionID = optionID;
    }

    public void getCreatedPoll(int createdPoll) {
        this.createdPoll = createdPoll;
    }


    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

}
