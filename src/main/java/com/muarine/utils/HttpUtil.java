/*
 * RT MAP, Home of Professional MAP
 * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */

package com.muarine.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * HttpUtil
 * 同步和异步请求
 * 其中同步请求使用JDK8自带的URLConnection
 *
 * 异步请求使用httpclient-async异步组件
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2016 16/7/24 09:58
 * @since 0.1
 */
@SuppressWarnings("all")
public class HttpUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103");
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送HTTP请求
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 */
	public static String send(String url, Map<String, String> params, String method) {

		String paramsString = "";
		if (params == null || params.size() == 0) {
			paramsString = null;
		} else {
			// 解析Map2String
			Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
			final StringBuffer buffer = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				buffer.append(key).append("=").append(value).append("&");
			}
			paramsString = buffer.substring(0, buffer.length() - 1);
		}

		if (method.toUpperCase().equals("POST")) {
			return sendPost(url, paramsString);
		} else {
			url = paramsString == null || paramsString.length() < 1 ? url : url + "?" + paramsString;
			return sendGet(url);
		}
	}

	/**
	 * 发送post请求
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> params) {
		String paramsString = "";
		if (params == null || params.size() == 0) {
			paramsString = null;
		} else {
			// 解析Map2String
			Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
			StringBuffer buffer = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String key = entry.getKey();
				String value = entry.getValue();
				buffer.append(key).append("=").append(value).append("&");
			}
			paramsString = buffer.substring(0, buffer.length() - 1);
		}
		return sendPost(url, paramsString);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		System.out.println("url:" + url + "\nparam:" + param);
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
            if(url.startsWith("https")){
                // 默认信任所有证书
                HostnameVerifier hv = new HostnameVerifier() {
                    @Override
                    public boolean verify(String urlHostName, SSLSession session) {
                        System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                                + session.getPeerHost());
                        return true;
                    }
                };
                trustAllHttpsCertificates();
                HttpsURLConnection.setDefaultHostnameVerifier(hv);
            }
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements TrustManager,
            X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }


	// http https 异步请求

	/**
	 * 设置信任自定义的证书
	 * @param keyStorePath
	 * @param keyStorePass
	 * @return
	 */
	public static SSLContext custom(String keyStorePath , String keyStorePass) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, IOException, CertificateException {

		KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File(keyStorePath));
		try {
			trustStore.load(instream, keyStorePass.toCharArray());
		} finally {
			instream.close();
		}
		// Trust own CA and all self-signed certs
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
				.build();
		return sslContext;
	}

	/**
	 * 绕过验证
	 *
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() {
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSLv3");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			sc.init(null, new TrustManager[] { trustManager }, null);
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sc;
	}

	/**
	 * 异步GET请求
	 * @param url
     */
	public static void asyncSendGet(String url){
		final CloseableHttpAsyncClient httpclient = getCloseableHttpAsyncClient(url);

		try {
			httpclient.start();
			HttpGet request = new HttpGet(url);
			Future<HttpResponse> future = httpclient.execute(request, new FutureCallback<HttpResponse>() {
				@Override
				public void completed(HttpResponse httpResponse) {
					System.out.println("completed");
					try {
						System.out.println(InputStreamUtils.getStringFromInputStream(httpResponse.getEntity().getContent()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.close();
				}

				@Override
				public void failed(Exception e) {
					e.printStackTrace();
					this.close();
				}

				@Override
				public void cancelled() {
					System.out.println("the request cancelled.");
					this.close();
				}

				public void close(){
					try {
						httpclient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 抽取的公共方法
	 * @param url
	 * @return
     */
	private static CloseableHttpAsyncClient getCloseableHttpAsyncClient(String url) {
		CloseableHttpAsyncClient httpclient;
		if(url.startsWith("https")) {
			SSLContext sslContext = createIgnoreVerifySSL();
			// Allow TLSv1 protocol only
			SSLIOSessionStrategy sslSessionStrategy = new SSLIOSessionStrategy(
					sslContext,
					new String[]{"TLSv1"},
					null,
					SSLIOSessionStrategy.getDefaultHostnameVerifier());

			httpclient = HttpAsyncClients.custom()
					.setSSLStrategy(sslSessionStrategy)
					.build();
		}else{
			httpclient = HttpAsyncClients.custom().build();
		}
		return httpclient;
	}

	/**
	 * 异步发送POST请求
	 * @param url
	 * @param paramMap
	 * @param type 	JSON FORM
     */
	public static void asyncSendPost(String url , Map<String,String> paramMap , String type){
		final CloseableHttpAsyncClient httpclient = getCloseableHttpAsyncClient(url);

		try {
			httpclient.start();
			HttpPost post = new HttpPost(url);
			if(type.equals("JSON")){
				// json
				post.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
				// 将JSON进行UTF-8编码,以便传输中文
				String encoderJson = URLEncoder.encode(JSON.toJSONString(paramMap), HTTP.UTF_8);
				StringEntity se = new StringEntity(encoderJson , "text/json");
				se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
				post.setEntity(se);
			}else{
				if(paramMap != null && paramMap.size() > 0){
					// 表单提交
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					Iterator it = paramMap.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry<String ,String > entry = (Map.Entry<String, String>) it.next();
						nvps.add(new BasicNameValuePair(entry.getKey() , entry.getValue()));
					}
					post.setEntity(new UrlEncodedFormEntity(nvps , HTTP.UTF_8));
				}
			}

			Future<HttpResponse> future = httpclient.execute(post, new FutureCallback<HttpResponse>() {
				@Override
				public void completed(HttpResponse httpResponse) {
					System.out.println("completed");
					try {
						System.out.println(InputStreamUtils.getStringFromInputStream(httpResponse.getEntity().getContent()));
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.close();
				}

				@Override
				public void failed(Exception e) {
					e.printStackTrace();
					this.close();
				}

				@Override
				public void cancelled() {
					System.out.println("the request cancelled.");
					this.close();
				}

				public void close(){
					try {
						httpclient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

//			future.get();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
