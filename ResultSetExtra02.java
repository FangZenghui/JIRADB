package com.qiankun;

import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;
import java.io.Writer;

public class ResultSetExtra02{
	
	public static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	
	public static final String DBURL = "jdbc:mysql://localhost:3306/jiradb";
	
	public static final String DBUSER = "jirauser";
	
	public static final String DBPASS = "jira";
	
	public static void main(String args[]) throws Exception {
		
		Connection conn = null;		
		Statement stmt = null;		
		ResultSet rs = null;		
		String sql = "SELECT ID,SUMMARY,ASSIGNEE,DUEDATE,RESOLUTION FROM jiraissue";
		Class.forName(DBDRIVER);	
		conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		Date nowdate = new Date();
		DateFormat df = DateFormat.getDateInstance();
		int i = 1;
		
		File f = new File("C:"+File.separator+"Users"+File.separator+"qiankun"+File.separator+"Desktop"+File.separator+"jiradb"+File.separator+"jiradb.txt");
		Writer out = new FileWriter(f);
		out.write("当前日期："+ df.format(nowdate) +", 延期任务列表：\r\n");
		
		while(rs.next()){	
			int id = rs.getInt("ID");	
			String summary = rs.getString("SUMMARY");
			String assignee = rs.getString("ASSIGNEE");
			java.util.Date duedate = rs.getDate("DUEDATE");
			int resolution = rs.getInt("RESOLUTION");
			if(duedate.before(nowdate)&&resolution!=1) {
				
				out.write("序号：" + i + "；");
				out.write("ID：" + id + "；");
				out.write("名称：" + summary + "；");
				out.write("经办人：" + assignee + "；");
				out.write("截止日期：" + duedate + "；");
				out.write("延期天数：" + (nowdate.getTime()-duedate.getTime())/1000/60/60/24 + "天；\r\n");
				out.write("---------------------------------------------------\r\n");
				i++;
			}	
		}
		rs.close();
		stmt.close();
		conn.close();	
		out.close();
	}
}

