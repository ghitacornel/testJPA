package entities.special;

import javax.persistence.*;

@Entity
public class EntityWithLOB {

    @Id
    private Integer id;

    /**
     * Note the LAZY marker is just a hint, it does not enforce lazy loading<br>
     * For LAZY LOADING behavior consider placing such fields in separate entities and use LAZY LOADABLE RELATIONSHIPS
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] fileContent;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
