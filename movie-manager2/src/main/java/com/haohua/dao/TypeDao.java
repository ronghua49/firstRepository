package com.haohua.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.haohua.entity.Type;
import com.haohua.util.Dbhelp;

public class TypeDao {

	public List<Type> findAll() {
		String sql = "select id,type_name as text from t_type";
		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class));
	}
	
	public static void deleteByMid(Integer movieId) {
		String sql = "delete from t_movie_type where t_movie_type.movie_id=?";
		Dbhelp.update(sql, movieId);
	}

	public List<Type> findTypesByMid(int mid) {

		String sql = "select id,type_name as text,create_time,remark from t_type t,t_movie_type mt where t.id=mt.type_id and mt.movie_id=?";

		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())),
				mid);
	}

	

	public int findTypesCount(String keys) {
		String sql = "select count(*) from t_type ";
		List<Object> params = new ArrayList<>();
		if (StringUtils.isNotEmpty(keys)) {
			sql+="where type_name like ? ";
			keys="%"+keys+"%";
			params.add(keys);
		}
		
		return Dbhelp.Query(sql, new ScalarHandler<Long>(), params.toArray()).intValue();
	}

	public List<Type> findTypesByPage(Integer start, Integer pageSize, String keys) {
		List<Object> params = new ArrayList<>();
		String sql = "select id, type_name as text,create_time from t_type ";
		if (StringUtils.isNotEmpty(keys)) {
			sql+="where type_name like ? ";
			keys="%"+keys+"%";
			params.add(keys);
		} 
		sql+="limit ?,?";
		params.add(start);
		params.add(pageSize);
		return Dbhelp.Query(sql, new BeanListHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())), params.toArray());
	}

	/**根据类型查询对象  防止前后台不同步
	 * @param tid
	 * @return
	 */
	public Type findType(int tid) {
		String sql = "select id,type_name as text,create_time from t_type where id =?";
		return Dbhelp.Query(sql, new BeanHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())), tid);
	}

	/**根据类型id删除类型
	 * @param parseInt
	 */
	public void deleteByTid(int tid) {
		String sql = "delete from t_type where id=?";
		Dbhelp.update(sql, tid);
	}

	/**根据类型名查询类型
	 * @param typeName
	 * @return
	 */
	public Type findType(String typeName) {
		String sql = "select id ,type_name as text,create_time from t_type where type_name=?";
		return Dbhelp.Query(sql, new BeanHandler<>(Type.class, new BasicRowProcessor(new GenerousBeanProcessor())), typeName);
	}
	
	/**根据类型名增加类型
	 * @param typeName
	 */
	public void addType(String typeName) {
		String sql = "insert into t_type (type_name) values (?)";
		Dbhelp.update(sql, typeName);
	}

	
	/**根据当前类型id和 传入的类型名字，修改此类型名
	 * @param editName 要修改为的类型名
	 * @param typeId 当前修改的类型id
	 */
	public void editType(String editName,int typeId) {
		String sql = "update t_type set type_name=? where id=?";
		Dbhelp.update(sql, editName,typeId);
	}

}
