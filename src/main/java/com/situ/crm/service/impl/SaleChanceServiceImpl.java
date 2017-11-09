package com.situ.crm.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.crm.common.DataGridResult;
import com.situ.crm.common.ServerResponse;
import com.situ.crm.mapper.SaleChanceMapper;
import com.situ.crm.pojo.SaleChance;
import com.situ.crm.pojo.SaleChanceExample;
import com.situ.crm.pojo.SaleChanceExample.Criteria;
import com.situ.crm.service.ISaleChanceService;
import com.situ.crm.util.Util;

@Service
public class SaleChanceServiceImpl implements ISaleChanceService{
	@Autowired
	private SaleChanceMapper saleChanceMapper;

	@Override
	public DataGridResult findAll(Integer page, Integer rows, SaleChance saleChance, Date beginTime, Date endTime) {
		DataGridResult result = new DataGridResult();
		SaleChanceExample saleChanceExample = new SaleChanceExample();
		//1、设置分页 
		PageHelper.startPage(page, rows);
		//2、执行查询
		//rows(分页之后的数据)
		Criteria createCriteria = saleChanceExample.createCriteria();
		if (StringUtils.isNotEmpty(saleChance.getCustomerName())) {
			createCriteria.andCustomerNameLike(Util.formatLike(saleChance.getCustomerName()));
		}
		if (StringUtils.isNotEmpty(saleChance.getLinkMan())) {
			createCriteria.andLinkManLike(Util.formatLike(saleChance.getLinkMan()));
		}
		if (StringUtils.isNotEmpty(saleChance.getCreateMan())) {
			createCriteria.andCreateManLike(Util.formatLike(saleChance.getCreateMan()));
		}
		if (saleChance.getStatus() != null) {
			createCriteria.andStatusEqualTo(saleChance.getStatus());
		}
		if (beginTime != null && endTime != null) {
			createCriteria.andCreateTimeBetween(beginTime, endTime);
		}
		
		List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);
		//total
		PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceList);
		int total = (int)pageInfo.getTotal();
		
		result.setTotal(total);
		result.setRows(saleChanceList);
		return result;
	}

	@Override
	public ServerResponse delete(String ids) {
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			saleChanceMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		return ServerResponse.createSUCCESS("数据已经成功删除");
	}

	@Override
	public ServerResponse add(SaleChance saleChance) {
		
		if (saleChance.getAssignMan() == null && saleChance.getAssignMan().trim().equals("")) {
			saleChance.setStatus(0);
		}
		if (saleChance.getAssignMan().equals("--暂不指派--")) {
			saleChance.setAssignMan(null);
			saleChance.setStatus(0);
		}
		
		if (saleChanceMapper.insert(saleChance) > 0) {
			return ServerResponse.createSUCCESS("添加成功! ");
		}
		return ServerResponse.createERROR("添加失败!");
	}

	@Override
	public ServerResponse update(SaleChance saleChance) {
		saleChance.setStatus(1);
		if (saleChanceMapper.updateByPrimaryKeySelective(saleChance) > 0) {
			return ServerResponse.createSUCCESS("修改成功! ");
		}
		return ServerResponse.createERROR("修改失败!");
	}

	@Override
	public ServerResponse<SaleChance> findSaleChanceById(Integer saleChanceId) {
		SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(saleChanceId);
		if (saleChance != null) {
			return ServerResponse.createSUCCESS(saleChance);
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后重试");
	}

	@Override
	public ServerResponse updateSaleChanceDevResult(SaleChance saleChance) {
		int result = saleChanceMapper.updateByPrimaryKeySelective(saleChance);
		
		if (result > 0) {
			return ServerResponse.createSUCCESS("更改成功");
		}
		return ServerResponse.createERROR("服务器繁忙，请稍后再试");
	}

	@Override
	public void createExcel(ServletOutputStream outputStream) throws IOException {
		
		List<SaleChance> list = saleChanceMapper.selectByExample(new SaleChanceExample());
		//1.创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//1.1创建合并单元格对象
		CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 7);
		//1.2创建头样式
		HSSFCellStyle style = createCellStyle(workbook,(short)20);
		//1.3创建列标题样式
		HSSFCellStyle style2 = createCellStyle(workbook, (short) 13);
		//2.创建工作表
		HSSFSheet sheet = workbook.createSheet("用户列表");
		//2.1 加载合并单元格对象
		sheet.addMergedRegion(rangeAddress);
		//2.2 设置默认列宽
		sheet.setDefaultColumnWidth(20);
		//3.创建行
		//3.1 创建头标题行，并设置头标题
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell1 = row1.createCell(0);
		//加载单元格样式
		cell1.setCellStyle(style);
		cell1.setCellValue("用户列表");
		
		//3.2 创建列标题，设置列标题
		HSSFRow rowHead = sheet.createRow(1);
		String[] str = {"编号","客户名称","概要","联系人","联系电话","创建人","创建时间","分配状态"};
		for (int i = 0; i < str.length; i++) {
			HSSFCell cell2 = rowHead.createCell(i);
			cell2.setCellStyle(style2);
			cell2.setCellValue(str[i]);
		}
		
		//4. 获得数据库数据将数据填入到表格
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(i + 2);
				HSSFCell cellId = row.createCell(0);
				cellId.setCellValue(list.get(i).getId());
				HSSFCell cellName = row.createCell(1);
				cellName.setCellValue(list.get(i).getCustomerName());
				HSSFCell cellView = row.createCell(2);
				cellView.setCellValue(list.get(i).getOverview());
				HSSFCell cellLinkMan = row.createCell(3);
				cellLinkMan.setCellValue(list.get(i).getLinkMan());
				HSSFCell cellLinkPhone = row.createCell(4);
				cellLinkPhone.setCellValue(list.get(i).getLinkPhone());
				HSSFCell cellCreatePerson = row.createCell(5);
				cellCreatePerson.setCellValue(list.get(i).getCreateMan());
				HSSFCell cellCreateTime = row.createCell(6);
				cellCreateTime.setCellValue("NULL");
				if (list.get(i).getCreateTime() != null) {
					cellCreateTime.setCellValue(list.get(i).getCreateTime());
				}
				HSSFCell cellStatus = row.createCell(7);
				if (list.get(i).getStatus() == null || list.get(i).getStatus() == 0) {
					cellStatus.setCellValue("未分配");
				} else {
					cellStatus.setCellValue("已分配");
				}
			}
		}
		//5.输出到文件
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}

	private HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
		font.setFontHeightInPoints(fontSize);
		//加载字体
		style.setFont(font);
		return style;
	}
	/*@Override
	public List<SaleChance> findSaleChanceName() {
		LinkedList<SaleChance> findSaleChanceName = saleChanceMapper.findSaleChanceName();
		SaleChance saleChance = new SaleChance();
		saleChance.setSaleChanceName(null);
		findSaleChanceName.addFirst(saleChance);
		return findSaleChanceName;
	}*/
	
	/*  完成导入Excel  */
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        String str = "";
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
        	SaleChance saleChance = new SaleChance();
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                // str += getStringCellValue(row.getCell((short) j)).trim() +
                // "-";
                switch (j) {
				case 1:
					saleChance.setCustomerName(getCellFormatValue(row.getCell((short) j)).trim());
					break;
				case 2:
					saleChance.setOverview(getCellFormatValue(row.getCell((short) j)).trim());
					break;
				case 3:
					saleChance.setLinkMan(getCellFormatValue(row.getCell((short) j)).trim());
					break;
				case 4:
					saleChance.setLinkPhone(getCellFormatValue(row.getCell((short) j)).trim());
					break;
				case 5:
					saleChance.setCreateMan(getCellFormatValue(row.getCell((short) j)).trim());
					break;
				case 6:
					String dateString = getDateCellValue(row.getCell((short) j)).trim();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = format.parse(dateString);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					saleChance.setCreateTime(date);
					break;
				case 7:
					saleChance.setStatus(Integer.parseInt(getCellFormatValue(row.getCell((short) j)).trim()));
					break;

				default:
					break;
				}
                j++;
            }
            content.put(i, str);
            str = "";
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    public  void readExcel() {
        try {

            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream("D:\\用户列表.xls");
            readExcelContent(is2);

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }


}
