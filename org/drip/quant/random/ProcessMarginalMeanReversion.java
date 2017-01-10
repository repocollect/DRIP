
package org.drip.quant.random;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * 
 *  This file is part of DRIP, a free-software/open-source library for buy/side financial/trading model
 *  	libraries targeting analysts and developers
 *  	https://lakshmidrip.github.io/DRIP/
 *  
 *  DRIP is composed of four main libraries:
 *  
 *  - DRIP Fixed Income - https://lakshmidrip.github.io/DRIP-Fixed-Income/
 *  - DRIP Asset Allocation - https://lakshmidrip.github.io/DRIP-Asset-Allocation/
 *  - DRIP Numerical Optimizer - https://lakshmidrip.github.io/DRIP-Numerical-Optimizer/
 *  - DRIP Statistical Learning - https://lakshmidrip.github.io/DRIP-Statistical-Learning/
 * 
 *  - DRIP Fixed Income: Library for Instrument/Trading Conventions, Treasury Futures/Options,
 *  	Funding/Forward/Overnight Curves, Multi-Curve Construction/Valuation, Collateral Valuation and XVA
 *  	Metric Generation, Calibration and Hedge Attributions, Statistical Curve Construction, Bond RV
 *  	Metrics, Stochastic Evolution and Option Pricing, Interest Rate Dynamics and Option Pricing, LMM
 *  	Extensions/Calibrations/Greeks, Algorithmic Differentiation, and Asset Backed Models and Analytics.
 * 
 *  - DRIP Asset Allocation: Library for model libraries for MPT framework, Black Litterman Strategy
 *  	Incorporator, Holdings Constraint, and Transaction Costs.
 * 
 *  - DRIP Numerical Optimizer: Library for Numerical Optimization and Spline Functionality.
 * 
 *  - DRIP Statistical Learning: Library for Statistical Evaluation and Machine Learning.
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
 * ProcessMarginalMeanReversion guides the Random Variable Evolution according to the 1D Mean Reversion
 *  Process.
 *
 * @author Lakshmi Krishnamurthy
 */

public class ProcessMarginalMeanReversion extends org.drip.quant.random.ProcessMarginal {
	private double _dblVolatility = java.lang.Double.NaN;
	private double _dblMeanReversionRate = java.lang.Double.NaN;
	private double _dblMeanReversionLevel = java.lang.Double.NaN;

	/**
	 * Generate a Standard Instance of ProcessMarginalMeanReversion
	 * 
	 * @param dblMeanReversionRate The Mean Reversion Rate
	 * @param dblMeanReversionLevel The Mean Reversion Level
	 * @param dblVolatility The Volatility
	 * 
	 * @return The Standard Instance of ProcessMarginalMeanReversion
	 */

	public static final ProcessMarginalMeanReversion Standard (
		final double dblMeanReversionRate,
		final double dblMeanReversionLevel,
		final double dblVolatility)
	{
		try {
			org.drip.quant.random.LocalDeterministicEvolutionFunction ldevDrift = new
				org.drip.quant.random.LocalDeterministicEvolutionFunction() {
				@Override public double value (
					final org.drip.quant.random.MarginalSnap ms)
					throws java.lang.Exception
				{
					if (null == ms)
						throw new java.lang.Exception
							("ProcessMarginalMeanReversion::DriftLDEV::value => Invalid Inputs");

					return -1. * dblMeanReversionRate * (dblMeanReversionLevel - ms.value());
				}
			};

			org.drip.quant.random.LocalDeterministicEvolutionFunction ldevVolatility = new
				org.drip.quant.random.LocalDeterministicEvolutionFunction() {
				@Override public double value (
					final org.drip.quant.random.MarginalSnap ms)
					throws java.lang.Exception
				{
					return dblVolatility;
				}
			};

			return new ProcessMarginalMeanReversion (dblMeanReversionRate, dblMeanReversionLevel,
				dblVolatility, ldevDrift, ldevVolatility);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private ProcessMarginalMeanReversion (
		final double dblMeanReversionRate,
		final double dblMeanReversionLevel,
		final double dblVolatility,
		final org.drip.quant.random.LocalDeterministicEvolutionFunction ldevDrift,
		final org.drip.quant.random.LocalDeterministicEvolutionFunction ldevVolatility)
		throws java.lang.Exception
	{
		super (ldevDrift, ldevVolatility);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblMeanReversionRate = dblMeanReversionRate) ||
			!org.drip.quant.common.NumberUtil.IsValid (_dblMeanReversionLevel = dblMeanReversionLevel) ||
				!org.drip.quant.common.NumberUtil.IsValid (_dblVolatility = dblVolatility))
			throw new java.lang.Exception ("ProcessMarginalMeanReversion Constructor => Invalid Inputs");
	}

	/**
	 * Retrieve the Mean Reversion Speed
	 * 
	 * @return The Mean Reversion Speed
	 */

	public double meanReversionRate()
	{
		return _dblMeanReversionRate;
	}

	/**
	 * Retrieve the Mean Reversion Level
	 * 
	 * @return The Mean Reversion Level
	 */

	public double meanReversionLevel()
	{
		return _dblMeanReversionLevel;
	}

	/**
	 * Retrieve the Volatility of the Log Process
	 * 
	 * @return Volatility of the Log Process
	 */

	public double volatility()
	{
		return _dblVolatility;
	}
}
