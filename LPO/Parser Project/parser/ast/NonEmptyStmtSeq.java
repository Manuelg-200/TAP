package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class NonEmptyStmtSeq  extends NonEmptySeq<Stmt, StmtSeq> implements StmtSeq {
    public NonEmptyStmtSeq(Stmt first, StmtSeq rest) {
        super(first, rest);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNonEmptyStmtSeq(first, rest);
    }
}
