
package org.drip.historical.attribution;

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
 * TreasuryFuturesMarketSnap contains the Metrics Snapshot associated with the relevant Manifest Measures for
 *  the given Treasury Futures Position.
 *
 * @author Lakshmi Krishnamurthy
 */

public class TreasuryFuturesMarketSnap extends org.drip.historical.attribution.PositionMarketSnap {

	/**
	 * TreasuryFuturesMarketSnap Constructor
	 * 
	 * @param dtSnap The Snapshot Date
	 * @param dblMarketValue The Snapshot Market Value
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public TreasuryFuturesMarketSnap (
		final org.drip.analytics.date.JulianDate dtSnap,
		final double dblMarketValue)
		throws java.lang.Exception
	{
		super (dtSnap, dblMarketValue);
	}

	/**
	 * Set the Yield Level and Position Sensitivity
	 * 
	 * @param dblYield The Yield Level
	 * @param dblYieldSensitivity The Position Yield Sensitivity
	 * @param dblYieldRollDown The Position Yield Roll Down
	 * 
	 * @return TRUE - The Yield Level and the Position Sensitivity successfully set
	 */

	public boolean setYieldMarketFactor (
		final double dblYield,
		final double dblYieldSensitivity,
		final double dblYieldRollDown)
	{
		return addManifestMeasureSnap ("Yield", dblYield, dblYieldSensitivity, dblYieldRollDown);
	}

	/**
	 * Set the Expiry Date
	 * 
	 * @param dtExpiry The Expiry Date
	 * 
	 * @return TRUE - The Expiry Date successfully set
	 */

	public boolean setExpiryDate (
		final org.drip.analytics.date.JulianDate dtExpiry)
	{
		return setDate ("Expiry", dtExpiry);
	}

	/**
	 * Retrieve the Expiry Date
	 * 
	 * @return The Expiry Date
	 */

	public org.drip.analytics.date.JulianDate expiryDate()
	{
		return date ("Expiry");
	}

	/**
	 * Set the CTD Bond Name
	 * 
	 * @param strCTDName Name of the CTD Bond
	 * 
	 * @return TRUE - The CTD Bond Name successfully set
	 */

	public boolean setCTDName (
		final java.lang.String strCTDName)
	{
		return setC1 ("CTDBondName", strCTDName);
	}

	/**
	 * Retrieve the CTD Name
	 * 
	 * @return The CTD Name
	 */

	public java.lang.String ctdName()
	{
		return c1 ("CTDBondName");
	}

	/**
	 * Set the Clean Expiry Price
	 * 
	 * @param dblExpiryCleanPrice The Clean Price of the CTD at Expiry
	 * 
	 * @return TRUE - The Clean Expiry Price Successfully Set
	 */

	public boolean setCleanExpiryPrice (
		final double dblExpiryCleanPrice)
	{
		return setR1 ("ExpiryCleanPrice", dblExpiryCleanPrice);
	}

	/**
	 * Retrieve the Clean Price at Expiry
	 * 
	 * @return The Clean Price at Expiry
	 * 
	 * @throws java.lang.Exception Thrown if the Clean Price at Expiry cannot be obtained
	 */

	public double expiryCleanPrice()
		throws java.lang.Exception
	{
		return r1 ("ExpiryCleanPrice");
	}

	/**
	 * Set the CTD Conversion Factor at Expiry
	 * 
	 * @param dblConversionFactor The Conversion Factor at Expiry
	 * 
	 * @return TRUE - The CTD Conversion Factor at Expiry Successfully Set
	 */

	public boolean setConversionFactor (
		final double dblConversionFactor)
	{
		return setR1 ("ConversionFactor", dblConversionFactor);
	}

	/**
	 * Retrieve the CTD Conversion Factor at Expiry
	 * 
	 * @return The CTD Conversion Factor at Expiry
	 * 
	 * @throws java.lang.Exception Thrown if the CTD Conversion Factor cannot be obtained
	 */

	public double conversionFactor()
		throws java.lang.Exception
	{
		return r1 ("ConversionFactor");
	}
}
