package com.mnn.mydream.cosmetology.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class PermissionUtil {

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        if (!isPermissionsGranted(activity, permissions) && permissions != null) {
            //若权限没有开启，则请求权限
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    public static boolean isPermissionsGranted(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        boolean granted = true;
        for (String permission : permissions) {
            granted = granted && ((ContextCompat.checkSelfPermission(activity, permission)
                    == PackageManager.PERMISSION_GRANTED));
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(activity, "若不授予权限，程序可能会无法使用！", Toast.LENGTH_SHORT).show();
            }
        }
        return granted;
    }
}
