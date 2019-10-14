package org.BossCrawler;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.ToolBox.net.HttpUtils;
import com.ToolBox.util.HtmlTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2019年10月13日 上午10:49:20
 * <p>
 * 项目名称：JavaTestProject
 * 
 * <p>
 * 类说明： boss直聘爬虫
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：TestTest.java
 */

class bossInfo {
	// 招聘链接、招聘岗位名称、工资、工作地点、需要工作经验、企业名称、企业类型/行业，是否需要融资，企业规模，数据来源
	public String href, title, money, addr, year, name, pc, top, num, source;

	public String getHref() {
		return href;
	}

	public String getTitle() {
		return title;
	}

	public String getMoney() {
		return money;
	}

	public String getAddr() {
		return addr;
	}

	public String getYear() {
		return year;
	}

	public String getName() {
		return name;
	}

	public String getPc() {
		return pc;
	}

	public String getTop() {
		return top;
	}

	public String getNum() {
		return num;
	}

	public String getSource() {
		return source;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setSource(String source) {
		this.source = source;
	}
}

public class Main {

	// 写入Excel表格
	public static void write(String save, String... data) throws Exception {
		String filePath = save;
		File file = new File(filePath);
		if (file.exists()) {
			HSSFWorkbook excel1 = new HSSFWorkbook();
			FileInputStream myxls = new FileInputStream(filePath);
			excel1 = new HSSFWorkbook(myxls);
			HSSFSheet worksheet = excel1.getSheetAt(0);
			int lastRow = worksheet.getLastRowNum();
			Row row = worksheet.createRow(++lastRow);
			for (int i = 0; i < data.length; i++) {
				row.createCell(i).setCellValue(data[i]);
			}
			for (int j = 0; j < data.length; j++) {
				row.getCell(j).getCellStyle().setAlignment(HorizontalAlignment.CENTER);
			}
			myxls.close();
			excel1.write(new File(filePath));
		} else {
			HSSFWorkbook excel1 = new HSSFWorkbook();
			Sheet sheet = excel1.createSheet("Java开发工程师企业名单");
			Row row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("企业名称");
			row1.createCell(1).setCellValue("城市");
			row1.createCell(2).setCellValue("招聘岗位");
			row1.createCell(3).setCellValue("薪资");
			row1.createCell(4).setCellValue("经验要求");
			row1.createCell(5).setCellValue("招聘链接");
			row1.createCell(6).setCellValue("所属行业");
			row1.createCell(7).setCellValue("融资情况");
			row1.createCell(8).setCellValue("企业规模");
			row1.createCell(9).setCellValue("岗位来源");
			for (int i = 0; i < data.length; i++) {
				row1.getCell(i).getCellStyle().setAlignment(HorizontalAlignment.CENTER); // 设置字体居中
			}
			excel1.write(new File(filePath));
			write(save, data);
		}
	}

	// 获取工资
	public static String getName(String data, String re) {
		// System.out.println(" data : " +data);
		for (String d : data.split("\n")) {
			if (d.indexOf(re) != -1) {
				return new HtmlTool(d).getByElement("a").getText().toString();
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		if (args.length > 4) {
			String hs[] = {
					"Accept: text/html,application/xhtml,xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3",
					"Accept-Language: zh-CN,zh;q=0.9", "Connection: keep-alive",

					args[0], "Host: www.zhipin.com", "Sec-Fetch-Mode: navigate", "Sec-Fetch-Site: none", // args[0]这个参数为网页的临时cookie
					"Sec-Fetch-User: ?1", "Upgrade-Insecure-Requests: 1",
					"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36" };

			HttpUtils hu = new HttpUtils();
			StringTool st = new StringTool();
			hu.setHeaders(hs);
			// https://www.zhipin.com/c101010100-p100101/?query=Java开发工程师&page=1
			for (int i = Integer.parseInt(args[1].replaceAll("\\s+", "")); i < 9999; i++) { // args[1]这个参数为起始的页面位置
				String arr[] = hu.toHtml(args[2].replaceAll("\\s+", "") + i).getByElement("ul").toString().split("\n"); // args[2]这个参数为搜索的链接，但不需要page后面的参数，可以参照上面那条链接
				if (arr.length == 1) {// 如果没有获取到数据就终止程序并输出当前获取到哪个位置
					System.err.println("page in : " + i); //这条数据是用来提醒用户cookie已经过期
					break;
				} else {
					for (int j = 0; j < arr.length; j++) {
						if (arr[j].indexOf("job-primary") != -1) {
							String data1 = new HtmlTool(arr[j]).getByElement("li").toString();
							for (String hh : data1.split("\n")) {
								bossInfo bi = new bossInfo();
								HtmlTool ht = new HtmlTool(hh);
								bi.setHref("https://www.zhipin.com"
										+ ht.getByElementValue("href").toString().replaceAll("\\s+", ""));
								bi.setTitle(st.getByString(hh, "job-title\"(.+?<)", "job-title|\"|(<|>)")
										.replaceAll("\\s+", ""));
								bi.setMoney(st.getByString(hh, "red\"(.+?<)", "red|\"|(<|>)").replaceAll("\\s+", ""));
								bi.setAddr(st.getByString(hh, "<p>(.+?<em)", "p|em|(<|>)").replaceAll("\\s+", ""));
								bi.setYear(st.getByString(hh, "em>(.+?<em)", "em|(<|>)").replaceAll("\\s+", ""));
								bi.setName(getName(new HtmlTool(hh).getByElement("a").toString(), "gongsi")
										.replaceAll("\\s+", ""));
								String arra[] = st
										.getByAllString(new HtmlTool(st.getByString(hh, "gongsi(.+?info-publis)", ""))
												.getByElement("p").toString(), ">(.+?<)", ">|<")
										.split("\n");
								bi.setPc(arra[0].replaceAll("\\s+|/em", "").replaceAll("\\s+", ""));
								bi.setTop(arra[1].replaceAll("\\s+|/em", "").replaceAll("\\s+", ""));
								bi.setNum(arra[2].replaceAll("\\s+|/em", "").replaceAll("\\s+", ""));
								bi.setSource("BOSS");
								// args[3]这个参数表示文件保存的位置
								write(args[3], bi.getName(), bi.getAddr(), bi.getTitle(), bi.getMoney(), bi.getYear(),
										bi.getHref(), bi.getPc(), bi.getTop(), bi.getNum(), bi.getSource());
							}

						}
					}
				}
			}
		} else {
			System.err.println("\n0 : cookie \n1: start page\n2 : link\n3: save name\n");
		}
	}

}
