result : scanner.l parser.y
	lex scanner.l
	yacc -d parser.y
	gcc lex.yy.c y.tab.c -o result

clean:
	rm -f FIX.html lex.yy.c y.tab.c y.tab.h result makefile~ README~