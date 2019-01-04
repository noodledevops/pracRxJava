package PrecReactiveJavaProject.ch02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import com.google.common.eventbus.Subscribe;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class Ex03ObserFrom {

	void emitFromArray() {
		// !! 래퍼런스 배열 객체로 수행하는 것이 포인트!!
		// 시스템에서 정의된 int 변수 배열의 경우 아래 전환 메소드를 사용함!
		Integer[] arr = { 100, 200, 300 };
		Observable<Integer> source = Observable.fromArray(arr);
		source.subscribe(System.out::println);
	}

	private static Integer[] toIntegerArray(int[] intArray) {
		return IntStream.of(intArray).boxed().toArray(Integer[]::new);
	}

	/// 이터레이터 이용한 from 류 사용 - 단 Map 자료구조는 없다.

	void emitFromIterable_List() {
		List<String> names = new ArrayList<>();
		names.add("Jerry");
		names.add("William");
		names.add("Bob");

		Observable<String> source = Observable.fromIterable(names);
		source.subscribe(System.out::println);
	}

	void emitFromIterable_Set() {
		Set<String> cities = new HashSet();
		cities.add("Seoul");
		cities.add("London");
		cities.add("Paris");

		Observable<String> source = Observable.fromIterable(cities);
		source.subscribe(System.out::println);
	}

	void emitFromIterable_BlockingQueue() {
		BlockingQueue<Order> orderQueue = new ArrayBlockingQueue<>(100);
		orderQueue.add(new Order("ORD-1"));
		orderQueue.add(new Order("ORD-2"));
		orderQueue.add(new Order("ORD-3"));

		Observable<Order> source = Observable.fromIterable(orderQueue);
		source.subscribe(order -> System.out.println(order.getmId()));
	}

	public static class Order {
		private String mId;

		public Order(String mId) {
			this.mId = mId;
		}

		public String getmId() {
			return mId;
		}

		public void setmId(String mId) {
			this.mId = mId;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Order [mId=").append(mId).append("]");
			return builder.toString();
		}
		
	}// end of class

	/// 비동기 관련 RxJava 프로그래밍 라이브러리, 이후 부터는 비동기 클래스나 인터페이스 연동을 살펴봄

	/**
	 * Java 5에서 추가된 동시성 API 인 Callable 인터페이스 비동기 실행 후 결과를 반환하는 call() 메소드 정의 -
	 * run 메서드를 구현하는 Runnable 인터페이스처럼 메서드가 하나이며 인자가 없다는 점에서 비슷 그러나 실행결과를 리턴한다는
	 * 점에서 차이가 있음
	 */
	void emitFromCallable() {
		Callable<String> callable = () -> {
			Thread.sleep(1000);
			return "Hello Callable";
		};

		Observable<String> source = Observable.fromCallable(callable);
		// 람다 표현식을 안쓰면 이런 모양임!
		source.subscribe(new Consumer<String>() {
			@Override
			public void accept(String t) throws Exception {
				System.out.println(t);
			}
		});
	}

	/**
	 * Future 인터페이스 자바 5에서 추가된 동시성 API 비동기 계산 결과를 구할 때 사용
	 * get 메서드를 호출하면 Callable 객체에서 구현한 계산 결과가 나올때 까지 블로킹함.
	 * Executors 클래스는 단일 쓰레드 실행자 뿐만 아니라 다양한 스레드 풀(FixedThreadPool, CachedThreadPool)을 지원함
	 * - 하지만 RxJava는 위와 같은 실행자를 활용하기 보다 RxJava에서 제공하는 스케줄러를 활요하도록 권장함
	 */
	void emitFromFuture() {
		Future<String> future = Executors.newSingleThreadExecutor().submit((Callable<String>) () -> {
			Thread.sleep(1000);
			return "Hello Future";
		});
		
		Observable<String> source = Observable.fromFuture(future);
		source.subscribe(System.out::println);
	}
	
	/**
	 * Publisher 는 자바 9의 표준임. Flow API의 일부. 아직 자바9가 정식 공개된 것은 아님.
	 * fromPublisher 사용법!
	 */
	void emitFromPublisher() {
		Publisher<String> publisher = (Subscriber<? super String> s) -> {
			s.onNext("Hello Obserable.fromPublisher()");
			s.onComplete();
		};
		
		Observable<String> source = Observable.fromPublisher(publisher);
		source.subscribe(System.out::println);
	}

	public static void main(String[] args) {

		Ex03ObserFrom demo = new Ex03ObserFrom();

		demo.emitFromArray();

		System.out.println("-------------------------------------");

		demo.emitFromIterable_List();

		System.out.println("-------------------------------------");

		demo.emitFromIterable_Set();

		System.out.println("-------------------------------------");

		demo.emitFromIterable_BlockingQueue();

		System.out.println("-------------------------------------");

		demo.emitFromCallable();

		System.out.println("비동기 확인용 출력 구문 수행!"); // 동시성 API 인 만큼 위 실행이 종료되야지만 나오는 것 같은데...
		
		System.out.println("-------------------------------------");

		demo.emitFromFuture();

		System.out.println("비동기 확인용 출력 구문 수행!"); // 동시성 API 인 만큼 위 실행이 종료되야지만 나오는 것 같은데...
		
		System.out.println("-------------------------------------");

		demo.emitFromPublisher();

		System.out.println("비동기 확인용 출력 구문 수행!"); // 동시성 API 인 만큼 위 실행이 종료되야지만 나오는 것 같은데...

	}// end of main()

}// end of class
