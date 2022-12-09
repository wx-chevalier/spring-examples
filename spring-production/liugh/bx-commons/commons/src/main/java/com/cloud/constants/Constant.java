package com.cloud.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liugh
 */
public class Constant {

    public static final int BYTE_BUFFER = 1024;
    public static final int BUFFER_MULTIPLE = 10;

    public static final String PASSWORD = "123456";

    //存储用户登录前的位置
    public static Map<String, String> RETURN_URL = new HashMap<>();

    //验证码过期时间(90秒)
    public static final Long PASS_TIME = 90 *1000L;

    public static class FilePostFix {
        public static final String ZIP_FILE = ".zip";

        public static final String[] IMAGES = {"jpg", "jpeg", "JPG", "JPEG", "gif", "GIF", "bmp", "BMP", "png"};
        public static final String[] ZIP = {"ZIP", "zip", "rar", "RAR"};
        public static final String[] VIDEO = {"mp4", "MP4", "mpg", "mpe", "mpa", "m15", "m1v", "mp2", "rmvb"};
        public static final String[] APK = {"apk", "exe"};
        public static final String[] OFFICE = {"xls", "xlsx", "docx", "doc", "ppt", "pptx"};
        public static final String STYLEFILE=".sld";

    }

    public static class FileType {
        public static final String FILE_IMG = "image";
        public static final String FILE_ZIP = "zip";
        public static final String FILE_VEDIO = "video";
        public static final String FILE_APK = "apk";
        public static final String FILE_OFFICE = "office";
        public static final String FILE_OTHER = "other";
        public static final String FILE_IMG_DIR = "/img/";
        public static final String FILE_ZIP_DIR = "/zip/";
        public static final String FILE_VEDIO_DIR = "/video/";
        public static final String FILE_APK_DIR = "/apk/";
        public static final String FILE_OFFICE_DIR = "/office/";
        public static final String FILE_OTHER_DIR = "/other/";
    }
}
