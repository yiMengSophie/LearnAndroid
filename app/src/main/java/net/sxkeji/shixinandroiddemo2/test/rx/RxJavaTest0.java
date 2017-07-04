package net.sxkeji.shixinandroiddemo2.test.rx;

import android.os.SystemClock;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <br/> Description:
 * <p>
 * <br/> Created by shixinzhang on 17/3/21.
 * <p>
 * <br/> Email: shixinzhang2016@gmail.com
 * <p>
 * <a  href="https://about.me/shixinzhang">About me</a>
 */

public class RxJavaTest0 {
    public static void main(String[] args) {
        String[] name = {"zhang", "shi", "xin", "haha"};
        Observable.create(new MySubscribe())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("complete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("回调所在线程 " + Thread.currentThread().getName());
                        System.out.println("next " + s);
                    }
                });
    }

    private static class MySubscribe implements Observable.OnSubscribe<String>{

        @Override
        public void call(final Subscriber<? super String> subscriber) {
            System.out.println("创建数据所在线程：" + Thread.currentThread().getName());
            SystemClock.sleep(10_000);
            for (int i = 0; i < 3; i++) {
                subscriber.onNext("Response from a slow network request: " + i);
            }
            subscriber.onCompleted();   //必须得调用 completed
        }
    }
}
