package com.hp.dbpowerpack.common.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanUtils;

import com.hp.dbpowerpack.common.model.ExcelModel;


/**
 * The Class ExcelUtil.
 */
public class ExcelUtil {

	/**
	 * Generate excel report.
	 * 
	 * @param response
	 *            the response
	 * @param excelModel
	 *            the excel model
	 * @return the http servlet response
	 */
	public static HttpServletResponse generateExcelReport(
			HttpServletResponse response, ExcelModel excelModel) {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ excelModel.getFileName());
		WritableWorkbook workbook = null;

		try {
			workbook = Workbook.createWorkbook(response.getOutputStream());
			workbook = generateExcel(workbook, excelModel);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the workbook
			if (workbook != null) {
				try {
					workbook.close();
				} catch (WriteException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}

		return response;

	}

	/**
	 * Generate excel report.
	 * 
	 * @param excelModel
	 *            the excel model
	 * @return the file
	 */
	public static File generateExcelReport(ExcelModel excelModel) {
		File file = new File(excelModel.getFileName());
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(file);
			workbook = generateExcel(workbook, excelModel);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the workbook
			if (workbook != null) {
				try {
					workbook.close();
				} catch (WriteException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

		}

		return file;

	}

	/**
	 * Generate excel.
	 * 
	 * @param workbook
	 *            the workbook
	 * @param excelModel
	 *            the excel model
	 * @return the writable workbook
	 */
	private static WritableWorkbook generateExcel(WritableWorkbook workbook,
			ExcelModel excelModel) {

		try {
			// create work sheet
			WritableSheet workSheet = null;
			workSheet = workbook.createSheet(excelModel.getSheetName(), 0);
			WritableCellFormat headerFormat = getHeaderFormat();
			WritableCellFormat redFormat = getRedFormat();
			WritableCellFormat GreenFormat = getGreenFormat();
			WritableCellFormat blackFormat = getBlackFormat();

			// write to datasheet
			int columnCnt = 0;
			int rowCnt = 0;
			/*
			 * for(int i=0;i<dbNameList.size();i++){ workSheet.mergeCells(i,
			 * rowCnt, i+1, rowCnt); i++; }
			 */

			if (excelModel.isDbNameHeader()) {
				for (String dbName : excelModel.getDbNameList()) {

					workSheet.addCell(new jxl.write.Label(columnCnt, rowCnt,
							dbName, headerFormat));
					workSheet.mergeCells(columnCnt, rowCnt, columnCnt
							+ excelModel.getColumnCount(), rowCnt);
					columnCnt = columnCnt + excelModel.getColumnCount() + 1;
				}
				rowCnt++;
			}

			if (excelModel.isDBHeader()) {

				columnCnt = 0;
				for (String headerName : excelModel.getHeaderList()) {
					if (!"versionMap".equals(headerName)) {
						workSheet.addCell(new jxl.write.Label(columnCnt,
								rowCnt, headerName, headerFormat));
						columnCnt++;
					}

				}
			}

			rowCnt++;
			columnCnt = 0;

			Object bean;
			for (final Iterator<?> iterator = excelModel.getObjLst().iterator(); iterator
					.hasNext();) {
				bean = iterator.next();
				int columnCount = 0;
				for (String fieldName : excelModel.getFieldNameList()) {
					String value = null;
					String colorFlag = "";
					if (excelModel.isColorFlag()) {
						colorFlag = BeanUtils.getProperty(bean, "colorFlag");
					}
					if ("java.lang.String".equals(fieldName)) {
						value = bean.toString();
					} else {
						value = BeanUtils.getProperty(bean, fieldName);
					}

					if ("versionMap".equals(fieldName)) {
						Map<String, String> valueMap = convertToMap(value);
						for (String dbName : excelModel.getDbNameList()) {
							String version = valueMap.get(dbName);
							if (version == null) {
								version = "";
							}
							if ("Red".equals(colorFlag)) {
								workSheet.addCell(new jxl.write.Label(
										columnCount, rowCnt, String
												.valueOf(version), redFormat));
							} else if ("Green".equals(colorFlag)) {
								workSheet
										.addCell(new jxl.write.Label(
												columnCount, rowCnt, String
														.valueOf(version),
												GreenFormat));
							} else {
								workSheet
										.addCell(new jxl.write.Label(
												columnCount, rowCnt, String
														.valueOf(version),
												blackFormat));
							}
							columnCount++;
						}

					} else {

						if (value == null) {
							value = "";
						}

						if ("Red".equals(colorFlag) || "RED".equals(colorFlag)) {
							workSheet.addCell(new jxl.write.Label(columnCount,
									rowCnt, String.valueOf(value), redFormat));
						} else if ("Green".equals(colorFlag)
								|| "GREEN".equals(colorFlag)
								|| "BLUE".equals(colorFlag)) {
							workSheet
									.addCell(new jxl.write.Label(columnCount,
											rowCnt, String.valueOf(value),
											GreenFormat));
						} else {
							workSheet
									.addCell(new jxl.write.Label(columnCount,
											rowCnt, String.valueOf(value),
											blackFormat));
						}

					}

					columnCount++;
				}
				rowCnt++;
			}

			// write to the excel sheet
			workbook.write();

		} catch (RowsExceededException e) {

			e.printStackTrace();
		} catch (WriteException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (NoSuchMethodException e) {

			e.printStackTrace();
		}

		return workbook;

	}

	/**
	 * Convert to map.
	 *
	 * @param value the value
	 * @return the map
	 */
	private static Map<String, String> convertToMap(String value) {
		Map<String, String> valueMap = new HashMap<String, String>();
		value = value.replace('{', ' ');
		value = value.replace('}', ' ');
		String[] valueArr = value.split(",");
		for (int i = 0; i < valueArr.length; i++) {
			String strValue = valueArr[i];
			String[] versionArr = strValue.split("=");
			valueMap.put(versionArr[0].trim(), versionArr[1].trim());
		}

		return valueMap;
	}

	/**
	 * Gets the header format.
	 * 
	 * @return the header format
	 */
	private static WritableCellFormat getHeaderFormat() {
		// Creating Writable font to be used in the report
		WritableFont headerFont = new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE);

		// creating plain format to write data in excel sheet
		WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
		try {
			headerFormat.setWrap(true);
			headerFormat.setAlignment(jxl.format.Alignment.CENTRE);
			headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			headerFormat.setWrap(false);
			headerFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THICK, jxl.format.Colour.BLACK);
		} catch (WriteException e) {

			e.printStackTrace();
		}

		return headerFormat;
	}

	/**
	 * Gets the red format.
	 * 
	 * @return the red format
	 */
	private static WritableCellFormat getRedFormat() {
		// Creating Writable font to be used in the report
		WritableFont redFont = new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);

		// creating plain format to write data in excel sheet
		WritableCellFormat redFormat = new WritableCellFormat(redFont);
		try {
			redFormat.setWrap(true);
			redFormat.setAlignment(jxl.format.Alignment.LEFT);
			redFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			redFormat.setWrap(false);
			redFormat.setBackground(jxl.format.Colour.YELLOW);
			redFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
		} catch (WriteException e) {

			e.printStackTrace();
		}

		return redFormat;

	}

