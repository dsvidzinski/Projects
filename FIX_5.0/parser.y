%{

  #include <stdio.h>
  #include <string.h>
  #include <math.h>
  #include "list.c"

  int yylex();
  FILE *yyin;

  char *GenerateCheckSum( char *buf, long bufLen );

  Node * BuildMessageList();

  void genHTML(Node ** header, int message_counter); 

  int yylex(void);
  void yyerror(char *);
  int yydebug=1;
  
  int listHead(Node ** head);

  void check_message( Node ** input, Node ** checker);
  void check_body(  Node ** input, Node ** checker);
 
  char tags[2000][50];
 
  Node * m_check_head; 
  Node * b_check_head; 
  Node * t_check_head; 

  Node * input_header;
  Node * input_body;
  Node * input_trailer; 

  char * msg_type; 

  Node * message_type_list;
 
  Node *  header_first;
  Node * body_first;

  Node * hash_table[100];
  int message_counter = 0;

  int test;

  int magic_sum; 

%}


%token ASSIGNMENT
%token DELIMETER
%token VALUE
%token TAG

%union{
  char * str;
  int tag; 
 }

%type<str> VALUE
%type<tag> TAG

%type<tag> T
%type<str> V

%%

message:
        field "\n"
	|field message 
        ;

field:               
      T ASSIGNMENT V D  { 
	//printf("The value of TAG is %d \n", $1); 
	//printf("The value of V is %s \n", $3);

	//printf("--------- MESSAGE %i --------------------\n", (message_counter +1) );

	if (checkTag(&m_check_head, $1)){                                                                                                                                                                                                                                                                                                                          
	  if ($1 == 35){                                                                                                                                                       
	                                                                                                    
	    char body[20];                                                                                                                                                     
	    strcpy(body, "./Library/Msg/" );
            strcat(body, $3);                                                                                                                                            
	  
	    b_check_head = BuildCheckList(body);

	  }   

                                                                                                                                                                                              if(checkReqd(&m_check_head, $1) == 1){                                                                                                                            
	    Push(&input_header, $1, tags[$1], $3, 'Y');                                                                                                                  
	  }                                                                                                                                                             
	  else{                                                                                                                                                             
	    Push(&input_header, $1, tags[$1], $3, 'N');                                                                                                                   
	  }                                                                                                                                                             

																							      //--------CHECK SUM--------//																							    
	    int nDigits = floor(log10(abs($1))) + 1; 
	    int total_length = (nDigits + strlen($3)+2); 
	    char field_string [total_length]; //accounting for the delemeter 
	    
	    char aInt[nDigits];
	    sprintf(aInt, "%d", $1);
	    
	    strcpy(field_string, aInt);
	    strcat(field_string, "=");
	    strcat(field_string, $3) ; 
	    strcat(field_string, "\x01");
	    
	    int num = atoi(GenerateCheckSum(field_string, total_length));
	    
	    magic_sum = magic_sum + num; 
                                                                                                                                                    
	}//end of input_message                                                                                                                                              
	
       //checking if the field belong to the message 
	else if ( checkTag(&b_check_head, $1) ){
       
	  //----------CHECK_SUM----------------///
	
            int  nDigits = floor(log10(abs($1))) + 1;
		
	    int total_length = (nDigits + strlen($3)+2);
	    char field_string [total_length]; //accounting for the delemeter  

	    char aInt[nDigits];
	    sprintf(aInt, "%d", $1);
	    strcpy(field_string, aInt);
	    strcat(field_string, "=");
	    strcat(field_string, $3) ;
	    strcat(field_string, "\x01");

	    int num = atoi(GenerateCheckSum(field_string, total_length));
	 
            magic_sum = magic_sum + num;

	  //-----------END OF CHECK_SUM-----------///

	  //checking if the message is Required or No 
	  if(checkReqd(&b_check_head, $1) == 1){
            Push(&input_body, $1, tags[$1], $3, 'Y');
          }
          else{
            Push(&input_body, $1, tags[$1], $3, 'N');
          }


	}//end of if statment 
	
	//checker statement // this needs to checked against and put into a messge checker linked list  
	else if ( $1 == 10){
	   
	  Push(&input_trailer, $1, tags[$1], $3, 'Y'); 

	  int input_check_trailer = atoi($3); 
	 
	  //printf("--------- MESSAGE %i --------------------\n", (message_counter +1) );
	  printf("\n"); 

	  if( (magic_sum%256) != input_check_trailer){
	      printf("ERROR: The value of input trailer check sum is %i, but the total value of bytes in the message is %i \n", input_check_trailer, magic_sum%256);   
	    }

	  //------------THIS IS WHERE ILL INSERT INTO A HASH TABLE ------------////////////

	  check_message(&input_header, &m_check_head);
          check_body(&input_body, &b_check_head);


	  printf("--------- END OF MESSAGE %i -------------\n",(message_counter +1) );  
	  printf("\n");
	  printf("*****************************************\n");

	  //check_message(&input_header, &m_check_head);
	  // check_body(&input_body, &b_check_head);

	  //putting together the message. 
	   header_first = listTail(&input_header);
	   body_first = listTail(&input_body);

	   Node * message = header_first;
	   message -> body = body_first;
	   message -> body -> trailer = input_trailer;

	   //printf("The value of message is %d \n", message_counter-1);  
	   hash_table[message_counter] = message; 
        
	   //re-initializing the list we will compare                                                                                                                             
             input_header = BuildList();

	   //re-initializing the input body list                                                                                                                                  
	     input_body = BuildList();	   

	   //re-initializing the trailer node 
	     input_trailer = BuildList();
	    
	     //re-initialzing magic_sim 
	     magic_sum  = 0; 

	   //adjusting the counter 
	   message_counter = message_counter + 1;
	   
	   printf("--------- MESSAGE %i --------------------\n", (message_counter +1) );  

	}
       
	//else this field doesnt belong 
	else{
	 
	  printf("ERROR %i tag does NOT belong!!\n", $1); 

	}
      
      }
      ;
 
