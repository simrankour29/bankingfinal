package com.cg.banking.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.daoservices.AccountDAOImpl;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;
import com.cg.banking.util.BankingDBUtil;

public class BankingServicesImpl implements  BankingServices{
	
	AccountDAOImpl service = new AccountDAOImpl();
	Account account;
	
	@Override
	public Account openAccount(String accountType, float initBalance)
			throws InvalidAmountException, InvalidAccountTypeException, BankingServicesDownException {
		if(!(accountType.equalsIgnoreCase("Savings") | accountType.equalsIgnoreCase("Current")))
			throw new InvalidAccountTypeException("Invalid Account Type");
		if(initBalance<0 | initBalance<500 )
			throw new InvalidAmountException("Invalid Amount!!! Enter Amount > 500");
			Account account = new Account(accountType,initBalance);
		account.transactions=new HashMap<>();
		account = service.save(account);
		return account; 
	}

	@Override
	public List<Account> getAllAccountDetails() throws BankingServicesDownException {
		return service.findAll();
	}
	
	
	@Override
	public Account getAccountDetails(long accountNo) throws AccountNotFoundException, BankingServicesDownException {
		 account=service.findOne(accountNo);
		 if(account==null)
			 throw new AccountNotFoundException("Invalid Account Number");
		return account;
	}
	
	
	
	@Override
	public float depositAmount(long accountNo, float amount)
		throws AccountNotFoundException, BankingServicesDownException, AccountBlockedException {
		Account account=service.findOne(accountNo);
		if(account== null)
			throw new AccountNotFoundException("Enter correct account number!!! ");
		float finalAmount=account.getAccountBalance()+amount;
		account.setAccountBalance(finalAmount);
		
		Transaction transaction=new Transaction();
		Integer transactionID=BankingDBUtil.getTRANSACTION_ID();
		int tId=transactionID;
		transaction.setTransactionId(tId);
		transaction.setTransactionType(BankingDBUtil.getDEPOSITE_STATUS());
		transaction.setAmount(amount);
		account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transaction);
//		System.out.println(BankingDBUtil.getDEPOSITE_STATUS()
		return (int)finalAmount;
	}

	@Override
	public float withdrawAmount(long accountNo, float amount, int pinNumber) throws InsufficientAmountException,
			AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException {
		account = service.findOne(accountNo);
		if(account==null)
			throw new AccountNotFoundException("Invalid Account Number!!! ");
		  do
		    {
		    	
		    }while(pinNumber!=account.getPinNumber());
		if(pinNumber==account.getPinNumber()) {
			if(amount<account.getAccountBalance()) {
			float newAmount=account.getAccountBalance()-amount;
			account.setAccountBalance(newAmount);
			
			Transaction transactionWith=new Transaction();
			Integer transactionID=BankingDBUtil.getTRANSACTION_ID();
			int tId=transactionID;
			transactionWith.setTransactionId(tId);
			transactionWith.setTransactionType(BankingDBUtil.getWITHDRAW_STATUS());
			transactionWith.setAmount(amount);
			account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transactionWith);
			return newAmount;
			}
			else
				throw new InsufficientAmountException("Insufficient Amount");
		}
		else
			throw new InvalidPinNumberException("Invalid PIN Number!!!");
	}
	@Override
	public boolean fundTransfer(long accountNoFrom, long accountNoTo, float transferAmount, int pinNumber)
			throws InsufficientAmountException, AccountNotFoundException, InvalidPinNumberException,
			BankingServicesDownException, AccountBlockedException {
		
		Transaction transactionFT=new Transaction();
		Integer transactionID=BankingDBUtil.getTRANSACTION_ID();
		transactionFT.setTransactionId(transactionID);
		transactionFT.setAmount(transferAmount);
		
		account = service.findOne(accountNoFrom);
		if(account==null)
			throw new AccountNotFoundException("Ivalid Account Number!!! ");
		if(pinNumber==account.getPinNumber()) {
			if(transferAmount<account.getAccountBalance()) {
			float deductedAmount=account.getAccountBalance()-transferAmount;
			account.setAccountBalance(deductedAmount);
			transactionFT.setTransactionType(BankingDBUtil.getTRANSFER_STATUS());			
			account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transactionFT);
			}
			else
				throw new InsufficientAmountException("Insufficient Amount!!!");
		}
		else
			throw new InvalidPinNumberException("Invalid Pin Number!!!");
		Transaction depStatus=new Transaction();
			account =service.findOne(accountNoTo);
			float addedAmount=account.getAccountBalance()+transferAmount;
				account.setAccountBalance(addedAmount);
				transactionFT.setTransactionType(BankingDBUtil.getTRANSFER_STATUS());
				account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transactionFT);
			return true;
	}
	@Override
	public List<Transaction> getAccountAllTransaction(long accountNo)
			throws BankingServicesDownException, AccountNotFoundException {
		account=service.findOne(accountNo);
		if(account==null)
			throw new AccountNotFoundException("Invalid Account Number");
		Account account=service.findOne(accountNo);
		ArrayList arrayList=new ArrayList<>(account.transactions.values());
		if(arrayList.isEmpty())
			throw new BankingServicesDownException("No Transactions to be Displayed!!!");
		else
			return arrayList;
		}

	@Override
	public String accountStatus(long accountNo)
			throws BankingServicesDownException, AccountNotFoundException, AccountBlockedException {
		return null;
	}

}

