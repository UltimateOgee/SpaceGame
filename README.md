# SpaceGame

This repository will be used for all of the remaining HW assignments for CS1321 this semester. But don't worry! Git is very good at tracking your changes, letting you fix mistakes, and being a "safety net" for your code.

To initially set up your project you should perform the following steps:

1. Clone the created repository to your local machine.
  * To to this, issue a command similar to the following:
  ```git clone https://github.com/CSCI1321/SpaceGame-USERNAME.git```
  * This should create a folder on your machine named SpaceGame-USERNAME that contains this README.md file, and a src folder that contains the starter code for this project.
  
2. Create an Eclipse project within the folder just created.
  * To do this, open Eclipse, right click in the left panel and choose "New/Scala Project"
  * In the "New Scala Project" window, **_de-select_** the checkbox next to "Use default location"
  * Click on the "Browse..." button next to "Location" and in the pop-up window select the SpaceGame-USERNAME folder
  * This should automatically change the "Project name" field to become SpaceGame-USERNAME
  * Click on the "Finish" button to complete creating the project
  
3. Configure the newly created project to use Scala and ScalaFX.
  * Right click on the project and select the "Properties" option from the context menu
  * In the pop-up window, select "Java Build Path" from the list on the left, and go to the "Libraries" tab in the right panel
  * You should see an entry in the list that says something like "JRE System Library [Java ..."
  * If you do NOT see "Scala library container..." in this list, then click on the "Add Library..." button, select "Scala library" from the list, click "Next >", choose the most recent version of Scala listed, and then click on "Finish"
  * Add the ScalaFX library by clicking on the "Add External JARs..." button, navigate to the appropriate scalafx.jar file (on the lab machines, you can use the one at /users/mhibbs/scalafx.jar), and then click on "Open"
  
At this point, your project should be set up and configured correctly. From here, you can edit the files in the src folder to contain your solution to the homework assignment. You should regularly save your work and commit/push it to the repositories. To do this, you can use the standard git commands from within your SpaceGame-USERNAME folder. For example:
```
git status
git add -A
git commit -m "PUT_YOUR_COMMIT_MESSAGE_HERE"
git push
```

At the completion of each HW assignment, you will create a **_branch_** containing your submission to the particular homework assignment. The names of the branches that you will create are HW1, HW2, HW3, and HW4. It is important that you do not make further changes to your submissions within these branches. Instead you should continue to edit your master branch. We will discuss how to do this in class, but in general the process of creating a branch will be:
```
git branch HW1 #To create the branch locally
git push --set-upstream origin HW1 #To push the branch into GitHub
git checkout master #To go back to using the master branch
```