T: 
   TAG  
   ;

V:
  VALUE                              
  ;

D:
  DELIMETER
  ;

%%


void yyerror(char *s) {

  // fprintf(stderr, "Not a proper field \n");

 }



int main(void) {

  //creating a check list to check message header 
   m_check_head = BuildCheckList("./Library/Msg/MessageHeader"); 
   
  //initializing the list we will compare
  input_header = BuildList(); 

  //initialzing the input body list  
  input_body = BuildList(); 

  //initializing input check 
  input_trailer = BuildList();


  //intializing check sum 
  magic_sum = 0; 

  
  printf("\n"); 
  printf("FIX 5.0 ERROR CHECKER \n"); 
  printf("\n"); 

  printf("--------- MESSAGE %i --------------------\n", (message_counter +1) ); 

  //----------------------------

  //assigning TAG values                                                                                                                                                           
  FILE * fp;
  fp = fopen ("./Library/Tags.txt", "r");

  char * line = NULL;
  size_t len = 0;
  ssize_t read;
  int counter = 0;


  while ((read = getline(&line, &len, fp)) != -1) {
                                                                                                                                                             
    strcpy(tags[counter], line);
    counter++;
  }

  fclose(fp);
  if (line)
    free(line);

  /****************
      INPUT FILE 
   ****************/
	 
  FILE * pt = fopen("input", "r" );
  yyin = pt;
  

  //----------------------------

  yyparse();
  
  //check_message(&input_header, &m_check_head);
  // check_body(&input_body, &b_check_body);

  genHTML( &(hash_table[0]), message_counter);

   //freeing memory
   free(m_check_head);
   free(input_header); 
   free(b_check_head); 
   free(input_body); 
   free(message_type_list);
   free(input_trailer);
  //closing the input file 
  fclose(pt);

  //generating HTML if all tests 

  return 0;

}


