package finservices;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
	
	private long accountNumber;
	private double amount;
	private Date txnDate;
	private String currency;
	private boolean approved;
	private int fraudScore;

	 public Transaction(long accountNumber 
			 , double amount
			 , Date txnDate
			 , String currency
			 , boolean approved
			 , int fraudScore) {
	        this.setAccountNumber(accountNumber);
	        this.setAmount(amount);
	        this.setTxnDate(txnDate);
	        this.setCurrency(currency);
	        this.setApproved(approved);
	        this.setFraudScore(fraudScore);
	    }

	/**
	 * @return the fraudScore
	 */
	public int getFraudScore() {
		return fraudScore;
	}

	/**
	 * @param fraudScore the fraudScore to set
	 */
	public void setFraudScore(int fraudScore) {
		this.fraudScore = fraudScore;
	}

	/**
	 * @return the accountNumber
	 */
	public long getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the txnDate
	 */
	public Date getTxnDate() {
		return txnDate;
	}

	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

}



