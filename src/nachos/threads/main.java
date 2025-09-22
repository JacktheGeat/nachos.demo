package nachos.threads;

import nachos.threads.DLList;

public class main {
    public static void main(String[] args) {
        DLList linkedlist = new DLList();
        linkedlist.insert("A", 0);
        System.out.println(linkedlist.removeHead());
        System.out.println(linkedlist.reverseToString());


    }
    
}
