package com.hzkans.crm.modules.activity.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author wsh
 * @Title: FileUtil
 * @ProjectName dongyin-CRM
 * @date 2018/12/21 14:15
 */

public class FileUtil {
    public static final String CHAR_SET_UTF8 = "UTF-8";
    public static final String CHAR_SET_GBK = "GBK";
    public static final int NEW_LINE_CHAR_LENGTH = System.getProperty("line.separator").getBytes().length;
    private static final String FILE_PATH = "deploy/data/www/static/upload/";

    public FileUtil() {
    }

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        return !file.exists()?false:(file.isFile()?deleteFile(fileName):deleteDirectory(fileName));
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if(file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public static void destroyFile(String fileName) {
        String filePath = "/deploy/data/www/static/upload/" + fileName;
        File file = new File(filePath);
        if(file.isFile() && file.exists()) {
            file.delete();
        }

    }

    public static boolean deleteDirectory(String dir) {
        if(!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }

        File dirFile = new File(dir);
        if(dirFile.exists() && dirFile.isDirectory()) {
            boolean flag = true;
            File[] files = dirFile.listFiles();
            if(files != null) {
                for(int i = 0; i < files.length; ++i) {
                    if(files[i].isFile()) {
                        flag = deleteFile(files[i].getAbsolutePath());
                        if(!flag) {
                            break;
                        }
                    } else {
                        flag = deleteDirectory(files[i].getAbsolutePath());
                        if(!flag) {
                            break;
                        }
                    }
                }
            }

            return !flag?false:dirFile.delete();
        } else {
            return false;
        }
    }

    public static boolean deleteDirFiles(String dir) {
        boolean flag = true;
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        if(files != null) {
            for(int i = 0; i < files.length; ++i) {
                if(files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if(!flag) {
                        break;
                    }
                } else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if(!flag) {
                        break;
                    }
                }
            }
        }

        return flag;
    }

    public static boolean deleteDir(File dir) {
        if(dir.isDirectory()) {
            String[] children = dir.list();

            for(int i = 0; i < children.length; ++i) {
                boolean success = deleteDir(new File(dir, children[i]));
                if(!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static void write(String fileName, String charSet, String content) {
        write(fileName, content, charSet, false);
    }

    public static void write(String fileName, String content, String charSet, boolean append) {
        try {
            File file = new File(fileName);
            File fileParent = file.getParentFile();
            if(!fileParent.exists()) {
                boolean exist = fileParent.mkdirs();
                if(!exist) {
                    throw new RuntimeException("生成文件路径" + fileParent.getAbsolutePath() + "失败!");
                }
            }

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charSet));

            try {
                bw.write(content);
            } finally {
                bw.close();
            }

        } catch (Exception var11) {
            throw new RuntimeException("写入文件失败", var11);
        }
    }

    public static long getFileRowCount(String fileName, String charSet, long lineByteSize, int newLineCharLength) {
        try {
            long rowCount = 0L;
            if(lineByteSize > 0L) {
                long len = (new File(fileName)).length();
                rowCount = (len + (long)newLineCharLength) / (lineByteSize + (long)newLineCharLength);
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));

                try {
                    while(br.readLine() != null) {
                        ++rowCount;
                    }
                } finally {
                    br.close();
                }
            }

            return rowCount;
        } catch (Exception var13) {
            throw new RuntimeException("读取文件失败", var13);
        }
    }

    public static List<String> readFileToList(String fileName, String charSet, long startLineIndex, long lineByteSize, long maxLine, int newLineCharLength) {
        try {
            List<String> list = new ArrayList();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
            String buf = null;
            long lineNum = 0L;
            int lineIndex = 0;

            try {
                if(lineByteSize > 0L) {
                    br.skip(startLineIndex * (lineByteSize + (long)newLineCharLength));
                } else {
                    while((long)lineIndex < startLineIndex) {
                        br.readLine();
                        ++lineIndex;
                    }
                }

                while(++lineNum <= maxLine && (buf = br.readLine()) != null) {
                    list.add(buf);
                }
            } finally {
                br.close();
            }

            return list;
        } catch (Exception var19) {
            throw new RuntimeException("读取文件失败", var19);
        }
    }

