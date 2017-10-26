package com.web.util;

import com.liuzd.page.Page;


/**
* 分页工具类
* @author liuzd
* @version 1.0 2011-05-12
* @since JDK1.5
* */
public class PageUtil {
	
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final String PAGE_DESC = "↓";
	public static final String PAGE_ASC = "↑";
	public static final String PAGE_NULL = "&nbsp;&nbsp;";	
	public static final String SESSION_PAGE_KEY = "page";	
	
	
	/**
	 * 初始化分页类
	 * @param initPageSql  未分页的查询SQL
	 * @param totalCount   总行数
	 * @param index        分页状态
	 * @param value        只有在设置每页显示多少条时,值不会NULL,其它为NULL
	 */
	public  static Page inintPage(Long totalCount,Integer index,String value,Page sessionPage){	
		Page page = null;
		if(index < 0){
			 page = new Page(totalCount);
		}else{
			 /**每页显示多少条*/
			 Long everPage = null == value ? 10 : Long.parseLong(value);
			 /**获取Session中的分页类,方便保存页面分页状态*/
			 page = sessionPage;			 
			 page.setEveryPage(everPage);
			 page.setTotalCount(totalCount);			
		}	
		return page;		
	}
	
	

	
	/**
	 * 当页点击：首页,前一页,后一页,末页,排序,到第多少页时进行分页操作
	 * @param index 分页状态
	 * @param value 排序字段名或者到第多少页
	 */
	public static Page execPage(int  index,String value,Page sessionPage){	
		Page page = sessionPage;			
		/**调用方法进行分页计算*/
		page.pageState(index,value);		
		return page;		
	}
	
	
	/**
	 * @author wangenlai
	 * 分页公共方法
	 *@param pageIndex:当前页码  total：总条数 pages：页码显示个数   pagez：分页数
	 */
	public static PageUtils execsPage(int  pageIndex,int total,int pages,int pagez){	
		PageUtils pageObject = new PageUtils();
		pageObject.setPageNum(pageIndex);  
        pageObject.setPageSize(total);
        pageObject.setTotlePages(pageObject.getTotlePages(total, pagez));  
        pageObject.setLoop(pageIndex, pageObject.getTotlePages(), pages);
        return pageObject;
	}
	
	/**
	 * @author wangdanqing
	 * 分页公共方法
	 *@param pageIndex:当前页码  total：总条数 pages：页码显示个数   pagez：分页数
	 */
	public static PageUtils execsPageNew(int  pageIndex,int pageSize, int total,int pages,int pagez){	
		PageUtils pageObject = new PageUtils();
		pageObject.setPageNum(pageIndex);  
        pageObject.setPageSize(pageSize);
        pageObject.setTotlePages(pageObject.getTotlePages(total, pagez));  
        pageObject.setLoop(pageIndex, pageObject.getTotlePages(), pages);
        return pageObject;
	}
	
}

