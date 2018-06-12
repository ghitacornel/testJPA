package entities.special;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Good practice :<br>
 * Ensure proper database CHECK constraints are in place when using enums<br>
 * Enums are excellent replacements for dictionary tables for which values hard coded business logic exists
 */
@Entity
public class EntityWithEnums {

    @Id
    private Integer id;

    /**
     * PRO : safe when adding, removing or changing order of enum elements<br>
     * PRO : easier understand to humans by reading directly from the database<br>
     * CONS : increases memory usage + slower performance due to text types usage
     */
    @Enumerated(EnumType.STRING)
    private SimpleEnum enum1;

    /**
     * PRO : better performance + better memory usage due to integer types usage<br>
     * CONS : raises problems when changing order of enum values
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
