package com.fl.web.utils;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：JasyptUtil
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-22 10:40
 */
public class JasyptUtil {
    public static void main(String[] args) {

//        String url="jdbc:mysql://10.1.0.211:3306/hcerp?useUnicode=true&useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true";
//        String url = "jdbc:mysql://10.1.0.228:3306/hcerpqas?useUnicode=true&useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true";
//        String url = "jdbc:mysql://10.1.0.228:3306/hcerpdev?useUnicode=true&useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true";
        String url = "jdbc:mysql://localhost:3306/hcerpdev?useUnicode=true&useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC";

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // application.properties, jasypt.encryptor.password
        encryptor.setPassword("hcsci");
        // encrypt root
        System.out.println(encryptor.encrypt(url));
        // decrypt, the result is root
//        System.out.println(encryptor.decrypt("r1wYFlOuS0QZuZYf/kZalw=="));


    }
}
