package pb;

import com.google.protobuf.*;
import com.google.protobuf.util.JsonFormat;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) throws Exception{

        System.out.println("PB java动态编译工具run...");

        Runtime run = Runtime.getRuntime();

        String dir = System.getProperty("user.dir");
        String protocDir = dir;
        String descSetOutDir = "test.desc";
        String protoFileDir = "pbtest.proto";

        // 生成bash命令
        String cmd = "protoc -I=" + protocDir + " --descriptor_set_out=" + descSetOutDir + " " + dir + "/"
                + protoFileDir;

        System.out.println(cmd);
        // 执行bash命令
        Process p = run.exec(cmd);

        // 如果不正常终止, 则生成desc文件失败
        if (p.waitFor() != 0) {
            if (p.exitValue() == 1) {
                //p.exitValue()==0表示正常结束，1：非正常结束
                System.err.println("命令执行失败!");
                System.exit(1);
            }
        }

        Map<String, String> dyMessageMap = new HashMap<String, String>();

        // 读取生成的desc文件 不同c++直接用importer载入
        FileInputStream fin = new FileInputStream(descSetOutDir);
        // 获取文件描述集
        DescriptorProtos.FileDescriptorSet descriptorSet = DescriptorProtos.FileDescriptorSet.parseFrom(fin);
        // 定义每个文件描述的临时变量
        Descriptors.FileDescriptor fd = null;

        if (descriptorSet.getFileCount() >= 1) {
            // 解析只处理单文件,获取index为0的FileDescriptorProto
            DescriptorProtos.FileDescriptorProto fdp = descriptorSet.getFileList().get(0);
            // 测试Log使用 List<FileDescriptorProto>大小
            System.out.println("FileList size : " + descriptorSet.getFileList().size());
            // 根据FileDescriptorProto 获取FileDescriptor
            fd = Descriptors.FileDescriptor.buildFrom(fdp, new Descriptors.FileDescriptor[]{});
            // 解析只处理单Message,获取index为0的MessageType
            if (fd.getMessageTypes().size() >= 1){
                // 测试Log使用 List<Descriptor>大小
                System.out.println("message size : " + fd.getMessageTypes().size());
                Descriptors.Descriptor descriptor = fd.getMessageTypes().get(0);
                // 构建动态编译Message的Builder
                DynamicMessage.Builder mbuilder = DynamicMessage.newBuilder(descriptor);

                // 测试Log使用 生成类名
                String className = fdp.getOptions().getJavaPackage() + "."
                        + fdp.getOptions().getJavaOuterClassname() + "$"
                        + descriptor.getName();

                // 终于拿到原始消息里所有字段
                List<Descriptors.FieldDescriptor> fileds = descriptor.getFields();

                // 测试Log使用 Descriptors.Descriptor可以获取 Fields, EnumTypes, NestedTypes
                // System.out.println(descriptor.getEnumTypes().get(0).getFullName());
                // System.out.println(descriptor.getNestedTypes().get(0).getName());

                // 测试Log使用 List<FiledDescriptor>大小
                System.out.println("filed size : " + fileds.size() );

                DynamicMessage addm = test_bm(descriptor.getNestedTypes().get(0));

                for(Descriptors.FieldDescriptor filed : fileds) {
                    // 拿到字段,根据字段开始插值
                    if (filed.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE){
                        mbuilder.setField(filed, addm);
                    }else {
                        mbuilder.setField(filed, getObject4Message("123",filed.getJavaType()));
                    }
                    // 测试Log使用 测试字段名称属性
                    // Filed_test(filed);
                    // 测试Log使用 测试字段类型
                    // System.out.println(filed.getJavaType());
                }
                // build出PB消息
                DynamicMessage message = (DynamicMessage) mbuilder.build();
                // 测试Log使用 序列化反序列化测试
                PB_test(message);

            }else {
                System.err.println("Message数量为0");
                System.exit(1);
            }
        }else {
            System.err.println("File数量为0");
            System.exit(1);
        }


    }

    public static DynamicMessage test_bm(Descriptors.Descriptor descriptor){

        DynamicMessage.Builder mbuilder = DynamicMessage.newBuilder(descriptor);
        List<Descriptors.FieldDescriptor> fileds = descriptor.getFields();
        for(Descriptors.FieldDescriptor filed : fileds) {
            // 拿到字段,根据字段开始插值
            mbuilder.setField(filed, getObject4Message("123",filed.getJavaType()));
        }
        // build出PB消息
        DynamicMessage message = (DynamicMessage) mbuilder.build();
        PB_test(message);
        return  message;
    }

    // 测试PB工具方法
    public static void PB_test(DynamicMessage msg){

        System.out.println("===== 构建msg模型结束 =====");

        System.out.println("===== msg Byte 开始=====");
        for(byte b : msg.toByteArray()){
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + msg.toByteString().size());
        System.out.println("===== msg Byte 结束 =====");

        System.out.println("===== 使用msg  反序列化生成对象开始 =====");
        DynamicMessage gd = null;
        try {
            gd = DynamicMessage.parseFrom(msg.getDescriptorForType(), msg.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(gd.toString());
        System.out.println("===== 使用msg 反序列化生成对象结束 =====");



        System.out.println("===== 使用msg 转成json对象开始 =====");

        String jsonFormatM = "";
        try {
            jsonFormatM = JsonFormat.printer().print(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonFormatM.toString());
        System.out.println("json数据大小：" + jsonFormatM.getBytes().length);
        System.out.println("===== 使用msg 转成json对象结束 =====");
    }

    // 测试Filed工具方法
    public static void Filed_test(Descriptors.FieldDescriptor filed){
        System.out.println("=========Field Test=============");
        System.out.println(filed.getFullName());
        System.out.println(filed.getJavaType());
        System.out.println(filed.getName());
        System.out.println("=========Field Test============");
    }

    /**
     * multiMessageParse() 多文件多Message解析
     * @param descriptorSet
     * @throws Exception
     */
    private static void multiMessageParse(DescriptorProtos.FileDescriptorSet descriptorSet)throws Exception{
        Descriptors.FileDescriptor fd = null;
        // 多文件解析
        for (DescriptorProtos.FileDescriptorProto fdp: descriptorSet.getFileList()) {
            fd = Descriptors.FileDescriptor.buildFrom(fdp, new Descriptors.FileDescriptor[]{});
            // 多message解析
            for (Descriptors.Descriptor descriptor : fd.getMessageTypes()) {
                // 根据descriptor构建动态编译的PB消息builder
                DynamicMessage.Builder mbuilder = DynamicMessage.newBuilder(descriptor);

                // 生成类名
                String className = fdp.getOptions().getJavaPackage() + "."
                        + fdp.getOptions().getJavaOuterClassname() + "$"
                        + descriptor.getName();

                // 终于拿到原始消息里所有字段
                List<Descriptors.FieldDescriptor> fileds = descriptor.getFields();
                // 字段解析
                for(Descriptors.FieldDescriptor filed : fileds) {
                    // 拿到字段,根据字段开始插值
                    mbuilder.setField(filed, getObject4Message("123",filed.getJavaType()));
                }
                // build出PB消息
                DynamicMessage message = (DynamicMessage) mbuilder.build();
                // 序列化反序列化测试
                PB_test(message);
            }
        }
    }

    /**
     * getObject() 动态构建PB Message填入MessageBuilder
     * @param rawString
     * @param type
     * @return
     */
    private static Object getObject4Message(String rawString, Descriptors.FieldDescriptor.JavaType type) {
        try {
            switch (type) {
                case INT:
                    return Integer.valueOf(rawString);
                case LONG:
                    return Long.valueOf(rawString);
                case FLOAT:
                    return Float.valueOf(rawString);
                case DOUBLE:
                    return Double.valueOf(rawString);
                case BOOLEAN:
                    return Boolean.valueOf(rawString);
                case STRING:
                    return rawString;
                default:
                    // BYTE_STRING, ENUM, MESSAGE后续做支持
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
