package com.quantosauros.manager.model.pricer;

import java.text.DecimalFormat;

import com.quantosauros.common.TypeDef;
import com.quantosauros.common.TypeDef.ConditionType;
import com.quantosauros.common.TypeDef.UnderlyingType;

public class LegPeriodPricerModel {

	//LegCouponInfo
	private String[] couponType;
	
	private double[] cap;
	private double[] floor;
	private double[] spreads;	
	private double[] inCouponRates;
	private double[] outCouponRates;	
	protected double[][] leverages;
	
	//[condition][period]
	protected double[][] upperLimits;
	protected double[][] lowerLimits;
	
	//LegScheduleInfo
	private String[] startDt;
	private String[] endDt;
	private String[] paymentDt;
	
	//result
	private String[] coupon;
	private String[] condition;
	
	public void setCouponType(String[] couponType){
		this.couponType = couponType;
	}
	public String[] getCouponType(){
		return couponType;
	}
	
	public void setStartDt(String[] startDt){
		this.startDt = startDt;
	}
	public String[] getStartDt(){
		return startDt;
	}
	public void setEndDt(String[] endDt){
		this.endDt = endDt;
	}
	public String[] getEndDt(){
		return endDt;
	}
	public void setPaymentDt(String[] paymentDt){
		this.paymentDt = paymentDt;
	}
	public String[] getPaymentDt(){
		return paymentDt;
	}
	
	public void setCap(double[] cap){
		this.cap = cap;
	}
	public double[] getCap(){
		return cap;
	}
	public void setFloor(double[] floor){
		this.floor = floor;
	}
	public double[] getFloor(){
		return floor;
	}
	public void setSpreads(double[] spreads){
		this.spreads = spreads;
	}
	public double[] getSpreads(){
		return spreads;
	}
	public void setInCouponRates(double[] inCouponRates){
		this.inCouponRates = inCouponRates;
	}
	public double[] getInCouponRates(){
		return inCouponRates;
	}
	public void setOutCouponRates(double[] outCouponRates){
		this.outCouponRates = outCouponRates;
	}
	public double[] getOutCouponRates(){
		return outCouponRates;
	}
	public void setLeverages(double[][] leverages){
		this.leverages = leverages;
	}
	public double[][] getLeverages(){
		return leverages;
	}	
	public void setUpperLimits(double[][] upperLimits){
		this.upperLimits = upperLimits; 
	}
	public double[][] getUpperLimits(){
		return upperLimits;
	}
	public void setLowerLimits(double[][] lowerLimits){
		this.lowerLimits = lowerLimits;
	}
	public double[][] getLowerLimits(){
		return lowerLimits;
	}
	
	public String[] getCoupon(){		
		return coupon;
	}
	public String[] getCondition(){
		return condition;
	}
	
