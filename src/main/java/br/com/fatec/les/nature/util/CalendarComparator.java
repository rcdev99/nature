package br.com.fatec.les.nature.util;

import java.util.Calendar;
import java.util.Comparator;

public class CalendarComparator implements Comparator<Object> { 
	    
	    public int compare(Object o1, Object o2) { 
	        
	        Calendar c1 = ( Calendar )o1; 
	        Calendar c2 = ( Calendar )o2; 
	        
	        int flag = 0; 
	        
	        if( c1.getTimeInMillis() < c2.getTimeInMillis() ) { 
	            flag = -1; 
	        } else { 
	            if( c1.getTimeInMillis() > c2.getTimeInMillis() ) { 
	                flag = 1; 
	            } 
	        } 
	        
	        return flag; 
	        
	    } 
	    
} 
	
	

