package com.anfy.java2.lesson4;

public class Main {

    static void calculate(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (arr[i] * (float) Math.sin(0.2f + i / 5) * (float) Math.cos(0.2f + i / 5) * (float) Math.cos(0.4f + i / 2));
        }
    }

    static float[][] split(float[] scr, int count) {
        if (count < 2) {
            float[][] dest = new float[1][];
            dest[0] = scr;
            return dest;
        }
        int length = scr.length / count;
        float[][] dest = new float[count][];
        int p = 0;
        count--;
        int i;
        for (i = 0; i < count; i++) {
            dest[i] = new float[length];
            System.arraycopy(scr, p, dest[i], 0, length);
            p += length;
        }
        length = scr.length - p;
        dest[i] = new float[length];
        System.arraycopy(scr, p, dest[i], 0, length);
        return dest;
    }

    static float[] stick(float[][] scr) {
        int length = 0;
        //Сначала посчитаем количество элементов в получаемом массиве
        for (int i = 0; i < scr.length; i++) length += scr[i].length;

        float[] dest = new float[length];

        //Теперь запихиваем всё в один массив
        int p = 0;
        for (int i = 0; i < scr.length; i++) {
            length = scr[i].length;
            System.arraycopy(scr[i], 0, dest, p, length);
            p += length;
        }
        return dest;
    }

    static float[] initTestArr(int length, float val) {
        float[] arr = new float[length];
        for (int i = 0; i < length; i++) {
            arr[i] = val;
        }
        return arr;
    }

    static class myThread extends Thread {

        float arr[];

        myThread(float arr[]) {
            this.arr = arr;
        }

        @Override
        public void run() {
            calculate(arr);
        }
    }

    static void doMultiFlowsTest(int length, float initVal, int flowsCount) {

        if (flowsCount < 2) {
            doSingleFlowTest(length, flowsCount);
            return;
        }

        float[] scrArr = initTestArr(length, initVal);
        float[] destArr;

        long startTime = System.currentTimeMillis(); //Start timer

        float[][] splitArs = split(scrArr, flowsCount);
        Thread[] treads = new Thread[flowsCount];
        for (int i = 0; i < flowsCount; i++) {
            treads[i] = new myThread((float[]) splitArs[i]);
        }
        for (int i = 0; i < flowsCount; i++) { //Делаем в разных циклах для того чтобы потоки запустились примерно одновременно
            treads[i].start();
        }
        destArr = stick(splitArs);

        long stopTime = System.currentTimeMillis(); //Stop timer

        System.out.printf("%d threads test time is: %d\n", flowsCount, stopTime - startTime);
    }

    static void doSingleFlowTest(int length, float initVal) {
        float[] scrArr = initTestArr(length, initVal);

        long startTime = System.currentTimeMillis(); //Start timer

        calculate(scrArr);

        long stopTime = System.currentTimeMillis(); //Stop timer

        System.out.printf("single threads test time is: %d\n", stopTime - startTime);
    }


    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            doMultiFlowsTest(10000000, 1, i);
        }
    }
}
