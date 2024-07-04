package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class PairLit extends BinaryOp {
    public PairLit(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPairLit(left, right);
    }
}
