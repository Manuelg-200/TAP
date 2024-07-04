package progetto2023_lpo22_37.parser.ast;

import progetto2023_lpo22_37.visitors.Visitor;

public interface AST {
    <T> T accept(Visitor<T> visitor);
    
}
