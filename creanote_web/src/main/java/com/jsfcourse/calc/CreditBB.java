package com.jsfcourse.calc;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@RequestScoped
//@SessionScoped
public class CreditBB {
	private Double amount;
	private Double time;
	private Double rate;
	private Double result;
	
	@Inject
	FacesContext ctx;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	
	public String calc() {
		//konwersja
		double amount = this.amount;
		double time = this.time;
		double rate = this.rate;
		// obliczenia
		double n = time * 12;
		double r = rate/100;
		this.result = amount * ((r / 12 * Math.pow((1+r/12),n))) / ((Math.pow((1 + r/12),n)-1));
//		String resultFinal = String.valueOf(this.result);
		return "showresult";
	}
}
