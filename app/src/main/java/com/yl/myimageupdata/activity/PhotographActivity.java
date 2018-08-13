package com.yl.myimageupdata.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yl.myimageupdata.R;
import com.yl.myimageupdata.golbal.GlobalConstants;
import com.yl.myimageupdata.service.LoopsService;
import com.yl.myimageupdata.utils.LogUtil;
import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;
import com.yl.myimageupdata.widget.FileToZip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotographActivity extends AppCompatActivity implements View.OnClickListener {
    //回调标示
    private Uri imageUri;
    private File outputImage;
    private TextView mTv;
    private String mPath;
    private String str2;
    private String fileName;
    private RequestQueue mSingleQueue;
    private String str;
    private EditText ed;
    private EditText id;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograph);
        Button photograph = (Button) findViewById(R.id.photograph);
        Button subBtn = (Button) findViewById(R.id.subBtn);
        id = (EditText) findViewById(R.id.id);
        address = (EditText) findViewById(R.id.address);
        ed = (EditText) findViewById(R.id.ed);
        mTv = (TextView) findViewById(R.id.tv);
        ImageView backspace = (ImageView) findViewById(R.id.backspace);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("拍 照");
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSingleQueue = Volley.newRequestQueue(getApplicationContext());
        subBtn.setOnClickListener(this);
        photograph.setOnClickListener(this);
        startService(new Intent(getApplicationContext(), LoopsService.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent stopIntent = new Intent(getApplicationContext(), LoopsService.class);
        stopService(stopIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photograph:
                takePhone();
                break;
            case R.id.subBtn:
                if (!TextUtils.isEmpty(id.getText().toString().trim()) && !TextUtils.isEmpty(address.getText().toString().trim()) && !TextUtils.isEmpty(ed.getText().toString().trim())) {
                    String s = getExternalCacheDir() + "/" + str + ".zip";
                    File file = new File(s);
                    if (file.exists()) {
                        file.delete();
                    }
                    delEmptyData();
                } else {
                    Toast.makeText(getApplicationContext(), "设备id、地址、Ip不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void delEmptyData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        File CacheDir = new File(getExternalCacheDir() + "/" + str);
        PrefUtils.putString(getApplication(), "path", CacheDir + "");
        try {
            String[] CacheDirlist = CacheDir.list();
            if (CacheDirlist != null) {
                for (int i = 0; i < CacheDirlist.length; i++) {
                    File file = new File(CacheDir + "/" + CacheDirlist[i]);
                    if (file.length() == 0) {
                        File delfile = new File(CacheDir + "/" + CacheDirlist[i]);
                        delfile.delete();
                    }
                }
                upData();
            } else {
                subZipData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int x = 0;

    private void upData() {
        String sourceFilePath = new GlobalConstants().path;
        mTv.setText("客官，小二正在为您打包文件夹，请耐心等待...");
        LogUtil.i("QQQQQQQQQQQQQ", "开始压缩" + sourceFilePath);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        fileName = sdf.format(new Date());
        String zipFilePath = getExternalCacheDir() + "";
        boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath, fileName);
        if (flag) {
            LogUtil.i("QQQQQQQQQQQQQ", "压缩成功");
            x = 0;
            boolean b = DeleteFolder(sourceFilePath);
            if (b) {
                mTv.setText("旧文件删除成功，等待上传压缩文件...");
                subZipData();
            } else {
                mTv.setText("旧文件删除失败，请重试...");
            }
        } else {
            File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
            if (zipFile.exists()) {
                zipFile.delete();
            }
            if (x <= 2) {
                x++;
                LogUtil.i("QQQQQQQQQQQQQ=========", "压缩失败");
                upData();
            } else {
                mTv.setText("压缩失败");
            }

        }
    }


    //上传压缩文件
    private void subZipData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        String url = ed.getText().toString().trim(); //换成自己的测试url地址
        Map<String, String> params = new HashMap<>();
        String idText = id.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        if (TextUtils.isEmpty(idText) && TextUtils.isEmpty(addressText)) {
            Toast.makeText(getApplicationContext(), "设备id或地址不能为空", Toast.LENGTH_SHORT).show();
        } else {
            params.put("id", idText);
            params.put("address", addressText);
            List<File> f = new ArrayList<>();
            File file;
            String s = getExternalCacheDir() + "/" + str + ".zip";
            file = new File(s);
            if (!file.exists()) {
                Toast.makeText(getApplicationContext(), "图片" + s + "不存在，测试无效", Toast.LENGTH_SHORT).show();
                return;
            } else {
                LogUtil.i("QQQQQQQQQQQQQ--------0000000000---------------", file + "");
                f.add(file);
               /* MultipartRequest request = new MultipartRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.i("QQQQQQQQQQQQQ--------11111111111---------------", response.toString());
                        Log.d("YanZi", "success,response = " + response);
                        Toast.makeText(getApplicationContext(), "uploadSuccess,response = " + response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "ip地址错误 " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("YanZi", "error,response = " + error.getMessage());
                    }
                }, "f_file[]", f, params); //注意这个key必须是f_file[],后面的[]不能少
                LogUtil.i("QQQQQQQQQQQQQ-------------333333----------", request.toString());
                 mSingleQueue.add(request);*/
                mTv.setText("上传地址有误");
                Toast.makeText(getApplicationContext(), "上传地址有误", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }


    //调用相机拍照
    private void takePhone() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        File CacheDir = new File(getExternalCacheDir(), str);
        try {
            if (!CacheDir.exists()) {
                CacheDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        str2 = sdf2.format(new Date());
        //创建一个File对象用于存储拍照后的照片
        mPath = getExternalCacheDir() + "/" + str;
        //  PrefUtils.putString(getApplication(), "path", path);
        outputImage = new File(mPath, str2 + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断Android版本是否是Android7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(Utils.getContext(), "com.yl.myimageupdata", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Log.i("QQQQQQQQQQQQQ1111111", "开始压缩");
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeFile(outputImage.getPath(),
                            options);
                    // 压缩图片
                    bitmap = compressImage(bitmap, 200);
                    if (bitmap != null) {
                        // 保存图片
                        FileOutputStream fos = null;
                        fos = new FileOutputStream(outputImage);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        mTv.setText(mPath + "/" + str2 + ".jpg");
                    }
                } catch (Exception e) {
                    Log.i("QQQQQQQQQQQQQ2222", "开小差了" + e.getMessage());
                }
            } else {
                startPhotoZoom(imageUri);
            }

        }

    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 200);//图片输出大小
        intent.putExtra("outputY", 200);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, 3);
    }

    /**
     * 将图片image压缩成大小为 size的图片（size表示图片大小，单位是KB）
     *
     * @param image 图片资源
     * @param size  图片大小
     * @return Bitmap
     */
    private Bitmap compressImage(Bitmap image, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            // 重置baos即清空baos
            baos.reset();
            // 每次都减少10
            options -= 10;
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);

        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}