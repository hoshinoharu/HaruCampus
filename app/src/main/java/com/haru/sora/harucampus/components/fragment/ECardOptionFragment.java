package com.haru.sora.harucampus.components.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.service.UserService;
import com.haru.sora.harucampus.vo.User;
import com.haru.sora.harucampus.vo.UserManager;
import com.haru.tools.HLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2017/1/8.
 */

public class ECardOptionFragment extends HFragment implements View.OnClickListener {


    private TextView txtVw_queryUserInfo ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_eacrd_options, container)  ;
        this.contentView = view ;
        this.txtVw_queryUserInfo = (TextView) this.findViewById(R.id.txtVw_queryUserInfo);
        this.init();
        return view ;
    }
    public void  init(){
        this.txtVw_queryUserInfo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == this.txtVw_queryUserInfo){
            User user = UserManager.getUser() ;
            UserService service = UserService.getUserService() ;
            service.queryInfo(user, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    HLog.e("TAG", response.body().string());
                }
            });
        }
    }
}
