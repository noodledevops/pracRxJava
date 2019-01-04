package PrecReactiveJavaProject.ch02;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * just() 함수 - 데이터를 발행하는 가장 쉬운 방법은 기존의 자료구조를 사용하는 것입니다. 
 * 				함수는 인자로 넣은 데이터를 차례로 발행하기 위해 Observable을 생성.  실제 데이터 발행은 subscribe 함수를 호출해야만 실행
 * 				한 개의 값을 넣을 수도 있고 여러개 값을 넣을 수도 있다. 
 * @author slicequeue
 */
public class Ex01ObserJust {
	
	public void emit() {
		Observable.just("Hello", "RxJava 2!!").subscribe(System.out::println);
	}

	public void emit2() {
		Observable.just(1,2,3,4,5,6).subscribe(System.out::println);
	}
	
	public void emit3() {
		Observable<String> source  = Observable.just("RED", "GREEN", "YELLOW");
		
		Disposable d = source.subscribe(
				v -> System.out.println(String.format("onNext(): %s", v)), 
				err -> System.out.println(String.format("onError(): %s", err.getMessage())),
				() -> System.out.println("onComlete()"));
		System.out.println(String.format("isDisposed(): %s", d.isDisposed()));
	}

	public static void main(String[] args) {
		Ex01ObserJust demo = new Ex01ObserJust();
//		demo.emit();
//		demo.emit2();
		demo.emit3();
	}
}// end of class