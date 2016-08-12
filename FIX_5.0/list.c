#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

 
//defining a list node struct                                                                                                                                                       
typedef struct node {

  int tag;
  int node_counter;
  char * field_name;
  char * field_value; 
  char  reqd;

  struct node* body; 
  struct node* trailer;
  struct node* next;
  struct node* previous;

} Node;



/*
Node * BuildMessageTypeList();
void printList( Node ** head);

///--------IMPLEMENTING LINKED_LIST FUNCTIONS-------///

int main(){

  //  printf("Hello World! \n"); 

  Node * msgList; 
  msgList =  BuildMessageTypeList();


  Node * current = msgList; 

  printList(&msgList);

  while ( (current -> next) != NULL){

    if ( strcmp(current -> field_name , "A") == 0 ){
      printf("We found it!\n"); 
      printf("%s", current -> field_value); 
	     }

    current = current->next; 
  }
  
  return 0; 
}
*/


Node * BuildList(){

  Node * head;
  head = malloc(sizeof(Node));

  head -> node_counter = 0;
  head -> next = NULL;
  head -> previous = NULL;

  return head;

}//end of BuildOneTwoThree                                                                                                                                                          


//a method that inserts nodes at the front of the lst                                                                                                                              
void Push( Node ** headRef, int tag_num, char * field_name, char * field_value, char reqd){

  //declaring and allocating space                                                                                                                                                  
  Node * newNode;
  newNode = malloc(sizeof(Node));

  //initialzing fields                                                                                                                                                               
  newNode -> node_counter = ((*headRef)->node_counter)+1;
  newNode -> tag = tag_num;
  newNode -> field_name = field_name;
  newNode -> field_value = field_value; 
  newNode -> reqd = reqd;
  newNode -> next = *headRef;

  //making the list double linked                                                                                                                                                    
  (*headRef) -> previous = newNode;

  *headRef = newNode;

}//end of insertInFront  



//bulding message check list 
Node * BuildMessageTypeList(){

  //initialzing the head                                                                                                                                                           
  Node * head;
  head = malloc(sizeof(Node));
  
  head -> node_counter = 0;
  head -> next = NULL;
  head -> previous = NULL;

  //Reading from the input textfile and store it in a linked list                                                                                                              
  char m[500][50] = {0};
  FILE * fp;
  fp = fopen ("./Library/Msg/Type", "r");

  char * line = NULL;
  size_t len = 0;
  ssize_t read;
  int counter = 0;

  while ((read = getline(&line, &len, fp)) != -1) {
    strcpy(m[counter], line);
    counter++;
  }

  //closing the file                                                                                                                                                          \
   fclose(fp);
  if (line)
    free(line);

  //Tokenizing the infomaton and adding it to a list;                                                                                                                           
  char* token;
  char* token_array[2];
  int array_counter;

  int i;
  for ( i = 1; i < counter; i++){

    token = strtok(m[i], "\t");

    array_counter = 0;

    while (token) {                                                                                                                                                           \

      token_array[array_counter] = token;
      array_counter++;
      token = strtok(NULL, "\t");
    }

    Push(&head, 0, token_array[0], token_array[1], 0);

  }//end of the for loop                                                                                                                                                       

  return head; 

}//BuildMessageTypeList 




//bulding a checklist                                                                                                                                                              
Node * BuildCheckList(char * input_file){

  //initialzing the head                                                                                                                                                           
  Node * head;
  head = malloc(sizeof(Node));

  head -> node_counter = 0;
  head -> next = NULL;
  head -> previous = NULL;

  //Reading from the input textfile and store it in a linked list                                                                                                                  
  char m[500][50] = {0};

  FILE * fp;
  fp = fopen (input_file, "r");

  char * line = NULL;
  size_t len = 0;
  ssize_t read;
  int counter = 0;

  while ((read = getline(&line, &len, fp)) != -1) {
    strcpy(m[counter], line);
    counter++;
  }

  //closing the file                                                                                                                                                               
  fclose(fp);
  if (line)
    free(line);

  //Tokenizing the infomaton and adding it to a list;                                                                                                                               
  char* token;
  char* token_array[3];
  int array_counter;

  int i;
  for ( i = 1; i < counter; i++){

    token = strtok(m[i], "\t");

    array_counter = 0;

    while (token) {
      // printf("token: %s\n", token);                                                                                                                                               
      token_array[array_counter] = token;
      array_counter++;
      token = strtok(NULL, "\t");
    }

    Push(&head, atoi(token_array[0]), token_array[1], "none", *token_array[2]);

  }//end of the for loop                                                                                                                                                             
  return head;

} //end of BuildCeckList    

                                                                                                                                                                                   
//Length Utility Function                                                                                                                                                          
int Length(Node * head){

  struct  node * current = head;
  int count = 0;

  while ( current != NULL){
    count++;
    current = current -> next;

  }//end while                                                                                                                                                                     
                                                                                                                                                                                   
  return count;

}//end of Length method                                                                                                                                                              
                                                                                                                                                                                                                                                                                                                                                  
//printfList function                                                                                                                                                               
void printList( Node ** head){

  int counter = 1;
  Node * current = *head;

  while( current->next != NULL){

    printf("(%i) tag: %d \n field name: %s field value: %s \n Req'd: %c \n Node_Number: %i \n", 
	  counter, current -> tag,  
          current -> field_name, 
          current -> field_value, 
          current -> reqd, 
          current -> node_counter);
    
    printf("\n"); 
 
     counter++;
    current = current -> next;

  }//end of while loop                                                                                                                                                             

}//end of print list functio



//printfList function                                                                                                                                                               
void printReverse( Node ** head){

  int counter = 1;
  Node * current = *head;

  while( current != NULL){

    printf("(%i) tag: %d \n field name: %s field value: %s \n Req'd: %c \n Node_Number: %i \n",
	   counter, current -> tag,
	   current -> field_name,
	   current -> field_value,
	   current -> reqd,
	   current -> node_counter);

    printf("\n");

    counter++;
    current = current -> previous;

  }//end of while loop                                                                                                                                                               

}//end of print list functio 


bool checkReqd(Node ** head, int tag){

  bool reqd = false;

  int counter = 1;
  Node * current = *head;

  while( (current->next) != NULL){

    if (current->tag == tag){

      if (current->reqd == 'Y'){
	reqd = true; 
      }//end of the inner if 
   
    }//end of the outer if 

    current = current -> next;

  }//end of while loop                                                                                                                                                              
                                                                                                                                                                                       return reqd;
}



//travers the list to find if the tag is present in this type of message                                                                                                           
bool checkTag( Node ** head, int tag){

  bool has_tag = false;

  int counter = 1;
  Node * current = *head;

  while( (current->next) != NULL){

    if (current->tag == tag){
      has_tag = true;
    }

    counter++;
    current = current -> next;

  }//end of while loop                                                                                                                                                                                                                                                                                                                                                
  return has_tag;

}//end of checkTag




// traverse the list and check the number
// of required elements

int numReqd(Node ** head){

  int num_required = 0; 

  Node * current = *head;

  while( (current->next) != NULL){

      if (current->reqd == 'Y'){        
	num_required++; 
      }//end of the inner if                                                                                                                                                                                                                                                                                                                 
    current = current -> next;

  }//end of while loop    

  return num_required; 

}//end 


//list front in O(1)                                                                                                                                                               
int listHead(Node ** head) {
  Node * current = *head;
  return current ->tag;
}


//list tail in O(n)                                                                                                                                                                
Node * listTail(Node **head){

  Node * current = *head;

  while( current->next != NULL){
    current = current ->next;
  }

  return (current -> previous); 
}
