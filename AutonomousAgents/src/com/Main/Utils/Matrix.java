package com.Main.Utils;

public class Matrix {

	private int[] matrix;
	public Matrix() {
	  matrix=new int[16];	
	}
	public Matrix(int[] matrix) {
		this.matrix=matrix;
	}
	public int Get(int col,int row) {
		return matrix[(row*4)+col];
	}
	public int GetMax(int col) {
		int max=0;
		for(int i=0;i<4;i++) {
			if(Get(col,i)>max)max=Get(col,i);
		}
		return max;
	}
	public int GetAction(int col) {
		int max=0;
		int index=0;
		for(int i=0;i<4;i++) {
			if(Get(col,i)>max) {
				max=Get(col,i);
				index=i;
			}
		}
		return index;
	}
	public void Set(int col,int row,int value) {
		matrix[(row*4)+col]=value;
	}
	public void Output() {
		System.out.println(matrix[0]+":"+matrix[1]+":"+matrix[2]+":"+matrix[3]);
		System.out.println(matrix[4]+":"+matrix[5]+":"+matrix[6]+":"+matrix[7]);
		System.out.println(matrix[8]+":"+matrix[9]+":"+matrix[10]+":"+matrix[11]);
		System.out.println(matrix[12]+":"+matrix[13]+":"+matrix[14]+":"+matrix[15]);
	}
}
