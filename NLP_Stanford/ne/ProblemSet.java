package com.sap.nic.nlp.ne;

public class ProblemSet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double e1 = Math.exp(0.3);
		double e2 = Math.exp(-0.6);
		double e3 = Math.exp(0);
		
		double sumE = e1 + e2 + e3;
		System.out.println(e1 / sumE);
		System.out.println(e2 / sumE);
		System.out.println(e3 / sumE);
		System.out.println( 1.0 / ( 1 + Math.exp(1.0)));
	}

}
