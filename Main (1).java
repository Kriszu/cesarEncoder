package com.company;


import javax.swing.*;
import java.math.BigDecimal;

import java.util.*;




public class Main {

    private static HashMap<Character, Double> freqMap;

    static {
        freqMap = new HashMap<>();
        freqMap.put('a', 0.08167);
        freqMap.put('b', 0.01492);
        freqMap.put('c', 0.02782);
        freqMap.put('d', 0.04253);
        freqMap.put('e', 0.12702);
        freqMap.put('f', 0.02228);
        freqMap.put('g', 0.02015);
        freqMap.put('h', 0.06094);
        freqMap.put('i', 0.06966);
        freqMap.put('j', 0.00153);
        freqMap.put('k', 0.00772);
        freqMap.put('l', 0.04025);
        freqMap.put('m', 0.02406);
        freqMap.put('n', 0.06749);
        freqMap.put('o', 0.07507);
        freqMap.put('p', 0.01929);
        freqMap.put('q', 0.00095);
        freqMap.put('r', 0.05987);
        freqMap.put('s', 0.06327);
        freqMap.put('t', 0.09056);
        freqMap.put('u', 0.02758);
        freqMap.put('v', 0.00978);
        freqMap.put('w', 0.0236);
        freqMap.put('x', 0.0015);
        freqMap.put('y', 0.01974);
        freqMap.put('z', 0.00074);
    }

    private static String cesarToString(String cesarString, int x){
        String realString = "";
        char arr[] = cesarString.toCharArray();
        for(char c : arr){
            if((char)(c-x) < 97){
                c = (char) (c-x + 26);
            } else{
                c = (char) (c-x);
            }
            realString = realString + c;

        }
        return realString;
    }

    private static HashMap<Character,Double> createCesarFreqList(String string){

        HashMap<Character,Double> freqMap = new HashMap<>();
        for(int i=0; i<26; ++i){
            freqMap.put((char)('a'+i),freqOfCharAtString(string,(char)('a'+i)));
        }
        return freqMap;

        }


    private static double freqOfCharAtString(String string, char c){
        double freq;
        double pom = 0;
        char arr[] = string.toCharArray();
        for (char x: arr
             ) {
            if(x==c){
                pom++;
            }
        }
        freq = (pom/arr.length);
        return freq;
    }


    private static HashMap<Integer, BigDecimal> freqofCharAtCesarString2(String cesarString, BigDecimal value){
        HashMap<Character,Double> cesarFreq = createCesarFreqList(cesarString);
        HashMap<Integer, BigDecimal> sumMap = new HashMap<>();
        double suma = 0;
        for (int i = 0; i<26; ++i){
            for(int j = 0; j<26; ++j){
                suma = suma + cesarFreq.get((char)('a'+(i+j)%26)) * freqMap.get((char)('a'+j));
            }
            BigDecimal temp = BigDecimal.valueOf(suma);
            sumMap.put(i, value.subtract(temp));
            suma = 0;
        }
        return sumMap;
    }


    private static int move2(String string){

        freqMap.entrySet().forEach(e -> e.setValue(e.getValue() * e.getValue()));
        BigDecimal p = new BigDecimal(freqMap.values().stream().mapToDouble(Number::doubleValue).sum());
        return freqofCharAtCesarString2(string, p).entrySet().stream().min(Comparator.comparing(Map.Entry::getValue))
                .orElseThrow(()->new IllegalArgumentException("Wartość nie istnieje :(")).getKey();

    }

    private static String cesarToString(String cesarString){

        return cesarToString(cesarString,move2(cesarString));
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame("Cezar encoder: ");
        frame.setSize(1000,600);
        frame.setVisible(true);
        String x = JOptionPane.showInputDialog(frame, "Podaj tekst zaszyfrowany: ");
        JTextArea y = new JTextArea("Przesunięcie o "+move2(x)+"\n tekst odszyfrowany: " + cesarToString(x));
        frame.add(y);
        y.setLineWrap(true);
        y.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(y);

    }
}
