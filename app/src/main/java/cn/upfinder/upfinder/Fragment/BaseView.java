package cn.upfinder.upfinder.Fragment;

/**
 * Created by Upfinder on 2016/7/17 0017.
 * 需View层（Fragment）实现此接口 将presenter传递给View
 *
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
}
