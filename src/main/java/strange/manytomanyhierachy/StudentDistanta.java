package strange.manytomanyhierachy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("distanta")
public class StudentDistanta extends Student {

    @Override
    public String toString() {
        return "StudentDistanta{" +
                "id=" + getId() +
                '}';
    }

}
