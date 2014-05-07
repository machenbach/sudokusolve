package org.mike.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;

/*
 * The main class.  Get a puzzle, try to solve it
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] oneToNine = {0, 1, 2, 3, 4, 5, 6, 7, 8 };
		
		Puzzle puzzle = new Puzzle();
		Solver solver = new Solver(puzzle);

		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(System.getProperty("user.dir"));
		System.out.print("Enter the board file: ");
		String board = "";
		try {
			board = br.readLine();
			puzzle.readBoard(new FileReader(board));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int step = 0;
		
		while (true) {
			System.out.println("Step " + step);
			puzzle.printBoard();
			if (puzzle.isSolved()) {
				System.out.println("Puzzle has been solved");
				return;
			}
			solver.step();
			for (int row : oneToNine) {
				for (int col : oneToNine) {
					if (solver.hasAnswer(row, col)) {
						System.out.println(row + ", " + col + ": " + solver.getAnswer(row, col));
					}
				}
			}
			if (!solver.madeProgress()) {
				System.out.println("Solver made no progress.  Save to (d to print debug): ");
				PrintWriter pr = null;
				try {
					String fName = br.readLine();
					if (fName.length() == 0) return;
					if (fName.equalsIgnoreCase("d")) {
						solver.printSolverInfo();
					}
					pr = new PrintWriter(new FileWriter(fName));
					pr.println(puzzle.writeBoard());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					if (pr != null) pr.close();
				}
				return;
			}
			solver.fillAnswers();
			step++;
			System.out.println("Step");
			try {
				if (br.readLine().equalsIgnoreCase("d")) {
					solver.printSolverInfo();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		

	}

}
