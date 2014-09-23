package com.icld.mt.autoupdate.exceptions;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;

public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static byte TYPE_NETWORK = 0x01;
	public final static byte TYPE_SOCKET = 0x02;
	public final static byte TYPE_HTTP_CODE = 0x03;
	public final static byte TYPE_HTTP_ERROR = 0x04;
	public final static byte TYPE_PARSE_FAILED = 0x05;
	public final static byte TYPE_IO = 0x06;
	public final static byte TYPE_RUN = 0x07;
	public final static byte TYPE_WEBSERVICE = 0x08;

	private byte type;
	private String code;
	private String description;

	private AppException(byte type, String code, Exception excp) {
		super(excp);
		this.type = type;
		this.code = code;
	}

	private AppException(byte type, String code, String description,
			Exception excp) {
		super(excp);

		this.type = type;
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return this.code;
	}

	public int getType() {
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * �����쳣��������XX
	 * 
	 * @param code
	 * @return
	 */
	public static AppException http(String code) {
		return new AppException(TYPE_HTTP_CODE, code, null);
	}

	/**
	 * �����쳣������ʱ
	 * 
	 * @param e
	 * @return
	 */
	public static AppException http(Exception e) {
		return new AppException(TYPE_HTTP_ERROR, "0", e);
	}

	/**
	 * �����쳣����ȡ���ݳ�ʱ
	 * 
	 * @param e
	 * @return
	 */
	public static AppException socket(Exception e) {
		return new AppException(TYPE_SOCKET, "0", e);
	}

	/**
	 * �ļ����쳣
	 * 
	 * @param e
	 * @return
	 */
	public static AppException io(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, "0", e);
		} else if (e instanceof IOException) {
			return new AppException(TYPE_IO, "0", e);
		}
		return run(e);
	}

	/**
	 * ���ݽ����쳣
	 * 
	 * @param e
	 * @return
	 */
	public static AppException parse(Exception e) {
		return new AppException(TYPE_PARSE_FAILED, "0", e);
	}

	/**
	 * ��������ʧ�ܣ�������������
	 * 
	 * @param e
	 * @return
	 */
	public static AppException network(Exception e) {
		if (e instanceof UnknownHostException || e instanceof ConnectException) {
			return new AppException(TYPE_NETWORK, "0", e);
		} else if (e instanceof HttpException) {
			return http(e);
		} else if (e instanceof SocketException) {
			return socket(e);
		}
		return http(e);
	}

	/**
	 * Ӧ�ó�������ʱ�쳣
	 * 
	 * @param e
	 * @return
	 */
	public static AppException run(Exception e) {
		return new AppException(TYPE_RUN, "0", e);
	}

	/**
	 * ���÷����쳣
	 * 
	 * @param e
	 * @return
	 */
	public static AppException webservice(String code, String description) {
		return new AppException(TYPE_WEBSERVICE, code, description, null);
	}

	// /**
	// * ��ʾ�ѺõĴ�����Ϣ
	// *
	// * @param ctx
	// */
	// public void makeToast(Context ctx) {
	// switch (this.getType()) {
	// case TYPE_HTTP_CODE:
	// String err = ctx.getString(R.string.err_http_status_code,
	// this.getCode());
	// Toast.makeText(ctx, err, Toast.LENGTH_SHORT).show();
	// break;
	// case TYPE_HTTP_ERROR:
	// Toast.makeText(ctx, R.string.err_http_exception, Toast.LENGTH_SHORT)
	// .show();
	// break;
	// case TYPE_SOCKET:
	// Toast.makeText(ctx, R.string.err_socket_exception,
	// Toast.LENGTH_SHORT).show();
	// break;
	// case TYPE_NETWORK:
	// Toast.makeText(ctx, R.string.err_network_not_connected,
	// Toast.LENGTH_SHORT).show();
	// break;
	// case TYPE_PARSE_FAILED:
	// Toast.makeText(ctx, R.string.err_parser_failed, Toast.LENGTH_SHORT)
	// .show();
	// break;
	// case TYPE_IO:
	// Toast.makeText(ctx, R.string.err_io_exception, Toast.LENGTH_SHORT)
	// .show();
	// break;
	// case TYPE_RUN:
	// Toast.makeText(ctx, R.string.err_app_run_code, Toast.LENGTH_SHORT)
	// .show();
	// break;
	//
	// case TYPE_WEBSERVICE:
	//
	// Toast.makeText(ctx, this.getDescription(), Toast.LENGTH_SHORT)
	// .show();
	//
	// break;
	//
	// }
	//
	// }

}
