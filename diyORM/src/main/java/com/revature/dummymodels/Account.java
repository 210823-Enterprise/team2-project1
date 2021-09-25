package com.revature.dummymodels;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Getter;
import com.revature.annotations.Id;
import com.revature.annotations.Setter;

@Entity(tableName = "account")
public class Account {


	@Id(columnName = "id")
	public int id;
	
	@Column(columnName = "accountname")
	public String accountName;
	@Column(columnName = "ownerId")
	public int ownerId;
	@Column(columnName = "balance")
	public double balance;

	public Account() {
		super();
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
	
	@Getter(name = "GetAccountId")
	public int getId() {
		return id;
	}
	@Setter(name = "SetAccountId")
	public void setId(int id) {
		this.id = id;
	}
	@Getter(name = "GetAccountBalance")
	public double getBalance() {
		return balance;
	}
	@Setter(name = "SetAccountBalance")
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Getter(name = "GetAccountName")
	public String getAccountName() {
		return accountName;
	}
	@Setter(name = "SetAccountName")
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
		return "Account [id=" + id + ", accountName=" + accountName + ", ownerId=" + ownerId + ", balance=" + balance
				+ "]";
	}
	
}
