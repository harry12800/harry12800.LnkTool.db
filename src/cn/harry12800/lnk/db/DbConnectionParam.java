package cn.harry12800.lnk.db;

import cn.harry12800.Lnk.core.DataEntity;

public class DbConnectionParam extends DataEntity<DbConnectionParam> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userName;
	String pass;
	String url;
	/**
	 * 获取userName
	 *	@return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置userName
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取pass
	 *	@return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * 设置pass
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * 获取url
	 *	@return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置url
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
 
	@Override
	public String toString() {
		return "DbExportType [userName=" + userName + ", pass=" + pass
				+ ", url=" + url + "]";
	}
	 
	
}
