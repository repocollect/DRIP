
package org.drip.execution.generator;

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
 * Almgren2003TrajectoryScheme contains the Temporary Impact Power Law Trading Trajectory generated by the
 *  Almgren and Chriss (2003) Scheme under the Criterion of No-Drift. The References are:
 * 
 * 	- Almgren, R., and N. Chriss (1999): Value under Liquidation, Risk 12 (12).
 * 
 * 	- Almgren, R., and N. Chriss (2000): Optimal Execution of Portfolio Transactions, Journal of Risk 3 (2)
 * 		5-39.
 * 
 * 	- Almgren, R. (2003): Optimal Execution with Nonlinear Impact Functions and Trading-Enhanced Risk,
 * 		Applied Mathematical Finance 10 (1) 1-18.
 * 
 * 	- Almgren, R., and N. Chriss (2003): Bidding Principles, Risk 97-102.
 * 
 * 	- Bertsimas, D., and A. W. Lo (1998): Optimal Control of Execution Costs, Journal of Financial Markets,
 * 		1, 1-50.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class Almgren2003TrajectoryScheme extends org.drip.execution.generator.OptimalTrajectoryScheme {

	/**
	 * Create the Standard Almgren2003TrajectoryScheme Instance
	 * 
	 * @param dblStartHoldings Trajectory Start Holdings
	 * @param dblFinishTime Trajectory Finish Time
	 * @param iNumInterval The Number of Fixed Intervals
	 * @param a2003p Almgren 2003 Impact Price Walk Parameters
	 * @param dblRiskAversion The Risk Aversion Parameter
	 * 
	 * @return The Almgren2003TrajectoryScheme Instance
	 */

	public static final Almgren2003TrajectoryScheme Standard (
		final double dblStartHoldings,
		final double dblFinishTime,
		final int iNumInterval,
		final org.drip.execution.dynamics.Almgren2003Parameters a2003p,
		final double dblRiskAversion)
	{
		try {
			return new Almgren2003TrajectoryScheme
				(org.drip.execution.strategy.DiscreteTradingTrajectoryControl.FixedInterval (new
					org.drip.execution.strategy.OrderSpecification (dblStartHoldings, dblFinishTime),
						iNumInterval), a2003p, new org.drip.execution.risk.MeanVarianceObjectiveUtility
							(dblRiskAversion));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private Almgren2003TrajectoryScheme (
		final org.drip.execution.strategy.DiscreteTradingTrajectoryControl ttc,
		final org.drip.execution.dynamics.Almgren2003Parameters a2003p,
		final org.drip.execution.risk.MeanVarianceObjectiveUtility mvou)
		throws java.lang.Exception
	{
		super (ttc, a2003p, mvou);
	}

	@Override public org.drip.execution.optimum.EfficientDiscreteTradingTrajectory generate()
	{
		org.drip.execution.dynamics.Almgren2003Parameters a2003p =
			(org.drip.execution.dynamics.Almgren2003Parameters) priceWalkParameters();

		org.drip.execution.impact.TransactionFunctionPower tfpTemporaryExpectation =
			a2003p.powerTemporaryExpectation();

		double dblLambda = ((org.drip.execution.risk.MeanVarianceObjectiveUtility)
			objectiveUtility()).riskAversion();

		org.drip.execution.strategy.DiscreteTradingTrajectoryControl ttc = control();

		double dblGamma = a2003p.linearPermanentExpectation().slope();

		final double dblK = tfpTemporaryExpectation.exponent();

		double dblEta = tfpTemporaryExpectation.constant();

		double dblSigma = a2003p.arithmeticPriceDynamicsSettings().volatility();

		final double dblX = ttc.startHoldings();

		final double dblTStar = java.lang.Math.pow (dblK * dblEta * java.lang.Math.pow (dblX, dblK - 1.) /
			(dblLambda * dblSigma * dblSigma), 1. / (dblK + 1.));

		double dblTMax = dblK > 1. ? (dblK + 1.) / (dblK - 1.) * dblTStar : java.lang.Double.NaN;

		double dblE = 0.5 * dblGamma * dblX * dblX + (dblK + 1.) / (3. * dblK + 1.) * dblEta *
			java.lang.Math.pow (dblX / dblTStar, dblK + 1.) * dblTStar;

		double dblV = (dblK + 1.) / (3. * dblK + 1.) * dblSigma * dblSigma * dblTStar * dblX * dblX;

		double dblHyperboloidBoundaryValue = java.lang.Math.pow ((dblK + 1.) / (3. * dblK + 1.), dblK + 1.) *
			dblEta * java.lang.Math.pow (dblSigma, 2. * dblK) * java.lang.Math.pow (dblX, 3. * dblK + 1.);

		org.drip.function.definition.R1ToR1 holdingsR1ToR1 = new org.drip.function.definition.R1ToR1 (null) {
			@Override public double evaluate (
				final double dblT)
				throws java.lang.Exception
			{
				if (!org.drip.quant.common.NumberUtil.IsValid (dblT))
					throw new java.lang.Exception
						("Almgren2003TrajectoryScheme::generate::evaluate => Invalid Inputs");

				if (1. > dblK)
					return dblX * java.lang.Math.pow (1. + ((1. - dblK) * dblT) / ((1. + dblK) * dblTStar),
						-1. * (1. + dblK) / (1. - dblK));

				if (1. == dblK) return dblX * java.lang.Math.pow (java.lang.Math.E, -1. * dblT/ dblTStar);

				double dblHoldings = dblX *  java.lang.Math.pow (1. - ((dblK - 1.) * dblT) / ((dblK + 1.) *
					dblTStar), (dblK + 1.) / (dblK + 1.));

				return 0. > dblX * dblHoldings ? 0. : dblHoldings;
			}
		};

		return org.drip.execution.optimum.Almgren2003TradingTrajectory.Standard (ttc.executionTimeNodes(),
			dblE, dblV, dblTStar, dblTMax, dblHyperboloidBoundaryValue, holdingsR1ToR1);
	}
}
