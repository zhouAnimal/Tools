package tools;

import java.io.File;
/**
 * 读取一个文件夹下的所有文件
 * @author BIGIOZ
 *
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	public ReadFile() {
	}

	/**
	 * 读取某个文件夹下的所有文件
	 */
	public static List<String> readfile(String filepath) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
//				System.out.println("文件");
//				System.out.println("path=" + file.getPath());
//				System.out.println("absolutepath=" + file.getAbsolutePath());
//				System.out.println("name=" + file.getName());
				list.add(file.getPath());

			} else if (file.isDirectory()) {
//				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
//						System.out.println("path=" + readfile.getPath());
//						System.out.println("absolutepath=" + readfile.getAbsolutePath());
//						System.out.println("name=" + readfile.getName());
						list.add(readfile.getPath());
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}
				

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return list;
	}

	

//	public static void main(String[] args) {
//		try {
//			readfile("C:\\Users\\BIGIOZ\\Desktop\\work\\报价\\第二波");
//			// deletefile("D:/file");
//		} catch (FileNotFoundException ex) {
//		} catch (IOException ex) {
//		}
//		System.out.println("All files in the folder have been read complete...");
//	}

}