package com.jw.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.jw.dao.UserDao;
import com.jw.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 用户名
                if (searchMap.get("username")!=null && !"".equals(searchMap.get("username"))) {
                	predicateList.add(cb.like(root.get("username").as(String.class), "%"+(String)searchMap.get("username")+"%"));
                }
                // 密码，加密存储
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 注册手机号
                if (searchMap.get("phone")!=null && !"".equals(searchMap.get("phone"))) {
                	predicateList.add(cb.like(root.get("phone").as(String.class), "%"+(String)searchMap.get("phone")+"%"));
                }
                // 注册邮箱
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 昵称
                if (searchMap.get("nick_name")!=null && !"".equals(searchMap.get("nick_name"))) {
                	predicateList.add(cb.like(root.get("nick_name").as(String.class), "%"+(String)searchMap.get("nick_name")+"%"));
                }
                // 真实姓名
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 使用状态（Y正常 N非正常）
                if (searchMap.get("status")!=null && !"".equals(searchMap.get("status"))) {
                	predicateList.add(cb.like(root.get("status").as(String.class), "%"+(String)searchMap.get("status")+"%"));
                }
                // 头像地址
                if (searchMap.get("head_pic")!=null && !"".equals(searchMap.get("head_pic"))) {
                	predicateList.add(cb.like(root.get("head_pic").as(String.class), "%"+(String)searchMap.get("head_pic")+"%"));
                }
                // QQ号码
                if (searchMap.get("qq")!=null && !"".equals(searchMap.get("qq"))) {
                	predicateList.add(cb.like(root.get("qq").as(String.class), "%"+(String)searchMap.get("qq")+"%"));
                }
                // 手机是否验证 （0否  1是）
                if (searchMap.get("is_mobile_check")!=null && !"".equals(searchMap.get("is_mobile_check"))) {
                	predicateList.add(cb.like(root.get("is_mobile_check").as(String.class), "%"+(String)searchMap.get("is_mobile_check")+"%"));
                }
                // 邮箱是否检测（0否  1是）
                if (searchMap.get("is_email_check")!=null && !"".equals(searchMap.get("is_email_check"))) {
                	predicateList.add(cb.like(root.get("is_email_check").as(String.class), "%"+(String)searchMap.get("is_email_check")+"%"));
                }
                // 性别，1男，2女
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
