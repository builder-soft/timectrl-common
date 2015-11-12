package cl.buildersoft.timectrl.business.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cl.buildersoft.framework.database.BSmySQL;
import cl.buildersoft.framework.exception.BSConfigurationException;
import cl.buildersoft.framework.exception.BSDataBaseException;
import cl.buildersoft.framework.util.BSDateTimeUtil;
import cl.buildersoft.framework.util.BSUtils;
import cl.buildersoft.timectrl.business.beans.Employee;
import cl.buildersoft.timectrl.business.beans.ReportParameterBean;
import cl.buildersoft.timectrl.business.beans.ReportPropertyBean;
import cl.buildersoft.timectrl.business.beans.ReportType;
import cl.buildersoft.timectrl.business.services.EmployeeService;
import cl.buildersoft.timectrl.business.services.ReportService;

/** Este es el reporte a Excel que enlaza dos hojas en la misma planilla */
public class XExcel2Impl extends ListToXExcelImpl implements ReportService {
	private static final int ROWS_VERIFY_WIDTH = 10;
	private static final String FORMAT_DDMMYYYY = "dd-MM-yyyy";
	private static final int SUMMARY_COL_FIRST = 0;
	private static final int SUMMARY_COL_RUT = 0;
	private static final int DETAIL_COL_RUT = 1;
	private static final int DETAIL_COL_DATE = 4;
	private static final int DETAIL_COL_INDEX = 0;
	protected String spNameSummary = null;
	protected Integer colsAsTitle = null;
	protected Integer rowOfSheet = 0;
	protected Integer employeeDepth = 0;
	protected Integer currentDepth = null;
	private static final Logger LOG = Logger.getLogger(XExcel2Impl.class.getName());

	private void relationPages(XSSFWorkbook workBook) {
		Map<DataInSheet, Integer> detailResult = inspectDetail(workBook);
		inspectSummary(workBook, detailResult);

	}

	private void autoSizeColumn(XSSFWorkbook workBook) {
		XSSFSheet summary = workBook.getSheetAt(0);
		XSSFSheet detailSheet = workBook.getSheetAt(1);

		XSSFSheet[] sheets = { summary, detailSheet };
		Iterator<Cell> cells = null;
		Integer index = null;
		Integer lastCellNum = 0;

		for (XSSFSheet sheet : sheets) {
			Integer tryCount = 0;
			XSSFRow row = null;

			Integer maxCol = getMaxCol(sheet);
			Integer currentCol = maxCol;

			while (row == null && tryCount < ROWS_VERIFY_WIDTH) {
				tryCount++;
				row = sheet.getRow(currentCol);
				if (row != null) {
					lastCellNum = (int) row.getLastCellNum();
					if (lastCellNum != maxCol) {
						row = null;
					}
				}
				currentCol--;
			}
			cells = row.cellIterator();
			index = 0;

			while (cells.hasNext()) {
				cells.next();
				sheet.autoSizeColumn(index++);
			}
		}
	}

	private Integer getMaxCol(XSSFSheet sheet) {
		Integer out = 0;
		Short cellNum = 0;
		XSSFRow row = null;
		for (Integer i = 0; i < ROWS_VERIFY_WIDTH; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				cellNum = row.getLastCellNum();
				if ((int) cellNum > out) {
					out = (int) cellNum;
				}
			}
		}

