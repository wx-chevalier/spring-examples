package com.cloud;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class RegisterConfigCenter {

    public static void main(String[] args) {

//        System.out.println(System.getProperty("user.dir").replace("\\","/")+"/gfc-core-server/register-config-center/src/main/java/com/cloud/");
        String resource = RegisterConfigCenter.class.getResource("").toString().replace("target/classes","src/main/java");
//        String path = resource+ "nacos/bin/startup.cmd -m standalone";
        String path = resource.replace("file:/","") + "nacos/bin/startup.cmd -m standalone";
        InputStream inputStream = null;
        try {
            Process child =  Runtime.getRuntime().exec("cmd.exe /c "+path);
            inputStream = child.getInputStream();
            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (IOException e) {
            System.err.println(e);
        }finally {
            if(null !=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
