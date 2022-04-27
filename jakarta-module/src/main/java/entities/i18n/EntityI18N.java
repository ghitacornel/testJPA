package entities.i18n;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EntityI18N {

    @Id
    private Integer id;

    @Nationalized// for safety enforce nvarchar
    private String value1;

    @Nationalized// for safety enforce nvarchar
    private String value2;

    @Nationalized// for safety enforce nvarchar
    private String value3;

    @Nationalized// for safety enforce nvarchar
    private String value4;

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

    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }
}
