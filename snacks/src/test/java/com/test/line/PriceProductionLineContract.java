package com.test.line;

import com.google.common.collect.Lists;
import com.test.materials.EchoMethod;
import com.test.materials.PriceSourceMaterials;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author niangao
 * @date 2019-06-16 23:48
 **/
public class PriceProductionLineContract {

	private Scheduler                 executor                = Schedulers.elastic();
	private List<PriceProductionLine> priceProductionLineList = Lists.newArrayList();

	{
		priceProductionLineList.add(new PriceProductionLine());
		priceProductionLineList.add(new PriceProductionLine());
		priceProductionLineList.add(new PriceProductionLine());
	}

	private List<String> getPriceTypeList() {
		return Lists.newArrayList("1", "2", "3");
	}

	public void singlePriceProductionLineWork(PriceProduction priceProduction,
	                                          PriceSourceMaterials priceSourceMaterials,
	                                          PriceProductionLineContract priceProductionLineContract) {
		List<String> itemPriceTypeList = priceProductionLineContract.getPriceTypeList();
		Flux.fromStream(priceProductionLineList.stream()).map(
				priceProductionLine -> calcProductionLinePriceWrap(priceProduction, priceSourceMaterials,
				                                                   priceProductionLineContract, priceProductionLine,
				                                                   itemPriceTypeList));

	}

	private Mono calcProductionLinePriceWrap(PriceProduction priceProduction, PriceSourceMaterials priceSourceMaterials,
	                                         PriceProductionLineContract priceProductionLineContract,
	                                         PriceProductionLine priceProductionLine, List<String> itemPriceTypeList) {
		return Mono.fromCallable(() -> {
			calcProductionLinePrice(priceProduction, priceSourceMaterials, priceProductionLineContract,
			                        priceProductionLine, itemPriceTypeList);
			return true;
		}).subscribeOn(executor);
	}

	private void calcProductionLinePrice(PriceProduction priceProduction, PriceSourceMaterials priceSourceMaterials,
	                                     PriceProductionLineContract priceProductionLineContract,
	                                     PriceProductionLine priceProductionLine, List<String> itemPriceTypeList) {
		EchoMethod.echoAfterTime("calc price", 1000, TimeUnit.MILLISECONDS);
	}
}
