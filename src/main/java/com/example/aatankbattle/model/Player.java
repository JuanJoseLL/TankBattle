package com.example.aatankbattle.model;

public class Player {
        private static Player instance;

        public Avatar player1;
        public Avatar player2;
        public Enemy player3;

        private Player(){
            this.player1=new Avatar(1);
            this.player2=new Avatar(2);
            this.player3=new Enemy();
        }

        public static Player getInstance(){
            if(instance==null){
                instance= new Player();
            }

            return instance;
        }
        public void createPlayers(String name1,String name2,String name3){
            this.player1.setName(name1);
            this.player2.setName(name2);
            this.player3.setName(name3);
        }

        public Avatar getPlayer1(){
            return player1;
        }

        public void setPlayer1(Avatar player1){
            this.player1= player1;
        }

        public Avatar getPlayer2(){return player2;}

        public void setPlayer2(Avatar player2){
            this.player2= player2;
        }

}
