package entities.simple.special;

import entities.simple.SimpleEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * Good practice :<br>
 * Ensure proper database CHECK constraints are in place when using enums<br>
 * Enums are excellent replacements for dictionary tables for which values we hard code business logic
 */
@Entity
public class EntityWithEnums {

    @Id
    private Integer id;

    /**
     * recommended<br>
     * safe when adding, removing or changing order of enum elements<br>
     * increase memory usage + slower performance due to text column usage
     */
    @Enumerated(EnumType.STRING)
    private SimpleEnum enum1;

    /**
     * raise problems when adding, removing or changing order of enum values<br>
     * better performance + better memory usage due to integer column usage
     */
    @Enumerated(EnumType.ORDINAL)
    private SimpleEnum enum2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SimpleEnum getEnum1() {
        return enum1;
    }

    public void setEnum1(SimpleEnum enum1) {
        this.enum1 = enum1;
    }

    public SimpleEnum getEnum2() {
        return enum2;
    }

    public void setEnum2(SimpleEnum enum2) {
        this.enum2 = enum2;
    }
}
