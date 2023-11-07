/**
 * This file contains a MyLinkedList class used to create, structure, organize, add to, remove from, and access a linked list of data. 
 * 
 * @author <Aiden Rowe>
 * 
 * This file is a part of a lab project from Oberlin College that was assigned in Professor Adam Eck's Data Structures 151 class on Oct 9, 2023. 
 * 
 * This file was authored in its entirety by Aiden Rowe but uses instructions contributed to by the following faculty members in the Oberlin College Computer Science Department: Stephen Checkoway, Adam Eck, Molly Q Feldman, Blair Rossetti, Alexa Sharp, Sam Taggart, Cynthia Taylor, Emily Wang, Tom Wexler, Lucas Draper. The lab instructions were also contributed to by the following Oberlin Students: Veronica Ayars, Hannah Babe, Tara Bobinac, Meg Davis, Eliza Bomfim Guimaraes Giane, William Knowles-Kellett, Pascale Leone, Mist Newman, Marilyn Recarte, Shiying Zheng.
*/

// IMPORTS 
import java.util.AbstractList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

//-----------------------------------------------

// MY LINKED LIST CLASS
 public class MyLinkedList<T> extends AbstractList<T> {

    // Class Variables 
    private DoublyLinkedNode firstNode; // Beginning Node 
    private DoublyLinkedNode lastNode; // Ending Node 
    private int size; // Current list size 

    //-----------------------------------------------

    // Constructor 
    /* Creates the basic structure of an empty List */
    public MyLinkedList() {
        // Empty List Creator 
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    //-----------------------------------------------

    // Size Getter 
    /* Returns the current size of the List */
    public int size() {
        return size;
    }

    //-----------------------------------------------
    
    // Variable Retriever 
    /* Returns the Node in the requested Index; throws an OutOfBounds error if index requested is too high */
    private DoublyLinkedNode getNthNode(int index) {

        // Setup
        DoublyLinkedNode node = this.firstNode; // Setting the starting point as the head of the List 
        int count = 0; // Number of loops through 

        // Looping
        while(true) {

            // Index Out of Range 
            /* Throws an error when the Index requested is not in the range of the current List*/
            if (node.next == null && count != index){
                throw new IndexOutOfBoundsException("Tried to retrieve an item from MyLinkedList with too large of an index. Index:" + index + " but size is " + this.size);

            // Index Not Found
            /* Moves on to the next Node as long as there is one; increases count by one */
            } else if (count != index ) {
                node = node.next;
                count++;
                continue;

            // Index Found
            /* Returns the correct Node at the index requested */
            } else {
                return node;
            }
        }
    }

    //-----------------------------------------------

    // Adding 
    public void add(int index, T item) {
        
        // Item is Null 
        /* Throws NullPointer error if item passed in is null */
        if (item == null) {
            throw new NullPointerException("Item:" + item + " is null");

        // Index Out of Range 
        /* Throws OutOfBounds error if index is out of range of the current List */ 
        } else if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index:" + index + " is out of range for the current List");

        // Index in Range  
        /* Continues forward as long as Item isn't null and Index is in range */
        } else {

            // Empty List  
            if (size == 0) {

                // New Node Maker 
                /* Creates a new Node to add to the List */
                DoublyLinkedNode newItem = new DoublyLinkedNode(item, null, null);

                // Adder
                /* Adds the new Item in as both the first and last Node in the List */
                this.firstNode = newItem;
                this.lastNode = newItem;

            // Index at the Start of List   
            } else if (index == 0) {

                // New Node Maker 
                /* Creates a new Node to add to the List */
                DoublyLinkedNode newItem = new DoublyLinkedNode(item, null, this.firstNode);

                // Update Old Head
                /* Sets the previous of the old head Node as the new Item being added; sets the head equal to the new Item */
                newItem.next.previous = newItem;
                this.firstNode = newItem;

            // Index at the End of List  
            } else if (index == size) {
                
                // New Node Maker 
                /* Creates a new Node to add to the List */
                DoublyLinkedNode newItem = new DoublyLinkedNode(item, this.lastNode, null);

                // Update Old Tail
                /* Sets the next of the old tail Node as the new Item being added; sets the tail equal to the new Item */
                this.lastNode.next = newItem;
                this.lastNode = newItem;

            // Index Within Range of the List (but not on the edges)
            } else {
                
                // Old Node 
                /* Finds the Node currently in the Index requested */
                DoublyLinkedNode oldNode = getNthNode(index);

                // New Node Maker 
                /* Creates a new Node to add to the List */
                DoublyLinkedNode newItem = new DoublyLinkedNode(item, oldNode.previous, oldNode);

                // Old Previous Next
                /* Sets the old previous Node's next to the new Item */
                oldNode.previous.next = newItem;

                // Old Node Previous
                /* Sets the old Node's previous to the new Item */
                oldNode.previous = newItem;
            }

            // Size Increaser 
                /* Increases size by one to account for the new item in the list */
                this.size++;
        }
    }

    //-----------------------------------------------

    // Adding Without Index 
    public boolean add(T item) {
        
        // Add Method Called 
        this.add(this.size, item); // Index set to size so Item is added at the end of the List 

        // Return
        return true;
    }

    //-----------------------------------------------

    // Getter 
    public T get(int index) {
        
        // Index Not In Range 
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index:" + index + " is out of range for the current List");

        // Index In Range 
        } else {
            return getNthNode(index).item;
        }
    }

    //-----------------------------------------------

    // Replacing 
    public T set(int index, T item) {
        
        // Item is Null
        /* Throws NullPointer error if item passed in is null */
        if (item == null) {
            throw new NullPointerException("Item:" + item + " is null");

        // Index Out of Range 
        /* Throws OutOfBounds error if index is out of range of the current List */  
        } else if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index:" + index + " is out of range for the current List");

        // Replacing
        } else {

            // Old Node 
            /* Retrieves the old Node at the Index requested */
            DoublyLinkedNode oldNode = getNthNode(index);

            // New Node Maker 
            /* Creates a new Node to add to the List */
            DoublyLinkedNode newItem = new DoublyLinkedNode(item, oldNode.previous, oldNode.next);

            // Previous Node
            /* Sets old Node's previous's next to the new Item */
            if (oldNode.previous != null) {
                oldNode.previous.next = newItem;
            } else {
                this.firstNode = newItem;
            }

            // Next Node
            /* Sets old Node's next's previous to the new Item */
            if (oldNode.next != null) {
                oldNode.next.previous = newItem;
            } else {
                this.lastNode = newItem;
            }

            // Return 
            return oldNode.item;
        }
    }

    //-----------------------------------------------

    // Removing
    public T remove(int index) {
        
        // Index Out of Range 
        /* Throws OutOfBounds error if index is out of range of the current List */  
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index:" + index + " is out of range for the current List");

        // Removing
        } else {

            // Old Node 
            /* Retrieves the old Node at the Index requested */
            DoublyLinkedNode oldNode = getNthNode(index);

            // Old Node was Head
            if (oldNode.previous == null) {
                this.firstNode = oldNode.next;
                oldNode.next.previous = null;

            // Old Node was Tail
            } else if (oldNode.next == null) {
                this.lastNode = oldNode.previous;
                oldNode.previous.next = null;

            // Old Nose was in the Middle
            } else {
                oldNode.previous.next = oldNode.next;
                oldNode.next.previous = oldNode.previous;
            }

            // Size Reduction
            /* Reduces the size of the List by one */
            this.size--;

            // Return 
            return oldNode.item;
        }
    }
    
    //-----------------------------------------------

    // Empty?
    /* Checks if the current list is empty or not */
    public boolean isEmpty() {
        
        // Setup
        boolean empty = false;

        // Checking
        if (size == 0){
            empty = true;
        }

        // Return
        return empty;
    }

    //-----------------------------------------------

    // Clear 
    /* Clears all items in the current list and resets the size to zero */
    public void clear() {

        // Clearing Head and Tail
        this.firstNode = null;
        this.lastNode = null;
        
        // Clearing Size
        this.size = 0;
    }

    /////////////////////////////////////////////////

    // DOUBLY LINKED NODE INNER CLASS
    /* Creates each Node of the list */
    public class DoublyLinkedNode {

        // Class Variables
        private T item; // The item stored in this Node
        private DoublyLinkedNode previous; // Previous Node in the list (null if Node is first in sequence)
        private DoublyLinkedNode next; // Next Node in the list (null if Node is last in sequence)

        //-----------------------------------------------

        // Constructor 
        DoublyLinkedNode(T i, DoublyLinkedNode prev, DoublyLinkedNode nex) {
            this.item = i;
            this.previous = prev;
            this.next = nex;
        }    
    }

    /////////////////////////////////////////////////

    // MY LINKED LIST ITERATOR CLASS
    private class MyLinkedListIterator implements ListIterator<T> {

        // Class Variables 
        private int place;
        private DoublyLinkedNode focus;

        //-----------------------------------------------

        // Constructor 
        public MyLinkedListIterator() {
            this.place = -1;
            this.focus = firstNode;
        }

        //-----------------------------------------------

        // New Iterators
        /* Creates a new instance of the MyLinkedListIterator class */
        public ListIterator<T> listIterator(){return new MyLinkedListIterator();}
        public ListIterator<T> Iterator() {return listIterator();}

        //-----------------------------------------------

        // Has Next
        /* Checks if there is an Item previous to the current Node */
        public boolean hasNext() {
            
            // Place in Range
            boolean hasNext = this.place < size - 1;

            // Return
            return hasNext;
        }

        //-----------------------------------------------

        // Next 
        /* Returns the Item previous to the current Node */
        public T next() {
            
            // No Next Exists
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element in the current list");

            // There's a Next
            } else {
                
                // Setting Next
                this.focus = this.focus.next;

                // Increasing Placemet 
                this.place++;

                // Return
                return this.focus.item;
            }
        }

        //-----------------------------------------------

        // Has Previous
        /* Checks if there is an Item previous to the current Node */
        public boolean hasPrevious() {
            
            // Place in Range
            boolean hasPrevious = place > 0;

            // Return
            return hasPrevious;
        }

        //-----------------------------------------------

        // Previous
        /* Returns the Item previous to the current Node */
        public T previous() {
            
            // No Previous Exists
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no previous element in the current list");

            // There is a Previous
            } else {
                
                // Setting Next
                this.focus = this.focus.previous;

                // Increasing Placemet 
                this.place--;

                // Return
                return this.focus.item;
            }
        }

        //-----------------------------------------------

        // Uneeded Stuff
        /* The following methods are required in a class that extends AbstractList, however they were not used for this lab */
        public int nextIndex() {throw new UnsupportedOperationException();}
        public int previousIndex() {throw new UnsupportedOperationException();}
        public void add(T item) {throw new UnsupportedOperationException();}
        public void remove() {throw new UnsupportedOperationException();}
        public void set(T item) {throw new UnsupportedOperationException();}
    }
 }
