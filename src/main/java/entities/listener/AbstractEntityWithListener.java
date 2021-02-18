package entities.listener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(EntityListener.class)
public abstract class AbstractEntityWithListener {
}
