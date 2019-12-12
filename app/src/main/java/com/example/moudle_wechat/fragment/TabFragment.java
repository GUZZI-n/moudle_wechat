package com.example.moudle_wechat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.moudle_wechat.MainActivity;
import com.example.moudle_wechat.R;
import com.example.moudle_wechat.utils.L;

public class TabFragment extends Fragment {
    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static interface OnTitleClickListener{
        void onClick(String title);
    }

    private OnTitleClickListener mListener;

    public void setOnTitleClickListener(OnTitleClickListener listener){
        mListener = listener;
    }

    //实现页面标题不同传入
    public static TabFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null){
            mTitle = arguments.getString(BUNDLE_KEY_TITLE,"");
        }
        L.d("onCreate,title = "+mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.d("onCreateView,title = "+mTitle);
        return inflater.inflate(R.layout.fragment_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);

        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取activity对象
               if (mListener != null){
                   mListener.onClick("change");
               }
            }
        });
    }

    @Override
    public void onDestroyView() {
        L.d("onDestroyView,title = "+mTitle);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        L.d("onDestroy,title = "+mTitle);
        super.onDestroy();
    }

    public void changeTitle(String title){
        if (!isAdded()){
            return;
        }//为了防止调用报错：空指针
        mTvTitle.setText(title);
    }
}
