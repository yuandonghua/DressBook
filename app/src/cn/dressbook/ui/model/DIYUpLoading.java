package cn.dressbook.ui.model;

import java.util.ArrayList;
/**
 * @description 上传diy的实体
 * @author 熊天富
 * @date 2016-01-28 10:04:39
 *
 */

public class DIYUpLoading {
	private String is_dz;
	private String dzattire_id;
	private String template_id;
	private String user_id;
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	private ArrayList<Param> paramList;

	public String getIs_dz() {
		return is_dz;
	}

	public void setIs_dz(String is_dz) {
		this.is_dz = is_dz;
	}

	public String getDzattire_id() {
		return dzattire_id;
	}

	public void setDzattire_id(String dzattire_id) {
		this.dzattire_id = dzattire_id;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public ArrayList<Param> getParamList() {
		return paramList;
	}

	public void setParamList(ArrayList<Param> paramList) {
		this.paramList = paramList;
	}

	

}
