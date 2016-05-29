[![CircleCI](https://circleci.com/gh/adityam7/ChatStat/tree/master.svg?style=svg)](https://circleci.com/gh/adityam7/ChatStat/tree/master)
# ChatStat v1.0.1
Using the API end point to retrieve messages and displaying the displaying them in two tabs

## Chat Tab
Here the chat is shown in order, the server does not send the messages in order so ordering is done locally and then the messages are saved.
You can even like a message. When a message is liked a broadcast is sent so everyone can refresh their data.

## Stat Tab
Here the stats of the group chat is displayed:
* Number of messages sent by a person
* Number of messages sent by a person that have been liked.

## ChatStat v1.0.1
Now runs 4 unit tests to check on the HomePresenter.

## Instructions for Set Up on your machine
* The Mock flavor is only for unit testing DO NOT try to run the mockDebug flavour on the emulator or device unless you like making network calls on the mainthread
* I Added the app.iml file by mistake on github so if the build does not compile try deleting this file and trying to build again.

### Third-Party Libraries Used
* Android Support AppCompact
* Android Support Design
* Android Support RecyclerView
* Android Support CardView
* Glide
* ButterKnife
* Retrofit
* RxJava
* RxAndroid
* GreenDao