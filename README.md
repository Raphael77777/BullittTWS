# BullittTWS
### Requirements
The set-up needed to launch the application and the required 
configurations will be published shortly by the author.

### Instructions for installation
Before we begin it is important to keep in mind that this setup has been designed to use only free data or tools that are generally available to most readers. In this way, a no-cost replication of this project setup is possible for almost anyone without prior investment.

To start with, we selected a broker, i.e. a broker to place our orders and get the price of the instruments in real time - In our search, we were careful to get a free tool, a reliable service, Rest API access but also a good documentation. At the end of the search, it was agreed that the Interactive Brokers platform met all the points that were important. - We will therefore need to download the trading workstation of this broker in order to run it on our laptop. To do this, you will find the installation executable at this link: https://www.interactivebrokers.com/en/index.php?f=16040. Once the application is installed, we need to create a demo account (Paper Trading). This usually only requires an email address to log in and get started. This will give us access to a trading environment with a large amount of money without taking any real risk as all transactions will be made with this demo account.

Once the demo account is created and connected, we will have to activate the settings to be able to communicate with the trading workstation from our software. To do this we need to go to the application > In the "File" tab on the top left > Select "Global configuration" from the drop down list > Select "API" > Select "Settings". We should now be on the settings that allow us to manage the Rest API settings. On this page we change the values to match the following figure and then apply and save the changes before exiting this window. When running our software, this application must be open on the desktop of the computer and in connected mode.

![image](https://user-images.githubusercontent.com/48348991/111172341-69288900-85ae-11eb-920d-b693f6a547c2.png)

The application we have just installed allows us to obtain a lot of information but the demonstration mode is a bit restrictive. Therefore, and to stay in a free environment, we will use another Rest API for all the technical analysis we will need later on. These technical indicators will mainly be used to determine buy and sell signals for our instruments. Alpha Vantage offers a reliable and free service that we will use for this. An API key must be created before we can start. To do this, we go to https://www.alphavantage.co/support/#api-key and create an API key for free. Once this is done, we make sure to save it in a safe place. We will not use it until later. The API key we have just obtained only allows for 5 requests per minute and 500 per day but it is sufficient for the strategy model we have chosen.

Now that we have all the third party elements back, we can start setting up our IDE (Integrated development environment). We will use IntelliJ for this project but you are free to use any IDE as long as you know the ropes. For the details, we have centralised our dependencies by creating our project with Maven and we are using Java 8. These features are popular and should easily be implemented by most readers with some Java knowledge.  Before we formally start coding, we still need to install some dependencies in our "pom.xml" file. We will need the Interactive Brokers dependency, a JSON adapter and JUnit to perform basic unit tests. 

To start, get the "TwsApi.jar" file either from http://interactivebrokers.github.io/# (Preliminary installation has to be done) or from the repository of this project (Faster and easier). We will use version 9.76 of the dependency that we took care to deposit inside our project. We will therefore update our "pom.xml" file by integrating the Interactive Brokers API but also by integrating the "json-simple" and "junit-jupiter-engine" dependencies which will be downloaded automatically. Once the file has been updated, don't forget to synchronise it so that all the changes are taken into account by our IDE. 

The initial setup is rather easy and we have done all the necessary operations for a quick start. In the following parts we will dive straight into the code and see how the components we have already seen in the architecture part work. We will use everything that has been retrieved in this part but also the implementation that has been done. Therefore, it is strongly recommended that you have done the previous steps before continuing reading. If any operations have not been performed successfully, it is possible that the system simply cannot be started.

### Instructions for use
The set-up needed to launch the application and the required 
configurations will be published shortly by the author.

### License

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
