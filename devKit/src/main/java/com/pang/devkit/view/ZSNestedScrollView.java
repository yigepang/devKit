package com.pang.devkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.pang.devkit.R;


/**
 * 由hoozy于2024/2/29 16:10进行创建
 * 描述：
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class ZSNestedScrollView extends NestedScrollView {
    /**
     * 顶部的view  id = activity_nested_view
     */
    private View topView;
    /**
     * 包裹TabLayout+RecyclerView 的 LinearLayout id = activity_nested_ll
     */
    private ViewGroup contentView;
    /**
     * 处理惯性滑动的工具类
     */
    private FlingHelperUtil mFlingHelper;
    /**
     * 记录当前自身已经滑动的距离
     */
    private double totalDy = 0;
    /**
     * 用于判断RecyclerView是否在fling
     */
    private boolean isRecyclerViewStartFling = false;
    /**
     * 记录当前滑动的y轴加速度
     */
    private int velocityY = 0;

    /**
     * 设置recyclerView是否滑动
     */
    private boolean determineRecyclerViewAble;

    /**
     * tabLayout 的父布局在上层布局的第几个
     */
    private int tabLayoutAt = 1;

    public ZSNestedScrollView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ZSNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZSNestScrollView);
        determineRecyclerViewAble = array.getBoolean(R.styleable.ZSNestScrollView_determineRecyclerViewAble, false);
        tabLayoutAt = array.getInteger(R.styleable.ZSNestScrollView_tabLayoutAt, 1);

        init(context);
    }

    public ZSNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mFlingHelper = new FlingHelperUtil(context);
        setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isRecyclerViewStartFling) {
                    totalDy = 0;
                    isRecyclerViewStartFling = false;
                }
                //scrollY 是 当前向上滑动了多少 0 就是一点没滑动 就是在顶部状态
                if (0 == scrollY) {
                }
                //v.measuredHeight() 就是屏幕高度
                if (topView.getMeasuredHeight() == scrollY) {
                    //滑动到底部以后 还有惯性让子类接着来滑动
                    dispatchChildFling();
                }
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    private void dispatchChildFling() {
        if (0 != velocityY) {
            //将惯性的加速度转换为具体的距离
            double splineFlingDistance = mFlingHelper.getSplineFlingDistance(velocityY);
            //举例解释：假设用力滑动一下 能滑动100个单位的距离，totalDy是外层ZSNestedScrollView已经滑动的距离
            // 假设是50 那么还有50 咋办呢 ，要让子布局（RecycleView）来滑动剩下的50
            if (splineFlingDistance > totalDy) {
                childFling(mFlingHelper.getVelocityByDistance(splineFlingDistance - totalDy));
            }
        }

        //重置变量
        totalDy = 0;
        velocityY = 0;
    }

    private void childFling(int velY) {
        if (null != contentView) {
            RecyclerView childRecyclerView = getChildRecyclerView(contentView);
            if (null != childRecyclerView) {
                childRecyclerView.fling(0, velY);
            }
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView && view.getClass() == RecyclerView.class) {
                return (RecyclerView) view;
            } else if (view instanceof ViewGroup) {
                ViewGroup childRecyclerView = (ViewGroup) getChildRecyclerView((ViewGroup) view);
                if (childRecyclerView != null) {
                    return (RecyclerView) childRecyclerView;
                }
            }
        }
        return null;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            this.velocityY = 0;
        } else {
            isRecyclerViewStartFling = true;
            this.velocityY = velocityY;
        }
    }

    /**
     * view 加载完成后执行
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = ((ViewGroup) getChildAt(0)).getChildAt(0);
        contentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(tabLayoutAt);
    }

    /**
     * 参数	解释
     * target	触发嵌套滑动的 view
     * dx	表示 view 本次 x 方向的滚动的总距离，单位：像素
     * dy	表示 view 本次 y 方向的滚动的总距离，单位：像素
     * consumed	输出：表示父布局消费的水平和垂直距离。
     * type	触发滑动事件的类型：其值有
     * ViewCompat. TYPE_TOUCH
     * ViewCompat. TYPE_NON_TOUCH
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //如果能继续向上滑动，就滑动
        boolean canScroll = dy > 0 && getScrollY() < topView.getMeasuredHeight();
        if (canScroll) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams lp = contentView.getLayoutParams();
        lp.height = getMeasuredHeight();
        // 调整contentView的高度为屏幕高度，这样ZSNestedScrollView总高度就是屏幕高度+topView的高度
        // 因此往上滑动 滑完topView后,TabLayout就卡在顶部了，因为ZSNestedScrollView滑不动了啊，就这么高
        // 接着在滑就是其内部的RecyclerView去滑动了
        contentView.setLayoutParams(lp);
    }
}
