package entities.ids.table;

import javax.persistence.*;

@Entity
public class EntityIdB {

    @TableGenerator(name = "generatorNameB", table = "ID_GENERATOR", pkColumnName = "ENTITY", pkColumnValue = "B", valueColumnName = "LAST_VALUE")
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "generatorNameB")
    private Integer id;

    @Column
    private String name;

    public Integer getId() {
        return id;
    }

    // keep it disabled since is generated by the database
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
