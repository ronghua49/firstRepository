package com.haohua.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.haohua.Exception.ServiceException;
import com.haohua.dao.AdminDao;
import com.haohua.dao.MovieDao;
import com.haohua.dao.MovieTypeDao;
import com.haohua.dao.ReplyDao;
import com.haohua.dao.TypeDao;
import com.haohua.entity.Admin;
import com.haohua.entity.Movie;
import com.haohua.entity.MovieType;
import com.haohua.entity.Reply;
import com.haohua.entity.Type;
import com.haohua.util.CacheUtil;
import com.haohua.util.Config;
import com.haohua.util.Page;

public class MovieService {
	AdminDao adminDao = new AdminDao();
	MovieDao movieDao = new MovieDao();
	MovieTypeDao movieTypeDao = new MovieTypeDao();
	TypeDao typeDao = new TypeDao();
	ReplyDao replyDao = new ReplyDao();

	public Admin Login(String adminName, String password) {
		password = DigestUtils.md5Hex(password + Slat.SLAT);
		Admin admin = adminDao.findAdminByName(adminName);
		if (admin!=null&&password.equals(admin.getPassword())) {
			return admin;
		} else {
			throw new ServiceException("用户名或者密码错误");
		}

	}

	public void Addmovie(String filmName, String director, String area, String year, String imgName, String content,
			String[] types) {
		// 添加电影需要向两张表中添加数据 和数据关系
		Movie movie = new Movie();
		
		Document doc = Jsoup.parse(content);//解析HTML字符串返回一个Document实现
		//Element link = doc.select("p").first();//查找第一个p元素
		
		String text = doc.body().text();//取得字符串中的文本
		//如果解析的文本大于100字节，则取100字节
		if (text.length()>100) {
			text=text.substring(0,100)+"...";
		}
		movie.setArea(area);
		movie.setSimpleContent(text);
		movie.setContent(content);
		movie.setDirectorName(director);
		movie.setImgName(imgName);
		movie.setYear(year);
		movie.setMovieName(filmName);

		movie.setReplyNum(Integer.parseInt(Config.DEFAULT_REPLY));
		movie.setScanNum(Integer.parseInt(Config.DEFAULT_VIEW));
		
		Integer movieId = movieDao.save(movie);// 返回当前movie id
		
		//System.out.println("添加电影的电影id"+movieId);
		
		
		//存入关联关系表
		for (String type : types) {
			MovieType movieType = new MovieType();
			//把传过来的typeId集合转换为Integer类型
			Integer typeId = Integer.parseInt(type);
			movieType.setMovieId(movieId);
			movieType.setTypeId(typeId);
			movieTypeDao.save(movieType);
		}
	}

	public List<Type> findTypeList() {
		@SuppressWarnings("unchecked")
		List<Type> typelist = (List<Type>) CacheUtil.get(Config.TYPE_CACHE, "typelist");
		if (typelist!=null) {
			return typelist;
		}else {
			typelist = typeDao.findAll();
			CacheUtil.set(Config.TYPE_CACHE, "typelist", typelist);
			return typelist;
		}
	}
	/**根据页面参数 返回对应的movie 页面信息 
	 * @param pageNumNow
	 * @param keys
	 * @return
	 */
	public Page<Movie> findMoviePageByPageNo(int pageNumNow,String keys) { //根据关键字查询 movie列表 count值为 查询到的符合关键字的数据条数
		int count = movieDao.findMovieCount(keys);
		//根据当前页和总条数 得出page对象（获得 当前页要显示的开始条数，和每页显示数量）
		Page<Movie> page = new Page<>(pageNumNow, count);
		//传入page 的参数 查询当前要显示的list列表页面
		Map<String , String> params = new HashMap<>();
		params.put("start", page.getStart().toString());
		params.put("pageSize", page.getPageSize().toString());
		params.put("keys", keys);
		List<Movie> movieList = movieDao.findAll(params);
		//迭代获得每个movie 对象
		for (Movie movie : movieList) {
			//根据movieID获得 对应的types 添加到当前movie对象
			int mid = movie.getId();
			List<Type> typeList = typeDao.findTypesByMid(mid);
			movie.setTypelist(typeList);
			
		}
		//把查询到的movielist 添加到page对象 传送给servlet
		page.setItems(movieList);
		return page;
	}

	public boolean findMovieName(String filmName,String mid) {
		//根据修改的电影名查询电影
		Movie movie = movieDao.findMovieByName(filmName);
		//如果电影名 查询到的电影为null 则可以修改和添加
		if (movie == null) {
			return true;
		} 
		//如果电影名 查询到的电影不为null，并且mid没值值，表示为添加，存在重名 返回false
		if (StringUtils.isEmpty(mid)) {
			return false;
		}
		//表示查询到的重名为当前修改的电影
		if (movie.getId()==Integer.parseInt(mid )) {
			return true;
		}
		return false;
	}

	public void deleteMovieById(Integer movieId) {

		if (movieId == null) {
			movieId = 0;
		}
		movieDao.deleteById(movieId);
		movieTypeDao.deleteByMid(movieId);
	}

