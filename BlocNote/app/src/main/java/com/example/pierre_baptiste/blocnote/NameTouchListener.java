package com.example.pierre_baptiste.blocnote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class NameTouchListener extends RecyclerView.SimpleOnItemTouchListener implements RecyclerView.OnItemTouchListener {

    public interface OnNameClickListener {
        void onSingleTap(String name);

        void onDoubleTap(String name);

        void onLongPress(String name);
    }

    public NameTouchListener(Context context, final OnNameClickListener listener) {
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /**
             * Notified when a single-tap occurs.
             * <p>
             * Unlike {@link GestureDetector.OnGestureListener#onSingleTapUp(MotionEvent)}, this
             * will only be called after the detector is confident that the user's
             * first tap is not followed by a second tap leading to a double-tap
             * gesture.
             *
             * @param e The down motion event of the single-tap.
             * @return true if the event is consumed, else false
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                listener.onSingleTap(findNameUnder(e.getX(), e.getY()));
                return true;
            }

            /**
             * Notified when a double-tap occurs.
             *
             * @param e The down motion event of the first tap of the double-tap.
             * @return true if the event is consumed, else false
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                listener.onDoubleTap(findNameUnder(e.getX(), e.getY()));
                return true;
            }

            /**
             * Notified when a long press occurs with the initial on down {@link MotionEvent}
             * that trigged it.
             *
             * @param e The initial on down motion event that started the longpress.
             */
            @Override
            public void onLongPress(MotionEvent e) {
                listener.onLongPress(findNameUnder(e.getX(), e.getY()));
            }
        });
    }

    private final GestureDetector gestureDetector;

    private RecyclerView recyclerView;

    /**
     * <p>Silently observe and/or take over touch events sent to the RecyclerView
     * before they are handled by either the RecyclerView itself or its child views.</p>
     * <p>The onInterceptTouchEvent methods of each attached OnItemTouchListener will be run
     * in the order in which each listener was added, before any other touch processing
     * by the RecyclerView itself or child views occurs.</p>
     *
     * @param e MotionEvent describing the touch event. All coordinates are in
     *          the RecyclerView's coordinate system.
     * @return true if this OnItemTouchListener wishes to begin intercepting touch events, false
     * to continue with the current behavior and continue observing future events in
     * the gesture.
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        recyclerView = rv;
        return gestureDetector.onTouchEvent(e);
    }

    private String findNameUnder(float x, float y) {
        final View view = recyclerView.findChildViewUnder(x, y);
        final int position = recyclerView.getChildAdapterPosition(view);
        return ((NameAdapter) recyclerView.getAdapter()).getItem(position);
    }
}
