package com.mnn.mydream.cosmetology.activity;

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
import com.mnn.mydream.cosmetology.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

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

    private static final int NO_LOGINFLAG = -10000;//返回 没有登陆
    private static final int YES_LOGINFLAG = 10002;//登陆成功


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ConfigDataMethod.bmobInit(getApplicationContext());
//        CrashReport.initCrashReport(getApplicationContext(), "35ac317edb", true);
        //默认用户名密码
//        Intent intent = getIntent();
//        //从Intent当中根据key取得value
//        if (intent != null) {
//            String name = intent.getStringExtra("name");
//            String pwd = intent.getStringExtra("pwd");
//            _emailText.setText(name);
//            _passwordText.setText(pwd);
//        }
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess(email, password);
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
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
            this.setResult(YES_LOGINFLAG);
//            ConfigDataMethod.toastShow(this, "登录成功，正在跳转", AppMsg.STYLE_ALERT);
//            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent();
//            intent.setClass(this, HomeActivity.class);
//            startActivity(intent);
            finish();
        } else {
            ToastUtils.showToast(getApplication(), "登录失败,请检查用户名和密码", true);
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
            this.setResult(NO_LOGINFLAG);
            finish();
        }
        return false;
    }


}
