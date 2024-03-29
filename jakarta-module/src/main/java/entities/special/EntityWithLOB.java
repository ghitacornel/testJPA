package entities.special;

import jakarta.persistence.*;

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

    @Lob
    private String largeText;

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

    public String getLargeText() {
        return largeText;
    }

    public void setLargeText(String largeText) {
        this.largeText = largeText;
    }
}