	/**
	 * Gets the Green format.
	 * 
	 * @return the Green format
	 */
	private static WritableCellFormat getGreenFormat() {
		// Creating Writable font to be used in the report
		WritableFont GreenFont = new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

		// creating plain format to write data in excel sheet
		WritableCellFormat GreenFormat = new WritableCellFormat(GreenFont);
		try {
			GreenFormat.setWrap(true);
			GreenFormat.setAlignment(jxl.format.Alignment.LEFT);
			GreenFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			GreenFormat.setWrap(false);
			GreenFormat.setBackground(jxl.format.Colour.LIGHT_GREEN);
			GreenFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
		} catch (WriteException e) {

			e.printStackTrace();
		}

		return GreenFormat;
	}

	/**
	 * Gets the black format.
	 * 
	 * @return the black format
	 */
	private static WritableCellFormat getBlackFormat() {
		// Creating Writable font to be used in the report
		WritableFont blackFont = new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);

		// creating plain format to write data in excel sheet
		WritableCellFormat blackFormat = new WritableCellFormat(blackFont);
		try {
			blackFormat.setWrap(true);
			blackFormat.setAlignment(jxl.format.Alignment.LEFT);
			blackFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			blackFormat.setWrap(false);
			blackFormat.setBorder(jxl.format.Border.ALL,
					jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);
		} catch (WriteException e) {

			e.printStackTrace();
		}

		return blackFormat;
	}
}
