package cc.cannot;

/**
 * Created by Danceiny@GitHub.com on 2017/7/17.
 *
 * Problem Descrition  ##########
 *
 * You are given an n x n 2D matrix representing an image.

 Rotate the image by 90 degrees (clockwise).

 Follow up:
 Could you do this in-place?
 */
public class rotate_image {
    // n x n, so matrix.length = matrix[0].length
    public void rotate_clockwise(int[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=i; j<matrix[0].length; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        for(int i=0; i<matrix.length; i++){
            for(int j=i; j<matrix.length/2; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length - 1 -j];
                matrix[i][matrix.length - 1 - j] = temp;
            }
        }
    }
}
