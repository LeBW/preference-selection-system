package fudan.se.preferenceselectionsystem.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LBW
 */
public class ChoicesOverview {
    private String major;

    @JsonProperty("degree-type")
    private String degreeType;

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

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
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
