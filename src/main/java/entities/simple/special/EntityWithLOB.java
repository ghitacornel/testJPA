package entities.simple.special;

import javax.persistence.*;

@Entity
public class EntityWithLOB {

    @Id
    private Integer id;

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
