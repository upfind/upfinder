package cn.upfinder.upfinder.Adapter;

/**
 * 为RecycleView添加点击事件
 *
 * @author upfinder
 * @project OnRecyclerViewListener
 * @date 2016-08-03-15:15
 */
public interface OnRecyclerViewListener {
    void onItemClick(int position);

    boolean onItemLongClick(int position);
}