//writing the assembly 
void genHTML( Node ** header, int message_counter){

  //  printf("This is the list we will generate HTML from c\n");
  // printf("The value of message counter is %i \n", message_counter); 


  //template file                                                                                                                                            
  FILE * read_from;
  read_from = fopen ("template", "r");

  //html file                                                                                                                                                  
  FILE *out_file;
  out_file = fopen("FIX.html", "w+");

  char * line = NULL;
  size_t len = 0;
  ssize_t read;

  //copying from the template to the FIX.html 
  while ((read = getline(&line, &len, read_from)) != -1) {
    //printf("Line of length %zu\n", read);                                                                                                                                       \     //  printf("%s", line);                                                                                                                                                       
    fputs(line, out_file);
    // strcpy(tags[counter], line);  
   // counter++;  
  }
 
  //closing read_from file                                                                                                                                                       
  fclose(read_from);
  if (line)
    free(line);


  int m_counter = 0; 

  while (m_counter < message_counter){


    Node * current = *header;
    char * message_title [4]; 


    // traversing the list to print out message details 
    // 35, 49, 52, 56 

    //fputs("<p> &nbsp; </p> \n", out_file);
    // fputs ("<div id = msg_header >\n ", out_file); 
  
    fputs("<table border = ""3"" align=""center"" style= ""margin: 10px 10px 100px 10px;""   > \n", out_file);
    fputs("<br> </br> \n", out_file);
    fputs("<tr bgcolor=  #245C81 >\n", out_file);
    fputs("<th>Time</th>\n", out_file);
    fputs("<th>Sender</th>\n", out_file);
    fputs("<th>Target</th>\n", out_file);
    fputs("<th>Message</th>\n", out_file);
    fputs("</tr>\n", out_file);

    //writting HTML                                                                                                                                                                 
    while( current != NULL){
                                                                                                                                                                                    
      //time sent 
      if ( (current -> tag) == 52) {
	message_title[0] = current -> field_value;
      }

      //sender 
      if ( (current -> tag) == 49) {
	message_title[1] = current -> field_value;
      }
      
      //reciever 
      if ( (current -> tag) == 56){
	message_title[2] = current -> field_value;
      }

      //check ching the type of the message 
      //message type
    if ( (current -> tag) == 35) {

      Node * msgNode  = BuildMessageTypeList();

      //searching for the appropraite msgtypeMessage
      while ( (msgNode->next) != NULL){
	
	if ( strcmp(msgNode -> field_name , current->field_value) == 0 ){
	  
	  message_title[3] = msgNode -> field_value;
	}

	msgNode = msgNode ->next;
      }

    }
      current = current -> previous;
    
    }

    //filling a message header 
    fputs("<tr bgcolor = #99ddff >\n", out_file);
      fputs("<th>", out_file);                                                                                                                                                   
      fputs(message_title[0], out_file);                                                                                                                                   
      fputs("</th>\n", out_file);
      fputs("<th>", out_file);
      fputs(message_title[1], out_file);
      fputs("</th>\n", out_file);
      fputs("<th>", out_file);
      fputs(message_title[2], out_file);
      fputs("</th>\n", out_file);
      fputs("<th>", out_file);
      fputs(message_title[3], out_file);
      fputs("</th>\n", out_file);
    fputs("</tr>\n", out_file);

    fputs ("</div>\n ", out_file);
    
    //---- THIS PRINTS OUT THE MESSAGE DETAILS --------//
  
  int counter = 1;
  current = *header;  
  
  fputs ("<div id = msg_body >\n ", out_file);
  fputs("<tr bgcolor=  #245C81>\n", out_file);
  fputs("<th>Tag</th>\n", out_file);
  fputs("<th>Field Name</th>\n", out_file);
  fputs("<th>Field Value</th>\n", out_file);
  fputs("<th>Req'd</th>\n", out_file);
  fputs("</tr>\n", out_file);


  //will go the end of the list 

  while( current != NULL){

    /* printf("(%i) tag: %d \n field name: %s field value: %s \n Req'd: %c \n Node_Number: %i \n",
           counter, current -> tag,
           current -> field_name,
           current -> field_value,
           current -> reqd,
           current -> node_counter); */
    /*

    fputs("<table border = ""1"" > \n", out_file); 
    fputs("<tr>\n", out_file);
    fputs("<th>Tag</th>\n", out_file); 
    fputs("<th>Field Name</th>\n", out_file); 
    fputs("<th>Field Value</th>\n", out_file); 
    fputs("<th>Req'd</th>\n", out_file); 
    */

    char tag_buffer[10];
    sprintf(tag_buffer, "%d", current->tag);
   
    //writting HTML                                                                                                                                                               
    fputs("<tr bgcolor = #BDBDBD >\n", out_file);

    //adding the tag                                                                                                                                                               
    fputs("<th>", out_file);
    fputs(tag_buffer, out_file);
    fputs("</th>\n", out_file);

    //adding the field                                                                                                                                                             
    fputs("<th>", out_file);
    fputs(current -> field_name, out_file);
    fputs("</th>\n", out_file);

    //adding the field_value                                                                                                                                                       
    fputs("<th>", out_file);
    fputs(current -> field_value, out_file);                                                                                                                                       
    fputs("</th>\n", out_file);

    //adding the field_value 
    char reqd_buffer[10];
    sprintf(reqd_buffer, "%c", current -> reqd);
                                                                                                                                                      
    fputs("<th>", out_file);
    fputs( reqd_buffer, out_file);
    fputs("</th>\n", out_file);
 
    fputs("</tr>\n", out_file);
     
    counter++;
    current = current -> previous;

  }//end of while loop   

  //----------ADDING THE BODY OF THE MESSAGE ---------/////
  
  counter = 1;
  current = ((*header)->body);

  while( current != NULL){

    // printf("(%i) tag: %d \n field name: %s field value: %s \n Req'd: %c \n Node_Number: %i \n",
    //       counter, current -> tag,
    //       current -> field_name,
    //       current -> field_value,
    //       current -> reqd,
    //       current -> node_counter); 

    char tag_buffer[10];
    sprintf(tag_buffer, "%d", current->tag);

    //writting HTML                                                                                                                                                                
    fputs("<tr bgcolor = #81BEF7>\n", out_file);
    
    //adding the tag                                                                                                                                                               
    fputs("<th>", out_file);
    fputs(tag_buffer, out_file);
    fputs("</th>\n", out_file);

    //adding the field                                                                                                                                                             
    fputs("<th>", out_file);
    fputs(current -> field_name, out_file);
    fputs("</th>\n", out_file);

    //adding the field_value                                                                                                                                                       
    fputs("<th>", out_file);
    fputs(current -> field_value, out_file);
    fputs("</th>\n", out_file);

    //adding the field_value                                                                                                                                                       
    char reqd_buffer[10];
    sprintf(reqd_buffer, "%c", current -> reqd);

    fputs("<th>", out_file);
    fputs( reqd_buffer, out_file);
    fputs("</th>\n", out_file);

    fputs("</tr>\n", out_file); 
   
    counter++;
    current = current -> previous;

  }

  //----------ADDING THE TRAILER OF THE MESSAGE ---------/////  

  counter = 1;
  current = ((*header)->body->trailer);

  //  printf("The value of TRAILER TAG is %d \n", current -> tag);
  
  char tag_buffer[10];
  sprintf(tag_buffer, "%d", current->tag);

  //writting HTML                                                                                                                                                                  
  fputs("<tr bgcolor = #FE2E64>\n", out_file);

  //adding the tag                                                                                                                                                                 
  fputs("<th>", out_file);
  fputs(tag_buffer, out_file);
  fputs("</th>\n", out_file);

  //adding the field                                                                                                                                                               
  fputs("<th>", out_file);
  fputs(current -> field_name, out_file);
  fputs("</th>\n", out_file);

  //adding the field_value                                                                                                                                                         
  fputs("<th>", out_file);
  fputs(current -> field_value, out_file);
  fputs("</th>\n", out_file);

  //adding the field_value                                                                                                                                                         
  char reqd_buffer[10];
  sprintf(reqd_buffer, "%c", current -> reqd);

  fputs("<th>", out_file);
  fputs( reqd_buffer, out_file);
  fputs("</th>\n", out_file);

  fputs("</tr>\n", out_file);

  //-------INCREMENTING THE OUTER LOOP ------------///

  ++m_counter;
  ++header;

 }//end of the outher while loop 

  //--------END OF THE OUTER LOOP -----------//
  
    fputs("</table> \n", out_file);  
    fputs("<br> </br> \n", out_file); 
    fputs("</div> \n", out_file);
    fputs("</div> \n", out_file);
 
    fputs("<div id = ""footer""> \n UChicago Fall 2015 \n </div> \n", out_file); 
    fputs("</body> \n", out_file); 
    fputs("</html> \n", out_file);


    ///////--------------- CSS -------------------////////////
 
    fputs("<style> \n", out_file);
    
    fputs("#header { \n ", out_file); 
    fputs("background-color:#245C81; \n ", out_file);
    fputs( "color:white;\n", out_file);
    fputs( "text-align:center;\n", out_file);
    fputs( "padding:5px; } \n", out_file);
     
    fputs("#msg_space { \n padding: 2em 2em 4em; \n  margin-left: auto; \n margin-right: auto; \n ", out_file); 
    fputs(" width: auto; \n text-align: center; \n font-family: ""Helvetica Neue"", Helvetica, Arial, sans-serif; \n", out_file); 
    fputs(" font-size: 16px; \n line-height: 1.5em;\n color: #ffffff; \n  background-color: #ffffff; \n box-shadow: 0 0 2px rgba(0, 0, 0, 0.06); \n } \n ", out_file); 

    fputs( "#footer { \n background-color:#245C81; \n color:white;\n clear:both; \n text-align:center; \n padding:5px; \n } \n", out_file); 
    
    fputs(" </style> \n", out_file); 

  //closing the out_file 
  fclose(out_file);


}//end of genHTML 


