package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public class AssignStmt extends AbstractAssignStmt {
    public AssignStmt(Variable var, Exp exp) {
        super(var, exp);
    }
    
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitAssignStmt(var, exp);
    }
}
