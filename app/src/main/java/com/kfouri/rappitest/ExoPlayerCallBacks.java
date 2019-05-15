package com.kfouri.rappitest;

public interface ExoPlayerCallBacks {

    void callbackObserver(Object obj);

    public interface playerCallBack {
        void onItemClickOnItem(Integer albumId);

        void onPlayingEnd();
    }
}
