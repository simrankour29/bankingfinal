package com.cg.banking.beans;

public class Transaction {
private int transactionId;
private float amount;
private String transacriotnType;
public Transaction() {}
public Transaction(float amount,String transactionType)
	{
		super();
		this.amount=amount;
		this.transacriotnType=transactionType;
	}
public Transaction(int transactionId,float amount,String transactionType){
	super();
	this.transactionId=transactionId;
	this.transacriotnType=transactionType;
	this.amount=amount;
}
public int getTransactionId() {
	return transactionId;
}
public void setTransactionId(int transactionId) {
	this.transactionId = transactionId;
}
public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}
public String getTransacriotnType() {
	return transacriotnType;
}
public void setTransactionType(String transacriotnType) {
	this.transacriotnType = transacriotnType;
}
@Override
public String toString() {
	return "Transaction [transactionId=" + transactionId + ", amount=" + amount + ", transacriotnType="
			+ transacriotnType + "]";
}
}