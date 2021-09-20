package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Account {

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String accountName;
	@Column
	private int ownerId;
	@Column
	private double balance;

	public Account() {
		super();
	}
	
	public Account(int id, String accountName, int ownerId, double balance) {
		super();
		this.id = id;
		this.balance = balance;
		this.ownerId = ownerId;
		this.accountName = accountName;
	}

	public int getPrimaryOwnerId() {
		return ownerId;
	}

	public void setPrimaryOwnerId(int primaryOwnerId) {
		this.ownerId = primaryOwnerId;
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
		return "Account [id=" + id + ", accountName=" + accountName + ", ownerId=" + ownerId + ", balance=" + balance
				+ "]";
	}
	
}
