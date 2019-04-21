package com.spread.it.service;

import com.spread.it.pojo.JSONInformation;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通過圖片信息生成JSON文件
 */
@Service
public class FileResolveBusiness {

    /**
     * 根據圖片信息創建JSON文件
     */
    public  void createJsonFile(String fileName,String macAddr,Boolean isZip){
        try {
            //1. 獲取文件path address
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources(fileName);
            //2. 判斷物理地址上是否存在此文件
           if (resources.hasMoreElements()){
               File file = new File(resources.nextElement().getFile());
               if (file.exists()&&file.isFile()&&!file.isDirectory()){

                   ImageInputStream inputStream=new FileImageInputStream(file);
                    //3. 讀取圖片信息
                   BufferedImage image = ImageIO.read(inputStream);
                   Map<String,Integer> imageInfo=new ConcurrentHashMap<>();

                   imageInfo.put("width",image.getWidth());
                   imageInfo.put("height",image.getHeight());
                   JSONInformation information = new JSONInformation(file.getName(), "0C23CAF98100 ", true, "e29");
                   information.generateJsonFile();
                   information.setFileName(fileName);
                   information.setMacAddr(macAddr);
                   information.setZip(isZip);
               }else {
                   throw  new FileNotFoundException();
               }
           }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
