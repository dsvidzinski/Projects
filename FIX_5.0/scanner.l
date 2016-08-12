%{
  
#include <stdio.h>
#include <string.h>
void yyerror(char *);
#include "y.tab.h"

%}


%%

[0-9]+/([" "]+|"=")            { yylval.tag = atoi(yytext); return TAG; }   

"="                            { return ASSIGNMENT; }

"^A"                           { return DELIMETER; }

([0-9]|[A-Za-z]|:|"."|-)*      { yylval.str = strdup(yytext); return VALUE; }

%%

int yywrap(void) {

    yyin = fopen("input", "r");
    yylex();
    fclose(yyin);

    return 1;
}

