package com.haru.tools;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class MathTool {
    /**
     *
     * @param px 原点x坐标
     * @param py 原点y坐标
     * @param mx 当前点x坐标
     * @param my 当前点y坐标
     * @return
     */
    public static double getAngle(float px, float py, float mx, float my){
        double x = Math.abs(px-mx);
        double y = Math.abs(py-my);
        double z = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        double cos = y/z;
        double radina = Math.acos(cos);//用反三角函数求弧度
        double angle = Math.floor(180/(Math.PI/radina));//将弧度转换成角度

        if(mx>px&&my>py){//鼠标在第四象限
            angle = 180 - angle;
        }

        if(mx==px&&my>py){//鼠标在y轴负方向上
            angle = 180;
        }

        if(mx>px&&my==py){//鼠标在x轴正方向上
            angle = 90;
        }

        if(mx<px&&my>py){//鼠标在第三象限
            angle = 180+angle;
        }

        if(mx<px&&my==py){//鼠标在x轴负方向
            angle = 270;
        }

        if(mx<px&&my<py){//鼠标在第二象限
            angle = 360 - angle;
        }
        return angle;
    }
    public static float[] roate(double x1, double y1, double x0, double y0, double angle){
        float[] point = new float[2];
        angle=new Float(Math.toRadians(angle));
        point[0]=new Float((x1-x0)*Math.cos(angle) +(y1-y0)*Math.sin(angle)+x0);
        point[1]=new Float(-(x1-x0)*Math.sin(angle) + (y1-y0)*Math.cos(angle)+y0);
        return point ;
    }

    public static float getBiggerWithAbs(float a, float b){
        if(Math.abs(a) > Math.abs(b)){
            return  a ;
        }
        return b ;
    }
}
