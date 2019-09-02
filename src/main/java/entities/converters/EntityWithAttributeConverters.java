package entities.converters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityWithAttributeConverters {

    @Id
    private Integer id;

    @Column(nullable = false)
    @Convert(converter = BooleanAttributeConverter.class)
    private Boolean booleanValue;

    @Column(nullable = false)
    @Convert(converter = PasswordEncrypterAttributeConverter.class)
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
