import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {

    public static void main(String[] args) throws Exception {
        unZipFile("D:\\2019-10-14-1-1111.zip", "D:\\");
    }

    /**
     * 解压zip格式的压缩文件到指定位置
     *
     * @param zipFileName      压缩文件 全路径
     * @param dstDirectoryPath 解压目录
     * @throws Exception
     */
    public static boolean unZipFile(String zipFileName, String dstDirectoryPath)
            throws Exception {
        // 乱码问题以及文件内容重复问题
        System.setProperty("sun.zip.encoding",
                System.getProperty("sun.jnu.encoding"));
        try {
            File dstDiretory = new File(dstDirectoryPath);
            if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
                dstDiretory.mkdirs();
            }
            File f = new File(zipFileName);
            if ((!f.exists()) && (f.length() <= 0)) {
                throw new Exception("要解压的文件不存在!");
            }
            ZipFile zipFile = new ZipFile(zipFileName, Charset.forName("gbk")); // 处理中文文件名乱码的问题
            String strPath, gbkPath, strtemp;
            File tempFile = new File(dstDirectoryPath);
            strPath = tempFile.getAbsolutePath();
            Enumeration<?> e = zipFile.entries();
            while (e.hasMoreElements()) {
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath = zipEnt.getName();
                // 判断是目录还是文件 如果是文件直接解析和上传
                // 如果是目录 创建文件夹 递归去处理文件
                if (zipEnt.isDirectory()) {
                    strtemp = strPath + File.separator + gbkPath;
                    File dir = new File(strtemp);
                    dir.mkdirs();
                    continue;
                } else {
                    // 读写文件
                    InputStream is = zipFile.getInputStream(zipEnt);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;

                    // 建目录 直接处理怕出现重名 覆盖
                    String strsubdir = gbkPath;
                    for (int i = 0; i < strsubdir.length(); i++) {
                        if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                            String temp = strPath + File.separator
                                    + strsubdir.substring(0, i);
                            File subdir = new File(temp);
                            if (!subdir.exists())
                                subdir.mkdir();
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(strtemp);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    int c;
                    while ((c = bis.read()) != -1) {
                        bos.write((byte) c);
                    }
                    bos.close();
                    fos.close();

                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
