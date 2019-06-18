package com.test.line;

import com.test.materials.PriceSourceMaterials;
import org.junit.Test;

/**
 * @author niangao
 * @date 2019-06-17 00:29
 **/
public class TestPriceProductionLine {

	PriceProductionLineContract priceProductionLineContract = new PriceProductionLineContract();

	@Test
	public void testPriceProductionLine() {
		PriceProduction priceProduction = new PriceProduction();
		PriceSourceMaterials priceSourceMaterials = new PriceSourceMaterials();
		PriceProductionLineContract priceProductionLineContract = new PriceProductionLineContract();
		priceProductionLineContract.singlePriceProductionLineWork(priceProduction, priceSourceMaterials,
		                                                          priceProductionLineContract);
	}
}
