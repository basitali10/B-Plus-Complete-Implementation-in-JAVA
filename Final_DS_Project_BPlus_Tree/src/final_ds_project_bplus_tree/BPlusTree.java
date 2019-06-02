/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author well come
 */

//------------------- B+ TREE CLASS-------------------
public class BPlusTree{
    //MEMBER VARIABLES
    private BPlusNode root;
    private final int m;
    private int size;
    
    //Constructor
    public BPlusTree(int m_order){
        m=m_order;
        root=null;
        size=0;
    }
    //MEMBER FUNCTIONS
    
    //SEARCHING 
    public BPlusNode[] search(int value,boolean toInsert){
        BPlusNode temp;
        temp=root;
        BPlusNode previous=null;
        BPlusNode[] two= new BPlusNode[2];
        int i,j;
        boolean here=false,found=false;
        while(true){
            if(temp.isleaf){
                for(i=0;i<m-1;i++){
                    if(temp.keys[i]==value){
                        if(!toInsert){
                            System.out.println("NODE FOUND .");
                            found=true;
                        }
                        two[0]=temp;
                        break;
                    }
                }
                if(!toInsert && !found){
                    System.out.println("NODE NOT FOUND");
                }
                two[0]=temp;
                break;
            }
            else{
                for(i=0;i<m-1;i++){
                    if(value<temp.keys[i] && temp.keys[i]!=-1){
                        previous=temp;
                        temp=temp.childrens[i];
                        here=true;
                        break;
                    }
                    else{
                        if(temp.keys[i]==-1){
                            break;
                        }
                    }
                }
                if(!here){
                    previous=temp;
                    temp=temp.childrens[i];
                    here=false;
                }
            }
        }
        two[1]=previous;
        return two;
    }
    
    //INSERTION
    public void insert(int value){
        
        int i,j,k;
        if(root==null){
            BPlusNode newa=new BPlusNode(m);
            for(i=0;i<m-1;i++){
                if(newa.keys[i]==-1){
                    newa.keys[i]=value;
                    root=newa;
                    size++;
                    return;
                }
            }
        }
        if(root.keys[m-2]==-1 && root.isleaf ){
            for(i=0;i<m-1;i++){
                if(root.keys[i]>value && root.keys[i]!=-1){
                    for(j=m-2;root.keys[j]!=-1;j--){
                        
                    }
                    for(k=j-1;k>=i;k--){
                        root.keys[k+1]=root.keys[k];
                        root.childrens[k+2]=root.childrens[k+1];
                    }
                    root.keys[i]=value;
                    root.childrens[i]=null;
                    size++;
                    return;
                }
                if(root.keys[i]==-1){
                    root.keys[i]=value;
                    root.childrens[i]=null;
                    size++;
                    return;
                }
            }
        }
            
        BPlusNode[] second=search(value,true);
        BPlusNode insertHere=second[0];
        BPlusNode par=second[1];
        insertHere.parent=par;
        if(insertHere.keys[m-2]==-1){
            for(i=0;i<m-1;i++){
                if(insertHere.keys[i]==-1){
                    insertHere.keys[i]=value;
                    insertHere.parent=par;
                    break;
                }
                if(insertHere.keys[i]>value){
                    for(j=m-2;insertHere.keys[j]!=-1;j--){
                        
                    }
                    for(k=j-1;k>=i;k--){
                        insertHere.keys[k+1]=insertHere.keys[k];
                        insertHere.childrens[k+2]=insertHere.childrens[k+1];
                    }
                    insertHere.keys[i]=value;
                    insertHere.childrens[i]=null;
                    break;
                }
                if(insertHere.keys[i]==-1){
                    insertHere.keys[i]=value;
                    insertHere.childrens[i]=null;
                    break;
                }
            }
        }
        else{
            //splitting
            split(insertHere,par,value);
//          
        }
        size++;
    }
    
    //DELETION
    public void delete(int value){
        
    }
    //MERGING
    
    public void merge(){
    }
    
