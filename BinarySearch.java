

public class BinarySearch {
	
	public static int rc1;
	public static int rc2;
	public static int final_count;
	public static int n;

	//Binary search algorithm
	public static int binarySearch(int[] a, int x) {
		int low = 0;
		int high = a.length - 1;
		int mid;
		
		while (low <= high) {
			mid = (low + high) / 2;
			if (a[mid] < x) {
				low = mid + 1;
				rc1++;
				
				
			} else if (a[mid] > x) {
				high = mid - 1;
				rc2++;
				
			} else {
				return mid;
				
			}
		}
		System.out.println("Input not in sorted arary\n");
		return -1;
	}
	
	static void finalcount () {
		final_count = (rc1 + rc2);
		System.out.println("AverageCost for Binary search = "+final_count);
	}
	
	//main function to perform binary search in array, where the value given
	//in variable input is searched.
	public static void main(String[] args) {
		int[] array = {5,15,25,35,45,55,65,75,85,95,105,115,125,135,145,155};
		    int input=20; 
			int location = binarySearch(array, input);
			System.out.println("element searched= "+input + ": " +"Location in array= " + location);
		    finalcount();
	}

}