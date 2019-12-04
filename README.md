This is the java implementation of the spea2 algorithm, which contains the algorithm and an exercise

I simulated running on an Android phone, this is his effect

![x15fk ekoig](https://s2.ax1x.com/2019/12/05/Q1z7HU.gif)



You can set a listener for Spea2, and he will pass the results of each time into this listener

# how to use

1. Inherit the Problem to implement the abstract method Calculation, the meaning of the parameters：

   UpperLimit：Maximum value range

   LowerLimit：Minimum value range

   NbVariablesDecision：Number of decision variables

2. Instantiate the Problem just written

3. Instantiate GAParameters, the meaning of the parameters:

   PopSize：Population size

   ArchiveSize：Elite population size

   Gmax：Maximum number of inheritances

   Pc：Cross probability

   Pm：Mutation probability

4. Instantiate Spea2，afferent the Problem and gaParameters

5. Call Spea2.getResult () to calculate the result