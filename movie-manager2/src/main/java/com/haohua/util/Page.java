package com.haohua.util;

import java.util.List;

//把前端的请求走servlet从数据库查询后 带回查询结果的总数据条数、
public class Page<T> {
	// 当前的页码，从前端请求参数返回
	private Integer pageNo;
	// 总页数 通过计算得知
	private Integer totalPage;
	// 规定的每页显示的数据条数
	private Integer pageSize = 5;
	// 每页数据的开始行 在数据库中分页显示使用
	private Integer start;
	// 方便把查询的对象结果 一同封装到此对象中
	private List<T> items;

	// 创建对象时，根据当前页码和数据库总条数 决定返回查询结果要显示的页面 条数
	public Page(int pageNom, int count) {
		int totalPage = 1;
		if (count >= pageSize) {
			// 如果总条数大于 分页的条数
			totalPage = count / pageSize;
			// 如果余数不为0
			if (!(count % pageSize == 0)) {
				totalPage++;
			}
		}
		this.totalPage = totalPage;
			//获得每页的开始条数
		int start = (pageNom - 1) * pageSize;

		this.start = start;
		
		if (pageNom<1) {
			pageNom=1;
		}else if(pageNom>totalPage) {
			pageNom=totalPage;
		}
		this.pageNo = pageNom;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
