package stf;

import java.util.*;

// Java program to minimize average completion time

class Process 
{ 
	int pid; // Process ID 
	int bt; // Burst Time 
	
	
	public Process(int pid, int bt) 
	{ 
		this.pid = pid; 
		this.bt = bt; 
		 
	}
        
} 


public class STF 
{ 
	// Method to find the waiting time for all 
	// processes 
        
	static void findWaitingTime(Process proc[], int n, int wt[]) 
	{ 
		wt[0]=0; 
	
		
		for (int i = 1; i < n; i++) 
			wt[i]=proc[i-1].bt+wt[i-1]; 
	} 
	
	// Method to calculate completion time 
	static void findCompletionTime(Process proc[], int n, int wt[], int tat[]) 
	{ 
		
		for (int i = 0; i < n; i++) 
			tat[i] = proc[i].bt + wt[i]; 
	} 
	
	// Method to calculate average time 
	static void findavgTime(Process proc[], int n) 
	{ 
		
                int[] wt =new int[n];
                int[] tat= new int[n]; 
                int total_wt=0, total_tat=0; 
	
		// Function to find waiting time of all 
		// processes 
		//System.out.print(n);
                findWaitingTime(proc, n, wt); 
	
		// Function to find completion for 
		// all processes 
		findCompletionTime(proc, n, wt, tat); 
	
		// Display processes along with all 
		// details 
		System.out.println("Processes " + 
						" Processing time " + 
						" Waiting time " + 
						" Completion time"); 
	
		// Calculate total waiting time and 
		// completion time 
		for (int i = 0; i < n; i++) { 
			total_wt = total_wt + wt[i]; 
			total_tat = total_tat + tat[i]; 
			System.out.println(" " + proc[i].pid + "\t\t"
							+ proc[i].bt + "\t\t " + wt[i] 
							+ "\t\t" + tat[i]); 
		} 
	
		System.out.println("Average waiting time = " + 
						(float)total_wt / (float)n); 
		System.out.println("Average Completion time = " + 
						(float)total_tat / (float)n); 
	} 
	
	// Driver Method 
	public static void main(String[] args) 
	{ 
		Process proc[] = { new Process(1, 6), 
                                   new Process(2, 8),
                                   new Process(3, 7), 
				   new Process(4, 3)}; 
                System.out.println("order of process execution:");
  
                int[] procbt = new int[proc.length];
                for (int i = 0; i < proc.length; i++) {
                procbt[i]=proc[i].bt;
                }
                Arrays.sort(procbt);
                for (int j = 0; j < proc.length; j++) {
                    for (int k = 0; k < proc.length; k++){
                if(proc[k].bt==procbt[j]){
                
                System.out.println(proc[k].pid);
                }
                    }    
                }
		findavgTime(proc, proc.length); 
	} 
} 
