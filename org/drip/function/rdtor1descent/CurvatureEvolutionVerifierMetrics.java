
package org.drip.function.rdtor1descent;

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
 * CurvatuveEvolutionVerifierMetrics implements the Armijo Criterion used for the Inexact Line Search
 *  Increment Generation to ascertain that the Gradient of the Function has reduced sufficiently. The
 *  References are:
 * 
 * 	- Wolfe, P. (1969): Convergence Conditions for Ascent Methods, SIAM Review 11 (2) 226-235.
 * 
 * 	- Wolfe, P. (1971): Convergence Conditions for Ascent Methods; II: Some Corrections, SIAM Review 13 (2)
 * 		185-188.
 *
 * @author Lakshmi Krishnamurthy
 */

public class CurvatureEvolutionVerifierMetrics extends
	org.drip.function.rdtor1descent.LineEvolutionVerifierMetrics {
	private boolean _bStrongCurvatureCriterion = false;
	private double[] _adblNextVariateFunctionJacobian = null;
	private double _dblCurvatureParameter = java.lang.Double.NaN;

	/**
	 * CurvatureEvolutionVerifierMetrics Constructor
	 * 
	 * @param dblCurvatureParameter The Curvature Criterion Parameter
	 * @param bStrongCurvatureCriterion TRUE - Apply the "Strong" Curvature Criterion
	 * @param uvTargetDirection The Target Direction Unit Vector
	 * @param adblCurrentVariate Array of Current Variate
	 * @param dblStepLength The Incremental Step Length
	 * @param adblCurrentVariateFunctionJacobian The Function Jacobian at the Current Variate
	 * @param adblNextVariateFunctionJacobian The Function Jacobian at the Next Variate
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public CurvatureEvolutionVerifierMetrics (
		final double dblCurvatureParameter,
		final boolean bStrongCurvatureCriterion,
		final org.drip.function.definition.UnitVector uvTargetDirection,
		final double[] adblCurrentVariate,
		final double dblStepLength,
		final double[] adblCurrentVariateFunctionJacobian,
		final double[] adblNextVariateFunctionJacobian)
		throws java.lang.Exception
	{
		super (uvTargetDirection, adblCurrentVariate, dblStepLength, adblCurrentVariateFunctionJacobian);

		if (!org.drip.quant.common.NumberUtil.IsValid (_dblCurvatureParameter = dblCurvatureParameter) ||
			null == (_adblNextVariateFunctionJacobian = adblNextVariateFunctionJacobian) ||
				adblCurrentVariate.length != _adblNextVariateFunctionJacobian.length)
			throw new java.lang.Exception
				("CurvatureEvolutionVerifierMetrics Constructor => Invalid Inputs");

		_bStrongCurvatureCriterion = bStrongCurvatureCriterion;
	}

	/**
	 * Retrieve the Curvature Parameter
	 * 
	 * @return The Curvature Parameter
	 */

	public double curvatureParameter()
	{
		return _dblCurvatureParameter;
	}

	/**
	 * Retrieve Whether of not the "Strong" Curvature Criterion needs to be met
	 * 
	 * @return TRUE - The "Strong" Curvature Criterion needs to be met
	 */

	public boolean strongCriterion()
	{
		return _bStrongCurvatureCriterion;
	}

	/**
	 * Retrieve the Function Jacobian at the Next Variate
	 * 
	 * @return The Function Jacobian at the Next Variate
	 */

	public double[] nextVariateFunctionJacobian()
	{
		return _adblNextVariateFunctionJacobian;
	}

	/**
	 * Indicate if the Curvature Criterion has been met
	 * 
	 * @return TRUE - The Curvature Criterion has been met
	 */

	public boolean verify()
	{
		double[] adblDirectionVector = targetDirection().component();

		try {
			double dblNextFunctionIncrement = org.drip.quant.linearalgebra.Matrix.DotProduct
				(adblDirectionVector, _adblNextVariateFunctionJacobian);

			double dblParametrizedCurrentFunctionIncrement = _dblCurvatureParameter *
				org.drip.quant.linearalgebra.Matrix.DotProduct (adblDirectionVector,
					currentVariateFunctionJacobian());

			return _bStrongCurvatureCriterion ? java.lang.Math.abs (dblNextFunctionIncrement) <=
				java.lang.Math.abs (dblParametrizedCurrentFunctionIncrement) : dblNextFunctionIncrement >=
					dblParametrizedCurrentFunctionIncrement;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
