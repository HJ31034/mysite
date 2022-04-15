package com.kosta.lhj.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.MultipartStream;

import com.kosta.lhj.vo.BoardVo;

public class BoardDaoImpl implements BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}

	public List<BoardVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();
System.out.println("getList 전체게시글보기");
		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.hit, to_char(b.reg_date, 'yy-mm-dd') rd, b.user_no, u.name "
					+ " from board b, users u " + " where b.user_no = u.no " + " order by no desc";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("rd");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return list;

	}

	public BoardVo getBoard(int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		BoardVo boardVo = null;

		try {

			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.content, b.hit, to_char(b.reg_date, 'yy-mm-dd') rd, b.user_no, u.name, b.filename, b.filesize "
					+ " from board b, users u " 
					+ " where b.user_no = u.no "
					+ " and b.no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("rd");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");
				String filename = rs.getString("filename");
				Long filesize = rs.getLong("filesize");

				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, userName, filename, filesize);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		System.out.println(boardVo);
		return boardVo;

	}

	public int insert(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		// MultipartRequest multi = null;
		int filesize = 0;
		String filename = null;
		int count = 0;

		try {
			conn = getConnection();

			System.out.println("vo.userNo : [" + vo.getUserNo() + "]");
			System.out.println("vo.title : [" + vo.getTitle() + "]");
			System.out.println("vo.content : [" + vo.getContent() + "]");
			System.out.println("vo.filename : [" + vo.getFilename() + "]");
			System.out.println("vo.fileSize : [" + vo.getFilesize() + "]");
			System.out.println("vo.OriFilename : [" + vo.getOriFilename() + "]");

			/*
			 * query =
			 * " insert into board(no, title,content,hit,reg_date,user_no,filename, orifilename)"
			 * ; query += " values(seq_board_no.nextval,?, ?, ?, sysdate, ?, ?, '111')";
			 */

			query = " insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?, ?,?,?)";
			// 3. SQL문 준비 / 바인딩 / 실행

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			pstmt.setString(4, vo.getFilename());
			pstmt.setString(5, vo.getOriFilename());
			pstmt.setLong(6, vo.getFilesize());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int delete(int no) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int update(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public List<BoardVo> getList(String keyword, String startDate, String endDate, String keyField) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 데이터 전송을 위한 리스트
		String query = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();
 
			System.out.println("keyword:" + keyword);
			
			if (keyword != null || !" ".equals(keyword) && !keyField.equals("regDate")) {// 제목 작성자 검색
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
				System.out.println("keyField " + keyField);
				
				query = "select  b.no no2, b.title title2, b.hit hit2, to_char(b.reg_date, 'yy-mm-dd') rd, u.name userName \r"
						+ " from users u, board b where u.no=b.user_no and " + keyField + " like ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + keyword + "%");
				rs = pstmt.executeQuery();
			} else if (startDate != null && endDate != null) { // 기간 검색
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");

				                                 
				query = "select  b.no no2, b.title title2, b.hit hit2, to_char(b.reg_date, 'yy-mm-dd') rd, u.name userName"
						+ " from users u, board b where u.no=b.user_no and reg_date between ? and ? ";
				/*
				 * query =
				 * "select  b.no no2, b.title title2, b.hit hit2, to_date(b.reg_date, 'yy-mm-dd hh24:mi') regDate, u.name userName\r\n"
				 * + "from users u, board b where u.no=b.user_no and\r\n" +
				 * " reg_date between ? and ? ";
				 */
				pstmt = conn.prepareStatement(query);

				
				pstmt.setString(1, startDate);
				pstmt.setString(2, endDate);
				rs = pstmt.executeQuery();
			}
			 
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no2");
				String title = rs.getString("title2");
				int hit = rs.getInt("hit2");
			 	String regDate = rs.getString("rd");
				String userName = rs.getString("userName");
				
				System.out.println(no + ": " + title + "/ " + hit + "("  + regDate + ")" + userName);

				BoardVo vo = new BoardVo(no, title, hit, regDate , userName);
				list.add(vo);
			 
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return list;
	}

	public void upHit(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

	}

}