		return out;
	}

	private Map<DataInSheet, Integer> inspectSummary(XSSFWorkbook workBook, Map<DataInSheet, Integer> detailResult) {
		XSSFSheet sheet = workBook.getSheetAt(0);
		// XSSFSheet detail = workBook.getSheetAt(1);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;
		Integer lastRowNumber = sheet.getLastRowNum();

		Map<DataInSheet, Integer> out = new HashMap<DataInSheet, Integer>();

		Cell firstCell = null, cellRut = null;
		// Cell cellDate = null;
		Integer rowIndex = 0, position = 0;
		DataInSheet dataInSheet = null;
		String value = null;
		String startDateValue = null;
		Calendar startDate = null;
		Boolean doContinue = true, firstLoop = true;

		XSSFCellStyle hlink_style = (XSSFCellStyle) this.bodyStyle.clone();
		XSSFFont hlink_font = workBook.createFont();
		hlink_font.setUnderline(Font.U_SINGLE);
		hlink_font.setColor(IndexedColors.BLUE.getIndex());
		hlink_style.setFont(hlink_font);

		while (rowIterator.hasNext()) {
			if (firstLoop) {
				row = rowIterator.next();
			}

			firstCell = row.getCell(SUMMARY_COL_FIRST);
			if (firstCell.getCellType() == Cell.CELL_TYPE_STRING) {
				value = firstCell.getStringCellValue();
				if (value.toLowerCase().startsWith("inicio")) {
					firstLoop = false;
					position = value.indexOf(":");
					startDateValue = value.substring(position + 1);
					startDate = BSDateTimeUtil.string2Calendar(startDateValue, "yyyy-MM-dd");

					row = rowIterator.next();
					if (rowIterator.hasNext()) {
						firstCell = row.getCell(SUMMARY_COL_FIRST);
						if (firstCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (firstCell.getStringCellValue().toLowerCase().startsWith("rut")) {
								row = rowIterator.next();
								doContinue = true;
								while (doContinue && row.getRowNum() <= lastRowNumber) {
									cellRut = row.getCell(SUMMARY_COL_RUT);

									if (cellRut.getCellType() == Cell.CELL_TYPE_STRING) {
										value = cellRut.getStringCellValue();
										if (isRut(value)) {
											dataInSheet = new DataInSheet();

											dataInSheet.setMonth(startDate.get(Calendar.MONTH));
											dataInSheet.setYear(startDate.get(Calendar.YEAR));
											dataInSheet.setRut(value);

											CreationHelper createHelper = workBook.getCreationHelper();
											Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);

											Integer rowNumber = detailResult.get(dataInSheet);
											if (rowNumber != null) {
												String address = "'Detalle'!B" + rowNumber;
												link.setAddress(address);
												cellRut.setHyperlink(link);
												cellRut.setCellStyle(hlink_style);
											}

											if (rowIterator.hasNext()) {
												row = rowIterator.next();
											} else {
												doContinue = false;
											}

										} else {
											doContinue = false;
										}
									}
								}
							}
						}
					}
				}
			} else {
				rowIndex++;
			}

		}
		return out;
	}

	private Boolean isRut(String mayBeRut) {
		Boolean out = false;
		Integer position = mayBeRut.indexOf("-");
		if (position > -1) {
			if (BSUtils.isNumber(mayBeRut.substring(0, position))) {
				if (mayBeRut.substring(position + 1, mayBeRut.length()).length() == 1) {
					out = true;
				}
			}
		}
		return out;
	}

	private Map<DataInSheet, Integer> inspectDetail(XSSFWorkbook workBook) {
		XSSFSheet sheet = workBook.getSheetAt(1);
		Iterator<Row> rowIterator = sheet.iterator();
		Row row = null;
		Map<DataInSheet, Integer> out = new HashMap<DataInSheet, Integer>();

		Cell cellIndex = null;
		Cell cellDate = null;
		Integer rowIndex = 0;
		DataInSheet dataInSheet = null;

		while (rowIterator.hasNext()) {
			row = rowIterator.next();

			rowIndex++;

			cellIndex = row.getCell(DETAIL_COL_INDEX);
			cellDate = row.getCell(DETAIL_COL_DATE);

			String cellDateString = cellDate.toString();

			if (cellIndex.getCellType() == Cell.CELL_TYPE_NUMERIC && BSDateTimeUtil.isValidDate(cellDateString, FORMAT_DDMMYYYY)) {
				dataInSheet = new DataInSheet();

				Calendar calendar = BSDateTimeUtil.string2Calendar(cellDateString, FORMAT_DDMMYYYY);

				dataInSheet.setRut(row.getCell(DETAIL_COL_RUT).getStringCellValue());
				dataInSheet.setMonth(calendar.get(Calendar.MONTH));
				dataInSheet.setYear(calendar.get(Calendar.YEAR));

				if (!out.containsKey(dataInSheet)) {
					out.put(dataInSheet, row.getRowNum() + 1);
				}

			}
		}

		return out;
	}

	@Override
	public List<String> execute(Connection conn, Long idReport, ReportType reportType,
			List<ReportPropertyBean> reportPropertyList, List<ReportParameterBean> reportInputParameterList) {
		List<String> out = new ArrayList<String>();

		readProperties(conn, reportPropertyList);

		configOutputPathAndFile();
		processBossAndEmployeeParameter(conn, reportInputParameterList, idReport);

		List<Object> params = getReportParams(conn, reportInputParameterList);

		XSSFWorkbook workbook = new XSSFWorkbook();
		super.configStyles(workbook);
		createSummarySheet(conn, workbook, params, getTitleSummary(conn, reportInputParameterList));
		createDetailSheet(conn, workbook, params);

		try {
			relationPages(workbook);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "We can not make relation between sheets", e);
		}
		try {
			autoSizeColumn(workbook);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "We can not resize columns of sheets", e);
		}

		FileOutputStream stream = saveFile(workbook);
		closeFile(workbook, stream);

		out.add(this.outputFileAndPath);

		return out;
	}

	private void processBossAndEmployeeParameter(Connection conn, List<ReportParameterBean> reportInputParameterList,
			Long idReport) {
		Integer index = 1;
		for (ReportParameterBean param : reportInputParameterList) {
			if ("BOSS_LIST".equals(param.getTypeKey())) {
				reportInputParameterList.add(index, newParam(conn, param, idReport));
				break;
			}
			index++;
		}
	}

	private ReportParameterBean newParam(Connection conn, ReportParameterBean bossParam, Long idReport) {
		ReportParameterBean out = new ReportParameterBean();

		this.currentDepth = 1;
		String employeeIds = getEmployeeIds(conn, Long.parseLong(bossParam.getValue()));
		out.setJavaType("STRING");
		out.setName("EmployeesId");
		out.setReport(idReport);
		out.setTypeKey("EMPLOYEE_LIST");
		out.setValue(employeeIds);
		return out;
	}

	private String getEmployeeIds(Connection conn, Long boss) {
		String out = "";

		String sql = "SELECT cId FROM tEmployee WHERE cBoss=?";
		BSmySQL mysql = new BSmySQL();
		ResultSet rs = mysql.queryResultSet(conn, sql, boss);

		try {
			while (rs.next()) {
				Long employee = rs.getLong(1);

				if (haveJunior(conn, employee, mysql) && boss != employee) {
					if (this.employeeDepth == 0) {
						out += getEmployeeIds(conn, employee) + ",";
					} else {
						if (this.currentDepth < this.employeeDepth) {
							this.currentDepth++;
							out += getEmployeeIds(conn, employee) + ",";
							this.currentDepth--;

						}
					}
				}
				out += employee.toString() + ",";
			}
			mysql.closeSQL(rs);
		} catch (SQLException e) {
			throw new BSDataBaseException(e);
		}
		return out.length() > 0 ? out.substring(0, out.length() - 1) : "";
	}

	private boolean haveJunior(Connection conn, Long employee, BSmySQL mysql) {
		String sql = "SELECT COUNT(cId) FROM tEmployee WHERE cBoss=?";
		String count = mysql.queryField(conn, sql, employee);
		return Integer.parseInt(count) > 0;
	}

	private String getTitleSummary(Connection conn, List<ReportParameterBean> reportInputParameterList) {
		String out = null;
		Boolean firstLoop = true;
		String bossId = null;
		String startMonth = null;
		String startYear = null;
		String endMonth = null;
		String endYear = null;
		for (ReportParameterBean rip : reportInputParameterList) {
			if ("BOSS_LIST".equals(rip.getTypeKey())) {
				bossId = rip.getValue();
			} else if ("MONTH".equals(rip.getTypeKey())) {
				if (firstLoop) {
					startMonth = rip.getValue();
				} else {
					endMonth = rip.getValue();
				}
			} else if ("YEAR".equals(rip.getTypeKey())) {
				if (firstLoop) {
					startYear = rip.getValue();
				} else {
					endYear = rip.getValue();
				}
				firstLoop = false;
			}
		}
		if (bossId == null || startMonth == null || startYear == null || endMonth == null || endYear == null) {
			out = "Resumen de informe.";
		} else {
			Calendar startDate = BSDateTimeUtil.string2Calendar("1-" + startMonth + "-" + startYear, "dd-MM-yyyy");
			String format = BSDateTimeUtil.getFormatDate(conn);
			String startDateString = BSDateTimeUtil.calendar2String(startDate, format);

			String endDateString = getLastDayOfMonth(endMonth, endYear, format);

			// startMonth
			// startYear
			//
			// endMonth
			// endYear

			out = "Supervisor:" + getEmployeeName(conn, bossId) + ". Desde: " + startDateString + " hasta " + endDateString;
		}

		return out;
	}

	private String getLastDayOfMonth(String endMonth, String endYear, String format) {
		Calendar calendar = BSDateTimeUtil.string2Calendar("1-" + endMonth + "-" + endYear, "dd-MM-yyyy");

		calendar.add(Calendar.MONTH, 1);
		// calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		return BSDateTimeUtil.calendar2String(calendar, format);
	}

	private String getEmployeeName(Connection conn, String bossId) {
		EmployeeService service = new EmployeeServiceImpl();
		Employee employee = service.getEmployee(conn, Long.parseLong(bossId));
		return employee.getName();
	}

	private void createDetailSheet(Connection conn, XSSFWorkbook workbook, List<Object> params) {
		XSSFSheet detailSheet = workbook.createSheet("Detalle");
		/**
		 * <code>
		 * ejecuta sp
		 * recorre respuesta y la graba en detailSheet
		 * 
		 </code>
		 */

		BSmySQL mysql = new BSmySQL();
		// ResultSet rs = mysql.callSingleSP(conn, spName, params);
		List<List<Object[]>> rss = mysql.callComplexSP(conn, spName, params, true);
		this.rowOfSheet = 0;

		Integer i = 0, j = 0;
		XSSFRow row = null; // summarySheet.createRow(this.rowOfSheet++);
		for (List<Object[]> rs : rss) {
			i = 0;
			// Object[] headerLine = null;
			// Object[] firstLine = null;
			// Object[] headerData = new Object[this.colsAsTitle];
			for (Object[] line : rs) {
				j = 0;
				// if (i == 0) {
				// headerLine = line;
				// } else if (i == 1) {
				//
				// firstLine = line;
				// for (int k = 0; k <= this.colsAsTitle - 1; k++) {
				// headerData[k] = headerLine[k].toString() + " : " +
				// firstLine[k].toString();
				//
				// }
				// row = summarySheet.createRow(this.rowOfSheet++);
				// writeDataLine(0, j, row, headerData, true);
				//
				// row = summarySheet.createRow(this.rowOfSheet++);
				// writeDataLine(0, j, row, headerLine, false);
				// row = summarySheet.createRow(this.rowOfSheet++);
				// writeDataLine(i, j, row, firstLine, false);
				// } else {
				row = detailSheet.createRow(this.rowOfSheet++);
				writeDataLine(i, j, row, line, true, i == 0 ? this.headerStyle : this.bodyStyle);
				// }
				i++;
			}
			this.rowOfSheet++;
		}

	}

	private void createSummarySheet(Connection conn, XSSFWorkbook workbook, List<Object> params, String title) {
		Integer i = null;
		Integer j = null;
		BSmySQL mysql = new BSmySQL();
		List<List<Object[]>> rss = mysql.callComplexSP(conn, spNameSummary, params, true);

		XSSFSheet summarySheet = workbook.createSheet("Resumen");
		// XSSFCell cell = null;

		XSSFRow row = summarySheet.createRow(this.rowOfSheet++);
		createCell(row, 0, super.titleStyle, title);
		this.rowOfSheet++;
		// XSSFCellStyle style = null;

		/**
		 * <code>
		 * CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
</code>
		 */
		summarySheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

		for (List<Object[]> rs : rss) {
			i = 0;
			Object[] headerLine = null;
			Object[] firstLine = null;
			Object[] headerData = new Object[this.colsAsTitle];
			for (Object[] line : rs) {
				j = 0;
				if (i == 0) {
					headerLine = line;
				} else if (i == 1) {
					/**
					 * <code>
					   rescata fila 1, 
					   rescata col 1 y 2, 
					   print parameters table, 
					   print header, 
					   imprime fila 1
					 </code>
					 */
					firstLine = line;
					for (int k = 0; k <= this.colsAsTitle - 1; k++) {
						headerData[k] = headerLine[k].toString() + " : " + firstLine[k].toString();
						// headerData[k]="";
					}
					row = summarySheet.createRow(this.rowOfSheet++);
					writeDataLine(0, j, row, headerData, true);
					// summarySheet.addMergedRegion(new
					// CellRangeAddress(this.rowOfSheet-1, this.rowOfSheet-1, 0,
					// 1));
					// summarySheet.addMergedRegion(new
					// CellRangeAddress(this.rowOfSheet-1, this.rowOfSheet-1, 2,
					// 3));
					row = summarySheet.createRow(this.rowOfSheet++);
					writeDataLine(0, j, row, headerLine, false);
					row = summarySheet.createRow(this.rowOfSheet++);
					writeDataLine(i, j, row, firstLine, false);
				} else {
					/** Imprime fila de manera normal */
					row = summarySheet.createRow(this.rowOfSheet++);
					writeDataLine(i, j, row, line, false);
				}
				i++;
			}
			this.rowOfSheet++;
			// this.rowOfSheet++;
		}
		/**
		 * <code>
 </code>
		 */
	}

	private void writeDataLine(Integer i, Integer j, XSSFRow row, Object[] line, Boolean force) {
		writeDataLine(i, j, row, line, force, null);
	}

	private void writeDataLine(Integer i, Integer j, XSSFRow row, Object[] line, Boolean force, XSSFCellStyle style) {
		XSSFCellStyle customStyle = null;
		for (Object data : line) {
			if (force) {
				if (style == null) {
					customStyle = super.headerStyle;
				} else {
					customStyle = style;
				}
				createCell(row, j++, customStyle, data);
			} else {
				if (j >= this.colsAsTitle) {
					if (i == 0) {
						customStyle = style == null ? super.headerStyle : style;
					} else {
						customStyle = style == null ? super.bodyStyle : style;
					}
					createCell(row, (j - this.colsAsTitle), customStyle, data);
					j++;
				} else {
					j++;
				}
			}
		}
	}

	private FileOutputStream saveFile(XSSFWorkbook workbook) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(this.outputFileAndPath));
			workbook.write(out);
		} catch (IOException e) {
			throw new BSConfigurationException(e);
		}
		return out;
	}

	private void closeFile(XSSFWorkbook workbook, FileOutputStream stream) {
		try {
			workbook.close();
			stream.close();
		} catch (IOException e) {
			throw new BSConfigurationException(e);
		}

	}

	@Override
	protected Boolean getPropertyValue(String key, String value) {
		Boolean out = true;
		if (!super.getPropertyValue(key, value)) {
			if ("SP_SUMMARY".equalsIgnoreCase(key)) {
				this.spNameSummary = value;
			} else if ("COLS_AS_TITLE".equalsIgnoreCase(key)) {
				this.colsAsTitle = Integer.parseInt(value);
			} else if ("EMPLOYEE_DEPTH".equalsIgnoreCase(key)) {
				this.employeeDepth = Integer.parseInt(value);
			} else {
				out = false;
			}
		}
		return out;
	}

	@Override
	protected String parseCustomVariable(String key) {
		return null;
	}

}

class DataInSheet {
	private String rut = null;
	private Integer month = null;
	private Integer year = null;

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "\nDataInSheet [rut=" + rut + ", month=" + month + ", year=" + year + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((rut == null) ? 0 : rut.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DataInSheet)) {
			return false;
		}
		DataInSheet other = (DataInSheet) obj;
		if (month == null) {
			if (other.month != null) {
				return false;
			}
		} else if (!month.equals(other.month)) {
			return false;
		}
		if (rut == null) {
			if (other.rut != null) {
				return false;
			}
		} else if (!rut.equals(other.rut)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}
}