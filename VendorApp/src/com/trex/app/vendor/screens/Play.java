package com.trex.app.vendor.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sjuyal on 10/4/14.
 */
public class Play implements Screen {

    public static TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private int blocked[][];
    private int nearestSrcX, nearestSrcY, nearestDestX,  nearestDestY;
    private ArrayList<Rectangle> blocks;
    private static final int VIRTUAL_WIDTH = 1024;
    private static final int VIRTUAL_HEIGHT = 512;
    String message;
    BitmapFont font = null;
    public Play(String message) {
        this.message = message;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        renderer.setView(camera);
        renderer.render();
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "Dominoes", 40, 670);
        font.draw(spriteBatch, "PizzaHut", 180, 670);
        font.draw(spriteBatch, "Water Counter1", 310, 670);
        font.draw(spriteBatch, "WashRooms", 510, 670);
        font.draw(spriteBatch, "Water Counter2", 770, 670);
        font.draw(spriteBatch, "Subway", 970, 670);
        font.draw(spriteBatch, "KFC", 1165, 670);
        font.draw(spriteBatch, "McD", 1242, 670);

        spriteBatch.end();
        //renderer.getSpriteBatch().begin();
        //player.draw(renderer.getSpriteBatch());
        //renderer.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        /*float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);

        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        /*float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;

        camera.viewportWidth = w;
        camera.viewportHeight = h;
        camera.position.set(w/2,h/2,0);
        */
    }

    @Override
    public void show()  {
        map = new TmxMapLoader().load("data/newmap.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        font = new BitmapFont(Gdx.files.internal("fonts/droidserif.fnt"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.update();
        //camera.position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);
        //player = new Player(new Sprite(new Texture("img/player.png")));

        try {
            // 10.567 25.567
            //String json="{loc:[{\"location\":\"charmiar\",\"lng\":78.47466450000002,\"lat\":17.3615636},{\"location\":\"gachibowli\",\"lng\":78.47466450000452,\"lat\":17.3615636}]}";
            //String json = DataThread.getData("1:2");
            //JsonValue jsonValue = new JsonReader().parse(json);
            //System.out.println(jsonValue.get("xCoordinate"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String,String> seatHash = new HashMap<String, String>();
        HashMap<String,String> vendorHash = new HashMap<String, String>();


        //message="[{\"xCoordinate\":6.0,\"yCoordinate\":32.0},{\"xCoordinate\":27.0,\"yCoordinate\":0.0}]";
        //message = "6:12,Dominoes";
        //System.out.println(this.seatNo);
        makePath(map);

        int x = 0;
        int y = 0;
        int newX = 0;
        int newY = 0;

        String data[] = message.split(",");
        String Pair[] = data[0].split(":");
        y=Integer.parseInt(Pair[0]);
        x=Integer.parseInt(Pair[1]);


        vendorHash.put("Dominoes","4:29");
        vendorHash.put("PizzaHut", "11:29");
        vendorHash.put("Water Counter1", "19:29");
        vendorHash.put("WashRooms", "28:29");
        vendorHash.put("Water Counter2", "42:29");
        vendorHash.put("Subway", "50:29");
        vendorHash.put("KFC", "59:29");
        vendorHash.put("McD", "63:29");

        String result =vendorHash.get(data[1]);
        Pair = result.split(":");
        newX = Integer.parseInt(Pair[0]);
        newY = Integer.parseInt(Pair[1]);

        System.out.println("X:"+x+"-Y:"+y+"-newX:"+newX+"-newY:"+newY);
        drawPath(x, y, newX, newY);
    }

    private void nearestPathSrc(int x, int y){

        if(x < 9 && y < 7){
            if( Math.abs((x-9)) < Math.abs((y-7)) ){
                nearestSrcX = 9;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x > 9 && x < 31 && y < 7){
            int a = Math.abs(x-31), b= Math.abs(x-9), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestSrcX = 31;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = 9;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x > 31 && x < 54 && y < 7){
            int a = Math.abs(x-31), b= Math.abs(x-54), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestSrcX = 31;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = 54;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x > 54 && y < 7){
            if( Math.abs((x-54)) < Math.abs((y-7)) ){
                nearestSrcX = 54;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x < 9 && y < 24 && y > 7){
            int a = Math.abs(x-9), b= Math.abs(y-24), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestSrcX = 9;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = x;
                nearestSrcY = 24;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x > 54 && y > 7 && y < 24){
            int a = Math.abs(x-54), b= Math.abs(y-24), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestSrcX = 54;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = x;
                nearestSrcY = 24;
            }else{
                nearestSrcX = x;
                nearestSrcY = 7;
            }
        }else if(x > 31 && x < 54 && y > 24){
            int a = Math.abs(x-31), b= Math.abs(x-54), c=Math.abs(y-24);

            if(a<b && a<c){
                nearestSrcX = 31;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = 54;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 24;
            }
        }else if(x > 9 && x < 31 && y > 24){
            int a = Math.abs(x-9), b= Math.abs(x-31), c=Math.abs(y-24);
            System.out.println("Here"+a+":"+b+":"+c);

            if(a<b && a<c){
                nearestSrcX = 9;
                nearestSrcY = y;
            }else if(b<c){
                nearestSrcX = 31;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 24;
            }
        }else if(y > 24 && x < 9){
            if( Math.abs((y-24)) < Math.abs((x-9)) ){
                nearestSrcX = x;
                nearestSrcY = 24;
            }else{
                nearestSrcX = 9;
                nearestSrcY = y;
            }
        }else if(x > 54 && y > 24){
            if( Math.abs((x-54)) < Math.abs((y-24)) ){
                nearestSrcX = 54;
                nearestSrcY = y;
            }else{
                nearestSrcX = x;
                nearestSrcY = 24;
            }
        }

    }

    private void nearestPathDest(int x, int y){

        if(x < 9 && y < 7){
            if( Math.abs((x-9)) < Math.abs((y-7)) ){
                nearestDestX = 9;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x > 9 && x < 31 && y < 7){
            int a = Math.abs(x-31), b= Math.abs(x-9), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestDestX = 31;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = 9;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x > 31 && x < 54 && y < 7){
            int a = Math.abs(x-31), b= Math.abs(x-54), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestDestX = 31;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = 54;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x > 54 && y < 7){
            if( Math.abs((x-54)) < Math.abs((y-7)) ){
                nearestDestX = 54;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x < 9 && y < 24 && y > 7){
            int a = Math.abs(x-9), b= Math.abs(y-24), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestDestX = 9;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = x;
                nearestDestY = 24;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x > 54 && y > 7 && y < 24){
            int a = Math.abs(x-54), b= Math.abs(y-24), c=Math.abs(y-7);

            if(a<b && a<c){
                nearestDestX = 54;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = x;
                nearestDestY = 24;
            }else{
                nearestDestX = x;
                nearestDestY = 7;
            }
        }else if(x > 31 && x < 54 && y > 24){
            int a = Math.abs(x-31), b= Math.abs(x-54), c=Math.abs(y-24);

            if(a<b && a<c){
                nearestDestX = 31;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = 54;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 24;
            }
        }else if(x > 9 && x < 31 && y > 24){
            int a = Math.abs(x-9), b= Math.abs(x-31), c=Math.abs(y-24);
            System.out.println("Here"+a+":"+b+":"+c);

            if(a<b && a<c){
                nearestDestX = 9;
                nearestDestY = y;
            }else if(b<c){
                nearestDestX = 31;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 24;
            }
        }else if(y > 24 && x < 9){
            if( Math.abs((y-24)) < Math.abs((x-9)) ){
                nearestDestX = x;
                nearestDestY = 24;
            }else{
                nearestDestX = 9;
                nearestDestY = y;
            }
        }else if(x > 54 && y > 24){
            if( Math.abs((x-54)) < Math.abs((y-24)) ){
                nearestDestX = 54;
                nearestDestY = y;
            }else{
                nearestDestX = x;
                nearestDestY = 24;
            }
        }

    }

    private void drawPath(int x, int y, int newX, int newY){
        nearestPathSrc(x,y);
        //nearestPathDest(newX,newY);
        nearestDestX = newX;
        nearestDestY = newY;
        System.out.println("X:"+nearestSrcX+"-Y:"+nearestSrcY+"-XX:"+nearestDestX+"-YY:"+nearestDestY);
        if(blocked[nearestSrcX][nearestSrcY]==1){
            TiledMapTileLayer layer = (TiledMapTileLayer) this.map.getLayers()
                    .get(0);
            TiledMapTile tile =map.getTileSets().getTile(27097);
            layer.getCell(x,y).setTile(tile);
            if(x==nearestSrcX){
                int a=y<nearestSrcY?y:nearestSrcY;
                int b=y>nearestSrcY?y:nearestSrcY;
                for(int j=a;j<=b;j++){
                    layer.getCell(x,j).setTile(tile);
                }
            }
            if(y==nearestSrcY){
                int a=x<nearestSrcX?x:nearestSrcX;
                int b=x>nearestSrcX?x:nearestSrcX;
                for(int j=a;j<=b;j++){
                    layer.getCell(j,y).setTile(tile);
                }
            }
            tile =map.getTileSets().getTile(648);
            layer.getCell(nearestSrcX,nearestSrcY).setTile(tile);
        }
        if(blocked[nearestDestX][nearestDestY]==1){
            TiledMapTileLayer layer = (TiledMapTileLayer) this.map.getLayers()
                    .get(0);
            TiledMapTile tile =map.getTileSets().getTile(21104);
            layer.getCell(newX,newY).setTile(tile);
            if(newX==nearestDestX){
                int a=newY<nearestDestY?newY:nearestDestY;
                int b=newY>nearestDestY?newY:nearestDestY;
                for(int j=a;j<=b;j++){
                    layer.getCell(newX,j).setTile(tile);
                }
            }
            if(newY==nearestDestY){
                int a=newX<nearestDestX?newX:nearestDestX;
                int b=newX>nearestDestX?newX:nearestDestX;
                for(int j=a;j<=b;j++){
                    layer.getCell(j,newY).setTile(tile);
                }
            }
            tile =map.getTileSets().getTile(648);
            layer.getCell(nearestDestX,nearestDestY).setTile(tile);
        }

        java.util.List<String> output =BredthFirstGrid.convert(blocked,nearestSrcX,nearestSrcY,nearestDestX,nearestDestY,64,32);
        TiledMapTileLayer layer = (TiledMapTileLayer) this.map.getLayers()
                .get(0);
        TiledMapTile tile =map.getTileSets().getTile(648);
        System.out.println(output);
        for(String pair : output){
            int index = pair.indexOf(',');
            x = Integer.parseInt(pair.substring(0,index));
            y = Integer.parseInt(pair.substring(index+1));
            layer.getCell(x,y).setTile(tile);
        }
    }

    private void makePath(TiledMap map){

        for (int i = 0; i < this.map.getLayers().getCount(); i++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) this.map.getLayers()
                    .get(i);
            blocked = new int[layer.getWidth()][layer.getHeight()];
            System.out.println(layer.getWidth()+":"+layer.getHeight());
            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    if (layer.getCell(x, y) != null
                            && layer.getCell(x, y).getTile().getProperties()
                            .containsKey("path")) {
                        //System.out.println(layer.getCell(x, y).getTile());
                        blocked[x][y] = 1;
                    }else{
                        blocked[x][y] = 0;
                    }
                }
            }
        }



    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
