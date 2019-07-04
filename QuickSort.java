 
import java.util.*; 
  
class Quick2 
{ 
// Swap function
static void Swap(int arr[], int pos1, int pos2) 
{  
    int t = arr[pos1];     
    arr[pos1] = arr[pos2];   
    arr[pos2] = t;  
} 
  
// The textbook algorithm for partition 
static int partition_txt(int arr[], int low, int high) 
{ 
    int pivot = arr[high]; 
    int i = (low - 1); 
    for (int j = low; j <= high- 1; j++) 
    {  
        if (arr[j] <= pivot) 
        { 
            i++;  
            Swap(arr, i, j); 
        } 
    } 
    Swap(arr, i + 1, high); 
    return (i + 1); 
} 
  
// QuickSort by using The textbook algorithm
static void quickSort_txt(int arr[], int low, int high) 
{ 
    if (low < high) 
    { 
        int pi = partition_txt(arr, low, high);  
        quickSort_txt(arr, low, pi - 1); 
        quickSort_txt(arr, pi + 1, high); 
    } 
} 

//Hoare partition algorithm
static int partition_hoare(int arr[], int low, int high) 
{ 
    int pivot = arr[low]; 
    int i = low - 1, j = high + 1; 
  
    while (true) 
    {  
        do
        { 
            i++; 
        } while (arr[i] < pivot); 
 
        do
        { 
            j--; 
        } while (arr[j] > pivot); 
  
        if (i >= j) 
            return j; 
        int t = arr[i]; 
        arr[i] = arr[j]; 
        arr[j] = t; 
        
    } 
} 
  
//QuickSort using hoare partition algorithm
static void quickSort_hoare(int arr[], int low, int high) 
{ 
    if (low < high) 
    { 
        int pi = partition_hoare(arr, low, high); 
        quickSort_hoare(arr, low, pi); 
        quickSort_hoare(arr, pi + 1, high); 
    } 
}
/* Function to print an array */
static void printArray(int arr[], int size) 
{ 
    int x; 
    for (x = 0; x < size; x++)
    System.out.print(" " + arr[x]); 
    System.out.println(); 
} 
  
// The main function to quick sort a random array 
static public void main (String[] args) 
{ 
    
    Random r = new Random();
    int arr[]=new int[10];
    for (int ai=0;ai<arr.length;ai++)
    {
     arr[ai]= r.nextInt(10)+1;
    }
    
    System.out.println("Random array to be sorted : "); 
    System.out.println(Arrays.toString(arr));
    
    int n = arr.length;
    int arr_txt[] =arr.clone();
    int arr_hoare[] =arr.clone();
    
    quickSort_txt(arr_txt, 0, n-1); 
    System.out.println("Sorted array by TextBook algorithm : "); 
    printArray(arr_txt, n); 
    
    quickSort_hoare(arr_hoare, 0, n - 1); 
    System.out.println("Sorted array by Hoare algorithm: "); 
    printArray(arr_hoare, n);
} 
} 
  
