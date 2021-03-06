package com.quantosauros.test.jpl;

import java.util.ArrayList;

import com.quantosauros.common.Frequency;
import com.quantosauros.common.TypeDef.ConditionType;
import com.quantosauros.common.TypeDef.CouponType;
import com.quantosauros.common.TypeDef.ModelType;
import com.quantosauros.common.TypeDef.OptionType;
import com.quantosauros.common.TypeDef.PayRcv;
import com.quantosauros.common.TypeDef.RateType;
import com.quantosauros.common.TypeDef.UnderlyingType;
import com.quantosauros.common.currency.Currency;
import com.quantosauros.common.currency.Money;
import com.quantosauros.common.date.Date;
import com.quantosauros.common.date.DayCountFraction;
import com.quantosauros.common.date.PaymentPeriod;
import com.quantosauros.common.date.Vertex;
import com.quantosauros.common.hullwhite.HullWhiteParameters;
import com.quantosauros.common.hullwhite.HullWhiteVolatility;
import com.quantosauros.common.interestrate.AbstractRate;
import com.quantosauros.common.interestrate.ZeroRate;
import com.quantosauros.common.interestrate.ZeroRateCurve;
import com.quantosauros.jpl.dto.LegAmortizationInfo;
import com.quantosauros.jpl.dto.LegCouponInfo;
import com.quantosauros.jpl.dto.LegDataInfo;
import com.quantosauros.jpl.dto.LegFloatingCouponInfo;
import com.quantosauros.jpl.dto.LegScheduleInfo;
import com.quantosauros.jpl.dto.LegStructuredCouponInfo;
import com.quantosauros.jpl.dto.OptionInfo;
import com.quantosauros.jpl.dto.ProductInfo;
import com.quantosauros.jpl.dto.market.MarketInfo;
import com.quantosauros.jpl.dto.market.RateMarketInfo;
import com.quantosauros.jpl.dto.underlying.RateUnderlyingInfo;
import com.quantosauros.jpl.dto.underlying.UnderlyingInfo;
import com.quantosauros.jpl.instrument.StructuredProduct;

public class testAPSSwapWithCondiOnR1 {

	static Date _asOfDate;
	static Date _issueDate;
	static Date _maturityDate;
	static Date _nextCouponDate;
	static Currency _currency;
	static Money _principal1;
	static Money _principal2;
	
	static Date[] _startDates1;
	static Date[] _endDates1;
	static Date[] _startDates2;
	static Date[] _endDates2;
	
	static Date[] _exerciseDates;
	static double[] _exercisePrices;
	static OptionType _optionType;
	
	static DayCountFraction _dcf1;
	static DayCountFraction _dcf2;
	
	static ZeroRateCurve _structuredLegCurve1;
	static ZeroRateCurve _structuredLegCurve2;
	static ZeroRateCurve _swapLegCurve;
	static ZeroRateCurve _discountCurve;
	
	static HullWhiteParameters _structuredLegHWParam1;
	static HullWhiteParameters _structuredLegHWParam2;
	static HullWhiteParameters _swapLegHWParam;
	static HullWhiteParameters _discountHWParam;
	
	static HullWhiteVolatility[] _structuredLegHWVol1;
	static HullWhiteVolatility[] _structuredLegHWVol2;
	static HullWhiteVolatility[] _swapLegHWVol;
	static HullWhiteVolatility[] _discountHWVol;
	static ModelType _discountHWType;
	
