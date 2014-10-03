package com.generic.app.emmd.dto;

public class VisaOffers {

	public VisaOffers(long accountId,long alertId, long maxUpcId, String acct_fundg_src_cd,String acct_fundg_src_subtyp_cd) {
		// TODO Auto-generated constructor stub
		this.accountId=accountId;
		this.alertId=alertId;
		this.maxUpcId=maxUpcId;
		this.acct_fundg_src_cd=acct_fundg_src_cd;
		this.acct_fundg_src_subtyp_cd=acct_fundg_src_subtyp_cd;
	}





	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getAlertId() {
		return alertId;
	}
	public String getAcct_fundg_src_cd() {
		return acct_fundg_src_cd;
	}
	public long getMaxUpcId() {
		return maxUpcId;
	}
	public void setAlertId(long alertId) {
		this.alertId = alertId;
	}
	public void setMaxUpcId(long maxUpcId) {
		this.maxUpcId = maxUpcId;
	}

	public void setAcct_fundg_src_cd(String acct_fundg_src_cd) {
		this.acct_fundg_src_cd = acct_fundg_src_cd;
	}
	



	private long accountId;
	private long alertId;
	private long maxUpcId;
	private String acct_fundg_src_cd;
	private String acct_fundg_src_subtyp_cd;
	public String getAcct_fundg_src_subtyp_cd() {
		return acct_fundg_src_subtyp_cd;
	}





	public void setAcct_fundg_src_subtyp_cd(String acct_fundg_src_subtyp_cd) {
		this.acct_fundg_src_subtyp_cd = acct_fundg_src_subtyp_cd;
	}
	






	
}
