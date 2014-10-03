package com.generic.app.emmd;


//import java.io.Console;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.datastax.driver.core.Session;
import com.generic.app.emmd.dao.MySQLDao;
import com.generic.app.emmd.dao.VisaDao;
import com.generic.app.emmd.dto.VisaOffers;
import com.generic.app.emmd.utils.CassandraConnectionUtil;
import com.generic.app.emmd.utils.Profiler;

public class Driver {
	//private static final Log log = (Log) LogFactory.getLog(Driver.class);
	//private static final Log log = (Log) LogFactory.getLog(Driver.class);
//	private static final org.apache.commons.logging.Log log = LogFactory.getLog(CassandraConnectionUtil.class);
	private static String serverNames = "";
	private static int partitioncount=10;
	private static int clcount=1;
	private static String operation="";
	private static int batchsize=25;
	private static int startpartition=1;

	public static void main(String args[]) throws InterruptedException, IOException{
		//System.out.println("args.length"+args.length);
		if (  args.length < 3)
		{
			System.out.println("Usage to write rows: java -jar Visa-1.0-SNAPSHOT.jar servername  writes #partitions #clustercolsperpartition #batchsize #startpartition");
			System.out.println("or");
			System.out.println("Usage to read rows: java -jar Visa-1.0-SNAPSHOT.jar servername  reads #reads ");
			return;
		}
		else if ( args[1].equals("writes")){
			serverNames=args[0];
			operation=args[1];
			partitioncount=Integer.parseInt(args[2]);
			if ( args.length >3)
				clcount=Integer.parseInt(args[3]);
			else
				System.out.println("Clustering columns size not specfied.. defaulting to 1");
			
			if (args.length > 4) 
				batchsize=Integer.parseInt(args[4]);
			else
				System.out.println("defaulting to original batchsize"+batchsize);
			

			if (args.length > 5) 
				startpartition=Integer.parseInt(args[5]);
			else
				System.out.println("defaulting to original startpartition"+startpartition);
			
			System.out.println("server is: "+ serverNames + " operation: "+ operation + "  Number of partitions:" + partitioncount +" clustering columns:" + clcount
					         +" Batchsize:"+batchsize);
			processOldWay(serverNames,operation,partitioncount,clcount,batchsize,startpartition);
			
			
		}
		else if ( args[1].equals("reads")){
			serverNames=args[0];
			operation=args[1];
			partitioncount=Integer.parseInt(args[2]);
			processOldWay(serverNames,operation,partitioncount,0,0,0);

		}

	}
	
	
	@SuppressWarnings("deprecation")
	static void processOldWay(String serverNames, String operation, int partitioncount, int clcount,int batchsize,int startpartition) throws IOException{
		
//		log.info("start");
		CassandraConnectionUtil casConUtil = CassandraConnectionUtil.getInstance(serverNames);
		Session session = casConUtil.getSession();
		VisaDao visaDao= new VisaDao(session);

		/*	
		CassandraDao cassandraDao=new CassandraDao(session);
		CopyOfCassandraDao CopyOfCassandraDao= new CopyOfCassandraDao(session);
		MySQLDao dao = new MySQLDao();
		List<YcsOffer> list = dao.getYcsOffers();
		Profiler profiler = new Profiler();
		//cassandraDao.copyYcsOfferDataorig();
	//	cassandraDao.copyYcsOfferDataorig(list);
		CopyOfCassandraDao.copyYcsOfferData(list);
		//profiler.report(1000);
		profiler.report("Total execution time for processing "+list.size()+" record ");
		*/

		if ( operation.equals("writes"))
		{
			System.out.println("Write operations");

			MySQLDao dao = new MySQLDao(partitioncount,clcount,startpartition);
			List<VisaOffers> list = dao.getVisaOffers();
			//Profiler profiler = new Profiler();
			long starttime=System.currentTimeMillis();
			//System.out.println("start time"+ System.currentTimeMillis());

			visaDao.visaOfferData(list,batchsize);
			long endtime=System.currentTimeMillis();
			long diff=endtime-starttime;
			String str = new Date().toString()+" "+ " :in "+(diff*1000)+"" + " us"
					 + ":in "+(diff)+"" + " ms"
							+ ":in "+(diff/1000)+"" + " seconds";

			System.out.println(str);;

			//profiler.report("THis is a testTotal execution time for processing "+list.size()+" records ");
			casConUtil.shutdown();

		}
		else if (operation.equals("reads")){
			System.out.println("Read operations");
			//Profiler profiler = new Profiler();
			long starttime=System.currentTimeMillis();
			//System.out.println("start time"+ System.currentTimeMillis());

			int i= VisaDao.retrieveVisaData(partitioncount);
			long endtime=System.currentTimeMillis();
			long diff=endtime-starttime;
			String str = new Date().toString()+" "+ " To process " +i + "records: "+ ":in "+(diff*1000)+"" + " us"
					 + ":in "+(diff)+"" + " ms"
							+ ":in "+(diff/1000)+"" + " seconds";

			System.out.println(str);;

			casConUtil.shutdown();


		}
		else
		{
			System.out.println("Incorrect operation: please only enter reads or writes");
			casConUtil.shutdown();
			return;
		}
		
		
	}
}
