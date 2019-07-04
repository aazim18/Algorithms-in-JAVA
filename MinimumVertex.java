package reach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;
public class MinimumVertex {

	int[] reach;
	int[] colors;
	Vertex[] labelToVertex;
	public int[] reach(AjacentGraph graph, int[] labels){
		labelToVertex=new Vertex[graph.size()+1];
		
		//sorting by label in linear time.
		for (int i=0; i<graph.size(); i++){
			labelToVertex[labels[i]]=graph.vertices.getVertexByIndex(i);
		}
		
		reach=new int[graph.size()];
		colors=new int[graph.size()];
		Arrays.fill(colors, 0); //white
		
		AjacentGraph t=graph.transpose();
		for (int i=1; i<=graph.size(); i++){
			Vertex v=labelToVertex[i];
			if (colors[v.index]==0){
				//i is the label for v
				DFS(t, v.getIndex(),  i);
			} 
			
		}
		return reach;
	}
	
	
	public void DFS(AjacentGraph graph, int i, int minLabel){
		colors[i]=1; //grey
		for (Edge edge: graph.getEdges(i)){
			if (reach[edge.to]==0){
				//not set minLabel
				reach[edge.to]=minLabel; //no matter the children is touched or not, we set the minLabel.
			}
			if (colors[edge.to]==0){
				DFS(graph, edge.to,  minLabel);
			} 
		}
		colors[i]=2; //black
	}
	
	@Test 
        public void testMinimumVertex(){
		AjacentGraph g=new AjacentGraph();
                int[] label={2,4,7,8,3,1,9,6,5};
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		g.addVertex("i");
		
		g.initGraph();
		g.addEdge("a", "c", 1);
		g.addEdge("b", "e", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("c", "h", 1);
		g.addEdge("d", "e", 1);
		g.addEdge("d", "f", 1);
		
		g.addEdge("e", "g", 1);
		g.addEdge("f", "c", 1);
		g.addEdge("f", "d", 1);
		g.addEdge("f", "g", 1);
		g.addEdge("g", "i", 1);
		g.addEdge("i", "h", 1);
	
		int[] expected={1,3,1,1,5,1,5,0,6};
		String resultStr=Arrays.stream(reach(g, label)).mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
		System.out.println("Resulting Minimum vertices:");
                System.out.println(resultStr);
		String expectedStr=Arrays.stream(expected).mapToObj(i->new Integer(i).toString()).collect(Collectors.joining(","));
		System.out.println("Expected Minimum vertices:");
                System.out.println(expectedStr);
                assertThat(resultStr, equalTo(expectedStr));
                
		
		
	}
        
        public static void main(String args[]) {
      org.junit.runner.JUnitCore.main("reach.MinimumVertex");
    }
  
	
}

class AjacentGraph  extends Graph{
	

	public ArrayList<LinkedList<Edge>> ajacentList=new ArrayList<>();
	
	public void initGraph() {
		for (int i=0; i<vertices.verticesArray.size(); i++) {
			ajacentList.add(new LinkedList<Edge>());
		}
	}
	public void addEdge(String from, String to, int distance) {
		Vertex fromVertex=vertices.getVertexByName(from);
		Vertex toVertex=vertices.getVertexByName(to);
		Edge edge=new Edge(fromVertex.index, toVertex.index, distance);
		ajacentList.get(fromVertex.index).add(edge);
	}
	
	public void addEdge(int from, int to) {
		Edge edge=new Edge(from, to, 1);
		ajacentList.get(from).add(edge);
	}
	 
	@Override
	public List<Edge> getEdges(int index) {
		return ajacentList.get(index);
	}
	
	public  AjacentGraph transpose(){
		
		 AjacentGraph t=new  AjacentGraph();
		 t.vertices=this.vertices;
		 t.initGraph();
		 this.ajacentList.stream().flatMap(edges->edges.stream()).forEach(edge->t.addEdge(edge.to, edge.from));
		 return t;
	}
	
	
}

class Vertex {

	public String name;
	public int index;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Vertex(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}
	
	public String toString() {
		return name;
	}
}

abstract class Graph {

	Vertices vertices=new Vertices();
	

	public int size(){
		return vertices.verticesArray.size();
	}
	public void addVertex(String name) {
		vertices.addVertex(name);
	}
	public abstract void initGraph();
	public abstract void addEdge(String from, String to, int distance);
	
	public abstract List<Edge> getEdges(int index) ;
	
	public static class TempStruct implements Comparable<TempStruct>{
		public int selfVertex;
		public int from;
		public int distance;
		@Override
		public int compareTo(TempStruct o) {
			if (distance<o.distance) {
				return -1;
			}
			if (distance>o.distance) {
				return 1;
			}
			return 0;
		}
		public TempStruct(int selfVertex, int distance) {
			super();
			this.selfVertex = selfVertex;
			this.distance = distance;
		}
		
		
	}
	public List<Vertex> dijkstraShortestPath(String from, String to){
		
		PriorityQueue<TempStruct> unvisited=new PriorityQueue<>();
		int fromIndex=vertices.getVertexByName(from).index;
		int toIndex=vertices.getVertexByName(to).index;
	
		
		TempStruct[] tempStructs=new TempStruct[vertices.verticesArray.size()];
		for (int i=0; i<vertices.verticesArray.size(); i++) {
			TempStruct tempStruct;
			if (i==fromIndex) {
				tempStruct=new TempStruct(i, 0);
				tempStruct.from=-1;
			} else {
				tempStruct=new TempStruct(i, Integer.MAX_VALUE);
				
			}
			unvisited.add(tempStruct);
			tempStructs[i]=tempStruct;
		}

		
		TempStruct tempStruct;
		while ((tempStruct=unvisited.poll())!=null) {
			if (tempStruct.distance==Integer.MAX_VALUE) {
				
				break;
			}
			if (tempStruct.selfVertex==toIndex) {
				break;
			}
			for (Edge edge: getEdges(tempStruct.selfVertex)) {
				if (edge==null) {
					continue;
				}
				TempStruct targetStruct=tempStructs[edge.to];
				if (targetStruct.distance>tempStruct.distance+edge.distance) {
					unvisited.remove(targetStruct);
					targetStruct.distance=tempStruct.distance+edge.distance;
					targetStruct.from=tempStruct.selfVertex;
					unvisited.add(targetStruct);
				}
			}

		}
		if (tempStruct.distance==Integer.MAX_VALUE) {
			return null;
		}
		LinkedList<Vertex> path=new LinkedList<>();
		TempStruct lastOne=tempStruct;
		while (true) {
			path.addFirst(vertices.verticesArray.get(lastOne.selfVertex));
			if (lastOne.from!=-1) {
				lastOne=tempStructs[lastOne.from];
			} else {
				break;
			}
			
		} 
		
		
		return path;
		
	}
}

class Vertices {

	public Map<String, Vertex> verticeMaps=new HashMap<String, Vertex>();
	public ArrayList<Vertex> verticesArray=new ArrayList<>();
	
	public Vertex addVertex(String vertex) {
		Vertex vertex2=new Vertex(vertex, verticesArray.size());
		verticesArray.add(vertex2);
		verticeMaps.put(vertex, vertex2);
		return vertex2;
	}
	
	public Vertex getVertexByName(String name) {
		return verticeMaps.get(name);
	}
	
	public Vertex getVertexByIndex(int index) {
		return verticesArray.get(index);
	}
	
}

class Edge {
	public int from;
	public int to;
	public int distance;
	public Edge(int from, int to, int distance) {
		super();
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
}
