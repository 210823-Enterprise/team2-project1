package com.revature.models;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName = "account123")
public class Account {


	@Id(columnName = "id")
	public int id;
	
	@Column(columnName = "accountName")
	public String accountName;
	@Column(columnName = "ownerId")
	public int ownerId;
	@Column(columnName = "balance")
	public double balance;

	public Account() {
		super();
	}
	public Account(int id) {
		super();
		this.id = id;
	}
	public Account(String accountName, int ownerId, double balance) {
		super();
		this.balance = balance;
		this.ownerId = ownerId;
		this.accountName = accountName;
	}
	public Account(int id, String accountName, int ownerId, double balance) {
		super();
		this.id = id;
		this.balance = balance;
		this.ownerId = ownerId;
		this.accountName = accountName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountName, balance, id, ownerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountName, other.accountName)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id
				&& ownerId == other.ownerId;
	}

	@Override
	public String toString() {
		
		return String.format( "Id = %-8d Account Name = %-22s Owner Id = %-8d Balance = $%-8.2f", id, accountName,ownerId,balance);
	}
	
}
