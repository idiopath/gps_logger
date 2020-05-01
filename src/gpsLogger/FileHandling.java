package gpsLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileHandling {

//	static TreeMap<Long, List<Double>> importFiles(String path) {
//		TreeMap<Long, List<Double>> map = new TreeMap<Long, List<Double>>();
//		
//		File file = new File(path);
//		
//		for (File f:file.listFiles((dir, name) ->  name.toLowerCase().endsWith(".csv"))) {
//		    FileReader fr;
//		    
//		    LocalDateTime filename = LocalDateTime.of(Integer.valueOf(f.getName().substring(0, 4)), 
//		    		Integer.valueOf(f.getName().substring(4, 6)), 
//		    		Integer.valueOf(f.getName().substring(6, 8)), 
//		    		Integer.valueOf(f.getName().substring(9, 11)), 
//		    				Integer.valueOf(f.getName().substring(11, 13)), 
//		    						Integer.valueOf(f.getName().substring(13, 15)));
//			try {
//				fr = new FileReader(f);
//			    BufferedReader br = new BufferedReader(fr);
//			    String zeile = br.readLine();
//			    zeile = br.readLine();
//			    long first = Long.valueOf(zeile.substring(0, zeile.indexOf(";")));
//			    LocalDateTime dt = LocalDateTime.ofEpochSecond(first, 0, ZoneOffset.ofHours(1));
//			    System.out.print("\n" + f.getName() + " (" + filename + ") first " + dt + " diff " + (first - filename.toEpochSecond(ZoneOffset.ofHours(1))));
//			    
//			     do {	
//			    	String[] values = zeile.split(";");
//			    	if (values.length < 7 || !values[6].contains("."))
//			    		break;
//			    	List<Double> list = new ArrayList<Double>();
//			    	for (int i=1;i<values.length;i++) {
//			    		if (values[i].isBlank())
//			    			list.add(null);
//			    		else
//			    			list.add(Double.valueOf(values[i]));
//			    	}
//			    	long key = Long.valueOf(values[0]);
//			    	if (map.containsKey(key)) {System.out.print("\n" + key + " exists");
//			    		if (!map.get(key).equals(list)) {System.out.print("... is equal");
//			    			if (!map.containsKey(key-1)) {System.out.print("... free space before...");
//			    				map.put(key-1, map.get(key));
//			    				map.put(key, list);
//			    			} else
//			    				map.put(key+1, list);
//			    		}
//			    	} else
//			    		map.put(key, list);
//			    			
//			    } while((zeile = br.readLine()) != null);
//		
//			    br.close();
//
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		
//		return map;
//	}
	
	static int rawToOneFile (String origin, String destination) {
		File dir = new File(origin);

		TreeMap<String, ArrayList<String>> map = read(dir, "");
		
		File file = new File(destination);
		
		return writeFile(file, map);
	}
				
	static int writeFile(File file, TreeMap<String, ArrayList<String>> map) {
	    int ret = 0;

	    try {
	    	FileWriter fw = new FileWriter(file);
	    	BufferedWriter bw = new BufferedWriter(fw);
		    bw.write("Filename;System;Timestamp;No;Latitude;Longitude;Altitude;Bearing;Speed;Accuracy");
		    bw.newLine();

		    for (String k : map.keySet())
		    	for (int i=0;i<map.get(k).size();i++) {
		    		bw.write(k + ";" + i + ";" + map.get(k).get(i));
		    		bw.newLine();
		    		ret++;
		    	}
		    bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	static TreeMap<String, ArrayList<String>> read(File dir, String subDir) {
		TreeMap<String, ArrayList<String>> ret = new TreeMap<String, ArrayList<String>>();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				addAll(ret, read(f, f.getName()));
			} else {
				if (f.getName().matches("\\d{8}-\\d{6}.csv")) {
					readFile(f, subDir, ret);
				}
			}
		}
		
		return ret;
	}
	static void readFile(File f, String subDir, TreeMap<String, ArrayList<String>> map) {
		try {
			FileReader fr = new FileReader(f);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while((line = br.readLine()) != null) {
		    	if (!line.matches("\\d.*"))
		    		continue;
		    	String[] l = line.split(";");
		    	if (l.length < 7 || !l[6].contains("."))
		    		continue;
		    	String key = f.getName().substring(0,15 ) + ";" + subDir + ";" + l[0];
		    	String value =
		    			toDouble(l[1]) + ";" + toDouble(l[2]) + ";" + toDouble(l[3]) + ";" +
		    			toDouble(l[4]) + ";" + toDouble(l[5]) + ";" + toDouble(l[6]) + ";";
		    	add(map, key, value);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void addAll (TreeMap<String, ArrayList<String>> ret, TreeMap<String, ArrayList<String>> temp) {
		for (Entry<String, ArrayList<String>> e : temp.entrySet()) {
			for (String v : e.getValue()) {
				add(ret, e.getKey(), v);
			}
		}
	}
	static void add (TreeMap<String, ArrayList<String>> map, String key, String value) {
		if (!map.containsKey(key)) {
			ArrayList<String> a = new ArrayList<String>();
			a.add(value);
			map.put(key, a);
			return;
		}
		if (!map.get(key).contains(value)) {
			map.get(key).add(value);
		}
	}
	static String toDouble(String ret) {
		if (!ret.equals("")) {
			double d = Double.valueOf(ret);
			if (d == 0)
				ret = "0";
			else
				ret = d + "";
		}
		return ret;
	}
}
