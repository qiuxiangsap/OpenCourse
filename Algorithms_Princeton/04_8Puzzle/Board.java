package com.sap.nic.pqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.introcs.StdRandom;


public class Board {
	
	private final char[] board;
	
	private final char[] sol;
	private final int len;
	private int manhDist;
	
	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(final int[][] blocks) {

		this.len = blocks.length * blocks.length;
		int dim = blocks.length;
		
		board = new char[len];
		sol = new char[len];
		
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				board[i * dim + j] = (char)blocks[i][j];
			}
		}
		
		for (int i = 0; i < len - 1; i++) {
			sol[i] = (char)(i + 1);
		}
		sol[len - 1] = (char) 0;
		calcManhattan();
		
	}
	
	// board dimension N
	public int dimension() {
		return (int)Math.sqrt(this.len);
	}

	// number of blocks out of place
	public int hamming() {
		int notSame = 0;
		for (int i = 0; i < len ; i++) {
			if (board[i] != 0 && board[i] != (i + 1)) {
				notSame++;
			}
		}

		return notSame;
	}

	private void calcManhattan() {
		int dist = 0;
		final int dim = (int) Math.sqrt(len);
		for (int i = 0; i < len; i++) {
			if (board[i] != 0) {
				final int digit = board[i];
				final int row = (digit - 1) / dim;
				final int column = (digit - 1) % dim;
				
				final int rrow = i / dim;
				final int rcol = i % dim;
				dist += ( Math.abs(rrow - row) + Math.abs(rcol - column)); 
				
			}
		}
		
		manhDist = dist;
		
	}
	

	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhDist;
	}

//	// is this board the goal board?
	public boolean isGoal() {
		return Arrays.equals(board, sol);
	}
	
	public Board twin() {
		int size = dimension();
        final int [ ][ ] copy = new int[size][size];
        for (int i = 0; i < size * size; i++)
            copy[i / size][i % size] = board[i];

        int row = StdRandom.uniform(0, size);
        while (copy[row][0] == 0 || copy[row][1] == 0)
            row = StdRandom.uniform(0, size);

        exch(copy, row, 0, row, 1);
        return new Board(copy);
	}

	// a board obtained by exchanging two adjacent blocks in the same row
	public Board twin1() {
		
		
		final int dim = dimension();
		final int[][] b = new int[dim][dim];
		
		for (int i = 0; i < len; i++) {
			b[i / dim][i % dim] = (int)board[i];
		}
		
		while (true) {
			Random random = new Random();
			int i = random.nextInt(dim);
			int j = random.nextInt(dim - 1);
			
			if (b[i][j] != 0 && b[i][j + 1] != 0) {
				int tmp = b[i][j];
				b[i][j] = b[i][j + 1];
				b[i][j + 1] = tmp;
				break;
			}

			
		}
		
		Board bd = new Board(b);
		return bd;
		

	}

	// does this board equal y?
	public boolean equals(final Object y) {
		if (y == this) return true;
		if (y == null) return false;
		
		if (y.getClass() != this.getClass()) {
			return false;
		}
		
		final Board yBoard = (Board) y;
		return Arrays.equals(yBoard.board, board);
	}
	
	
	private void exch(final int[][]arr, final int orgx, final int orgy, final int destx, final int desty) {
//
//		char[] cpy = new char[len];
//		System.arraycopy(board, 0, cpy, 0, len);
//		char tmp = cpy[org];
//		cpy[org] = cpy[dest];
//		cpy[dest] = tmp;
//		
//		Board copy = new Board(cpy);
//	
//		return copy;
		final int tmp = arr[orgx][orgy];
		arr[orgx][orgy] = arr[destx][desty];
		arr[destx][desty] = tmp;
		
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		final int dim = dimension();
		final ArrayList<Board> neibors = new ArrayList<Board>();
		
		int zeroPos = 0;
		final int[][] b = new int[dim][dim];
		for (int i = 0; i < len; i++) {
			if (board[i] == 0) {
				zeroPos = i;
			}
			b[i / dim][i % dim] = board[i];
		}
		final int i = zeroPos / dim;
		final int j = zeroPos % dim;
		
		if ( (i + 1) < dim) {
			exch(b, i, j, i + 1, j);
			neibors.add(new Board(b));
			exch(b, i, j, i + 1, j);
		}
		
		if ( (i - 1) >= 0 ) {
			exch(b, i, j, i - 1, j);
			neibors.add(new Board(b));
			exch(b, i, j, i - 1, j);
		}
		
		if ( (j + 1) < dim ) {
			exch(b, i, j, i , j + 1);
			neibors.add(new Board(b));
			exch(b, i, j, i , j + 1);
			
		}
		
		if ( (j - 1) >= 0 ) {
			exch(b, i, j, i, j - 1);
			neibors.add(new Board(b));
			exch(b, i, j, i, j - 1);
		}
		
		return neibors;
		
	}

	// string representation of the board (in the output format specified below)
	public String toString() {
		StringBuilder s = new StringBuilder();
		int dim = dimension();
		s.append(dim + "\n");

		for (int i = 0; i < len; i++) {
			s.append((int)board[i] + " ");
			if ( (i + 1) % dim == 0) {
				s.append("\n");
			}
		}
		

		return s.toString();
	}

	public static void main(String[] args) {
		int[][] board = new int[3][3];
		board[0][0] = 8;
		board[0][1] = 1;
		board[0][2] = 3;
		board[1][0] = 4;
		board[1][1] = 0;
		board[1][2] = 2;
		board[2][0] = 7;
		board[2][1] = 6;
		board[2][2] = 5;
		
		Board board2 = new Board(board);
		System.out.println(board2.manhattan());
		System.out.println(board2.hamming());
		System.out.println(board2.isGoal());
		
	}

}
