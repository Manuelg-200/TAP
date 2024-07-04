package progetto2023_lpo22_37.visitors;

import progetto2023_lpo22_37.parser.ast.Block;
import progetto2023_lpo22_37.parser.ast.Exp;
import progetto2023_lpo22_37.parser.ast.Stmt;
import progetto2023_lpo22_37.parser.ast.StmtSeq;
import progetto2023_lpo22_37.parser.ast.Variable;

public interface Visitor<T> {
    T visitAdd(Exp left, Exp right);

    T visitAssignStmt(Variable var, Exp exp);

    T visitIntLiteral(int value);

    T visitEq(Exp left, Exp right);

    T visitNonEmptyStmtSeq(Stmt first, StmtSeq rest);

    T visitMul(Exp left, Exp right);

    T visitPrintStmt(Exp exp);

    T visitMyLangProg(StmtSeq stmtSeq);

    T visitSign(Exp exp);

    T visitVariable(Variable var); // only in this case more efficient than T visitVariable(String name)

    T visitEmptyStmtSeq();

    T visitVarStmt(Variable var, Exp exp);

    T visitNot(Exp exp);

    T visitAnd(Exp left, Exp right);

    T visitBoolLiteral(boolean value);

    T visitIfStmt(Exp exp, Block thenBlock, Block elseBlock);

    T visitBlock(StmtSeq stmtSeq);

    T visitPairLit(Exp left, Exp right);

    T visitFst(Exp exp);

    T visitSnd(Exp exp);
    
    T visitForEachStmt(Variable var, Exp exp, Block block);

    T visitVectLiteral(Exp ind, Exp dim);
}
