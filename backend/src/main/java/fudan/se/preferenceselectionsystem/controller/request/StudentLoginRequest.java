package fudan.se.preferenceselectionsystem.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LBW
 */
public class StudentLoginRequest {
    @JsonProperty("ticket-number")
    private String ticketNumber;
    @JsonProperty("id-number")
    private String idNumber;
    private String name;

    public StudentLoginRequest() {}


    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StudentLoginRequest{" +
                "ticketNumber='" + ticketNumber + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
