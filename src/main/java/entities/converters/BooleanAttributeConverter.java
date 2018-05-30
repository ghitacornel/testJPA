package entities.converters;

import javax.persistence.AttributeConverter;

/**
 * convert a boolean to Y / N
 */
public class BooleanAttributeConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value != null && value) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "Y".equals(value);
    }
}