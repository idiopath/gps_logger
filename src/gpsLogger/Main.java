package gpsLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("F:\\gps_logs");
		TreeMap<Long, List<Double>> map = new TreeMap<Long, List<Double>>();
		Map<Long, List<Double>> map2 = new TreeMap<Long, List<Double>>();
		
		for (File f:file.listFiles()) {
		    FileReader fr;
			try {
				fr = new FileReader(f);
			    BufferedReader br = new BufferedReader(fr);
		
			    String zeile = "";
		
			    while( (zeile = br.readLine()) != null )
			    {	
			    	if (zeile.startsWith("T"))
			    		continue;
			    	String[] temp = zeile.split(";");
			    	if (temp.length < 3)
			    		break;
			    	List<Double> list = new ArrayList<Double>();
			    	for (int i=1;i<temp.length;i++) {
			    		if ((i < 3 && temp[i].length() < 9) || !temp[i].contains("."))
			    			break;
			    		list.add(Double.valueOf(temp[i]));
			    	}
			    	long key = Long.valueOf(temp[0]);
			    	if (map.containsKey(key)) {
			    		if (!map.get(key).equals(list)) {
			    			if (map.get(key).size() == list.size()) {
			    				if (map2.containsKey(key)) {
						    		if (!map2.get(key).equals(list)) {
						    			System.out.println(key + "\told\t" + map.get(key) + "\tnew\t" + list + " " + f.getName());
						    		}
			    				} else map2.put(Long.valueOf(temp[0]), list);
			    			} else if (map.get(key).size() < list.size())
			    					map.put(Long.valueOf(temp[0]), list);
			    		}
			    	} else map.put(Long.valueOf(temp[0]), list);
			    }
		
			    br.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Set<Long> keys = new HashSet<Long>();
		keys.addAll(map2.keySet()); 
		for (long k:keys) {
			if (!map.containsKey(k + 1)) {
				map.put(k + 1, map2.get(k));
			} else if (!map.containsKey(k - 1)) {
				map.put(k - 1, map.get(k));
				map.put(k, map2.get(k));
			} else {
				List<Double> l = new ArrayList<Double>();
				for (int i=0;i<map.get(k).size();i++) {
					l.add((map.get(k).get(i) + map.get(k).get(i)) / 2);
				}
				map.put(k, l);
			}
			map2.remove(k);
		}
		
		
		System.out.println(map.size() + " Einträge gelesen"); 
		for (Long l:map2.keySet()) {
			System.out.println((l - map.lowerKey(l)) + " " + l + " " + (map.higherKey(l) - l));
		}
		System.out.println(map2.size() + " Einträge in map2 gelesen"); 

		try {
		    FileWriter fw = new FileWriter(File.createTempFile("java", ".csv", file));

		    System.out.println(fw.getEncoding());
		    BufferedWriter bw = new BufferedWriter(fw);
	
		    for (Entry<Long, List<Double>> e:map.entrySet()) {
		    	bw.write(e.getKey().toString());
		    	for (Double d:e.getValue()) {
			    	bw.write(";" + d.toString());
		    	}
			    bw.newLine();
		    }
	
		    bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
		    FileWriter fw = new FileWriter(File.createTempFile("excel", ".csv", file));

		    System.out.println(fw.getEncoding());
		    BufferedWriter bw = new BufferedWriter(fw);
	
		    for (Entry<Long, List<Double>> e:map.entrySet()) {
		    	bw.write((e.getKey()).toString());
		    	for (Double d:e.getValue()) {
			    	bw.write(";" + d.toString());
		    	}
			    bw.newLine();
		    }
	
		    bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Ende gut, alles gut"); 
	}

}
