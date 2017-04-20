package com.choosecity.choosecity.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.choosecity.choosecity.bean.Messages;
import com.choosecity.choosecity.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

/**
 * Http请求的工具类
 * 
 * @author 石云鹤
 * 
 */
public class HttpUtils
{
	private static final String TAG = "HttpUtils";
	public static final String STATUS_NETWORK = "network_available";
	private static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(11000);
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) {
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
						   AsyncHttpResponseHandler res) {
		client.get(urlString, params, res);
	}

	public static void get(String urlString, JsonHttpResponseHandler res)

	{
		client.get(urlString, res);
	}

	public static void get(String urlString, RequestParams params,
						   JsonHttpResponseHandler res)

	{
		client.get(urlString, params, res);
	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler)

	{
		client.get(uString, bHandler);
	}
	public static void post(Context context, String urlString, RequestParams params ,boolean again) {
		post(context,urlString,params,null, again);
	}
	public static void post(Context context, String urlString, RequestParams params) {
		post(context,urlString,params,null, false);
	}
	/**
	 * 网络交互，
	 * @param context
	 * @param urlString
	 * @param params
     * @param listener
     */
	public static void post(Context context, String urlString, RequestParams params,
							OnResultListener listener, boolean again) {
		post(context,urlString,params,0,listener,null,again);
	}
	public static void post(Context context, String urlString, RequestParams params,
							OnResultListener listener ) {
		post(context,urlString,params,0,listener,null,false);
	}
	/**
	 * 网络交互，
	 * @param context
	 * @param urlString
	 * @param params
	 * @param listener
	 */
	public static void post(Context context, String urlString, RequestParams params,
							OnResultListener listener, ProgressDialog progressDialog, boolean again) {
		post(context,urlString,params,0,listener,progressDialog,again);
	}
	public static void post(Context context, String urlString, RequestParams params,
							OnResultListener listener, ProgressDialog progressDialog) {
		post(context,urlString,params,0,listener,progressDialog,false);
	}
	/**
	 * 网络交互，
	 * @param context
	 * @param urlString
	 * @param params
	 * @param listener
	 */
	public static void post(Context context, String urlString, RequestParams params,
							int resquestCode, OnResultListener listener, boolean again) {
		post(context,urlString,params,resquestCode,listener,null,again);
	}
	public static void post(Context context, String urlString, RequestParams params,
							int resquestCode, OnResultListener listener) {
		post(context,urlString,params,resquestCode,listener,null,false);
	}
	public static void post(Context context, String urlString, RequestParams params,
							final int resquestCode, final OnResultListener listener, final ProgressDialog progressDialog, boolean again) {
		post( context,  urlString,  params,
		 resquestCode,   listener,   progressDialog,null,again);
	}
	public static void post(Context context, String urlString, RequestParams params,
							final int resquestCode, final OnResultListener listener, final ProgressDialog progressDialog) {
		post( context,  urlString,  params,
				resquestCode,   listener,   progressDialog,null,false);
	}
	/**
	 * 网络交互，有progressDialog
	 * @param context
	 * @param urlString
	 * @param params
	 * @param listener
	 */
	public static void post(final Context context, final String urlString, final RequestParams params,
							final int resquestCode, final OnResultListener listener, final ProgressDialog progressDialog, final View view,final boolean again) {

		if(!NetUtils.isConnected(context)){
			if (progressDialog!=null){
				if (progressDialog.isShowing())
				progressDialog.dismiss();
			}
			if (view!=null){
				view.setEnabled(true);
			}
			Toast.makeText(context,  context.getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
			listener.onFailure(resquestCode, context.getResources().getString(R.string.net_error));
			return;
		}
		LogUtil.e(TAG, "post: "+urlString+"?"+params.toString());
		if (view!=null){
			view.setEnabled(false);
		}
		if (progressDialog!=null){
			if (!progressDialog.isShowing())
				progressDialog.show();
		}
		client.post(urlString, params, new TextHttpResponseHandler() {
			@Override
			public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

				if (view!=null){
					view.setEnabled(true);
				}
				if (progressDialog!=null){
					if (progressDialog.isShowing())
						progressDialog.dismiss();
				}
				if (s!=null){
					LogUtil.e(TAG, "onFailure: "+ s);
				}
				if (again){
					post( context,  urlString,  params,
					  resquestCode,   listener,   progressDialog,   view,  again);
				}else {
					if (listener!=null)
					listener.onFailure( resquestCode,""+"加载失败≡(▔﹏▔)≡");
				}


			}

			@Override
			public void onSuccess(int i, Header[] headers, String s) {

				if (progressDialog!=null){
					if (progressDialog.isShowing())
						progressDialog.dismiss();
				}
				if (view!=null){
					view.setEnabled(true);
				}

				LogUtil.e(TAG, "onSuccess: "+s );
				Messages messages = JsonUtils.getMessages(s);
				if (messages.getStates()==1){
					if (listener!=null)
					listener.onSuccess( resquestCode,JsonUtils.JsonParse(messages));
				}else {
					if (listener!=null)
						listener.onAlert( resquestCode,messages.getMsg());
				}
			}
		});

	}


public static void calcle(Context context){
	client.cancelRequests(context,true);
}

	private static boolean isSuccess = false;

	/**
	 * 异步上传文件
	 *
	 * @param path
	 *            :文件路径
	 * @param url
	 *            :上传的url
	 */
	public static boolean uploadFile(final Context context, String path, String url, String filename,
									 final ProgressDialog progress) throws Exception {
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/ce.docx");
		if (file.exists()) {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("uploadFile", file);
			params.put("name", filename);

			// 设置进度条最大值
			progress.setMax(Integer.parseInt(file.length() + ""));

			// 上传文件
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					// 上传成功后要做的工作
					progress.dismiss();
					Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
					// progress.setProgress(0);
					isSuccess = true;
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					progress.dismiss();
					Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
					isSuccess = false;
				}

				@Override
				public void onProgress(long bytesWritten, long totalSize) {
					int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
					// 上传进度显示
					progress.setProgress(count);
					super.onProgress(bytesWritten, totalSize);
				}

				@Override
				public void onRetry(int retryNo) {
					super.onRetry(retryNo);
					// 返回重试次数
				}
			});

		} else {
			progress.dismiss();
			Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
		}
		return isSuccess;

	}

}
