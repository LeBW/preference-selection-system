package fudan.se.preferenceselectionsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LBW
 */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @JsonProperty("ticket-number")
    private String ticketNumber;

    private String name;

    @JsonProperty("degree-type")
    private String degreeType;

    private String department;

    private String major;

    private String direction;

    @JsonProperty("id-number")
    private String idNumber;

    @JsonProperty("first-choice-major")
    private String firstChoiceMajor;

    @JsonProperty("first-choice-direction")
    private String firstChoiceDirection;

    @JsonProperty("second-choice-major")
    private String secondChoiceMajor;

    @JsonProperty("second-choice-direction")
    private String secondChoiceDirection;

    @JsonProperty("adjust-major")
    private boolean adjustMajor;

    @JsonProperty("adjust-degree-type")
    private boolean adjustDegreeType;

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @JsonProperty("last-modify-time")
    private Date lastModifyTime;

    public Student() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFirstChoiceMajor() {
        return firstChoiceMajor;
    }

    public void setFirstChoiceMajor(String firstChoiceMajor) {
        this.firstChoiceMajor = firstChoiceMajor;
    }

    public String getFirstChoiceDirection() {
        return firstChoiceDirection;
    }

    public void setFirstChoiceDirection(String firstChoiceDirection) {
        this.firstChoiceDirection = firstChoiceDirection;
    }

    public String getSecondChoiceMajor() {
        return secondChoiceMajor;
    }

    public void setSecondChoiceMajor(String secondChoiceMajor) {
        this.secondChoiceMajor = secondChoiceMajor;
    }

    public String getSecondChoiceDirection() {
        return secondChoiceDirection;
    }

    public void setSecondChoiceDirection(String secondChoiceDirection) {
        this.secondChoiceDirection = secondChoiceDirection;
    }

    public boolean getAdjustMajor() {
        return adjustMajor;
    }

    public void setAdjustMajor(boolean adjustMajor) {
        this.adjustMajor = adjustMajor;
    }

    public boolean getAdjustDegreeType() {
        return adjustDegreeType;
    }

    public void setAdjustDegreeType(boolean adjustDegreeType) {
        this.adjustDegreeType = adjustDegreeType;
    }
}
