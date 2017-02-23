package com.example.chenlong.mvppractice.demo1.mvp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chenlong.mvppractice.R;
import com.example.chenlong.mvppractice.demo1.mvp.presenter.LoginPresenterImpl;
import com.example.chenlong.mvppractice.demo1.mvp.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView
{

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.et_account)
    EditText mAccount;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.button_login)
    Button mLogin;
    private LoginPresenterImpl mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenterImpl(this);

        mLogin.setOnClickListener(v -> mLoginPresenter.OnLogin());
    }

    @Override
    public void showProgress()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress()
    {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void loginSuccess()
    {
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed()
    {
        Toast.makeText(this, "登录失败!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getAccount()
    {
        return mAccount.getText().toString();
    }

    @Override
    public String getPassword()
    {
        return mPassword.getText().toString();
    }
}
