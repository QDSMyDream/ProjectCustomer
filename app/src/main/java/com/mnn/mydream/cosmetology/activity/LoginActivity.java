package com.mnn.mydream.cosmetology.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mnn.mydream.cosmetology.R;
import com.mnn.mydream.cosmetology.bean.User;
import com.mnn.mydream.cosmetology.utils.ConfigDataMethod;
import com.mnn.mydream.cosmetology.utils.Constons;
import com.mnn.mydream.cosmetology.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.functions.Consumer;

/**
 * 创建人 :MyDream
 * 创建时间：2020/5/3 18:18
 * 类描述：管理员登陆activity
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getMissions();

        ConfigDataMethod.bmobInit(getApplicationContext());

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void getMissions() {

        //RX权限获取
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET,
                Manifest.permission.CHANGE_NETWORK_STATE

        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //申请的权限全部允许
//                    Toast.makeText(HomeActivity.this, "允许了权限!", Toast.LENGTH_SHORT).show();
                } else {
                    //只要有一个权限被拒绝，就会执行
                    ToastUtils.showToast(getBaseContext(), "未授权权限，部分功能不能使用", false);
                }
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        onLoginSuccess(email, password);

    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String name, String pwd) {
        _loginButton.setEnabled(true);
        selectUser(name, pwd);

    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
//        ConfigDataMethod.toastShow(this, "Please enter the correct mailbox and password!", AppMsg.STYLE_GULES);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || email.length() < 3) {
            _emailText.setError("账户名不能小于三位");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("密码为4-10位字符");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    //登录
    public void selectUser(final String name, final String pwd) {

        User bu2 = new User();
        bu2.setUsername(name);
        bu2.setPassword(pwd);
        bu2.login(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                isUser(e, name, pwd);
            }
        });


    }

    private void isUser(BmobException e, String name, String pwd) {
        if (e == null) {

            ToastUtils.showToast(getApplication(), "登录成功", true);
            progressDialog.dismiss();
            this.setResult(Constons.RESULT_LONGIN_SUCCESS);
            finish();
        } else {
            ToastUtils.showToast(getApplication(), "登录失败,请检查用户名和密码", false);

            progressDialog.dismiss();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.setResult(Constons.RESULT_LONGIN_FAIL);
            finish();
        }
        return false;
    }


}
