/*  
 * Made by Jack Lynch, with base provided by Chris Fernandez
 *  CSC 335 - Operating Systems, 9/11/2025
 */

package nachos.threads;  // don't change this. Gradescope needs it.

public class DLList
{
    private DLLElement first;  // pointer to first node
    private DLLElement last;   // pointer to last node
    private int size;          // number of nodes in list

    /**
     * Creates an empty sorted doubly-linked list.
     */ 
    public DLList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * Add item to the head of the list, setting the key for the new
     * head element to min_key - 1, where min_key is the smallest key
     * in the list (which should be located in the first node).
     * If no nodes exist yet, the key will be 0.
     */
    public void prepend(Object item) {
        // If empty, start the list with the key = 0
        if (this.isEmpty()) {
            KThread.yieldIfShould(0);
            DLLElement newNode = new DLLElement(item, 0);
            first = newNode;
            KThread.yieldIfShould(4);
            last = newNode;
        }
        // not empty, prepend with key = first.key - 1
        else {
            KThread.yieldIfShould(1);

            DLLElement newNode = new DLLElement(item, first.key-1);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } 
        size +=1;
        KThread.yieldIfShould(2);
    }

    /**
     * Removes the head of the list and returns the data item stored in
     * it.  Returns null if no nodes exist.
     *
     * @return the data stored at the head of the list or null if list empty
     */
    public Object removeHead() {
        if (this.isEmpty()) return null;
        Object toReturn = first.data;
        first = first.next;
        if (first == null) last = null;
        else first.prev = null;
        size -= 1;
        return toReturn;
    }

    /**
     * Tests whether the list is empty.
     *
     * @return true iff the list is empty.
     */
    public boolean isEmpty() {
        if (size <= 0) return true;
        else return false;
    }

    /**
     * returns number of items in list
     * @return
     */
    public int size(){
        return size;
    }


    /**
     * Inserts item into the list in sorted order according to sortKey.
     */
    public void insert(Object item, Integer sortKey) {
        DLLElement newNode = new DLLElement(item, sortKey);
        // If list is empty, set first and last to newnode and finish
        if (this.isEmpty()) {
            size += 1;
            last = newNode;
            KThread.yieldIfShould(3);

            first = newNode;
            return;
        }

        DLLElement runner = first;
        while (!(runner == null) && runner.key < newNode.key) {
            runner = runner.next;
        }
        // When runner == null, the included sortKey is greater than all other sortkeys, and should be last.
        if (runner ==null) {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        } 
        // if runner is first, the included sortkey is less than all other sortkeys,should be set to first.
        else if (runner.equals(first)){
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        // else runner.key >= newnode.key
        else {
            newNode.next = runner;
            newNode.prev = runner.prev;
            if (newNode.prev != null) newNode.prev.next = newNode;
            newNode.next.prev = newNode;
        }
        size += 1;
    }


    /**
     * returns list as a printable string. A single space should separate each list item,
     * and the entire list should be enclosed in parentheses. Empty list should return "()"
     * @return list elements in order
     */
    public String toString() {
        DLLElement runner = first;
        String toReturn = "(";
        while (!(runner == null)) {
            if (!toReturn.equals("(")) toReturn += " ";
            toReturn += runner.toString();
            runner = runner.next;
        }
        return toReturn + ")";
    }

    /**
     * returns list as a printable string, from the last node to the first.
     * String should be formatted just like in toString.
     * @return list elements in backwards order
     */
    public String reverseToString(){
        DLLElement runner = last;
        String toReturn = "(";
        while (!(runner == null)) {
            if (!toReturn.equals("(")) toReturn += " ";
            toReturn += runner.toString();
            runner = runner.prev;
        }
        return toReturn + ")";
    }

    /**
     *  inner class for the node
     */
    private class DLLElement
    {
        private DLLElement next; 
        private DLLElement prev;
        private int key;
        private Object data;

        /**
         * Node constructor
         * @param item data item to store
         * @param sortKey unique integer ID
         */
        public DLLElement(Object item, int sortKey)
        {
        	key = sortKey;
        	data = item;
        	next = null;
        	prev = null;
        }

        /**
         * returns node contents as a printable string
         * @return string of form [<key>,<data>] such as [3,"ham"]
         */
        public String toString(){
            return "[" + key + "," + data + "]";
        }
    }
}