    //SPLITTING
    private BPlusNode split(BPlusNode kuchBhi,BPlusNode par,int value){
        int i,j=0,k,u;
        if(kuchBhi!=root){
            BPlusNode newNode=new BPlusNode(m+1);
            for(i=0;i<m-1;i++){
                newNode.keys[i]=kuchBhi.keys[i];
            }
            for(i=0;i<m;i++){
                newNode.childrens[i]=kuchBhi.childrens[i];
            }
            specialSort(newNode,value);
            
            int median=newNode.keys[(m)/2];
            //check if parent is also full;
            
            if(par.keys[m-2]!=-1){
                
                par=split(par,par.parent,median);
                
                
            }
            for(i=0;i<m-1;i++){
                if(par.keys[i]>=median){
                    j=m-2;
                    while(j>0 && par.keys[j]!=-1){
                        j--;
                    }
                    for(k=j-1;k>=i;k--){
                        par.keys[k+1]=par.keys[k];
                        par.childrens[k+2]=par.childrens[k+1];
                    }
                    par.keys[i]=median;
                    par.childrens[i]=null;
                    break;
                }
                if(par.keys[i]==-1){
                    par.keys[i]=median;
                    par.childrens[i]=null;
                    break;
                }
            }
            int leftChildren=i;
            int rightChildren=i+1;
            int[] temp=new int[m-(m/2)+1];
            j=0;
            for(i=(m/2);i<m;i++){
                temp[j++]=newNode.keys[i];
                newNode.keys[i]=-1;
            }
            BPlusNode other=new BPlusNode(m); 
            if(kuchBhi.isleaf){
                for(i=0;i<j;i++){
                    other.keys[i]=temp[i];
                }
            }
            else{
                u=0;
                for(i=0;i<j;i++){
                    if(temp[i]!=median){
                        other.keys[u++]=temp[i];
                    }
                }
            }
            for(i=(m/2);i<m-1;i++){
                kuchBhi.keys[i]=-1;
            }
            for(i=0;i<m/2;i++){
                kuchBhi.keys[i]=newNode.keys[i];
            }
            par.childrens[leftChildren]=kuchBhi;
            par.childrens[rightChildren]=other;
            kuchBhi.parent=par;
            other.parent=par;
            kuchBhi.next=other;
            par.isleaf=false;
            if(value>=par.keys[leftChildren]){
                return par.childrens[rightChildren];
            }
            else{
                return par.childrens[leftChildren];
            }
        }
        else{
            //WHEN KUCHBHI IS OUR ROOT
            BPlusNode extra=new BPlusNode(m+1); 
            for(i=0;i<m-1;i++){
                extra.keys[i]=kuchBhi.keys[i];
            }
            for(i=0;i<m;i++){
                extra.childrens[i]=kuchBhi.childrens[i];
            }
            extra=specialSort(extra,value);
            
            BPlusNode newRoot=new BPlusNode(m); 
            newRoot.keys[0]=extra.keys[m/2];
            
            int[] temp=new int[m-(m/2)+1];
            
            for(i=(m/2);i<m;i++){
                temp[j]=extra.keys[i];
                j++;
                extra.keys[i]=-1;
            }
            
            BPlusNode other=new BPlusNode(m); 
            
            if(kuchBhi.isleaf){
                for(i=0;i<j;i++){
                    other.keys[i]=temp[i];
                }
            }
            else{
                u=0;
                for(i=0;i<j;i++){
                    if(temp[i]!=value){
                        other.keys[u++]=temp[i];
                    }
                }
            }
            for(i=(m/2);i<m-1;i++){
                kuchBhi.keys[i]=-1;
                kuchBhi.childrens[i+1]=null;
            }
            for(i=0;i<m/2;i++){
                kuchBhi.keys[i]=extra.keys[i];
                kuchBhi.childrens[i]=extra.childrens[i];
            }
            newRoot.childrens[0]=kuchBhi;
            newRoot.childrens[1]=other;
            kuchBhi.parent=newRoot;
            other.parent=newRoot;
            kuchBhi.next=other;
            root=newRoot;
            root.isleaf=false;
            if(value>=root.keys[0]){
                return root.childrens[1];
            }
            else{
                return root.childrens[0];
            }
        }
    }
    
