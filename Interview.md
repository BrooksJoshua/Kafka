# Resume Optimization for valuable vacancy 
<ol>
<li>Read massive JD and extract core skill set demanded by companies that offer considerable payment.</li>
<li>Forget yourself & your skillset level when decorate your Cv, and then fulfill yourself by self-improving.</li>
<li>Constant appending skillset. Constant improving.</li>
<li>An down-to-earth, bubbly and approachable pic. First impression(online or offline) COUNTS. Expression management, body shape, nails off etc. Confident eye contact.</li>
<li>8-10am is the best duration for CV dispatching.</li>
<li>dispatch CV by email? If yes, specify your theme concisely i.e. CV from Joshua for vacancy DataEngineer with 5yrs experience.</li>
<li>Blogging and post, then put the link on your cv</li>
</ol>

 

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * created by Joshua.H.Brooks on 2020.9月.25.13.46
 */
public class MyTest {

    @Test
    public void test() throws IOException {
        String path = "E:\\20200925\\WXA.sql";
        File file = new File(path);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] pieces = line.split(" ");
            for (String element : pieces) {
                if (element.toUpperCase().startsWith("IICC_WXA.")) {
                    stringBuilder.append(element).append("\n");
                }
            }
        }
        String filePath = "E:" + File.separator + "20200925";
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(filePath + File.separator + "exportedWXA.sql");
        if (!destFile.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(destFile, false);
        //true表示在文件末尾追加
        fos.write(stringBuilder.toString().getBytes());
        fos.write("\r\n".getBytes());
        fos.close();
    }

    @Test
    public void test2() throws IOException {
        //去重
        String path = "E:\\20200925\\exportedSDC.sql";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = br.readLine()) != null) {
            boolean contains = stringBuilder.toString().contains(line);
            if (!contains) {
                stringBuilder.append(line).append("\n");
            }

        }
        String filePath = "E:" + File.separator + "20200925";

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destFile = new File(filePath + File.separator + "DistinctTables_Final_SDC.sql");
        if (!destFile.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(destFile, false);
        //true表示在文件末尾追加
        fos.write(stringBuilder.toString().getBytes());
        fos.write("\r\n".getBytes());
        fos.close();
    }


    @Test
    public void test03() {
        String string = "111,222,333";
        string = "'" + string + "'";//字符串前后加'
        string = StringUtils.join(string.split(","), "','");//先按逗号分隔为数组，然后用','连接数组
        System.out.println(string);
        System.out.println(StringUtils.countMatches(string, ","));
        System.out.println(StringUtils.substringBetween(string, "/"));
    }

    @Test
    public void test04() throws IOException {
        //Read the last N lines of a specified file.

        // 1. 定义结果集,行数,已读行数,源文件路径
        List<String> result = new ArrayList<String>();
        long count = 0;
        long numRead = 0;
        String path = "D:\\MyData\\liwa2\\Downloads\\logs_archive_21\\0x257c3fd20ba4037e\\1\\catalina.out";
        File sourceFile = new File(path);
        // 2. 排除不可读状态
        if (!sourceFile.exists() || sourceFile.isDirectory() || !sourceFile.canRead()) {
            return;
        }

        FileReader in = new FileReader(sourceFile);
        LineNumberReader reader = new LineNumberReader(in);
        reader.skip(Long.MAX_VALUE);
        long lines = reader.getLineNumber();
        reader.close();
        System.out.println("总行数:=" + lines);

        RandomAccessFile r = new RandomAccessFile(sourceFile, "r");
        long length = r.length();
        r.seek(length - 1);
        boolean todayFlag = false;
        //3. 读取流
        BufferedReader br = new BufferedReader(new FileReader(sourceFile));
        String currentLine = null;
        while ((currentLine = br.readLine()) != null) {
            if (currentLine.contains("2020-09-27")) {
                todayFlag = true;
            }
            if (todayFlag) {
                result.add(currentLine);
                result.add("\n");
            }
        }
        String destFile = "D:\\MyData\\liwa2\\Downloads\\20200927CatalinaToday.log";
        FileOutputStream fos = new FileOutputStream(destFile, false);
        //true表示在文件末尾追加
        fos.write(result.toString().getBytes());
        fos.write("\r\n".getBytes());
        fos.close();

    }


}
生产: 3306-W-BI-PRD-TLY01-MYC1.service.dc_sd.consul iicc_inf_ss/A7lBlGDm
UAT:  3306-W-BD-UAT-TLY01-MYC5.service.dcnh.consul iicc_inf_ss/y004TOBK


  
-- 接口平台

4. 跳过测试类编译并启动springboot项目,在项目根路径依次执行
Step1 本地项目路径终端执行如下命令,发不到本地开发环境:
 mvn clean install -Pdev -Dmaven.test.skip=true
 mvn spring-boot:run
Step2: 在开发环境进行登录测试界面及功能是否生效:
  登录URL: 
   https://signinuat.midea.com/login?service=http://localhost:8080/iicc_inf2/shiro-cas
Step3: 执行如下命令部署到UAT:
 
mvn clean tomcat7:redeploy -Dmaven.test.skip=true -Ptest


mvn clean install -Ptest -Dmaven.test.skip=true


-- 关于version_num=17 导致trash_flag=0记录条数>=2的情况
select id, count(*) ct from iicc_inf_api_resource_info where trash_flag=0 GROUP BY id having ct>=2;

