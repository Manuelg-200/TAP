package progetto2023_lpo22_37.parser.ast;

import static java.util.Objects.requireNonNull;

import progetto2023_lpo22_37.visitors.Visitor;

public class PrintStmt implements Stmt {
    private final Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = requireNonNull(exp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + exp + ")";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPrintStmt(exp);
    }
}
