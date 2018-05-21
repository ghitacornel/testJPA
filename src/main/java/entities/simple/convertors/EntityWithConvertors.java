package entities.simple.convertors;

import javax.persistence.*;

@Entity
public class EntityWithConvertors {

    @Id
    private Integer id;

    @Column(nullable = false)
    @Convert(converter = BooleanAttributeConverter.class)
    private Boolean booleanValue;

    @Column(nullable = false)
    @Convert(converter = PasswordEncryptorAttributeConverter.class)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
