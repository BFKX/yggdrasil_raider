package tools;

public class Node<T> {
    private Node next;
    private T payload;

    public Node(T payload, Node next) {
        this.payload = payload;
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public T getPayload() {
        return payload;
    }
}
