package com.generic.app.emmd.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.QueryTrace;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.generic.app.emmd.dto.VisaOffers;
import com.generic.app.emmd.utils.CassandraConnectionUtil;
//import com.generic.app.emmd.utils.EnvProps;
import com.generic.app.emmd.utils.Profiler;

public class VisaDao {

    //private static final Log log = LogFactory.getLog(VisaDao.class);
    private static Session session =null;
    public VisaDao(Session session){
    	this.session=session;
    }
    
    public static int retrieveVisaData(int count)
	{
    	int i= 0;
    	ResultSet rs=null;
		PreparedStatement ps = session.prepare("select org_id, alrt_id, alrt_bid_id,acct_fundg_src_cd,acct_fundg_src_subtyp_cd from emmd.visatable where org_id=?");
		for ( long j=1; j <=count; j++)
		{
			 rs=session.execute(ps.bind(j));
			for (Row row : rs) {
			i++;
			//System.out.println("rs.org_id"+row.getLong("org_id") + 	row.getLong("alrt_id")+ row.getString("acct_fundg_src_cd") + row.getString("acct_fundg_src_subtyp_cd"));	
		}
		}
		//System.out.println("i is "+ i);
		return i;
		
	}
	public void visaOfferData(List<VisaOffers> list,int batchsize){

		
		PreparedStatement ps = session.prepare(
				      "INSERT INTO emmd.visatable " +
				      "(org_id, alrt_id, alrt_bid_id,acct_fundg_src_cd,acct_fundg_src_subtyp_cd) " +
					     "VALUES (?, ?, ?,?,?);");
		ps.setConsistencyLevel(ConsistencyLevel.ONE);
		
		BatchStatement batch = new BatchStatement();
		
        StringBuilder sb = new StringBuilder("BEGIN BATCH \n");

      //  Profiler profiler = new Profiler();
        long count=list.size();
        int i =0;
        for (VisaOffers visa : list){
        	i++;
        	batch.add(ps.bind(visa.getAccountId(),visa.getAlertId(),visa.getMaxUpcId(),visa.getAcct_fundg_src_cd(),visa.getAcct_fundg_src_subtyp_cd()));
        	if(i%100000 ==0 )
    		{
    			System.out.println("Number of rows inserted so far..."+ i);
    		}
    		
        	if(i%batchsize ==0 ){
        		//System.out.println("Iteration"+ i);
        		//session.executeAsync(batch);
        		 ResultSet results = session.execute(batch);
        	      ExecutionInfo executionInfo = results.getExecutionInfo();
    			 // QueryTrace queryTrace = executionInfo.getQueryTrace();
    		 // System.out.println("Trace id: %s\n\n"+ "hosts"+  executionInfo.getQueriedHost()+"consistency"+ executionInfo.getAchievedConsistencyLevel());
        		batch.clear();
        	}
		}

		if (i%batchsize >0 ){

			session.execute(batch);
			//session.executeAsync(batch);
			batch.clear();
			sb=null;
		}
		
	}

	
}
