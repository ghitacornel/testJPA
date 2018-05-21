package entities.simple.special;

import javax.persistence.*;
import java.util.Date;

/**
 * Good practice :<br>
 * Ensure proper date precision and type is used since it affects database performance, range checks and automated tests precision
 */
@Entity
public class EntityWithDate {

    @Id
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fullDate")
    private Date fullDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "onlyDate")
    private Date onlyDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "onlyTime")
    private Date onlyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFullDate() {
        return fullDate;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public Date getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(Date onlyDate) {
        this.onlyDate = onlyDate;
    }

    public Date getOnlyTime() {
        return onlyTime;
    }

    public void setOnlyTime(Date onlyTime) {
        this.onlyTime = onlyTime;
    }
}
