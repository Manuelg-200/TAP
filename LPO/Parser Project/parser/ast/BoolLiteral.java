package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class BoolLiteral extends AtomicLiteral<Boolean>{
    public BoolLiteral(boolean b) {
        super(b);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBoolLiteral(value);
    }
}
