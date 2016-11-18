
package org.drip.execution.optimum;

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
 * AC2000TradingTrajectory contains the Trading Trajectory generated by the Almgren and Chriss (2000) Scheme
 *  under the Criterion of No-Drift. The References are:
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

public class AC2000TradingTrajectory extends org.drip.execution.optimum.EfficientDiscreteTradingTrajectory {
	private double _dblKappa = java.lang.Double.NaN;
	private double _dblKappaTilda = java.lang.Double.NaN;

	/**
	 * AC2000TradingTrajectory Constructor
	 * 
	 * @param adblExecutionTimeNode Array containing the Trajectory Time Nodes
	 * @param adblHoldings Array containing the Holdings
	 * @param adblTradeList Array containing the Trade List
	 * @param dblKappaTilda AC2000 Kappa-Tilda
	 * @param dblKappa AC2000 Kappa
	 * @param dblTransactionCostExpectation The Expected Transaction Cost
	 * @param dblTransactionCostVariance The Variance of the Transaction Cost
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public AC2000TradingTrajectory (
		final double[] adblExecutionTimeNode,
		final double[] adblHoldings,
		final double[] adblTradeList,
		final double dblKappaTilda,
		final double dblKappa,
		final double dblTransactionCostExpectation,
		final double dblTransactionCostVariance)
		throws java.lang.Exception
	{
		super (adblExecutionTimeNode, adblHoldings, adblTradeList, dblTransactionCostExpectation,
			dblTransactionCostVariance);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblKappaTilda = dblKappaTilda) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblKappa = dblKappa))
			throw new java.lang.Exception ("AC2000TradingTrajectory Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Kappa Tilda
	 * 
	 * @return The Kappa Tilda
	 */

	public double kappaTilda()
	{
		return _dblKappaTilda;
	}

	/**
	 * Retrieve the Kappa
	 * 
	 * @return The Kappa
	 */

	public double kappa()
	{
		return _dblKappa;
	}

	/**
	 * Retrieve the Half-Life
	 * 
	 * @return The Half-Life
	 */

	public double halfLife()
	{
		return 1. / _dblKappa;
	}
}
