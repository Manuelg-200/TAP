package progetto2023_lpo22_37.parser;

import java.io.IOException;

public interface Tokenizer extends AutoCloseable {
    
    TokenType next() throws TokenizerException;

    TokenType tokenType();

    String tokenString();

    int intValue();

    boolean boolValue();

    void close() throws IOException;

    int getLineNumber();
}
