package entities.i18n;

import javax.persistence.*;

@Entity
public class EntityI18N {

    @Id
    private Integer id;

    @Column
    private String value1;

    @Column
    private String value2;

    @Column
    private String value3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }
}
