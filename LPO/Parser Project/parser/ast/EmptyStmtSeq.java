package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class EmptyStmtSeq extends EmptySeq<Stmt> implements StmtSeq {
    
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitEmptyStmtSeq();
    }
}
