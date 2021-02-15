package strange.manytomanyhierachy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
