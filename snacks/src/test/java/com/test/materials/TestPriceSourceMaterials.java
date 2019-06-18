package com.test.materials;

import com.google.common.collect.Lists;
import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestPriceSourceMaterials {

	PriceSourceMaterialFillerImplPublisherWrapper wrapper = new PriceSourceMaterialFillerImplPublisherWrapper();

	@Test
	public void testGetMaterial() {
		//用于让调用者线程等待多个异步任务全部结束
		CountDownLatch ct = new CountDownLatch(3);
		//统一的finallyCallback
		Runnable finallyCallback = () -> ct.countDown();

		List<Long> itemIdList = Lists.newArrayList(1L, 2L, 3L);
		ProvinceArea provinceArea = new ProvinceArea();
		MixItemPriceFactoryContract mixItemPriceFactoryContract = new MixItemPriceFactoryContract();
		PriceSourceMaterials priceSourceMaterials = new PriceSourceMaterials();
		wrapper.fillSourceMaterialsItem(priceSourceMaterials, itemIdList).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		wrapper.fillSourceMaterialsItemPriceRule(priceSourceMaterials, itemIdList, provinceArea).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		wrapper.fillSourceMaterialsItemBatchByItemIdList(priceSourceMaterials, itemIdList).flatMap(
				(userInfo) -> this.fillDependOnBatch(priceSourceMaterials, provinceArea,
				                                     mixItemPriceFactoryContract)).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		try {
			ct.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Mono<Void> fillDependOnBatch(PriceSourceMaterials priceSourceMaterials, ProvinceArea provinceArea,
	                                    MixItemPriceFactoryContract mixItemPriceFactoryContract) {
		System.out.println("get depend on batch:"+priceSourceMaterials.getBatch());
		//用于让调用者线程等待多个异步任务全部结束
		CountDownLatch ct = new CountDownLatch(4);
		//统一的finallyCallback
		Runnable finallyCallback = () -> ct.countDown();

		wrapper.fillSourceMaterialsLogisticCarry(priceSourceMaterials, null, null, null).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		wrapper.fillSourceMaterialsPackFee(priceSourceMaterials, null, null).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		wrapper.fillSourceMaterialsBiddingPrice(priceSourceMaterials, provinceArea).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		wrapper.fillSourceMaterialsPreferenceFromUmp(priceSourceMaterials, mixItemPriceFactoryContract).doFinally(
				(s) -> finallyCallback.run()).subscribe();
		try {
			ct.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Mono.empty();
	}
}
