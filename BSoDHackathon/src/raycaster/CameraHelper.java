/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raycaster;

import Utilities.ImageCollection;
import Utilities.Rect;
import Utilities.Vector2;

/**
 *
 * @author KyleSweeney
 */
public class CameraHelper implements Runnable{
    
    private Camera camera;
    private int start;
    private int finish;
    ImageCollection batch;
    
    //Raycasting stuff;
    double mindist;
    double dist;
    
    double theta;
    double angle;
    double angleOffset;
    double angleStep;
    
    //player X,Y
    double X;
    double Y;
    
    double x,y;
    double dx,dy;
    
    double cellSize;
    double rayHeight;
    double sf;
    double screenX;
    double xStep;
    double screenHeight;
    
    boolean textures;
    
    
    public CameraHelper(int start, int finish, double X, double Y, 
            boolean textures, double sf, ImageCollection batch, 
            double angleOffset, double angleStep, double screenX, double xStep,
            double screenHeight, double angle){
        camera=Camera.instance;
        this.start=start;
        this.finish=finish;
        this.X=X;
        this.Y=Y;
        this.textures=textures;
        this.sf=sf;
        this.batch=batch;
        cellSize=camera.cellSize;
        this.angleOffset=angleOffset;
        this.angleStep=angleStep;
        this.screenX=screenX;
        this.xStep=xStep;
        this.screenHeight=screenHeight;
        this.angle=angle;
    }

    @Override
    public void run() {
        double tan;
        double cot;
        Rect rect;
        boolean done;
        
        angleOffset += (angleStep*start);
        screenX += (xStep*start);
        
        for (int i = this.start; i < finish; i++) {
            mindist = 10000;
            theta = (angle - angleOffset);
            if (theta > Math.PI * 2) {
                theta -= Math.PI * 2;
            }
            if (theta < 0) {
                theta += Math.PI * 2;
            }
            if (theta == Math.PI * 0.5 || theta == Math.PI * 1 / 5) {
                tan = 1000000;
                cot = 0;
            } else if (theta == 0 || theta == Math.PI) {
                tan = 0;
                cot = 1000000;
            } else {
                tan = Math.tan(theta);
                cot = 1 / tan;
            }

            x = X;
            y = Y;
            if (theta > Math.PI) {
                dy = cellSize * Math.ceil(y / cellSize) - y;
            } else {
                dy = cellSize * Math.floor(y / cellSize) - y;
            }
            dx = -cot * dy;
            x += dx;
            y += dy;
            //dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
            if ((camera.cell(x, y + dy / 2) > 0)) {
                mindist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y)) + 1;
            } else {
                done = false;
                if (dy > 0) {
                    dy = cellSize;
                } else {
                    dy = -cellSize;
                }
                dx = -cot * dy;
                while (!done) {
                    x += dx;
                    y += dy;
                    dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
                    if ((camera.cell(x, y + dy / 2) > 0) && !done) {
                        mindist = dist;
                        done = true;
                    }
                }
            }

            x = X;
            y = Y;


