package btree;

import java.util.*;

class BTree
{

//variables available to tree

	static int order; // order of tree

	BNode root;  //every tree has at least a root node



// constructor for tree                        |

	public BTree(int order)
	{
		this.order = order;

		root = new BNode(order, null);

	}



// method to search for a given node where   
// to insert a key value.                                 



	public BNode search(BNode root, int key)
	{
		int i = 0;//to start searching the 0th index of node.

		while(i < root.count && key > root.key[i])//keep incrementing
                    							  //in node while key >
				                    			  //current value.
		{
			i++;
		}

		if(i <= root.count && key == root.key[i])
							                     
		{


			return root;
		}

		if(root.leaf)
        {

			return null ;

		}
		else//else if it is not leave recurse down through ith child
		{

			return search(root.getChild(i),key);

		}
	}

//  the split method.  It will split node, to insert into if it is full.                     


	public void split(BNode x, int i, BNode y)
	{
		BNode z = new BNode(order,null);

		z.leaf = y.leaf;//set boolean to same as y

		z.count = order - 1;//this is updated size

		for(int j = 0; j < order - 1; j++)
		{
			z.key[j] = y.key[j+order]; //copy end of y into front of z

		}
		if(!y.leaf)//if not leaf, reassign child nodes.
		{
			for(int k = 0; k < order; k++)
			{
				z.child[k] = y.child[k+order]; //reassing child of y
			}
		}

		y.count = order - 1; //new size of y

		for(int j = x.count ; j> i ; j--)//if pushing key into x,
		{				 //rearrange child nodes

			x.child[j+1] = x.child[j]; //shift children of x

		}
		x.child[i+1] = z; //reassign i+1 child of x

		for(int j = x.count; j> i; j--)
		{
			x.key[j + 1] = x.key[j]; // shift keys
		}
		x.key[i] = y.key[order-1];//finally push value up into root.

		y.key[order-1 ] = 0; //erase value pushed from

		for(int j = 0; j < order - 1; j++)
		{
			y.key[j + order] = 0; //'delete' old values
		}



		x.count ++;  //increase count of keys in x
	}


// insert method when node is not full.        


	public void nonfullInsert(BNode x, int key)
	{
		int i = x.count; //i is number of keys in node x

		if(x.leaf)
		{
			while(i >= 1 && key < x.key[i-1])//here find spot to put key.
			{
				x.key[i] = x.key[i-1];//shift values to make room

				i--;//decrement
			}

			x.key[i] = key;//finally assign value to node
			x.count ++; //increment count of keys in this node now.

		}


		else
		{
			int j = 0;
			while(j < x.count  && key > x.key[j])//find spot to recurse
			{			             //on correct child  		
				j++;
			}

		//	i++;

			if(x.child[j].count == order*2 - 1)
			{
				split(x,j,x.child[j]);//call split on node x's ith child

				if(key > x.key[j])
				{
					j++;
				}
			}

			nonfullInsert(x.child[j],key);//recurse
		}
	}

//the method to insert in general, it will call insert non full if needed.                                    


	public void insert(BTree t, int key)
	{
		BNode r = t.root;//this method finds the node to be inserted as 
				 //it goes through this starting at root node.
		if(r.count == 2*order - 1)//if is full
		{
			BNode s = new BNode(order,null);//new node
                        //node initialization
			t.root = s;    
	    			       	
			s.leaf = false;
	    			        
			s.count = 0;   
	    			       	
			s.child[0] = r;

			split(s,0,r);//split root

			nonfullInsert(s, key); //call insert method
		}
		else
			nonfullInsert(r,key);//if its not full just insert it
	}


// method to print out a node, or recurses when root node is not leaf 



	public void print(BNode n)
	{
		for(int i = 0; i < n.count; i++)
		{
			System.out.print(n.getValue(i)+" " );//this part prints root node
		}

		if(!n.leaf)//this is called when root is not leaf;
		{

			for(int j = 0; j <= n.count  ; j++)//in this loop, recurse
			{				  //to print out tree in
				if(n.getChild(j) != null) //preorder fashion.
				{			  //going from left most
				System.out.println();	  //child to right most
				print(n.getChild(j));     //child.
				}
			}
		}
	}


// method to print out a node                    


	public void SearchPrintNode( BTree T,int x)
	{
		BNode temp= new BNode(order,null);

		temp= search(T.root,x);

		if (temp==null)
		{

		System.out.println("The Key does not exist in this tree");
		}

		else
		{

		print(temp);
		}


	}


//method to delete a key value from the leaf node it is 
//in.  Using the search method to traverse through the   
//tree to find the node where the key is in.Then     
//iterating through key[] array until getting to node and    
//assign k[i] = k[i+1] overwriting key to delete            


