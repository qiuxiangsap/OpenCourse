package com.sap.nic.pqueue;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Solver {

	private final Board inital;
	private int minMoves = 0;
	private SearchNode sol;
	private boolean isFeasible = false;

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		this.minMoves = -1;
		this.inital = initial;
		solve();
	}

	private void solve() {
		final Board twin = inital.twin();
		
		// solve origin and its twin configuration simultaneously

		final MinPQ<SearchNode> pOrg = new MinPQ<SearchNode>();
		final MinPQ<SearchNode> pTwin = new MinPQ<SearchNode>();
		
		pOrg.insert(new SearchNode(inital, 0, null));
		pTwin.insert(new SearchNode(twin, 0, null));
		
		
		
		while(!pOrg.isEmpty() && !pTwin.isEmpty()) {
			final SearchNode orgNode = pOrg.delMin();
			final SearchNode twiNode = pTwin.delMin();
			
			
			if (orgNode.getBoard().isGoal()) {
				isFeasible = true;
				// get Solution;
				sol = orgNode;
				minMoves = orgNode.getMoves();
				break;
			}
			
			if (twiNode.getBoard().isGoal()) {
				isFeasible = false;
				break;
			}
			
			for (final Board b:orgNode.getBoard().neighbors()) {
				if (orgNode.prev() == null || orgNode.prev().getBoard() != b) {
					pOrg.insert(new SearchNode(b, orgNode.getMoves() + 1, orgNode));
				} 
		
			}
			
			for (final Board b:twiNode.getBoard().neighbors()) {
				if (twiNode.prev() == null || twiNode.prev().getBoard() != b) {
					pTwin.insert(new SearchNode(b, twiNode.getMoves() + 1, twiNode));
				} 
		
			}
		}
	}
	
	
	// is the initial board solvable?
	public boolean isSolvable() {
		return isFeasible;
	}

	// min number of moves to solve initial board; -1 if no solution
	public int moves() {
		return minMoves;
	}

	// sequence of boards in a shortest solution; null if no solution
	public Iterable<Board> solution() {
		if (isFeasible) {
			Stack<Board> solution = new Stack<Board>();
			solution.push(sol.getBoard());
			
			SearchNode node = sol;
			
			while(true) {
				
				SearchNode curNode = node.prev();
				if (curNode != null) {
					solution.push(curNode.getBoard());
				} else {
					break;
				}
				
				if (curNode.prev() == null) {
					break;
				}
				node = curNode;
			}
			return solution;
		}

		
		return null;
	}

	private class SearchNode implements Comparable<SearchNode> {

		final Board board;
		final int move;
		final SearchNode prev;
		final int score;
		
		public SearchNode(final Board board, final int move, final SearchNode prev) {
			this.board = board;
			this.move = move;
			this.prev = prev;
			score = move + board.manhattan();
		}

		SearchNode prev() {
			return prev;
		}
		
		public Board getBoard() {
			return board;
		}
		
		public int getMoves() {
			return move;
		}
		
		public int priority() {
			return move + board.manhattan();
		}
		
		@Override
		public int compareTo(final SearchNode arg0) {
			return priority() - arg0.priority();
		}

	}

	// solve a slider puzzle (given below)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In("8puzzle/puzzle28.txt");
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}

	}

}
