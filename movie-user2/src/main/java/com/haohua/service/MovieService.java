package com.haohua.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.haohua.Exception.ServiceException;
import com.haohua.dao.AdminDao;
import com.haohua.dao.MovieDao;
import com.haohua.dao.MovieTypeDao;
import com.haohua.dao.ReplyDao;
import com.haohua.dao.TypeDao;
import com.haohua.dao.UserDao;
import com.haohua.entity.Admin;
import com.haohua.entity.Movie;
import com.haohua.entity.MovieType;
import com.haohua.entity.Reply;
import com.haohua.entity.Type;
import com.haohua.entity.User;
import com.haohua.util.CacheUtil;
import com.haohua.util.Config;
import com.haohua.util.Page;

public class MovieService {
	AdminDao adminDao = new AdminDao();
	MovieDao movieDao = new MovieDao();
	MovieTypeDao movieTypeDao = new MovieTypeDao();
	TypeDao typeDao = new TypeDao();
	ReplyDao replyDao = new ReplyDao();
	UserDao userDao = new UserDao();

	public Admin Login(String adminName, String password) {
		password = DigestUtils.md5Hex(password + Slat.SLAT);

		Admin admin = adminDao.findAdminByName(adminName);

		if (admin.getPassword().equals(password)) {
			return admin;
		} else {
			throw new ServiceException("用户名或者密码错误");
		}

	}

