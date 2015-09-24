package cl.buildersoft.timectrl.business.services.impl;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cl.buildersoft.framework.database.BSBeanUtils;
import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.ReportService;

public class ListToXExcelImpl extends AbstractReportService implements ReportService {
	protected String outputPath = null;
	protected String outputFile = null;
	protected String spName = null;
	protected String outputFileAndPath = null;
	protected String[] keyValues = {};
	protected String bgColorHeader = "#190033";
	protected String fontColorHeader = "#E0E0E0";
	protected String bgColorCell = "#FFFF99";
	protected String fontColorCell = "#660000";
	protected String fontName = "Trebuchet MS";
	protected Integer fontSize = 10;
	protected XSSFCellStyle headerStyle = null;
	protected XSSFCellStyle bodyStyle = null;
	protected XSSFCellStyle titleStyle = null;

	@Override
	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportParameterList) {
		List<String> out = new ArrayList<String>();

		readProperties(conn, reportPropertyList);

		configOutputPathAndFile();

		BSmySQL mysql = new BSmySQL();

		List<Object> params = getReportParams(conn, reportParameterList);
		processEmployeeParameter(conn, reportParameterList);
		ResultSet rs = mysql.callSingleSP(conn, this.spName, params);

		resultSetToFile(conn, rs);

		out.add(this.outputFileAndPath);

		return out;
	}

	protected void configOutputPathAndFile() {
		this.outputPath = parsePropertes(this.outputPath, keyValues);
		createPathIfNotExists(this.outputPath);
		this.outputFileAndPath = fixPath(this.outputPath) + parsePropertes(this.outputFile, keyValues);
	}

	private void resultSetToFile(Connection conn, ResultSet rs) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Listado");
		configStyles(workbook);
		try {
			resultSetToSheet(rs, sheet);

			FileOutputStream out = new FileOutputStream(new File(this.outputFileAndPath));
			workbook.write(out);
			workbook.close();
			out.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void resultSetToSheet(ResultSet rs, XSSFSheet sheet) throws SQLException {
		ResultSetMetaData metaData;
		metaData = rs.getMetaData();
		Integer colCount = metaData.getColumnCount();
		String[] colNames = new String[colCount];
		Integer i = 0;
		Integer j = 0;
		for (j = 1; j <= colCount; j++) {
			colNames[j - 1] = metaData.getColumnLabel(j);
		}


		XSSFRow row = sheet.createRow(i++);
		XSSFCell cell = null;
		j = 0;
		for (String colName : colNames) {
			cell = row.createCell(j++);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(colName);
		}

		Object value = null;
		while (rs.next()) {
			row = sheet.createRow(i++);

			for (j = 0; j < colCount; j++) {
				value = rs.getObject(j + 1);
				cell = createCell(row, j, bodyStyle, value);
			}
		}
	}

	protected XSSFCell createCell(XSSFRow row, Integer col, XSSFCellStyle style) {
		return createCell(row, col, style, null);
	}

	protected XSSFCell createCell(XSSFRow row, Integer col, XSSFCellStyle style, Object value) {
		XSSFCell out = row.createCell(col);
		out.setCellStyle(style);

		if (value == null) {
			out.setCellValue("");
		} else {
			if (value instanceof Long) {
				out.setCellValue((Long) value);
			} else if (value instanceof Integer) {
				out.setCellValue((Integer) value);
			} else if (value instanceof Boolean) {
				out.setCellValue((Boolean) value);
			} else {
				out.setCellValue(value.toString());
			}
		}
		return out;
	}

	protected void configStyles(XSSFWorkbook workbook) {
		this.headerStyle = getHeaderStyle(workbook);
		this.bodyStyle = getBodyStyle(workbook);
		this.titleStyle = getStyle(workbook, fontColorHeader, bgColorHeader, fontSize + 2);
	}

	private XSSFCellStyle getBodyStyle(XSSFWorkbook workbook) {
		return getStyle(workbook, fontColorCell, bgColorCell, fontSize);
	}

	private XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook) {
		return getStyle(workbook, fontColorHeader, bgColorHeader, fontSize);
	}

	private XSSFCellStyle getStyle(XSSFWorkbook workbook, String fontColor, String bgColor, Integer size) {
		XSSFCellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
		font.setColor(new XSSFColor(hex2Rgb(fontColor)));
		font.setFontName(fontName);
		font.setFontHeight(size);
		style.setFont(font);
		style.setFillForegroundColor(new XSSFColor(hex2Rgb(bgColor)));
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return style;
	}

	protected List<Object> getReportParams(Connection conn, List<ReportParameterBean> inParamList) {
		List<Object> out = new ArrayList<Object>();
		Object value = null;
		BSBeanUtils bu = new BSBeanUtils();
		for (ReportParameterBean reportParam : inParamList) {
			value = getParameterType(conn, reportParam.getJavaType(), bu, reportParam.getValue());

			// if (reportParam.getFromUser()) {
			out.add(value);
			// }
		}
		return out;
	}

	@Override
	protected Boolean getPropertyValue(String key, String value) {
		Boolean out = true;
		if ("OUTPUT_FILE".equalsIgnoreCase(key)) {
			this.outputFile = value;
		} else if ("OUTPUT_PATH".equalsIgnoreCase(key)) {
			this.outputPath = value;
		} else if ("SP".equalsIgnoreCase(key)) {
			this.spName = value;
		} else if ("BG_COLOR_HEAD".equalsIgnoreCase(key)) {
			bgColorHeader = value;
		} else if ("FONT_COLOR_HEAD".equalsIgnoreCase(key)) {
			fontColorHeader = value;
		} else if ("BG_COLOR_CELL".equalsIgnoreCase(key)) {
			bgColorCell = value;
		} else if ("FONT_COLOR_CELL".equalsIgnoreCase(key)) {
			fontColorCell = value;
		} else if ("FONT_NAME".equalsIgnoreCase(key)) {
			fontName = value;
		} else if ("FONT_SIZE".equalsIgnoreCase(key)) {
			fontSize = Integer.parseInt(value);
		} else {
			// System.out.println("Property '" + key + "' not found");
			out = false;
		}
		return out;
	}

	@Override
	protected String parseCustomVariable(String key) {
		return null;
	}

	public Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
}
