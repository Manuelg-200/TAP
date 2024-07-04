package progetto2023_lpo22_37.parser;

import progetto2023_lpo22_37.parser.ast.Prog;

public interface Parser extends AutoCloseable {
    
    Prog parseProg() throws ParserException;
}
