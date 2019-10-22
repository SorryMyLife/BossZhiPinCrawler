package org.BossCrawler.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.ToolBox.util.HtmlTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2019年10月16日 下午2:48:13
 * <p>
 * 项目名称：BossCrawler
 * 
 * <p>
 * 类说明：
 * boss直聘爬虫修改版
 * 基于selenium
 * @version 1.0
 * @since JDK 1.8 文件名称：test.java
 */

class boss {
	/**
	 * </p>
	 * 职位链接、职位名称、职位月薪、职位工龄、文凭要求、工作地址、招聘公司名称、招聘公司链接、所属行业、公司规模、公司融资情况
	 */
	public String jobLink, jobName, money, year, diploma, addr, companyName, companyLink, business, FinancingScale,
			FinancingSituation;

	public String getJobLink() {
		return jobLink;
	}

	public String getJobName() {
		return jobName;
	}

	public String getMoney() {
		return money;
	}

	public String getYear() {
		return year;
	}

	public String getDiploma() {
		return diploma;
	}

	public String getAddr() {
		return addr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getCompanyLink() {
		return companyLink;
	}

	public String getBusiness() {
		return business;
	}

	public String getFinancingScale() {
		return FinancingScale;
	}

	public String getFinancingSituation() {
		return FinancingSituation;
	}

	public void setJobLink(String jobLink) {
		this.jobLink = jobLink;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCompanyLink(String companyLink) {
		this.companyLink = companyLink;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public void setFinancingScale(String financingScale) {
		FinancingScale = financingScale;
	}

	public void setFinancingSituation(String financingSituation) {
		FinancingSituation = financingSituation;
	}
}

public class test {

	private static final StringTool st = new StringTool();

	private static int count = 1;

	// 获取职位链接
	public static String getHref(String data) {
		String d = new HtmlTool(data).getByElement("a").getHref().toString();
		return d != null ? "https://www.zhipin.com" + d : null;
	}

	// 获取职位名称
	public static String getJobName(String data) {
		String d = st.getByString(data, "job-title\"(.+?</div)", "job-title\">|</div");
		return d != null ? d : null;
	}

	// 获取职位工资
	public static String getJobMoney(String data) {
		String d = st.getByString(data, "red\">(.+?</spa)", "red\">|</spa");
		return d != null ? d : null;
	}

	// 获取公司地址
	public static String getJobAddr(String data) {
		String d = st.getByString(data, "info-detail\"></div></a></h3><p>(.+?<emclass)",
				"info-detail\"></div></a></h3><p>|<emclass");
		return d != null ? d : null;
	}

	// 获取职位要求的经验时间
	public static String getJobAge(String data) {
		String d = st.getByString(data, "em>(.+?<em)", "em|>|<");
		return d != null ? d : null;
	}

	// 获取职位所需要的文凭
	public static String getJobMax(String data) {
		String d = st.getByString(st.getByString(data, "</em>(.+?</p></div><divclass=\"info)", ""), "line\">(.+?</p)",
				"line\"></em>|</p");
		return d != null ? d : null;
	}

	// 获取招聘公司的链接
	public static String getJobGongsiHref(String data) {
		String d = st.getByString(data, "href=\"/gongsi(.+?\")", "href=|\"");
		return d != null ? "https://www.zhipin.com" + d : null;
	}

	// 获取招聘公司的名字
	public static String getJobGongsiName(String data) {
		String d = st.getByString(data, "ustompage\"target=\"_blank\">(.+?</a></h3>)",
				"ustompage\"target=\"_blank\">|</a></h3>");
		return d != null ? d : null;
	}

	// 获取招聘公司的所属行业、融资情况、规模大小
	public static String[] getJobHangye(String data) {
		String d = st.getByString(data, "custompage\"target=\"_blank\">(.+?</p></div></div><divclass=\"info-publis)",
				"");
		String tmp[] = new String[3];
		tmp[0] = st.getByString(d, "<p>(.+?<em)", "<p>|<em");
		tmp[1] = st.getByString(d, "/em(.+?<emclass)", "/em>|<emclass");
		tmp[2] = st.getByString(st.getByString(d, "vline\">(.+?</p)", ""), "emclass(.+?</p)",
				"emclass=\"vline\"></em>|</p");
		return tmp != null ? tmp : null;
	}

	/**
	 * 这里需要传入四个固定参数，一个动态参数 savepath是用来存放xls文件的路径 savename是xls文件名称
	 * sheetname是Excel里的sheet名字 sheetAt是读取sheet表格的位置，默认是0
	 * 
	 * data则是一个动态字符串数组
	 * 
	 */

	public static void write(String savePath, String saveName, String sheetName, int sheetAt, String... data)
			throws Exception {
		File dirFile = new File(savePath);
		if (dirFile.exists()) {
			File file = new File(savePath + "/" + saveName);
			HSSFWorkbook excel = new HSSFWorkbook();
			if (file.exists()) {
				FileInputStream myxls = new FileInputStream(file);
				excel = new HSSFWorkbook(myxls);
				HSSFSheet worksheet = excel.getSheetAt(0);
				int lastRow = worksheet.getLastRowNum();
				Row row = worksheet.createRow(++lastRow);
				for (int i = 0; i < data.length; i++) {
					row.createCell(i).setCellValue(data[i]);
				}
				for (int j = 0; j < data.length; j++) {
					row.getCell(j).getCellStyle().setAlignment(HorizontalAlignment.CENTER);
				}
				myxls.close();
				excel.write(file);

			} else {
				// 职位链接、职位名称、职位月薪、职位工龄、文凭要求、工作地址、招聘公司名称、招聘公司链接、所属行业、公司规模、公司融资情况
				Sheet sheet = excel.createSheet(sheetName);
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("职位名称");
				row.createCell(1).setCellValue("职位月薪");
				row.createCell(2).setCellValue("职位工龄");
				row.createCell(3).setCellValue("文化程度");
				row.createCell(4).setCellValue("公司地址");
				row.createCell(5).setCellValue("招聘公司名称");
				row.createCell(6).setCellValue("招聘公司链接");
				row.createCell(7).setCellValue("所属行业");
				row.createCell(8).setCellValue("公司规模");
				row.createCell(9).setCellValue("融资情况");
				row.createCell(10).setCellValue("职位链接");
				row.createCell(11).setCellValue("数据来源");
				for (int i = 0; i < 12; i++) {
					row.getCell(i).getCellStyle().setAlignment(HorizontalAlignment.CENTER);
				}
				excel.write(file);
				write(savePath, saveName, sheetName, sheetAt, data);
			}
		} else {
			dirFile.mkdirs();
			write(savePath, saveName, sheetName, sheetAt, data);
		}
	}

	/**
	 * 存储并写入到本地Excel表的函数 需要提供两个参数 data为传进来的网页源码，sheetname就是搜索的名字
	 */
	public static void info(String data, String sheetName) {
		// new FileTool().writeFile(data, "e:\\test\\files\\jjj.html");
		String savePath = "e:\\test\\files\\Boss"; // 文件保存路径
		String fileName = "Java开发.xls"; // 保存的文件名称
		int sheetAt = 0;

		HtmlTool ht = new HtmlTool(data);
		for (String sdata : ht.getByElement("li").toString().split("\n")) {
			if (sdata.indexOf("job-pr") != -1) {
				// System.out.println(sdata);
				boss b = new boss();
				b.setJobLink(getHref(sdata).replaceAll("\\s+", ""));
				b.setJobName(getJobName(sdata).replaceAll("\\s+", ""));
				b.setAddr(getJobAddr(sdata).replaceAll("\\s+", ""));
				b.setMoney(getJobMoney(sdata).replaceAll("\\s+", ""));
				b.setYear(getJobAge(sdata).replaceAll("\\s+", ""));
				b.setDiploma(getJobMax(sdata).replaceAll("\\s+", ""));
				b.setCompanyLink(getJobGongsiHref(sdata).replaceAll("\\s+", ""));
				b.setCompanyName(getJobGongsiName(sdata).replaceAll("\\s+", ""));
				String arr[] = getJobHangye(sdata);
				b.setBusiness(arr[0].replaceAll("\\s+", ""));
				b.setFinancingSituation(arr[1].replaceAll("\\s+", ""));
				b.setFinancingScale(arr[2].replaceAll("\\s+", ""));
				try {
					write(savePath, fileName, sheetName, sheetAt, b.getJobName(), b.getMoney(), b.getYear(),
							b.getDiploma(), b.getAddr(), b.getCompanyName(), b.getCompanyLink(), b.getBusiness(),
							b.getFinancingScale(), b.getFinancingSituation(), b.getJobLink(), "BOSS直聘");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.err.println("第 [" + count + "] 次数据爬取已经完成!");
		count++;
	}

	/**
	 * 用于线程休眠
	 */
	public static void sleep(int time) {
		try {
			new Thread().sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 从这里开始运行程序
	 */
	public static void start() {
		System.setProperty("webdriver.chrome.driver", "F:\\新建文件夹\\chromedriver_win32\\chromedriver.exe"); // 设置webdrive驱动位置

		int pageNum = 1024; // 最大页面数量
		String sheetName = "java开发"; // Excel里的sheet名字
		ChromeOptions options = new ChromeOptions();
		ArrayList<String> excludeSwitches = new ArrayList<String>();
		excludeSwitches.add("enable-automation");

		options.setExperimentalOption("excludeSwitches", excludeSwitches); // 设置为开发者模式
		/**
		 * Python里则是用add_experimental_option('excludeSwitches', ['enable-automation'])取代
		 */

		ChromeDriver driver = new ChromeDriver(options);

		String tmpPage = null;
		// driver.get("https://www.zhipin.com/job_detail/?query=" + sheetName +
		// "&city=101010100");//这是其中一个搜索方式
		driver.get("https://www.zhipin.com/c101010100-p100101/?query=" + sheetName); // 另一个搜索链接
		int num = 0;
		while (num < pageNum) {
			sleep(2000);
			String page = driver.getPageSource().replaceAll("\\s+", ""); //获取网页源码并移除其他特殊符号
			if (page.equals(tmpPage)) {
				break;
			} else {
				info(page, sheetName);
				WebElement we = driver.findElement(By.className("next")); //找到下一页的位置
				we.click();
				num++;
			}
			tmpPage = page;
		}
		System.err.println("数据已经爬取完成!");
		driver.quit();
	}

	public static void main(String[] args) {
		// 开始运行程序
		start();
	}

}
