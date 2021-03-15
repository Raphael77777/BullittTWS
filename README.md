# BullittTWS

### Requirements
The project requires the following dependencies: 
 - Java 8
 - Maven

### Instructions for installation
Before we begin it is important to keep in mind that this setup has been designed to use only free data or tools that are generally available to most readers. In this way, a no-cost replication of this project setup is possible for almost anyone without prior investment.

To start with, we selected a broker, i.e. a broker to place our orders and get the price of the instruments in real time - In our search, we were careful to get a free tool, a reliable service, Rest API access but also a good documentation. At the end of the search, it was agreed that the Interactive Brokers platform met all the points that were important. - We will therefore need to download the trading workstation of this broker in order to run it on our laptop. To do this, you will find the installation executable at this link: https://www.interactivebrokers.com/en/index.php?f=16040. Once the application is installed, we need to create a demo account (Paper Trading). This usually only requires an email address to log in and get started. This will give us access to a trading environment with a large amount of money without taking any real risk as all transactions will be made with this demo account.

Once the demo account is created and connected, we will have to activate the settings to be able to communicate with the trading workstation from our software. To do this we need to go to the application > In the "File" tab on the top left > Select "Global configuration" from the drop down list > Select "API" > Select "Settings". We should now be on the settings that allow us to manage the Rest API settings. On this page we change the values to match the following figure and then apply and save the changes before exiting this window. When running our software, this application must be open on the desktop of the computer and in connected mode.

<img width="718" alt="settingsIB" src="https://user-images.githubusercontent.com/48348991/111173397-49de2b80-85af-11eb-9fe4-1e69541070bc.PNG">

The application we have just installed allows us to obtain a lot of information but the demonstration mode is a bit restrictive. Therefore, and to stay in a free environment, we will use another Rest API for all the technical analysis we will need later on. These technical indicators will mainly be used to determine buy and sell signals for our instruments. Alpha Vantage offers a reliable and free service that we will use for this. An API key must be created before we can start. To do this, we go to https://www.alphavantage.co/support/#api-key and create an API key for free. Once this is done, we make sure to save it in a safe place. We will not use it until later. The API key we have just obtained only allows for 5 requests per minute and 500 per day but it is sufficient for the strategy model we have chosen.

Now that we have all the third party elements back, we can start setting up our IDE (Integrated development environment). We will use IntelliJ for this project but you are free to use any IDE as long as you know the ropes. For the details, we have centralised our dependencies by creating our project with Maven and we are using Java 8. These features are popular and should easily be implemented by most readers with some Java knowledge.  Before we formally start coding, we still need to install some dependencies in our "pom.xml" file. We will need the Interactive Brokers dependency, a JSON adapter and JUnit to perform basic unit tests. 

To start, get the "TwsApi.jar" file either from http://interactivebrokers.github.io/# (Preliminary installation has to be done) or from the repository of this project (Faster and easier). We will use version 9.76 of the dependency that we took care to deposit inside our project. We will therefore update our "pom.xml" file by integrating the Interactive Brokers API but also by integrating the "json-simple" and "junit-jupiter-engine" dependencies which will be downloaded automatically. Once the file has been updated, don't forget to synchronise it so that all the changes are taken into account by our IDE. 

The initial setup is rather easy and we have done all the necessary operations for a quick start. In the following parts we will dive straight into the code and see how the components we have already seen in the architecture part work. We will use everything that has been retrieved in this part but also the implementation that has been done. Therefore, it is strongly recommended that you have done the previous steps before continuing reading. If any operations have not been performed successfully, it is possible that the system simply cannot be started.


### Instructions for use
For this we will need all the elements we have studied so far. It is therefore imperative that you have followed the previous sections to understand what we are going to do in this final stage. To do this, it is necessary to have the source code of the software or the executable. It is possible to copy the source code directly from the official GitHub repository or to find the corresponding JAR file also available in the same repository. We will assume that everyone who now wants to run the software on their own machines has the executable or is able to compile the source code to start the program.

