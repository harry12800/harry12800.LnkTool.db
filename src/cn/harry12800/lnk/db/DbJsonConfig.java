package cn.harry12800.lnk.db;

import java.util.List;

import cn.harry12800.tools.Lists;

public class DbJsonConfig {

	private List<DbConnectionParam> list = Lists.newArrayList();

	/**
	 * 获取list
	 *	@return the list
	 */
	public List<DbConnectionParam> getList() {
		return list;
	}

	/**
	 * 设置list
	 * @param list the list to set
	 */
	public void setList(List<DbConnectionParam> list) {
		this.list = list;
	}
	
}
