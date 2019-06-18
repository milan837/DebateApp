package com.group.milan.debate.debate.ActivityAuthentication.forget_password;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.group.milan.debate.debate.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPasswordFragment extends Fragment {
    @BindView(R.id.authentication_forgate_password_back_button)
    ImageView backButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication_forget_password,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView();
    }

    private void initView(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"back",Toast.LENGTH_LONG).show();
                if(getFragmentManager().getBackStackEntryCount() > 0 ){
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
    }


}
