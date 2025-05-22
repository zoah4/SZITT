package szitt.dto;

import szitt.enums.LocationEnum;
import szitt.enums.StatusEnum;

import java.time.LocalDateTime;

public class ReservationDTO {
    Long attendantId;
    Long instructorId;
    Long subjectId;
    LocalDateTime dateFrom;
    LocalDateTime dateTo;
    Integer duration;
    LocationEnum location;
    StatusEnum status;
    LocalDateTime created;

    public ReservationDTO() {}
    public ReservationDTO(Long attendantId,
                          Long instructorId,
                          Long subjectId,
                          LocalDateTime dateFrom,
                          LocalDateTime dateTo,
                          Integer duration,
                          LocationEnum location,
                          StatusEnum status,
                          LocalDateTime created) {
        this.attendantId = attendantId;
        this.instructorId = instructorId;
        this.subjectId = subjectId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.duration = duration;
        this.location = location;
        this.status = status;
        this.created = created;
    }

    public Long getAttendantId() {
        return attendantId;
    }
    public void setAttendantId(Long attendantId) {
        this.attendantId = attendantId;
    }
    public Long getInstructorId() {
        return instructorId;
    }
    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public LocalDateTime getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }
    public LocalDateTime getDateTo() {
        return dateTo;
    }
    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public LocationEnum getLocation() {
        return location;
    }
    public void setLocation(LocationEnum location) {
        this.location = location;
    }
    public StatusEnum getStatus() {
        return status;
    }
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