    //TRAVERSALS
    public void preOrderTraversal(){
        if(root!=null){
            Stack <BPlusNode > s1=new Stack<>();
            int i,visitedNode=1;
            System.out.println(" \n\nPRE ORDER TRAVERSAL : ");
            s1.push(root);
            while(!s1.isEmpty()){
                BPlusNode temp=s1.pop();
                System.out.print("Visited node Number : "+(visitedNode++)+" = | ");
                for(i=0;i<m-1;i++){
                    if(temp.keys[i]!=-1){
                        System.out.print(temp.keys[i]+" ");
                    }
                }
                System.out.print(" |");
                for(i=m-1;i>=0;i--){
                    if (temp.childrens[i] != null) {
                        s1.push(temp.childrens[i]);
                    }
                }
                System.out.println("");
            }
            System.out.println("\n\n");
                
        }
        else{
            System.out.println("Tree is empty.");
        }
    }
    public void inOrderTraversal (){
        if(root==null ){
            System.out.println("Tree is empty.");
        }
        else{
            if(root.isleaf){
                System.out.println("| ");
                for(int i=0;i<m-1;i++){
                    if(root.keys[i]!=-1){
                        System.out.print(root.keys[i]+"  ");
                    }
                }
                System.out.print("| ----> null\n\n ");
            }
            else{
                System.out.println("\n\nINORDER TRAVERSAL USING LINKED LIST : ");
                BPlusNode first=findFirstleaf();
                while(first!=null){
                    System.out.print("| ");
                    for(int i=0;i<m-1;i++){
                        if(i<(m-1) && first.keys[i]!=-1){
                            System.out.print(first.keys[i]+" ");
                        }
                    }
                    System.out.print("| ---->  ");
                    first=first.next;
                }
                System.out.println("null\n\n");
            }
        }
    }
    public void levelOrderTraversal(){
        int i;
        // Base Case
        if(root == null){
            System.out.println(" \n\nTree is Empty.\n\n");
            return;
        }
        // Create an empty queue for level order tarversal
        Queue<BPlusNode> q1 =new LinkedList<>();
        int levelno=1;
        System.out.println("\n\nLEVEL ORDER TRAVERSAL : \n\n");
        // Enqueue Root and initialize height
        q1.add(root);
        System.out.print("\n\nRoot Level : ");
        while(true){
             
            // nodeCount (queue size) indicates number of nodes
            // at current level.
            int nodeCount = q1.size();
            if(nodeCount == 0){
                break;
            }
             
            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while(nodeCount > 0){
                BPlusNode temp = q1.element();
                System.out.print(" | ");
                for(i=0;i<m-1;i++){
                    if(temp.keys[i]!=-1){
                        System.out.print(temp.keys[i]+"  ");
                    }
                }
                System.out.print("  | ");
                q1.remove();
                for(i=0;i<m;i++){
                    
                    if (temp.childrens[i] != null) {
                        q1.add(temp.childrens[i]);
                    }
                }
                nodeCount--;
            }
            System.out.println();
            System.out.print("LEVEL "+levelno+ " : ");
            levelno++;
        }
        System.out.println("\n\n");
    }
    
    //UTILITY FUNCTIONS
    private BPlusNode specialSort(BPlusNode p,int val){
        int i,j,k;
        for(i=0;i<m;i++){
            if(p.keys[i]>val){
                for(j=m-1;p.keys[j]!=-1;j--){

                }
                for(k=j-1;k>=i;k--){
                    p.keys[k+1]=p.keys[k];
                    p.childrens[k+2]=p.childrens[k+1];
                }
                p.keys[i]=val;
                p.childrens[i+1]=null;
                break;
            }
            if(p.keys[i]==-1){
                p.keys[i]=val;
                p.childrens[i+1]=null;
                break;
            }
        }
        return p;
    }
    private BPlusNode findFirstleaf() {
        BPlusNode temp=root;
        while(temp.childrens[0]!=null){
            temp=temp.childrens[0];
        }
        return temp;
    }
    public int getSize() {
        System.out.println("Total no of elements in the B+ tree = "+this.size);
        return this.size;
    }
    public int heightOfTheTree(){
        int i;
        if(root == null){
            System.out.println(" Tree is Empty.");
            return -1;
        }
        Queue<BPlusNode> q1 =new LinkedList<>();
        int levelno=0;
        q1.add(root);
        while(true){
            int nodeCount = q1.size();
            if(nodeCount == 0){
                break;
            }
            while(nodeCount > 0){
                BPlusNode temp = q1.element();
                q1.remove();
                for(i=0;i<m;i++){
                    
                    if (temp.childrens[i] != null) {
                        q1.add(temp.childrens[i]);
                    }
                }
                nodeCount--;
            }
            levelno++;
        }
        return (levelno-1);
    }
    public int countNodes(){
        Queue q1=new LinkedList();
        int j=1,i,k=0,levelno=1,totalNodes=0,leafNodes=0,internalNodes=0;
        int [] countChildrens=new int[size*4];
        Arrays.fill(countChildrens, 9999);
        countChildrens[0]=0;
        if(this.root!=null){
            q1.add(root);
            while(!q1.isEmpty()){
                
                BPlusNode temp=(BPlusNode) q1.element();
                if(temp.isleaf){
                    leafNodes++;
                }
                else{
                    internalNodes++;
                }
                q1.remove();
                if(countChildrens[k]<=0){
                    levelno++;
                    k++;
                }
                else{
                    countChildrens[k]--;
                }
                countChildrens[j]=0;
                for(i=0;i<m;i++){
                    if (temp.childrens[i] != null) {
                        q1.add(temp.childrens[i]);
                        countChildrens[j]=countChildrens[j]+1;
                    }
                }
                j++;
            }
            totalNodes=leafNodes+internalNodes;
            System.out.println("\nLeaf Nodes = "+leafNodes+"\nInternal Nodes = "+internalNodes+"\n\nTotal Nodes = "+totalNodes+"\n\n");
            
        }
        return (totalNodes);
    }
}