package relationships.unrelated;

import java.util.Objects;

public class UnrelatedPair implements Comparable<UnrelatedPair> {

    public Integer idLeft;
    public Integer idRight;

    public UnrelatedPair(Integer idLeft, Integer idRight) {
        if (idLeft == null && idRight == null) throw new IllegalStateException("cannot have both ids null");
        this.idLeft = idLeft;
        this.idRight = idRight;
    }

    @Override
    public String toString() {
        return "(idLeft=" + idLeft + ",idRight=" + idRight + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnrelatedPair that = (UnrelatedPair) o;
        return idLeft.equals(that.idLeft) && idRight.equals(that.idRight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLeft, idRight);
    }

    @Override
    public int compareTo(UnrelatedPair o) {
        if (idLeft == null) {
            return o.idLeft == null ? Integer.compare(idRight, o.idRight) : -1;
        }
        int compareLeft = Integer.compare(idLeft, o.idLeft);
        return compareLeft == 0 ? Integer.compare(idRight, o.idRight) : compareLeft;
    }
}
