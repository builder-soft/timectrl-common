package cl.buildersoft.timectrl.test;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 System.out.println(isNumeric("1.2"));
		 System.out.println(isNumeric("1"));
		 System.out.println(isNumeric(""));

		// System.out.println( 7 > String.valueOf("1234567").trim().length());
		// System.out.println( 7 > String.valueOf("12345678").trim().length());
		// System.out.println( 7 > String.valueOf("123456").trim().length());
		// System.out.println( 7 > String.valueOf("1234567").trim().length());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static boolean isNumeric(String str) throws IllegalArgumentException {
		boolean isnumeric = false;

		if (0 < str.trim().length()) {
			isnumeric = true;
			char[] chars = str.toCharArray();

			for (int i = 0;

			i < chars.length; i++) {
				if (!Character.isDigit(chars[i])) {
					isnumeric = false;
				}
			}
		}

		return isnumeric;
	}

	public static void setRutCliente(String rutCliente) {
		System.out.println(0 < rutCliente.trim().length() && 11 > rutCliente.trim().length());

	}

}
