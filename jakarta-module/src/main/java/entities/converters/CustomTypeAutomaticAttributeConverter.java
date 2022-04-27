package entities.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Encrypt / decrypt a password upon storing in the database
 */
@Converter(autoApply = true)
public class CustomTypeAutomaticAttributeConverter implements AttributeConverter<CustomType, String> {


    @Override
    public String convertToDatabaseColumn(CustomType value) {
        try {
            return value.getX() + "-" + value.getY();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CustomType convertToEntityAttribute(String value) {
        try {
            CustomType result = new CustomType();
            result.setX(Integer.parseInt(value.substring(0, value.indexOf("-"))));
            result.setY(value.substring(value.indexOf("-") + 1));
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}