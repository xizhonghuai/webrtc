/**
 * 
 */
package com.websocket.origin;

/**
 *@author xizhonghuai
 *@date 2018Äê11ÔÂ20ÈÕ
 *@readme 
 */
public class SignalMsg {
	
	private String rooId;
	private String type;
	private Object data;
	/**
	 * 
	 */
	public SignalMsg() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param rooId
	 * @param type
	 * @param data
	 */
	public SignalMsg(String rooId, String type, Object data) {
		super();
		this.rooId = rooId;
		this.type = type;
		this.data = data;
	}
	public String getRooId() {
		return rooId;
	}
	public void setRooId(String rooId) {
		this.rooId = rooId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SignalMsg [rooId=" + rooId + ", type=" + type + ", data=" + data + "]";
	}
	
	

}
