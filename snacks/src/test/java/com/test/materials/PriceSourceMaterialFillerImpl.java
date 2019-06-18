package com.test.materials;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author niangao
 * @date 2019-06-16 21:13
 **/
public class PriceSourceMaterialFillerImpl {

	public void fillSourceMaterialsItem(PriceSourceMaterials priceSourceMaterials, List<Long> itemIdList) {
		EchoMethod.echoAfterTime("fillSourceMaterialsItem", 1000, TimeUnit.MILLISECONDS);
		priceSourceMaterials.setItem("fill item");
	}

	public void fillSourceMaterialsItemPriceRule(PriceSourceMaterials priceSourceMaterials, List<Long> itemIdList,
	                                             ProvinceArea provinceArea) {
		EchoMethod.echoAfterTime("fillSourceMaterialsItemPriceRule", 1000, TimeUnit.MILLISECONDS);

	}

	public void fillSourceMaterialsItemBatchByItemIdList(PriceSourceMaterials priceSourceMaterials,
	                                                     List<Long> itemIdList) {
		EchoMethod.echoAfterTime("fillSourceMaterialsItemBatchByItemIdList", 10, TimeUnit.MILLISECONDS);
		priceSourceMaterials.setBatch("fill batch");
	}

	public void fillSourceMaterialsLogisticCarry(PriceSourceMaterials priceSourceMaterials,
	                                             Set<Long> logisticTemplateIdSet, Integer filterLogisticType,
	                                             Boolean filterLogisticTypeIsByReal) {
		EchoMethod.echoAfterTime("fillSourceMaterialsLogisticCarry", 1000, TimeUnit.MILLISECONDS);
	}

	public void fillSourceMaterialsPackFee(PriceSourceMaterials priceSourceMaterials, Set<Long> packTemplateIdSet,
	                                       Integer spec) {
		EchoMethod.echoAfterTime("fillSourceMaterialsPackFee", 1000, TimeUnit.MILLISECONDS);

	}

	public void fillSourceMaterialsBiddingPrice(PriceSourceMaterials priceSourceMaterials, ProvinceArea provinceArea) {
		EchoMethod.echoAfterTime("fillSourceMaterialsBiddingPrice", 1000, TimeUnit.MILLISECONDS);
	}

	public void fillSourceMaterialsPreferenceFromUmp(PriceSourceMaterials priceSourceMaterials,
	                                                 MixItemPriceFactoryContract mixItemPriceFactoryContract) {
		EchoMethod.echoAfterTime("fillSourceMaterialsPreferenceFromUmp", 1000, TimeUnit.MILLISECONDS);
	}

}
