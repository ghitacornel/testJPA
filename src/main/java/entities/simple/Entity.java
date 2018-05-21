package entities.simple;

import javax.persistence.*;
import java.util.Date;

/**
 * a simple {@link javax.persistence.Entity}<br>
 * jpa default mappings usage is encouraged<br>
 * jpa enum mapping usage is encourage over standard database dictionaries
 * whenever possible<br>
 *
 * @author Cornel
 */
@javax.persistence.Entity
@Table(name = "SimpleEntity")
public class Entity {

    @Id
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic
    private Integer value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "simpleDate")
    private Date simpleDate;

    @Transient
    private Integer notMapped;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean booleanValue;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] fileContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNotMapped() {
        return notMapped;
    }

    public void setNotMapped(Integer notMapped) {
        this.notMapped = notMapped;
    }

    public Date getSimpleDate() {
        return simpleDate;
    }

    public void setSimpleDate(Date simpleDate) {
        this.simpleDate = simpleDate;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
