package com.fl.web.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：FileUtils
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 09:27
 */
public class FileUtils {


    public static HttpServletResponse downLoadFiles(List<File> files, String zipPathName, HttpServletResponse response) throws Exception {
        // List<File> 作为参数传进来，就是把多个文件的路径放到一个list里面
        // 创建一个临时压缩文件
        // 临时文件可以放在CDEF盘中，但不建议这么做，因为需要先设置磁盘的访问权限，最好是放在服务器上，方法最后有删除临时文件的步骤
//        String zipPathName = "E:/export/tempFile.zip";
        File zipFile = new File(zipPathName + ".zip");
        zipFile.createNewFile();
        if (!zipFile.exists()) {
            zipFile.createNewFile();
        }
        response.reset();
        // response.getWriter()
        // 创建文件输出流
        FileOutputStream fous = new FileOutputStream(zipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fous);
        zipFile(files, zipOut);
        zipOut.close();
        fous.close();
        return downloadZip(zipFile, files, response);
    }


    /**
     * 把接受的全部文件打成压缩包
     */
    public static void zipFile(List files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpServletResponse downloadZip(File zipFile, List<File> files, HttpServletResponse response) throws Exception {
        if (zipFile.exists() == false) {
            System.out.println("待压缩的文件目录：" + zipFile + "不存在.");
        } else {
            OutputStream toClient = null;
            try {
                // 以流的形式下载文件。
                InputStream fis = new BufferedInputStream(new FileInputStream(zipFile.getPath()));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + new String(zipFile.getName().getBytes("GB2312"), "ISO8859-1"));
                toClient.write(buffer);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (toClient != null) {
                        toClient.flush();
                        toClient.close();
                    }
                    //删除压缩包文件
//                    File f = new File(zipFile.getPath());
                    if (zipFile.exists()) {
                        zipFile.delete();
                    }
                    //删除压缩包中所有文件
                    for (File file : files) {
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                } catch (Exception e) {
                    throw new Exception(e);
                }
            }
        }
        return response;
    }
}
