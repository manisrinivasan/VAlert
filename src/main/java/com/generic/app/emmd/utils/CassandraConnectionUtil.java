package com.generic.app.emmd.utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

public class CassandraConnectionUtil {

//		private static final Log log = LogFactory.getLog(CassandraConnectionUtil.class);

	private static CassandraConnectionUtil instance = null;

	private Cluster cluster;
	
	private Session session;
	
	private CassandraConnectionUtil(String serverNames) {
	//	log.debug("Goint to setup cluster");
		setCluster(serverNames);
//		log.debug("cluster setup completed");
	}
	
	public static CassandraConnectionUtil getInstance(String serverNames) {
		if (instance == null) {
			synchronized (CassandraConnectionUtil.class) {
				if (instance == null) {
	//				log.debug("Creating instance of CassandraConnectionUtil class");
					instance = new CassandraConnectionUtil(serverNames);
				}
			}
		}
		return instance;
	}
	
	private void setCluster(String serverNames) {
		EnvProps props = EnvProps.getInstance();
		//"localhost";//props.getProperty("cassandra.db.servers").split(",");
		//serverNames[0]="127.0.0.2";
		//serverNames[0]="localhost";
		int serverPort = 9042;//Integer.parseInt(props.getProperty("cassandra.db.port"));
		try {
			if (cluster == null) {
			//	SocketOptions so=new SocketOptions();
			//	so.setReadTimeoutMillis(120000);
		//		log.debug("Creating cluster object......");
				cluster = new Cluster.Builder()
				//.withSocketOptions(so)
//				.withRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE)
				.addContactPoints(serverNames)
				//.withLoadBalancingPolicy(new TokenAwarePolicy(new DCAwareRoundRobinPolicy("DC2")))
				.withPort(serverPort)
				.build();
				Metadata metadata = cluster.getMetadata();
		  /*      log.debug("Connected to cluster: "+metadata.getClusterName()+"\n");
		        for (Host host : metadata.getAllHosts()) {
		            log.debug("Datacenter: "+host.getDatacenter()+"; Host: "+host.getAddress()+"; Rack: "+host.getRack()+"\n");
		        }*/
			}
		} catch (Exception e) {
			//log.error(e, e);
		}
	}
	
	public Session getSession(){
		session = cluster.connect("emmd");
		return session;
	}
	
	public void shutdown(){
		if(null != session){
			session.close();
	        session.getCluster().close();
		}
	}
}
