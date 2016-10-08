/*
 * LZKingsoft, All Right Reserved, 2012-2013.
 * 
 * http://www.zhiyao99.com/
 *
 */
package com.zhiyao99.daemon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.xml.sax.InputSource;

/**
 * 
 * <p>名称:com.zhiyao99.daemon.util.DBHelper.java</p>
 * <p>描述:</p>
 * <p>日期:2013-5-2 下午10:45:55</p>
 * 
 * @author  wangxw
 * @version 1.0
 *
 */
public class DBHelper {

	/** Create logs for DBHelper.class */
	private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

	/** A flag to diagnose JDBC connection */
	private static boolean diagnosticMode = false;

	/** Connection of JDBC */
	private Connection connection = null;

	/** Prepared statement of JDBC */
	private PreparedStatement preparedStatement = null;

	/** Regist for MySQL JDBC */
	static {
		try {
			File file = new File("");
			File confFile = null;

			try {
				confFile = new File(file.getCanonicalPath() + "/proxool.xml");
			} catch (IOException e) {
				e.printStackTrace();
			}

			InputStream is = new FileInputStream(confFile);
			JAXPConfigurator.configure(new InputSource(is), false);
		} catch (Exception e) {
			logger.error("Error on initializing the database pool. \n", e);
		}
	}

	public static boolean isDiagnosticMode() {
		return DBHelper.diagnosticMode;
	}

	public static void setDiagnosticMode(boolean diagnosticMode) {
		DBHelper.diagnosticMode = diagnosticMode;
	}

	/**
	 * Default constructor
	 */
	public DBHelper() {
	}

	/**
	 * Get a connection
	 * <p>diagnostic Model to test JDBC connection is out of date</p>
	 * @return A JDBC connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (diagnosticMode) {
			StringBuilder sb = new StringBuilder(
					"DBHelper.diagnosticMode == true.\n");
			StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StackTraceElement frame = null;

			if (stack.length > 1) {
				for (int i = 1; i < stack.length; i++) {
					frame = stack[i];

					if (frame.getClassName().indexOf("com.zhiyao99.") == -1)
						break;

					sb.append("\n");

					for (int j = 1; j < i; j++)
						sb.append("  ");

					sb.append("  ");
					sb.append(frame.getClassName());
					sb.append(".");
					sb.append(frame.getMethodName());
					sb.append("() line:");
					sb.append(frame.getLineNumber());
				}
			}

			logger.debug(sb.toString());
		}

		return DriverManager.getConnection("proxool.mysql");
	}

	/**
	 * Open connection if need
	 * @return Open connection state Always be true
	 * @throws SQLException
	 */
	public boolean open() throws SQLException {
		try {
			if (connection == null || connection.isClosed())
				connection = DBHelper.getConnection();
		} catch (SQLException e) {
			throw e;
		}

		return true;
	}

