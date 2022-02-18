/*
 * Copyright (C) 2021 Karpenko Aleksandr Aleksandrovich
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ColorGenerator;

import java.util.LinkedList;

/**
 *Среда (главный класс) функции генерации аддитивной схемы светлоты цветовой
 * палитры.
 * @author Karpenko Aleksandr Aleksandrovich
 */
public class BrightnessScheme {
    /**
     * Функция генерации аддитивной цветовой схемы светлоты с заданным числом
     * цветов:
     * 1. в случае 1 цвета в результате выдается цвет, увеличенный на указанное
     * число % светлоты;
     * 2. в случае множества цветов в результате выдается массив цветов в форма-
     * те RGB в виде градации от заданного цвета (первый цвет массива) до пол-
     * ностью белого (последний цвет массива).
     * 
     * Цвет генерируется путем увеличения интенсивности каждого из спектров с
     * сохранением основного цвета и изменения прозрачности/светлоты (насыщен-
     * ность не меняется).
     * 
     * Пользователь самостоятельно задает цвет или прибегает к генерации
     * исходного цвета для построения схемы.
     * 
     * @param p - если isComplex == true, то p - это число цветов генерации, ед.;
     * если isComplex == false, то p - это процент осветления цвета относительно
     * первоначального, %.
     * @param isComplex - "реле" выбора генерации множества цветов (true - цвета
     * генерируются случайно, false - цвета генерируются с отсчетом от задан-
     * ного цвета R,G,B);
     * @param isRandom - "реле" выбора генерации множества цветов (случайный
     * выбор цветов или пользователь сам задает начальный цвет);
     * @param isForward - "реле" выбора направления генерации схемы: либо
     * увеличение светлоты цветов, либо уменьшение;
     * @param R0 - значение интенсивности красного цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @param G0 - значение интенсивности зеленого цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @param B0 - значение интенсивности синего цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @return
     */
    
    public static LinkedList<Integer[]> generateBrightnessScheme(int p, boolean isComplex, boolean isRandom, boolean isForward, int R0, int G0, int B0) {
        //Шаг 0: инициализация переменных:
        LinkedList<Integer[]> colors = new LinkedList();
        Integer[] mas = new Integer[3];
        int maxR = 255;
        int maxG = maxR;
        int maxB = maxR;
        int minR = 60;
        int dR;
        int dG;
        int dB;
        if (isRandom) {
            //генерация случайных цветов
            R0 = (int) (20+Math.random()*(240-20));
            G0 = (int) (20+Math.random()*(240-20));
            B0 = (int) (20+Math.random()*(240-20));
        }
        //Шаг 1: работа с генерацией множества цветов:
        if (isComplex) {
        //1.1. Расчет величины шага для каждого цвета:
            dR = (int) (maxR-R0)/p;
            dG = (int) (maxG-G0)/p;
            dB = (int) (maxB-B0)/p;
            if (dR >0 && dG >0 && dB>0) {
        //1.2. Расчет цветов (в т.ч. цвет от пользователя):
                for (int i = 0; i<=p;i++) {
                    mas[0] = R0+i*dR;
                    if (mas[0]>255) {
                        mas[0] = 255;
                    }
                    mas[1] = G0+i*dG;
                    if (mas[1]>255) {
                        mas[1] = 255;
                    }
                    mas[2] = B0+i*dB;
                    if (mas[2]>255) {
                        mas[2] = 255;
                    }
                    colors.addLast(mas);
                }
            }
        } else {
        //Шаг 2: работа с генерацией одного числа.
            if (p>1) { //пользователь указал проценты, а не доли
                mas[0] = R0+p/100*(maxR-R0);
                if (mas[0]>255) {
                    mas[0] = 255;
                }
                mas[1] = G0+p/100*(maxG-G0);
                if (mas[1]>255) {
                    mas[1] = 255;
                }
                mas[2] = B0+p/100*(maxB-B0);
                if (mas[2]>255) {
                    mas[2] = 255;
                }
                colors.addLast(mas);
            } else { //пользователь указал доли, а не проценты
                mas[0] = R0+p*(maxR-R0);
                if (mas[0]>255) {
                    mas[0] = 255;
                }
                mas[1] = G0+p*(maxG-G0);
                if (mas[1]>255) {
                    mas[1] = 255;
                }
                mas[2] = B0+p*(maxB-B0);
                if (mas[2]>255) {
                    mas[2] = 255;
                }
                colors.addLast(mas);
            }
        }
        return colors;
    }
}