            //check the nearest vertical wall
            if (theta < Math.PI * 0.5 || theta > Math.PI * 1.5) {
                dx = cellSize * Math.ceil(x / cellSize) - x;
            } else {
                dx = cellSize * Math.floor(x / cellSize) - x;
            }
            dy = -tan * dx;
            x += dx;
            y += dy;
            dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));

            if (camera.cell(x + dx / 2, y) > 0 && dist <= mindist) {
                //rayTexs[i]=cell(x+dx/2,y);
                rayHeight = (sf / (dist * Math.cos(angleOffset)));
                if (!textures) {
                    rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2 - rayHeight / 2), (int) xStep, (int) rayHeight);
                    camera.fillRect(batch, rect, camera.getCol(camera.cell(x + dx / 2, y), dist, false), (int) rayHeight + 1000);
                } else {
                    camera.drawTex(batch, i, camera.cell(x + dx / 2, y), screenX, screenHeight / 2, x + y, rayHeight, false);
                }
                mindist = dist;
            } else {
                done = false;
                if (dx > 0) {
                    dx = cellSize;
                } else {
                    dx = -cellSize;
                }
                dy = -tan * dx;
                while (!done) {
                    x += dx;
                    y += dy;
                    dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
                    if (dist > mindist) {
                        done = true;
                    }
                    if (camera.cell(x + dx / 2, y) > 0 && !done) {
                        rayHeight = (sf / (dist * Math.cos(angleOffset)));
                        if (!textures) {
                            rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2 - rayHeight / 2), (int) xStep, (int) rayHeight);
                            camera.fillRect(batch, rect, camera.getCol(camera.cell(x + dx / 2, y), dist, false), (int) rayHeight + 1000);
                        } else {
                            camera.drawTex(batch, i, camera.cell(x + dx / 2, y), screenX, screenHeight / 2, x + y, rayHeight, false);
                        }
                        mindist = dist;
                        done = true;
                    } else if (!done && dist < 2000) {
                        rayHeight = (sf / (dist * Math.cos(angleOffset)));
                        rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2), (int) xStep, (int) rayHeight / 2);
                        if (camera.floor) {
                            camera.fillRect(batch, rect, camera.getCol(camera.cell(x + dx / 2, y), dist, true), (int) dist / 2);
                        }
                    }
                }
            }
            //checks the nearest horizontal wall
            x = X;
            y = Y;
            if (theta > Math.PI) {
                dy = cellSize * Math.ceil(y / cellSize) - y;
            } else {
                dy = cellSize * Math.floor(y / cellSize) - y;
            }
            dx = -cot * dy;
            x += dx;
            y += dy;
            dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
            if ((camera.cell(x, y + dy / 2) > 0)) {
                //dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
                if (dist < mindist) {

                    rayHeight = (sf / (dist * Math.cos(angleOffset)));
                    if (!textures) {
                        rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2 - rayHeight / 2), (int) xStep, (int) rayHeight);
                        camera.fillRect(batch, rect, camera.getCol(camera.cell(x, y + dy / 2), dist, true), (int) rayHeight + 1000);
                    } else {
                        camera.drawTex(batch, i, camera.cell(x, y + dy / 2), screenX, screenHeight / 2, x + y, rayHeight, true);
                    }
                }
            } else {
                done = false;
                if (dy > 0) {
                    dy = cellSize;
                } else {
                    dy = -cellSize;
                }
                dx = -cot * dy;
                while (!done) {
                    x += dx;
                    y += dy;
                    dist = Math.sqrt((x - X) * (x - X) + (y - Y) * (y - Y));
                    if (dist > mindist) {
                        done = true;
                    }
                    if ((camera.cell(x, y + dy / 2) > 0) && !done) {
                        rayHeight = (sf / (dist * Math.cos(angleOffset)));
                        if (!textures) {
                            rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2 - rayHeight / 2), (int) xStep, (int) rayHeight);
                            camera.fillRect(batch, rect, camera.getCol(camera.cell(x, y + dy / 2), dist, true), (int) rayHeight + 1000);
                        } else {
                            camera.drawTex(batch, i, camera.cell(x, y + dy / 2), screenX, screenHeight / 2, x + y, rayHeight, true);
                        }
                        done = true;
                    } else if (!done && dist < 2000) {
                        rayHeight = (sf / (dist * Math.cos(angleOffset)));
                        rect = new Rect(new Vector2(screenX - xStep / 2, screenHeight / 2), (int) xStep, (int) rayHeight / 2);
                        if (camera.floor) {
                            camera.fillRect(batch, rect, camera.getCol(camera.cell(x, y + dy / 2), dist, true), (int) dist / 2);
                        }
                    }
                }
            }
            angleOffset += angleStep;
            screenX += xStep;

        }
    }
    
}
