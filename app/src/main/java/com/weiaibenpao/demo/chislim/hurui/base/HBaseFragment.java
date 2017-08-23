package com.weiaibenpao.demo.chislim.hurui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.weiaibenpao.demo.chislim.hurui.httpClient.ApiClient;
import com.weiaibenpao.demo.chislim.hurui.httpClient.HHttpInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by lenovo on 2017/4/10.
 */

public class HBaseFragment extends Fragment{
    public Activity mActivity ;
    public HHttpInterface apiStores = ApiClient.getModel().getService();
    private List<Call> calls;
    private Unbinder unbinder ;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mActivity = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callCancel();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);

    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }
}
