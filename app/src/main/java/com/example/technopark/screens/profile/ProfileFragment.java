package com.example.technopark.screens.profile;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.technopark.App;
import com.example.technopark.R;
import com.example.technopark.profile.service.ProfileService;
import com.example.technopark.util.ThreadPoster;

public class ProfileFragment extends Fragment implements View.OnLongClickListener, Button.OnClickListener {

    private ProfilePresenter presenter;

    public ProfileFragment() {
    }

    public static Fragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //noinspection ConstantConditions
        presenter = new ProfilePresenter(getProfileService(), getMainThreadPoster());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProfileViewMvpImpl view = new ProfileViewMvpImpl(inflater, container, this);
        //((BaseActivity)getActivity()).setBarVisible(View.VISIBLE);
        presenter.bindView(view);
        return view.getRootView();


//        myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
////        Back button
//        Bundle bundle = this.getArguments();
//        if (bundle == null) {
//            hideBackButton(v);
//        }
//
//
////        set up good scroll
//        ScrollView scrollView = (ScrollView) v.findViewById(R.id.profile_scroll_view);
//        OverScrollDecoratorHelper.setUpOverScroll(scrollView);
//
////        Exit
//        v.findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog exitDialog = new Dialog(getActivity(), R.style.ExitDialogAnimation);
//                exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
//                exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 0, 0, 0)));
//                exitDialog.setContentView(R.layout.exit_popup_view);
//                exitDialog.setCancelable(true);
//                exitDialog.show();
//
//                exitDialog.findViewById(R.id.exitButtonApprove).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                      TODO: make log out
//                        exitDialog.dismiss();
//                        // ((BaseActivity)getActivity()).setAuthorizationView();
////                        Toast.makeText(getActivity(), "log out in progress", Toast.LENGTH_SHORT).show();
//
//
//                    }
//                });
//                exitDialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        exitDialog.dismiss();
//                    }
//                });
//            }
//        });
//
//
//        androidx.appcompat.widget.Toolbar toolbar = v.findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//
//        TextView textView = v.findViewById(R.id.back_to_group);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//
//        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        String text = textView.getText().toString();
        presenter.copyTextViewText(text, getActivity());
        return true;
    }

    @Override
    public void onClick(View v) {
        presenter.onGroupButtonClicked();
    }

    private void hideBackButton(View v) {
        androidx.appcompat.widget.Toolbar toolBar = v.findViewById(R.id.toolbar);
        toolBar.setNavigationIcon(null);
        TextView textView = v.findViewById(R.id.back_to_group);
        textView.setVisibility(View.INVISIBLE);
    }

    private ProfileService getProfileService() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideProfileService();
    }

    private ThreadPoster getMainThreadPoster() {
        App app = (App) getActivity().getApplication();
        assert app != null;
        return app.provideMainThreadPoster();
    }
}
