package com.huhx0015.gotimer;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class rxplayground {

    @Test
    public void test2() {
        Observable first = Observable.just(1, 2, 3);
        Observable second = Observable.just(4, 5, 6);

        Observable.merge(first, second)
                .subscribe(item -> System.out.println(item));

    }

    @Test
    public void test() {
        Single<String> assets = Single.just("assets");
        Single<String> featured = Single.just("featured");
        Single<String> sections = Single.just("sections");
        Single<String> campaigns = Single.just("campaigns");
        Single<String> dt = Single.just("dt");

        StringBuilder builder = new StringBuilder();
        final TEstBuilder bld = new TEstBuilder();

        Observable base = Observable.zip(assets.toObservable(),
                featured.toObservable(),
                sections.toObservable(),
                (a, f, s) -> {

                    bld.append("Assets: " + a);
                    bld.append(", Featured: " + f);
                    bld.append(", Sections: " + s);
                    System.out.println("Stringbuilder has: " + bld.current);
                    return bld;
                });

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(str -> {
            System.out.println("string is being emitted: " + str);
        });

        behaviorSubject.onNext("STRING 1");


//        Single<String> test = Single.fromObservable(behaviorSubject).flatMap(str -> {
//            System.out.println("got to the flatmap");
//            return Single.just(str);
//        }).doOnError(e -> System.out.println("doOnError"));
//
//        CompositeDisposable disposable = new CompositeDisposable();
//        disposable.add(test.subscribe(str -> System.out.println("test string: " + str)));
//        disposable.dispose();
        behaviorSubject.onNext("STRING 2");

        //behaviorSubject.firstOrError().subscribe(str -> { System.out.println("First or error: " + str);});

//        behaviorSubject.flatMap(s -> {
//            return Single.just(s);
//        }).doOnError(e -> System.out.println("error")).subscribe(str -> {
//            System.out.println("subscriber 2: " + str);
//        });



        behaviorSubject.onNext("STRING 3");

        //
//        Observable.mergeDelayError(Observable.zip(
//                assets.toObservable(),
//                featured.toObservable(),
//                sections.toObservable(),
//                        (a, f, s) -> {
//                            bld.append("Assets: " + a);
//                            bld.append(", Featured: " + f);
//                            bld.append(", Sections: " + s);
//                            System.out.println("Stringbuilder has: " + bld.current);
//                            return bld;
//                        }),
//                campaigns.toObservable().map(
//                        str -> {
//                            bld.append(str);
//                            System.out.println("campaigns");
//                            throw new IllegalArgumentException();
//                            //return bld;
//                        }),
//                dt.toObservable().map(
//                        str -> {
//                            System.out.println("dt");
//                            bld.append(str);
//                            return bld;
//                        }))
//                .subscribe(str -> {
//                    System.out.println("str is: " + str.getClass());
//                    System.out.println("subscriber sees: " + ((TEstBuilder)str).current);
//                }, error -> {
//                    // one case: assets/featured/sections fails, abandon all hope
//                    // another case: campaigns only fails, still show base + DT
//                    // another case: DT only fails, still show base + campaigns
//                    // another case: both campaigns and dt fail, still show base content
//
//                    System.out.println("Error! " + error);
//                });

//        Observable.mergeDelayError(base,
//                campaigns.toObservable().map(str -> {
//                    bld.append(str);
//                    return bld;
//                }),
//                dt.toObservable().map(str -> {
//                    bld.append(str);
//                    return bld;
//                }))
//                .subscribe(str -> {
//                    System.out.println("str is: " + str.getClass());
//                    System.out.println("subscriber sees: " + ((TEstBuilder)str).current);
//                }, error -> { });

        System.out.println("testzz");
    }

    private static class TEstBuilder {
        public String current = "";
        public void append(String appends) {
            current = current + appends;
        }
        @Override
        public String toString() {
            return "YOU CALLED TOSTRING!!!!!!!!!!";
        }
    }
}