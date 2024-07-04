package progetto2023_lpo22_37.parser.ast;

import static java.util.Objects.requireNonNull;

import progetto2023_lpo22_37.visitors.Visitor;

public class ForEachStmt implements Stmt {
    private final Variable var;
    private final Exp exp;
    private final Block block;

    public ForEachStmt(Variable var, Exp exp, Block block) {
        this.var = requireNonNull(var);
        this.exp = requireNonNull(exp);
        this.block = requireNonNull(block);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + var + "," + exp + "," + block + ")";
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitForEachStmt(var, exp, block);
    }
}
