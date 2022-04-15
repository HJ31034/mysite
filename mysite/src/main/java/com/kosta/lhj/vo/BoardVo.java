package com.kosta.lhj.vo;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String userName;
	private String filename;
	private Long filesize;
	private String oriFilename;
	

	public BoardVo() {
	}
	
	public BoardVo(int no, String title, String content) {//modify
		this.no = no;		
		this.title = title;
		this.content = content;
	}

	  
	
	public BoardVo(int no, String title, int hit, String regDate, int userNo, String userName) { //getList
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
	}
	
	public BoardVo(int no, String title, int hit, String regDate, String userName) { //작성일시 기준 검색
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate=regDate;
		this.userName = userName;
	}
	
	 

	public int getNo() {
		return no;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getHit() {
		return hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public int getUserNo() {
		return userNo;
	}

	public String getUserName() {
		return userName;
	}

	public String getFilename() {
		return filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public String getOriFilename() {
		return oriFilename;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public void setOriFilename(String oriFilename) {
		this.oriFilename = oriFilename;
	}

	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName,
			String filename, Long filesize, String oriFilename) { //데이터 전체
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
		this.filename = filename;
		this.filesize = filesize;
		this.oriFilename = oriFilename;
	}
	 
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName,
			String filename, Long filesize) { //getBoard
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
		this.filename = filename;
		this.filesize = filesize;
		 
	}
	
	
	
	public BoardVo(String title, String content, int userNo, String filename,
			Long filesize, String oriFilename) { //insert
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.filename = filename;
		this.filesize = filesize;
		this.oriFilename = oriFilename;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", userName=" + userName + ", filename=" + filename + ", filesize="
				+ filesize + ", oriFilename=" + oriFilename + "]";
	}

	
 

}
