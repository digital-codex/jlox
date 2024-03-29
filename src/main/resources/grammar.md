## Syntax Grammar § 1.1

The syntactic grammar is used to parse the linear sequence of tokens into the \
nested syntax tree structure. It starts with the first rule that matches an entire \
Lox program (or a single REPL entry).

````
program     -> declaraction* <EOF> ;
````

### Declarations § 1.1.1

A program is a series of declaration, which are the statements that bind new \
identifiers or any of the other statements types.

````
declaration -> classDecl
             | funDecl
             | varDecl
             | statement ;

classDecl   -> "class <IDENTIFIER> ( "<" <IDENTIFIER> )? "{" function* "}" ;
funDecl     -> "fun" function ;
varDecl     -> "var" <IDENTIFIER> ( "=" expression )? ";" ;
````

### Statements § 1.1.2

The remaining statement rules produce side effects, but do not introduce \
bindings.

````
statement   -> exprStmt
             | forStmt
             | ifStmt
             | printStmt
             | returnStmt
             | whileStmt
             | block ;

exprStmt    -> expression ";" ;
forStmt     -> "for" "(" ( varDecl | exprStmt | ";" ) expression? ";" expression? ")" statement;
ifStmt      -> "if" "(" expression ")" statement ( "else" statement )? ;
printStmt   -> "print" experession ";" ;
returnStmt  -> "return" expression? ";" ;
whileStmt   -> "while" "(" expression ")" statement ;
block       -> "{" declaration* "}" ;
````

Note that **block** is a statement rule, but is also used as a non-terminal in a \
couple of other rules for things like function bodies.

### Expression § 1.1.3

Expression produce values. Lox has a number of unary and binary operators \
with different levels of precedence. Some grammars for languages do not \
directly encode the precedence relationship and specify that elsewhere. Here, \
we use a separate rule for each precedence level to make it explicit.

````
expression  -> assignment ;

assignment  -> ( call "." )? IDENTIFIER "=" assignment | logic_or ;

logic_or    -> logic_and ( "or" logic_and )* ;
logic_and   -> equality ( "and" equality )* ;
equality    -> comparison ( ( "!=" | "==" ) comparison* )* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factor      -> unary ( ( "/" | "*" ) unary )* ;

unary       -> ( "!" | "-" ) unary | call ;
call        -> primary ( "(" arguments? ")" | "." IDENTIFIER )* ;
primary     -> "true" 
             | "false" 
             | "nil" 
             | "this" 
             | <NUMBER> 
             | <STRING>
             | <IDENTIFIER>
             | "(" expression ")"
             | "super" "." <IDENTIFIER> ;
````

### Utility rules § 1.1.4

In order to keep the above rules a littler cleaner, some of the grammar is split out \
into a few reused helper rules.

````
function    -> <IDENTIFIER> "(" parameters? ")" block ;
parameters  -> <IDENTIFIER> ( "," <IDENTIFIER> )* ;
arguments   -> expression ( "," expression )* ;
````

## Lexical Grammar § 1.2

The lexical grammar is used by the lexer to group characters into tokens. \
Where the syntax is [context free](https://en.wikipedia.org/wiki/Context-free_grammar), the lexical grammar is [regular](https://en.wikipedia.org/wiki/Regular_grammar) - note that \
there are no recursive rules.

````
NUMBER      -> <DIGIT>+ ( "." <DIGIT>+ )? ;
STRING      -> "\"" <CHARACTER>* "\"" ;
IDENTIFIER  -> <ALPHA> ( <ALPHA> | <DIGIT> )* ;
ALPHA       -> "a" ... "z" | "A" ... "Z" | "_" ;
DIGIT       -> "0" ... "9" ;
CHARACTER   -> "\t" | "\n" | " " ... "~"
````