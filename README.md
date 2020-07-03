# Princeton Algorithms I on Coursera
This repository contains all the projects in the course Algorithms Part I.


## Percolation
Percolation was intended to give the student practice with the quick union data structure while demonstrating the practical potency of a good algorithm. 

I implemented two weighted quick unions and a 2D grid to create the percolation data structure, then modified the information from the 2D array for the quick union data structure to ensure sites were properly opened (and connected to the virtual top and bottom to effectively test percolation). Then I implemented a client to run n tests with the Percolation type and find averages, standard deviations, and other useful data.


## Stacks and Queues
This project involved three sections designed to extend understanding of the initial abstract data types taught in the course--stacks and queues. 

First I constructed a deque, a queue that can be pushed, popped, queued, and unqueued (so added and removed from both sides). I utilized a bidirectional linked list to implement the functionality.

The second part of the project was a randomized queue and client interface. The randomized queue was was a FIRO, or first in random out. I used a resizing array implemented like with a queue with the extra random functionality via swapping the random element with what a normal queue would remove, the "last" element.


## Collinear Points
In the hardest project in the course, I worked with a modified mergesort to improve the brute force version of ascertaining collinearity amongst a set of data points. By sorting the array first by points then by slope, because mergesort is stable, I was able to effectively find collinear points of any number (by finding points with the same slope) and adding a line with the min and max to a resizing stack.


## 8 Puzzle
In this project, I used a modified priority queue to solve a classic game, the 8 puzzle. After making a board data structure capable of calculating two types of priority for comparison, I created a solver that continually added possible solutions (four for every path) to a minimum priority queue that determined priority with the previous priority functions, manhattan in this case. By popping the minimum and adding its next moves, eventually either the board or its twin would be solved, returning the solution or null respectively.

## Kd-Tree
In the final project, after implementing the brute force method of finding all points in a 2D range with a preconstructed binary search tree, I created my own binary search tree structured as a 2D-tree to segment the data and make range and nearest neighbor functionality very rapid. Essentially, the BST alternated between sorting by x and y value, storing the rectangle range the subtree contained. When finding points in a range, it would check if a subtree intersected the range rectangle before following it, pruning the possible paths to make a more efficient algorithm.
