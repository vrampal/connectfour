Scenario: Horizontal line
Given a board 7 by 5
When playerA drop in column 4
When playerA drop in column 5
When playerA drop in column 2
When playerA drop in column 3
Then playerA is a winner
