package com.web.util;

public class PageUtils {

	private int pageNum;//当前页码

	private int pageSize;//总页数容器

	private int totlePages;//总页数

	private int start;//首页码

	private int end;//尾页码

	private int totleSize;//总条数

	public int getTotlePages(int size, int pagesize) {
		if (size % pagesize == 0) {
			this.setTotlePages(size / pagesize);
		} else {
			this.setTotlePages(size / pagesize + 1);
		}
		return this.totlePages;
	}
	/***
	 * page是页码，totle是总页数，x是想要显示的页数（最好是奇数）
	 * @param page
	 * @param totle
	 * @param x
	 */
	public void setLoop(int page, int totle, int x) {
		if (totle > x) {
			if (page > x / 2 + 1) {
				if (page > totle - (x / 2 + 1)) {
					this.setStart(totle - (x - 1));
					this.setEnd(totle);
				} else {
					this.setStart(page - x / 2);
					this.setEnd(page + x / 2);
				}
			} else {
				this.setStart(1);
				this.setEnd(x);
			}
		} else {
			this.setStart(1);
			this.setEnd(totle);
		}
		 System.out.println("共"+totle+"页,当前第"+page+"页");
		 if(start>1){
		 System.out.print("...");
		 }
		 for(int i = start;i<=end;i++){
		 if(i==page)
		 System.out.print (i+" ");
		 else
		 System.out.print ("<"+i+"> ");
		 }
		 if(end<totle)
		 System.out.println("...");
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotlePages() {
		return totlePages;
	}

	public void setTotlePages(int totlePages) {
		this.totlePages = totlePages;
	}

	public int getTotleSize() {
		return totleSize;
	}

	public void setTotleSize(int totleSize) {
		this.totleSize = totleSize;
	}
}