/*
 * Copyright (C) 2020 Karpenko Aleksandr Aleksandrovich
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
 *Среда (главный класс) функции генерации цветовой палитры.
 * @author Karpenko Aleksandr Aleksandrovich
 */
public class AnalogScheme {
     /**
     * Функция генерации аддитивной аналоговой цветовой схемы с заданным числом
     * цветов, выбором случайной или детерминированной генерации цветов и, в
     * случае последней, с набором начального цвета.
     * 
     * Цвет генерируется путем прохода по цветовому кругу с помощью смены
     * значений интенсивности цветов в формате RGB.
     * 
     * NOTE: Если генерация цвета детерминирована, пользователь должен за-
     * дать начальные значения интенсивности в качестве переменных R,G и B.
     * Значения интенсивностей цветов должны задаваться:
     * - в явном виде, где значение интенсивности любого цвета является цело-
     *   численным и может принимать значения от 0 до 255;
     * - в случае интеграции в модель AnyLogic в качестве начальных значений
     *   должна задаваться функция userColorParse(Color, ringN), где Color -
     *   буквенное обозначение цвета, значение интенсивности которого указы-
     *   вается (type String), ring N - порядковый номер прямоугольника, из
     *   которого извлекается цвет, выбранный пользователем (type Integer).
     * 
     * @param p - число цветов генерации, ед.;
     * @param isRandom - "реле" стохастической генерации цветов (true - цвета
     * генерируются случайно, false - цвета генерируются с отсчетом от задан-
     * ного цвета R,G,B);
     * @param R0 - значение интенсивности красного цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @param G0 - значение интенсивности зеленого цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @param B0 - значение интенсивности синего цвета при детерминирован-
     * ой генерации цветов, ед.;
     * @return
     */
    
    
    public static LinkedList<Integer[]> generateAnalogScheme(int p, boolean isRandom, int R0, int G0, int B0) {
        //Шаг0: инициализация переменных.
        LinkedList<Integer[]> colors = new LinkedList<>();
        Integer[] mas = new Integer[p];
        int h = 0; //R ->0, G ->1, B ->2. Смена цветов: R->B->G->R...
        int r = 240-20;
	//traceln("Размах = "+r);
	int s_min = 35;
	int s_max = 120;
	int s = Math.min(Math.max((int)r/p,s_min),s_max);
        int R;
        int G;
        int B;
	//traceln("Шаг = "+s);
	//Шаг 1: генерация первоначальных значений цвета.
	if (isRandom) {	
            //генерация случайных цветов
            R = (int) (20+Math.random()*r);
            G = (int) (20+Math.random()*r);
            B = (int) (20+Math.random()*r);
        } else {
            R = R0;
            G = G0;
            B = B0;
        }
	/*traceln("R = "+R);
	traceln("G = "+G);
	traceln("B = "+B);*/
	//Шаг 2: Запись первого цвета в массив цветов сегментов.
	mas[0] = R;
        mas[1] = G;
        mas[2] = B;
        colors.addLast(mas);
        //Шаг 3: выбор цвета отсчета:
        Integer[] diff = new Integer[3];
        for (int i=0;i<diff.length;i++) {
            diff[i] = Math.max(240-mas[i], mas[i]-20);
        }
        for (int i=0;i<mas.length;i++) {
            if (diff[i]==Math.max(diff[0],Math.max(diff[1],diff[2]))) {
                h = i;
            }
        }
        //Шаг 4: генерация оставшихся цветов.
        boolean increase = true;
        for (int j = 1;j<p;j++) { //проход по всем показателям (сегментам) одного кольца;
            switch(h) {
		case 0 -> {
                    if (R <= 240-s && increase) {
                        // если от текущего до максимального значения интенсивности красного можно совершить шаг смены цвета
                        R+=s;
                        //traceln("R has changed = "+R);
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (R > 240-s) {
                            h = 2;
                            increase = true; // меняем цвет отсчета
                            //traceln("R is near max");
                        }
                    } else {
                        // если нельзя увеличить интенсивность красного на размер шага
                        R-=s;
                        //traceln("R has changed = "+R);
                        increase = false;
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (R-s <= 20) {
                            h = 2;
                            increase = true; // меняем цвет отсчета
                            //traceln("R is near min");
                        }
                    }
                    //}
                }
		case 1 -> {
                    if (G <= 240-s && increase) {
                        // если от текущего до максимального значения интенсивности зеленого можно совершить шаг смены цвета
                        G+=s;
                        //traceln("G has changed = "+G);
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (G > 240-s) {
                            h = 0; // меняем цвет отсчета
                            increase = true;
                            //traceln("G is near max");
                        }
                    } else {
                        // если нельзя увеличить интенсивность зеленого на размер шага
                        G-=s;
                        //traceln("G has changed = "+G);
                        increase = false;
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (G-s <= 20) {
                            h = 0; // меняем цвет отсчета
                            increase = true;
                            //traceln("G is near min");
                        }
                    }
                    //}
                }
		case 2 -> {
                    if (B <= 240-s && increase) { // если от текущего до максимального значения интенсивности синего можно совершить шаг смены цвета
                        B+=s;
                        //traceln("B has changed = "+B);
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (B > 240-s) {
                            h = 1; // меняем цвет отсчета
                            increase = true;
                            //traceln("B is near max");
                        }
                    } else { // если нельзя увеличить интенсивность синего на размер шага
                        B-=s;
                        //traceln("B has changed = "+B);
                        increase = false;
                        mas[0] = R;
                        mas[1] = G;
                        mas[2] = B;
                        colors.addLast(mas); //добавляем в список цветов следующий цвет
                        if (B-s <= 20) {
                            h = 1; // меняем цвет отсчета
                            increase = true;
                            //traceln("B is near min");
                        }
                    }
                }
	}
            //if (colorSpectr() == R) { // если цветом отсчета является красный
            //if (colorSpectr() == G) {
            //if (colorSpectr() == B) {
            	}
    return colors;
    }    
}