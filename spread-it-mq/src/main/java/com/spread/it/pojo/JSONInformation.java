package com.spread.it.pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Random;

/**
 * @author lsy
 * @apiNote 用於生成JSON文件信息
 */
public class JSONInformation implements Serializable {
    private static  final   String  exeFilePath="D:/MQTT PROJECT/Bmp2EslJson.exe";

    private static final Random random=new Random(10010);
    private final String eslType;
    // -f：文件名
    private String  fileName;
    // -p：密码
    private Long password;
    // -m：mac地址
    private String macAddr;
    // -s：json消息ID
    private Integer jsonId;
    // -id：图片ID
    private Integer imageId;

    private Boolean isZip;

    public JSONInformation(String fileName, String macAddr, Boolean isZip,String eslType) {
        this.fileName = fileName;
        this.macAddr = macAddr;
        this.isZip = isZip;
        this.setPassword(System.currentTimeMillis());
        this.setJsonId(generateJsonFileId());
        this.setImageId(generateImageId());
        this.eslType=eslType;
    }

    public String getEslType() {
        return eslType;
    }

    public String getExeFilePath() {
        return exeFilePath;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public Integer getJsonId() {
        return jsonId;
    }

    public void setJsonId(Integer jsonId) {
        this.jsonId = jsonId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Boolean getZip() {
        return isZip;
    }

    public void setZip(Boolean zip) {
        isZip = zip;
    }


    /**
     * -id：图片ID
     * 生成圖片ID
     * @return
     */
    public int generateImageId(){
        return random.nextInt();
    }

    /**
     * -s：json消息ID
     * 生成JSON 文件的ID
     * @return
     */
    public int generateJsonFileId(){
        return random.nextInt(1001011);
    }

    /**
     * -z：压缩模式，y表示压缩，n表示不压缩
     * @param isZip
     * @return
     */
    public String isZip(Boolean isZip){
        if (isZip){
            return "y";
        }
        return "n";
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("com.spread.it.pojo.JSONInformation{");
        sb.append("exeFilePath='").append(exeFilePath).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", imageId=").append(imageId);
        sb.append(", isZip=").append(isZip);
        sb.append(", jsonId=").append(jsonId);
        sb.append(", macAddr='").append(macAddr).append('\'');
        sb.append(", password=").append(password);
        sb.append(", zip=").append(getZip());
        sb.append('}');
        return sb.toString();
    }

    public void generateJsonFile(){
        StringBuffer buffer=new StringBuffer();
        buffer.append(exeFilePath).append(" ")
                .append("-f")
                .append(" ")
                .append(this.fileName)
                .append(" ")
                .append("-t")
                .append(" ")
                .append(this.eslType)
                .append(" ")
                .append("-p")
                .append(" ")
                .append(this.password)
                .append(" ")
                .append("-m")
                .append(" ")
                .append(this.macAddr)
                .append(" ")
                .append("-id")
                .append(" ")
                .append(this.imageId)
                .append(" ")
                .append("-s")
                .append(" ")
                .append(this.jsonId)
                .append(" ")
                .append("-z")
                .append(" ")
                .append(isZip(this.isZip));
        String cmdStr = buffer.toString();
        System.out.println(cmdStr);
        Runtime runtime=Runtime.getRuntime();
        try {
            Process exec = runtime.exec(cmdStr);
            InputStreamReader inputStreamReader = new InputStreamReader(exec.getInputStream());
            BufferedReader br=new BufferedReader(inputStreamReader);
            String line=null;
            StringBuffer b=new StringBuffer();
            while ((line=br.readLine())!=null){
                b.append(line+"\n");
            }
            System.out.println(b.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
