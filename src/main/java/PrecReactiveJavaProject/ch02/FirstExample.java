package PrecReactiveJavaProject.ch02;

import io.reactivex.Observable;

public class FirstExample {

	public void emit() {
		Observable.just("Hello", "RxJava 2!!").subscribe(System.out::println);
	}

	public void emit2() {
		Observable.just(1,2,3,4,5,6).subscribe(System.out::println);
	}

	public static void main(String[] args) {
		FirstExample demo = new FirstExample();
//		demo.emit();
		demo.emit2();
	}

}// end of class
