package gpsLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class FileHandling {

	static TreeMap<Long, List<Double>> importFiles(String path) {
		TreeMap<Long, List<Double>> map = new TreeMap<Long, List<Double>>();

		File file = new File(path);
		
		for (File f:file.listFiles((dir, name) ->  name.toLowerCase().endsWith(".csv"))) {
		    FileReader fr;
		    
		    LocalDateTime filename = LocalDateTime.of(Integer.valueOf(f.getName().substring(0, 4)), 
		    		Integer.valueOf(f.getName().substring(4, 6)), 
		    		Integer.valueOf(f.getName().substring(6, 8)), 
		    		Integer.valueOf(f.getName().substring(9, 11)), 
		    				Integer.valueOf(f.getName().substring(11, 13)), 
		    						Integer.valueOf(f.getName().substring(13, 15)));
			try {
				fr = new FileReader(f);
			    BufferedReader br = new BufferedReader(fr);
			    String zeile = br.readLine();
			    zeile = br.readLine();
			    long first = Long.valueOf(zeile.substring(0, zeile.indexOf(";")));
			    LocalDateTime dt = LocalDateTime.ofEpochSecond(first, 0, ZoneOffset.ofHours(1));
			    System.out.print("\n" + f.getName() + " (" + filename + ") first " + dt + " diff " + (first - filename.toEpochSecond(ZoneOffset.ofHours(1))));
			    
			     do {	
			    	String[] values = zeile.split(";");
			    	if (values.length < 7 || !values[6].contains("."))
			    		break;
			    	List<Double> list = new ArrayList<Double>();
			    	for (int i=1;i<values.length;i++) {
			    		if (values[i].isBlank())
			    			list.add(null);
			    		else
			    			list.add(Double.valueOf(values[i]));
			    	}
			    	long key = Long.valueOf(values[0]);
			    	if (map.containsKey(key)) {System.out.print("\n" + key + " exists");
			    		if (!map.get(key).equals(list)) {System.out.print("... is equal");
			    			if (!map.containsKey(key-1)) {System.out.print("... free space before...");
			    				map.put(key-1, map.get(key));
			    				map.put(key, list);
			    			} else
			    				map.put(key+1, list);
			    		}
			    	} else
			    		map.put(key, list);
			    			
			    } while((zeile = br.readLine()) != null);
		
			    br.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
		return map;
	}
	
	
}
