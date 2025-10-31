
/**
 * LinkedDeque.java - doubly-linked list implementation of Deque ADT
 */
import java.util.NoSuchElementException;

public class LinkedDeque<T> implements DequeInterface<T>
{
    private DLNode firstNode;
    private DLNode lastNode;
    
    public LinkedDeque()
    {
        firstNode = null;
        lastNode = null;
    }
    
    private class DLNode
    {
        private T data;
        private DLNode next;
        private DLNode previous;
        
        public DLNode(T theData)
        {
            data = theData;
            next = null;
            previous = null;
        }
    }
    
    /** Adds a new entry to the front/back of this deque.
    @param newEntry  An object to be added. */
    public void addToFront(T newEntry)
    {
        //create new Node
        DLNode newNode = new DLNode(newEntry);
        
        //if empty, this is also the lastNode; if not empty, connect firstNode BACK to newNode
        if (isEmpty())
            lastNode = newNode;
        else
            firstNode.previous = newNode;
            
        //either case, connect newNode FORWARD to firstNode
        newNode.next = firstNode;
        
        //reassign firstNode
        firstNode = newNode;
        
    }

    public void addToBack(T newEntry)
    {
        //create new Node
        DLNode newNode = new DLNode(newEntry);
        
        //if empty, this is also the firstNode; if not, connect lastNode FORWARD to newNode
        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.next = newNode;
            
        //either case, connect newNode BACK to lastNode
        newNode.previous = lastNode;
        
        //reassign lastNode
        lastNode = newNode;
        
    }

    /** Removes and returns the front/back entry of this deque.
    @return  The object at the front/back of the deque.
    @throws  NoSuchElementException if the deque is empty before the
    operation. */
    public T removeFront()
    {
        T front = getFront();       // might throw exception
        
        firstNode = firstNode.next; // reassign first node
        
        if (firstNode == null)      //if this was the only Node, must set lastNode as well
            lastNode = null;
        else
            firstNode.previous = null;  //otherwise, we close off the new front's back pointer
            
        return front;
            
    }

    public T removeBack()
    {
        T back = getBack();         // might throw exception
        
        lastNode = lastNode.previous;   // reassign last node
        
        if(lastNode == null)        // if this was the only Node, must set firstNode as well
            firstNode = null;
        else
            lastNode.next = null;   // otherwise, close off new last node's forward pointer
            
        return back;
    }

    /** Retrieves the front/back entry of this deque.
    @return  The object at the front/back of the deque.
    @throws  NoSuchElementException if the deque is empty. */
    public T getFront()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        else 
            return firstNode.data;
    }

    public T getBack()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return lastNode.data;
    }

    /** Detects whether this deque is empty.
    @return  True if the deque is empty, or false otherwise. */
    public boolean isEmpty()
    {
        return (firstNode == null) && (lastNode == null);
    }

    /*  Removes all entries from this deque. */
    public void clear()
    {
        firstNode = null;
        lastNode = null;
    }
}
