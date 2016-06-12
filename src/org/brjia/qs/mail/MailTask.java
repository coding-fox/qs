package org.brjia.qs.mail;

public interface MailTask {

	/**
	 * 运行情况订阅
	 */
	void publishRegister();
	/**
	 * 帮会数据订阅
	 */
	void publishGang();
}
