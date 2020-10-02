
public class Main {
	public static void main(String args[]) {
		System.out.println("1, 2, 3, 4, 5, 6, 7, 8, 9 ou 10");
		
		int array[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

		int i = 0;
		
		do {
			System.out.print(array[i]);
			
			if(i + 1 == array.length) {
				break;
			}
			
			if(!(i + 2 == array.length)) {
				System.out.print(", ");
			} else{
				System.out.print(" ou ");
			}
			i++;
		} while(i < array.length);
		
		System.out.println();
		
		System.out.println(juntarNumeros(0, array));
	}
	
	public static String juntarNumeros(int i, int array[]) {
		
		if(i == 0) {
			return String.format("%d", array[i]) + juntarNumeros(++i, array);
		}
		
		if(i + 1 == array.length) {
			return String.format(" ou %d", array[i]);
		} 
		
		return String.format(", %d", array[i]) + juntarNumeros(++i, array);
	}
}
