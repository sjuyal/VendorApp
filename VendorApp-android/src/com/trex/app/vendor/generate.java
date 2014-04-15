package com.trex.app.vendor;

import java.util.ArrayList;
import java.util.List;

public class generate{

public static ArrayList<SensorData> test()
{
	ArrayList<SensorData> list = new ArrayList<SensorData>();
    	double lat = 10.12512346, lon = 12.9874652;
    	int id = 1;
    	for(int row = 0; row < IConstants.ROWS; row++) {
    		lon = 12.9874652;
    		for(int col = 0; col < IConstants.COLS[row]; col++) {
    			list.add(new SensorData("Sensor"+(id++), lat+"_"+lon));
    			lon++;
    		}
    		lat++;
    	}
    	return list;
    }
    }

class SensorData {
private String sensorid;
List<String> values = new ArrayList<String>();
public SensorData(String n, String s) {
    setSensorid(n);
    values.add(s);
}
public String getSensorid() {
    return sensorid;
}
public void setSensorid(String sensorid) {
    this.sensorid = sensorid;
}


}

interface IConstants {
	public static final int ROWS = 30;
	public static final int []COLS = /*new int[] {1,2}; */new int[] {61,61,61,61,61,61,61,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,61,61,61,61,61,61,61};
}
