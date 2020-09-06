
public class Main
{
	public static void main(String[] args)
	{
		Sorter sorter = new Sorter();
		
		int[] asc = sorter.ascending(new int[] {1, 2, 3});
		

		System.out.println("Hello, world");
		System.out.println("\n=== RESULT ===");
		for(int i = 0; i < asc.length; i++) {
			System.out.print(asc[i] + " ");
		}
		System.out.println("");
	}
}