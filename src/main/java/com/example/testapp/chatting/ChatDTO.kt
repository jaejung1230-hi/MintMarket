package com.example.testapp.chatting

class ChatDTO {
    var userName :String = ""
    var messageContent:String = ""

    constructor()

    constructor(userName :String, messageContent:String){
        this.userName = userName
        this.messageContent = messageContent
    }
}
