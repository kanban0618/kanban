package com.kanban.tool;

import com.kanban.config.MyWebConfig;

public class PathTool {
    public static String getCurrentJarPath(){
        String path = PathTool.class.getProtectionDomain().getCodeSource().getLocation().getPath();//获得jar的路径
        System.out.println(path); //file:/D:/Users/Administrator/IdeaProjects/kanban/target/kanban-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
        int i = path.indexOf(".jar")+".jar".length();//找到.jar的位置
        String path2 = path.substring(0, i);//从最开头的0 到 .jar的位置，保存到新的字符串中，其余的不要 file:/D:/Users/Administrator/IdeaProjects/kanban/target/kanban-0.0.1-SNAPSHOT
        System.out.println("新的路径地址"+path2);//新的路径地址file:/D:/Users/Administrator/IdeaProjects/kanban/target/kanban-0.0.1-SNAPSHOT.jar
        int i1 = path2.lastIndexOf("/");//查找最后一个斜杠
        String path3 = path2.substring(0, i1);//去掉jar包的文件名
        int i2 = path3.indexOf("file:/");
        if(i2>=0){ // 如果包含file:/字符子串，就删掉它
            path3 = path3.substring("file:/".length());
        }
        System.out.println("最后的处理结果："+path3);
        return path3;
    }
}
