
Scenario: Some drops
Given a board 7 by 5
Then cell 3,0 is empty
When playerA drop in column 3
Then cell 3,0 is playerA
Then cell 3,1 is empty
When playerB drop in column 3
Then cell 3,1 is playerB

Scenario: Horizontal line
Given a board 7 by 5
When playerA drop in column 4
When playerA drop in column 5
When playerB drop in column 0
When playerA drop in column 2
When playerA drop in column 3
Then playerA is a winner

Scenario: Vertical line
Given a board 7 by 5
When playerB drop in column 2
When playerA drop in column 4
When playerB drop in column 2
When playerB drop in column 2
When playerB drop in column 3
When playerB drop in column 2
Then playerB is a winner
