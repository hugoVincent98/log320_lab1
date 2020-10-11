//www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/

// Java program to sort hashmap by values 
import java.util.*; 
import java.lang.*; 

public class GFG { 

	// function to sort hashmap by values 
	public static HashMap<Byte, Integer> sortByValue(HashMap<Byte, Integer> hm) 
	{ 
		// Create a list from elements of HashMap 
		List<Map.Entry<Byte, Integer> > list = 
			new LinkedList<Map.Entry<Byte, Integer> >(hm.entrySet()); 

		// Sort the list 
		Collections.sort(list, new Comparator<Map.Entry<Byte, Integer> >() { 
			public int compare(Map.Entry<Byte, Integer> o1, 
							Map.Entry<Byte, Integer> o2) 
			{ 
				return (o1.getValue()).compareTo(o2.getValue()); 
			} 
		}); 
		
		// put data from sorted list to hashmap 
		HashMap<Byte, Integer> temp = new LinkedHashMap<Byte, Integer>(); 
		for (Map.Entry<Byte, Integer> aa : list) { 
			temp.put(aa.getKey(), aa.getValue()); 
		} 
		return temp; 
	} 
} 
