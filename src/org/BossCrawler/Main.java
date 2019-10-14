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
 * ����ʱ�䣺2019��10��13�� ����10:49:20
 * <p>
 * ��Ŀ���ƣ�JavaTestProject
 * 
 * <p>
 * ��˵���� bossֱƸ����
 * 
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�TestTest.java
 */

class bossInfo {
	// ��Ƹ���ӡ���Ƹ��λ���ơ����ʡ������ص㡢��Ҫ�������顢��ҵ���ơ���ҵ����/��ҵ���Ƿ���Ҫ���ʣ���ҵ��ģ��������Դ
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

	// д��Excel���
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
			Sheet sheet = excel1.createSheet("Java��������ʦ��ҵ����");
			Row row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("��ҵ����");
			row1.createCell(1).setCellValue("����");
			row1.createCell(2).setCellValue("��Ƹ��λ");
			row1.createCell(3).setCellValue("н��");
			row1.createCell(4).setCellValue("����Ҫ��");
			row1.createCell(5).setCellValue("��Ƹ����");
			row1.createCell(6).setCellValue("������ҵ");
			row1.createCell(7).setCellValue("�������");
			row1.createCell(8).setCellValue("��ҵ��ģ");
			row1.createCell(9).setCellValue("��λ��Դ");
			for (int i = 0; i < data.length; i++) {
				row1.getCell(i).getCellStyle().setAlignment(HorizontalAlignment.CENTER); // �����������
			}
			excel1.write(new File(filePath));
			write(save, data);
		}
	}

	// ��ȡ����
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

					args[0], "Host: www.zhipin.com", "Sec-Fetch-Mode: navigate", "Sec-Fetch-Site: none", // args[0]�������Ϊ��ҳ����ʱcookie
					"Sec-Fetch-User: ?1", "Upgrade-Insecure-Requests: 1",
					"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36" };

			HttpUtils hu = new HttpUtils();
			StringTool st = new StringTool();
			hu.setHeaders(hs);
			// https://www.zhipin.com/c101010100-p100101/?query=Java��������ʦ&page=1
			for (int i = Integer.parseInt(args[1].replaceAll("\\s+", "")); i < 9999; i++) { // args[1]�������Ϊ��ʼ��ҳ��λ��
				String arr[] = hu.toHtml(args[2].replaceAll("\\s+", "") + i).getByElement("ul").toString().split("\n"); // args[2]�������Ϊ���������ӣ�������Ҫpage����Ĳ��������Բ���������������
				if (arr.length == 1) {// ���û�л�ȡ�����ݾ���ֹ���������ǰ��ȡ���ĸ�λ��
					System.err.println("page in : " + i); //�������������������û�cookie�Ѿ�����
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
								// args[3]���������ʾ�ļ������λ��
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