Before we start our actual software, we will start the trading workstation and log in. This will be necessary to make the connections at the start of the software and then get all the data about our account and the assets we will be monitoring. We have already discussed all the steps to be taken in the initial setup section, so we will simply repeat them once again. If we have followed the instructions, we should have a screen that looks like the following figure.

![1](https://user-images.githubusercontent.com/48348991/111172877-de945980-85ae-11eb-805a-5beb02e53a66.PNG)

Now we can start our software. The reader should also be able to do this thanks to all the information we have given so far. The following figure represents the welcome screen and means that the program is ready.

![2](https://user-images.githubusercontent.com/48348991/111172905-e48a3a80-85ae-11eb-8df0-f87be834474e.PNG)

Clicking on the "HOME" button takes us to the home page of this software. Here we see the list of steps that are necessary for start-up and monitoring, as well as some basic data that will be useful for monitoring the evolution of the algorithm once it is running. The following figure shows the home screen we are talking about.

![2A](https://user-images.githubusercontent.com/48348991/111172939-ea801b80-85ae-11eb-87dc-5f5db496ac64.PNG)

Before officially starting the algorithm, we will check and adjust the parameters. To do this, we go to the "SETTINGS" tab. Here we can change the instrument we want to monitor, but also the precision, the timescale, the multiplier, the take profit and stop loss limits and the type of order used. If we have not correctly understood what these values correspond to, which have been previously detailed in this document, it is not recommended to continue but rather to go back and read them. It is very dangerous to start a trading algorithm without knowing exactly what we are doing. Once all the changes have been made, do not forget to save the values by pressing the "GO" button. The following figure shows the interface for entering this data.

![3](https://user-images.githubusercontent.com/48348991/111172954-ef44cf80-85ae-11eb-8fa7-3134c7dd2fe5.PNG)

To finish, we are going to carry out a last small adjustment. We have in our possession an API key that we took care to get at the beginning of the development part, it is now time to use it. To do this, we can stay on the "SETTINGS" page. Next to the strategy we have a white information button. If we click on this button, the system will ask us to enter the API key we have. When entering the key, we should remember to exclude any spaces that may be in front of or behind the string. The following figure shows the input field for the API key.

![4](https://user-images.githubusercontent.com/48348991/111172998-f8ce3780-85ae-11eb-99c7-eb170da6fc11.PNG)

We are ready for take-off! Let's buckle up and go to the "ENGINE" tab. This is where we will start the algorithm. We check that the data on this screen is the data we want to start the algorithm with and that the API key is active. If this is not the case or if we want to change any information, we can simply go back and make new changes. In our scenario, we will start the algorithm with the data we see in the following figure.

![5](https://user-images.githubusercontent.com/48348991/111173031-fff54580-85ae-11eb-9b17-ebadff0d8601.PNG)

3, 2, 1, Let's go! To start, we click once on the "START" button. This button will change from red to green to indicate its status and an immediate stop button will appear in the top left corner of our application. This button is specifically designed to stop the system immediately if needed. In the following figure, you can see the two changes that occurred after the start-up.

![6](https://user-images.githubusercontent.com/48348991/111173061-0683bd00-85af-11eb-97be-b2960c281e40.PNG)

Now that everything has been launched we enter the monitoring phase. To do this, we move to the "MONITOR" tab. On this new screen we see values. These values are updated by our software to reflect the actual value of the portfolio or algorithm. If we have reached this point, it is likely that no further explanation is necessary, as the field names are self-explanatory. However, a small clarification is required at this point. The values for prices are calculated according to the price at the start of the algorithm and not since the beginning of the opening of the markets. The following figure shows the data we have obtained since we started.

![7](https://user-images.githubusercontent.com/48348991/111173083-0be10780-85af-11eb-86de-98eb47e8a9cc.PNG)

We have seen in the previous figure that an order was executed by our system. We will now switch to our trading workstation to see how the transaction went. In the following figure, you will see the 3 orders that have been placed by our software but are still pending because the limit price has not yet been reached.

![8](https://user-images.githubusercontent.com/48348991/111173120-126f7f00-85af-11eb-8ed6-3844d24682d1.PNG)

Finally, our order was executed a few moments later. (We deliberately changed the limit to illustrate this tutorial, otherwise we would have had to wait for the order to trigger itself based on the price, but this might never have happened). If we look at the status of our account again, we will see that 1 of the 3 orders transmitted has been executed. The other two orders are pending as they are exit orders that will be triggered depending on the market evolution. The following figure illustrates what has just been explained.

![9](https://user-images.githubusercontent.com/48348991/111173149-169b9c80-85af-11eb-9c21-1c72184dcfa1.PNG)

Everything seems to be going well, so now it's time to see if our software also provides us with the relevant information. So we go to the "HISTORY" tab. Here we can see that we have all the information corresponding to the transaction we have just carried out. The following figure confirms this.

![10](https://user-images.githubusercontent.com/48348991/111173173-1bf8e700-85af-11eb-8cc2-ec88fed14aa3.PNG)

To get information about the state of our account, we can easily go to the tab "ACCOUNTS" which will give us useful data. On this screen we find the amount of funds available, the total value of our account, our buying power and the necessary margin that is blocked on the account. Other information on the evolution of the account is also available in the right column. All this information comes from our broker and is updated automatically in case of changes. The following figure shows the details of these values.

![11](https://user-images.githubusercontent.com/48348991/111173208-21eec800-85af-11eb-910b-cd8c7b2ff98c.PNG)

There is one last screen that we have not yet explored together, the "POSITIONS" screen. We go there straight away. Here we have various information that corresponds to the instrument we are currently monitoring. The volume, average unit cost, value, type as well as the instrument-specific development data. As before, these values are continuously updated when changes occur. The following figure shows this information.

![12](https://user-images.githubusercontent.com/48348991/111173231-27e4a900-85af-11eb-8bed-97653320dc47.PNG)

We talked about the home screen at the very beginning and mentioned that it also had some basic information. Now that our software has been launched, we can go back and check that this is also updated. This data includes the number of buys, sells, difference, exposure in the monitored currency and the direction of our exposure. We can see from the following figure that the data is up to date and that it corresponds to the transactions that have been made.

![13](https://user-images.githubusercontent.com/48348991/111173251-2b783000-85af-11eb-9b3e-c22dcd70c7ae.PNG)

Knowing how to start is good. Knowing how to stop is better. Just like in the casino, it is necessary to know how to take your losses or your winnings before you end up plucked. To stop the software, we have seen a big red button in one of the corners of each screen since the start-up. It is on this button that it will be necessary to click to finish the process. When stopping our software will automatically close all open positions and delete any orders that have not been executed so far, especially stop loss or take profit orders. Once the red button is clicked, it disappears and the status in the "ENGINE" tab becomes red again as shown in the following figure.

![14](https://user-images.githubusercontent.com/48348991/111173281-3337d480-85af-11eb-983e-1952cd6c1d2b.PNG)

Before ending this chapter, we will check that our software has reset the positions and orders to zero with our broker. To do this, we will switch to the trading workstation one last time. We will see in the following figure that there are no more active orders and that all positions are at 0.

![15](https://user-images.githubusercontent.com/48348991/111173312-37fc8880-85af-11eb-9280-74468ec7386b.PNG)

Once the algorithm is stopped, all screens are not automatically reset. The data is still visible for analysis or manual export to a trading book. Only after the algorithm is restarted will the values be reset. The user is free to start the algorithm as many times as he wants with different instruments each time if he wishes.

If the application is closed and then reopened, there is no need to redefine the strategy or API key, all these values are stored locally on the user's PC to allow a smooth user experience. If these files are deleted or the user runs the application on a new computer, the data will have to be re-entered before the algorithm can be run.

The use of this software is, by virtue of its MIT licence, free. However, we would like to remind you that this software has been developed for educational purposes and that it is strongly advised not to use a real account if the user does not have the necessary knowledge of the stock markets or the programming language used. We decline in advance any responsibility as for the use which could be made of this software.


### License [MIT]

Copyright (c) 2021 Raphaël Darbellay

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
