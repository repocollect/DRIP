
package org.drip.execution.parameters;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2016 Lakshmi Krishnamurthy
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
 * ArithmeticLinearMarketImpact contains the Arithmetic Linear Market Impact Inputs used in the Construction
 *  of the Impact Parameters for the Almgren and Chriss (2000) Optimal Trajectory Generation Scheme. The
 *  References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 *
 * 	- Chan, L. K. C., and J. Lakonishak (1995): The Behavior of Stock Prices around Institutional Trades,
 * 		Journal of Finance, 50, 1147-1174.
 *
 * 	- Keim, D. B., and A. Madhavan (1997): Transaction Costs and Investment Style: An Inter-exchange
 * 		Analysis of Institutional Equity Trades, Journal of Financial Economics, 46, 265-292.
 *
 * @author Lakshmi Krishnamurthy
 */

public class AssetTransactionSettings {
	private double _dblPrice = java.lang.Double.NaN;
	private double _dblBidAskSpread = java.lang.Double.NaN;
	private double _dblParticipationVolume = java.lang.Double.NaN;

	/**
	 * AssetTransactionSettings Constructor
	 * 
	 * @param dblPrice The Asset Price
	 * @param dblParticipationVolume The Participation Volume
	 * @param dblBidAskSpread The Bid-Ask Spread
	 *  
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AssetTransactionSettings (
		final double dblPrice,
		final double dblParticipationVolume,
		final double dblBidAskSpread)
		throws java.lang.Exception
	{
		if (!org.drip.quant.common.NumberUtil.IsValid (_dblPrice = dblPrice) || 0. >= _dblPrice ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblParticipationVolume = dblParticipationVolume) ||
				0. >= _dblParticipationVolume || !org.drip.quant.common.NumberUtil.IsValid (_dblBidAskSpread
					= dblBidAskSpread))
			throw new java.lang.Exception ("AssetTransactionSettings Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Asset Price
	 *  
	 * @return The Asset Price
	 */

	public double price()
	{
		return _dblPrice;
	}

	/**
	 * Retrieve the Bid-Ask Spread
	 * 
	 * @return The Bid-Ask Spread
	 */

	public double bidAskSpread()
	{
		return _dblBidAskSpread;
	}

	/**
	 * Retrieve the Participation Volume
	 * 
	 * @return The Participation Volume
	 */

	public double participationVolume()
	{
		return _dblParticipationVolume;
	}
}