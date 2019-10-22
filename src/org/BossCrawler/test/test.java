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
 * ����ʱ�䣺2019��10��16�� ����2:48:13
 * <p>
 * ��Ŀ���ƣ�BossCrawler
 * 
 * <p>
 * ��˵����
 * bossֱƸ�����޸İ�
 * ����selenium
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�test.java
 */

class boss {
	/**
	 * </p>
	 * ְλ���ӡ�ְλ���ơ�ְλ��н��ְλ���䡢��ƾҪ�󡢹�����ַ����Ƹ��˾���ơ���Ƹ��˾���ӡ�������ҵ����˾��ģ����˾�������
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

	// ��ȡְλ����
	public static String getHref(String data) {
		String d = new HtmlTool(data).getByElement("a").getHref().toString();
		return d != null ? "https://www.zhipin.com" + d : null;
	}

	// ��ȡְλ����
	public static String getJobName(String data) {
		String d = st.getByString(data, "job-title\"(.+?</div)", "job-title\">|</div");
		return d != null ? d : null;
	}

	// ��ȡְλ����
	public static String getJobMoney(String data) {
		String d = st.getByString(data, "red\">(.+?</spa)", "red\">|</spa");
		return d != null ? d : null;
	}

	// ��ȡ��˾��ַ
	public static String getJobAddr(String data) {
		String d = st.getByString(data, "info-detail\"></div></a></h3><p>(.+?<emclass)",
				"info-detail\"></div></a></h3><p>|<emclass");
		return d != null ? d : null;
	}

	// ��ȡְλҪ��ľ���ʱ��
	public static String getJobAge(String data) {
		String d = st.getByString(data, "em>(.+?<em)", "em|>|<");
		return d != null ? d : null;
	}

	// ��ȡְλ����Ҫ����ƾ
	public static String getJobMax(String data) {
		String d = st.getByString(st.getByString(data, "</em>(.+?</p></div><divclass=\"info)", ""), "line\">(.+?</p)",
				"line\"></em>|</p");
		return d != null ? d : null;
	}

	// ��ȡ��Ƹ��˾������
	public static String getJobGongsiHref(String data) {
		String d = st.getByString(data, "href=\"/gongsi(.+?\")", "href=|\"");
		return d != null ? "https://www.zhipin.com" + d : null;
	}

	// ��ȡ��Ƹ��˾������
	public static String getJobGongsiName(String data) {
		String d = st.getByString(data, "ustompage\"target=\"_blank\">(.+?</a></h3>)",
				"ustompage\"target=\"_blank\">|</a></h3>");
		return d != null ? d : null;
	}

	// ��ȡ��Ƹ��˾��������ҵ�������������ģ��С
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
	 * ������Ҫ�����ĸ��̶�������һ����̬���� savepath���������xls�ļ���·�� savename��xls�ļ�����
	 * sheetname��Excel���sheet���� sheetAt�Ƕ�ȡsheet����λ�ã�Ĭ����0
	 * 
	 * data����һ����̬�ַ�������
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
				// ְλ���ӡ�ְλ���ơ�ְλ��н��ְλ���䡢��ƾҪ�󡢹�����ַ����Ƹ��˾���ơ���Ƹ��˾���ӡ�������ҵ����˾��ģ����˾�������
				Sheet sheet = excel.createSheet(sheetName);
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("ְλ����");
				row.createCell(1).setCellValue("ְλ��н");
				row.createCell(2).setCellValue("ְλ����");
				row.createCell(3).setCellValue("�Ļ��̶�");
				row.createCell(4).setCellValue("��˾��ַ");
				row.createCell(5).setCellValue("��Ƹ��˾����");
				row.createCell(6).setCellValue("��Ƹ��˾����");
				row.createCell(7).setCellValue("������ҵ");
				row.createCell(8).setCellValue("��˾��ģ");
				row.createCell(9).setCellValue("�������");
				row.createCell(10).setCellValue("ְλ����");
				row.createCell(11).setCellValue("������Դ");
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
	 * �洢��д�뵽����Excel��ĺ��� ��Ҫ�ṩ�������� dataΪ����������ҳԴ�룬sheetname��������������
	 */
	public static void info(String data, String sheetName) {
		// new FileTool().writeFile(data, "e:\\test\\files\\jjj.html");
		String savePath = "e:\\test\\files\\Boss"; // �ļ�����·��
		String fileName = "Java����.xls"; // ������ļ�����
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
							b.getFinancingScale(), b.getFinancingSituation(), b.getJobLink(), "BOSSֱƸ");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.err.println("�� [" + count + "] ��������ȡ�Ѿ����!");
		count++;
	}

	/**
	 * �����߳�����
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
	 * �����￪ʼ���г���
	 */
	public static void start() {
		System.setProperty("webdriver.chrome.driver", "F:\\�½��ļ���\\chromedriver_win32\\chromedriver.exe"); // ����webdrive����λ��

		int pageNum = 1024; // ���ҳ������
		String sheetName = "java����"; // Excel���sheet����
		ChromeOptions options = new ChromeOptions();
		ArrayList<String> excludeSwitches = new ArrayList<String>();
		excludeSwitches.add("enable-automation");

		options.setExperimentalOption("excludeSwitches", excludeSwitches); // ����Ϊ������ģʽ
		/**
		 * Python��������add_experimental_option('excludeSwitches', ['enable-automation'])ȡ��
		 */

		ChromeDriver driver = new ChromeDriver(options);

		String tmpPage = null;
		// driver.get("https://www.zhipin.com/job_detail/?query=" + sheetName +
		// "&city=101010100");//��������һ��������ʽ
		driver.get("https://www.zhipin.com/c101010100-p100101/?query=" + sheetName); // ��һ����������
		int num = 0;
		while (num < pageNum) {
			sleep(2000);
			String page = driver.getPageSource().replaceAll("\\s+", ""); //��ȡ��ҳԴ�벢�Ƴ������������
			if (page.equals(tmpPage)) {
				break;
			} else {
				info(page, sheetName);
				WebElement we = driver.findElement(By.className("next")); //�ҵ���һҳ��λ��
				we.click();
				num++;
			}
			tmpPage = page;
		}
		System.err.println("�����Ѿ���ȡ���!");
		driver.quit();
	}

	public static void main(String[] args) {
		// ��ʼ���г���
		start();
	}

}
