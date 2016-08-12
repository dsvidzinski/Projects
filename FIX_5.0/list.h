
typedef struct node {

  int tag;
  int node_counter;
  char * field_name;
  char * field_value; 
  char  reqd;

  struct node* next;
  struct node* previous;

} Node;


//functions defined in list.c 
extern void Push( Node ** headRef, int tag, char * fieled_name, char * field_value, char reqd);
extern Node * BuildList();
extern Node * BuildCheckList(char * input_file);
extern Node * BuildMessageTypeList(); 
extern int Length(struct node * head);
extern void printList( Node ** head);
extern void printReverse( Node ** head);
extern void reversePrint( Node ** head);
extern bool checkReqd(Node ** head, int tag); 
extern bool checkTag( Node ** head, int tag);
extern int listHead(Node ** head);
extern Node * listTail(Node ** head);
extern Node * reverseList(Node ** head);
