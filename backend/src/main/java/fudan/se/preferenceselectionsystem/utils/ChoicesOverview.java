package fudan.se.preferenceselectionsystem.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LBW
 */
public class ChoicesOverview {
    private String major;

    private Integer spots;

    @JsonProperty("first-choice-major-numbers")
    private Integer firstChoiceMajorNumbers;

    public ChoicesOverview() {}


    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getSpots() {
        return spots;
    }

    public void setSpots(Integer spots) {
        this.spots = spots;
    }

    public Integer getFirstChoiceMajorNumbers() {
        return firstChoiceMajorNumbers;
    }

    public void setFirstChoiceMajorNumbers(Integer firstChoiceMajorNumbers) {
        this.firstChoiceMajorNumbers = firstChoiceMajorNumbers;
    }
}
