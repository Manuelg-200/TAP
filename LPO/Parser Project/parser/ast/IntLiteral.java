package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class IntLiteral extends AtomicLiteral<Integer>{
    public IntLiteral(int n) {
        super(n);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitIntLiteral(value);
    }
}
