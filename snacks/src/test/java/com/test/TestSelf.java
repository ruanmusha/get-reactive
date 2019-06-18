package com.test;

import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class TestSelf {

	public String getItem(List<Long> itemIdList) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("get Item");
		return "item";
	}

	public String getBatch(List<Long> itemIdList) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("get batch");
		return "batch";
	}

	public Flux<String> getBatchFlux(List<Long> itemIdList) {
		Flux<String> objectFlux = Flux.create(fluxSink -> {
			fluxSink.next(getBatch(itemIdList));
			//终止关闭
			fluxSink.complete();
		});
		return objectFlux.subscribeOn(Schedulers.parallel(), false);
	}

	public Flux<String> getItemFlux(List<Long> itemIdList) {
		Flux<String> objectFlux = Flux.create(fluxSink -> {
			fluxSink.next(getItem(itemIdList));
			//终止关闭
			fluxSink.complete();
		});
		return objectFlux.subscribeOn(Schedulers.parallel(), false);
	}

	@Test
	public void testGetMaterial_1() {
		Set<String> materialSet = new HashSet<String>();

		Map<String, List> itemPriceContract = new HashMap();
		List<Long> itemIdList = new ArrayList<Long>();
		itemPriceContract.put("itemIdList", itemIdList);

		CountDownLatch countDownLatch = new CountDownLatch(2);
		Flux<String> itemFlux = this.getItemFlux(itemIdList).doOnComplete(() -> countDownLatch.countDown());
		itemFlux.subscribe(item -> materialSet.add(item));
		Flux<String> batchFlux = this.getBatchFlux(itemIdList).doOnComplete(() -> countDownLatch.countDown());
		batchFlux.subscribe(batch -> materialSet.add(batch));
		System.out.println("异步中。。。");
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(materialSet.contains("item"));
		Assert.assertTrue(materialSet.contains("batch"));
	}

	@Test
	public void testGetMaterial_2() {

	}

	@Test
	public void testCalcPrice() {
		Set<String> priceSet = new HashSet<>();
		List<Integer> priceLine = new ArrayList<>();
		priceLine.add(1);
		priceLine.add(2);
		CountDownLatch countDownLatch = new CountDownLatch(2);
		Flux.fromStream(priceLine.stream()).parallel().runOn(Schedulers.parallel()).subscribe(line -> {
			System.out.println(Thread.currentThread().getName() + " -> " + line);
			priceSet.add(calcPriceLine(line));
			countDownLatch.countDown();
		});
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(priceSet);
	}

	private String calcPriceLine(int line) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("calc price of line" + line);
		return "price:" + line;
	}

	@Test
	public void test2(){
		Flux<String> ids = Flux.just("1","2","3");

//		Flux<String> combinations =
//				ids.flatMap(id -> {
//					Mono<String> nameTask = ifhrName(id);
//					Mono<Integer> statTask = ifhrStat(id);
//
//					return nameTask.zipWith(statTask,
//					                        (name, stat) -> "Name " + name + " has stats " + stat);
//				});

		Flux<String> combinations2 =
				ids.flatMap(id -> {
					Mono<String> nameTask = ifhrName(id);
					Mono<Integer> statTask = ifhrStat(id);

					return nameTask.zipWith(statTask,
					                        (name, stat) -> calcPriceLine(stat));
				});

//		Mono<List<String>> result = combinations.collectList();
		Mono<List<String>> result2 = combinations2.collectList();

//		List<String> results = result.block();
		List<String> results2 = result2.block();

//		System.out.println(result);
		System.out.println(result2);
//		System.out.println(results);
		System.out.println(results2);

	}

	private Mono<String> ifhrName(String id){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ifhrName:" + id);
		return Mono.just(id+"s");
	}

	private Mono<Integer> ifhrStat(String id){
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ifhrStat:" + id);
		return Mono.just(Integer.parseInt(id));
	}
}
