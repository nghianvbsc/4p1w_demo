package vinaedu.a4pics1word.common.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import vinaedu.a4pics1word.model.objects.ItemOutput;

/**
 * Created by bscenter on 14/08/2016
 */
public class QuestionUtil {
    private static int sumCharacterInput = 14;
    public static String strCharacter = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

    public static String randomListTextForChoise(String trueAnswer) {
        String result = "";
        ArrayList<String> arrResult = new ArrayList<>();
        int lengthTrueAnswer = trueAnswer.length();
        int countTextAdd = sumCharacterInput - lengthTrueAnswer;
        int index = 0;

        String[] listCharacter = strCharacter.trim().split(" ");
        String[] listCharacterTrueAnswer = trueAnswer.trim().split("");

        for (String s : listCharacterTrueAnswer) {
            if (!s.trim().equals("")) arrResult.add(s);
        }

        HashMap<String, String> mapCharacter = convertArrStringToMap(listCharacter);

        for (String s : arrResult) {
            if (mapCharacter.containsKey(s)) {
                mapCharacter.remove(s);
            }
        }

        ArrayList<String> libaryCharacter = convertMapToArrayList(mapCharacter);
        for (index = 0; index < countTextAdd; index++) {
            int min = 0;
            int max = libaryCharacter.size() - 1;
            Random r = new Random();
            int pisition = r.nextInt(max - min + 1) + min;
            String characterAddToListAnswer = libaryCharacter.get(pisition);
//            libaryCharacter.remove(pisition);
            arrResult.add(characterAddToListAnswer);

        }


        Log.d("Utils", "list answer to choise before shuffle: " + arrResult.toString());
        Collections.shuffle(arrResult);
        Log.d("Utils", "list answer to choise affter shuffle: " + arrResult.toString());

        return convertArrStringToString(arrResult);

    }

    public static HashMap<String, String> convertArrStringToMap(String[] listString) {
        HashMap<String, String> map = new HashMap<>();
        for (String s : listString) {
            map.put(s.trim(), s.trim());
        }

        return map;
    }

    public static ArrayList<String> convertMapToArrayList(Map<String, String> map) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(entry.getValue().toString().trim());
        }

        return arrayList;
    }

    public static String convertArrStringToString(ArrayList<String> strString) {
        String result = "";
        for (String s : strString) {
            result += s;
        }

        return result;
    }

    public static ArrayList<String> convertStringToArraylist(String strString) {
        ArrayList<String> result = new ArrayList<>();
        String[] arr = strString.split("");

        for (String s : arr) {
            if (!s.trim().equals(""))
                result.add(s);
        }

        return result;
    }

    public static ArrayList<ItemOutput> createListAnswer(int lengthTrueAnswer) {
        ArrayList<ItemOutput> list = new ArrayList<>();
        for (int index = 0; index < lengthTrueAnswer; index++) {
            ItemOutput itemOutPut = new ItemOutput();
            itemOutPut.setCharacter("");
            list.add(itemOutPut);
        }

        return list;
    }

    public static ArrayList<ItemOutput> createListObjectAnswer(ArrayList<String> strings) {
        ArrayList<ItemOutput> list = new ArrayList<>();

        for (String s : strings) {
            ItemOutput newItem = new ItemOutput();
            newItem.setCharacter(s.trim());
            list.add(newItem);
        }
        return list;
    }

}
