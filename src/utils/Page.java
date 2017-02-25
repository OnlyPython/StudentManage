package utils;

import java.util.List;

public class Page<E> {
	public static final int DEFAULT_CURRENT_PAGE = 1;
	public static final int DEFAULT_NUM_PER_PAGE = 10;
	private List<E> list;
	private int currentPage;
	private int numPerPage;
	private int totalNum;
	
	public Page(int currentPage, int numPerPage, int totalNum) {
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.totalNum = totalNum;
	}
	public List<E> getList() {
		return list;
	}
	public void setList(List<E> list) {
		this.list = list;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getOffset(){
		return (this.currentPage - 1) * this.numPerPage;
	}
	public int getTotalPageNum(){
		return (this.totalNum + this.numPerPage - 1) / this.numPerPage;
	}
}