package gpsLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
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
//		TreeMap<Long, List<Double>> map = FileHandling.importFiles("C:\\Users\\idiop\\OneDrive\\Anwendungen\\gps log\\Wiko Sunny");

		System.out.println(FileHandling.rawToOneFile("D:\\Henrik\\gps_data", "D:\\Henrik\\gps_data\\all-" + System.currentTimeMillis() + ".csv"));
		
//		Set<Long> keys = new HashSet<Long>();
//		keys.addAll(map2.keySet()); 
//		for (long k:keys) {
//			if (!map.containsKey(k + 1)) {
//				map.put(k + 1, map2.get(k));
//			} else if (!map.containsKey(k - 1)) {
//				map.put(k - 1, map.get(k));
//				map.put(k, map2.get(k));
//			} else {
//				List<Double> l = new ArrayList<Double>();
//				for (int i=0;i<map.get(k).size();i++) {
//					l.add((map.get(k).get(i) + map.get(k).get(i)) / 2);
//				}
//				map.put(k, l);
//			}
//			map2.remove(k);
//		}
//		
//		
//		System.out.println(map.size() + " Einträge gelesen"); 
//		for (Long l:map2.keySet()) {
//			System.out.println((l - map.lowerKey(l)) + " " + l + " " + (map.higherKey(l) - l));
//		}
//		System.out.println(map2.size() + " Einträge in map2 gelesen"); 
//
//		try {
//		    FileWriter fw = new FileWriter(File.createTempFile("java", ".csv", file));
//
//		    System.out.println(fw.getEncoding());
//		    BufferedWriter bw = new BufferedWriter(fw);
//	
//		    for (Entry<Long, List<Double>> e:map.entrySet()) {
//		    	bw.write(e.getKey().toString());
//		    	for (Double d:e.getValue()) {
//			    	bw.write(";" + d.toString());
//		    	}
//			    bw.newLine();
//		    }
//	
//		    bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//		    FileWriter fw = new FileWriter(File.createTempFile("excel", ".csv", file));
//
//		    System.out.println(fw.getEncoding());
//		    BufferedWriter bw = new BufferedWriter(fw);
//	
//		    for (Entry<Long, List<Double>> e:map.entrySet()) {
//		    	bw.write((e.getKey()).toString());
//		    	for (Double d:e.getValue()) {
//			    	bw.write(";" + d.toString());
//		    	}
//			    bw.newLine();
//		    }
//	
//		    bw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Ende gut, alles gut"); 
	}

}
