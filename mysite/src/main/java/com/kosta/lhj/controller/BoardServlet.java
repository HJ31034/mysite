package com.kosta.lhj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import com.kosta.lhj.dao.BoardDaoImpl;
import com.kosta.lhj.util.WebUtil;
import com.kosta.lhj.vo.BoardVo;
import com.kosta.lhj.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * public enum EPath { MIDDLEPATH(
	 * "C:\\Users\\illus\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\images"
	 * ), THUMBPATH("123");
	 * 
	 * String value;
	 * 
	 * private EPath(String value){ this.value = value; } public String getValue() {
	 * return value; } }
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("a");

		System.out.println("doGet Call");
		System.out.println("doGet Filename = " + request.getParameter("fileName"));
		System.out.println("board:" + actionName);

		if ("list".equals(actionName)) {
			// 리스트 가져오기
			BoardDaoImpl dao = new BoardDaoImpl();

			String keyword = request.getParameter("kwd");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			if (keyword == null) { // 검색호출 전
				List<BoardVo> list = dao.getList();
				System.out.println("keywordkeywordkeyword: " + keyword);
				System.out.println("list: " + list.size());
				request.setAttribute("list", list);

			} else { // 검색!!
				System.out.println("keywordkeywordkeyword: " + keyword);
				System.out.println("startDatestartDatestartDate: " + startDate);
				System.out.println("endDateendDendDateateendDate: " + endDate);

				String keyField = request.getParameter("keyField");

				List<BoardVo> list = null;

				System.out.println("keyField: " + keyField);

				if (!keyField.equals("regDate")) { // 제목 내용 작성자 검색
					list = dao.getList(keyword, null, null, keyField);
					request.setAttribute("list", list);
				} else if (keyField.equals("regDate")) {
					list = dao.getList(null, startDate, endDate, keyField);
					request.setAttribute("list", list);
				}
			}
			/*
			 * if(startDate != null && endDate != null) {//작성일자 기준 검색
			 * 
			 * list = dao.getList(null,startDate,endDate,keyField);
			 * 
			 * }
			 */

			// 리스트 화면에 보내기
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("read".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDaoImpl dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);
			dao.upHit(no); // 조쇠수 증가
			System.out.println(boardVo.toString());

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("modifyform".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDaoImpl dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		} else if ("modify".equals(actionName)) {
			// 게시물 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo vo = new BoardVo(no, title, content);
			BoardDaoImpl dao = new BoardDaoImpl();

			dao.update(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");
		} else if ("writeform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("delete".equals(actionName)) { // 데이터 삭제
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDaoImpl dao = new BoardDaoImpl();
			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} else if ("filedownload".equals(actionName)) {// 파일 다운로드

			System.out.println("filedownload CALl");

			String fileName = request.getParameter("fileName");

			// String realPath2 = request.getSession().getServletContext().getRealPath("/");
			String realPath = request.getSession().getServletContext().getRealPath("WEB-INF\\views\\images\\");

			File downfile = new File(realPath + "\\" + fileName);
			System.out.println("동적PATH: " + realPath + "\\" + fileName);
			System.out.println("realPath " + realPath);
			System.out.println("downfile :" + downfile);

			System.out.println(" file.length = " + downfile.length());

			ServletOutputStream outStream = null;
			FileInputStream inputStream = null;

			// 동적
			// C:\Users\illus\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\
			// wtpwebapps\mysite\src\main\webapp\WEB-INF\views\images\BoardServlet.java

			// 정적
			// C:\Users\illus\eclipse-workspace\mysite\src\main\webapp\WEB-INF\views\images\BoardServlet.java

			try {
				outStream = response.getOutputStream();
				inputStream = new FileInputStream(downfile);
				// Setting Resqponse Header

				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
				System.out.println("downfile.getName(): " + downfile.getName());
				// Writing InputStream to OutputStream

				byte[] outByte = new byte[4096];

				while (inputStream.read(outByte, 0, 4096) != -1) {
					outStream.write(outByte, 0, 4096);
				}

			} catch (Exception e) {

				throw new IOException();

			} finally {
				inputStream.close();
				outStream.flush();
				outStream.close();
			}

		}

		else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");

		}
	}

	private static final String CHARSET = "utf-8";
	private static final String ATTACHES_DIR = "C:\\Users\\illus\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\images";
	private static final int LIMIT_SIZE_BYTES = 1024 * 1024;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		doGet(request, response);
		request.setCharacterEncoding(CHARSET);

		String actionName = request.getParameter("board_type");

		System.out.println("doPost actionName:" + actionName);

		// 파일업로드
		PrintWriter out = response.getWriter();

		File attachesDir = new File(ATTACHES_DIR);

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setRepository(attachesDir);
		fileItemFactory.setSizeThreshold(LIMIT_SIZE_BYTES);
		ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);

		// vo 전달용 파라메타
		String title = null;
		String content = null;
		String filename = request.getParameter("fileName");
		;
		Long filesize = null;
		String newFileName = null;

		try {
			List<FileItem> items = fileUpload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					System.out.printf("aa파라미터 명=" + item.getFieldName() + " , value=" + item.getString(CHARSET));

					if (item.getFieldName().equals("title")) {
						title = item.getString(CHARSET);

					} else if (item.getFieldName().equals("content")) {
						content = item.getString(CHARSET);
					}
				}

				else {
					System.out.printf("\n+ ss파라미터 명: " + item.getFieldName() + "  파일 명 : " + item.getName()
							+ " ,  파일 크기 : " + item.getSize() + "bytes \n");
					filename = item.getName(); // 원본파일이름
					filesize = item.getSize();
					UUID uuid = UUID.randomUUID();
					newFileName = item.getName() + uuid;
					if (item.getSize() > 0) {

						// String extension = filename.substring(filename.lastIndexOf("."),
						// filename.length()); //확장자명
						// uuid = UUID.randomUUID();
						// newFileName = filename+uuid.toString() + extension;
						// System.out.println("newFileName: "+newFileName);

						String separator = File.separator;
						int index = item.getName().lastIndexOf(separator);
						String fileName = item.getName().substring(index + 1);
						File uploadFile = new File(ATTACHES_DIR + separator + fileName);
						item.write(uploadFile);
					}
				}

			}
			// out.println("<h1>파일 업로드 완료</h1>");
		} catch (Exception e) {
			// 파일 업로드 처리 중 오류가 발생하는 경우
			e.printStackTrace();
			out.println("<h1>파일 업로드 중 오류가  발생하였습니다.</h1>");
		}

		// System.out.println("nameFile: "+nameFile+" sizeFile: "+sizeFile);

		UserVo authUser = getAuthUser(request);

		int userNo = authUser.getNo();

		System.out.println("userNo : [" + userNo + "]");
		System.out.println("title : [" + title + "]");
		System.out.println("content : [" + content + "]");
		System.out.println("filename : [" + filename + "]");
		System.out.println("filesize : [" + filesize + "]");
		System.out.println("newFileName : [" + newFileName + "]");

		BoardVo vo = new BoardVo(title, content, userNo, filename, filesize, newFileName);
		BoardDaoImpl dao = new BoardDaoImpl();
		dao.insert(vo);

		System.out.println("nameFile: " + filename + " sizeFile: " + filesize);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		// WebUtil.redirect(request, response, "/mysite/board?a=list");

	}

	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

}
