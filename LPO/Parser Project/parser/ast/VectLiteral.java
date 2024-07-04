package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class VectLiteral extends BinaryOp {
    public VectLiteral(Exp ind, Exp dim) {
        super(ind, dim);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitVectLiteral(left, right);
    }
}