	public void Addmovie(String filmName, String director, String area, String year, String imgName, String content,
			String[] types) {
		// 添加电影需要向两张表中添加数据 和数据关系
		Movie movie = new Movie();
		movie.setArea(area);
		movie.setContent(content);
		movie.setDirectorName(director);
		movie.setImgName(imgName);
		movie.setYear(year);
		movie.setMovieName(filmName);

		movie.setReplyNum(Integer.parseInt(Config.DEFAULT_REPLY));
		movie.setScanNum(Integer.parseInt(Config.DEFAULT_VIEW));
		Integer movie_id = movieDao.save(movie);// 返回当前movie id

		for (String type : types) {
			MovieType movieType = new MovieType();
			Integer type_id = Integer.parseInt(type);
			movieType.setMovie_id(movie_id);
			movieType.setType_id(type_id);
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

	public Page<Movie> findMoviePageByPageNo(int pageNumNow,String keys,String typeId) {
		
		if(StringUtils.isNotEmpty(typeId) && !StringUtils.isNumeric(typeId)) {
			throw new ServiceException("参数异常");
		}
		//根据关键字查询 movie列表 count值为 查询到的符合关键字的数据条数
		int count = movieDao.findMovieCount(keys,typeId);
		//根据当前页和总条数 得出page对象（获得 当前页要显示的开始条数，和每页显示数量）
		Page<Movie> page = new Page<>(pageNumNow, count);
		//传入page 的参数 查询当前要显示的list列表页面
		Map<String , String> params = new HashMap<>();
		params.put("start", page.getStart().toString());
		params.put("pageSize", page.getPageSize().toString());
		params.put("keys", keys);
		params.put("typeId", typeId);
		List<Movie> movieList = movieDao.findAll(params);
		//迭代获得每个movie 对象
		for (Movie movie : movieList) {
			//根据movieID获得 对应的types 添加到当前movie对象
			int mid = movie.getId();
			List<Type> typeList = typeDao.findTypesByMid(mid);
			movie.setTypelist(typeList);
			movie.setRequestImgName(Config.getProperty("http.address")+movie.getImgName());
		}
		//把查询到的movielist 添加到page对象 传送给servlet
		page.setItems(movieList);
		return page;
	}

	
	/**根据电影名 和 当前修改的电影id 校验此电影名是否可用
	 * @param filmName  修改为的电影名
	 * @param mid  当前修改的电影id
	 * @return true为可用 false 为不可用
	 */
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

	/**删除电影 根据电影id
	 * @param movieId
	 */
	public void deleteMovieById(Integer movieId) {

		if (movieId == null) {
			movieId = 0;
		}
		movieDao.deleteById(movieId);
		movieTypeDao.deleteByMid(movieId);
	}

	/**
	 * 根据电影id查询对应的movie 
	 * @param mid 电影id
	 * @return 单个电影对象
	 */
	public Movie findDetailMovieById(String mid) {
		if (StringUtils.isNumeric(mid)) {
			Movie movie = movieDao.findById(Integer.parseInt(mid));
			
			//只要查询电影的详情，就把浏览量加1 
			movie.setScanNum(movie.getScanNum()+1);
			movieDao.editBy(movie);
			
			// 设置movie RequestImgName该为发起下载请求的 全名
			movie.setRequestImgName(Config.getProperty("http.address")+ movie.getImgName());
			
			List<Type> typeList = typeDao.findTypesByMid(Integer.parseInt(mid));
			movie.setTypelist(typeList);
			
			Reply lastReply = replyDao.findLastReply(Integer.parseInt(mid),Config.REVIEWDPASS_STATE);
			
			if (lastReply!=null) {
				movie.setLastReplyTimestamp(lastReply.getCreateTime());
			}
			return movie;

		} else {
			throw new ServiceException("参数异常！");
		}

	}

	/**
	 * 根据电影id查询对应的type类型集合
	 * @param mid 电影 id
	 * @return 对应的type类型集合
	 */
	public List<Type> findTypeList(String mid) {
		if (StringUtils.isNumeric(mid)) {
			List<Type> selectedList = typeDao.findTypesByMid(Integer.parseInt(mid));
			for (Type type : selectedList) {
				type.setSelected(true);
			}
			// List<Type> allTypeList = typeDao.findAll();
			// Map<String , List<Type>> maps = new HashMap<>();
			// maps.put("selected", selectedList);
			// maps.put("allTypes", allTypeList);
			return selectedList;
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
	 * @param types当前电影 的类型id数组
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
			movieType.setMovie_id(Integer.parseInt(mid));
			movieType.setType_id(Integer.parseInt(type));
			movieTypeDao.save(movieType);
		}

	}

	/**查询排名前五的电影列表
	 * @return 电影的list集合
	 */
	public List<Movie> findSortMovies() {
		 List<Movie> sortMovieList = movieDao.findSortMovieList();
		 for (Movie movie : sortMovieList) {
			if (movie.getMovieName().length()>10) {
				movie.setMovieName(movie.getMovieName().substring(0, 10)+"...");
			}
		}
		 return sortMovieList;
	}

	/**根据电影id查询所有审核通过的评论列表
	 * @param state
	 * @return
	 */
	public List<Reply> findReplysByMidState(String mid,int state) {
		@SuppressWarnings("unchecked")
		List<Reply> replyList = (List<Reply>) CacheUtil.get(Config.REPLY_CACHE, "replyList"+mid);
		if (replyList!=null) {
			return replyList;
		} else {
			replyList=replyDao.findReplyByMidAndState(Integer.parseInt(mid),state);
			CacheUtil.set(Config.REPLY_CACHE, "replyList"+mid, replyList);
			return replyList;
		}
	}

	/**根据电影id，评论者id，和评论内容 添加电影
	 * @param movieId 电影id
	 * @param userId  评论者id
	 * @param content 评论内容
	 */
	public void addComment(String movieId, int userId, String content) {
		
			replyDao.addComment(Integer.parseInt(movieId),userId,content);
		
	}

	/**用户登录
	 * @param username
	 * @param password
	 */
	public User userLogin(String username, String password) {
		User user = userDao.findUserByName(username);
		password =  DigestUtils.md5Hex(password + Slat.SLAT);
		if (user!=null && user.getPassword().equals(password)) {
			return user;
		}else {
			throw new ServiceException("用户名或者密码错误");
		}
	}

	/**用户注册
	 * @param username 
	 * @param password 加密存储
	 */
	public void userRegiste(String username, String password,String tel,String email) {
		password = DigestUtils.md5Hex(password + Slat.SLAT);
		userDao.insert(username,password,tel,email);
	}

	/**根据用户名查找是否存在user
	 * @param username
	 * @return
	 */
	public boolean findUser(String username) {
		User user = userDao.findUserByName(username);
		if (user!=null) {
			return false;
		} else {
			return true;
		}
	}
}
