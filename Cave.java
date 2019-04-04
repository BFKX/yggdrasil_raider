[1mdiff --cc src/mapping/Cave.java[m
[1mindex 4c57f6a,da9feaf..0000000[m
[1m--- a/src/mapping/Cave.java[m
[1m+++ b/src/mapping/Cave.java[m
[36m@@@ -4,25 -6,27 +6,38 @@@[m [mimport java.util.Random[m
  import java.util.concurrent.ThreadLocalRandom;[m
  [m
  public class Cave extends Room {[m
[31m--[m
[31m -    public Cave(int width, int height, Random pseudoRandomList, int fillPurcentage) {[m
[31m -        super(width, height, pseudoRandomList);[m
[31m -        fillPurcentage = ThreadLocalRandom.current().nextInt(43, 47);[m
[31m -        this.width = width;[m
[31m -        this.height = height;[m
[31m -        this.map = new int[width][height];[m
[32m +    public Cave (int width , int height,Random pseudoRandomList) {[m
[32m +        super (width,height,pseudoRandomList);[m
[32m +        int fillPurcentage = ThreadLocalRandom.current().nextInt(43, 47);[m
[32m +        this.width=width;[m
[32m +        this.height=height;[m
[32m +        this.map = new int[width][height] ;[m
          randomFill(fillPurcentage);[m
[31m-         for (int i = 0; i < 25; i++){[m
[32m+         for (int i = 0; i < 25; i++) {[m
              filtering();[m
          }[m
[31m-         for (int i = 0; i < 50; i++){[m
[32m+         for (int i = 0; i < 50; i++) {[m
              additiveFiltering();[m
          }[m
[31m -        for (int i= 0)[m
          placeWall();[m
      }[m
  [m
[32m++    public void removeSmall(){[m
[32m++        int setvalue =0;[m
[32m++        for (int i = 0; i < width; i++) {[m
[32m++            for (int j = 0; j < height; j++) {[m
[32m++                setvalue--;[m
[32m++                if (detection(i,j,setvalue,0,0,15)){[m
[32m++                    remplacement(i,j,setvalue,0);[m
[32m++                }[m
[32m++            }[m
[32m++        }[m
[32m++    }[m
[32m++[m
[32m++[m
      /**[m
       * generation d'un bruit[m
[32m+      *[m
       * @param fillPurcentage[m
       */[m
      public void randomFill(int fillPurcentage) {[m
[36m@@@ -135,29 -194,25 +205,25 @@@[m
          }[m
      }[m
  [m
[31m-     public  void additiveFiltering(){[m
[31m-         int [][] temp = new int [width] [height] ;[m
[31m-         for ( int i = 0 ; i<width ;i++) {[m
[31m-             for (int j = 0; j < height ; j++) {[m
[31m-                 if(map[i][j] == 0) {[m
[31m-                     int sum = 0;[m
[31m-                     for (int l = -2; l < 3; l++) {[m
[31m-                         for (int k = -2; k < 3; k++) {[m
[31m-                             if (i + l < width && j + k >= 0 && j + k < height && i + l>= 0 ) {[m
[31m-                                 sum=sum+ map[i+l][j+k];[m
[31m-                             }[m
[32m+     public void additiveFiltering() {[m
[32m+         int[][] temp = new int[width][height];[m
[31m -        for (int i = 0; i < width; i++) {[m
[32m++        for (int i =0; i < width; i++) {[m
[32m+             for (int j = 0; j < height; j++) {[m
[32m+                 int sum = 0;[m
[32m+                 for (int l = -2; l < 3; l++) {[m
[32m+                     for (int k = -2; k < 3; k++) {[m
[32m+                         if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {[m
[32m+                             sum = sum + map[i + l][j + k];[m
                          }[m
                      }[m
[31m-                     if(sum>12) {[m
[31m-                         temp[i][j] = 1 ;[m
[31m-                     }else {[m
[31m-                         temp[i][j]= map[i][j];[m
[31m-                     }[m
[32m+                 }[m
[32m+                 if (sum > 12) {[m
[32m+                     temp[i][j] = 1;[m
[31m -                } else {[m
[31m -                    temp[i][j] = map[i][j];[m
[32m +                }else {[m
[31m-                     temp[i][j]= map[i][j];[m
[32m++[m
                  }[m
              }[m
[31m-             }[m
[32m+         }[m
          for ( int i = 0 ; i<width;i++) {[m
              for (int j = 0; j < height ; j++) {[m
                  map[i][j]=temp[i][j];[m
[1mdiff --git a/src/mapping/Map.java b/src/mapping/Map.java[m
[1mindex 6c58404..b45f693 100644[m
[1m--- a/src/mapping/Map.java[m
[1m+++ b/src/mapping/Map.java[m
[36m@@ -106,7 +106,7 @@[m [mpublic class Map {[m
                     case 41: sprite = swCorner; break;[m
                     case 82: sprite = seCorner; break;[m
                     case 163: sprite = neCorner; break;[m
[31m-                    default: sprite = voidImage;[m
[32m+[m[32m                    default: sprite = red;[m
                 }[m
                 gc.drawImage(sprite, (column - ((int)(positionCharac.getX() / side) - 19)) * side - positionCharac.getX() % side, (line - ((int)(positionCharac.getY() / side) - 11)) * side - positionCharac.getY() % side, side, side);[m
             }[m
