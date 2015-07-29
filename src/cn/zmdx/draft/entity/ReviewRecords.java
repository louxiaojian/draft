package cn.zmdx.draft.entity;

import java.util.Date;

public class ReviewRecords {
	private int id;
	private String status;
	private int photoSetId;
	private String descs;
	private Date datetime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPhotoSetId() {
		return photoSetId;
	}
	public void setPhotoSetId(int photoSetId) {
		this.photoSetId = photoSetId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
}
