package me.tatarka.redux.rx2;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.tatarka.redux.Dispatcher;

public class FlowableDispatcher<A> extends Dispatcher<Flowable<A>, Disposable> {

    private final Consumer<A> dispatchAction;

    public FlowableDispatcher(final Dispatcher<A, ?> dispatcher) {
        dispatchAction = new Consumer<A>() {
            @Override
            public void accept(A action) {
                dispatcher.dispatch(action);
            }
        };
    }

    @Override
    public Disposable dispatch(Flowable<A> action) {
        return action.subscribe(dispatchAction);
    }
}
