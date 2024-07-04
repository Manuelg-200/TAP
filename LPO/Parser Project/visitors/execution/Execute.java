package progetto2023_lpo22_37.visitors.execution;

import java.io.PrintWriter;
import java.util.Arrays;

import progetto2023_lpo22_37.environments.EnvironmentException;
import progetto2023_lpo22_37.environments.GenEnvironment;
import progetto2023_lpo22_37.parser.ast.Block;
import progetto2023_lpo22_37.parser.ast.Exp;
import progetto2023_lpo22_37.parser.ast.Stmt;
import progetto2023_lpo22_37.parser.ast.StmtSeq;
import progetto2023_lpo22_37.parser.ast.Variable;
import progetto2023_lpo22_37.visitors.Visitor;

import static java.util.Objects.requireNonNull;

public class Execute implements Visitor<Value> {
    
    private final GenEnvironment<Value> env = new GenEnvironment<>();
    private final PrintWriter printWriter; // output stream used to print values

    public Execute() {
        printWriter = new PrintWriter(System.out, true);
    }

    public Execute(PrintWriter printWriter) {
        this.printWriter = requireNonNull(printWriter);
    }

    // dynamic semantics for programs; no value returned by the visitor

    @Override
    public Value visitMyLangProg(StmtSeq stmtSeq) {
        try {
            stmtSeq.accept(this);
            // possible runtime errors
            // EnvironmentException: undefined variable
        } catch(EnvironmentException e) {
            throw new InterpreterException(e);
        }
        return null;
    }

    // dynamic semantics for statements; no value returned by the visitor

    @Override
    public Value visitAssignStmt(Variable var, Exp exp) {
        env.update(var, exp.accept(this));
        return null;
    }

    @Override
    public Value visitPrintStmt(Exp exp) {
        printWriter.println(exp.accept(this));
        return null;
    }

    @Override
    public Value visitVarStmt(Variable var, Exp exp) {
        env.dec(var, exp.accept(this));
        return null;
    }

    @Override
    public Value visitIfStmt(Exp exp, Block thenBlock, Block elseBlock) {
        if(exp.accept(this).toBool())
            thenBlock.accept(this);
        else if(elseBlock != null)
            elseBlock.accept(this);
        return null;
    }

    @Override
    public Value visitBlock(StmtSeq stmtSeq) {
        env.enterScope();
        stmtSeq.accept(this);
        env.exitScope();
        return null;
    }

    @Override
    public Value visitForEachStmt(Variable var, Exp exp, Block block) {
        VectValue vect = exp.accept(this).toVect();
        env.enterScope();
        env.dec(var, new IntValue(0));
        for(int i=0; i<vect.getLength(); i++) {
            env.update(var, new IntValue(vect.get(i)));
            block.accept(this);            
        }
        env.exitScope();
        return null;
    }

    // dynamic semantics for sequences of stamtements
    //no value returned by the visitor

    @Override
    public Value visitEmptyStmtSeq() {
        return null;
    }

    @Override
    public Value visitNonEmptyStmtSeq(Stmt first, StmtSeq rest) {
        first.accept(this);
        rest.accept(this);
        return null;
    }

    // dynamic semantics of expressions; a value is returned by the visitor

    // auxiliary function to check if the length of two vectors is the same
    private void checkLength(VectValue vect1, VectValue vect2) throws InterpreterException {
        if(vect1.getLength() != vect2.getLength())
            throw new InterpreterException("Vectors must have the same dimensions");
        }

    // auxiliary genericAdd to handle the the different value types
    private Value genericAdd(Value val1, Value val2) {
        // case 1: two int types
        if(val1 instanceof IntValue && val2 instanceof IntValue)
            return new IntValue(val1.toInt() + val2.toInt());  
        // case 2: two vect types
        else if(val1 instanceof VectValue && val2 instanceof VectValue) {
            checkLength(val1.toVect(), val2.toVect());
            return new VectValue(val1.toVect().sum(val2.toVect()));
        }
        // case 3: one int type
        else if(val1 instanceof IntValue)
            throw new InterpreterException("Expecting an integer");
        // case 4: one vector type
        else if(val1 instanceof VectValue)
            throw new InterpreterException("Expecting a vector");
        else
            throw new InterpreterException("Expecting an integer or a vector");
    }

    @Override
    public Value visitAdd(Exp left, Exp right) {
        return genericAdd(left.accept(this), right.accept(this));
    }

    @Override
    public IntValue visitIntLiteral(int value) {
        return new IntValue(value);
    }

    // auxiliary genericMul to handle the different value types
    private Value genericMul(Value val1, Value val2) {
        // case 1: two int types
        if(val1 instanceof IntValue && val2 instanceof IntValue)
            return new IntValue(val1.toInt() * val2.toInt());
        // case 2: two vect types
        else if(val1 instanceof VectValue && val2 instanceof VectValue)
        {
            checkLength(val1.toVect(), val2.toVect());
            return new IntValue(val1.toVect().mul(val2.toVect()));
        }
        // case 3: one int value, one vect value
        else if(val1 instanceof VectValue && val2 instanceof IntValue)
            return new VectValue(val1.toVect().scalarMul(val2.toInt()));
        else if(val1 instanceof IntValue && val2 instanceof VectValue)
            return new VectValue(val2.toVect().scalarMul(val1.toInt()));
        else
            throw new InterpreterException("Expecting an integer or a vector");
    }

    @Override
    public Value visitMul(Exp left, Exp right) {
        return genericMul(left.accept(this), right.accept(this));
    }

    @Override
    public IntValue visitSign(Exp exp) {
        return new IntValue(-exp.accept(this).toInt());
    }

    @Override
    public Value visitVariable(Variable var) {
        return env.lookup(var);
    }

    @Override
    public BoolValue visitNot(Exp exp) {
        return new BoolValue(!exp.accept(this).toBool());
    }

    @Override
    public BoolValue visitAnd(Exp left, Exp right) {
        return new BoolValue(left.accept(this).toBool() && right.accept(this).toBool());
    }

    @Override
    public BoolValue visitBoolLiteral(boolean value) {
        return new BoolValue(value);
    }

    @Override
    public BoolValue visitEq(Exp left, Exp right) {
        return new BoolValue(left.accept(this).equals(right.accept(this)));
    }

    @Override
    public PairValue visitPairLit(Exp left, Exp right) {
        return new PairValue(left.accept(this), right.accept(this));
    }

    @Override
    public Value visitFst(Exp exp) {
        return exp.accept(this).toPair().getFstVal();
    }

    @Override
    public Value visitSnd(Exp exp) {
        return exp.accept(this).toPair().getSndVal();
    }

    @Override
    public VectValue visitVectLiteral(Exp exp1, Exp exp2) {
        Integer ind = exp1.accept(this).toInt();
        Integer dim = exp2.accept(this).toInt();
        if(dim < 0)
            throw new InterpreterException("Can't create a vector of negative dimensions");
        if(ind < 0 || ind >= dim)
            throw new InterpreterException("Vector index out of bounds (" + ind + "," + dim +")");
        Integer[] list = new Integer[dim];
        Arrays.fill(list, 0);
        list[ind] = 1;
        return new VectValue(list);        
    }
}
