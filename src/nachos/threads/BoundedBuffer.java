package nachos.threads;

public class BoundedBuffer {
    private Lock bufferLock = new Lock();
    private int maxsize;
    private int size;
    private char[] buffer;
    private int nextIN;
    private int nextOUT;
    private Condition2 overflow = new Condition2(bufferLock);
    private Condition2 underflow = new Condition2(bufferLock);


    // non-default constructor with a fixed size
    public BoundedBuffer(int maxsize){
        this.maxsize = maxsize;
        this.buffer = new char[maxsize];
        this.size = 0;
        this.nextIN = 0;
        this.nextOUT = 0;
    }
    // Read a character from the buffer, blocking until there is a char
    // in the buffer to satisfy the request. Return the char read.
    public char read(){
        bufferLock.acquire();
        if (size == 0) {
            underflow.sleep();
        }

        char toReturn = buffer[nextOUT];
        nextOUT = (nextOUT + 1) % maxsize;
        size--;

        if (size == maxsize -1) { overflow.wake();}
        bufferLock.release();
        return toReturn;
    }
    // Write the given character c into the buffer, blocking until
    // enough space is available to satisfy the request.
    public void write(char c){
        bufferLock.acquire();
        while (size == maxsize){
            overflow.sleep();
        }
        buffer[nextIN] = c;
        nextIN=(nextIN + 1) % maxsize;
        size++;
        if (size == 1){underflow.wake();}
        bufferLock.release();
    }
    // Prints the contents of the buffer; for debugging only
    public void print(){
        bufferLock.acquire();
        System.out.println(this.buffer);
        bufferLock.release();
    }
    public int size(){
        return size;
    }
}
