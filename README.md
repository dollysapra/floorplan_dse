# floorplan_dse
Design space exploration for floorplans with multiple processor-cores. The GA based algorithm in our framework returns the Pareto Set from the population upon convergence.


To start the DSE - provide set up details in the configs file located at /src/helpers/Configs.Java.  
workspacePath: The path to folder where all temp data  and floorplan files  (.flp file extension ) are saved. 
simulatorPath: The path to the simulator to be used.

Any simulator can be used to explore the design space of floorplans. The simulator is executed and results are processed in the class "DSEProblem" (function fitnessEvaluations()). 

The simulator used in the paper - _"Exploring Multi-core Systems with Lifetime Reliability and Power Consumption Trade-offs"_ is available at https://github.com/sudam41/SLICER. Please email to get access. 
