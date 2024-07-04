package progetto2023_lpo22_37.parser.ast;

public abstract class EmptySeq<T> {

    protected EmptySeq() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}