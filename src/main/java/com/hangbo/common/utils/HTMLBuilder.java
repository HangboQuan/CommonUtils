package com.hangbo.common.utils;

/**
 * @author quanhangbo
 * @date 22-9-19 下午7:54
 */

/**
 * 可以在Java中构建表格信息, 一般可用来发送邮件
 */
public class HTMLBuilder {

	private final StringBuilder body = new StringBuilder();
	public static String HTML_START = "<html>";
	public static String STYLE = "<style>" +
			"table {\n" +
			"    border-collapse: collapse;\n" +
			"}\n" +
			"th {\n" +
			"    background-color: #00BFFF;\n" +
			"    color: white;\n" +
			"}\n" +
			"table, th, td {\n" +
			"    border: 1px solid black;\n" +
			"    padding: 5px;\n" +
			"    text-align: left;\n" +
			"}</style>";
	public static String HTML_BODY_START = "<body>";
	public static String HTML_BODY_END = "</body>";
	public static String HTML_END = "</html>";


	public HTMLBuilder(){
		body.append(HTML_START);
		body.append(STYLE);
		body.append(HTML_BODY_START);
	}


	public String build() {
		body.append(HTML_BODY_END);
		body.append(HTML_END);
		return body.toString();
	}


	public void buildHtmlContent(String content) {
		body.append(content);
	}




	static class TableBuilder{
		int columns;
		private StringBuilder table = new StringBuilder();
		public static String TABLE_START = "<table>";
		public static String TABLE_END = "</table>";
		public static String TABLE_LINE_START = "<tr>";
		public static String TABLE_LINE_END = "</tr>";
		public static String TABLE_HEAD_START = "<th>";
		public static String TABLE_HEAD_END = "</th>";
		public static String TABLE_CONTENT_START = "<td>";
		public static String TABLE_CONTENT_END = "</td>";

		public TableBuilder(int columns, String description) {
			this.columns = columns;
			table.append(TABLE_START);
		}

		public String build() {
			return table.append(TABLE_END).toString();
		}


		public void buildHeadValues(String ... values) {
			if(values.length != this.columns) {
				System.out.println("ERROR COLUMN LENGTH");
			}
			table.append(TABLE_LINE_START);

			for(int i = 0; i < values.length; i ++ ) {
				table.append(TABLE_HEAD_START);
				table.append(values[i]);
				table.append(TABLE_HEAD_END);
			}

			table.append(TABLE_LINE_END);
		}


		public void buildRowValues(String ... values) {
			if(values.length != this.columns) {
				System.out.println("ERROR COLUMN LENGTH");
			}
			table.append(TABLE_LINE_START);

			for(int i = 0; i < values.length; i ++ ) {
				table.append(TABLE_CONTENT_START);
				table.append(values[i]);
				table.append(TABLE_CONTENT_END);
			}

			table.append(TABLE_LINE_END);
		}

		public static void main(String[] args) {
			HTMLBuilder htmlBuilder = new HTMLBuilder();
			TableBuilder tableBuilder = new TableBuilder(4, null);
			tableBuilder.buildHeadValues("A", "B", "C", "D");
			tableBuilder.buildRowValues("1A", "1B", "1C", "1D");
			tableBuilder.buildRowValues("2A", "2B", "2C", "2D");
			tableBuilder.buildRowValues("3A", "3B", "3C", "3D");

			String html = tableBuilder.build();
			htmlBuilder.buildHtmlContent(html);
			String value = htmlBuilder.build();
			System.out.println(value.toString());
		}
	}
}
