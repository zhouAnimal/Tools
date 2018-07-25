package tools;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
 
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
 
import com.lowagie.text.pdf.PdfReader;
 
public class Pdf2Img {
//	public static void main(String[] args) {
//		pdf2Image("C:\\Users\\BIGIOZ\\Desktop\\work\\报价\\第二波\\天润新禾 中科院报价单.pdf", "C:\\Users\\BIGIOZ\\Desktop\\work\\报价\\第二波\\测试", 300);
//	}
 
	/***
	 * PDF文件转PNG图片，全部页数
	 * 
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static void pdf2Image(List<String> PdfFilePaths,String picFormat ,int dpi) {
		for (String PdfFilePath : PdfFilePaths) {
			if(!PdfFilePath.endsWith(".pdf")) {
				System.out.println(PdfFilePath+"不是pdf文件，跳过");
				break;
			}
			File file = new File(PdfFilePath);
			PDDocument pdDocument;
			try {
				String imgPDFPath = file.getParent();
				int dot = file.getName().lastIndexOf('.');
				String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
				String imgFolderPath = null;
				imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 图片存放的文件夹路径
	 
				if (createDirectory(imgFolderPath)) {
					pdDocument = PDDocument.load(file);
					PDFRenderer renderer = new PDFRenderer(pdDocument);
					/* dpi越大转换后越清晰，相对转换速度越慢 */
					PdfReader reader = new PdfReader(PdfFilePath);
					int pages = reader.getNumberOfPages();
					StringBuffer imgFilePath = null;
					for (int i = 0; i < pages; i++) {
						String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
						imgFilePath = new StringBuffer();
						imgFilePath.append(imgFilePathPrefix);
						imgFilePath.append("_");
						imgFilePath.append(String.valueOf(i + 1));
						imgFilePath.append("."+picFormat);
						File dstFile = new File(imgFilePath.toString());
						BufferedImage image = renderer.renderImageWithDPI(i, dpi);
						ImageIO.write(image, picFormat, dstFile);
						System.out.println("导出第"+i + 1+"张图片...");
					}
					System.out.println("PDF文档转"+picFormat+"图片成功！");
	 
				} else {
					System.out.println("PDF文档转"+picFormat+"图片失败：" + "创建" + imgFolderPath + "失败");
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}
 
}