
public class Main {
	public static void main(String[] args) {
		// Stack<Integer> stack = new Stack<Integer>();
		
		// ILISP lisp = new LISP();
		// boolean exist = lisp.checkBracketBalance("({)}");
		// System.out.println("=== RESULT ===\n" + exist);

		// ICalc calc = new PostfixCalc();
		// calc.evaluate("10 4 - 2 -");

		ICafe cafe = new Cafe();
		cafe.arrive(1, 1, 1);
		System.out.println(cafe.serve());
		System.out.println(cafe.stat());
	}
}