	/**根据电影id查询电影 （修改电影回显查询）
	 * @param mid
	 * @return
	 */
	public Movie findMovieById(String mid) {
		if (StringUtils.isNumeric(mid)) {
			Movie movie = movieDao.findById(Integer.parseInt(mid));
			// 把movie 的imgname该为发起下载请求的 全名
			movie.setRequestImgName(Config.getProperty("http.address") + movie.getImgName());
			//movie.setDbImgName(dbImgName);
			return movie;
		} else {
			throw new ServiceException("参数异常！");
		}
	}
	/**根据电影id 查询对应的type集合 (由于电影对应的类型基本确定，所以放入缓存)
	 * @param mid
	 * @return
	 */
	public List<Type> findTypeList(String mid) {
		if (StringUtils.isNumeric(mid)) {
			@SuppressWarnings("unchecked")
			List<Type> selectedList = (List<Type>) CacheUtil.get(Config.TYPE_CACHE, "selectedList");
			if (selectedList!=null) {
				return selectedList;
			} else {
				selectedList = typeDao.findTypesByMid(Integer.parseInt(mid));
				for (Type type : selectedList) {
					type.setSelected(true);
				}
				CacheUtil.set(Config.TYPE_CACHE, "selectedList", selectedList);
				return selectedList;
			}
		} else {
			throw new ServiceException("参数异常！");
		}

	}

	/**
	 * @param mid
	 *            要修改的电影id
	 * @param filmName
	 * @param director
	 * @param area
	 * @param year
	 * @param imgName
	 * @param content
	 *
	 */
	public void EditMovie(String mid, String filmName, String director, String area, String year, String imgName,
			String content, String[] types) {
		//封装电影对象
		Movie movie = new Movie();
		movie.setId(Integer.parseInt(mid));
		movie.setMovieName(filmName);
		movie.setDirectorName(director);
		movie.setArea(area);
		movie.setYear(year);
		movie.setImgName(imgName);
		movie.setContent(content);
		//修改电影
		movieDao.editBy(movie);

		// 删除当前电影id 对应的type
		movieTypeDao.deleteByMid(Integer.parseInt(mid));
		// 重新建立类型关系
		for (String type : types) {
			MovieType movieType = new MovieType();
			movieType.setMovieId(Integer.parseInt(mid));
			movieType.setTypeId(Integer.parseInt(type));
			movieTypeDao.save(movieType);
		}

	}

	/**根据关键字 typeName查询对应的电影类型列表
	 * @return 类型对象列表
	 */
	public Page<Type> findTypeByTypeName(int pageNumNow,String keys) {
		
		int count = typeDao.findTypesCount(keys);
		Page<Type> page = new Page<>(pageNumNow, count);
		//根据当前页参数 返回对应列表
		List<Type> types = typeDao.findTypesByPage(page.getStart(),page.getPageSize(),keys);
		page.setItems(types);
		return page;
	}

	/**根据typeid删除类型
	 * @param tid
	 */
	public void deleteType(String tid) {
		Type type = typeDao.findType(Integer.parseInt(tid));
		List<Movie> movie = movieDao.findMovies(Integer.parseInt(tid));
		if (type==null || !movie.isEmpty() ) {
			throw new ServiceException("查询的类型不存在或者已有电影占用");
		} else {
			//执行删除
			typeDao.deleteByTid(Integer.parseInt(tid));
		}
		
	}

	/**根据类型名查询 是否有同名的类型
	 * @param typeName
	 * @return
	 */
	public boolean findTypeName(String typeName,String tid) {
		Type type = typeDao.findType(typeName);
		//无论新增和修改 type为null 表示名字都可用
		if (type==null) {
			return true;
		} 
		//type不为空，tid为空 ，表示为新增，返回false；
		if (StringUtils.isEmpty(tid)) {
			return false;
		}
		//type 和 tid都不为空，并且tid不是一个数字 ，表示篡改 
		if (!StringUtils.isNumeric(tid)) {
		 throw new ServiceException("参数异常");
		}
		//type 和 tid都不为空,并且tid是一个数字  若查到的type id和当前修改 id相同 ，则表示修改当前节点 允许重复 
		if(type.getId()==Integer.parseInt(tid)) {
			return true;
		}
		return false;
	}

	/**添加类型
	 * @param typeName 要添加类型名
	 */
	public void AddType(String typeName) {
		typeDao.addType(typeName);
	}

	/**修改类型
	 * @param editName 要修改的类型名（经过校验不重复的）
	 */
	public void editType(String editName,String editTypeId) {
		typeDao.editType(editName,Integer.parseInt(editTypeId));
	}

	/**查询所有的为审核的电影评论(分页查询)
	 * @param uNREVIEWD_STATE 0 未审核的状态
	 * @return reply 对象集合
	 */
	public Page<Reply> findReplyList(int uNREVIEWD_STATE,int p) {
		int count = replyDao.countUnview(uNREVIEWD_STATE);
		Page<Reply> page = new Page<>(p, count);
		 List<Reply> replyList = replyDao.findReplyList(uNREVIEWD_STATE,page.getStart(),page.getPageSize());
		 page.setItems(replyList);
		 return page;
	}

	/**根据replyid和状态 更新reply表 若状态为审核通过，则电影的回复量加一
	 * @param replyId 要修改的id
	 * @param state 修改为的状态
	 */
	public void updateReply(int replyId, int state) {
		replyDao.updateReply(replyId,state);
		if (state==Config.REVIEWDPASS_STATE) {
			Movie movie = movieDao.findMovieByReplyId(replyId);
			movie.setReplyNum(movie.getReplyNum()+1);
			movieDao.editBy(movie);
		}
	}

	/**查询reply表 新的未审核的个数
	 * @return 未审核个数
	 */
	public int countNewComment(int unviewState) {
		return replyDao.countUnview(unviewState);
	}

}
