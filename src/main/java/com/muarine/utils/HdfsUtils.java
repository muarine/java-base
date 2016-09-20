/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import static org.apache.hadoop.fs.FileSystem.get;

/**
 * com.rtmap.wcp.utils.HdfsUtils
 * HDFS 写入文件
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 16/9/5
 * @since 1.0
 */
public class HdfsUtils {

    private static Map<String, String> context = new HashMap<String, String>();
    private static Configuration conf = null;

    static {

        loadResource();

    }

    private static void loadResource() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config/hdfs");
        Iterator<String> it = resourceBundle.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            String value = resourceBundle.getString(key);
            context.put(key , value);
        }
    }

    public static void createNewFile(String path, String content, FileSystem fs) throws Exception{
        Path d_path = new Path(path);
        FSDataOutputStream os = null;
        if(fs.exists(d_path)){
            try {
                os = fs.append(d_path);
                os.write(content.getBytes("UTF-8"));
            } catch (Exception e) {
                appendFileContent(path, content);
            }

        }else{
            InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
            //输出流
            fs = FileSystem.get(URI.create(path), getConf());
            OutputStream out = fs.create(new Path(path), new Progressable() {
                @Override
                public void progress() {
                    System.out.println("上传完一个设定缓存区大小容量的文件！");
                }
            });
            //连接两个流，形成通道，使输入流向输出流传输数据
            IOUtils.copyBytes(in, out, 4096, true);

        }
        if(os != null){
            os.close();
        }
    }

    public static void appendFileContent(String path, String content) throws Exception{

        FileSystem fileSystem = FileSystem.newInstance(getConf());
        Path d_path = new Path(path);
        if(fileSystem.exists(d_path)){
            byte[] bytes = readHDFSFile(path);
            FSDataOutputStream os = fileSystem.create(d_path);
            os.write(bytes);
            os.write("\n".getBytes("UTF-8"));
            os.write(content.getBytes("UTF-8"));
            os.close();
        }else{
            createNewFile(path, content, fileSystem);
        }

    }

    /*
     * read the hdfs file content
     * notice that the dst is the full path name
     */
    public static byte[] readHDFSFile(String dst) throws Exception
    {
        Configuration conf = getConf();
        FileSystem fs = get(conf);

        // check if the file exists
        Path path = new Path(dst);
        if ( fs.exists(path) )
        {
            FSDataInputStream is = fs.open(path);
            // get the file info to create the buffer
            FileStatus stat = fs.getFileStatus(path);
            // create the buffer
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            is.readFully(0, buffer);

            is.close();
            fs.close();

            return buffer;
        }
        else
        {
            throw new Exception("the file is not found .");
        }
    }

    //文件系统连接到 hdfs的配置信息
    public static Configuration getConf(){
        Configuration conf = new Configuration();
        // 这句话很关键，这些信息就是hadoop配置文件中的信息
//        conf.set("mapred.job.tracker", context.get("mapred.job.tracker"));
        conf.set("fs.defaultFS", context.get("fs.default.name"));
        conf.set("dfs.support.append", context.get("dfs.support.append"));
        return conf;
    }

}
