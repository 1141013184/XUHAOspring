package com.lero.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lero.dao.UserDao;
import com.lero.model.Admin;
import com.lero.model.DormManager;
import com.lero.model.Student;
import com.lero.util.JDBCUtil;

public class PasswordServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JDBCUtil dbUtil = new JDBCUtil();
	UserDao userDao = new  UserDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");

		//点击左侧导航修改密码时
		if("preChange".equals(action)) {
			passwordPreChange(request, response);
			return;
			//修改密码时
		} else if("change".equals(action)) {
			passwordChange(request, response);
			return;
		}
	}

	private void passwordChange(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object currentUserType = session.getAttribute("currentUserType");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		Connection con = null;
		try {
			con = dbUtil.getConn();
			
			if("admin".equals((String)currentUserType)) {
				Admin admin = (Admin)(session.getAttribute("currentUser"));
				if(oldPassword.equals(admin.getPassword())) {
					userDao.adminUpdate(con, admin.getAdminId(), newPassword);
					admin.setPassword(newPassword);
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "修改成功 ");
					request.setAttribute("mainPage", "admin/passwordChange.jsp");
					request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
				} else {
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "原密码错误");
					request.setAttribute("mainPage", "admin/passwordChange.jsp");
					request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
				}
			} else if("dormManager".equals((String)currentUserType)) {
				DormManager manager = (DormManager)(session.getAttribute("currentUser"));
				if(oldPassword.equals(manager.getPassword())) {
					userDao.managerUpdate(con, manager.getDormManagerId(), newPassword);
					manager.setPassword(newPassword);
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "修改成功 ");
					request.setAttribute("mainPage", "dormManager/passwordChange.jsp");
					request.getRequestDispatcher("mainManager.jsp").forward(request, response);
				} else {
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "原密码错误");
					request.setAttribute("mainPage", "dormManager/passwordChange.jsp");
					request.getRequestDispatcher("mainManager.jsp").forward(request, response);
				}
			} else if("student".equals((String)currentUserType)) {
				Student student = (Student)(session.getAttribute("currentUser"));
				if(oldPassword.equals(student.getPassword())) {
					userDao.studentUpdate(con, student.getStudentId(), newPassword);
					student.setPassword(newPassword);
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "修改成功 ");
					request.setAttribute("mainPage", "student/passwordChange.jsp");
					request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
				} else {
					request.setAttribute("oldPassword", oldPassword);
					request.setAttribute("newPassword", newPassword);
					request.setAttribute("rPassword", newPassword);
					request.setAttribute("error", "原密码错误");
					request.setAttribute("mainPage", "student/passwordChange.jsp");
					request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeAll(null,null,con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//点击修改密码
	private void passwordPreChange(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {

		HttpSession session = request.getSession();
		Object currentUserType = session.getAttribute("currentUserType");
		if("admin".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "admin/passwordChange.jsp");
			request.getRequestDispatcher("mainAdmin.jsp").forward(request, response);
		} else if("dormManager".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "dormManager/passwordChange.jsp");
			request.getRequestDispatcher("mainManager.jsp").forward(request, response);
		} else if("student".equals((String)currentUserType)) {
			request.setAttribute("mainPage", "student/passwordChange.jsp");
			request.getRequestDispatcher("mainStudent.jsp").forward(request, response);
		}
	}
	
}
