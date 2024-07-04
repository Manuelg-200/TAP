package progetto2023_lpo22_37.parser;

public enum TokenType {
    // symbols
    ASSIGN, MINUS, PLUS, TIMES, NOT, AND, EQ, STMT_SEP, PAIR_OP, OPEN_PAR, CLOSE_PAR, OPEN_BLOCK, CLOSE_BLOCK, OPEN_SQR, CLOSE_SQR,
    // keywords
    PRINT, VAR, BOOL, IF, ELSE, FST, SND, FOREACH, IN,
    // non singleton categories
    SKIP, IDENT, NUM, VECTOR,
    // end-of-file
    EOF,
}