    public static List<String> unZip(String path) throws IOException {
        int count;
        int index;
        int buffer = 256;
        File src = new File(path);
        String savepath = src.getAbsolutePath();
        savepath = savepath.substring(0, savepath.lastIndexOf(".")) + File.separatorChar;
        (new File(savepath)).mkdir();
        List<String> unZipFiles = new ArrayList();
        ZipInputStream zis = null;

        try {
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(path)));

            String temp;
            for(ZipEntry entry = null; (entry = zis.getNextEntry()) != null; unZipFiles.add(temp)) {
                byte[] data = new byte[buffer];
                temp = entry.getName();
                index = temp.lastIndexOf("/");
                if(index != -1) {
                    temp = temp.substring(index + 1);
                }

                temp = savepath + temp;
                File f = new File(temp);
                f.createNewFile();
                BufferedOutputStream bos = null;

                try {
                    bos = new BufferedOutputStream(new FileOutputStream(f), buffer);

                    while((count = zis.read(data, 0, buffer)) != -1) {
                        bos.write(data, 0, count);
                    }

                    bos.flush();
                } finally {
                    if(bos != null) {
                        bos.close();
                    }

                }
            }
        } catch (Exception var27) {
            throw new RuntimeException("解压文件失败", var27);
        } finally {
            if(zis != null) {
                try {
                    zis.close();
                } catch (IOException var25) {
                    ;
                }
            }

        }

        return unZipFiles;
    }

    public static void unZip(String srcFile, String dest, boolean deleteFile) throws Exception {
        File file = new File(srcFile);
        if(!file.exists()) {
            throw new Exception("解压文件不存在!");
        } else {
            ZipFile zipFile = new ZipFile(file);
            Enumeration e = zipFile.entries();

            while(true) {
                while(e.hasMoreElements()) {
                    ZipEntry zipEntry = (ZipEntry)e.nextElement();
                    if(zipEntry.isDirectory()) {
                        String name = zipEntry.getName();
                        name = name.substring(0, name.length() - 1);
                        File f = new File(dest + name);
                        f.mkdirs();
                    } else {
                        File f = new File(dest + zipEntry.getName());
                        f.getParentFile().mkdirs();
                        f.createNewFile();
                        InputStream is = zipFile.getInputStream(zipEntry);
                        FileOutputStream fos = new FileOutputStream(f);
                        int length;
                        byte[] b = new byte[1024];

                        while((length = is.read(b, 0, 1024)) != -1) {
                            fos.write(b, 0, length);
                        }

                        is.close();
                        fos.close();
                    }
                }

                if(zipFile != null) {
                    zipFile.close();
                }

                if(deleteFile) {
                    file.deleteOnExit();
                }

                return;
            }
        }
    }

    public static Boolean unZip(String inPath, String outPath) {
        String unzipfile = inPath;

        try {
            ZipInputStream zin = new ZipInputStream(new FileInputStream(unzipfile), Charset.forName("GBK"));

            while(true) {
                ZipEntry entry;
                while((entry = zin.getNextEntry()) != null) {
                    File directory;
                    if(entry.isDirectory()) {
                        directory = new File(outPath, entry.getName());
                        if(!directory.exists() && !directory.mkdirs()) {
                            System.exit(0);
                        }

                        zin.closeEntry();
                    } else {
                        directory = new File(entry.getName());
                        FileOutputStream fout = new FileOutputStream(outPath + directory.getPath());
                        DataOutputStream dout = new DataOutputStream(fout);
                        byte[] b = new byte[1024];
                        boolean var9 = false;

                        int len;
                        while((len = zin.read(b)) != -1) {
                            dout.write(b, 0, len);
                        }

                        dout.close();
                        fout.close();
                        zin.closeEntry();
                    }
                }

                return Boolean.valueOf(true);
            }
        } catch (IOException var10) {
            var10.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    public static List<String> readFileToList(String fileName, String charSet) {
        try {
            List<String> list = new ArrayList();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charSet));
            String buf = null;

            try {
                while((buf = br.readLine()) != null) {
                    list.add(buf);
                }
            } finally {
                br.close();
            }

            return list;
        } catch (Exception var9) {
            throw new RuntimeException("读取文件失败", var9);
        }
    }
}
