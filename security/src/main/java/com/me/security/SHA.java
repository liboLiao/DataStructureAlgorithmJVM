package com.me.security;

import org.junit.Test;

/**
 * Created by llb on 2018/8/10.
 */
public class SHA {
    //1.准备工作
    public static final int[] abcde = {
            0x67452301,
            0xEFCDAB89,
            0x98BADCFE,
            0x10325476,
            0xC3D2E1F0
    };
    //摘要数据存储用的数组（存放密文的)  20个字节*8=160；
    public static int[] h=new int[5];
    //计算过程中需要用到的临时数据存储数组
    public static int[] m=new int[80];

    //定义辅助方法

    //将字符转换为十六进制字符串
    public static String byteToHexString(byte b){//97
        char[] digit={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        char[] ob=new char[2];
        ob[0]=digit[(b>>>4)&0x0F];//9
        ob[1]=digit[b&0x0f];//7
        String s=new String(ob);//"97"
        return s;
    }
    //将字节数组转换为十六进制字符串
    public static String byteArrayToHexString(byte[] byteArray){
        String strDigest="";
        for(int i=0;i<byteArray.length;i++){
            strDigest+=byteToHexString(byteArray[i]);
        }
        return strDigest;
    }

    //4字节数组转换为int  i个byte合成到byteData[]中
    public static int byteArrayToInt(byte[] byteData,int i){
        //0a 0b 0c 0d  24       16       8       0
        //         0a000000  or  0b0000  or   0c00  or   0d
        //            0a0b0c0d
        return ((byteData[i]&0xff)<<24)|((byteData[i+1]&0xff)<<16)|((byteData[i+2]&0xff)<<8)|(byteData[i+3]&0xff);
    }
    //整数转换为4字节数组   int分解到byte数组中
    public static void intToByteArray(int intValue,byte[] byteData,int i){
        byteData[i]=(byte)((intValue>>>24)&0xff);
        byteData[i+1]=(byte)((intValue>>>16)&0xff);
        byteData[i+2]=(byte)((intValue>>>8)&0xff);
        byteData[i+3]=(byte)((intValue&0xff));
    }
    /*
    Ft(b,c,d)  ((b&c)|((~b)&d))    (0 <= t <= 19)
    Ft(b,c,d) (b^c^d)             (20 <= t <= 39)
    Ft(b,c,d) ((b&c)|(b&d)|(c&d))  (40 <= t <= 59)
    Ft(b,c,d) (b^c^d)               (60 <= t <= 79)
     */
    public static int f1(int x,int y,int z){
        return (x&y)|(~x&z);
    }
    public static int f2(int x,int y,int z){
        return x^y^z;
    }
    public static int f3(int x,int y,int z){
        return (x&y)|(x&z)|(y&z);
    }
    public static int f4(int x,int y,int z){
        return x^y^z;
    }

    //开始逻辑
    //进行对原数据的补位
    public static byte[] byteArrayFormatData(byte[] byteData){
        //补0的个数
        int fill=0;
        //补位后的总位数,64的倍数
        int size=0;
        //原数据的长度
        int srcLength=byteData.length;
        //对64求余数   n%512      56数据    8长度   53
        int m=srcLength%64;
        if(m<56){
            fill=55-m;
            size=srcLength-m+64;//数据只有一块
        }else if(m==56){
            fill=63;
            size=srcLength+8+64;
        }else{
            fill=63-m+56;//   58    60+56  116-64=52   55-52=3
            size=(srcLength+64)-m+64;
        }
        //补位后生成的新数组的内容
        byte[] newbyte=new byte[size];
        System.arraycopy(byteData,0,newbyte,0,srcLength);

        //补1
        int startLocation=srcLength;
        newbyte[startLocation++]=(byte)0x80;
        //补0
        for(int i=0;i<fill;i++){
            newbyte[startLocation++]=(byte)0x00;
        }
        //处理长度的位置  字节*8=?位   512-468=64位，用来存放长度
        long n=(long)srcLength*8;
        byte h8=(byte)(n&0xff);
        byte h7=(byte)((n>>8)&0xff);
        byte h6=(byte)((n>>16)&0xff);
        byte h5=(byte)((n>>24)&0xff);
        byte h4=(byte)((n>>32)&0xff);
        byte h3=(byte)((n>>40)&0xff);
        byte h2=(byte)((n>>48)&0xff);
        byte h1=(byte)((n>>56));
        newbyte[startLocation++]=h1;
        newbyte[startLocation++]=h2;
        newbyte[startLocation++]=h3;
        newbyte[startLocation++]=h4;
        newbyte[startLocation++]=h5;
        newbyte[startLocation++]=h6;
        newbyte[startLocation++]=h7;
        newbyte[startLocation++]=h8;

        return newbyte;
    }
    //开始计算密文 算摘要
    public static int process_input_bytes(byte[] byteData){
        System.arraycopy(abcde,0,h,0,abcde.length);
        //格式化数据
        byte[] newbyte=byteArrayFormatData(byteData);
        //计算有多少个大块
        int mCount=newbyte.length/64;
        //循环计算每一块的内容
        for(int pos=0;pos<mCount;pos++){
            //对每一块都进行加密计算
            //(1). 将 Mi 分成 16 个字 W0, W1, ... , W15,  W0 是最左边的字
            for(int i=0;i<16;i++){
                m[i]=byteArrayToInt(newbyte,(pos*64)+(i*4));
            }
            //计算
            encrypt();
        }

        return 20;
    }
    //n是一个整数且0<=n<=32。Sn(X) = (X<<n)OR(X>>(32-n))
    public static int s(int x,int i){
        return (x<<i)|x>>>(32-i);
    }
    public static void encrypt(){
        //(2). 对于 t = 16 到 79 令
        // Wt = S1(Wt-3 XOR Wt-8 XOR Wt- 14 XOR Wt-16).
        for(int t=16;t<=79;t++){
            m[t]=s(m[t-3]^m[t-8]^m[t-14]^m[t-16],1);
        }
        //3.令 A = H0, B = H1, C = H2, D = H3, E = H4.
        int[] tempabcde=new int[5];
        for(int i=0;i<tempabcde.length;i++){
            tempabcde[i]=h[i];
        }
        //4.对于 t = 0 到 79，执行下面的循环
        //TEMP = S5(A) + ft(B,C,D) + E + Wt + Kt;
        //E = D; D = C; C = S30(B); B = A; A = TEMP;
        //一共有80次操作
        //Kt = 0x5A827999  (0 <= t <= 19)
//        Kt = 0x6ED9EBA1 (20 <= t <= 39)
//        Kt = 0x8F1BBCDC (40 <= t <= 59)
//        Kt = 0xCA62C1D6 (60 <= t <= 79)
        for(int i=0;i<=19;i++){
            int temp=s(tempabcde[0],5)
                    +f1(tempabcde[1],tempabcde[2],tempabcde[3])
                    +tempabcde[4]
                    +m[i]+0x5A827999;
            tempabcde[4]=tempabcde[3];
            tempabcde[3]=tempabcde[2];
            tempabcde[2]=s(tempabcde[1],30);
            tempabcde[1]=tempabcde[0];
            tempabcde[0]=temp;
        }
        for(int i=20;i<=39;i++){
            int temp=s(tempabcde[0],5)
                    +f2(tempabcde[1],tempabcde[2],tempabcde[3])
                    +tempabcde[4]
                    +m[i]+0x6ED9EBA1;
            tempabcde[4]=tempabcde[3];
            tempabcde[3]=tempabcde[2];
            tempabcde[2]=s(tempabcde[1],30);
            tempabcde[1]=tempabcde[0];
            tempabcde[0]=temp;
        }
        for(int i=40;i<=59;i++){
            int temp=s(tempabcde[0],5)
                    +f3(tempabcde[1],tempabcde[2],tempabcde[3])
                    +tempabcde[4]
                    +m[i]+0x8F1BBCDC;
            tempabcde[4]=tempabcde[3];
            tempabcde[3]=tempabcde[2];
            tempabcde[2]=s(tempabcde[1],30);
            tempabcde[1]=tempabcde[0];
            tempabcde[0]=temp;
        }
        for(int i=60;i<=79;i++){
            int temp=s(tempabcde[0],5)
                    +f4(tempabcde[1],tempabcde[2],tempabcde[3])
                    +tempabcde[4]
                    +m[i]+0xCA62C1D6;
            tempabcde[4]=tempabcde[3];
            tempabcde[3]=tempabcde[2];
            tempabcde[2]=s(tempabcde[1],30);
            tempabcde[1]=tempabcde[0];
            tempabcde[0]=temp;
        }
        //5.令 H0 = H0 + A, H1 = H1 + B, H2 = H2 + C, H3 = H3 + D, H4 = H4 + E.
        for(int i=0;i<tempabcde.length;i++){
            h[i]=h[i]+tempabcde[i];
        }
        //完成了一次操作
        //清除之前的内容，开始下一个块的计算
        for(int i=0;i<m.length;i++){
            m[i]=0;
        }

    }
    //把已经算好的数据提供一个接口进行输入和输出
    public static byte[] getDigestOfBytes(byte[] byteData){
        process_input_bytes(byteData);
        byte[] digest=new byte[20];
        for(int i=0;i<h.length;i++){
            intToByteArray(h[i],digest,i*4);
        }
        return digest;
    }
    public static String getDigestOfString(byte[] byteData){
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }

    @Test
    public void test(){
        //ad93ae3d06a9114b3cbb33b6433ad546f0aa9f42
        //378940973d2f16265b7a7f2a78a253c45d953b0b
        String param="jett";
        System.out.println("加密前:"+param);
        String digest=getDigestOfString(param.getBytes());
        System.out.println("加密后的结果："+digest);
    }
}
