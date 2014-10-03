package com.generic.app.emmd.utils;

import java.io.Console;
import java.util.Date;


public class Profiler{
	 //private static final Log log = LogFactory.getLog(Profiler.class);
	long start=0;
	long end =0;
	public Profiler(){
		start = System.currentTimeMillis();
		System.out.println("start time "+ start);
	}
	public void init(){
		start = System.currentTimeMillis();
	}
	public void report(String preamble){
		end = System.currentTimeMillis();;
		long diff = end - start;
		diff /=1000;
		long seconds = diff/1000;
		long microseconds = diff%1000;
		String str = new Date().toString()+" "+preamble+ " :in "+(diff*1000)+"" + " us"
		 + ":in "+(diff)+"" + " ms"
				+ ":in "+(diff/1000)+"" + " seconds";

		System.out.println(str);
	}
}
