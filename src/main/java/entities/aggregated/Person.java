package entities.aggregated;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

@javax.persistence.Entity
public class Person {

    @Id
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic
    private Integer countryId;

    public Person(Integer id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.countryId = country.getId();
    }

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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
