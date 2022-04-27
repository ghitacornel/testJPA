package strange.manytomanyhierachy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

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
