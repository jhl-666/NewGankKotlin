package com.ljhdemo.newgank.common.utils;

import com.ljhdemo.newgank.common.http.BaseModel;
import com.ljhdemo.newgank.common.http.MsgException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulerUtil {
    /**
     * 在RxJava的使用过程中我们会频繁的调用subscribeOn()和observeOn(),通过Transformer结合
     * Observable.compose()我们可以复用这些代码
     *
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static final <T> ObservableTransformer<BaseModel<T>, T> preApplySchedulers() {
        return new ObservableTransformer<BaseModel<T>, T>() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.flatMap(new Function<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(BaseModel<T> result) {
                        if (result.getCode() == 200) {//检查是否掉接口成功了
                            T data = result.getData();
                            return createData(data);//成功，剥取我们要的数据，把BaseModel丢掉
                        } else {
                            return Observable.error(new MsgException(result.getMessage()));//出错就返回服务器错误
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> subscriber) throws Exception {
                try {
                    if (data == null)//onNext不能传入空值
                        subscriber.onNext((T) new Object());
                    else subscriber.onNext(data);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}