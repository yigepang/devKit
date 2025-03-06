package com.pang.devkit.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Utils {
    /**
     * bitmap转base64
     *
     * @param bitmap 图片
     * @return 返回
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * base64转为bitmap
     *
     * @param base64Data 数据
     * @return 返回图片
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


//    public static Boolean base64ToFile(String path ,String base64Data)  throws IOException{
//
//        //base64解码 对字节数组字符串进行base64解码并生成文件
//        byte[] byt = Base64.decode(base64Data, Base64.DEFAULT);
//        for (int i = 0; i < byt.length; i++) {
//            //调整异常数据
//            if(byt[i] < 0){
//                byt[i] += 256;
//            }
//        }
//
//        OutputStream out = null;
//        InputStream input = new ByteArrayInputStream(byt);
//        try {
//            //生成指定格式的文件
//            out = new FileOutputStream(path);
//            byte[] buff = new byte[1024];
//            int len = 0;
//            while((len = input.read(buff)) != -1){
//                out.write(buff,0,len);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            out.flush();
//            out.close();
//        }
//        return true;
//    }


    //Base64字符串转为文件，base64为字符串，filaName为保存的文件名称，savePath为保存路径
    public static Boolean base64ToFile(String base64, String fileName, String savePath) {
        //前台在用Ajax传base64值的时候会把base64中的+换成空格，所以需要替换回来。
        //有的后台传的数据还有image:content..., 转换的时候都需要替换掉,转换之前尽量把base64字符串检查一遍
        base64 = base64.replaceAll(" ", "+");

        File file = null;
        //创建文件目录
        String filePath = savePath;
        File dir = new File(filePath);
        if (!dir.exists()) {
            boolean a = dir.mkdirs();
            Log.d("TAG", "base64ToFile: 文件不存在,创建" + a);
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);//base64编码内容转换为字节数组
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteInputStream);
            Log.d("TAG", "base64ToFile: ${bytes}==" + bytes.length);

            file = new File(filePath + "/" + fileName);
            Log.d("TAG", "filePath:==" + file.getPath());
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

}