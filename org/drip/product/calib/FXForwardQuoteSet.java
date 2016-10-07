
package org.drip.product.calib;

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
 * FXForwardQuoteSet extends the ProductQuoteSet by implementing the Calibration Parameters for the FX
 *  Forward Component. Currently it exposes the Outright and the PIP Fields.
 * 
 * @author Lakshmi Krishnamurthy
 */

public class FXForwardQuoteSet extends org.drip.product.calib.ProductQuoteSet {

	/**
	 * FXForwardQuoteSet Constructor
	 * 
	 * @param aLSS Array of Latent State Specification
	 * 
	 * @throws java.lang.Exception Thrown if Inputs are invalid
	 */

	public FXForwardQuoteSet (
		final org.drip.state.representation.LatentStateSpecification[] aLSS)
		throws java.lang.Exception
	{
		super (aLSS);
	}

	/**
	 * Set the Terminal FX Forward Outright
	 * 
	 * @param dblFXForwardOutright The Terminal FX Forward Outright
	 * 
	 * @return TRUE - The Terminal FX Forward Outright successfully set
	 */

	public boolean setOutright (
		final double dblFXForwardOutright)
	{
		return set ("Outright", dblFXForwardOutright);
	}

	/**
	 * Indicate if the Terminal FX Forward Outright Field exists
	 * 
	 * @return TRUE - Terminal FX Forward Outright Field Exists
	 */

	public boolean containsOutright()
	{
		return contains ("Outright");
	}

	/**
	 * Retrieve the Terminal FX Forward Outright
	 * 
	 * @return Terminal FX Forward Outright Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Terminal FX Forward Outright Field does not exist
	 */

	public double outright()
		throws java.lang.Exception
	{
		return get ("Outright");
	}

	/**
	 * Set the Terminal FX Forward PIP
	 * 
	 * @param dblFXForwardPIP The Terminal FX Forward PIP
	 * 
	 * @return TRUE - The Terminal FX Forward PIP successfully set
	 */

	public boolean setPIP (
		final double dblFXForwardPIP)
	{
		return set ("PIP", dblFXForwardPIP);
	}

	/**
	 * Indicate if the Terminal FX Forward PIP Field exists
	 * 
	 * @return TRUE - Terminal FX Forward PIP Field Exists
	 */

	public boolean containsPIP()
	{
		return contains ("PIP");
	}

	/**
	 * Retrieve the Terminal FX Forward PIP
	 * 
	 * @return Terminal FX Forward PIP Basis
	 * 
	 * @throws java.lang.Exception Thrown if the Terminal FX Forward PIP Field does not exist
	 */

	public double pip()
		throws java.lang.Exception
	{
		return get ("PIP");
	}
}
