package com.gradle.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 0, 0}
        };  // Modify the initial grid here


        String input;
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            PrintGrid(grid);
            CheckNeighbours(grid);

            input = scanner.nextLine();
            if (input.equalsIgnoreCase("q"))
            {
                System.out.println("Quitting");
                break;
            }
        }

        scanner.close();
    }

    public static void CheckNeighbours(int[][] grid)
    {
        int rows = grid.length;
        int cols = grid[0].length;

        int[][] nextGrid = new int[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (grid[i][j] == 1) // cell is alive
                {
                    if (CheckUnderpopulation(i, j, grid) || CheckOverpopulation(i, j, grid))
                        nextGrid[i][j] = 0;
                    else
                        nextGrid[i][j] = 1;
                }
                else
                {
                    if (CheckReproduction(i, j, grid))
                        nextGrid[i][j] = 1;
                    else
                        nextGrid[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < rows; i++)
        {
            System.arraycopy(nextGrid[i], 0, grid[i], 0, cols);
        }
    }

    public static int countLiveNeighbors(int i, int j, int[][] grid) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // skip the cell itself
                int ni = i + dx;
                int nj = j + dy;

                if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && grid[ni][nj] == 1) {
                    count++;
                }
            }
        }
        return count;
    }


    // Dead cell has exactly 3 live neighbours, it is resurrected
    public static boolean CheckReproduction(int i, int j, int[][] grid)
    {
        return countLiveNeighbors(i, j, grid) == 3;
    }

    public static boolean CheckOverpopulation(int i, int j, int[][] grid)
    {
        return countLiveNeighbors(i, j, grid) >= 4;
    }

    // Check if 1st rule: Underpopulation, if has less than 2 neighbours
    public static boolean CheckUnderpopulation(int i, int j, int[][] grid)
    {
        return countLiveNeighbors(i, j, grid) < 2;
    }

    public static void PrintGrid(int[][] grid)
    {
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[i].length; j++)
            {
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.print('\n');
        }

    }
}
