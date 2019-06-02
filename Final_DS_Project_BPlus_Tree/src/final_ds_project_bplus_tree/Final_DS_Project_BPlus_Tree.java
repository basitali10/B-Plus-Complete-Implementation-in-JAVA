/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

import java.util.Scanner;

/**
 *
 * @author well come
 */
public class Final_DS_Project_BPlus_Tree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanf=new Scanner(System.in);
        int ch;
        int value;
        System.out.println("------WELCOME TO B+ TREE PROGRAM------\n\n1.Insert a value \n2.Delete A value\n3.Search for value\n4.Pre-Order Traversal\n5.In-Order Traversal\n6.Level Order Traversal\n7.No of Elements (size) \n8.Height of the Tree \n9.Count Nodes\n10.Exit\n\n");
        System.out.println("First Enter the order of the tree ( m ) : ");
        value = scanf.nextInt();
        if(value<=2){
            System.out.println("Sorry Invalid Order. Order can't be lesser then 3 .  ");
            return;
        }
        BPlusTree b1=new BPlusTree(value);
        do{
            System.out.println("--------------------------------------");
            System.out.println("Enter Your Choice : "); 
            ch=scanf.nextInt();
            switch(ch){
                case 1:
                    System.out.println("Enter value to insert : ");
                    value = scanf.nextInt();
                    b1.insert(value);
                    b1.levelOrderTraversal();
                    break;
                case 2:
                    System.out.println("We are still working on deletion. Deletion os BST is very tricky so we are trying our best to make this possible.");
                    break;
                case 3:
                    System.out.println("Enter value to search : ");
                    value = scanf.nextInt();
                    b1.search(value,false);
                    break;    
                case 4:
                    b1.preOrderTraversal();
                    break; 
                case 5:
                    b1.inOrderTraversal();
                    break;
                case 6:
                    b1.levelOrderTraversal();
                    break;    
                case 7:
                    b1.getSize();
                    break;
                case 8:
                    System.out.println("Height of the tree = "+b1.heightOfTheTree());
                    break;    
                case 9:
                    b1.countNodes();
                    break; 
                case 10:
                    System.out.println("------------Program ended Gracefully.------------");
                    System.exit(0);
                    break;    
                default:    
                    System.out.println("Invalid Choice.");
            }
        }while(ch!=10);
        
    }
    
}
