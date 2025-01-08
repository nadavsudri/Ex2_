This is my solution to Ex2 from cs101 as represented in https://docs.google.com/document/d/1-18T-dj00apE4k1qmpXGOaqttxLn-Kwi/edit

the solution is a digital spreadsheet capable of simple calculations such as [+,-,\,*]

the solution is based on formulas - ex: "=1+2*(3+4)".

and also capable of milty cell calculations - ex: "= 2*A2-(4+A3)".

when an invalid data is enterd an error is shown:

1) invalid formula - ERR_Form
2) cerciular ref - ERR_cycle_form
my solution calculates values of Refrenced cells by using depth property for each cell.

each dependent cell's printed value is updated when its subcells are updated.

the Solution's UI only shows [A-I] and [1-16] but tested when spreadsheet size in [A-Z] - [0-99]

feel free to test the limits of my solution.

if needed https://github.com/nadavsudri/Ex2.git -------> First Part Only!

<img src="https://raw.githubusercontent.com/nadavsudri/Ex2_/refs/heads/master/for%20readme.webp" alt="Description of the Image" width="300">




