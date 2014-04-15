package com.trex.app.vendor.screens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BredthFirstGrid {

    /**
     * Input.txt
     N ( Integer for number of Test )
     R S 0000S 0W000 00W00 00000 0000E
     Example Input File.
     1
     5 3 00S 00W 000 W00 E0W
     */

    private static String FILE_NAME = "<INPUT FILE No of such grids, "
            + "followed by string, char W is used for blocks, S for start and E for end>";

    private static List<Position> getNeighour(Position p, int row, int col) {
        List<Position> neighours = new ArrayList<Position>();

        Position posLeft = p.getLeft();
        if (posLeft.row >= 0
                && posLeft.row < row
                && posLeft.col >= 0
                && posLeft.col < col)
            neighours.add(posLeft);
        Position posRight = p.getRight();
        if (posRight.row >= 0
                && posRight.row < row
                && posRight.col >= 0
                && posRight.col < col)
            neighours.add(posRight);
        Position posUp = p.getUp();
        if (posUp.row >= 0
                && posUp.row < row
                && posUp.col >= 0
                && posUp.col < col)
            neighours.add(posUp);
        Position posDown = p.getBottom();
        if (posDown.row >= 0
                && posDown.row < row
                && posDown.col >= 0
                && posDown.col < col)
            neighours.add(posDown);

        return neighours;

    }

    public static List<String> getPath(char[][] arr, int row, int col) {
        final int[][] grid = new int[row][col];
//        for(int i=0;i<arr.length;i++) {
//        	for(int j=0;j<arr[i].length;j++) {
//        		System.out.print(arr[i][j]+" ");
//        	}
//        	System.out.println();
//        }
        PriorityQueue<Position> queue = new PriorityQueue<Position>(col * row,
                new Comparator<Position>() {

                    @Override
                    public int compare(Position o1, Position o2) {
                        if (grid[o1.row][o1.col] < grid[o2.row][o2.col])
                            return -1;
                        else if (grid[o1.row][o1.col] > grid[o2.row][o2.col])
                            return 1;
                        else
                            return 0;
                    }
                });
        for(int i=0;i<arr.length;i++) {
            for (int c = 0; c < arr[0].length; c++) {
                if (arr[i][c] == 'S') {
                    queue.offer(new Position(i, c));
                    grid[i][c] = 0;
                }
            }
        }
        // System.out.println("Queue "+queue);
        while (!queue.isEmpty()) {

            Position current = queue.poll();
            List<Position> neighours = getNeighour(current, row, col);
            //  System.out.println("current "+current+"row "+row+"col "+col);
            //  System.out.println("Neighbours "+neighours);
            for (Position neighour : neighours) {

                if (!(arr[neighour.row][neighour.col] == 'W')
                        && grid[neighour.row][neighour.col] == 0) {

                    grid[neighour.row][neighour.col] = grid[current.row][current.col] + 1;
                    queue.offer(neighour);
                }

                if (arr[neighour.row][neighour.col] == 'E') {
                    //     	System.out.println("End Reached");
                    int value = grid[current.row][current.col];
                    //	System.out.println("End "+neighour);
                    List<Position> neighbours = getNeighour(neighour,row, col);
                    List<String> output = new ArrayList<String>();
                    while(value!=0) {
                        neighbours = getNeighour(neighour,row, col);
                        for(Position pos:neighbours) {
                            if(grid[pos.row][pos.col]==value) {
                                neighour = pos;
                                value--;
                                output.add(pos.toString());
                                //	System.out.println(sb.toString());
                                break;
                            }
                        }
                    }
                	/* for(int i=0;i<grid.length;i++) {
                     	int [] internal = grid[i];
                     	for(int j=0;j<internal.length;j++) {
                     		System.out.print(" "+internal[j]);
                     	}
                     	System.out.println();
                     } */
                    //   	System.out.println("output "+output);
                    return output;
                    // return grid[current.row][current.col] + 1;
                }
            }
        }

        return new ArrayList<String>();
    }

    public static void main(String[] args) throws Exception{
        FILE_NAME = "input.txt";
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        char [][] input = new char[64][];
        int j=0;
        while((line=br.readLine())!=null) {
            String [] val = line.split(" ");
            char [] arr1 = new char[32];
            for(int i=0;i<val.length;i++) {
                arr1[i] = val[i].charAt(0);
            }
            input[j]=arr1;
            j++;
        }
        System.out.println(getPath(input, 64, 32));
    }

    public static List<String> convert(int input[][], int startx, int starty, int endx, int endy, int row, int col) {
        char [][] output = new char[input.length][];
        for(int i=0;i<input.length;i++) {
            output[i] = new char[col];
        }
        for(int i=0;i<input.length;i++) {
            int [] internal = input[i];
            for(int j=0;j<internal.length;j++) {
                if(i==startx && j==starty) {
                    output[i][j] = 'S';
                    //	sb.append("S");
                }
                else if(i==endx && j==endy) {
                    output[i][j] = 'E';
                    //sb.append("E");
                }
                else if(input[i][j]==0) {
                    output[i][j] = 'W';
                } else if(input[i][j] == 1){
                    output[i][j] = '0';
                }
            }
        }
        return getPath(output, row, col);
        //return output;
    }

    @SuppressWarnings("unused")
    private static void test(char[][] arr, int row, int col) {
        // test
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j)
                System.out.print(arr[i][j]);
            System.out.println();
        }
    }

    @SuppressWarnings("unused")
    private static void test(int[][] arr, int row, int col) {
        // test
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j)
                System.out.print(arr[i][j]);
            System.out.println();
        }
    }
}

class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Position getLeft() {
        return new Position(row, col - 1);
    }

    public Position getRight() {
        return new Position(row, col + 1);
    }

    public Position getBottom() {
        return new Position(row + 1, col);
    }

    public Position getUp() {
        return new Position(row - 1, col);
    }

    public String toString() {
        return row + "," + col;
    }
}