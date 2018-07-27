# FOREX Market Simulator

![main_page](https://user-images.githubusercontent.com/36813986/117592326-0e378c00-b130-11eb-9b34-61f51070069f.png)

**FOREX Market Simulator** is a web application made with [Spring-boot 2](https://spring.io/projects/spring-boot)
and [Angular 6](https://angular.io/) to simulate the [Forex](https://www.forex.com/en/)  Market exchange and trading operations
to practice and understand the work of such platforms safely with virtual money, and no risk involved.

It also has a trading bot that utilizes machine learning to decide on whether to open a short or long position (Buy/Sell)
by predicting the target Currency Pair trend (Up/Down).

So until AI takes over the world, _Trade Safely_ :heavy_dollar_sign:
### Features

* Login / Registration 

* Account management

* Provide fundamental understanding of Trading concepts

* Support for three types of trade orders: market, limit and stop orders

* Allow Trading using virtual balance by opening short and long positions (Buy/Sell)

* Includes a trading bot that uses Machine Learning to trade on **FMS**

# Getting Started

## Pre-requisites

For building and running the application you need:

* [Mysql 5.7](https://dev.mysql.com/downloads/windows/installer/5.7.html) (or [Docker](https://docs.docker.com/get-docker/))
* Backend:
    - [JDK 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
    - [Maven 3.6](https://maven.apache.org/download.cgi)

* Frontend:
    - [Node.js](https://nodejs.org/en/download/)
    - [npm](https://www.npmjs.com/get-npm) (comes with Node.js)


## Running the application locally

This project has three components that need to be up and running:

### Database
For this step we are going to have the database up and running and restore the `data/dump.sql`,
you have two options:

- If you have Mysql installed locally then just run:
        
        $ mysql -u<MYSQL_USER> -p<MYSQL_PASSWORD> < data/dump.sql
    
    change the `MYSQL_USER` and `MYSQL_PASSWORD` depending on your local configuration.
    
- **Otherwise**, if you want to use **docker**, you need to first set up the database credentials by updating
    the `config/db-cred.cnf` file that consists of the following:   
    
    ---    
    - `MYSQL_ROOT_PASSWORD`= The password for the root account.
    - `MYSQL_DATABASE`= The name of the database.
    - `MYSQL_USER`= The name of the database user to connect with. (We can connect with the root but its not advised)
    - `MYSQL_PASSWORD`= The password of the user to connect to the MySQL database.
    ---
       
    Then run this command to get an instance of MySQL up and running:
    
        $  docker run -d --name mysql-db  --env-file=config/db-cred.cnf -p 3306:3306  mysql:5.7.34
        
    then load the data using this command:
    
        $ docker exec -i mysql-db sh -c 'exec mysql -u"$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DATABASE"' < data/dump.sql

### Backend
First `cd` into the `FMS-backend` directory, then update the file `config/env.sh` with the right database credentials and host.

**Note:** If you chose to work with **docker**, and you're using docker machine or toolbox, then the `MYSQL_HOST` should be set
to your `docker-machine ip`, otherwise it is your `localhost`.
             
After updating `config/env.sh` run this command so that the backend can access the DB credentials and host from the
environment variables.
      
    $ source config/env.sh


There are several ways to run a Spring Boot application on your local machine. we're going to use the
[Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

    $ mvn spring-boot:run

**Note:** if you don't have `mvn` installed on your machine you can instead use `mvnw` and it will install it for you.

### Frontend
First `cd` into the `FMS-frontend` directory.

The frontend for **FMS** was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.0.8.

To get it up and running, you need to first install the dependencies using `npm`:

    $ npm install

then simply serve it using:

    $ npm start

Now you should be able to access **FMS** at http://localhost:4200/

# Usage
* The home page of **FMS** should be a summary of the changes in currencies in the day.
You will find an option to login or register for a new account in both the home page, and the navigation bar.

    ![main_page](https://user-images.githubusercontent.com/36813986/117592326-0e378c00-b130-11eb-9b34-61f51070069f.png)


## Login and Registration

* New users need to register for an account to start trading on the application, for that click on the sign up on the navigation bar

    ![sign_up_empty](https://user-images.githubusercontent.com/36813986/117592338-11cb1300-b130-11eb-9069-c1439602c506.png)

* After registering the user will be redirected to the login page:    

    ![login_page](https://user-images.githubusercontent.com/36813986/117592324-0e378c00-b130-11eb-9a8e-55035b4827cb.png)

* Upon the login, the user will be greeted by his profile page that has his account information and his virtual wallet status.

    The **Wallet** is composed of 5 key attributes that their understanding is crucial for trading:
    
    - **Balance**: Shows the funds in the account, including the profit and loss from closed trades.
    - **Equity**: It is a dynamic value and takes into consideration the profit/loss from open trades at the time.
    - **Margin**: It  is the total amount of money that’s currently _"locked up_" to maintain all open positions.
    - **Free Margin**: It is the amount available in the account to open NEW positions.
    - **Profit**: It is the amount of money thus user is making from his open positions, it can be negative (called loss).
    
    Every new user will have access to “1,000,000 USD” and a leverage of 1:2 by default which he can use to open positions
    as will be explained [here](#start-trading)
       
    ![profile_page](https://user-images.githubusercontent.com/36813986/117701201-dd526800-b1be-11eb-8493-c07f142d0af4.png)

* At the profile page the user can click on the edit button to change any of his account information by providing the password:    

    ![edit_account](https://user-images.githubusercontent.com/36813986/117592320-0c6dc880-b130-11eb-8c78-74d6c9956908.png)

* The user will be also able to delete his account just by clicking on the delete button and confirming the pop up:
    
    ![delete_account](https://user-images.githubusercontent.com/36813986/117701524-3fab6880-b1bf-11eb-93f2-e33ee65c8de1.png)
    
## Start Trading

* To start Trading on **FMS**, the user can either click the trade button in his profile page or through the navigation
bar to access the Trading page where he can open positions in the Currency Pair of his choosing.
Opening a position means that the user either buying a specific amount of the  base currency if it's a short position (SELL),
or a specific amount of the quote currency if it’s a long position (BUY).
 
    ![trading_page](https://user-images.githubusercontent.com/36813986/117703693-dc6f0580-b1c1-11eb-8315-69483e928870.png)

* The user can __Buy__ a currency pair simply by clicking it and scrolling down to the order section and selecting the type of the 
order he wishes to pass, and then enters the details. If his balance allows the transaction then a success pop up will appear in 
the top of the page informing him that his position was opened:
    
    ![buy_trade](https://user-images.githubusercontent.com/36813986/117703781-fad50100-b1c1-11eb-9c61-30cd2e63a910.png)
    
* The user can also find the option to __Sell__(open a long position in) a currency pair in the order section:
    
    ![sell_trade](https://user-images.githubusercontent.com/36813986/117703852-150edf00-b1c2-11eb-93fb-66d59b2238ee.png)
    
* If for any reason the order isn't approved by the server such as the amount of the order is superior to the user's
**Free Margin** then a rejection pop up will appear in the top of the page informing him that the order was rejected
and the reason for it:
    
    ![forbiden_trade](https://user-images.githubusercontent.com/36813986/117703944-2821af00-b1c2-11eb-8a9c-913645359de5.png)

## Open Positions

* After opening a position, the user __Profit__ will start to change according to the Currency Pair quote changing.
    
    So firstly the value of 1 pip of that position is calculated in USD (the base account currency),
    and then upon every 1 pip change in the Currency Pair quote the __Profit__ will change by the **amount** of the position
    times the 1 pip value change,
    and if the Profit is changing, the Equity and the Free Margin will as well, since:
    
    _**Equity** = Balance + Profit_  
    
    and 
    
    _**Free Margin** = Equity – Margin_
    
    The **Free Margin** is basically the volume u can still use to open new positions.
    The **Margin** is the cost to open the position in USD and since we have a 1:2 leverage it is half of the volume to
    open the position in USD.
     
    The user can simply view the open positions by clicking the Positions button on his profile or using the Nav bar
    and can then see the details about every position as well as the profit it is making.

    ![current_positions](https://user-images.githubusercontent.com/36813986/117712554-63c17680-b1cc-11eb-9136-69673875b25f.png)
    
* The user can visualize the changes to his wallet status that are due to his open positions'
currencies pairs quote changing when other users open positions by navigating to his profile.
Simply click the user icon and choose Profile:  
    
    ![go_to_profile](https://user-images.githubusercontent.com/36813986/117724475-b6565f00-b1db-11eb-8701-9bed27ba3778.png)

* At the user profile, we can see the changes to the Equity, Margin, Free Margin, and Profit bacaue of the open position,
we can also see that the Balance didn't change yet since the user didn't close any position yet:

    ![current_wallet](https://user-images.githubusercontent.com/36813986/117712549-6328e000-b1cc-11eb-8b54-c5bf49460ca8.png)
    
* The user can open more positions and keep track of them:
    
    ![more_open_positions](https://user-images.githubusercontent.com/36813986/117704161-6fa83b00-b1c2-11eb-9c3f-731c092d8c70.png)
    
* And we can see the new changes to the Wallet brought by the new open positions:

    ![more_open_positions_wallet](https://user-images.githubusercontent.com/36813986/117712829-bbf87880-b1cc-11eb-9475-309e1f878578.png)

## Close Positions

* The user can chose to close a position simply by pressing the close button in the open positions page:
  
    ![close_position_button](https://user-images.githubusercontent.com/36813986/117725217-bdca3800-b1dc-11eb-9dc3-d7253f15cefc.png)
       
   ![closed_current_positions](https://user-images.githubusercontent.com/36813986/117713504-a9cb0a00-b1cd-11eb-9d2b-5fa5a8ef7b43.png)
    
* After closing a position the *Profit*, *Equity*, *balance*, *Margin* and *Free Margin* will be updated:

    ![after_closing_position_wallet](https://user-images.githubusercontent.com/36813986/117704336-a2eaca00-b1c2-11eb-9db3-25a406252303.png)
    
* If the user has closed all of his positions:

    ![closed_positions](https://user-images.githubusercontent.com/36813986/117704411-b9912100-b1c2-11eb-92d2-ca6cd097a821.png)
    
* At the profile page, we can see that the _Margin = Profit = 0_ and that the _Balance = Equity = Free Margin_
since there is no current positions the user can be making a profit from or investing in:

    ![wallet_closed_positions](https://user-images.githubusercontent.com/36813986/117704504-d0d00e80-b1c2-11eb-8fd2-7d4865568c4f.png)


# FMS Trading Bot

**FMS Trading Bot** is an initiative of making an automated trading application that utilises machine learning to analyze
quotes and perform trading operations following an underlying algorithm. For the ML features, we used [WEKA](https://www.cs.waikato.ac.nz/ml/weka/)
for its ease of integration with Java applicatons and the workbench it provides to perform all sort of 
tests on that dataset before the implemntation.

## Data Collection & Processing

The first step to build this Robot is to collect the historical data required to build the classifier,
which [investigate.com](www.investing.com) provided.

The initial dataset features are *date*, *time*, *open*, *high*, *low* and *close* which then got filtered to
only the *open*, *high*, *low* and *close* since the *date* and *time* are irrelevant to this context.

The next step was to label the data, and our two classes are:
- **UP**: means that in the next hour the closing price will go up.
- **Down**: means that in the next hour the closing price will go down.

After collecting the data, it is now possible to use the Weka workbench Explorer to have a better understanding of our data,
also we made sure our data is balanced, so we avoid the overfitting problem.

Here we can see that we have a total of 2501 instances composed of 1195 UP and 1306 DOWN which is a good enough balance.

![weka_data](https://user-images.githubusercontent.com/36813986/117746262-0a753980-b204-11eb-950d-90794a487199.png)

## Classification Model Setup

After loading the data, we used the Weka workbench for testing the performance of the classifier and experimenting with 
models before using it in the Trading Bot application.

![weka_classify](https://user-images.githubusercontent.com/36813986/117746267-0c3efd00-b204-11eb-82f4-1d7bfd8c9a08.png)

After testing and exploring with the Weka workbench, the most accurate model and settings got wrapped in
the Trading Bot java application.

## Running the application locally

To Run the **Trading Bot** simply run this command to package the application:
    
    $ mvn package
    
then run it using this command:

    $ java -jar target/fms-trading-bot-0.0.1-SNAPSHOT.jar

Now the Trading Bot will build the classifier and will query the Market API to get the
Currency Pair EUR/USD open, high, low and close information and then predict the trend, you should see this output.

    Class Value 0 is UP
    Class Value 1 is Down
    Evaluation results:
    
    Correctly Classified Instances        1541               61.6154 %
    Incorrectly Classified Instances       960               38.3846 %
    Kappa statistic                          0.2337
    Mean absolute error                      0.4198
    Root mean squared error                  0.5101
    Relative absolute error                 84.1333 %
    Root relative squared error            102.1268 %
    Total Number of Instances             2501
    
    Correct % = 61.615353858456615
    Incorrect % = 38.384646141543385
    AUC = 0.652644377094453
    kappa = 0.23373486593168655
    MAE = 0.419839397574305
    RMSE = 0.5101317461821371
    RAE = 84.1333239139825
    RRSE = 102.12681198191825
    Precision = 0.6444073455759599
    Recall = 0.5911179173047473
    fMeasure = 0.6166134185303513
    Error Rate = 0.3838464614154338
    === Overall Confusion Matrix ===
    
       a   b   <-- classified as
     769 426 |   a = UP
     534 772 |   b = Down
    
    {"open": 1.16585,"low": 1.16584,"high": 1.16588,"close": 1.16587 }
    UP

After predicting what is the trend going to be the next hour (UP or DOWN), the Trading Bot will send a POST request
to the **FMS** API to open a long position (BUY) if the trend is predicted UP, OR a short position (SELL) if the trend is predicted DOWN.








