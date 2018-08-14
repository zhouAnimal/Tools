package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;


import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

/**
 * 导出docx文件中的所有图片，目前不支持doc文件
 * 
 * @author BIGIOZ
 *
 */
public class Docx2Img {

	

	@SuppressWarnings("resource")
	public void readPicture(List<String> srcFiles, String imageFormat) throws Exception {
		for (String srcFile : srcFiles) {
			String[] split = srcFile.split("\\\\");//按照File.separator分割为数组
			String fileMessage = split[split.length - 1];//文件名+后缀
			String[] fileDetail = fileMessage.split("[.]");//当使用.分隔时，需要加上[]或者\\进行转义
			String imagePath = srcFile.substring(0,srcFile.lastIndexOf("\\")) +File.separator +fileDetail[0]+File.separator;
			if (fileMessage.endsWith(".doc")) {
				System.out.println("由于jar包冲突暂时注释");
//				HWPFDocument doc = new HWPFDocument(in);
//				int length = doc.characterLength();
//				PicturesTable pTable = doc.getPicturesTable();
//				int j = 0;
//				for (int i = 0; i < length; i++) {
//					Range range = new Range(i, i + 1, doc);
//					CharacterRun cr = range.getCharacterRun(0);
//					if (pTable.hasPicture(cr)) {
//						// 图片命名
//						j++;
//						String picfileName = fileDetail[0] + "_" + j + ".jpg";
//						Picture pic = pTable.extractPicture(cr, false);
//						OutputStream out = new FileOutputStream(new File(imagePath + picfileName));
//						pic.writeImageContent(out);
//
//					}
//				}
//				System.out.println(fileMessage + " --> 解析图片" + j + "张");
			} else if(fileMessage.endsWith(".docx")&& !fileMessage.startsWith("~$")){
				judeDirExists(new File(imagePath));
				FileInputStream in = new FileInputStream(new File(srcFile));
				try {
					// 用XWPFWordExtractor来获取文字
					XWPFDocument document = new XWPFDocument(in);
					XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(document);
					String text = xwpfWordExtractor.getText();
					String txtFileName = fileDetail[0] + ".txt";
					new PrintStream(txtFileName).println(text);
					// 用XWPFDocument的getAllPictures来获取所有的图片
					List<XWPFPictureData> picList = document.getAllPictures();
					int k = 0;
					for (XWPFPictureData pic : picList) {
						k++;
						byte[] bytev = pic.getData();
						// 大于1000bites的图片我们才弄下来，消除word中莫名的小图片的影响
//						if (bytev.length > 300) {
						FileOutputStream fos = new FileOutputStream(imagePath + setImageName(fileDetail[0],k,imageFormat));
						fos.write(bytev);
//						}
					}
					System.out.println("在路径："+imagePath+" 下保存了"+k+"张图片...");
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
			}

		}

	}
	
	/**
	 * 
	 * @param docxName
	 * @param count
	 * @return
	 */
	public String setImageName(String docxName,int i,String imageFormat){
		return docxName + "_" +i +"."+imageFormat;
	}
	// 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }

}