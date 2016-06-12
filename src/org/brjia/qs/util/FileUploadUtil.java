package org.brjia.qs.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator 2015年10月9日
 */
public class FileUploadUtil {
//	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	private static Pattern file_name_reg = Pattern.compile("\\S{1,}\\d{4}_\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2}[.]txt");

	public static String upload(String path, MultipartFile[] myfiles) {
		String validMsg = null;
		if ((validMsg = check(myfiles)) != null) {
			return validMsg;
		}
		/*for (MultipartFile file : myfiles) {
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
			} catch (IOException e) {
				e.printStackTrace();
				return "复制文件异常：" + e.getMessage();
			}
		}*/
		return null;
	}
//public static Date parseDate(String str) throws ParseException{
//		
//		return sdf.parse(str,new ParsePosition(str.length()-23));
//	}
	public static List<QnyhCollectArg> readString(MultipartFile[] myfiles){
		List<QnyhCollectArg> result = new ArrayList<QnyhCollectArg>();
		int actual=0;
		QnyhCollectArg  arg ;
		byte[] temp=null;
		for (MultipartFile mfile : myfiles) {
			try {
				temp =mfile.getBytes();
				ByteArrayInputStream bis = new ByteArrayInputStream(temp);
				InputStreamReader reader = new InputStreamReader(bis,"gbk");
				char[] chars = new char[bis.available()];
				actual = reader.read(chars);
				reader.close();
				
				arg = new QnyhCollectArg();
				arg.setMdStr(DigestUtils.md5Hex(temp));
				arg.setFilename(mfile.getOriginalFilename());
				arg.setContent(String.valueOf(chars,0,actual));
				result.add(arg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @param myfiles
	 */
	public static String check(MultipartFile[] myfiles) {
		if (myfiles == null || myfiles.length == 0) {
			return "没有选择任何文件";
		}
		for (MultipartFile myfile : myfiles) {
			if(myfile.isEmpty()){
				return "一个或多个无效文件";
			}
			if (!file_name_reg.matcher(myfile.getOriginalFilename()).matches()) {
				return "一个或多个无效文件名";
			}
			if (!"text/plain".equals(myfile.getContentType())) {
				return "不支持的文件类型";
			}
		}
		return null;
	}
}

