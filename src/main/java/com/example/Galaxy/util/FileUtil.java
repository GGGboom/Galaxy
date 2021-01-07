package com.example.Galaxy.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUtil {
    /*
     * @param file
     * @param String
     * @return String
     * */
    public static String uploadFile(MultipartFile file, String userId) throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("\"yyyy-MM-dd\"");
        Date date = new Date();
        //处理  "字符
        String str = sdf.format(date).replace("\"", "");
        //获取resources目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String originName = file.getOriginalFilename();
        String targetPath = path.getAbsolutePath() + "/static/img";
        String suffix = originName.substring(originName.indexOf('.'));
        String newName = UUID.randomUUID().toString().replace("-", "");

        newName = newName + suffix;
        targetPath = targetPath + "/" + userId + "/" + str + "/";

        String resourcePath = File.separator + "img" + File.separator + userId + File.separator + str + File.separator;
        File targetDir = new File(targetPath);
        if (file == null)
            return "";
        if (!targetDir.exists())
            targetDir.mkdirs();
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(targetPath + newName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "http://localhost:4396/" + resourcePath + newName;
    }
}
