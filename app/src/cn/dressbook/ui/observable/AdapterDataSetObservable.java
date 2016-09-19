package cn.dressbook.ui.observable;

import android.database.DataSetObserver;
import android.database.Observable;


public class AdapterDataSetObservable extends Observable<DataSetObserver> {
	
	
	@Override
	public void registerObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		super.registerObserver(observer);
	}

	@Override
	public void unregisterObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		super.unregisterObserver(observer);
	}

	/**
	 * Invokes {@link DataSetObserver#onChanged} on each observer. Called when
	 * the contents of the data set have changed. The recipient will obtain the
	 * new contents the next time it queries the data set.
	 */
	public void notifyChanged() {
		synchronized (mObservers) {
			// since onChanged() is implemented by the app, it could do
			// anything, including
			// removing itself from {@link mObservers} - and that could cause
			// problems if
			// an iterator is used on the ArrayList {@link mObservers}.
			// to avoid such problems, just march thru the list in the reverse
			// order.
			for (int i = mObservers.size() - 1; i >= 0; i--) {
				mObservers.get(i).onChanged();
			}
		}
	}

	public void notifyChangedApart(int position) {
		synchronized (mObservers) {
			// since onChanged() is implemented by the app, it could do
			// anything, including
			// removing itself from {@link mObservers} - and that could cause
			// problems if
			// an iterator is used on the ArrayList {@link mObservers}.
			// to avoid such problems, just march thru the list in the reverse
			// order.
			for (int i = mObservers.size() - 1; i >= 0; i--) {
				mObservers.get(i).onChanged();
			}
		}
	}

	/**
	 * Invokes {@link DataSetObserver#onInvalidated} on each observer. Called
	 * when the data set is no longer valid and cannot be queried again, such as
	 * when the data set has been closed.
	 */
	public void notifyInvalidated() {
		synchronized (mObservers) {
			for (int i = mObservers.size() - 1; i >= 0; i--) {
				mObservers.get(i).onInvalidated();
			}
		}
	}

	public void notifyInvalidatedApart(int position) {
		synchronized (mObservers) {
			for (int i = mObservers.size() - 1; i >= position; i--) {
				mObservers.get(i).onInvalidated();
			}
		}
	}
}