	public static void marketData(){
		double[] spotRateValue1 = new double[] {
				0.0225,	0.024,	0.0235045,	0.0234797,	0.02353,	0.0238322,	0.0241856,	0.0251007,	0.0258179,	0.0263855,	0.0269068,	0.0273808,	0.0278061,	0.0282655,	0.0287033,	0.0292773,	0.0298588,	0.0313365,
//				0.000895,	0.0012325,	0.00157,	0.001959,	0.002336,	0.00242,	0.00274,	0.00334013,	0.00509101,	0.00723647,	0.0116903,	0.0152582,	0.017711,	0.0196054,	0.0217129,	0.0227614,	0.0239931,	0.0252333,	0.027289,	0.0291018,	0.0304552,

		};
		Vertex[] spotRateVertex1 = new Vertex[] {
				Vertex.valueOf("D1"),	
				Vertex.valueOf("M3"),	Vertex.valueOf("M6"), Vertex.valueOf("M9"),	
				Vertex.valueOf("Y1"),	Vertex.valueOf("Y1H"),
				Vertex.valueOf("Y2"),	Vertex.valueOf("Y3"),	Vertex.valueOf("Y4"),	
				Vertex.valueOf("Y5"),Vertex.valueOf("Y6"),	Vertex.valueOf("Y7"), 	
				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),	
				Vertex.valueOf("Y12"),
				Vertex.valueOf("Y15"),	Vertex.valueOf("Y20"),
//				Vertex.valueOf("D1"), Vertex.valueOf("W1"), Vertex.valueOf("M1"),
//				Vertex.valueOf("M2"), Vertex.valueOf("M3"), Vertex.valueOf("M6"),
//				Vertex.valueOf("M9"), Vertex.valueOf("Y1"), Vertex.valueOf("Y1H"),
//				Vertex.valueOf("Y2"), Vertex.valueOf("Y3"), Vertex.valueOf("Y4"),
//				Vertex.valueOf("Y5"), Vertex.valueOf("Y6"), Vertex.valueOf("Y7"),
//				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),
//				Vertex.valueOf("Y12"), Vertex.valueOf("Y15"), Vertex.valueOf("Y20"),
		
		};
		double[] spotRateValue2 = new double[] {
//				0.0225,	0.024,	0.0235045,	0.0234797,	0.02353,	0.0238322,	0.0241856,	0.0251007,	0.0258179,	0.0263855,	0.0269068,	0.0273808,	0.0278061,	0.0282655,	0.0287033,	0.0292773,	0.0298588,	0.0313365,
				0.000895,	0.0012325,	0.00157,	0.001959,	0.002336,	0.00242,	0.00274,	0.00334013,	0.00509101,	0.00723647,	0.0116903,	0.0152582,	0.017711,	0.0196054,	0.0217129,	0.0227614,	0.0239931,	0.0252333,	0.027289,	0.0291018,	0.0304552,

		};
		Vertex[] spotRateVertex2 = new Vertex[] {
//				Vertex.valueOf("D1"),	
//				Vertex.valueOf("M3"),	Vertex.valueOf("M6"), Vertex.valueOf("M9"),	
//				Vertex.valueOf("Y1"),	Vertex.valueOf("Y1H"),
//				Vertex.valueOf("Y2"),	Vertex.valueOf("Y3"),	Vertex.valueOf("Y4"),	
//				Vertex.valueOf("Y5"),Vertex.valueOf("Y6"),	Vertex.valueOf("Y7"), 	
//				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),	
//				Vertex.valueOf("Y12"),
//				Vertex.valueOf("Y15"),	Vertex.valueOf("Y20"),
				Vertex.valueOf("D1"), Vertex.valueOf("W1"), Vertex.valueOf("M1"),
				Vertex.valueOf("M2"), Vertex.valueOf("M3"), Vertex.valueOf("M6"),
				Vertex.valueOf("M9"), Vertex.valueOf("Y1"), Vertex.valueOf("Y1H"),
				Vertex.valueOf("Y2"), Vertex.valueOf("Y3"), Vertex.valueOf("Y4"),
				Vertex.valueOf("Y5"), Vertex.valueOf("Y6"), Vertex.valueOf("Y7"),
				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),
				Vertex.valueOf("Y12"), Vertex.valueOf("Y15"), Vertex.valueOf("Y20"),
		
		};

		double[] swapLegRateValue = new double[] {
				0.0225,	0.024,	0.0235045,	0.0234797,	0.02353,	0.0238322,	0.0241856,	0.0251007,	0.0258179,	0.0263855,	0.0269068,	0.0273808,	0.0278061,	0.0282655,	0.0287033,	0.0292773,	0.0298588,	0.0313365,
//				0.000895,	0.0012325,	0.00157,	0.001959,	0.002336,	0.00242,	0.00274,	0.00334013,	0.00509101,	0.00723647,	0.0116903,	0.0152582,	0.017711,	0.0196054,	0.0217129,	0.0227614,	0.0239931,	0.0252333,	0.027289,	0.0291018,	0.0304552,

		};
		Vertex[] swapLegRateVertex = new Vertex[] {
				Vertex.valueOf("D1"),	
				Vertex.valueOf("M3"),	Vertex.valueOf("M6"), Vertex.valueOf("M9"),	
				Vertex.valueOf("Y1"),	Vertex.valueOf("Y1H"),
				Vertex.valueOf("Y2"),	Vertex.valueOf("Y3"),	Vertex.valueOf("Y4"),	
				Vertex.valueOf("Y5"),Vertex.valueOf("Y6"),	Vertex.valueOf("Y7"), 	
				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),	
				Vertex.valueOf("Y12"),
				Vertex.valueOf("Y15"),	Vertex.valueOf("Y20"),
