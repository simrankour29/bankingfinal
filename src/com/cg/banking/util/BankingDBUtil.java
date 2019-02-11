package com.cg.banking.util;

import java.util.HashMap;
import java.util.Random;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;

public class BankingDBUtil {
	public static HashMap<Long,Account> accountDetails = new HashMap<>();
//	public static HashMap<Integer,Transaction> transactions=new HashMap<>();
	static private int n=9999-1000;
	static Random pin=new Random();
	static Random transactionID=new Random();
	public static long accountNumber = 100;
	public static int pinNumber=200;
	public static int transactionId=transactionID.nextInt(9999)%n;
	public static String accountStatus = "Active";
	public static String depositeTransaction="Amount Deposited";
	public static String withdrawTransaction="Amount Withdrawn";
	public static String fundTransferTransaction="Money Transfered";
	public static long getACCOUNT_NUMBER() {
		return ++accountNumber;
	}
	public static int getPIN_NUMBER() {
		return ++pinNumber;
	}
	public static Integer getTRANSACTION_ID() {
		return ++transactionId;
	}
	public static String getACCOUNT_STATUS() {
		return accountStatus;
	}
	public static String getDEPOSITE_STATUS() {
		return depositeTransaction;
	}
	public static String getWITHDRAW_STATUS() {
		return withdrawTransaction;
	}
	public static String getTRANSFER_STATUS() {
		return fundTransferTransaction;
	}
}