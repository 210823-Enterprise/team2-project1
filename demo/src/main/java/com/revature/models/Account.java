package com.revature.models;

import java.util.Objects;

public class Account {

	private int id; // primary key
	private double balance;
	private int primaryOwnerId;
	private int secondaryOwnerId;
	private boolean isActive;

	public Account() {
		super();
	}
	
	public Account(int id, double balance, int primaryOwnerId, int secondaryOwnerId, boolean isActive) {
		super();
		this.id = id;
		this.balance = balance;
		this.primaryOwnerId = primaryOwnerId;
		this.secondaryOwnerId = secondaryOwnerId;
		this.isActive = isActive;
	}

	public int getPrimaryOwnerId() {
		return primaryOwnerId;
	}

	public void setPrimaryOwnerId(int primaryOwnerId) {
		this.primaryOwnerId = primaryOwnerId;
	}

	public int getSecondaryOwnerId() {
		return secondaryOwnerId;
	}

	public void setSecondaryOwnerId(int secondaryOwnerId) {
		this.secondaryOwnerId = secondaryOwnerId;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + primaryOwnerId;
		result = prime * result + secondaryOwnerId;
		return result;
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
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (primaryOwnerId != other.primaryOwnerId)
			return false;
		if (secondaryOwnerId != other.secondaryOwnerId)
			return false;
		return true;
	}
	

}
