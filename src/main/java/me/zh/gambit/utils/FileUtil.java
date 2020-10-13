package me.zh.gambit.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;

/**
 * @author ziheng
 * @since 2020.10.12
 */
public class FileUtil {
    /**
     * move File or Folder
     *
     * @param oldPath old path
     * @param newPath new path
     * @throws IOException IO exception
     */
    public static void moveTo(String oldPath,String newPath) throws IOException {
        copyFile(oldPath,newPath);
        deleteFile(oldPath);
    }

    /**
     * delete file or folder
     *
     * @param filePath file path
     */
    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory() ) {
            File[] list = file.listFiles();

            for (File f : list) {
                deleteFile(f.getAbsolutePath()) ;
            }
        }
        file.delete();
    }

    /**
     * copy file
     *
     * @param oldPath old path
     * @param newPath new path
     * @throws IOException IO exception
     */
    public static void copyFile(String oldPath ,String newPath ) throws IOException {
        File oldFile = new File(oldPath) ;
        if  (oldFile.exists())  {

            if(oldFile.isDirectory()){
                File newPathDir = new File(newPath);
                newPathDir.mkdirs();
                File[] lists = oldFile.listFiles() ;
                if(lists != null && lists.length > 0 ){
                    for (File file : lists) {
                        copyFile(file.getAbsolutePath(), newPath.endsWith(File.separator) ? newPath + file.getName() : newPath + File.separator + file.getName()) ;
                    }
                }
            }else {
                InputStream inStream = new FileInputStream(oldFile);
                FileOutputStream fs = new FileOutputStream(newPath);
                write2Out(inStream ,fs) ;
                inStream.close();
            }
        }
    }

    /**
     * rename file
     *
     * @param file file's object
     * @param name name
     * @return renamed file's object
     */
    public static File renameFile(File file , String name ){
        String fileName = file.getParent()  + File.separator + name ;
        File dest = new File(fileName);
        file.renameTo(dest) ;
        return dest ;
    }

    /**
     * zip files
     *
     * @param zipFileName zip's name
     * @param files file need to be zip
     * @return ZIP ojbect
     * @throws Exception exceptions
     */
    public static File createZips(String zipFileName, File... files) throws Exception {
        File outFile = new File(zipFileName) ;
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(outFile));
            bo = new BufferedOutputStream(out);

            for (File file : files) {
                zip(out, file, file.getName(), bo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bo.close();
            } finally {
                out.close(); // 输出流关闭
            }
        }
        return outFile;
    }

    /**
     * create a zip
     *
     * @param zipFileName zip file's name
     * @param inputFile files need to be zip
     * @return ZIP
     * @throws Exception exception
     */
    public static File createZip(String zipFileName, File inputFile) throws Exception {
        File outFile = new File(zipFileName) ;
        ZipOutputStream out = null;
        BufferedOutputStream bo = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(outFile));
            bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bo.close();
            } finally {
                out.close(); // 输出流关闭
            }
        }
        return outFile;
    }

    private static void zip(ZipOutputStream out, File f, String base,BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if ( fl == null ||  fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/"));
            }else{
                for (int i = 0; i < fl.length; i++) {
                    zip(out, fl[i], base + "/" + fl[i].getName(), bo);
                }
            }

        } else {
            out.putNextEntry(new ZipEntry(base));
            System.out.println(base);
            BufferedInputStream bi = new BufferedInputStream(new FileInputStream(f));

            try {
                write2Out(bi,out) ;
            } catch (IOException e) {
                //Ignore
            }finally {
                bi.close();// 输入流关闭
            }
        }
    }

    private static void write2Out(InputStream input , OutputStream out) throws IOException {
        byte[] b = new byte[1024];
        int c = 0 ;
        while ( (c = input.read(b)) != -1 ) {
            out.write(b,0,c);
            out.flush();
        }
        out.flush();
    }

    /**
     * use path to load Yaml
     * @param path path
     * @return Yml's FileConfiguration object
     */
    public static FileConfiguration loadYml(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                System.out.println("错误:" + e.toString());
            }
        }
        FileConfiguration YML = YamlConfiguration.loadConfiguration(file);
        DumperOptions yamlOptions = null;
        try {
            Field f = YamlConfiguration.class.getDeclaredField("yamlOptions");
            f.setAccessible(true);
            yamlOptions = new DumperOptions() {
                public void setAllowUnicode(boolean allowUnicode) {
                    super.setAllowUnicode(false);
                }
                public void setLineBreak(DumperOptions.LineBreak lineBreak) {
                    super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                }
            };
            yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            f.set(YML, yamlOptions);
        }catch (ReflectiveOperationException ex) {
            System.out.println("错误:" + ex.toString());
        }
        return YamlConfiguration.loadConfiguration(new File(path));
    }

    /**
     * save Yml
     *
     * @param Filec yml
     * @param file file
     */
    public static void saveYml(FileConfiguration Filec, File file) {
        try {
            Filec.save(file);
        }catch (IOException e) {
            System.out.println("错误:" + e.toString());
        }
    }

    /**
     * load yml
     *
     * @param file file
     * @return YML's FileConfiguration
     */
    public static FileConfiguration loadYml(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                System.out.println("错误:" + e.toString());
            }
        }
        FileConfiguration YML = YamlConfiguration.loadConfiguration(file);
        DumperOptions yamlOptions = null;
        try {
            Field f = YamlConfiguration.class.getDeclaredField("yamlOptions");
            f.setAccessible(true);
            yamlOptions = new DumperOptions() {
                public void setAllowUnicode(boolean allowUnicode) {
                    super.setAllowUnicode(false);
                }
                public void setLineBreak(DumperOptions.LineBreak lineBreak) {
                    super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                }
            };
            yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            f.set(YML, yamlOptions);
        }catch (ReflectiveOperationException ex) {
            System.out.println("error:" + ex.toString());
        }
        return YML;
    }

}