void check_body(  Node ** input, Node ** checker){

  int num_required = numReqd(checker);
  int tag_check[num_required];

  int counter = 0;

  Node * current = * checker;

  while( (current->next) != NULL){

    if (current->reqd == 'Y'){
      tag_check[counter] = current -> tag;
      counter++;
    }
    current = current -> next;

  }//end of while loop  

//  printf("The total number of required elements is %i \n", num_required);                                                                                                        
//printf("The required elements are : \n");  
 
 for ( int i = 0; i < num_required; i++){                                                                                                                                          
   // printf("%i, ", tag_check[i]);   
  }

 //printf("\n"); 

}//end of check_body 



//This function will check if the input message is valid message 
void check_message( Node ** input, Node ** checker){

  //The Message header has 7 required tags: 8, 9, 34, 35, 49, 52, 56
  //The FIRST THREE tags MUST be 8, 9, 35   

  // finding the number of required elements and initialzing an array 
  // to fit all those elemets
  int num_required = numReqd(checker); 
  int tag_check[num_required];

  int counter = 0; 

  Node * current = * checker; 

  while( (current->next) != NULL){

    if (current->reqd == 'Y'){
      tag_check[counter] = current -> tag;
      counter++; 
    }						
    current = current -> next;

  }//end of while loop     

  /*
  printf("The total number of required elements is %i \n", num_required); 
  for ( int i = 0; i < num_required; i++){                                                                                                                                                          printf("%i, ", tag_check[i]);                                     
  */


  //traversing the input message header list 
  Node * first = * input; 

  while( (first->next) != NULL){
    
    if ( checkReqd(&first, first -> tag) == 1) {
      
      // printf("%i is a required tag \n", first -> tag);

      for ( int i = 0; i < num_required; i++){                                                                                                                                    
	if  (tag_check[i] == (first -> tag)){

	  //printf("The value of tagcheck is %i\n", tag_check[i]);

	        tag_check[i] = 0; 
	}
      }//end of for loop 
      
    }//end of second if statement
   		
    first = first -> next;

  }//

  
  int check_sum = 0; 
  for ( int i = 0; i < num_required; i++){                                                                                                                                                     
    check_sum = check_sum + tag_check[i];                                                                                                        
  }
  printf("\n");

  // printf("The vaue of check_sum is %i \n", check_sum);

  if( check_sum!= 0){
    printf("ERROR: Missing a required field! \n"); 
  }

 
  //getting the pointer to the tail 
  Node * tail = listTail(input);

  if ( (tail -> tag) != 8){   
    printf ("ERROR: The first tag in the message header is not 8! \n");
  }
 
  tail = tail -> previous; 
    
  if ( (tail -> tag) != 9){
    printf ("ERROR: The second tag in the message header is not 9!\n");
  }
 
  tail = tail -> previous;

  if ( (tail -> tag) != 35){
    printf ("ERROR: The third tag in the message header is not 34! \n"); 
  }
 
  printf("\n");

}


//code is taken from http://www.onixs.biz/fix-dictionary/4.2/app_b.html
char *GenerateCheckSum( char *buf, long bufLen )
{
  static char tmpBuf[ 4 ];
  long idx;
  unsigned int cks;

  for( idx = 0L, cks = 0; idx < bufLen; cks += (unsigned int)buf[ idx++ ] );
  sprintf( tmpBuf, "%03d", (unsigned int)( cks % 256 ) );  
  return( tmpBuf );
}

//sprintf( tmpBuf, "%03d", (unsigned int)( cks % 256 ) );
