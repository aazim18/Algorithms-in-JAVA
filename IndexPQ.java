
import java.util.Iterator;

public class IndexPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
    private int n;           // number of elements on Priority Queue
    private int[] pq;        // heap ordered complete binary tree
    private int[] qp;        // inverse of priority queue
    private Key[] keys;      
    private int maxN;
    private int counter1=0,counter2=0,counter3=0,counter4=0;
    
    public IndexPQ(int maxN) {
        n = 0;
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];
        keys = (Key[]) new Comparable[maxN + 1];
        for (int imax = 0; imax <= maxN; imax++)
            qp[imax] = -1;
    }

    //function for queue size
    public int size() {
    	counter4++;
    	return n;
        
    }
    
    //insert function
    public void insert(int ins, Key key) {
    	counter1++;  
    	n++;
        qp[ins] = n;
        pq[n] = ins;
        keys[ins] = key;
        swim(n);
        
    }

    //delete function
    public int FirstDelete() {
    	counter2++;
    	if (n == 0);
        int min = pq[1];
        exch(1, n--);
        sink(1);

        assert pq[n+1] == min;
        qp[min] = -1;        
        keys[min] = null;    
        pq[n+1] = -1;        
        
        return min;
        
    }

    //change function
    public void changeKey(int ci, Key key) {
    	counter3++;
    	if (!contains(ci));
        
        keys[ci] = key;
        
        swim(qp[ci]);
        sink(qp[ci]);
        
    }

    public void print() {
    	System.out.println("Insert counter: "+ counter1);
    	System.out.println("Delete counter: "+ counter2);
    	System.out.println("Change counter: "+ counter3);
    	System.out.println("Report counter: "+ counter4);
    }   
    

    //queue empty checking 
    public boolean isEmpty() {
        return n == 0;
    }
    //queue existing element checking
    public boolean contains(int c) {
    	if (c<0||c >= maxN)
    		return qp[c]!=-1;
		return false;
    }

    
    private boolean less(int iles, int jles) {
        return keys[pq[iles]].compareTo(keys[pq[jles]]) < 0;
    }

    private void exch(int iles, int jles) {
        int swap = pq[iles];
        pq[iles] = pq[jles];
        pq[jles] = swap;
        qp[pq[iles]] = iles;
        qp[pq[jles]] = jles;
    }

    private void swim(int ksw) {
        while (ksw > 1 && less(ksw/2, ksw)) {
            exch(ksw, ksw/2);
            ksw = ksw/2;
        }
    }

    private void sink(int ksi) {
        while (2*ksi <= n) {
            int jsi = 2*ksi;
            if (jsi < n && less(jsi, jsi+1)) jsi++;
            if (!less(ksi, jsi)) break;
            exch(ksi, jsi);
            ksi = jsi;
        }
    }

    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        
        private IndexPQ<Key> copy;

        public HeapIterator() {
            copy = new IndexPQ<Key>(pq.length - 1);
            for (int ihi = 1; ihi <= n; ihi++)
                copy.insert(pq[ihi], keys[pq[ihi]]);
        }
        public boolean hasNext()  { return !copy.isEmpty();                     }
        
        public Integer next() {
        
            return copy.FirstDelete();
        }
    }
    
    //main function
    public static void main(String[] args) {
        // insert a bunch of strings
        String[] strings = { "MIT", "Harvard", "USC", "Yale", "Princeton", "UCLA", "Temple", "OSU", "GATech", "NYU" };
        String[] random = {"DHAKA","Syllet"};
        String[] insertS = strings.clone(); 
        
        IndexPQ<String> pq1 = new IndexPQ<String>(strings.length);
        IndexPQ<String> pq2 = new IndexPQ<String>(strings.length);
        IndexPQ<String> pq3 = new IndexPQ<String>(strings.length);
        
        
        for (int i1 = 0; i1 < strings.length; i1++) {
            pq1.insert(i1, strings[i1]);
        }
        
        for (int i2 = 0; i2 < strings.length; i2++) {
            pq2.insert(i2, strings[i2]);
        }
        
        for (int ip1 : pq1) {
        	System.out.println(ip1 + " " + strings[ip1]);
        }
        
        boolean found = false;
        for (String element:insertS  ) {
        	if(element.equals(random[0])) {
        		found = true;
        	}
        }
        
        if (found) {
        	System.out.println("NO Add");
        }
        
        else {
        	insertS[insertS.length-1] = random[0]; 
        }
        
        
        for (int i2 = 0; i2 < insertS.length; i2++) {
            pq3.insert(i2, insertS[i2]);
        }
        
        //for (int ip2 : pq3) {
        	//System.out.println(ip2 + " " + insertS[ip2]);
        //}
        
       //Changing word at valid index i
        
        int j1=5;
        pq1.changeKey(j1, random[0]);
        
            
        
        //Reporting the number of words in the structure
        
        System.out.println("Size of the Queue: "+pq1.size());
        
        
        //Removing first word in the structure
        if (!pq1.isEmpty())
        {
        	pq1.FirstDelete();
        }
        
        //System.out.println("Queue after removal:\n");
        //for (int i : pq1) {
        	//System.out.println(i + " " + strings[i]);
        //}
        
        pq1.print();
        }

}