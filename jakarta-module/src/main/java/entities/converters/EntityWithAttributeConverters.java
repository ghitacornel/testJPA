package entities.converters;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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

    @Column(nullable = false)
    private CustomType customType;

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

    public CustomType getCustomType() {
        return customType;
    }

    public void setCustomType(CustomType customType) {
        this.customType = customType;
    }
}
