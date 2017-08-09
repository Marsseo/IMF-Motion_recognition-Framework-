package com.mycompany.myapp.dto;

import org.springframework.social.facebook.api.CoverPhoto;

public class Member {
	private String mname;
	private String memail;
	private String moriginalfilename;
	private String msavedfilename;
	private String mfilecontent;
	private String mlevel;
	private String mphoto;
	
	public String getMoriginalfilename() {
		return moriginalfilename;
	}
	public String getMphoto() {
		return mphoto;
	}
	public void setMphoto(String mphoto) {
		this.mphoto = mphoto;
	}
	public void setMoriginalfilename(String moriginalfilename) {
		this.moriginalfilename = moriginalfilename;
	}
	public String getMsavedfilename() {
		return msavedfilename;
	}
	public void setMsavedfilename(String msavedfilename) {
		this.msavedfilename = msavedfilename;
	}
	public String getMfilecontent() {
		return mfilecontent;
	}
	public void setMfilecontent(String mfilecontent) {
		this.mfilecontent = mfilecontent;
	}
	public String getMlevel() {
		return mlevel;
	}
	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
}
