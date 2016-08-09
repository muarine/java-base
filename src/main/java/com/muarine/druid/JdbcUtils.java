/*
 *
 *  *
 *  *  * RT MAP, Home of Professional MAP
 *  *  * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 *  *  * as indicated by the @author tags. All rights reserved.
 *  *  * See the copyright.txt in the distribution for a
 *  *  * full listing of individual contributors.
 *  *
 *
 */

package com.muarine.druid;

import com.alibaba.druid.pool.DruidDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * JdbcUtils
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2016 16/7/26 23:58
 * @since 0.1
 */
@SuppressWarnings("all")
public class JdbcUtils {

	// 数据库用户名
	private static final String USERNAME = "root";
	// 数据库密码
	private static final String PASSWORD = "hue";
	// 驱动信息
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	// 数据库地址
	private static final String URL = "jdbc:mysql://192.168.14.71:3306/data_collect";
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;

    private final static JdbcUtils INSTANCE = Singleton.jdbcUtils;

    public static class Singleton{

        private static JdbcUtils jdbcUtils = new JdbcUtils();

    }

	private JdbcUtils() {
		try {

            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(DRIVER);
            druidDataSource.setUrl(URL);
            druidDataSource.setUsername(USERNAME);
            druidDataSource.setPassword(PASSWORD);
            //配置初始化大小、最小、最大
            druidDataSource.setInitialSize(1);
            druidDataSource.setMinIdle(1);
            druidDataSource.setMaxActive(10);
            //配置获取连接等待超时的时间
            druidDataSource.setMaxWait(60000);
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
            //配置一个连接在池中最小生存的时间，单位是毫秒
            druidDataSource.setMinEvictableIdleTimeMillis(300000);
            druidDataSource.setValidationQuery("SELECT x");
            druidDataSource.setTestWhileIdle(true);
            druidDataSource.setTestOnBorrow(false);
            druidDataSource.setTestOnReturn(false);
            //打开PSCache，并且指定每个连接上PSCache的大小
            druidDataSource.setPoolPreparedStatements(true);
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

            druidDataSource.setFilters("stat");
            druidDataSource.init();

            connection = druidDataSource.getConnection();

		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	/**
	 * 通过反射机制查询单条记录
	 * 
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params, Class<T> cls) throws Exception {
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// 通过反射机制创建一个实例
			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true);
				field.set(resultObject, cols_value);
			}
		}
		this.releaseConn();
		return resultObject;

	}

    public List findList(Object objClass, String sql, List params)
            throws Exception {
        List<Object> info = new ArrayList<Object>();
        //获得Class对象
        Class c = objClass.getClass();
        try{
            int index = 1;
            pstmt = connection.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            resultSet = pstmt.executeQuery();
            Class type = null ; //属性类型
            while(resultSet.next()){
                //创建一个实例
                objClass = c.newInstance();

                //获取所有的字段名称
                List<String> list = getKeys(c);

                Method method = null;//声明Method对象

                for (int i = 0; i < list.size(); i++) {
                    String key = list.get(i);
                    String mName = getSetMethodName(key); //组装set方法名称
                    String typeName = c.getDeclaredField(key).getType().getName();  //获取字段类型名称
                    /*
                     * 判断字段类型
                     */
                    if(typeName.equals("java.lang.Integer")){
                        type = Integer.class; //赋值属性类型
                        method = c.getMethod(mName, type); //获得Method实例
                        method.invoke(objClass, resultSet.getInt(key));  //调用该set方法
                    }else if(typeName.equals("java.lang.String")){
                        type = String.class;
                        method = c.getMethod(mName, type);
                        method.invoke(objClass, resultSet.getString(key));
                    }
                }
                info.add(objClass);
            }
        }catch(Exception e){
            System.out.println("访问数据方法实现类findList方法出错");
            e.printStackTrace();
        }finally{
            this.releaseConn();
        }
        return info;
    }

    public static List<String> getKeys(Class<?> c){
        List<String> list = new ArrayList<String>();
        try{
            //根据Class的静态方法获取所以字段名称、不包括继承字段
            Field[] fs = c.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                list.add(fs[i].getName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 组装set方法
     * @param columnName 字段名
     * @return
     */
    private static String getSetMethodName(String columnName) {
        return "set" + columnName.substring(0, 1).toUpperCase()
                + columnName.toLowerCase().substring(1);
    }

    /**
	 * 增加、删除、改
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {
		boolean flag = false;
		int result = -1;
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		this.releaseConn();
		return flag;
	}

	/**
	 * 释放数据库连接
	 */
	private void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}

    public void test(String sql) throws SQLException {

        pstmt = connection.prepareStatement(sql);

        ResultSet resultSet = pstmt.executeQuery();

        while(resultSet.next()){


            int id = resultSet.getInt("id");
            Date date = resultSet.getDate("date");
            String appid = resultSet.getString("appid");
            String tag = resultSet.getString("tag");
            String url = resultSet.getString("url");
            int page = resultSet.getInt("page");
            String state = resultSet.getString("state");
            Date create_time = resultSet.getDate("create_time");

            System.out.println(id + "..." + date.toString() + "..." + appid + "..." + tag + "..." + url + "..."
                    + page + "..." + state + "..." + create_time.toString());
        }


    }

	public static void main(String[] args) throws SQLException {

        String sql = "SELECT * FROM source_setting";
        for (int i = 0; i < 10; i++) {
            JdbcUtils.INSTANCE.test(sql);
        }



	}

}
