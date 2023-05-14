package gygk.hungrygame.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gygk.hungrygame.pojo.GameItem;
import gygk.hungrygame.pojo.GameMap;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.List;

public class FileUtil {


    public static void writeGameItemFiles(String uri, List<GameItem> arenas){
        File f1 = new File(uri);
        try {
            f1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String s1 = JSON.toJSONString(arenas);
//        String s = gson.toJson(arenas);
        try {
            FileWriter fileWriter = new FileWriter(f1.getAbsolutePath());
            fileWriter.write(s1);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<GameItem> readGameItemFiles(String uri){
        File f1 = new File(uri);
        BufferedReader br = null;
        Gson gson = new Gson();
        try {
            br = new BufferedReader(new InputStreamReader
                    (new FileInputStream(new File
                            (f1.getAbsolutePath())
                    ), "GBK"));//UTF-8
            String str = null;
            String s1 = "";
            int i = 0;
            while ((str = br.readLine()) != null) {
                String[] v1 = str.trim().split("\\s+"); //剔除调前、后、中间所有的空格
//                System.out.println(str);
                s1 = s1 + str;
                i++;
            }
            List<GameItem> plist = gson.fromJson(s1, new TypeToken<List<GameItem>>(){}.getType());


            return plist;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void writeGameMapFiles(String uri, GameMap gameMap){
        File f1 = new File(uri);
        try {
            f1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String s1 = JSON.toJSONString(gameMap);
//        String s = gson.toJson(arenas);
        try {
            FileWriter fileWriter = new FileWriter(f1.getAbsolutePath());
            fileWriter.write(s1);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static GameMap readGameMapFiles(String uri){
        File f1 = new File(uri);
        BufferedReader br = null;
        Gson gson = new Gson();
        try {
            br = new BufferedReader(new InputStreamReader
                    (new FileInputStream(new File
                            (f1.getAbsolutePath())
                    ), "GBK"));//UTF-8
            String str = null;
            String s1 = "";
            int i = 0;
            while ((str = br.readLine()) != null) {
                String[] v1 = str.trim().split("\\s+"); //剔除调前、后、中间所有的空格
//                System.out.println(str);
                s1 = s1 + str;
                i++;
            }
            GameMap gameMap = gson.fromJson(s1, GameMap.class);


            return gameMap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
