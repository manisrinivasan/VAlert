package com.generic.app.emmd.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.generic.app.emmd.dto.VisaOffers;

public class MySQLDao {
private int count;
private int clcount;
	//public int count = 10;
String s="LVeyZAeBtj52sekQnU1iR5D7Ocl7c4xKQEUiRR393lvV3GTrw4Q2a5HT6c2RzRHlu6IARQSvUs8sf7HGBpfEi7o2fZMaxCxNlmL7sUP3IT9dh63sKbRuYhg53kUCrheYWxZGZOIapzmPCpREOHWVtyfW92WwszVowdEdhJcgWJDIzDuWsQsn1aDrU17yBIxg64AL4ER5VRWq30nv0GUK5eDTW5N4k3xYFLpO8SjUNxeUvK689JcubpWew7aLQwPxBe6O99tWQzOuFVOnX7pJqOqj7c59d4Y5zyfmEvx3knevigFwEKrSLEKKxEsApcg8QfRPo1ue3Hh9G69fK1wMOqhvSh6vyrN0F8rWiNXo1jIoWqpraLvr3B8NbEwUMVm8axjGmqX3hDbcXx2ADBt0nhdlzzlBnFnrZWDbH9WBm78DQJRJKRfdjQoBCQxcMXxVMBx08PmknNWDZm8RwVjkh4JJGcj6COkKAJsH5YWJHCPVVHwFizrYSTX07PDbCDvVYYys4hnl8CeJJBcBlUBCOFNILpJRTNo1eGkyjNTUuaJK2zJ6PHVbWRsQ8bm0B9qYepP5uaEsQSfK3SylPoVxYHHzS6b5MxoJ8bw2ZRxYqRm8S3ZwZECiPMU0brfJfhKThf98YP679W0pHLIrA8kbqp2DhWBlsxgozLBVDF9srKH2fLJke1EG9kPpO6c8P1J5y5rBJvmvQIGth4BqS6pO3JFx2zvCr8CZrDih1O2IuVEU3R8f5RD7GUgOYDsAskEAnFM0uIOLoC4qYo9Fnk26ta3bjVzyyn59WXykM396DPz4nSN2V1jJKDMVVKKjHjqK7j7gmiS4zL1wR1jMFcdiXaWtUg3Qiy6jPK4zKkEnXaGxy7UwyA6PqmxMpVaWfEt2NHao";
private int startpartition;

	public long getYcsOffersCount() {
		int count=0;
		return count;
	}

	
	
	public List<VisaOffers> getVisaOffers() throws IOException{
		List<VisaOffers> offers= new ArrayList<VisaOffers>();
	System.out.println("Count is "+ count);

		for(int i=1; i <=count;i++ ){
			for (int j = 1; j <=clcount; j++)
			{
				offers.add(new VisaOffers((i+startpartition),j,i,s,"testing subtype"));
				//System.out.println(" i and j are " + (i+startpartition) + ":"+ j);

			}
		}

		return offers;
	}
	public static void main(String args[]) throws IOException{
		//MySQLDao dao = new MySQLDao();
		//dao.getYcsOffers();
	}

	public MySQLDao(int count,int clcount,int startpartition) {
		super();
		this.count = count;
		this.clcount=clcount;
		this.startpartition=startpartition;
	}

	
}
