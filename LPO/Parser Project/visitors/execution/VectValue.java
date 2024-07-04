package progetto2023_lpo22_37.visitors.execution;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class VectValue implements Value {

    private final Integer list[];

    public VectValue(Integer list[]) {
        this.list = requireNonNull(list);
    }

    public VectValue(VectValue vect) {
        this.list = vect.list;
    }

    public Integer get(Integer i) {
        return list[i];
    }

    public Integer getLength() {
        return list.length;
    }

    public VectValue sum(VectValue other) {
        // used only if the two vectors have the same length and it was checked already
        VectValue result = new VectValue(this);
        for(int i=0; i<this.getLength(); i++) 
            result.list[i] += other.list[i];
        return result;
    }

    public Integer mul(VectValue other) {
        // used only if the two vectors have the same length and it was checked already
        Integer result = 0;
        for(int i=0; i<this.getLength(); i++)
            result += this.list[i]*other.list[i];
        return result;        
    }

    public VectValue scalarMul(int c) {
        VectValue result = new VectValue(this);
        for(int i=0; i<this.getLength(); i++)
            result.list[i] *= c;
        return result;
    }

    @Override
    public VectValue toVect() {
        return this;
    }

    @Override
    public String toString() {
        String string = "[";
        for(int i=0; i<this.getLength(); i++)
            string += this.list[i] + ";";
        string = string.substring(0, string.length() -1);
        return string + "]";
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj instanceof VectValue vect)
            return Arrays.equals(this.list, vect.list);
        return false;
    }
}
