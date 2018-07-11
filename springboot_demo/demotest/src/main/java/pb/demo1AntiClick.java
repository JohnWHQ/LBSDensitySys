package pb;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class demo1AntiClick {
    public static void main(String[] args) throws Exception{
        System.out.println("Java DynamicMessage Parse Start...");

        Runtime run = Runtime.getRuntime();
        // 获取工程根目录
        String dir = System.getProperty("user.dir");
        String protocDir = dir + "/src/main/java/pb/";
        // 定义生成文件描述的存储路径
        String descSetOutDir = protocDir + "anti_click_uflow.desc";
        // 等待编译的.proto文件
        String protoFileDir = "anti_click_uflow.proto";
        // 生成bash命令
        String cmd = "protoc -I=" + protocDir + " --descriptor_set_out=" + descSetOutDir + " "
                + protocDir + protoFileDir;
        // 测试Log使用
        System.out.println("Protoc bash : " + cmd);
        // 执行bash命令
        Process pCompile = run.exec(cmd);

        // 检查desc编译执行状态,如果不正常终止, 则生成desc文件失败
        if (pCompile.waitFor() != 0) {
            if (pCompile.exitValue() == 1) {
                //p.exitValue()==0表示正常结束，1：非正常结束
                System.err.println("命令执行失败!");
                System.exit(1);
            }
        }

        // 读取生成的desc文件 不同c++直接用importer载入
        FileInputStream fileInputStream = new FileInputStream(descSetOutDir);

        PBUtils.compileDynamicMessage(fileInputStream);
        cmd = "rm -rf " + descSetOutDir;
        run.exec(cmd);

//        for (Map.Entry<String, Charset> entry :  Charset.availableCharsets().entrySet()) {
//            System.out.println(entry.getKey() + "     " + entry.getValue());
//        }
//        System.out.println(Charset.defaultCharset());

    }
}
