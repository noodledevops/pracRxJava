package PrecReactiveJavaProject.ch02;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * create() 함수
 * - onNext, onComplete, onError 같은 알림을 개발자가 직접 호출해야함.
 * - 개발자가 무언가를 직접하는 느낌이 강한 함수.
 * 구독자에게 데이터를 발생하려면 onNext() 함수를 호출해야하며 모든 데이터를 발행한 후에는 반드시 onComplete() 함수를 호출해야한다.
 * 
 * - create() 는 RxJava에 익숙한 사용자만 활용하도록 권고!
 *	 사실 create()를 사용하지 않고 다른 팩토리 함수를 활용하여 같은 효과를 낼 수 있음
 * 	 그래도 사용할 경우 아래 사항을 지켜야 함
 * 		1. Observable이 구독(dispose)되었을 때 등록된 콜백을 모두 해제해야함. 그렇지 않으면 잠재적 메모리누수 발생!
 * 		2. 구독자가 구독하는 동안 onNext, onComplete 이벤트를 호출 해야만 함
 * 		3. 에러가 발생했을 때는 오직 onError 이벤트로만 에러를 전달해야 함
 * 		4. 배압(back pressure)을 직접 처리해야 함
 * 
 * 
 * @author slicequeue
 */
public class Ex02ObserCreate {

	public static void main(String[] args) {
		Observable<Integer> source = Observable.create((ObservableEmitter<Integer> emitter) -> {
			// 100 데이터 발행
			emitter.onNext(100);
			// 200 데이터 발행
			emitter.onNext(200);
			// 300 데이터 발행
			emitter.onNext(300);
			// 350 데이터 발행
			emitter.onNext(350);
			
			// 모든 데이터를 발행한 후 반드시 onComplete 호출!!!
			emitter.onComplete();
		});
		
		source.subscribe(data -> System.out.println(String.format("Result: %s", data)));
	}
	
}// end of class
