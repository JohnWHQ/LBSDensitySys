package pb;

import com.google.protobuf.*;
import com.google.protobuf.util.JsonFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PBUtils {

    public static void main(String[] argus) throws Exception {
         testMain();
    }

    private static void testMain() throws Exception{
        System.out.println("Java DynamicMessage Parse Start...");

        Runtime run = Runtime.getRuntime();

        // 获取工程根目录
        String dir = System.getProperty("user.dir") + "/sources";
        String protocDir = dir;

        // 定义生成文件描述的存储路径
        String descSetOutDir = dir + "/test.desc";
        // 待编译的.proto文件
        String protoFileDir = "pbtest.proto";

        // 生成bash命令
        String cmd = "protoc -I=" + protocDir + " --descriptor_set_out=" + descSetOutDir + " " + dir + "/"
                + protoFileDir;

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
        // 根据desc的inputStream开始干活
        DynamicMessage msg = compileDynamicMessage(fileInputStream);
        cmd = "rm -rf " + descSetOutDir;
        run.exec(cmd);

        // String descFile = "test.desc";
         String binaryFile = "binaryOutput";
         write2Binary(msg,dir, binaryFile);
        // DynamicMessage tmsg = readFromBinary(dir, binaryFile, descFile);
        // testPBMsg(tmsg);

    }

    /**
     * compileDynamicMessage() 动态编译PB,根据.desc文件的inputstream构建
     * @param fileInputStream
     * @return DynamicMessage 动态编译解析生成的msg对象
     * @throws Exception
     */
    public static DynamicMessage compileDynamicMessage(FileInputStream fileInputStream) throws Exception{
        // 获取文件描述集
        DescriptorProtos.FileDescriptorSet descriptorSet =
                DescriptorProtos.FileDescriptorSet.parseFrom(fileInputStream);

        // 定义除基本类型外 特殊类型的map
        HashMap<String, Descriptors.Descriptor> messageMap = new HashMap<String, Descriptors.Descriptor>();
        HashMap<String, Descriptors.EnumDescriptor> enumTypemap = new HashMap<String, Descriptors.EnumDescriptor>();

        // 定义每个文件描述的临时变量
        Descriptors.FileDescriptor fd = null;

        if (descriptorSet.getFileCount() >= 1) {
            // 解析只处理单文件,获取index为0的FileDescriptorProto
            DescriptorProtos.FileDescriptorProto fdp = descriptorSet.getFileList().get(0);

            // 测试Log使用 List<FileDescriptorProto>大小
            System.out.println("FileList size : " + descriptorSet.getFileList().size());

            // 根据FileDescriptorProto 获取FileDescriptor
            fd = Descriptors.FileDescriptor.buildFrom(fdp, new Descriptors.FileDescriptor[]{});

            // 测试Log使用 List<Descriptor>个数
            System.out.println("FileMsg Message size : " + fd.getMessageTypes().size());
            getAllMessageDescriptor(fd.getMessageTypes(), messageMap, enumTypemap);
            // 测试Log使用 所有嵌套的Enum个数
            System.out.println("-Total Message size : " + messageMap.size());

            // 测试Log使用 List<Descriptor>个数
            System.out.println("FileMsg Enum size : " + fd.getEnumTypes().size());
            getAllEnumDescripor(fd, enumTypemap);
            // 测试Log使用 所有嵌套的Enum个数
            System.out.println("-Total Enum size : " + enumTypemap.size());

            // 解析单Message,以此为主Msg进行解析,获取index为0的MessageType
            if (fd.getMessageTypes().size() >= 1){

                Descriptors.Descriptor descriptor = fd.getMessageTypes().get(0);

                // 构建动态编译Message的Builder
                DynamicMessage.Builder mbuilder = DynamicMessage.newBuilder(descriptor);

                // 测试Log使用 生成类名
                // String className = fdp.getOptions().getJavaPackage() + "."
                //         + fdp.getOptions().getJavaOuterClassname() + "$"
                //         + descriptor.getName();

                // 其实所有的Descriptor主要就三个部分: Fields, EnumTypes, NestedTypes

                // 终于拿到原始消息里所有字段
                List<Descriptors.FieldDescriptor> fileds = descriptor.getFields();

                // 构建Map
                // List<Descriptors.EnumDescriptor> enums = descriptor.getEnumTypes();
                // List<Descriptors.Descriptor> msgs = descriptor.getNestedTypes();

                // 测试Log使用 Descriptors.Descriptor可以获取 Fields, EnumTypes, NestedTypes
                // System.out.println(descriptor.getEnumTypes().get(0).getFullName());
                // System.out.println(descriptor.getNestedTypes().get(0).getFullName());

                // 测试Log使用 List<FiledDescriptor>大小
                System.out.println("Main Field size : " + fileds.size() + "\n" );

                // 测试Log使用 获取Field详情
                // testAllFileds(descriptor.getFields());

                // 构建MainMsg的所有Fileds
                buildFields(fileds, mbuilder, messageMap, enumTypemap, descriptor.getFullName());

                // build出PB消息
                DynamicMessage message = (DynamicMessage) mbuilder.build();

                // 测试Log使用 序列化反序列化等功能测试
                testPBMsg(message);

                return message;

            }else {
                System.err.println("Message数量为0");
                System.exit(1);
            }
        }else {
            System.err.println("File数量为0");
            System.exit(1);
        }
        return null;
    }

    /**
     * buildFields() 根据Field的描述集构建builder
     * @param fileds
     * @param mbuilder
     * @param messageMap
     * @param enumTypemap
     */
    private static void buildFields(List<Descriptors.FieldDescriptor> fileds, DynamicMessage.Builder mbuilder,
        HashMap<String, Descriptors.Descriptor> messageMap, HashMap<String, Descriptors.EnumDescriptor> enumTypemap,
        String pName) {
        for(Descriptors.FieldDescriptor filed : fileds) {
            String name = pName + "." + filed.getName();
            // 拿到字段,根据字段开始插值
            if (filed.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                // 测试Log使用 获取嵌套Message类型名称
                // System.out.println(filed.getMessageType().getFullName());
                if (filed.isRepeated()) {
                    DynamicMessage addMsg1 = compileDynamicMessage4Nested(filed.getMessageType(), messageMap,
                        enumTypemap, name);
                    List msgList = getRepeatedObj4Message(filed);
                    msgList.add(addMsg1);
                    mbuilder.setField(filed, msgList);
                }else {
                    DynamicMessage addMsg = compileDynamicMessage4Nested(filed.getMessageType(), messageMap,
                        enumTypemap, name);
                    mbuilder.setField(filed, addMsg);
                }
                // to-do buildMessageEntity()
            }else if (filed.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                if (filed.isRepeated()) {
                    List enumlist = getRepeatedObj4Message(filed);
                    enumlist.add(enumTypemap.get(filed.getEnumType().getFullName()).getValues().get(1));
                    mbuilder.setField(filed, enumlist);
                }else {
                    mbuilder.setField(filed, enumTypemap.get(filed.getEnumType().getFullName()).getValues().get(1));
                }
                // to-do buildEnumEntity()
            }else {

                if (filed.isRepeated()) {
                    List addList = getRepeatedObj4Message(filed);
                    mbuilder.setField(filed, addList);
                }else {
                    mbuilder.setField(filed, getObject4Message("123",filed.getJavaType()));
                }
            }
        }
    }

    public static Object buildInt(String value) {
        return Integer.valueOf(value);
    }

    public static Object buildLong(String value) {
        return Long.valueOf(value);
    }

    public static Object buildFloat(String value) {
        return Float.valueOf(value);
    }

    public static Object buildDouble(String value) {
        return Double.valueOf(value);
    }

    public static Object buildString(String value) {
        return String.valueOf(value);
    }
    public static Object buildBoolean(String value) {
        return Boolean.valueOf(value);
    }

    public static Object buildByteString(String value){
        return  ByteString.copyFrom(value, Charset.forName("UTF8"));
    }

    public static void buildEnum(){

    }

    public static List buildRepeatedInt(){
        List res = new LinkedList<Integer>();
        for (int i = 0; i < 6; i++) {
            res.add(Integer.valueOf(i));
        }
        return res;
    }
    public static List buildRepeatedLong(){
        List res = new LinkedList<Long>();
        for (int i = 0; i < 6; i++) {
            res.add(Long.valueOf(i));
        }
        return res;
    }

    public static List buildRepeatedFloat(){
        List res = new LinkedList<Float>();
        for (int i = 0; i < 6; i++) {
            res.add(Float.valueOf(i));
        }
        return res;
    }
    public static List buildRepeatedDouble(){
        List res = new LinkedList<Double>();
        for (int i = 0; i < 6; i++) {
            res.add(Double.valueOf(i));
        }
        return res;
    }
    private static List buildRepeatedBoolean() {
        List res = new LinkedList<Boolean>();
        for (int i = 0; i < 6; i++) {
            res.add(Boolean.valueOf((i%2) == 0 ? true : false));
        }
        return res;
    }

    public static List buildRepeatedString(){
        List res = new LinkedList<String>();
        for (int i = 0; i < 6; i++) {
            res.add(String.valueOf(i));
        }
        return res;
    }

    public static void buildRepeatedEnum(){
    }

    public static void buildRepeatedMessage(){
    }



    public static List buildRepeatedByteString(){
        List res = new LinkedList<ByteString>();
        res.add(ByteString.copyFrom("abc", Charset.forName("UTF8")));
        res.add(ByteString.copyFrom("中文", Charset.forName("UTF8")));
        return res;
    }


    public static void getAllMessageDescriptor(List<Descriptors.Descriptor> list,
        HashMap<String, Descriptors.Descriptor> messageMap, HashMap<String, Descriptors.EnumDescriptor> enumTypemap) {
        if (list == null || list.size() < 1){
            return;
        }
        for (Descriptors.Descriptor descriptor : list) {
            messageMap.put(descriptor.getFullName(), descriptor);
            getAllMessageDescriptor(descriptor.getNestedTypes(), messageMap, enumTypemap);
            getAllEnumDescripor4Per(descriptor.getEnumTypes(), enumTypemap);
        }
    }

    public static void getAllEnumDescripor(Descriptors.FileDescriptor fd,
        HashMap<String, Descriptors.EnumDescriptor> enumTypemap) {
        if (fd == null || fd.getEnumTypes().size() < 1) {
            return;
        }
        for (Descriptors.EnumDescriptor enumDescriptor : fd.getEnumTypes()) {
            enumTypemap.put(enumDescriptor.getFullName(), enumDescriptor);
        }
        return;
    }

    public static void getAllEnumDescripor4Per(List<Descriptors.EnumDescriptor> list,
        HashMap<String, Descriptors.EnumDescriptor> enumTypemap) {
        if (list == null || list.size() < 1){
            return;
        }
        for (Descriptors.EnumDescriptor enumDescriptor : list) {
            enumTypemap.put(enumDescriptor.getFullName(), enumDescriptor);
        }
        return;
    }

    public static DynamicMessage compileDynamicMessage4Nested(Descriptors.Descriptor descriptor,
        HashMap<String, Descriptors.Descriptor> messageMap, HashMap<String, Descriptors.EnumDescriptor> enumTypemap,
            String pName){
        DynamicMessage.Builder mbuilder = DynamicMessage.newBuilder(descriptor);
        // 获取Message中的Fileds
        List<Descriptors.FieldDescriptor> fileds = descriptor.getFields();
        buildFields(fileds, mbuilder, messageMap, enumTypemap, pName);
        // build出PB消息
        DynamicMessage message = (DynamicMessage) mbuilder.build();

        return  message;
    }

    /**
     * 处理PB中的基本类型数据,获取插入PB的Field对象
     * @param rawString
     * @param type
     * @return Object Field对应的Value对象
     */
    public static Object getObject4Message(String rawString, Descriptors.FieldDescriptor.JavaType type) {
        try {
            switch (type) {
                case INT:
                    return buildInt(rawString);
                case LONG:
                    return buildLong(rawString);
                case FLOAT:
                    return buildFloat(rawString);
                case DOUBLE:
                    return buildDouble(rawString);
                case BOOLEAN:
                    return buildBoolean(rawString);
                case STRING:
                    return buildString(rawString);
                case BYTE_STRING:
                    return buildByteString("abc");
                default:
                    // ENUM, MESSAGE, BYTE_STRING 通过Parse解析方法构建走不到这里 未知类型不支持
                    System.out.println(type + " 类型 未知");
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理PB中的repeated类型数据,获取插入PB的List对象
     * @param descriptor
     * @return Object Field对应的Value对象
     */
    public static List getRepeatedObj4Message(Descriptors.FieldDescriptor descriptor){
        Descriptors.FieldDescriptor.JavaType type = descriptor.getJavaType();
        try {
            switch (type) {
                case INT:
                    return buildRepeatedInt();
                case LONG:
                    return buildRepeatedLong();
                case FLOAT:
                    return buildRepeatedFloat();
                case DOUBLE:
                    return buildRepeatedDouble();
                case BOOLEAN:
                    return buildRepeatedBoolean();
                case STRING:
                    return buildRepeatedString();
                case BYTE_STRING:
                    return buildRepeatedByteString();
                case MESSAGE:
                    return new LinkedList<DynamicMessage>();
                case ENUM:
                    return new LinkedList<Descriptors.EnumValueDescriptor>();
                default:
                    System.out.println(type + " 类型 未知");
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将msg写入本地二进制文件
     * @param msg
     * @param path
     * @param fileName
     * @return
     */
    public static boolean write2Binary(DynamicMessage msg, String path, String fileName){
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(new File(path + "/" +fileName));
            msg.writeTo(output);
            output.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从二进制文件读取构建Msg对象
     * @param path
     * @param binaryFileName
     * @param descName
     * @return DynamicMessage 从二进制文件中生成的Msg对象
     */
    public static DynamicMessage readFromBinary(String path, String binaryFileName, String descName){
         FileInputStream inputDesc = null;
         FileInputStream inputBinary = null;
         DynamicMessage msg = null;
         try {
             inputDesc = new FileInputStream(new File(path + "/" + descName));
             inputBinary = new FileInputStream(new File(path + "/" + binaryFileName));

             DescriptorProtos.FileDescriptorSet descriptorSet =
                     DescriptorProtos.FileDescriptorSet.parseFrom(inputDesc);

             DescriptorProtos.FileDescriptorProto fileDescriptorProto = descriptorSet.getFileList().get(0);

             Descriptors.FileDescriptor fileDescriptor = Descriptors.FileDescriptor.
                     buildFrom(fileDescriptorProto, new Descriptors.FileDescriptor[]{});

             msg = DynamicMessage.parseFrom(fileDescriptor.getMessageTypes().get(0), inputBinary);

         }catch (Exception e) {
             e.printStackTrace();
         }

         return msg;
    }

    /**
     * 根据descriptorSet构建多文件多信息解析
     * @param descriptorSet
     */
    public static void multiMessageCompile(DescriptorProtos.FileDescriptorSet descriptorSet){
        // to-do
    }

    public static String bytesToString(ByteString src, String charSet) {
        if(charSet == null || charSet.length() < 1) {
            charSet = "GB2312";
        }
        return bytesToString(src.toByteArray(), charSet);
    }

    public static String bytesToString(byte[] input, String charSet) {
        if(input == null || input.length < 1) {
            return "";
        }

        ByteBuffer buffer = ByteBuffer.allocate(input.length);
        buffer.put(input);
        buffer.flip();

        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;

        try {
            charset = Charset.forName(charSet);
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    // 测试PB工具方法
    private static void testPBMsg(DynamicMessage msg){

        System.out.println("\n------>>>> 开始构建msg模型................");

        System.out.println("Step1:\n===== msg Byte 开始=====");
        for(byte b : msg.toByteArray()){
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + msg.toByteString().size());
        System.out.println("===== msg Byte 结束 =====\n");

        System.out.println("Step2:\n===== 使用msg 反序列化生成对象开始 =====");
        DynamicMessage gd = null;
        try {
            gd = DynamicMessage.parseFrom(msg.getDescriptorForType(), msg.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(gd.toString());
        System.out.println("===== 使用msg 反序列化生成对象结束 =====\n");

        System.out.println("Step3:\n===== 使用msg 转成json对象开始 =====");
        String jsonResult = "";
        try {
            jsonResult = JsonFormat.printer().print(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonResult.toString());
        System.out.println("json数据大小：" + jsonResult.getBytes().length);
        System.out.println("===== 使用msg 转成json对象结束 =====\n");
        System.out.println("------>>>> 构建msg模型结束.................");

    }

    // 测试Filed工具方法
    private static void testFiled(Descriptors.FieldDescriptor field) {
        System.out.println("=========Field=============");

        System.out.println("Field.FullName = " + field.getFullName());
        System.out.println("Field.Name     = " + field.getName());
        System.out.println("Field.Type     = " + field.getJavaType());

        if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
            System.out.println("Field.NestType = " + field.getMessageType().getName());
        }
        if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
            System.out.println("Field.EnumType = " + field.getEnumType().getName());
        }

        System.out.println("=========Field============");
    }

    // 测试AllFileds工具方法
    private static void testAllFileds(List<Descriptors.FieldDescriptor> fields) {
        if (fields == null || fields.size() < 1) {
            System.out.println("Fields are empty...");
            return;
        }
        for (Descriptors.FieldDescriptor field : fields) {
            testFiled(field);
        }
    }

}
