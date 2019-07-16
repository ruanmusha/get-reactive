package com.test.materials;

import com.google.common.collect.Lists;
import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.List;

public class TestPriceSourceMaterials_v2 {

	PriceSourceMaterialFillerImplPublisherWrapper wrapper = new PriceSourceMaterialFillerImplPublisherWrapper();

	@Test
	public void testGetMaterial() {
		/*
		 * 参数
		 */
		List<Long> itemIdList = Lists.newArrayList(1L, 2L, 3L);
		ProvinceArea provinceArea = new ProvinceArea();
		MixItemPriceFactoryContract mixItemPriceFactoryContract = new MixItemPriceFactoryContract();
		PriceSourceMaterials priceSourceMaterials = new PriceSourceMaterials();

		//商品
		Mono<Boolean> itemMono = wrapper.fillSourceMaterialsItem(priceSourceMaterials, itemIdList);
		//售价规则
		Mono<Boolean> itemPriceRule = wrapper.fillSourceMaterialsItemPriceRule(priceSourceMaterials, itemIdList,
		                                                                       provinceArea);
		//批次以及关联信息
		Mono<Boolean> batchMono = wrapper.fillSourceMaterialsItemBatchByItemIdList(priceSourceMaterials,
		                                                                        itemIdList).flatMap(
				//依赖批次信息
				(result) -> this.fillDependOnBatch(priceSourceMaterials, provinceArea, mixItemPriceFactoryContract));
		Mono.zip(itemMono, itemPriceRule, batchMono).block();
	}

	//依赖批次信息
	private Mono<Boolean> fillDependOnBatch(PriceSourceMaterials priceSourceMaterials, ProvinceArea provinceArea,
	                                    MixItemPriceFactoryContract mixItemPriceFactoryContract) {
		System.out.println("get depend on batch:" + priceSourceMaterials.getBatch());

		Mono<Boolean> logisticCarryMono = wrapper.fillSourceMaterialsLogisticCarry(priceSourceMaterials, null, null,
		                                                                           null);
		Mono<Boolean> packFeeMono = wrapper.fillSourceMaterialsPackFee(priceSourceMaterials, null, null);
		Mono<Boolean> biddingPriceMono = wrapper.fillSourceMaterialsBiddingPrice(priceSourceMaterials, provinceArea);
		Mono<Boolean> activityMono = wrapper.fillSourceMaterialsPreferenceFromUmp(priceSourceMaterials,
		                                                                          mixItemPriceFactoryContract);
		return Mono.zip(logisticCarryMono, packFeeMono, biddingPriceMono, activityMono).then(Mono.just(true));
	}
}
