package com.test.materials;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Set;

/**
 * @author niangao
 * @date 2019-06-16 21:13
 **/
public class PriceSourceMaterialFillerImplPublisherWrapper {

	//线程池
	private Scheduler                     executor                  = Schedulers.elastic();
	private PriceSourceMaterialFillerImpl priceSourceMaterialFiller = new PriceSourceMaterialFillerImpl();

	public Mono<Boolean> fillSourceMaterialsItem(PriceSourceMaterials priceSourceMaterials, List<Long> itemIdList) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsItem(priceSourceMaterials, itemIdList);
			return true;
		}).subscribeOn(executor);
	}

	public Mono<Boolean> fillSourceMaterialsItemPriceRule(PriceSourceMaterials priceSourceMaterials,
	                                                      List<Long> itemIdList, ProvinceArea provinceArea) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsItemPriceRule(priceSourceMaterials, itemIdList,provinceArea);
			return true;
		}).subscribeOn(executor);
	}

	public Mono<Boolean> fillSourceMaterialsItemBatchByItemIdList(PriceSourceMaterials priceSourceMaterials,
	                                                              List<Long> itemIdList) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsItemBatchByItemIdList(priceSourceMaterials, itemIdList);
			return true;
		}).subscribeOn(executor);

	}

	public Mono<Boolean> fillSourceMaterialsLogisticCarry(PriceSourceMaterials priceSourceMaterials,
	                                                      Set<Long> logisticTemplateIdSet, Integer filterLogisticType,
	                                                      Boolean filterLogisticTypeIsByReal) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsLogisticCarry(priceSourceMaterials, logisticTemplateIdSet,filterLogisticType,filterLogisticTypeIsByReal);
			return true;
		}).subscribeOn(executor);

	}

	public Mono<Boolean> fillSourceMaterialsPackFee(PriceSourceMaterials priceSourceMaterials,
	                                                Set<Long> packTemplateIdSet, Integer spec) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsPackFee(priceSourceMaterials, packTemplateIdSet,spec);
			return true;
		}).subscribeOn(executor);

	}

	public Mono<Boolean> fillSourceMaterialsBiddingPrice(PriceSourceMaterials priceSourceMaterials,
	                                                     ProvinceArea provinceArea) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsBiddingPrice(priceSourceMaterials,provinceArea);
			return true;
		}).subscribeOn(executor);
	}

	public Mono<Boolean> fillSourceMaterialsPreferenceFromUmp(PriceSourceMaterials priceSourceMaterials,
	                                                          MixItemPriceFactoryContract mixItemPriceFactoryContract) {
		return Mono.fromCallable(() -> {
			priceSourceMaterialFiller.fillSourceMaterialsPreferenceFromUmp(priceSourceMaterials, mixItemPriceFactoryContract);
			return true;
		}).subscribeOn(executor);
	}

}
