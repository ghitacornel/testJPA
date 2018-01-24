package frameworks.versionable.tests.model;

import frameworks.versionable.VersionableEntity;

import javax.persistence.*;

@Entity
@Table(name = "VersionableEntity")
public class Model extends VersionableEntity<ModelVersion> {

    @Id
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

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
}
