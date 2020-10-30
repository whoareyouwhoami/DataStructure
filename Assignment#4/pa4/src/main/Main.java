
public class Main {
	public static void main(String[] args) {
		IHeap<Integer> heap = new Heap<>(new Integer[] {1, 4, 7, 3 });
		Node<Integer> node = heap.getRoot();
		System.out.println("mainCall : "+node.getData());
		System.out.println("Hello, world\n");
	}
}