//				Vertex.valueOf("D1"), Vertex.valueOf("W1"), Vertex.valueOf("M1"),
//				Vertex.valueOf("M2"), Vertex.valueOf("M3"), Vertex.valueOf("M6"),
//				Vertex.valueOf("M9"), Vertex.valueOf("Y1"), Vertex.valueOf("Y1H"),
//				Vertex.valueOf("Y2"), Vertex.valueOf("Y3"), Vertex.valueOf("Y4"),
//				Vertex.valueOf("Y5"), Vertex.valueOf("Y6"), Vertex.valueOf("Y7"),
//				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),
//				Vertex.valueOf("Y12"), Vertex.valueOf("Y15"), Vertex.valueOf("Y20"),
		
		};
		double[] discountRateValue = new double[] {
				0.0225,	0.024,	0.0235045,	0.0234797,	0.02353,	0.0238322,	0.0241856,	0.0251007,	0.0258179,	0.0263855,	0.0269068,	0.0273808,	0.0278061,	0.0282655,	0.0287033,	0.0292773,	0.0298588,	0.0313365,
		}; 
		Vertex[] discountRateVertex = new Vertex[] {
				Vertex.valueOf("D1"),	
				Vertex.valueOf("M3"),	Vertex.valueOf("M6"), Vertex.valueOf("M9"),	
				Vertex.valueOf("Y1"),	Vertex.valueOf("Y1H"),
				Vertex.valueOf("Y2"),	Vertex.valueOf("Y3"),	Vertex.valueOf("Y4"),	
				Vertex.valueOf("Y5"),Vertex.valueOf("Y6"),	Vertex.valueOf("Y7"), 	
				Vertex.valueOf("Y8"), Vertex.valueOf("Y9"), Vertex.valueOf("Y10"),	
				Vertex.valueOf("Y12"),
				Vertex.valueOf("Y15"),	Vertex.valueOf("Y20"),
		};
		
		ArrayList<AbstractRate> spotRates1 = new ArrayList<>();
		ArrayList<AbstractRate> spotRates2 = new ArrayList<>();
		ArrayList<AbstractRate> swapLegRates = new ArrayList<>();
		ArrayList<AbstractRate> discountRates = new ArrayList<>();
		for (int i = 0; i < spotRateValue1.length; i++){
			spotRates1.add(new ZeroRate(spotRateVertex1[i], spotRateValue1[i]));			
		}
		for (int i = 0; i < spotRateValue2.length; i++){
			spotRates2.add(new ZeroRate(spotRateVertex2[i], spotRateValue2[i]));			
		}
		for (int i = 0; i < swapLegRateValue.length; i++){
			swapLegRates.add(new ZeroRate(swapLegRateVertex[i], swapLegRateValue[i]));			
		}
		for (int i = 0; i < discountRateValue.length; i++){
			discountRates.add(new ZeroRate(discountRateVertex[i], discountRateValue[i]));			
		}
		_structuredLegCurve1 = new ZeroRateCurve(_asOfDate, spotRates1,
				Frequency.valueOf("C"), DayCountFraction.ACTUAL_365);
		_structuredLegCurve2 = new ZeroRateCurve(_asOfDate, spotRates2,
				Frequency.valueOf("C"), DayCountFraction.ACTUAL_365);
		_swapLegCurve = new ZeroRateCurve(_asOfDate, swapLegRates,
				Frequency.valueOf("C"), DayCountFraction.ACTUAL_365);
		_discountCurve = new ZeroRateCurve(_asOfDate, discountRates,
				Frequency.valueOf("C"), DayCountFraction.ACTUAL_365);
		
		double meanReversion1_1F = 0.01;
		double hullWhiteVolatility_1F = 0.04;
		double meanReversion1_2F = 0.01;
		double meanReversion2_2F = 0.005; 
		double hullWhiteVolatility1_2F = 0.01;
		double hullWhiteVolatility2_2F = 0.005; 
		double correlation = -0.75;
		
		_structuredLegHWParam1 = new HullWhiteParameters(
				meanReversion1_1F, hullWhiteVolatility_1F, 
				meanReversion1_2F, meanReversion2_2F, 
				hullWhiteVolatility1_2F, hullWhiteVolatility2_2F, 
				correlation);
		
		_structuredLegHWParam2 = new HullWhiteParameters(
				meanReversion1_1F, hullWhiteVolatility_1F, 
				meanReversion1_2F, meanReversion2_2F, 
				hullWhiteVolatility1_2F, hullWhiteVolatility2_2F, 
				correlation);
		
		_swapLegHWParam = new HullWhiteParameters(
				meanReversion1_1F, hullWhiteVolatility_1F, 
				meanReversion1_2F, meanReversion2_2F, 
				hullWhiteVolatility1_2F, hullWhiteVolatility2_2F, 
				correlation);
		
		_discountHWParam = new HullWhiteParameters(
				meanReversion1_1F, hullWhiteVolatility_1F, 
				meanReversion1_2F, meanReversion2_2F, 
				hullWhiteVolatility1_2F, hullWhiteVolatility2_2F, 
				correlation);
		
		double[] tenors = new double[] {0.25,};
		double[] volatilities = new double[] {0.01};
		
		_structuredLegHWVol1 = new HullWhiteVolatility[]{
				new HullWhiteVolatility(tenors, volatilities),
		};
		_structuredLegHWVol2 = new HullWhiteVolatility[]{
				new HullWhiteVolatility(tenors, volatilities),
		};
		_swapLegHWVol = new HullWhiteVolatility[]{
				new HullWhiteVolatility(tenors, volatilities),
		};
		_discountHWVol = new HullWhiteVolatility[]{
				new HullWhiteVolatility(tenors, volatilities),
		};
		
		_discountHWType = ModelType.HW1F;
	}
	
	public static void instrumentData(){
		
		_asOfDate = Date.valueOf("20151107");
		
		_issueDate = Date.valueOf("20140509");
		_maturityDate = Date.valueOf("20170509");
		_nextCouponDate = Date.valueOf("20151109");
		
		_currency = Currency.getInstance("KR");
		_principal1 = Money.valueOf("KRW", 1.0E10);
		_principal2 = Money.valueOf("KRW", 1.0E10);
		
		_startDates1 = new Date[] {
				new Date("20140509"),	new Date("20140809"),	new Date("20141109"),	new Date("20150209"),	new Date("20150509"),	new Date("20150809"),	new Date("20151109"),	new Date("20160209"),	new Date("20160509"),	new Date("20160809"),	new Date("20161109"),	new Date("20170209"),
		};
		_endDates1 = new Date[]{
				new Date("20140809"),	new Date("20141109"),	new Date("20150209"),	new Date("20150509"),	new Date("20150809"),	new Date("20151109"),	new Date("20160209"),	new Date("20160509"),	new Date("20160809"),	new Date("20161109"),	new Date("20170209"),	new Date("20170509"),
		};
		_startDates2 = new Date[] {
				new Date("20140509"),	new Date("20140809"),	new Date("20141109"),	new Date("20150209"),	new Date("20150509"),	new Date("20150809"),	new Date("20151109"),	new Date("20160209"),	new Date("20160509"),	new Date("20160809"),	new Date("20161109"),	new Date("20170209"),
		};
		_endDates2 = new Date[]{
				new Date("20140809"),	new Date("20141109"),	new Date("20150209"),	new Date("20150509"),	new Date("20150809"),	new Date("20151109"),	new Date("20160209"),	new Date("20160509"),	new Date("20160809"),	new Date("20161109"),	new Date("20170209"),	new Date("20170509"),
		};
		
//		_exerciseDates = new Date[]{
//			new Date("20150509"),	new Date("20150809"),	new Date("20151109"),	new Date("20160209"),	new Date("20160509"),	new Date("20160809"),	new Date("20161109"),	new Date("20170209"),
//		};
//		_exercisePrices = new double[_exerciseDates.length];
//		for (int i = 0; i < _exerciseDates.length; i++){
//			_exercisePrices[i] = 1;
//		}
		_optionType = OptionType.NONE;
		
		_dcf1 = DayCountFraction.ACTUAL_365;
		_dcf2 = DayCountFraction.ACTUAL_360;
	}
	
	public static void main(String[] args) {
		
		instrumentData();
		marketData();
				
		//PRODUCT INFO
		ProductInfo productInfo = new ProductInfo(
				_issueDate, _maturityDate, _currency, true);
				
		//LEG SCHEDULE INFO
		LegScheduleInfo[] legScheduleInfos = new LegScheduleInfo[2];
		
		PaymentPeriod[] periods1 = new PaymentPeriod[_startDates1.length];
		for (int i = 0; i < _startDates1.length; i++){
			periods1[i] = new PaymentPeriod(
					_startDates1[i], _startDates1[i], _endDates1[i], _endDates1[i]);
		}
		
		legScheduleInfos[0] = new LegScheduleInfo(periods1, _dcf1);
		
		PaymentPeriod[] periods2 = new PaymentPeriod[_startDates2.length];
		for (int i = 0; i < _startDates2.length; i++){
			periods2[i] = new PaymentPeriod(
					_startDates2[i], _startDates2[i], _endDates2[i], _endDates2[i]);
		}
		legScheduleInfos[1] = new LegScheduleInfo(periods2, _dcf2);
		
		//LEG DATA INFO
		LegDataInfo[] legDataInfos = new LegDataInfo[2];
		legDataInfos[0] = new LegDataInfo(_nextCouponDate);
		legDataInfos[0].setCumulatedAvgCoupon(0.03);		
		
		legDataInfos[1] = new LegDataInfo(_nextCouponDate);
		legDataInfos[1].setNextCouponRate(0.016);

		//LEG AMORTIZATION INFO
		LegAmortizationInfo[] legAmortizationInfos = new LegAmortizationInfo[2];
		legAmortizationInfos[0] = new LegAmortizationInfo(_principal1);
		legAmortizationInfos[1] = new LegAmortizationInfo(_principal2);
		
		//LEG COUPON INFO
		LegCouponInfo[] legCouponInfos = new LegCouponInfo[2];
		
		CouponType[] couponType1 = new CouponType[_startDates1.length];
		UnderlyingType underlyingType1 = UnderlyingType.R1mR2;
		ConditionType conditionType1 = ConditionType.R2;
		
		UnderlyingInfo[] payUndInfos = new UnderlyingInfo[]{
			new RateUnderlyingInfo(
					ModelType.HW1F, 0.25, Frequency.valueOf("Q"), RateType.SPOT),
			new RateUnderlyingInfo(
					ModelType.HW1F, 0.25, Frequency.valueOf("Q"), RateType.SPOT),
		};
				
		int conditionNum = 1;
		int referenceNum = payUndInfos.length;
		double[][] upperLimits = new double[conditionNum][_startDates1.length];
		double[][] lowerLimits = new double[conditionNum][_startDates1.length];
		double[] inCouponRates = null;
		double[] outCouponRates = null;
		boolean hasCap = true;
		double[] cap = new double[_startDates1.length];
		boolean hasFloor = true;
		double[] floor = new double[_startDates1.length];
		double[][] leverages1 = new double[referenceNum][_startDates1.length];
		double[] spreads1 = new double[_startDates1.length];
		PayRcv payRcv1 = PayRcv.PAY;
		
		for (int i = 0; i < _startDates1.length; i++){
			couponType1[i] = CouponType.AVERAGE;
			upperLimits[0][i] = 0.05;
			lowerLimits[0][i] = 0.0;
			cap[i] = 0.04;
			leverages1[0][i] = 2;
			leverages1[1][i] = 2;
			spreads1[i] = 0.015;
		}
					
		legCouponInfos[0] = new LegStructuredCouponInfo(payRcv1,
				couponType1, underlyingType1, conditionType1, 
				payUndInfos,
				upperLimits, lowerLimits, 
				inCouponRates, outCouponRates, 
				hasCap, cap, hasFloor, floor, 
				leverages1, spreads1);
		
		PayRcv payRcv2 = PayRcv.RCV;
		CouponType[] couponType2 = new CouponType[_startDates2.length];
		UnderlyingType underlyingType2 = UnderlyingType.R1;
		ConditionType conditionType2 = ConditionType.NONE;
		
		UnderlyingInfo[] rcvUndInfos = new UnderlyingInfo[]{
			new RateUnderlyingInfo(
					ModelType.HW1F, 0.25, Frequency.valueOf("Q"), RateType.SPOT),
			
		};
		
		double[] spreads2 = new double[_startDates2.length];
		double[][] leverages2 = new double[referenceNum][_startDates2.length];
		for (int i = 0; i < _startDates2.length; i++){
			couponType2[i] = CouponType.RESET;
			leverages2[0][i] = 1.0;
			spreads2[i] = 0.0;
		}
		
		legCouponInfos[1] = new LegFloatingCouponInfo(payRcv2,
				couponType2, underlyingType2, conditionType2,
				rcvUndInfos,
				leverages2, spreads2);	
		
		//OPTION INFO
		OptionInfo optionInfo = new OptionInfo(
				_optionType, _exerciseDates, _exercisePrices);
				
		//QUANTO INFO
		double[][] fxAssetCorrelations = new double[legCouponInfos.length][];
		double[][] fxVolatilities = new double[legCouponInfos.length][];
		Currency productCCY = productInfo.getCurrency();
		for (int legIndex = 0; legIndex < legCouponInfos.length; legIndex++){
			int undNum = legCouponInfos[legIndex].getUnderlyingNumber();
			fxAssetCorrelations[legIndex] = new double[undNum];
			fxVolatilities[legIndex] = new double[undNum];
			for (int undIndex = 0; undIndex < undNum; undIndex++){
				fxAssetCorrelations[legIndex][undIndex] = 0;
				fxVolatilities[legIndex][undIndex] = 0.1;
			}
		}
				
		int monitorFrequency = 1;
		long seed = 12345L;
		int simNum = 5000;
		
		StructuredProduct product = new StructuredProduct(
				_asOfDate,
				productInfo, 
				legScheduleInfos, 
				legDataInfos, 
				legAmortizationInfos, 
				legCouponInfos, 
				optionInfo,
				_discountHWType,
				monitorFrequency,
				seed, simNum);
		
		MarketInfo[][] legUnderlyingInfos = new MarketInfo[2][];
		legUnderlyingInfos[0] = new MarketInfo[2];
		legUnderlyingInfos[0][0] = new RateMarketInfo(
				_structuredLegCurve1, _structuredLegHWParam1, _structuredLegHWVol1			
		);
		legUnderlyingInfos[0][1] = new RateMarketInfo(
				_structuredLegCurve2, _structuredLegHWParam2, _structuredLegHWVol2			
		);
		legUnderlyingInfos[1] = new MarketInfo[1];
		legUnderlyingInfos[1][0] = new RateMarketInfo(
				_swapLegCurve, _swapLegHWParam, _swapLegHWVol			
		);
		
		//01
//		double r1r2 = 1; double r1r3 = 0.7; double r1r4 = 0.5;
//		double r2r3 = 0.7; double r2r4 = 0.5; double r3r4 = 0.8;
		//02
//		double r1r2 = 0.9; double r1r3 = 1; double r1r4 = 0.5;
//		double r2r3 = 0.9; double r2r4 = 0.7; double r3r4 = 0.5;
		//03
//		double r1r2 = 0.9; double r1r3 = 0.7; double r1r4 = 1;
//		double r2r3 = 0.5; double r2r4 = 0.9; double r3r4 = 0.7;
		//12
//		double r1r2 = 0.9; double r1r3 = 0.9; double r1r4 = 0.5;
//		double r2r3 = 1; double r2r4 = 0.7; double r3r4 = 0.7;
		//13
//		double r1r2 = 0.7; double r1r3 = 0.5; double r1r4 = 0.7;
//		double r2r3 = 0.9; double r2r4 = 1; double r3r4 = 0.9;
		//23
//		double r1r2 = 0.8; double r1r3 = 0.7; double r1r4 = 0.7;
//		double r2r3 = 0.5; double r2r4 = 0.5; double r3r4 = 1;

		//012
//		double r1r2 = 1; double r1r3 = 1; double r1r4 = 0.8;
//		double r2r3 = 1; double r2r4 = 0.8; double r3r4 = 0.8;
		//013
//		double r1r2 = 1; double r1r3 = 0.8; double r1r4 = 1;
//		double r2r3 = 0.8; double r2r4 = 1; double r3r4 = 0.8;
		//023
		double r1r2 = 0.8; double r1r3 = 1; double r1r4 = 1;
		double r2r3 = 0.8; double r2r4 = 0.8; double r3r4 = 1;
		//123
//		double r1r2 = 0.8; double r1r3 = 0.8; double r1r4 = 0.8;
//		double r2r3 = 1; double r2r4 = 1; double r3r4 = 1;
		//0123
//		double r1r2 = 0.1; double r1r3 = 0.2; double r1r4 = 0.3;
//		double r2r3 = 0.4; double r2r4 = 0.5; double r3r4 = 0.6;
		
		//r1, r4 = KRW, r2, r3 = USD
//		double r1r2 = 0.8; double r1r3 = 0.8; double r1r4 = 1;
//		double r2r3 = 1; double r2r4 = 0.8; double r3r4 = 0.8;

		
		double[][] correlations = new double[][]{
			{1, r1r2, r1r3, r1r4}, 
			{r1r2, 1, r2r3, r2r4}, 
			{r1r3, r2r3, 1, r3r4},
			{r1r4, r2r4, r3r4, 1},
		};
		
		RateMarketInfo discountUnderlyingInfo = new RateMarketInfo(
				_discountCurve, _discountHWParam, _discountHWVol);
		
		
		Money price = product.getPrice(
				legUnderlyingInfos, discountUnderlyingInfo,
				correlations);
		
		Money leg1Price = product.getLegPrice(0);
		Money leg2Price = product.getLegPrice(1);
		
		System.out.println(price + ", " + leg1Price + ", " + leg2Price);
		
		double[][] lsmcParams1 = product.getLSMCParams(0);
		double[][] lsmcParams2 = product.getLSMCParams(1);
		
		product.getResults();
		
		double epsilon = 0.001;
		for (int legIndex = 0; legIndex < legCouponInfos.length; legIndex++){
			for (int undIndex = 0; undIndex < legCouponInfos[legIndex].getUnderlyingNumber(); undIndex++){
				ArrayList<Double> delta = product.getDelta(legUnderlyingInfos, discountUnderlyingInfo, 
						correlations, legIndex, undIndex, epsilon);
				
				for (int deltaIndex = 0; deltaIndex < delta.size(); deltaIndex++){
					System.out.println(delta.get(deltaIndex));
				}
				
			}
		}
		
	}

}
