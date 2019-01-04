package PrecReactiveJavaProject.ch02;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Single 클래스는 RxJava 1.x 부터 존재하는 Obserable의 특수한 형태 이 클래스는 오직 1개의 데이터만 발행하도록
 * 한점함. 보통! 결과가 유일한 서버 API 호출에 유용히 사용!!!!
 * 
 * @author slicequeue
 *
 */
public class Ex04Single {

	void emitJust() {
		Single<String> source = Single.just("Hello Single");
		source.subscribe(System.out::println);
	}

	void emitSingle() {
		// 1. 기존 Observable 에서 Single 객체로 변환
		Observable<String> source = Observable.just("Hello Single");
		Single.fromObservable(source).subscribe(System.out::println);

		// 2. Observable single() 함수를 호출하여 Single 객체 생성
		Observable.just("Hello Single").single("default item").subscribe(System.out::println);

		// 2.1. Observable single() 함수를 호출하여 Single 객체 생성
		try {
			Observable.just("Hello Single", "Error").single("default item").subscribe(System.out::println, System.out::println);
		} catch (Exception e) {
//			System.out.println(e.getMessage()); // try catch 무쓸모....
		}
		// 3. Observable first() 함수를 호출해 Single 객체 생성
		String[] colors = { "Red", "Green", "Gold" };
		Observable.fromArray(colors).first("default item").subscribe(System.out::println);

		// 4. Observable empty obserable 에서 Single 객체 생성 - "default item" 나오는
		// 경우!
		Observable.empty().single("default item").subscribe(System.out::println);

		// 5. Observable take 함수에서 Single 객체 생성
		Observable.just(new Ex03ObserFrom.Order("ORD-1"), new Ex03ObserFrom.Order("ORD-2")).take(1)
				.single(new Ex03ObserFrom.Order("default order")).subscribe(System.out::println);

		try {
			Observable.just(new Ex03ObserFrom.Order("ORD-1"), new Ex03ObserFrom.Order("ORD-2")).take(2)
					.single(new Ex03ObserFrom.Order("default order")).subscribe(System.out::println, System.out::println);
		} catch (Exception e) {
//			System.out.println(e.getMessage()); // try catch 무쓸모...
		}

	}

	public static void main(String[] args) {
		Ex04Single demo = new Ex04Single();

		demo.emitJust();

		System.out.println("-------------------------------------");

		demo.emitSingle();

	}

}// end of class
