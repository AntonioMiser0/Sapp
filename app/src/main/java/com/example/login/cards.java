package com.example.login;

public class cards {
    private String userId;
    private String name;
    private String imageUrl;
    public cards(String userId,String name,String imageUrl){
        this.userId=userId;
        this.name=name;
        this.imageUrl=imageUrl;
    }
    public String getimageUrl(){
        return imageUrl;
    }
    public void setimageUrl(String imageUrl){
       this.imageUrl=imageUrl;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public String getName(){
        return name;
    }
    public void setName(String userId){
        this.name=name;
    }

}