	/**
	 * Set Transaction commit mode by receive a parameter
	 * @param autoCommit
	 * @throws SQLException
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if (this.connection == null || this.connection.isClosed())
			new SQLException("DBHelper.connection is null or is closed.");

		this.connection.setAutoCommit(autoCommit);
	}

	/**
	 * close connection
	 */
	public void close() {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
		} catch (Exception e) {
			;
		}

		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception e) {
			;
		}
	}

	/**
	 * Execute SQL to query
	 * @param sql
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		if (sql == null)
			new SQLException("sql is empty");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		if (preparedStatement != null && !preparedStatement.isClosed())
			preparedStatement.close();

		preparedStatement = connection.prepareStatement(sql);
		try {
			return preparedStatement.executeQuery();
		} catch (SQLException ex) {
			throw new SQLException("DBHelper.executeQuery(): " + sql + "\n\t"
					+ ex.getMessage());
		}
	}

	/**
	 * Execute a SQL to Update mysql,return a int number
	 * <p>int=0
	 * [1]no record effect
	 * [2]the record doesn't change at all
	 * </p>
	 * <p>int>0
	 * [1]number of records effect
	 * </p>
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeNonQuery(String sql) throws SQLException {
		if (sql == null)
			new SQLException("sql is null.");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		if (preparedStatement != null && !preparedStatement.isClosed())
			preparedStatement.close();

		preparedStatement = connection.prepareStatement(sql);
		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLException("DBHelper.executeNonQuery(): " + sql
					+ "\n\t" + ex.getMessage());
		}
	}

	/**
	 * PreparedStatement a SQL
	 * @param sql
	 * @return Object of preparedStatment
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if (sql == null)
			new SQLException("sql is null");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		try {
			return connection.prepareStatement(sql);
		} catch (SQLException ex) {
			throw new SQLException("DBHelper.prepareStatement(): " + sql
					+ "\n\t" + ex.getMessage());
		}
	}

	/**
	 * CallableStatement by SQL
	 * @param sql
	 * @return Object of CallableStatement
	 * @throws SQLException
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {
		if (sql == null)
			new SQLException("sql is null.");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		try {
			return connection.prepareCall(sql);
		} catch (SQLException ex) {
			throw new SQLException("DBHelper.prepareCall(): " + sql + "\n\t"
					+ ex.getMessage());
		}
	}

	/**
	 * BeginTrasaction by set auto commit false
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException {
		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		if (this.connection.getAutoCommit())
			this.connection.setAutoCommit(false);
	}

	/**
	 * BeginTransaction set a Savepoint
	 * @param name
	 * @return A savepoint
	 * @throws SQLException
	 */
	public Savepoint beginTransaction(String name) throws SQLException {
		if (name == null || name.trim().length() == 0)
			new SQLException("name is null or is empty.");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		if (this.connection.getAutoCommit())
			this.connection.setAutoCommit(false);

		return this.connection.setSavepoint(name);
	}

	/**
	 * Commit a transaction by manual
	 * @throws SQLException
	 */
	public void commitTransaction() throws SQLException {
		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		this.connection.commit();
	}

	/**
	 * Roll back Transaction
	 * @throws SQLException
	 */
	public void rollbackTransaction() throws SQLException {
		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		this.connection.rollback();
	}

	/**
	 * Roll back transaction to a savepoint
	 * @param savepoint
	 * @throws SQLException
	 */
	public void rollbackTransaction(Savepoint savepoint) throws SQLException {
		if (savepoint == null)
			throw new SQLException("savepoint is null.");

		if (this.connection == null || this.connection.isClosed())
			new SQLException("connection is null or is closed.");

		this.connection.rollback(savepoint);
	}

	/**
	 * Init class by close all resources
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * Close connection by resultSet parameter
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		Statement stmt = null;
		Connection conn = null;

		try {
			if (rs == null)
				return;

			stmt = rs.getStatement();
			if (stmt != null)
				conn = stmt.getConnection();

		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
			}

			try {
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
			}

			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Close connection by preparedStatement,connection parameters
	 * @param pstmt
	 * @param conn
	 */
	public static void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Close connection by connection parameter
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		}
	}

	public static void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Unit test for this class
	 * @param args 
	 */
	public static void main(String args[]) {
		// 1.使用DBHelper对象
		System.out.println("-------------------------------");
		DBHelper dbHelper = new DBHelper();
		try {
			dbHelper.open();

			// 查询SQL
			ResultSet rs = dbHelper
					.executeQuery("SELECT ID,url FROM news LIMIT 0, 10");
			while (rs.next()) {
				System.out.println("\tID=" + rs.getString(1) + " URL="
						+ rs.getString(2));
			}
			rs.close();

			// 非查询SQL
			int count = dbHelper
					.executeNonQuery("UPDATE news SET url = url WHERE ID = 0");
			System.out.println("Updated row count: " + count);

			// 非查询SQL--使用事务
			try {
				dbHelper.beginTransaction();
				dbHelper.executeNonQuery("UPDATE news SET url = url WHERE ID = 0");
				dbHelper.executeNonQuery("UPDATE news SET url = url WHERE ID = 1");
				dbHelper.executeNonQuery("UPDATE news SET url = url WHERE ID = 2");
				dbHelper.commitTransaction();
			} catch (SQLException e) {
				logger.debug("更新失败", e);
				dbHelper.rollbackTransaction();
			}

			// 打开数据库连接的自动提交
			dbHelper.setAutoCommit(true);

			System.out.println("\t更新数据成功！");
		} catch (Exception e) {
			logger.debug("数据库异常", e);
		} finally {
			dbHelper.close();
		}
		System.out.println("-------------------------------");

		// 2.直接使用获取的数据库连接
		Connection conn = null;
		try {
			conn = DBHelper.getConnection();

			// 数据库操作...
		} catch (Exception e) {
			logger.debug("数据库异常", e);
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Print SQL Exceptio on logs
	 * @param e
	 * @param sql
	 */
	public static void printSQLException(SQLException e, String sql) {
		if (e != null) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}

		if (sql != null) {
			logger.info("Related SQL or DB Operation: " + sql);
		}
	}

}
