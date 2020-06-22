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
     * @return String
     * */
    public static String uploadFile(MultipartFile file) throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("\"yyyy-MM-dd\"");
        Date date = new Date();
        String str = sdf.format(date);
        //获取根目录
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String originName = file.getOriginalFilename();
        System.out.println("根目录"+path);
        String targetPath = path.getAbsolutePath() + "/img/";
        System.out.println("根目录"+targetPath);
        String suffix = originName.substring(originName.indexOf('.'));
        String newName = UUID.randomUUID().toString().replace("-", "");
        newName = newName + suffix;
        File targetFile = new File(targetPath);
        if (file == null)
            return "";
        if (!targetFile.exists())
            targetFile.mkdir();
        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(targetPath + newName));
            stream.write(file.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stream != null) stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(targetPath+newName);
        return targetPath+newName;
    }
}
