/*********/
/* FILE NAME: LEX_FILE.lex */
/*********/

/*****/
/* USER CODE */
/*****/

import java_cup.runtime.*;

/**********/
/* DOLLAR DOLLAR - DON'T TOUCH! */
/**********/

%%

/************/
/* OPTIONS AND DECLARATIONS SECTION */
/************/
   
/*******************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*******************/ 
%class Lexer

/************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/************************/
%line
%column

/***************************/
/* Note that this has to be the EXACT same name of the class the CUP generates */
/***************************/
%cupsym TokenNames

/**********************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/**********************/
%cup

/******/
/* DECLARATIONS */
/******/
/***************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied verbatim (letter to letter) into the Lexer class code.     */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/***************************/   
%{
	/***************************/
	/* Create a new java_cup.runtime.Symbol with information about the current token */
	/***************************/
	private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}

	/***************/
	/* Enable line number extraction from main */
	/***************/
	public int getLine() { return yyline + 1; } 

	/****************/
	/* Enable token position extraction from main */
	/****************/
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

/*********/
/* MACRO DECLARATIONS */
/*********/

LineTerminator	= \r|\n|\r\n
Space           = [ \t]
WhiteSpace		= {LineTerminator} | {Space}
INTEGER			= 0 | [1-9][0-9]*
ID				= [a-zA-Z][a-zA-Z0-9]*

TEXT = [a-zA-Z0-9\(\)\[\]\{\}\?\!\+\-\*\/\.\;]
TEXT_COMMENT2 = [a-zA-Z0-9\(\)\[\]\{\}\?\!\+\-\/\.\;]

LETTERS_ = [a-zA-Z]*
DIGIT_ = [0-9]*

STRING = \"{LETTERS_}\"
COMMENT_TYPE_1 = "//"({TEXT} | {Space})*{LineTerminator}?	
COMMENT_TYPE_2   = "/*"({WhiteSpace}|{TEXT_COMMENT2}|(\*+[^*/]))*\*+"/"
BAD_COMMENT2 = "/*"({WhiteSpace}|{TEXT_COMMENT2}|(\*+[^*/]))* | COMMENT_TYPE_2
BAD_INTEGER = 0[0-9]+


/**********/
/* DOLLAR DOLLAR - DON'T TOUCH! */
/**********/

%%

/********************/
/* LEXER matches regular expressions to actions (Java code) */
/********************/

/**********************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**********************/

<YYINITIAL> {
{BAD_INTEGER}      	{ return symbol(TokenNames.ERROR);}
{COMMENT_TYPE_1}	{/* just skip what was found, do nothing */}
{COMMENT_TYPE_2}	{/* just skip what was found, do nothing */}
{BAD_COMMENT2}      { return symbol(TokenNames.ERROR);}
"("		            { return symbol(TokenNames.LPAREN);}
")"            		{ return symbol(TokenNames.RPAREN);}
"["            		{ return symbol(TokenNames.LBRACK);}
"]"           		{ return symbol(TokenNames.RBRACK);}
"{"           		{ return symbol(TokenNames.LBRACE);}
"}"          		{ return symbol(TokenNames.RBRACE);}
"+"              	{ return symbol(TokenNames.PLUS);}
"-"           		{ return symbol(TokenNames.MINUS);}
"*"             	{ return symbol(TokenNames.TIMES);}
"/"          		{ return symbol(TokenNames.DIVIDE);}
","             	{ return symbol(TokenNames.COMMA);}
"."              	{ return symbol(TokenNames.DOT);}
";"        			{ return symbol(TokenNames.SEMICOLON);}
"int"          		{ return symbol(TokenNames.TYPE_INT);}
"string"      		{ return symbol(TokenNames.TYPE_STRING);}
"void"        		{ return symbol(TokenNames.TYPE_VOID);}
":="           		{ return symbol(TokenNames.ASSIGN);}
"="                	{ return symbol(TokenNames.EQ);}
"<"               	{ return symbol(TokenNames.LT);}
">"               	{ return symbol(TokenNames.GT);}
"array"             { return symbol(TokenNames.ARRAY);}
"class"             { return symbol(TokenNames.CLASS);}
"return"            { return symbol(TokenNames.RETURN);}
"while"            	{ return symbol(TokenNames.WHILE);}
"if"             	{ return symbol(TokenNames.IF);}
"else"            	{ return symbol(TokenNames.ELSE);}
"new"              	{ return symbol(TokenNames.NEW);}
"extends"           { return symbol(TokenNames.EXTENDS);}
"nil"               { return symbol(TokenNames.NIL);}
{INTEGER} { 
	try {
		int val = Integer.parseInt(yytext());
    	if (val < -32767 || val > 32767) {
        	return symbol(TokenNames.ERROR);
    	}
    	else {
        	return symbol(TokenNames.INT, Integer.valueOf(yytext()));
    	}
    }
    catch(Exception err) {
        return symbol(TokenNames.ERROR);
    }
}
{STRING} {return symbol(TokenNames.STRING, new String(yytext()));}
{ID}				{ return symbol(TokenNames.ID, yytext());}
{WhiteSpace}		{/* just skip what was found, do nothing */}
.					{ return symbol(TokenNames.ERROR);}
<<EOF>>				{ return symbol(TokenNames.EOF);}
}