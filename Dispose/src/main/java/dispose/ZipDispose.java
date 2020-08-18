package dispose;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩处理
 *
 * @author fyy
 */
public class ZipDispose {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP
     *
     * @param filePath          压缩文件夹路径
     * @param outputStream      压缩文件输出流
     * @param keepPathStructure 是否保留原来的目录结构(true:保留目录结构 false:所有文件跑到压缩包根目录下)(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    public static void toZip(
            String filePath,
            OutputStream outputStream,
            boolean keepPathStructure
    ) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(outputStream);
            File sourceFile = new File(filePath);
            compress(sourceFile, zos, sourceFile.getName(), keepPathStructure);
        } catch (Exception e) {
            throw new RuntimeException("压缩处理失败！", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP
     *
     * @param fileList     需要压缩的文件列表
     * @param outputStream 压缩文件输出流
     */
    public static void toZip(
            List<File> fileList,
            OutputStream outputStream
    ) throws RuntimeException {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(outputStream);
            for (File file : fileList) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(file.getName()));
                int len;
                FileInputStream in = new FileInputStream(file);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("压缩处理失败！", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile        源文件
     * @param zipOutputStream   zip输出流
     * @param name              压缩后的名称
     * @param keepPathStructure 是否保留原来的目录结构(true:保留目录结构 false:所有文件跑到压缩包根目录下)(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     */
    private static void compress(
            File sourceFile,
            ZipOutputStream zipOutputStream,
            String name,
            boolean keepPathStructure
    ) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zipOutputStream.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zipOutputStream.write(buf, 0, len);
            }
            // Complete the entry
            zipOutputStream.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepPathStructure) {
                    // 空文件夹的处理
                    zipOutputStream.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zipOutputStream.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepPathStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zipOutputStream, name + "/" + file.getName(), true);
                    } else {
                        compress(file, zipOutputStream, file.getName(), false);
                    }
                }
            }
        }
    }
}
