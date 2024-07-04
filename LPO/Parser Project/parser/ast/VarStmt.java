package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class VarStmt extends AbstractAssignStmt {
    public VarStmt(Variable var, Exp exp) {
        super(var, exp);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitVarStmt(var, exp);
    }
}
