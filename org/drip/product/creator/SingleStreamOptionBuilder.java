
package org.drip.product.creator;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for fixed income analysts and developers -
 * 		http://www.credit-trader.org/Begin.html
 * 
 *  DRIP is a free, full featured, fixed income rates, credit, and FX analytics library with a focus towards
 *  	pricing/valuation, risk, and market making.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * SingleStreamOptionBuilder contains the suite of helper functions for creating the Options Product Instance
 * 	off of a single stream underlying.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class SingleStreamOptionBuilder {

	/**
	 * Create a Standard Futures Option
	 * 
	 * @param dtEffective Effective date
	 * @param forwardLabel The Forward Label
	 * @param dblStrike The Option Strike
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCaplet Is the Futures Option a Caplet? TRUE - YES
	 * @param csp Cash Settle Parameters
	 * 
	 * @return The Standard Futures Option Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloorlet FuturesOption (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike,
		final java.lang.String strManifestMeasure,
		final boolean bIsCaplet,
		final org.drip.param.valuation.CashSettleParams csp)
	{
		org.drip.product.fra.FRAStandardComponent fraStandard =
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFutures (dtEffective,
				forwardLabel);

		try {
			return null == fraStandard? null : new org.drip.product.fra.FRAStandardCapFloorlet
				(fraStandard.name() + "::OPT", fraStandard, strManifestMeasure, bIsCaplet, dblStrike, 1., new
					org.drip.product.params.LastTradingDateSetting
						(org.drip.product.params.LastTradingDateSetting.MID_CURVE_OPTION_QUARTERLY, "",
							java.lang.Integer.MIN_VALUE), new org.drip.pricer.option.BlackScholesAlgorithm(),
								csp);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Create an Exchange-traded Standard Futures Option
	 * 
	 * @param dtEffective Effective date
	 * @param forwardLabel The Forward Label
	 * @param dblStrike The Option Strike
	 * @param strManifestMeasure Measure of the Underlying Component
	 * @param bIsCaplet Is the Futures Option a Caplet? TRUE - YES
	 * @param strTradingMode The Trading Mode
	 * @param strExchange The Exchange
	 * 
	 * @return The Standard Futures Option Instance
	 */

	public static final org.drip.product.fra.FRAStandardCapFloorlet ExchangeTradedFuturesOption (
		final org.drip.analytics.date.JulianDate dtEffective,
		final org.drip.state.identifier.ForwardLabel forwardLabel,
		final double dblStrike,
		final java.lang.String strManifestMeasure,
		final boolean bIsCaplet,
		final java.lang.String strTradingMode,
		final java.lang.String strExchange)
	{
		if (null == forwardLabel) return null;

		org.drip.market.exchange.FuturesOptions fo =
			org.drip.market.exchange.FuturesOptionsContainer.ExchangeInfo (forwardLabel.fullyQualifiedName(),
				strTradingMode);

		if (null == fo) return null;

		java.util.Set<java.lang.String> setExchanges = fo.exchanges();

		if (null == setExchanges || !setExchanges.contains (strExchange)) return null;

		org.drip.product.fra.FRAStandardComponent fraStandard =
			org.drip.product.creator.SingleStreamComponentBuilder.ForwardRateFutures (dtEffective,
				forwardLabel);

		try {
			return null == fraStandard ? null : new org.drip.product.fra.FRAStandardCapFloorlet
				(fraStandard.name() + "::OPT", fraStandard, strManifestMeasure, bIsCaplet, dblStrike, 1.,
					fo.ltdsArray (strExchange)[0], new org.drip.pricer.option.BlackScholesAlgorithm(), null);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