       public void deleteKey(BTree t, int key)
       {
			       
		BNode temp = new BNode(order,null);
			
		temp = search(t.root,key);//call of search method on tree for key

		if(temp.leaf && temp.count > order - 1)
		{
			int i = 0;

			while( key > temp.getValue(i))
			{
				i++;
			}
			for(int j = i; j < 2*order - 2; j++)
			{
				temp.key[j] = temp.getValue(j+1);
			}
			temp.count --;
		
		}
		else
		{
			System.out.println("This node is either not a leaf or has less than order - 1 keys.");
		}
       }


}

class BNode
{
	static int t;  //variable to determine order of tree

	int count; // number of keys in node

	int key[];  // array of key values

	BNode child[]; //array of references

	boolean leaf; //is node a leaf or not

	BNode parent;  //parent of current node.


// default constructor for new node      


	public BNode()
	{}

// initial value constructor for new node                                 


	public BNode(int t, BNode parent)
	{
		this.t = t;  //assign size

		this.parent = parent; //assign parent

		key = new int[2*t - 1];  // array of proper size

		child = new BNode[2*t]; // array of refs proper size

		leaf = true; // everynode is leaf at first;

		count = 0; 
	}


// method to return key value at index position


	public int getValue(int index)
	{
		return key[index];
	}


// method to get ith child of node            


	public BNode getChild(int index)
	{
		return child[index];
	}


}

public class BMain 
{

	public static void main(String[] args)
	{
        Scanner input = new Scanner( System.in );
		int n,n2,temp;
		System.out.print("Enter the t of the Tree?  ");
		n=input.nextInt();
		
        while ( n<2)
		{	
            System.out.print("Please enter a integer greater than 1 : ");
			n=input.nextInt();//  User inputs the order of Tree and is assinged to N.
		}
		BTree tree = new BTree(n);//  B-Tree Tree with order  N is created.

        
		System.out.print("\n How many values do you want to enter?:  ");	
                n2 = input.nextInt();

        for ( int i=0;i< n2;i++)
		{
            System.out.print("\nEnter Value:");
			System.out.println(i+1);
			temp=input.nextInt();
			tree.insert(tree,temp);
                        System.out.println("Current Node \n");
                        tree.SearchPrintNode(tree, temp);
                        System.out.println("");
		}
		int choice,k;// Variables used to control the Repeated loop of the MENU.

        boolean flag;
		flag=true;
			
			System.out.println("1. Enter more values in a Tree");
			System.out.println("2. Print the whole  Tree in preorder");
			System.out.println("3. Search for a Key and print the Node it belongs to");
			System.out.println("4. Delete a key from the leaf");
			System.out.println("5. Exit");

		while (flag)// This While loop runs as long as the user enters anything other than a 5.
		{


			System.out.print("\nPlease enter your choice::");
			choice=input.nextInt();
			if ( choice == 5)
			{		
                System.out.printf("The program is exiting...,\n");
			    System.exit(0);
				flag=false;
				break;
			}
		
            else
			{
			 	switch(choice)
			 	{
			 		case 1: //If the User Enters 1 this case is executed and 
                            //its function is to Enter more values in a Tree.
	
                        System.out.print("How many values do you want to enter?:");
						n2=input.nextInt();

                        for ( int i=0;i< n2;i++)
						{
                            System.out.print("\nEnter Value: ");
							System.out.println(i+1);
							temp=input.nextInt();
							tree.insert(tree,temp);
                        }
                        break;

					case 2: //If the User Enters 2 this case is executed and 
                            //its function is to Print the whole  Tree in preorder format.
						
                        tree.print(tree.root);
						System.out.println();
						break;

					case 3: //If the User Enters 3 this case is executed and 
                            //its function is to Delete a key from the leaf
		
                        System.out.println("What is the key you wish to search for:");
						int key2=input.nextInt();
                                                System.out.println("Current node with searched key: ");
						tree.SearchPrintNode(tree,key2);

						break;
					case 4: //If User Enters 4, this case is executed 
                            //Its Function is to search for a Key and print the Node it belongs to
						
                        System.out.println("Enter a key to be deleted:");
						int key=input.nextInt();
						tree.deleteKey(tree,key);
						System.out.println("Here is the tree printed in preorder after delete");
						tree.print(tree.root);
						break;

					case 5: //If the User Enters 5, this case is executed and 
                            //its function is to Exit
			
                        System.exit(0);
						break;

						default: // If the User enters a wrong choice, then this case is executed.
						System.out.println("\nPlease enter a valid choice of 1,2,3 or 4\n");
						break;
	 			}//end of switch block
			 }//end of else block
		}//end while(flag)
	}//main
}//class