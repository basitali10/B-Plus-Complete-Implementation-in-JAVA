/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_ds_project_bplus_tree;

import java.util.Arrays;

/**
 *
 * @author well come
 */

//------------------- B+ NODE CLASS-------------------
public class BPlusNode{
    //MEMBER VARIABLES
    boolean isleaf;
    BPlusNode[] childrens;
    int[ ] keys;
    BPlusNode next;
    BPlusNode parent;
    //CONSTRUCTOR
    public BPlusNode(int m){ // m is the order of the node
        childrens=new BPlusNode [m];
        keys=new int[m-1];
        Arrays.fill(keys,-1); //-1 Means the value in the node is empty.
        isleaf=true; //Because New inserted Node will always be inserted at leaf.
        next=null; //Points to it's neighbour
        parent=null;
    }
}
