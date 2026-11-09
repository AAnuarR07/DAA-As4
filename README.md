
# Assignment 4. Patient Zero

Student ID: 252035  
Name: Anuar  
Group: SE-2510

## Program output

The output of `java -cp out Main`:

    Mutations: 66
    Shared core: 1187
    Marker hits: 6
    First hit: 10520
    Border length: 4
    Shared words: 635
    Tests needed: 31
    Cheapest testing: 160
    Longest chain: 27

## My outbreak report

Screenshot:
![outbreak](output/outbreak.png)

## Questions

### Mission 1

The cell d[i][j] represents the minimum number of operations needed to transform the first i characters of string A into the first j characters of string B  
It is calculated using three possible operations: insertion, deletion and replacement. If the characters are equal, we take the diagonal value without adding anything  

### Mission 2

A subsequence does not need to have consecutive characters, while a substring must have consecutive characters  
The Shared core can be larger than the number of characters at the same positions because LCS searches for common characters in the same order, even if their positions are different  

### Mission 3

The prefix function stores the length of the longest proper prefix that is also a suffix for every prefix of the pattern  
KMP uses this information to avoid checking characters again. The text pointer never goes backwards because already processed characters do not need to be checked again  
The Border length of my marker is 4  

### Mission 4

We must compare the actual characters after the hashes are equal because different strings can have the same hash  
If many hashes are equal, more character comparisons are needed. This can make Rabin-Karp slower  

### Mission 5

Each person has two states: the minimum cost when the person is tested and the minimum cost when the person is not tested  
If a person is not tested, all their children must be tested. If the person is tested, we can choose the cheaper state for each child  
A greedy approach does not always work because testing the person with the most connections does not necessarily give the minimum total cost  

## AI use

ChatGPT helped with understanding of the themes needed in this project - their idea, meaning of some parts, as well as the basic implementation and it's meaning. It also hepled understand what was needed from me in the project

## Timing (Mission 7)

Milliseconds, average of 3 runs

| Test | Size | Time |
|---|---|---|
| Naive search | 20 000 | 46.2 ms |
| Naive search | 40 000 | 83.9 ms |
| Naive search | 80 000 | 165.3 ms |
| KMP | 20 000 | 2.3 ms |
| KMP | 40 000 | 0.7 ms |
| KMP | 80 000 | 0.3 ms |
| Edit distance | 500 | 5.1 ms |
| Edit distance | 1000 | 7.2 ms |
| Edit distance | 2000 | 19.5 ms |
| Edit distance | 4000 | 79.4 ms |

When the input size doubles, the edit distance time grows approximately four times because it has O(n*m) complexity. KMP grows approximately linearly with the input size  
Naive search can become slower when many characters match before a mismatch  

## Complexity

| Algorithm | Time | Space |
|---|---|---|
| Edit distance | O(n*m) | O(n*m) |
| LCS | O(n*m) | O(n*m) |
| Prefix function | O(m) | O(m) |
| KMP | O(n+m) | O(m+z) |
| Rabin-Karp | O(n+m) expected | O(m) |
| Tree DP | O(v) | O(v) |

Where:
- n = length of the text or first strain
- m = length of the pattern or second strain
- z = number of matches
- v = number of people

## Before submission

- [ ] All 9 output lines are correct.
- [ ] `java tools/Check.java` passes all examples
- [ ] Screenshot `output/outbreak.png` is included
- [ ] Timing results are checked on library computer
- [ ] README is completed
- [ ] All changes are committed and pushed to GitHub