	public void genCoupon(UnderlyingType underlyingType){
		int periodNum = couponType.length;
		coupon = new String[periodNum];
		
		String[] underlyingStr = new String[TypeDef.getNumOfUnderNum(underlyingType)];
		if (underlyingType.equals(UnderlyingType.NONE)){
			
		} else if (underlyingType.equals(UnderlyingType.R1)){
			underlyingStr[0] = " x R1 + ";
		} else if (underlyingType.equals(UnderlyingType.R1mR2)){
			underlyingStr[0] = " x R1 - ";
			underlyingStr[1] = " x R2) + ";
		} else if (underlyingType.equals(UnderlyingType.R1nR2)){
			underlyingStr[0] = " x R1 & ";
			underlyingStr[1] = " x R2 + ";
		} else if (underlyingType.equals(UnderlyingType.R1nR2mR3)){
			underlyingStr[0] = " x R1 & (";
			underlyingStr[1] = " x R2 + ";
			underlyingStr[2] = " x R3) + ";
		} 
		
		for (int periodIndex = 0; periodIndex < periodNum; periodIndex++){
			String tmpCouponType = couponType[periodIndex];
			DecimalFormat df = new DecimalFormat("##0.00");
			String tmpSpread = df.format(spreads[periodIndex] * 100);
			
			if (tmpCouponType.equals("RESET")){
				if (underlyingType.equals(UnderlyingType.R1)){
					coupon[periodIndex] = 
							Double.toString(leverages[0][periodIndex]) + underlyingStr[0] + 
							tmpSpread + "%";							
				} else {
					//ERROR
				}				
			} else if (tmpCouponType.equals("ACCRUAL")){
				
				coupon[periodIndex] = 
						"In: " + df.format(inCouponRates[periodIndex] * 100) + "%" +
						", Out: " + df.format(outCouponRates[periodIndex] * 100) + "%";						
			} else if (tmpCouponType.equals("AVERAGE")){
				if (underlyingType.equals(UnderlyingType.R1)){
					coupon[periodIndex] = 
							Double.toString(leverages[0][periodIndex]) + underlyingStr[0] + 
							Double.toString(leverages[1][periodIndex]) + underlyingStr[1] +
							tmpSpread + "%";							
				} else if (underlyingType.equals(UnderlyingType.R1mR2)){
					coupon[periodIndex] = "(" + 
							Double.toString(leverages[0][periodIndex]) + underlyingStr[0] + 
							Double.toString(leverages[1][periodIndex]) + underlyingStr[1] +
							tmpSpread + "%";
				} else {				
					//ERROR
				}
			} else if (tmpCouponType.equals("FIXED")){
				coupon[periodIndex] = tmpSpread + "%";
			}
			
		}
	}
	public void genCondition(ConditionType conditionType){
		int periodNum = couponType.length;
		condition = new String[periodNum];
		DecimalFormat df = new DecimalFormat("##0.00");
		
		for (int periodIndex = 0; periodIndex < periodNum; periodIndex++){
			if (conditionType.equals(ConditionType.NONE)){
				condition[periodIndex] = "NO Condition";
			} else if (conditionType.equals(ConditionType.R1)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
								
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R1 < " 
						+ tmpUpperBound1 + "%";
			} else if (conditionType.equals(ConditionType.R2)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
								
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R2 < " 
						+ tmpUpperBound1 + "%";
			} else if (conditionType.equals(ConditionType.R3)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
								
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R3 < " 
						+ tmpUpperBound1 + "%";
			} else if (conditionType.equals(ConditionType.R1mR2)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
								
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R1 - R2 < " 
						+ tmpUpperBound1 + "%";
				
			} else if (conditionType.equals(ConditionType.R2mR3)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
								
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R2 - R3 < " 
						+ tmpUpperBound1 + "%";
			} else if (conditionType.equals(ConditionType.R1nR2)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
				String tmpLowerBound2 = df.format(lowerLimits[1][periodIndex] * 100);
				String tmpUpperBound2 = df.format(upperLimits[1][periodIndex] * 100);
				
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R1 < " 
						+ tmpUpperBound1 + "%"
						+ tmpLowerBound2 + "% < R2 < " 
						+ tmpUpperBound2 + "%";
			} else if (conditionType.equals(ConditionType.R1nR3)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
				String tmpLowerBound2 = df.format(lowerLimits[1][periodIndex] * 100);
				String tmpUpperBound2 = df.format(upperLimits[1][periodIndex] * 100);
				
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R1 < " 
						+ tmpUpperBound1 + "%"
						+ tmpLowerBound2 + "% < R3 < " 
						+ tmpUpperBound2 + "%";				
			} else if (conditionType.equals(ConditionType.R2nR3)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
				String tmpLowerBound2 = df.format(lowerLimits[1][periodIndex] * 100);
				String tmpUpperBound2 = df.format(upperLimits[1][periodIndex] * 100);
				
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R2 < " 
						+ tmpUpperBound1 + "%"
						+ tmpLowerBound2 + "% < R3 < " 
						+ tmpUpperBound2 + "%";
			} else if (conditionType.equals(ConditionType.R1nR2mR3)){
				String tmpLowerBound1 = df.format(lowerLimits[0][periodIndex] * 100);
				String tmpUpperBound1 = df.format(upperLimits[0][periodIndex] * 100);
				String tmpLowerBound2 = df.format(lowerLimits[1][periodIndex] * 100);
				String tmpUpperBound2 = df.format(upperLimits[1][periodIndex] * 100);
				
				condition[periodIndex] = 
						tmpLowerBound1 + "% < R1 < " 
						+ tmpUpperBound1 + "%"
						+ tmpLowerBound2 + "% < R2 - R3< " 
						+ tmpUpperBound2 + "%";
			}
		}
		
	}
}
